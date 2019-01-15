
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private static String IPAddress = "localhost";
	private static int portNumber = 5454;
	private static Socket s;
	private static OutputStream os;

	public static void connect() throws Exception{
		s = new Socket(IPAddress, portNumber);
		os = s.getOutputStream();
	}
	
	public static void main(String[] args) throws Exception {
		connect();
		
		OutputStreamWriter osw = new OutputStreamWriter(os);
		PrintWriter pw = new PrintWriter(osw);
		
		pw.println("It's a unicorn");
		pw.flush();
		pw.close();
	}

}


