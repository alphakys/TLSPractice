import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
  
    class UserInfo{
        Socket serverSocket;

        UserInfo(Socket serverSocket){
            this.serverSocket = serverSocket;
        }
    }

    class UserThread extends Thread{
        Socket serverSocket;
        List<UserInfo> li = new ArrayList<UserInfo>();

        UserThread(Socket serverSocket, List li){
            this.serverSocket = serverSocket;
            this.li = li;
        }

        @Override
        public void run() {
            
            try {
                
                while(true){

                    InputStream is = serverSocket.getInputStream();
                    byte[] byteArray = new byte[256];
                    int size = is.read(byteArray);

                    if(size==-1){
                        break;
                    }

                    String message = new String(byteArray,0,size,"UTF-8");
                    System.out.println(message);

                    for(int i=0; i<li.size(); i++){
                        if(li.get(i).serverSocket !=serverSocket){
                            OutputStream os = li.get(i).serverSocket.getOutputStream();
                            os.write(byteArray);
                        }
                    }
                }


            } catch (Exception e) {
                System.out.println("Client 접속종료");
                for(int i=0; i<li.size();){
                    if(serverSocket == li.get(i).serverSocket){
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
                    Socket serverSocket = mainServerSocket.accept();
                    System.out.println("Client 접속");

                    li.add(new UserInfo(serverSocket));
                    UserThread userThread = new UserThread(serverSocket, li));
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
        } catch (Exception e) {
            
        }



    }

}
