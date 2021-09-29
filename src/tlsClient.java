import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class tlsClient {
	public static void main(String[] args)throws Exception{
		
		 
		try {
			SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
			SSLSocket socket = (SSLSocket)factory.createSocket("www.verisign.com", 443);
			
			socket.startHandshake();
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
			
			out.println("GET / HTTP/1.0");
			out.println();
			out.flush();
			
			if(out.checkError()) {
				System.out.println("SSLSocketClient: java.io.PrintWriter error");
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String inputLine;
			
			while((inputLine = br.readLine())!= null){
				System.out.println(inputLine);
			}
			br.close();
			out.close();
			socket.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
	}
}
