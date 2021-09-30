import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class SSLSocketClientWithAuth {

	public static void main(String[] args)throws Exception {
		
		String host = null;
		int port =-1;
		String path = null;
		
		for(int i=0; i<args.length; i++) {
			System.out.println(args[i]);
		}
		
		if(args.length <3) {
			System.out.println("USAGE: java SSLSocketClientwithAuth host port requestedfilepath");
			System.exit(-1);
		}
		
		
		try {
			host = args[0];
			port = Integer.parseInt(args[1]);
			path = args[2];
		}catch(IllegalArgumentException e) {
			System.out.println("USAGE: java SSLSocketClientWithClientAuth " +
	                 "host port requestedfilepath");
	             System.exit(-1);
		}
		
		try {
			SSLSocketFactory factory = null;
			
			try {
				SSLContext ctx;
				KeyManagerFactory kmf;
				KeyStore ks;
				
				char[] passphrase = "passphrase".toCharArray();
				
				ctx = SSLContext.getInstance("TLS");
				kmf = KeyManagerFactory.getInstance("SunX509");
				ks = KeyStore.getInstance("JKS");
				
				ks.load(new FileInputStream("testkeys"), passphrase);
				kmf.init(ks, passphrase);
				ctx.init(kmf.getKeyManagers(), null, null);
				
				
			}catch (Exception e) {
				throw new IOException(e.getMessage());
			}
		}catch (Exception e) {
			
		}
		
	}
		
	

		
		
		
		
		
	
	

}
