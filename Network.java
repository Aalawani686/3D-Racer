import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Network implements Runnable {


	final String client;
	final boolean isClient;
	String ip = "localhost";
	int port = 5454;
	
	Socket s;
	
	PrintWriter pw;
	BufferedReader br;

	public Network(String client) throws IOException{
		this.client = client;
		if(client.equals("client")) isClient = true;
		else isClient = false;
		
		if(!isClient){
			ServerSocket ss = new ServerSocket(port);
			s = ss.accept();
		}
		else{
			s = new Socket(ip, port);
		}
		
		pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		
	}	
	
	public synchronized void send(String message){
		pw.println(message);
		pw.flush();
	}
	
	public void receive(){
		String msg = null;
		while(true){
			try {
				msg = br.readLine();
				if(msg != null){
					System.out.println(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		receive();
	}


}





