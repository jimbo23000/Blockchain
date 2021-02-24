import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA implements Serializable {
    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private BigInteger privateKey;
    private BigInteger publicKey;
    private BigInteger modulus;

    //Generate an N-bit (roughly) public and private key.
    RSA(int N) {
        BigInteger p = BigInteger.probablePrime(N/2, random);
        BigInteger q = BigInteger.probablePrime(N/2, random);
        BigInteger phi = (p.subtract(one)).multiply(q.subtract(one));

        modulus = p.multiply(q);
        publicKey = greatestCommonDivisor(phi);
        privateKey = publicKey.modInverse(phi);
    }

    BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }

    private BigInteger greatestCommonDivisor(BigInteger phi) {
        int random = (int)(Math.random()*Character.MAX_VALUE);
        BigInteger e = new BigInteger( "" + random);
        while(!(e.gcd(phi).equals(one))) {
            random = (int)(Math.random()*Character.MAX_VALUE);
            e = new BigInteger("" + random);
        }
        return e;
    }

    public String toString() {
        String s = "";
        s += "public  = " + publicKey  + "\n";
        s += "private = " + privateKey + "\n";
        s += "modulus = " + modulus;
        return s;
    }

    //Figure out indexOutOfBounds problem before attempting to rerun the class.
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        RSA key = new RSA(N);
        System.out.println(key);

        //Create random message, encrypt and decrypt.
        BigInteger message = new BigInteger(N-1, random);

        //Create message by converting string to integer.
        //String s = "test";
        //byte[] bytes = s.getBytes();
        //BigInteger message = new BigInteger(bytes);

        BigInteger encrypt = key.encrypt(message);
        BigInteger decrypt = key.decrypt(encrypt);
        System.out.println("message   = " + message);
        System.out.println("encrypted = " + encrypt);
        System.out.println("decrypted = " + decrypt);
    }
}
