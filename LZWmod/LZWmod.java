/*************************************************************************
 *  Compilation:  javac LZWmod.java
 *  Execution:    java LZWmod - < input.txt   (compress)
 *  Execution:    java LZWmod + < input.txt   (expand)
 *  Execution:    java LZWmod - r < input.txt (reset dictionary)
 *  Execution:    java LZWmod - n < input.txt (don't reset dictionary)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *************************************************************************/

public class LZWmod {
    private static final int R = 256;       // number of input chars
    private static int L = 512;             // number of codewords = 2^W
    private static int W = 9;               // codeword width
    private static char sentinel;           // dictionary reset sentinel

    public static void compress() {
        /* INIT */
        if(sentinel != '\u0000') BinaryStdOut.write(sentinel);
        TrieSTNew<Integer> st = new TrieSTNew<>();
        int code = R+1;     // R is codeword for EOF
        for (int i = 0; i < R; i++)     //fill codebook with ASCII
            st.put(new StringBuilder("" + (char) i), i);

        /* COMPRESS OPERATION */
        char prev = ' ';
        StringBuilder curr = new StringBuilder("" + prev);
        while(!BinaryStdIn.isEmpty()){
            //read next char in
            prev = BinaryStdIn.readChar();
            curr.append(prev);

            //match prefix to dictionary
            int prefix = st.searchPrefix(curr);
            if(prefix < 2){     //2 indicates longest prefix
                prev = curr.charAt(curr.length()-1);
                curr.deleteCharAt(curr.length()-1);
                BinaryStdOut.write(st.get(curr), W);

                //modify codebook
                if(code >= L && W < 16){ L <<= 1; W++; }
                if(sentinel == 'r' && (code >= L && W >= 16)){
                    //reset dictionary
                    st = new TrieSTNew<>();
                    L = 512; W = 9;
                    code = R+1;
                    for (int i = 0; i < R; i++)
                        st.put(new StringBuilder("" + (char) i), i);
                }

                //add to codebook
                if(code < L){ 
                    curr.append(prev);
                    st.put(curr, code++);
                }
                
                //reset
                curr.delete(0, curr.length());
                curr.append(prev);
            }
        }
        /* FINISH */
        BinaryStdOut.write(R, W);
        BinaryStdOut.close();
    }

    public static void expand() {
        String[] st = new String[L << 7];   //65536
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        char sent = BinaryStdIn.readChar();
        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            if(i >= L && W < 16){ L <<= 1; W++; }   //modify codebook
            if(sent == 'r' && (i >= L && W >= 16)){
                //reset dictionary
                L = 512; W = 9;
                st = new String[L << 7];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = ""; 
            }
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args.length > 1)   sentinel = args[1].charAt(0);
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new RuntimeException("Illegal command line argument");
    }
}