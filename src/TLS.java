import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.TrustManagerFactory;
        
public class TLS {
    
    public static void main(String[] args)throws Exception{

        char[] passphrase = "ahsodks1004".toCharArray();

        KeyStore ksKeys = KeyStore.getInstance("PKCS12");
        ksKeys.load(new FileInputStream("/home/kang/server.jks"), passphrase);
        KeyStore ksTrust = KeyStore.getInstance("PKCS12");
        ksTrust.load(new FileInputStream("/home/kang/trustServer.cer"), passphrase);

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
        kmf.init(ksKeys, passphrase);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
        tmf.init(ksTrust);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        
        SSLEngine engine = sslContext.createSSLEngine();

    }
}
