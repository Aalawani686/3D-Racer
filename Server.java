
import java.io.IOException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
 
	private static Socket s;
	private int port = 5454;
	private static InputStream is;
	
	public static void connect() throws IOException{
		ServerSocket ss = new ServerSocket(5454);
		s = ss.accept();
		is = s.getInputStream();
	}
	
	public static void main(String[] args) throws Exception{
		connect();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		String line = null;

		line = br.readLine();
		System.out.println(line);
		br.close();
		
		}
	}
	
	
	
	


