public class TrieSTNew<Value>
{
    private static final int R = 256;        // extended ASCII
    private Node root;

    private static class Node {
        private Object val;
        private Node [] next = new Node[R];
        private int degree;  // how many children?
    }

   /****************************************************
    * Is the key in the symbol table?
    ****************************************************/
    public boolean contains(StringBuilder key) {
        return get(key) != null;
    }

    public Value get(StringBuilder key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return (Value) x.val;
    }

    private Node get(Node x, StringBuilder key, int d) {
        if (x == null) return null;		// Base case not found
        if (d == key.length()) return x;	// Base case found
        char c = key.charAt(d);			// get next char of key
        return get(x.next[c], key, d+1);	// follow reference for that char
    }

   /****************************************************
    * Insert key-value pair into the symbol table.
    ****************************************************/
    // Note that each "key" is not actually put into the trie directly
    // but rather it is stored indirectly via a path from the root to a
    // leaf.  Note also that the value is placed in the node that comes 
    // AFTER the last character match.  For example, if the key "cat"
    // were inserted, 4 nodes would be created.  The first 3 would be used
    // to branch down the tree and the final node would be used to store
    // the value.
    public void put(StringBuilder key, Value val) {
        root = put(root, key, val, 0);
    }

    private Node put(Node x, StringBuilder key, Value val, int d) {
        if (x == null) x = new Node();	// Base case - add new Node
        if (d == key.length()) {		// We are past the end of the key
            x.val = val;				// so add the value
            return x;
        }
        char c = key.charAt(d);			// get char to branch
        if (x.next[c] == null)			// If reference was previously null, we
        	x.degree++;					// are adding a new link, so increase the
        								// degree
        x.next[c] = put(x.next[c], key, val, d+1);	// Assign link with
        								// recursive call
        return x;
    }

	// This method will return one of four possible values:
	// 0 if the key is neither found in the TrieST nor a prefix to
	//      a key in the TrieST
	// 1 if the key is a prefix to some string in the TrieST but it
	//      is not itself in the TrieST
	// 2 if the key is found in the TrieST but it is not a prefix to
	//      a longer string within the TrieST
	// 3 if the key is found in the TrieST and is also a prefix to added
	//      longer string within the TrieST
	// Since this method is just traversing down the tree it could easily
	// be implemented iteratively.  However I am implementing it recursively
	// to be consistent with the author's implementations.
	public int searchPrefix(StringBuilder key)
	{
		return searchPrefix(root, key, 0);
	}
	
	private int searchPrefix(Node x, StringBuilder key, int d)
	{
		if (x == null)  // null reference means not found and not prefix
			return 0;
		else
		{
			if (d == key.length()) // all chars in the key are found
			{
				int total = 0;
				if (x.val != null)
					total += 2;  // val is non-null so key is found
				if (x.degree > 0)
					total += 1;  // x has at least one child so key
							     // is a prefix
				return total;
			}
			else return searchPrefix(x.next[key.charAt(d)], key, d+1);
		}
	}
}