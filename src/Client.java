import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

class ClientReader extends Thread{
    Socket clientSocket;

    ClientReader(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream is = clientSocket.getInputStream();
            byte[] byteArray = new byte[256];
            
            while(true){
                int size = is.read(byteArray);
                String readMessage = new String(byteArray, 0, size,"UTF-8");
                System.out.println(">"+ readMessage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

public class Client {
    
    public static void main(String[] args){
        System.out.println("Client 시작");
        
        try {
            
            Socket clientSocket = null;
          
            Scanner sc = new Scanner(System.in);

            clientSocket = new Socket();
            clientSocket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 4040));
            System.out.println("접속 성공");
            //System.out.println("localPort ==> "+clientSocket.getLocalPort());
           
            System.out.println("채팅방에 참여할 이름을 입력하세요 : ");
            String name = sc.nextLine();

            while(true){
                ClientReader clientReader = new ClientReader(clientSocket);
                clientReader.start();

                String sendMessage = sc.nextLine();
                sendMessage = name+" : "+sendMessage;
                byte[] byteArray = sendMessage.getBytes("UTF-8");

                OutputStream os = clientSocket.getOutputStream();
                BufferedOutputStream bos = new BufferedOutputStream(os);

                bos.write(byteArray);
                bos.flush();
                System.out.println("<<나>>" +sendMessage);
                
            }
        } catch (Exception e) {
            e.printStackTrace();        
        }

        System.out.println("Client 종료");
        
    }
    
}
