import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

public class SecurityProvider {
    
    public static void main(String[] args)throws NoSuchAlgorithmException{
        String str = "AlphaMalwewwe";
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(str.getBytes());
        
        StringBuilder sb = new StringBuilder();

        byte[] bytes = md.digest();

        for(byte b : bytes){
            sb.append(String.format("%02x", b));
        }

        System.out.println(sb);

        




    }
}
