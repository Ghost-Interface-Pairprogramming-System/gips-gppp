package as_is_prog.ukagaka;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public class UkagakaSSTPConnection {
	private String sender;
	private SocketAddress address;
	private String charset = "Shift-JIS";

	private final boolean DEBUG = true;
	
	public UkagakaSSTPConnection(String sender) throws UnknownHostException {
		this(sender,"127.0.0.1");
	}
	
	public UkagakaSSTPConnection(String sender,String hostname) throws UnknownHostException {
		this(sender,hostname,9801);
	}
	
	public UkagakaSSTPConnection(String sender, String hostname,int port) throws UnknownHostException {
		this.sender = sender;
		
		this.address = new InetSocketAddress(InetAddress.getByName(hostname), port);
	}
	
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	/***
	 * Notify1.0を送信します。
	 * @param eventName イベントの識別子
	 * @param ref Reference0 to 7
	 * @throws IOException 
	 */
	public String sendNotify1_0(String eventName,String... ref) throws IOException{
		String sendDataStr = "NOTIFY SSTP/1.1\r\n"
						   + "Sender: "+sender+"\r\n"
						   + "Event: "+eventName+"\r\n";
		
		StringBuilder sb = new StringBuilder(sendDataStr);

		
		for(int i = 0;i < ref.length;i++) {
			sb.append("Reference");
			sb.append(i);
			sb.append(": ");
			sb.append(ref[i]);
			sb.append("\r\n");
		}
		sb.append("Charset: "+charset+"\r\n\r\n");
		
		String response = send(sb.toString());
		return response;
	}
	
	private String send(String sendDataStr) throws IOException {
		Socket sc = new Socket();

		sc.connect(address);
		
		if(DEBUG) System.out.println("========SENDSTR========\r\n"+sendDataStr+"\r\n========SENDSTR========");

		BufferedReader br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(sc.getOutputStream()));
		
		bw.write(sendDataStr);
		bw.flush();
		
		String response = br.readLine();
		sc.close();
		return response;
	}

}
