import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    
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

                    
                }


            } catch (Exception e) {
            }
        }

        







    }   
    

}
