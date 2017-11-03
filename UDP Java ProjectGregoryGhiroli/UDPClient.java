/* This program is a UDP Client , made by Gregory Ghiroli  DUE: 11/27/2016   */

//Import net 
import java.net.*;
   //Import IO
import java.io.*;
//Import Util 
import java.util.*;

public class UDPClient
{
   public static void main(String[] args){
   
   //Global Variales
      String clientString = null;
     
   //Carrying bye array for Datagram
      byte[] carriage = new byte[4096];
   
      //Create byte parameters with the DatagramSocket for recieving messages 
      DatagramPacket   packet = new DatagramPacket(carriage, carriage.length);
      
      
    //Create an InetAddress for the server IP
      InetAddress serverIP = null;
      
      int SERVER_PORT_NUMBER = 2048;
     
     //Declare the Datagram packets and scokets 
      DatagramPacket send = null;
      DatagramSocket socket = null;
     
     //Create a scanner because we're using arguments and stuff 
      Scanner scanner = new Scanner(System.in);
      
   
   
   //Check if a name has been entered for the server
      if (args.length != 1) {
      
         System.out.println("You should have enter a name for the server, run the arguments please, maybe say localhost?");
         System.out.println("Now leaving");
         System.exit(0);
      }
    
    //Connect to the server ip by the name 
      try {
      
         serverIP = InetAddress.getByName(args[0]);
      }//end of try
      
      catch (UnknownHostException uhe){
         System.out.println("We mcfriggin lost'em " + args[0]);
         System.out.println("ERROR 404 Server not found");
         System.exit(0);
      }//end of catch
    
    //Create a datagramsocket
      try {
         socket = new DatagramSocket();
      }//end of try
      
      
      // 
      catch (IOException ioe)
      {
         System.out.println("CLIENT: creating socket: " + ioe.getMessage());
      }
    
      while (true)
      {
         //Recives the command from the menuScanner method, cause you need to "Modernize the code with methods " 
         int command = menuScreen(scanner);
      
         switch (command)
         {
            case 1: 
               clientString = periodChecker(scanner);
               
               messageSender(socket, serverIP, SERVER_PORT_NUMBER, "CAP:" + clientString);
               
               break;
            case 2: 
              messageSender(socket, serverIP, SERVER_PORT_NUMBER, "DATE");
              
               break;
            case 3: 
               System.out.print(">> ");
               
               clientString = scanner.nextLine();
               
               messageSender(socket, serverIP, SERVER_PORT_NUMBER, "EXE:" + clientString);
               break;
            case 4: 
               System.out.println("Thank you for uses the client to server, now exiting!");
               System.exit(0);
         }//end of swtich
      
         //Recieve the socket packet
         try{
            socket.receive(packet);
         }
         //Incase something goes wrong
         catch (IOException ioe){
         
            System.out.println("Failed to recieve the packet from the socket " + ioe.getMessage());
            System.exit(0);
         }
         
         //Call the print message method to recieve the packet
         printMessage(packet);
      
      
      
      }//end of while loop
   
   
   
   
   }//end of Main Method
    
    
 /*
 
 Create the menu screen via method due to it being used multiple times
 
 
 */   
   private static int menuScreen(Scanner _scanner) {
   
   
      while(true)
      {
         System.out.println("1) Capitalize text");
         System.out.println("2) Date and time");
         System.out.println("3) Retrieve a file");
         System.out.println("4) Exit UDPClient");
         System.out.print("> ");
      
         int command = _scanner.nextInt();
      
      // System.out.println(command);
      
      //Check if the command is between one and 4
         if ((command >= 1) && (command <= 4))
         {
            _scanner.nextLine();
              
            return command;
         }//end f if statement
         else{
         
            System.out.println("All commands must be entered via numbers between 1 and 4\n");
         }//end of else statment
      }//end of whil true
   }//end of menuScreen
      /*
   
   Message sender message to send to the server SERVER sender MUST REPLY BACK
   
   */
   private static void messageSender(DatagramSocket _socket, InetAddress _serverIP, int _serverPort, String _clientString)
   {
      //Create a datagram packet for sending
      DatagramPacket packet = new DatagramPacket(_clientString.getBytes(), 0, _clientString.length(), _serverIP, _serverPort);
      try
      {
         _socket.send(packet);
         
         //System.out.println(packet);
         
      }//end of try
      catch (IOException ioe)
      {
         System.out.println("Something went terrible wrong with sending the packet over to the server, check messageSender method" + ioe.getMessage());
      }//end of catch
   } 
  /* 
    This method checks for periods, uses Scanner  mainly just for thre capitalize command
  */ 
   private static String periodChecker(Scanner _scanner)
   {
      //Local variable string to hold everything
      String isItAPeriod = " ";
    
    //runs forever
      while(true){
      
         System.out.print(">> ");
      
         String period = _scanner.nextLine();
         if (period.equals(".")) {
         
            return isItAPeriod;
         }
         
         isItAPeriod = isItAPeriod+ period + "\n";
      }
   }

    
 //ok why isn't there a response?
//you didn't create a method to print it.....
//ohhhh



/*

Method to print the response

*/   
   private static void printMessage(DatagramPacket _packet)
   {
   //Exactly liek the example
      System.out.println("From: (" + _packet.getAddress() + ":" + _packet.getPort() + ")");
    
    //Recieved message
      String message = new String(_packet.getData(), _packet.getOffset(), _packet.getLength());
    
    //Print
      System.out.println(message);
   }//end of printMessgae
    
    
    
    
    
    
    
}//end of UDP Client