import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


public class Server implements Runnable {


	final String client;
	final boolean isClient;
	String ip = "192.168.1.75";
	int port = 5454;
	
	ArrayList<PrintWriter> PW;
	ArrayList<BufferedReader> BR;
	
	ServerSocket ss;
	Socket s;
	
	PrintWriter pw;
	BufferedReader br;
	
	
	
	class ServerThread extends Thread{

		Socket s;
		BufferedReader br1;
		public ServerThread(Socket s1){
			s = s1;
			try {
				br1 = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		public void run() {
			String msg = null;
			while(true){
				try {
					msg = br1.readLine();
					if(msg != null){
						if(msg.charAt(0) == 'p'){
							System.out.println("forward: " + msg.substring(1));
						}
						else if(msg.charAt(0) == 'l'){
							System.out.println("lateral: " + msg.substring(1));
						}
					}
					for(int i=0; i<PW.size(); i++){
						PW.get(i).println(msg);
						PW.get(i).flush();
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					if(s == null){
						try {
							s.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}


	public Server(String client) throws IOException{
		this.client = client;
		if(client.equals("client")) isClient = true;
		else isClient = false;
		
		if(!isClient){
			PW = new ArrayList<PrintWriter>();
			BR = new ArrayList<BufferedReader>();
			ss = new ServerSocket(port);
			
		}
		else{
			s = new Socket(ip, port);
			pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}
	}	
	
	public synchronized void send(String message){
		if(isClient){
			pw.println(message);
			pw.flush();
		}
		else{
			for(int i=0; i<PW.size(); i++){
				PW.get(i).println(message);
				PW.get(i).flush();
			}
		}
	}
	
	public void receive(){
		String msg = null;
		while(true){
			try {
				msg = br.readLine();
				if(msg != null){
					if(msg.charAt(0) == 'p'){
						System.out.println("forward: " + msg.substring(1));
					}
					else if(msg.charAt(0) == 'l'){
						System.out.println("lateral: " + msg.substring(1));
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				if(s == null){
					try {
						s.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void run() {
		if(isClient){
			receive();
		}
		else{
			try{
				s = ss.accept();
				pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PW.add(pw);
				BR.add(br);
				Thread t = new Thread(new ServerThread(s));
				t.start();
			} catch (Exception e){
				e.printStackTrace();
			}
			
		}
	}
	


}




















