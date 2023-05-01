import java.math.*;
import java.util.Hashtable;

/**Mario Alvarado
 * answer:1592751770r
 * */
public class BSGS {
    public static Hashtable<BigInteger,BigInteger> bshash  = new Hashtable<>();

    public static void main(String[] args) {
        BigInteger a= new BigInteger("3");
        BigInteger p=new BigInteger("3411390473");
        BigInteger b = new BigInteger("1788512386");
        System.out.println(BSGS_(a,b,p));
    }
    public static BigInteger BSGS_(BigInteger a, BigInteger b,BigInteger p){
        BigInteger N = p.subtract(BigInteger.valueOf(1)).sqrt();
        BigInteger gs;
        BigInteger J;
       // int N = Integer.parseInt(p.sqrt().toString());
        for(int j = 0; j<Integer.parseInt(N.toString());j++){
            bshash.put(a.pow(j).mod(p),BigInteger.valueOf(j));

        }
        System.out.print("KeyCheck:");
        for(int k= 0; k<Integer.parseInt(N.toString());k++ ){

            gs = b.multiply(a.modPow(N.multiply(BigInteger.valueOf(k)),p)).modInverse(p);

            if(bshash.containsKey(gs)){
                System.out.println(true);
                J= bshash.get(gs);
                return J.add(N.multiply(BigInteger.valueOf(k)).mod(p));
            }
        }

     return BigInteger.valueOf(-1);
    }


}
