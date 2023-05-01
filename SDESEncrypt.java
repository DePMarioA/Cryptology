import java.util.Arrays;
import java.util.HashMap;
/**
 *  >javac SDESEncrypt.java
 C:\Users\src>java SDES 4095 255
 [01111111, 11111111, 11111110, 11111101]

 round:0
 left: 111111
 right: 111111
 input to fxn: 111111 01111111
 Expanded: 11111111
 XORR: 10000000
 Input to sbox 1: 1000
 Input to sbox 2: 0000
 Output to sbox 1: 001
 output to sbox 2: 100
 Output of f: 001100
 XOR with left: 110011
 111111 110011

 round:1
 left: 111111
 right: 110011
 input to fxn: 110011 11111111
 Expanded: 11000011
 XORR: 00111100
 Input to sbox 1: 0011
 Input to sbox 2: 1100
 Output to sbox 1: 110
 output to sbox 2: 110
 Output of f: 110110
 XOR with left: 001001
 111111 001001

 round:2
 left: 110011
 right: 001001
 input to fxn: 001001 11111110
 Expanded: 00010101
 XORR: 11101011
 Input to sbox 1: 1110
 Input to sbox 2: 1011
 Output to sbox 1: 101
 output to sbox 2: 111
 Output of f: 101111
 XOR with left: 011100
 110011 011100

 round:3
 left: 001001
 right: 011100
 input to fxn: 011100 11111101
 Expanded: 01111100
 XORR: 10000001
 Input to sbox 1: 1000
 Input to sbox 2: 0001
 Output to sbox 1: 001
 output to sbox 2: 000
 Output of f: 001000
 XOR with left: 000001
 001001 000001
 011100000001
 1793

 **/
public class SDESEncrypt {

    public static void main(String[] args) {
        Helper();
        if(args.length!=0){
            int Plaintext= Integer.parseInt(args[0]);
            int subKey= Integer.parseInt(args[1]);
            if(Plaintext>4095||subKey>511||Plaintext<0||subKey<0){
                System.out.println("Please input plaintext first from 0-4095 and then Subkey from 0-255");     }
            else {
                String subKey_ = Integer.toBinaryString(subKey);
                while(subKey_.length()<9){
                    subKey_="0"+subKey_;
                }
                String plainText = Integer.toBinaryString(Plaintext);
                while(plainText.length()<12){
                    plainText="0"+plainText;
                }
                String keySchedule[] = new String[4];
                generateSchedule(subKey_,keySchedule);
                System.out.println(Arrays.toString(keySchedule));
//                System.out.println(subKey_);
                //String Cipher = round(plainText, keySchedule[0]);
               // System.out.println("Cypher: "+ Cipher);
                int L=0;
                String cipherCyle= plainText;
                for(String k:keySchedule){
                    System.out.println("\n round:"+ L);
                    L++;
                    cipherCyle= encrypt(cipherCyle,k);
                }
                System.out.println(cipherCyle);
                System.out.println(Integer.parseInt(cipherCyle, 2));
//                System.out.println(Integer.parseInt(Cipher, 2));
            }
        }
        else{
            System.out.println("Please input 2 integers like so: java SDES 1365 85");}
    }
    public static String encrypt(String cipher, String key){
        return round(cipher,key);
    }

    public static void generateSchedule(String key, String [] x){
        String tempKey= "";
//        for (int i = 0; i<key.length()-1;i++){
//            tempKey+= key.charAt(i);
//        }
//        x[0]=tempKey;
//        tempKey="";
//        for (int i = 1; i<key.length();i++){
//            tempKey+= key.charAt(i);
//        }
//        x[1]=tempKey;
//        tempKey="";
//        for (int i = 2; i<key.length();i++){
//            tempKey+= key.charAt(i);
//        }
        //System.out.println(key);
        for(int j =0;j<4;j++){
            for (int i = j; i<key.length();i++){
                if(j==0){
                    if(i<key.length()-1){
                        tempKey+=key.charAt(i);
                    }
                }else
                tempKey+=key.charAt(i);
            }
//            System.out.println("in loop :" +tempKey);
            x[j]=tempKey;
            tempKey="";
        }
        int i=0;
        int j=0;

        while(i<x.length){
            String y ="";
            while(x[i].length()<8){
                x[i]+=key.charAt(j);
                j++;
            }
            i++;
            j=0;
        }
//        System.out.println(Arrays.toString(x));
//    return x;

    }

    public static String round(String plaintext, String subkey){

        String textR="";
        String textL="";
        for(int i = 0; i<plaintext.length();i++){
            if(i>5){
                textR+=plaintext.charAt(i);}
            else
                textL+=plaintext.charAt(i);
        }
        System.out.println("left: "+textL);
        System.out.println("right: "+textR);
        String OutputF = encryptRound(textR,subkey);
        System.out.println("Output of f: " +OutputF);
        String XORL = xor(textL,OutputF);
        String XORL2 = xor(OutputF,textL);
        System.out.println("XOR with left: " +XORL);
        System.out.println(textL+ " "+ XORL);
        return textR+XORL;

// 010101100011
    }
    public static String encryptRound(String plaintext, String subKey){
        System.out.println("input to fxn: "+plaintext+" "+subKey);
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
