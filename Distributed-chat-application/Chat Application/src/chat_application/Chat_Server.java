package chat_application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Chat_Server {
    
    ArrayList<PrintWriter> printOutStreams;
    ArrayList<String> users;
    ArrayList<ServerSocket> sockets = new ArrayList<ServerSocket>();
    Panel frame;
    int port;
    
    Chat_Server(int aPort, Panel JFrame){
        this.port = aPort;
        this.frame = JFrame;
    }

    public class ClientHandler implements Runnable{
        BufferedReader buffReader;
        Socket socket;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter user){
            client = user;
            try {
                socket = clientSocket;
                InputStreamReader inReader = new InputStreamReader(socket.getInputStream());
                buffReader = new BufferedReader(inReader);
            }
            catch (Exception ex){
                frame.broadProcess("Exception error. \n");
            }
        }

        @Override
        public void run(){
            String message;
            try {
                while ((message = buffReader.readLine()) != null){
                    SendMessage(message);
                } 
            } 
            catch (Exception ex) {
                frame.broadProcess("Connection is lost. \n");
                ex.printStackTrace();
                printOutStreams.remove(client);
            } 
        }
    }
    
    public class ServerStart implements Runnable {
        int inputPort;
        ServerStart(int port){
            inputPort = port;
        }
        
        @Override
        public void run() 
        {
            printOutStreams = new ArrayList<PrintWriter>();
            users = new ArrayList<String>(); 
            
            try {
                ServerSocket serverSoc = new ServerSocket(inputPort);
                sockets.add(serverSoc);
                
                frame.broadProcess("Server started. \n");

                while (true) {
                    Socket clientSoc = serverSoc.accept();
                    PrintWriter writer = new PrintWriter(clientSoc.getOutputStream());
                    printOutStreams.add(writer);

                    Thread tListener = new Thread(new ClientHandler(clientSoc, writer));
                    tListener.start();
                    frame.broadProcess("Connected. \n");
                }
            }
            catch (Exception ex) {
                frame.broadProcess("Error with a connection. \n");
            }
        }
    }
    
    public void StartServer(){
        Thread tStarter = new Thread(new ServerStart(port));
        tStarter.start();
    }
    
    public void StopServer(){
        try {
            //five second delay
            Thread.sleep(5000);
            for (int i = 0; i < sockets.size(); i++) {
                try {
                    sockets.get(i).close();
                    System.out.println("Socket" + Integer.toString(i) + "closed");
                } catch (IOException ex) {
                    Logger.getLogger(Chat_Server.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        } 
        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
        SendMessage("Server stops and all users will be disconnected.\n");
        frame.broadProcess("Server is stopping... \n");
    }
    
    public void SendMessage(String message){
        Iterator<PrintWriter> iterator = printOutStreams.iterator();

        while (iterator.hasNext()) {
            try {
                PrintWriter writer = iterator.next();
                writer.println(message);
                writer.flush();
            } 
            catch (Exception ex) {
                frame.broadProcess("Error broadcasting message to clients. \n");
            }
        }
    }
}
