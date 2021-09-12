import java.security.MessageDigest;

public class MessageDigestExample {
    
    public static void main(String[] args){
        //MessageDigest example
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            String str = "alpha";
            String str1 = "Male";
            String str2 = "great";

            byte[] b = str.getBytes();        
            sha.update(b);
            byte[] byteArr = ((MessageDigest) sha.clone()).digest();

            byte[] b1 = str1.getBytes();
            sha.update(b1);
            byte[] byteArr1 = ((MessageDigest)sha.clone()).digest();

            byte[] b2 = str2.getBytes();
            sha.update(b2);
            byte[] byteArr2 = ((MessageDigest)sha.clone()).digest();

            StringBuilder sb = new StringBuilder();

            for(byte by: byteArr){
                sb.append(String.format("%02x", by));
            }
            sb.append("\n");
            for(byte by: byteArr1){
                sb.append(String.format("%02x", by));
            }
            sb.append("\n");
            for(byte by: byteArr2){
                sb.append(String.format("%02x", by));
            }

            System.out.println(sb);

        } catch (Exception e) {
            e.printStackTrace();
        }
        



    }



}
