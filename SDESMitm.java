//Mario Alvarado
import java.util.*;

/**
 *
 *
 * C:\src>java  0 247 4095 2808
 * [19, 10, 106, 125, 135, 200, 264, 143, 43, 25, 220, 292, 170, 67, 287, 29, 111, 349, 340, 64, 264, 256, 137, 359, 392]
 * [20, 103, 110, 125, 50, 230, 105, 3, 268, 96, 170, 38, 44, 52, 0, 226, 4, 93, 315, 150, 228, 452, 237, 98, 258]
 * 125
 * 170
 **/
public class SDESMitm {
    //HashMap<output,keyused>

    public static HashMap<Integer,Integer> initialKeys= new HashMap<>();
    public static MultiValueMap<Integer,Integer> Ik =new MultiValueMap<>();
    public static ArrayList<Integer> Matched = new ArrayList<Integer>();
    public static ArrayList<Integer> Matched2 = new ArrayList<Integer>();
    public static Boolean Tracker= false;
    public static int trackersize=0;

    public static void main(String[] args) {
        Helper();

        if(args.length!=0){//            int subKey= Integer.parseInt(args[1]);
            int Plaintext= Integer.parseInt(args[0]);
            int Ciphertext = Integer.parseInt(args[1]);
            int Plaintext2= Integer.parseInt(args[2]);
            int Ciphertext2 = Integer.parseInt(args[3]);
            keys();
            //System.out.println(Arrays.toString(keys_));
            //System.out.println(keys_.length);
            if(Plaintext>4095||Plaintext<0||Ciphertext>4095||Ciphertext<0){
                System.out.println("Please input plaintext first from 0-4095 and then Subkey from 0-255");
            }
            /** Parsing input*/
                String plainText = Integer.toBinaryString(Plaintext);
                String ciphertext= Integer.toBinaryString(Ciphertext);
            String plainText2 = Integer.toBinaryString(Plaintext2);
            String ciphertext2= Integer.toBinaryString(Ciphertext2);
                while(plainText.length()<12){
                    plainText="0"+plainText;
                }
                while(ciphertext.length()<12){
                    ciphertext="0"+ciphertext;
                }
            while(plainText2.length()<12){
                plainText2="0"+plainText2;
            }
            while(ciphertext2.length()<12){
                ciphertext2="0"+ciphertext2;
            }
/** runs the encrypt and decrypt inputs*/
                run(plainText,ciphertext);
            //System.out.println(initialKeys);
          //  System.out.println(initialKeys.size());
            //System.out.println(Ik);
          //  System.out.println(Ik.mappings.size());
            //System.out.println(trackersize);
            trackersize=0;
            Tracker=true;
            initialKeys.clear();
                run(plainText2,ciphertext2);
            //System.out.println(initialKeys);
          //  System.out.println(initialKeys.size());

//                System.out.println(Integer.parseInt(Cipher, 2));

//            }
        }
        else{
            System.out.println("Please input 2 integers like so: java SDES 1365 85");
        }
        /** prints out key arrays that were similar*/
        System.out.println("Common keys:");
        System.out.println("First list");
        System.out.println(Matched);
       // System.out.println(Matched.size());
        System.out.println("Second list");
        System.out.println(Matched2);
      //  System.out.println(Matched2.size());
        /** prints out Keys that are common between the two*/
        System.out.println("Matched Keys: ");
        for(Integer oo :Matched2){
            if(Matched.contains(oo)){
                System.out.println(oo);
            }
        }

    }

