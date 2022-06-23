package chat_application;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
public class Chat_Client {
    
    String address = "localhost";
    String username;
    int port;
    ArrayList<String> users = new ArrayList<String>();
    Socket sock;
    BufferedReader buffReader;
    PrintWriter pWriter;
    Panel frame;
    Boolean connected = false;
    
    Chat_Client(int port, String username, Panel frame)
    {
        this.port = port;
        this.username = username;
        this.frame = frame;
    }
    
    public void ListenThread(){
        Thread IncomingReader = new Thread(new IncomingReader());
        IncomingReader.start();
    }
    
    public void Connect(){
        if (connected == false) {
            try {
                sock = new Socket(address, port);
                InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                buffReader = new BufferedReader(streamreader);
                pWriter = new PrintWriter(sock.getOutputStream());
                frame.broadProcess(username + " connected.\n");
                connected = true; 
            } 
            catch (Exception ex) {
                frame.broadProcess("Unable to connect! Try Again. \n");
            }
            ListenThread();
            
        } else if (connected == true) {
            frame.broadProcess("You are already connected. \n");
        }
    }
    
    public void Disconnect() {
        try {
            sock.close();
            frame.broadProcess(username + " has disconnected. \n");
        } catch(Exception ex) {
            frame.broadProcess("Disconnecting failed. \n");
        }
        connected = false;

    }
        
    public void Send() {
        //Getting time in milliseconds
        long timeMilliseconds = System.currentTimeMillis();
        if (pWriter != null){
            pWriter.println(Long.toString(timeMilliseconds));
            pWriter.flush();
        }
        else {
            frame.broadProcess("Please connect a user first. \n");
        }
    }
    
    public class IncomingReader implements Runnable {
        @Override
        public void run() {
            String stream;
            try {
                while ((stream = buffReader.readLine()) != null) {
                    //This method returns the time in milliseconds
                    long timeMilliseconds = System.currentTimeMillis();
                    long elapsedTime = timeMilliseconds - Long.parseLong(stream);
                    String message = Long.toString(elapsedTime);
                    frame.queue.put(message);
                }
           } catch (Exception ex) {
               frame.broadProcess("Unexpected error \n");
           }
        }
    }
}
