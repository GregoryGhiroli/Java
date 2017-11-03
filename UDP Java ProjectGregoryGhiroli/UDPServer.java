/* This program is a UDP Server , made by Gregory Ghiroli  DUE: 11/27/2016   */

//Import net 
import java.net.*;
   //Import IO
import java.io.*;
//Import Util 
import java.util.*;


public class UDPServer{


   public static void main(String[] args){
   
   //Global message string for being passed around
       String message = null;

    //Declare the socket port and the packet that will be recieved from the client
      DatagramSocket socket = null;
      DatagramPacket packet = null;
    
    //Declare the server port number
      int SERVER_PORT_NUMBER = 2048;
     
      
           
         
      try{
      
      //Create a server DatagramSocket , number is 2048
         socket = new DatagramSocket(SERVER_PORT_NUMBER);
      
      }//end of try statement for creating serversocket
      
      catch (IOException ioe){
      
      
      //Send an IOException error message
         System.out.println(" IOException, Failed to create a Server Port!, now exiting" + ioe.getMessage());
      
      //Exit the system
         System.exit(0);
      
      }//end of catch IOException
   
       //Create a byte array of 4096 bytes (Reccommmended)
      byte[] carriage = new byte[4096];
      
      //Create byte parameters with the DatagramSocket for recieving messages 
      packet = new DatagramPacket(carriage, carriage.length);
   
      //Signal to the user that the server is indeed running 
      System.out.println("Server is waiting for a response");
   
   
      //Create a while loop to keep the server running forever
      while(true){
      
      
      //Try statement to receive any bytes from the client
         try{
            socket.receive(packet);
                 
         }//end of try statement for packet recieval
             
         catch(IOException ioe)
         {
         
         //Send an IOException error message
            System.out.println(" IOException, Failed to receive message packet!, now exiting" + ioe.getMessage());
         
         //Exit the system
            System.exit(0);
         
         
         
         }//end of IOException
       
       //Convert the packet data to a string for commands, this was provided by the       
         String convert = new String(packet.getData(), packet.getOffset(), packet.getLength());
       
       //Print out the adddress, port and command for the client to visualize
         System.out.println("(" + packet.getAddress() + ":" + packet.getPort() + ") " + convert);
        
       //Convert the command into an integer command for the switch statment  ( Calls convertor method "Convertor")
         int command = Convertor(convert);
         
         
       //  System.out.println(command);
      
         //Check if the command is not null or some other weird thing that'll happen
         
         if( command != 0){
            //switch statements to tell what to do
            switch(command){
               //Turn the message into upper case
               case 1: 
                  
                  message = convert.substring(4).toUpperCase();
                  
                  break;
              //Send the user the date
               case 2: 
                  
                  //Find todays date
                  Date date = new Date();
                  
                  //Send the date to a string
                  message = date.toString();
                  
                  
                  break;
                  
              //Create a file for the user
               case 3: 
                  //Create a new file
                  File file = new File(convert.substring(4));
                     
                  //Create a Buffered Reader
                  BufferedReader br = null;
                   
                  message = "";
               
                  String readStatement = null;
               
                   
                  //Read the file that was sent to the server 
                  try{
                  
                  
                     //Create a new  FileReader from the filename recieve 
                     br = new BufferedReader(new FileReader(file));
                     
                     //System.out.println("I did convert the file");
                     
                     
                     
                     while(true){
                     
                        try{
                        
                           //Read what ever is in the file
                           readStatement = br.readLine();
                        
                        }
                        
                        
                        catch (IOException ioe){
                        
                         //Send an IOException error message
                           System.out.println(" IOException, Failed to read File!, now exiting" + ioe.getMessage());
                        
                        //Exit the system
                           System.exit(0);
                           
                        }
                        
                        if (readStatement == null) {
                           break;
                        }
                        else
                        {
                           message = message + readStatement + "\n";
                        }
                        
                     }//end of while true statement for reading files
                  
                   
                  }//end of try bufferedreader
                  
                  catch (IOException ioe){
                  
                  
                     System.out.println("IOException failed to read the file, now exiting system" + ioe.getMessage());
                     
                     System.exit(0);
                  }//end of catch
               case 4: 
                  message = " ILLEGAL COMMAND ";
            
            }//end of swtich statment
            
            //Calls a datagram method to clean and send the datagram packet for the user to use
            messageSender(socket, packet.getAddress(), packet.getPort(), message);
         
            
         }//end of if statement of comand !=null
         
         
         
      }//end of while true server running  for infinite amount of time
      
      
      
      
      
   }//end of main Method
   
  /*
  
   A send reply method, made to send DatagramPackets to the Socket  via confirming the socket number, internet address , port number, and username
   
   @return packet
  
  */
   private static void messageSender(DatagramSocket _socket, InetAddress _clientIP, int _clientPort, String _clientString)
   {
      //Create a datagram packet to send
      DatagramPacket packet = new DatagramPacket(_clientString.getBytes(), 0, _clientString.length(), _clientIP, _clientPort);
      try{
         _socket.send(packet);
      }
      catch (IOException ioe){
      
      
         System.out.println("IOExcpetion, There was a problem sending the dat to the server... my bad " + ioe.getMessage());
      }
   }
  
    /*
   This method takes the string from the datagram received and converts it into an integer for the switch statement
   
   This method is needed because the description said to create mutiple methods
   
   @return integer 1, 2, 3
  
  */
  
   private static int Convertor(String convert){
   
      if (convert.substring(0, 4).equals("CAP:")) {
         return 1;
      }
      else if (convert.substring(0, 4).equals("DATE")) {
         return 2;
      }
      else if (convert.substring(0, 4).equals("EXE:")) {
         return 3;
      }
      else{
         return 4;
      }
   }
  
  
}//end of UDPServer