    public static void run (String plainText,String ciphertext){
        /** Runs each possible encryption key 0-511 and checks it against the decryption using all possible keys and compares them*/
        for(int ii =0; ii<keys_.length;ii++) {//runs thru each key

            /** creates key schedule, forwards and backwards**/
            String keySchedule[] = new String[4];
            generateSchedule(keys_[ii], keySchedule);
            //System.out.println(Arrays.toString(keySchedule));
//                System.out.println(subKey_);
            //String Cipher = round(plainText, keySchedule[0]);
            // System.out.println("Cypher: "+ Cipher);

            String keyScheduleR[] = new String[4];
            int op = 0;
            for (int i = 3; i > -1; i--) {
                keyScheduleR[op] = keySchedule[i];
                op++;
            }
//                    System.out.println(Arrays.toString(keyScheduleR));

            int L = 0;

            String cipherCyle = plainText;
                String cipherCycle2= ciphertext;

            for (String k : keySchedule) {
                //System.out.println("\n round:"+ L);
                L++;
                cipherCyle = encrypt(cipherCyle, k);
            }
            //System.out.println("Encrypt: ");
            //System.out.println(cipherCyle);
            /** Inserts into all possible encryption keys values in hashtable. Ex: key: Output(encrypted), value: Encrypted Key used(ii)  */
            initialKeys.put(Integer.parseInt(cipherCyle, 2),Integer.parseInt(keys_[ii], 2));
           // trackersize++;
            Ik.putValue(Integer.parseInt(cipherCyle, 2),Integer.parseInt(keys_[ii], 2));
//                    System.out.println(Integer.parseInt(cipherCyle, 2));
//                    System.out.println(Integer.parseInt(keys_[ii], 2));

            L = 0;
            /** Inserts into all possible Decryption keys values in hashtable. Ex: key: Output(decrypted), value: decrypted Key used (ii) */
                for(String k:keyScheduleR){
                   // System.out.println("\n round:"+ L);
                    L++;
                    cipherCycle2= decrypt(cipherCycle2,k);
                }

                if(initialKeys.containsKey(Integer.parseInt(cipherCycle2, 2))&&!Tracker){ /** Checks which run its on. Run being the first pair of plaintext and ciphertext values*/
                    /** Checks if decrytion key matches the encrypted key*/
                    Matched.add(initialKeys.get(Integer.parseInt(cipherCycle2, 2)));
                }
                else if(initialKeys.containsKey(Integer.parseInt(cipherCycle2, 2))&&Tracker) {
                    /** Checks if decrytion key matches the encrypted key 2nd run*/
                    Matched2.add(initialKeys.get(Integer.parseInt(cipherCycle2, 2)));
            }
//                System.out.println(cipherCycle2);
//                System.out.println(Integer.parseInt(cipherCycle2, 2));
        }

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

    public static String encrypt(String cipher, String key){
        return round(cipher,key);
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
   //     System.out.println("left: "+textL);
      //  System.out.println("right: "+textR);
        String OutputF = encryptRound(textR,subkey);
       // System.out.println("Output of f: " +OutputF);
        String XORL = xor(textL,OutputF);
        String XORL2 = xor(OutputF,textL);
    //    System.out.println("XOR with left: " +XORL);
      //  System.out.println(textL+ " "+ XORL);
        return textR+XORL;

// 010101100011
    }
    public static String decrypt(String cipher, String key){
        return round1(cipher,key);
    }
    public static String round1(String plaintext, String subkey){

        String textR="";
        String textL="";
        for(int i = 0; i<plaintext.length();i++){
            if(i>5){
                textR+=plaintext.charAt(i);}
            else
                textL+=plaintext.charAt(i);
        }
    //    System.out.println("left: "+textL);
    //    System.out.println("right: "+textR);
        String OutputF = encryptRound(textL,subkey);
      //  System.out.println("Output of f: " +OutputF);
        String XORL = xor(textR,OutputF);
     //   System.out.println("XOR with left: " +XORL);
     //   System.out.println( XORL+ " "+textL);
        return XORL+textL;

// 010101100011
    }
    public static String encryptRound(String plaintext, String subKey){
      //  System.out.println("input to fxn: "+plaintext+" "+subKey);
        String expandedTR = Expander(plaintext);
        String XORR;
        XORR = xor(expandedTR,subKey);
      //  System.out.println("XORR: "+ XORR);
        String SR1="";
        String SR2="";
        for(int i = 0; i<XORR.length();i++){
            if(i>3){
                SR2+=XORR.charAt(i);}
            else
                SR1+=XORR.charAt(i);
        }
       // System.out.println("Input to sbox 1: " +SR1+ "\n"+ "Input to sbox 2: " + SR2);

        String SR1O;
        String SR2O;
        SR1O = Sbox1(SR1);
        SR2O = Sbox2(SR2);
      //  System.out.println("Output to sbox 1: "+SR1O +"\noutput to sbox 2: " + SR2O);
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
        //System.out.println("Expanded: "+ expanded.toString());
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
    public static String [] keys_ = new String [512];
    public static void keys(){// generates all possible keys.
        for(int i = 0; i<keys_.length;i++){
            String subKey_ = Integer.toBinaryString(i);
            while(subKey_.length()<9){
                subKey_="0"+subKey_;
            }
            keys_[i]=subKey_;
            subKey_="";
        }

    }
    public static class MultiValueMap<K,V>
    {
        private final Map<K, Set<V>> mappings = new HashMap<K,Set<V>>();

        public Set<V> getValues(K key)
        {
            return mappings.get(key);
        }

        public Boolean putValue(K key, V value)
        {
            Set<V> target = mappings.get(key);

            if(target == null)
            {
                target = new HashSet<V>();
                mappings.put(key,target);
            }

            return target.add(value);
        }

    }

}
