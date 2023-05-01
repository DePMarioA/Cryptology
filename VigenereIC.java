import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class VigenereIC {
    public static int l= 14;
    static int trackerShift=0;
    static int trackerCounter=0;
    public static void main(String[] args) {
        String ciphertext = "GCMKGKRDBYTZLRORMTZLROGFEXIKCORSNSDZHFAZWLNCLIZIMZFWOTIPJIJOBIVQSPYURVTCSKTSNZJCRESEZMMUKZVMVCA" +
                "XHFASTEXCYPAYNHIZIUUHUIMZFUAYPZQSBOAXKCSGRRMMZGRHKEXBFCGGXVJTMUXNFTOLDYYWASPITKKCCSSGRUNCDCURWDRCNZVVGWEIURJ" +
                "DRCORSXDSQATHVXCLOSMTYCATXMEZGCVKVPCILTKVRIRDOXEXZFCVKVPCSPOGRUXCUAXHVQSPYIVVVHMRGRUYSQTXSPZFMFIMMDZGZGXZJBC" +
                "VKVPFWLGGRUKSYSGRKZJCRECFPBECUYGGSGNRSMZSTEXCDJHFEXEEYTYTNIICCNELYCXVGLJMEQSLTUVRIRCXVPFMSPEBIITHCAILVMCDMUV" +
                "RGGCVKVPXCPRATKKCJIZMTDOLEBIITGSPKVJOOPEBIITGSPXIDZZCAJIIZJCREWRDBRATHJDBLEXMEOVCHOWKJFWOLSLMGNEIMVNZGVKHKCS" +
                "PEURRHCREUJUPGRSAWGZBBEJMEVGSNHIRHHFEKEIOVGSGZVMMQMGPCNHYGKMEVJYSZGFNAGCGVVIORHORBJTRHKVZQSPSUJSGCMDYTZGZCDH" +
                "CRGZRHUWVBSLEXECNOLDKQGZFMRYWFOVYTORXGCPYGRUOFGUSTYOVCYISLGRZEISDZHFESSDZBRAXCDVGREXWFAODRGGKDCLOLEUJHRHORBJ" +
                "TRHKIEYZCSYGIPSJTOIJQWQIZIUWMRHKMECOZIZEEOGMFURVXCPNKVFAHFIYTZSSJOTXYZGAAXGVGMBIYXZIUSIYLRWZCITLRWWRATXJJTQO" +
                "SIFOVCRISIISPHUAWMSOUKRKOVCIXQZNILDKVJOOLDORXNVMWKEXZFRHKCRMSROQMCGCLEGRFOVCRNSNASPVKRKOVCIXLROFCDY";

        possibleKeyLength(ciphertext);
        System.out.println("Length:"+trackerShift);
        System.out.println("Key:"+possibleKey(ciphertext,7));
    }

    public static String possibleKey(String cypherText, int keyLength){
        double[] EnglishFrequencies = {0.082, 0.015, 0.028, 0.043, 0.127, 0.022, 0.020, 0.061, 0.070, 0.002, 0.008, 0.040, 0.024,
                0.067, 0.075, 0.019, 0.001, 0.060, 0.063, 0.091, 0.028, 0.010, 0.023, 0.001, 0.020, 0.001};
        StringBuilder key = new StringBuilder();
        double[] A2 =EnglishFrequencies;

        for(int i = 0;i<keyLength;i++){
            ArrayList<Character> let = new ArrayList<Character>();
            for(int j = 0;j<cypherText.length();j++){
                if(j%keyLength==i){
                    let.add(cypherText.charAt(j));
                }
            }
            // System.out.println(let);
            int[] temp = new int[26];
            // ArrayList temp2 = new ArrayList(26);
//            System.out.println(Arrays.toString(temp));
            for (Character character : let) {
                temp[(int) character - (int)'A']+=1;
//               System.out.println(temp[(int) character - (int)'A']);
            }


            float[] W = new float[26];
            for (int j =0; j<temp.length;j++){
                W[j] = (float)temp[j]/let.size();
            }
            // System.out.println(Arrays.toString(W));
            ArrayList<Double> sum = new ArrayList<Double>(26);
            intialized(sum);
            for (int j =0; j<26;j++){
                //System.out.println(Arrays.toString(A2));

                double total=0;
                for(int k =0;k<W.length;k++){
                    total+=W[k]*A2[k];
                }
                sum.set(j,total);
                A2=shiftArray(A2);
            }

            // System.out.println(sum);
            int index = sum.indexOf(Collections.max(sum));
            //System.out.println(index);
            // System.out.println(index);
            char letter = (char)((int)'A'+index);
            key.append(letter);
        }

        return key.toString();

    }
    public static void intialized (ArrayList<Double> x ){
        for(int i=0;i<26;i++){
            x.add(0.0);
        }

    }

    public static double[] shiftArray(double[] array){
        double[] array2 = new double[array.length];
        for(int i =0 ; i<array.length-1;i++){
            array2[i+1]=array[i];
        }
        array2[0]=array[array.length-1];
        return array2;
    }
    public static void possibleKeyLength(String cyphertext){
        for(int shift =1 ;shift<=14 ; shift++ ){
            int counter = 0;
            for(int index = 0; index<cyphertext.length();index++){
                int shiftindex = (index+shift)%cyphertext.length();
                if(cyphertext.charAt(index)==cyphertext.charAt(shiftindex)){
                    counter+=1;
                }
            }
            if(trackerCounter<counter){
                trackerCounter=counter;
                trackerShift=shift;
            }
        }

    }
}