import java.math.BigInteger;
import java.util.Arrays;

/***
*
 *
 C:\Users>java BBS 010100110100010101000011010101010101001001000101
 key: [0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0]
text: [0, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1]
Cipher: [0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1]

* ***/

public class BBS {
    public static void main(String[] args) {
        BigInteger[] key = bbs((args[0].length()));
        BigInteger[] text = new BigInteger[args[0].length()];
        for(int i = 0; i<args[0].length();i++){
           text[i]= new BigInteger(String.valueOf(args[0].charAt(i)));
        }

        System.out.println("text: "+Arrays.toString( text));
        String Key="";
        for(int i=0; i<key.length;i++){
            Key+=key[i];
            if((i+1)%4==0){
                Key+=" ";
            }
        }
        BigInteger[] Cipher = new BigInteger[args[0].length()];
        String CipherStr="";
        for(int i = 0;i<args[0].length();i++) {
            Cipher[i] = new BigInteger(String.valueOf(Integer.parseInt(String.valueOf((text[i]))) ^ Integer.parseInt(String.valueOf((key[i])))));
            CipherStr += Cipher[i];
            if ((i + 1) % 4 == 0) {
                CipherStr += " ";
            }
        }
        System.out.println("Cipher: "+ Arrays.toString(Cipher));

        System.out.println(CipherStr);
//        System.out.println("Plaintext: "+args[0]);
//        System.out.println("Key: "+ Key);


    }

    public static BigInteger[] bbs(int k){
        BigInteger[] list2 = new BigInteger[k];
        BigInteger p = new BigInteger("24672462467892469787");
        BigInteger q = new BigInteger("396736894567834589803");
        BigInteger n = new BigInteger(String.valueOf(p.multiply(q)));
        BigInteger x = new BigInteger("873245647888478349013");

        for(int i =0;i<k;i++){
            x=(x.multiply(x).mod(n));
            list2[i]= x.mod(new BigInteger("2"));
        }
        System.out.println("key: "+Arrays.toString(list2));
        //System.out.println(list2.length);

        return list2;
    }


}
