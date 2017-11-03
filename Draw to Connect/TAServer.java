//java imports
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
  *Final Project - ISTE 121 
  *Class TAServer to handle the acceptance of the TAClient class for the chat system. 
  *This class creates the server that will interact with the various TAClients.
  *The server is able to connect to multiple clients at once as it is threaded.
  *
  *@author Alex Kohl, Mohammed Suhail, Jason Carmichael, Gregory Ghiroli
  *@version 5/11/2016
  */
  
public class TAServer implements Serializable
{
   //Global Attributes
   private Vector<ObjectOutputStream> printVector = new Vector<ObjectOutputStream>();
   private Socket s;


/**
* The purpose of the main method is to run the TAServer constructor
* @param args the default string array passed in at runtime
*
*/
   public static void main (String [] args)
   {
      new TAServer();
   }//end main
/**   
 * The purpose of TAServer constructor is to continuely accept new clients as long as the server remains active
 * Every time a new client connects the server will create a ServerConnection for the client, and pass that object 
 * the socket the client used to connect to the server.
 * 
*/  
   public TAServer()
   {
      try
      {
         //Create a new serversocket
         ServerSocket ss = new ServerSocket(14789);
         
         while (true)
         {
            //ServerSocket accepts sockets
            s = ss.accept();
            
            //Call the serverConnection Thread
            ServerConnection con = new ServerConnection(s);
            
            //Start the serverConnection thread
            con.start();
            
         }//end while loop
         
      }//end of try 
      
      catch(IOException ioe)
      {
         System.out.println("IO Exception");
         
      }//end of catch IOException
      
   }//end ChatServer constructor
   
   /**
   *The ServerConnection class is a threaded class that handles all the actions for one 
   *connected client. It is created everytime a new user connects to the server.
   */
  
   public class ServerConnection extends Thread
   {
      //Local Attributes
      private Object readObj;
      private String readMsg;
     
     /**
     * The ServerConnection Constructor will accept a new socket and use that for
     * future connection to the client
     *
     * @param _s a socket that will be used to connect to the client in the run method
     */
      
      public ServerConnection(Socket _s)
      {
         s = _s;
      }//end ServerConnection constructor
      
      /**
     *
     * Run will continuously accept new Objects sent from its client, determine what type of object was sent 
     * and then this method will send that object back to all clients connected to this server
     */
     
      
      public void run()
      {
         ObjectOutputStream oos = null;
         
         try
         {
            //Create object inputstream
            InputStream is = s.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            
            //Create object output stream
            OutputStream os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
            
            //Add the object output stream to the vector
            printVector.add(oos);
            
            while (true)
            {
               //Reads the object from the object input stream
               readObj = ois.readObject();
               
               //Check if the object passed is a string for client names
               if (readObj instanceof String)
               {
                  if (readObj == null)
                  {
                     //close the object output stream if no string was found
                     oos.close();
                     return;
                     
                  }//end of if
                  
                  //Loop for all the objects in the printvector 
                  for (ObjectOutputStream output: printVector)
                  {
                     //Reset . write and flush the objects to all the clients in printvector
                     
                     output.reset();
                     output.writeObject(readObj);
                     output.flush();
                     
                  }//end of for object ouyput printvector
                  
               }//end of if object is string
               
               //Check if the object passed is an ImageIcon
               else if (readObj instanceof ImageIcon)
               {
               //Create a new image icon named label from the read object
                  ImageIcon label = (ImageIcon)readObj;
                  
                  //Check if there is usable label
                  if (label == null)
                  {
                     oos.close();
                     return;
                  }//end of if label is null
                
                //Prints the image label to all the clients in 5the printvector  
                  for (ObjectOutputStream output: printVector)
                  {
                  //Reset , write , and flush the object to all the clients
                     output.reset();
                     output.writeObject(label);
                     output.flush();
                     
                  }//end of object output print vector
                  
               }//end of if else statement
               
            }//end of while true
            
         }//end of try run()
         catch(IOException ioe)
         {
            System.out.println("A client has left the session.");
            printVector.remove(oos);
            
         }//end of catch IOExcetion
         catch(ClassNotFoundException cnfe)
         {
            System.out.println("Class not found exception.");
            
         }//end of catch ClassNotFoundException
         
      }//end run method for ServerConnection class
      
   }//end inner class ServerConnection
   
}//end TAServer class