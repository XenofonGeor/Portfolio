package Client;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {

    public static String CRLF = "\r\n";
    public static String SP = " ";
    public static String clientDomainName = "cDomain";
    static String forward_path = "recipienttest@domain.gr";
    static String reverse_path = "mytest@mail.gr";

    //Main Method:- called when running the class file.
    public static void main(String[] args) {

        //Portnumber:- number of the port we wish to connect on.
        int portNumber = 15882;
        //ServerIP:- IP address of the server.
        String serverIP = "localhost";

        try {
            //Create a new socket for communication
            Socket soc = new Socket(serverIP, portNumber);

            // create new instance of the client writer thread, intialise it and start it running
            ClientWriter clientWrite = new ClientWriter(soc);
            Thread clientWriteThread = new Thread(clientWrite);
            clientWriteThread.start();
        } catch (Exception except) {
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error --> " + except.getMessage());
        }
    }
}

//This thread is responsible for writing messages
class ClientWriter implements Runnable {

    Socket cwSocket = null;

    public ClientWriter(Socket outputSoc) {
        cwSocket = outputSoc;
    }

    public void run() {
        try {

            DataInputStream dataIn = new DataInputStream(cwSocket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(cwSocket.getOutputStream());
            
            boolean check = true;
            String serverMsg="";
            int input;
            Scanner scan = new Scanner(System.in); 
            serverMsg = dataIn.readUTF();
            
                //username encrypt
                int usernameShift,passwordShift,i,u,p;
		String username;
                String password;
		String str1="";
		String str2="";
                
		System.out.println(serverMsg + "\n\nLOGIN-->\nEnter username:");
		username=scan.nextLine();
		username=username.toLowerCase();
		u=username.length();
		char ch1[]=username.toCharArray();
		char ch3;
		usernameShift= 4;
		
		for(i=0;i<u;i++)
		{
			if(Character.isLetter(ch1[i]))
			{
				ch3=(char)(((int)ch1[i]+usernameShift-97)%26+97);
				//System.out.println(ch1[i]+" = "+ch3);
				str1=str1+ch3;
			}				
			else if(ch1[i]==' ')
			{
				str1=str1+ch1[i];
			}					
		}                
                //password encrypt
                System.out.println("Enter password:");
		password=scan.nextLine();
		password=password.toLowerCase();
		p=password.length();
		char ch2[]=password.toCharArray();
		char ch4;
		passwordShift= 4;
		
		for(i=0;i<p;i++)
		{
			if(Character.isLetter(ch2[i]))
			{
				ch4=(char)(((int)ch2[i]+passwordShift-97)%26+97);
				str2=str2+ch4;
			}				
			else if(ch2[i]==' ')
			{
				str2=str2+ch2[i];
			}					
		}            
            
            dataOut.writeUTF("Login" + " " + str1 + " " + str2);
            dataOut.flush();
            //read responses from server
            serverMsg = dataIn.readUTF();
            
        if(serverMsg.contains("You are logged in!")) { 
        
            while (check)
            {               
                //print a menu
                System.out.print("1)HELO\n2)MAIL FROM\n3)RCPT TO\n4)DATA\n5)DATA ENTRY\n6)GET MAILS\n7)DELETE MAILS\n8)NOOP\n9)HELP\n10)QUIT\n");
                //print server responses
                System.out.print("\nServer response: " + serverMsg + "\n\n");
                System.out.print("Enter Number:");
                // int input 
                input = scan.nextInt(); 

                switch (input) {
                    case 1: {
                        dataOut.writeUTF("HELO" + Client.SP + Client.clientDomainName + Client.CRLF);
                        dataOut.flush();

                        break;
                    }
                    case 2: {
                        dataOut.writeUTF("MAIL" + Client.SP + "FROM:<" + Client.reverse_path + ">" + Client.CRLF);
                        dataOut.flush();

                        break;
                    }
                    case 3: {
                        dataOut.writeUTF("RCPT" + Client.SP + "TO:<" + Client.forward_path + ">" + Client.CRLF);
                        dataOut.flush();
                        break;
                    }
                    case 4: {
                        dataOut.writeUTF("DATA" + Client.CRLF);
                        dataOut.flush();
                        break;
                    }
                    case 5: {
                        if (serverMsg.contains("354 OK")) {
                            System.out.println("Enter your message:\n");
                            String userIn = scan.next();

                            String msgToServer = "From: " + Client.reverse_path;
                            msgToServer = msgToServer + "\nTo: " + Client.forward_path;
                            msgToServer = msgToServer + "\nDate: 12 Dec 18 12:00:00";
                            msgToServer = msgToServer + "\nSubject: Test Mail\n";
                            msgToServer = msgToServer + userIn;
                            dataOut.writeUTF(msgToServer + Client.CRLF + "." + Client.CRLF);
                            dataOut.flush();
                        }
                        break;
                    }
                    case 6: {
                        dataOut.writeUTF("RETR" + Client.CRLF);
                        dataOut.flush();
                        break;
                    }
                    case 7: {
                        dataOut.writeUTF("DELE" + Client.CRLF);
                        dataOut.flush();
                        break;
                    }
                    case 8: {
                        dataOut.writeUTF("NOOP" + Client.CRLF);
                        dataOut.flush();
                        break;
                    }
                    case 9: {
                        System.out.println("HELP --> Enter a command.(MAIL/RCPT/DATA/QUIT)\n");
                        String userIn = scan.next();

                        dataOut.writeUTF("HELP" + Client.SP + userIn + Client.CRLF);
                        dataOut.flush();
                        break;
                    }
                    case 10: {
                        dataOut.writeUTF("QUIT" + Client.CRLF);
                        dataOut.flush();
                        check = false;
                        break;
                    }
                }
                 serverMsg = dataIn.readUTF();
            }     
        }else{
            System.out.print("Wrong username or password!");
            dataOut.close();
        }
            System.out.print("221");
            //close the stream once we are done with it
            dataOut.close();
        } catch (Exception except) {
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error in Writer--> " + except.getMessage());
        }
    }
}
