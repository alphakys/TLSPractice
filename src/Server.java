import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
  
    class UserInfo{
        Socket clientSocket;

        UserInfo(Socket clientSocket){
            this.clientSocket = clientSocket;
            //System.out.println(clientSocket.getPort());
        }
    }

    class UserThread extends Thread{
        Socket clientSocket;
        List<UserInfo> li = new ArrayList<UserInfo>();

        UserThread(Socket clientSocket, List<UserInfo> li){
            this.clientSocket = clientSocket;
            this.li = li;
        }

        @Override
        public void run() {
            
            try {
                
                while(true){

                    InputStream is = clientSocket.getInputStream();
                    byte[] byteArray = new byte[256];
                    int size = is.read(byteArray);

                    if(size==-1){
                        break;
                    }

                    String message = new String(byteArray,0,size,"UTF-8");
                    System.out.println("SERVER : "+message);
                    
                    //여러 클라이언트들에게 메세지를 보내기 위한 루프
                    for(int i=0; i<li.size(); i++){
                        if(li.get(i).clientSocket !=clientSocket){
                            OutputStream os = li.get(i).clientSocket.getOutputStream();
                            os.write(byteArray);
                        }
                    }
                }


            } catch (Exception e) {
                System.out.println("Client 접속종료");
                for(int i=0; i<li.size();){
                    if(clientSocket == li.get(i).clientSocket){
                        li.remove(i);
                    }else{
                        i++;
                    }
                }
            }
        }

    }   
    
    class ConnectThread extends Thread{
        ServerSocket mainServerSocket = null;
        List<UserInfo> li = new ArrayList<>();

        ConnectThread(ServerSocket mainServerSocket){
            this.mainServerSocket = mainServerSocket;
        }

        @Override
        public void run() {
            try {
                while(true){
                    Socket clientSocket = mainServerSocket.accept();
                    System.out.println("Client 접속");
                    System.out.println("Port ==> "+clientSocket.getPort());
                    System.out.println("===================================================");
                    li.add(new UserInfo(clientSocket));
                    UserThread userThread = new UserThread(clientSocket, li);
                    userThread.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    
        }
    }

public class Server {
    public static void main(String args[]){
        System.out.println("Server start");

        try {
            ServerSocket mainServerSocket = null;
            mainServerSocket = new ServerSocket();
            mainServerSocket.bind(new InetSocketAddress(InetAddress.getLocalHost(),4040));

            ConnectThread connectThread = new ConnectThread(mainServerSocket);
            connectThread.start();

            Scanner sc = new Scanner(System.in);
            int temp = sc.nextInt();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Server end");


    }

}
