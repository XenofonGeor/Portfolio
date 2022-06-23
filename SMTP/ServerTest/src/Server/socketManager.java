package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class socketManager {
	public Socket soc = null;
	public DataInputStream input = null;
	public DataOutputStream output = null;
	
	public socketManager(Socket socket) throws IOException {
		soc = socket;
		input = new DataInputStream(soc.getInputStream());
		output = new DataOutputStream(soc.getOutputStream());
	}
}