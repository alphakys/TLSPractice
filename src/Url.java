import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Url {

	public static void main(String[] args)throws Exception {
		URL url = new URL("http://www.basics.re.kr/main.do");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		while(br.readLine()!=null) {
			System.out.println(br.readLine());
		}
	}	

}
