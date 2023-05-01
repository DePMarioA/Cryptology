import java.math.BigInteger;
import java.util.HashMap;
/**
 *
 *
 *
 C:\Users>java SDES 0 0
 Expanded: 00000000
 XORR: 00000000
 Input to sbox 1: 0000
 Input to sbox 2: 0000
 Output to sbox 1: 101
 output to sbox 2: 100
 Output of f: 101100
 Expanded: 00000000
 XOR with left: 101100
 000000101100
 44

 C:\Users>java SDES 0 85
 Expanded: 00000000
 XORR: 01010101
 Input to sbox 1: 0101
 Input to sbox 2: 0101
 Output to sbox 1: 100
 output to sbox 2: 001
 Output of f: 100001
 Expanded: 00000000
 XOR with left: 100001
 000000100001
 33






 public static void main(String[] args) {
        Helper();
        String Cipher = round(1365,85);
        System.out.println(Cipher);
        System.out.println(Integer.parseInt(Cipher,2));

    }


* Expanded: 01101001
XORR: 00111100
Input to sbox 1: 0011
Input to sbox 2: 1100
Output to sbox 1: 110
output to sbox 2: 110
Output of f: 110110
Expanded: 01101001
XOR with left: 100011
010101100011
1379

Process finished with exit code 0
**/
public class SDES {

    public static void main(String[] args) {
        Helper();
        if(args.length!=0){
        int Plaintext= Integer.parseInt(args[0]);
        int subKey= Integer.parseInt(args[1]);
        if(Plaintext>4095||subKey>255||Plaintext<0||subKey<0){
            System.out.println("Please input plaintext first from 0-4095 and then Subkey from 0-255");     }
        else {
            String Cipher = round(Plaintext, subKey);
            System.out.println(Cipher);
            System.out.println(Integer.parseInt(Cipher, 2));
        }
        }
        else{
            System.out.println("Please input 2 integers like so: java SDES 1365 85");}
    }


    public static String round(int plaintext, int subkey){
        String subKey = Integer.toBinaryString(subkey);
        while(subKey.length()<8){
            subKey="0"+subKey;
        }
        String plainText = Integer.toBinaryString(plaintext);
        while(plainText.length()<12){
            plainText="0"+plainText;
        }

        String textR="";
        String textL="";
        for(int i = 0; i<plainText.length();i++){
            if(i>5){
                textR+=plainText.charAt(i);}
            else
                textL+=plainText.charAt(i);
        }
//        System.out.println(textR);
//        System.out.println("Text L : " +textL);
//        System.out.println(subKey);
//        System.out.println(plainText);
        String OutputF = encryptRound(textR,subKey);
        System.out.println("Output of f: " +OutputF);
        String ExpandedTL = Expander(textL);
        String XORL = xor(textL,OutputF);
        System.out.println("XOR with left: " +XORL);
        return textL+XORL;

// 010101100011
    }
    public static String encryptRound(String plaintext, String subKey){
        String expandedTR = Expander(plaintext);
        String XORR;
        XORR = xor(expandedTR,subKey);
        System.out.println("XORR: "+ XORR);
        String SR1="";
        String SR2="";
        for(int i = 0; i<XORR.length();i++){
            if(i>3){
                SR2+=XORR.charAt(i);}
            else
                SR1+=XORR.charAt(i);
        }
        System.out.println("Input to sbox 1: " +SR1+ "\n"+ "Input to sbox 2: " + SR2);

       String SR1O;
        String SR2O;
       SR1O = Sbox1(SR1);
        SR2O = Sbox2(SR2);
        System.out.println("Output to sbox 1: "+SR1O +"\noutput to sbox 2: " + SR2O);
        return SR1O+SR2O;

    }
    public static String Sbox1(String SR){
        String[][] S1 = {{"101", "010", "001", "110", "011", "100", "111", "000"},
                {"001", "100", "110", "010", "000", "111", "101", "011"}};
        String temp = "";
        if(SR.charAt(0)=='0'){
            temp = S1[0][Sbox1.get(SR.substring(1))];
        }
        else{
            temp = S1[1][Sbox1.get(SR.substring(1))];

        }
        return temp;

    }
    public static String Sbox2(String SR){
        String S2[][] = {{"100", "000", "110", "101", "111", "001", "011", "010"},
                {"101", "011", "000", "111", "110", "010", "001", "100"}};
        String temp = "";
        if(SR.charAt(0)=='0'){
            temp = S2[0][Sbox1.get(SR.substring(1))];
        }
        else{
            temp = S2[1][Sbox1.get(SR.substring(1))];

        }
        return temp;

    }

    public static HashMap<String,Integer> Sbox1 = new HashMap();
    public static void Helper(){
        Sbox1.put("000",0);
        Sbox1.put("001",1);
        Sbox1.put("010",2);
        Sbox1.put("011",3);
        Sbox1.put("100",4);
        Sbox1.put("101",5);
        Sbox1.put("110",6);
        Sbox1.put("111",7);


    }


    public static String Expander(String s){
        StringBuilder expanded = new StringBuilder();
        expanded.append(s.charAt(0)).append(s.charAt(1)).append(s.charAt(3)).append(s.charAt(2)).append(s.charAt(3)).append(s.charAt(2)).append(s.charAt(4)).append(s.charAt(5));
        //+s.charAt(3)+s.charAt(2)+s.charAt(3)+s.charAt(2)+s.charAt(4)+s.charAt(5);
        System.out.println("Expanded: "+ expanded.toString());
        return expanded.toString();
    }
    public static String xor(String r, String subKey){
        String round1="";
        for(int i = 0;i<r.length();i++) {

            round1+=  Integer.parseInt(String.valueOf(subKey.charAt(i))) ^
                    Integer.parseInt(String.valueOf(r.charAt(i))) ;
        } ;
        return round1;
    }

}
