import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class EchoServer extends ClassServer{
    
	
	protected EchoServer(ServerSocket ss) {
		super(ss);
	}

	@Override
	public byte[] getBytes(String path) throws IOException, FileNotFoundException {
		
		return null;
	}

	@Override
	public void run() {
		
		super.run();
	}

	public static void main(String[] args)throws Exception{
    	
		
		
		EchoServer server = new EchoServer(ss);
    }
}
