import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;



public class newTLS {

    private static final int delay = 1000; // in millis
    private static final String[] protocols = new String[] { "TLS1.3" };
    private static final String[] cipher_suites = new String[] { "TLS_AES_128_GCM_SHA256" };
    private static final String message = "Greatest Man alpha_male_KYS";

    public static void main(String[] args) throws Exception{
    	
    	 System.setProperty("javax.net.ssl.keyStore","clientKeyStore.key");
	    System.setProperty("javax.net.ssl.keyStorePassword","qwerty");
	    System.setProperty("javax.net.ssl.trustStore","clientTrustStore.key");
	    System.setProperty("javax.net.ssl.trustStorePassword","qwerty");
	    
	    
        try(EchoServer server = EchoServer.create()){
            new Thread(server).start();
            Thread.sleep(delay);

            try(SSLSocket socket = createSocket("localhost", server.port())){
                InputStream is = new BufferedInputStream(socket.getInputStream());
                OutputStream os = new BufferedOutputStream(socket.getOutputStream());

                os.write(message.getBytes());
                os.flush();
                byte[] data = new byte[2048];
                int len = is.read(data);

                if(len<=0){
                    throw new IOException("no data received");
                }
                String readMessage = new String(data,0,len);
                System.out.println(readMessage);
            }
        }
    }


    public static SSLSocket createSocket(String host, int port)throws IOException{

        SSLSocket socket = (SSLSocket)SSLSocketFactory.getDefault().createSocket(host, port);
        socket.setEnabledProtocols(protocols);
        socket.setEnabledCipherSuites(cipher_suites);
        
        return socket;
    }




    public static class EchoServer implements Runnable, AutoCloseable{
        
        private static final int FREE_PORT = 0;
        private final SSLServerSocket sslServerSocket;

        private EchoServer(SSLServerSocket sslServerSocket){
            this.sslServerSocket = sslServerSocket;
        }

        public int port(){
            return sslServerSocket.getLocalPort();
        }

        @Override
        public void close() throws Exception {
           if(sslServerSocket !=null && !sslServerSocket.isClosed()){
               sslServerSocket.close();
           }
            
        }
        
        @Override
        public void run() {
            System.out.println("Server started on port"+port());    
            
            try(SSLSocket socket = (SSLSocket)sslServerSocket.accept()) {
                System.out.println("accepted");

                InputStream is = socket.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                OutputStream os = socket.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);

                byte[] data = new byte[2048];
                int len = bis.read(data);

                if(len<=0){
                    throw new IOException("no data received");
                }

                String message = new String(data,0,len);
                System.out.println(message);

                bos.write(data,0,len);
                bos.flush();

            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("server stopped");
        }
       

        public static EchoServer create() throws IOException{
            return create(FREE_PORT);
        }

        public static EchoServer create(int port) throws IOException{
            SSLServerSocket socket = (SSLServerSocket)SSLServerSocketFactory.
            getDefault().createServerSocket(port);

            socket.setEnabledProtocols(protocols);
            socket.setEnabledCipherSuites(cipher_suites);
            return new EchoServer(socket);
        }



    }


}
