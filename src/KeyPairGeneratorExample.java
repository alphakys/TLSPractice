import java.math.BigInteger;
import java.security.DrbgParameters;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.SecureRandomParameters;
import java.security.Signature;
import java.security.DrbgParameters.Capability;
import java.security.interfaces.DSAParams;
import java.security.spec.DSAGenParameterSpec;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;

import javax.crypto.KeyGenerator;

public class KeyPairGeneratorExample {
    
    public static void main(String[] args){
        
        try {
            
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");

        SecureRandom random = SecureRandom.getInstance("DRBG", "SUN");
        String str = "ALPHA";
        random.setSeed(str.getBytes());
        
        //System.out.println(str.getBytes());
        keygen.initialize(2048, random);
        
        KeyPair pair = keygen.generateKeyPair();
        //System.out.println("privateKey : "+pair.getPrivate()+", publicKey : "+pair.getPublic());
        
        Signature dsa = Signature.getInstance("SHA256withDSA");

        PrivateKey priv = pair.getPrivate();
        dsa.initSign(priv);
        
        dsa.update("Alpha Male is great man".getBytes());
    
        //////////////////////////////////////////////////////////////////////////////////
        
        SecureRandom drbg;
        

        byte[] buffer = new byte[32];

        drbg = SecureRandom.getInstance("DRBG");
        drbg.nextBytes(buffer);
            
        SecureRandomParameters params = drbg.getParameters();
        
        if (params instanceof DrbgParameters.Instantiation) {
            DrbgParameters.Instantiation ins = (DrbgParameters.Instantiation) params;
            if (ins.getCapability().supportsReseeding()) {
                drbg.reseed();
            }
        }

        //drbg = SecureRandom.getInstance("DRBG", DrbgParameters.instantiation(112, NONE, null));

        drbg.nextBytes(buffer, DrbgParameters.nextBytes(256, false, "more".getBytes()));
        drbg.nextBytes(buffer, DrbgParameters.nextBytes(112, true, "more".getBytes()));














        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}