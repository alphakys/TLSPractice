import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;

public class TLS extends ClassServer{
    
	
	
    protected TLS(ServerSocket ss) {
		super(ss);
		// TODO Auto-generated constructor stub
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
    	
    }
}
