import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLSocketClientWithAuth {

	public static void main(String[] args)throws Exception {
		
		
		String host;
		int port = 2004;
		String path = null;
		/*
		for(int i=0; i<args.length; i++) {
			System.out.println(args[i]);
		}
		
		if(args.length <3) {
			System.out.println("USAGE: java SSLSocketClientwithAuth "+
								   "host port requestedfilepath");
			System.exit(-1);
		}
		*/
		/*
		try {
			host = args[0];
			port = Integer.parseInt(args[1]);
			path = args[2];
		}catch(IllegalArgumentException e) {
			System.out.println("USAGE: java SSLSocketClientWithClientAuth " +
	                 "host port requestedfilepath");
	             System.exit(-1);
		}
		*/
		
		try {
			SSLSocketFactory factory = null;
			
			try {
				SSLContext ctx;
				KeyManagerFactory kmf;
				KeyStore ks;
				char[] passphrase = "passphrase".toCharArray();
				
				ctx = SSLContext.getInstance("TLS");
				kmf = KeyManagerFactory.getInstance("Sunx509");
				ks = KeyStore.getInstance("JKS");
				
				ks.load(new FileInputStream("/root/다운로드/keystore"), passphrase);
				
				kmf.init(ks, passphrase);
				ctx.init(kmf.getKeyManagers(), null, null);
				
				factory = ctx.getSocketFactory();
			}catch (Exception e) {
				throw new IOException(e.getMessage());
			}
			System.out.println("TLS connecting start");
			SSLSocket socket = (SSLSocket)factory.createSocket("127.0.0.1", 4040);
			socket.startHandshake();
			//System.setProperties("javax.net.debug=all");
				//factory.createSocket(null, port)
			PrintWriter out = new PrintWriter(new BufferedWriter
													 (new OutputStreamWriter(socket.getOutputStream())));
			OutputStream os = socket.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			
			byte[] bytearr = new byte[256];
			
			String s = "TLS 시작";
			bos.write(s.getBytes("UTF-8"));
			//out.println("GET " + path + "HTTP/1.0");
			//out.println();
			//out.flush();
			
			if(out.checkError())
				System.out.println("SSLSocketClient: java.io.PrintWriter error");
			
			BufferedReader in = new BufferedReader(new InputStreamReader
															(socket.getInputStream()));
			
			String inputline;
			
			while((inputline = in.readLine())!=null) {
				System.out.println(inputline);
			}
			
			in.close();
			out.close();
			socket.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	

		
		
		
		
		
	
	

}
