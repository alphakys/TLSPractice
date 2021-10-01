import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class ClassServer implements Runnable{
	
	private ServerSocket server = null;
	
	protected ClassServer(ServerSocket ss) {
		server = ss;
		newListener();
	}
	
	public abstract byte[] getBytes(String path) 
		throws IOException, FileNotFoundException;
	
	
	public void run() {
		Socket socket;
		
		try {
			socket = server.accept();
		}catch(IOException e) {
			System.out.println("Class Server died: "+ e.getMessage());
			e.printStackTrace();
			return;
		}
		
		try {
			OutputStream rawOut = socket.getOutputStream();
			
			PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(rawOut)));
			
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String path = getPath(in);
				
				byte[] bytecodes = getBytes(path);
				
				try {
					out.print("Content-type: text/html\r\n\r\n");
					out.flush();
					rawOut.write(bytecodes);
					rawOut.flush();
				}catch(IOException e) {
					e.printStackTrace();
					return;
				}
				
			}catch(Exception e) {
				e.printStackTrace();
				
				out.println("HTTP/1.0 400" + e.getMessage() + "\r\n");
				out.println("Content-Type: text/html\r\n\r\n");
				out.flush();
			}
		}catch(IOException ex) {
			System.out.println("error writing response: "+ ex.getMessage());
			ex.printStackTrace();
		}finally {
			try {
				socket.close();
			}catch(IOException e) {
				
			}
		}
		
		
	}
	
	private void newListener() {
		(new Thread(this)).start();
	}
	
	private static String getPath(BufferedReader in)throws IOException 
	{
		String line = in.readLine();
		String path = "";
		
		if(line.startsWith("GET /")) {
			line = line.substring(5, line.length()-1).trim();
			int index = line.indexOf(' ');
			if(index != -1) {
				path = line.substring(0, index);
			}
		}
		
		do {
			line = in.readLine();
		}while((line.length() !=0) && (line.charAt(0) != '\r') && (line.charAt(0)!= '\n'));
		
		if(path.length() != 0) {
			return path;
		}else {
			throw new IOException("Malformed Header");
		}
	}
	


}
