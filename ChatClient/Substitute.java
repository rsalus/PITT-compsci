import java.util.Random;
public class Substitute implements SymCipher {
    private byte [] key, deKey;
    private Random ran = new Random();

    public Substitute(){
        //generate a 256 encode and decode map randomly
        key = new byte[256];
        for(int i = 0; i < key.length; i++)
            key[i] = (byte) i;
        shuffle(key);
        deKey = invert(key);
    }

    public Substitute(byte[] newKey){
        //use existing key to construct
        key = newKey;
        deKey = invert(key);
    }

    public byte[] getKey(){
        return key;
    }

    public byte[] encode(String s){
        //TA: for byte in s.. s = encode[s];
        byte[] convert = s.getBytes();
        for(int i = 0; i < convert.length; i++){
            convert[i] = key[convert[i] & 0xFF];  //bitwise pos
        } return convert;
    }

    public String decode(byte[] bytes){
        //do reverse of encode using decode map
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = deKey[bytes[i] & 0xFF];  //bitwise pos
        } return new String(bytes);
    }

    private void shuffle(byte[] arr){   //O(n)
        for(int i = arr.length - 1; i > 0; i--){
            int index = ran.nextInt(i + 1);
            byte curr = arr[index];
            arr[index] = arr[i];
            arr[i] = curr;
        }
    }

    private byte[] invert(byte[] arr){
        byte[] inverse = new byte[256];
        for(int i = 0; i < inverse.length; i++){
            inverse[arr[i] & 0xFF] = (byte) i;  //bitwise pos
        } return inverse;
    }
}