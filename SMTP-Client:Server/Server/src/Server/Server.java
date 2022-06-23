package Server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static String CRLF = "\r\n";
    public static String SP = " ";

    //Main Method:- called when running the class file.
    public static void main(String[] args) {

        //Portnumber:- number of the port we wish to connect on.
        int portNumber = 15882;
        try {
            //Setup the socket for communication 
            ServerSocket serverSoc = new ServerSocket(portNumber);
            ArrayList<socketManager> clients = new ArrayList<socketManager>();

            while (true) {

                //accept incoming communication
                System.out.println("Waiting for client");

                Socket soc = serverSoc.accept();
                socketManager temp = new socketManager(soc);
                clients.add(temp);
                //create a new thread for the connection and start it.
                ServerConnetionHandler sch = new ServerConnetionHandler(clients, temp);
                Thread schThread = new Thread(sch);
                schThread.start();

            }

        } catch (Exception except) {
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error --> " + except.getMessage());
        }
    }
}

class ServerConnetionHandler implements Runnable {

    socketManager sManager = null;
    ArrayList<socketManager> clients = null;

    public ServerConnetionHandler(ArrayList<socketManager> l, socketManager inSoc) {
        sManager = inSoc;
        clients = l;
    }

    public void run() {
        //array list that holds username and password
        ArrayList<String> Login = new ArrayList<String>();
        Login.add("testuser, testpass");

        //array list that holds valid names
        ArrayList<String> ValidDomainNames = new ArrayList<String>();
        ValidDomainNames.add("other@mail.com");
        ValidDomainNames.add("unknown@test.gr");
        ValidDomainNames.add("recipienttest@domain.gr");
        ValidDomainNames.add("mytest@mail.gr");

        ArrayList<String> MsgList = new ArrayList<String>();

        try {
            //Catch the incoming data in a data stream, read a line and output it to the console
            DataOutputStream dataOut = new DataOutputStream(sManager.output);
            DataInputStream dataIn = new DataInputStream(sManager.input);
            
            System.out.println("Client Connected");
            
            dataOut.writeUTF(("220  MyDomain.gr --> Service available\r\n"));
            dataOut.flush();
                        
            while(true){
            //read message from client
               String clientMessage = sManager.input.readUTF();
                //print message from client
            	System.out.println("--> " + clientMessage);
                String MessageToClient="";
                
                String command_from_server = "";                
                String str1="";
		String str2="";
                int usernameShift = 4;
                int passwordShift = 4;
 
                //take the first 4 characters of client message
                command_from_server = clientMessage.substring(0, 4);
                String inputLogin[] = clientMessage.split(" ");

                boolean flag = false;
                if (inputLogin[0].contains("Login")) {
                    //decrypt username
                    char ch1[] = inputLogin[1].toCharArray();
                    char ch3;
                    for (int i = 0; i < inputLogin[1].length(); i++) {
                        if (Character.isLetter(ch1[i])) {
                            if (((int) ch1[i] - usernameShift) < 97) {
                                ch3 = (char) (((int) ch1[i] - usernameShift - 97 + 26) % 26 + 97);
                            } else {
                                ch3 = (char) (((int) ch1[i] - usernameShift - 97) % 26 + 97);
                            }
                            str1 = str1 + ch3;
                        } else if (ch1[i] == ' ') {
                            str1 = str1 + ch1[i];
                        }
                    }
                    //decrypt password
                    char ch2[] = inputLogin[2].toCharArray();
                    char ch4;
                    for (int i = 0; i < inputLogin[2].length(); i++) {
                        if (Character.isLetter(ch2[i])) {
                            if (((int) ch2[i] - passwordShift) < 97) {
                                ch4 = (char) (((int) ch2[i] - passwordShift - 97 + 26) % 26 + 97);

                            } else {
                                ch4 = (char) (((int) ch2[i] - passwordShift - 97) % 26 + 97);
                            }
                            str2 = str2 + ch4;
                        } else if (ch1[i] == ' ') {
                            str2 = str2 + ch2[i];
                        }
                    }
                    for (int i = 0; i < Login.size(); i++) {
                        //checks if username and password are valid
                        String lSplit[] = Login.get(i).split(", ");
                        if (lSplit[0].contentEquals(str1) && lSplit[1].contentEquals(str2) ) {
                          
                            flag = true;
                            dataOut.writeUTF("You are logged in!");
                            dataOut.flush();
                        }
                        else if (!lSplit[0].contentEquals(str1) && !lSplit[1].contentEquals(str2) ) {
                          
                            flag = false;
                            dataOut.writeUTF("Wrong!");
                            dataOut.flush();
                        }
                    }
                    
                } else if (clientMessage.length() > 512) {
                    dataOut.writeUTF("500 -> Line too long");
                    dataOut.flush();

                } else if (command_from_server.length() == 4) {//if it is HELO command
                    if (command_from_server.contains("HELO")) {
                        MessageToClient = "250" + Server.SP + "OK";
                        dataOut.writeUTF(MessageToClient);
                        dataOut.flush();   
                      //if it is MAIL FROM command 
                    } 
                    else if (command_from_server.contains("MAIL")) {
                        String temp = clientMessage.substring(4, 5);
                        if (temp.contains(" ")) {
                            temp = clientMessage.substring(5, 9);
                            
                            if (temp.contains("FROM")) {
                                temp = clientMessage.substring(11, clientMessage.length() - 3);
                                //flag that checks if name exists in array
                                boolean matches_username = false;
                                //Ckeck for valid name
                                for (int i = 0; i < ValidDomainNames.size(); i++) {
                                    if (ValidDomainNames.get(i).matches(temp)) {
                                        //name exists in array
                                        matches_username = true;

                                        MessageToClient = "250" + Server.SP + "OK";
                                        dataOut.writeUTF(MessageToClient);
                                        dataOut.flush();
                                    }
                                }
                            } 
                            else {
                                //if there is not MAIL FROM command
                                MessageToClient = "504 Command parameter not implemented";
                                dataOut.writeUTF(MessageToClient);
                            }
                        }
                    } //if it is RCPT TO command
                    else if (command_from_server.contains("RCPT")) {

                        String temp = clientMessage.substring(4, 5);

                        if (temp.contains(" ")) {
                            temp = clientMessage.substring(5, 7);

                            if (temp.contains("TO")) {
                                temp = clientMessage.substring(9, clientMessage.length() - 3);

                                String name = temp;
                                //String domain_name = temp.substring(nameCheck, clientMessage.length() - 3);
                                boolean matches_name = false;
                                //Ckeck for valid name 
                                for (int i = 0; i < ValidDomainNames.size(); i++) {

                                    if (ValidDomainNames.get(i).matches(temp)) {
                                        //name exists in array
                                        matches_name = true;
                                        dataOut.writeUTF("250" + Server.SP + "OK");
                                        dataOut.flush();
                                    }
                                }
                            } else {
                                //if there is not RCPT TO command
                                MessageToClient = "504 Command parameter not implemented";
                                dataOut.writeUTF(MessageToClient);
                            }
                        }
                    } //if it is DATA command
                    else if (command_from_server.contains("DATA")) {
                        MessageToClient = "354" + Server.SP + "OK";
                        dataOut.writeUTF(MessageToClient);
                        dataOut.flush();
                    } //if mail starts with From message
                    else if (command_from_server.contains("From")) {
                        String str = clientMessage.substring(0, clientMessage.length());
                        if (str.contains(".")) {
                            //remove the last character of client message
                            System.out.print(str.substring(0, str.length() - 1));
                            dataOut.writeUTF("250 DATA OK");
                            dataOut.flush();
                        }
                        MsgList.add(clientMessage);
                        //if it is RETR command
                    } else if (command_from_server.contains("RETR")) {
                        if (!MsgList.isEmpty()) {
                            for (int i = 0; i < MsgList.size(); i++) {
                                MessageToClient ="+OK" + Server.SP + MsgList.get(i);
                                dataOut.writeUTF(MessageToClient);
                            }
                        } else if (MsgList.isEmpty()) {
                            MessageToClient = "There are not any mails!";
                            dataOut.writeUTF(MessageToClient);
                        }
                        //if it is DELE command
                    } else if (command_from_server.contains("DELE")) {
                        for (int i = 0; i < MsgList.size(); i++) {
                            MsgList.clear();
                            MessageToClient = "+OK" +Server.SP + "Mails deleted";
                            dataOut.writeUTF(MessageToClient);
                        }
                        //if it is noop command
                    } else if (command_from_server.contains("NOOP")) {
                        MessageToClient = "250" + Server.SP + "OK";
                        dataOut.writeUTF(MessageToClient);
                        //if it is help command
                    } else if (command_from_server.contains("HELP")) {
                        //takes the argument of help and sends back the appropriate message
                        String temp = clientMessage.substring(5, 9);
                        if (temp.contains("MAIL")) {
                            MessageToClient = "214" + Server.SP + "This command is used to initiate a mail transaction in which\n"
                                    + "the mail data is delivered to one or more mailboxes.";
                            dataOut.writeUTF(MessageToClient);
                            dataOut.flush();
                        } else if (temp.contains("RCPT")) {
                            MessageToClient = "214" + Server.SP + "This command is used to identify an individual recipient of\n"
                                    + "the mail data";
                            dataOut.writeUTF(MessageToClient);
                            dataOut.flush();
                        } else if (temp.contains("DATA")) {
                            MessageToClient = "214" + Server.SP + "The receiver treats the lines following the command as mail\n"
                                    + "data from the sender. If the receiver is ready to accept mail data, sender can send the mail";
                            dataOut.writeUTF(MessageToClient);
                            dataOut.flush();
                        } else if (temp.contains("QUIT")) {
                            MessageToClient = "214" + Server.SP + "This command specifies that the receiver must send an OK\n" +
                            "reply, and then close the transmission channel.";
                            dataOut.writeUTF(MessageToClient);
                            dataOut.flush();
                        }
                    } //if it is QUIT command
                    else if (command_from_server.contains("QUIT")) {
                        MessageToClient = "221 MyDomain.gr -> Service closing transmission channel";
                        dataOut.writeUTF(MessageToClient);
                        dataOut.flush();
                        break;
                    } //if there is not HELO or DATA or QUIT command
                    else if (!command_from_server.contains("HELO") || !command_from_server.contains("DATA") || !command_from_server.contains("From") || !command_from_server.contains("QUIT") || !command_from_server.contains("HELP") || !command_from_server.contains("Logi")) {
                        MessageToClient = "504 command parameter not implemented";
                        dataOut.writeUTF(MessageToClient);
                        dataOut.flush();
                    }
                }
            }
            //close the stream once we are
            sManager.soc.close();

        } catch (Exception except) {
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error in ServerHandler--> " + except.getMessage());
            //421 error when something goes wrong.
            try {
                sManager.output.writeUTF("421 <MyDomain.gr> Service not available,\n"
                        + "closing transmission channel");
            } catch (IOException ex) {
                Logger.getLogger(ServerConnetionHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
