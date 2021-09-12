import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;

import javax.net.ssl.SSLEngineResult;

public class TLS {
    
    public static void main(String[] args)throws Exception{

        char[] passphrase = "alphamale".toCharArray();

        KeyStore ksKeys = KeyStore.getInstance("JKS");
        ksKeys.load(new FileInputStream("testKeys"), passphrase);
    }
}
