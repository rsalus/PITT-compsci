import java.util.Random;
public class Add128 implements SymCipher {
    private byte[] key;

    public Add128(){
        //randomly generate a 128 sized bytes array
        key = new byte[128];
        new Random().nextBytes(key);
    }

    public Add128(byte[] newKey){
        //use existing key to construct
        key = newKey;
    }

    public byte[] getKey(){
        return key;
    }

    public byte[] encode(String s){
        //for byte in s:
        //s = s + key[i]; i++;
        //if out of keys boundary, go from first
        byte[] convert = s.getBytes();
        int k = 0;
        for(int i = 0; i < convert.length; i++){
            if(k == key.length) k = 0;  //overflow
            convert[i] = (byte) (convert[i] + key[k++]);
        } return convert;
    }

    public String decode(byte[] bytes){
        //do reverse of encode
        int k = 0;
        for(int i = 0; i < bytes.length; i++){
            if(k == key.length) k = 0;  //overflow
            bytes[i] = (byte) (bytes[i] - key[k++]);
        } return new String(bytes);
    }
}