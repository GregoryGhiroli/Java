import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**Class TAClient to act as the client that connects to the server TAServer class. TAClient consists of a GUI to be used by the user to communicate
   with others on code, send and receive images, and receive help from others. Incorporated in the GUI for TAClient is both a code tab to send and receive blocks
   of text or code, a whiteboard tab to send a receive drawn images, and a chat implementation to communicate with other members in the session.
  *
  *Final Project - ISTE 121
  *@author Alex Kohl, Mohammed Suhail, Jason Carmichael, Gregory Ghiroli
  *@version 4/18/2016
  *
  *
  */
  
public class TAClient extends JFrame implements ActionListener, Serializable
{
   // connection area
   private JButton send, connect;                     //buttons for the connection area
   private JTextArea chatBox;
   private JTextField sendBox, connectInfo, name;     //test fields for the connection area          
   
   // streams used for io
   private PrintWriter pw;
   private BufferedReader br;
   private ObjectOutputStream oos;
   private ObjectInputStream ois;
   private String readMsg;
   
   // code sending area
   private JPanel jpCodePanel;                       //mother panel
   private JPanel jpCodeButtons;                     //contains the buttons for sending the code
   private JTextArea jtaCodeSend, jtaCodeReceive;
   private JButton jbCodeSend, jbCodeClear;
   
   // menus and tabbed panes
   private JTabbedPane tabbedPane;
   private JMenuItem jmiClose, jmiAbout, jmiHelp;
   
   // whiteboard area
   JPanel jpWhiteBoardNorth = new JPanel(new BorderLayout()); 
   JPanel jpWhiteBoardSouth = new JPanel(new BorderLayout());
   JPanel jpWbSouthCenter = new JPanel();
   private JButton wbBlack, wbRed, wbGreen, wbBlue,   //buttons used for controlling the pen on the whiteboard
                   wbYellow, erase, fiveB, tenB, oneB; 
   private JButton wbClear, wbSend, wbNext, wbPrev;;
   private WhiteBoard wbNorth;
   private int saveCounter = 0;  //used to keep track of the whiteboard object the user is viewing
   private ImageIcon loadImage;
   private JLabel imageLabel;
   private BufferedImage image;
   JLabel picLabel;
   ArrayList<ImageIcon> images = new ArrayList<ImageIcon>();
   ArrayList<JLabel> imageList = new ArrayList<JLabel>();
   
   // IO
   private Socket s;
   private Object readObj;
   private Object sendObj;
   private JLabel newNoti;
  
   /**
     *The main method of TAClient instantiates an instance of the TAClient object.
     *@param args - arguments passed in at run-time.
     */
   public static void main(String [] args)
   {
      new TAClient();
   }//end main
   
   /**
   *  Constructor
   */
   public TAClient()
   {
      setTitle("Homework 07 Client/Server Chat");
      JPanel jpMain = new JPanel(new BorderLayout());
      JPanel northConnect = new JPanel(new BorderLayout());
      JPanel north1 = new JPanel(new FlowLayout());
      JPanel north2 = new JPanel(new FlowLayout());
      JPanel north3 = new JPanel(new FlowLayout());
      JPanel centerReceive = new JPanel(new FlowLayout());
      JPanel southSend = new JPanel(new FlowLayout());  
      JPanel jpWhiteBoardMain = new JPanel(new GridLayout(0,1));
      JPanel wbTools = new JPanel(new GridLayout(9,0));
      JPanel wbButtons = new JPanel(new FlowLayout());      
      
      //Create Whiteboard and Buttons
      wbNorth = new WhiteBoard();
      
      //Create WhiteBoard Buttons
      wbClear = new JButton("Clear");
      wbBlack = new JButton("Black");
      wbGreen = new JButton("Green");
      wbRed = new JButton("Red");
      wbYellow = new JButton("Yellow");
      wbBlue = new JButton("Blue");
      wbPrev = new JButton("< Prev");
      wbNext = new JButton("Next >");
      wbClear = new JButton("Clear");
      wbSend = new JButton("Send");
      newNoti = new JLabel("New");
      erase = new JButton("Eraser");
      fiveB = new JButton(" 5 point Width");
      tenB = new JButton(" 10 point Width");
      oneB = new JButton(" 1 point Width");
      newNoti.setForeground(Color.red);
      
      //Add whiteboard button action listeners
      wbBlack.addActionListener(this);
      wbRed.addActionListener(this);
      wbBlue.addActionListener(this);
      wbYellow.addActionListener(this);
      wbGreen.addActionListener(this);
      wbClear.addActionListener(this);
      wbPrev.addActionListener(this);
      wbNext.addActionListener(this);
      wbSend.addActionListener(this);
      erase.addActionListener(this);
      fiveB.addActionListener(this);
      tenB.addActionListener(this);   
      oneB.addActionListener(this);   
      
      //JMenu Bar
      JMenuBar jmb = new JMenuBar();
      JMenu jmFile = new JMenu("File");
      JMenu jmInfo = new JMenu("Info");
      jmiClose = new JMenuItem("Close");
      jmiAbout = new JMenuItem("About");
      jmiHelp = new JMenuItem("Help");
      jmiClose.addActionListener(this);
      jmiAbout.addActionListener(this);
      jmiHelp.addActionListener(this);
      jmFile.add(jmiClose);
      jmInfo.add(jmiAbout);
      jmInfo.add(jmiHelp);
      jmb.add(jmFile);
      jmb.add(jmInfo);
      
      //chat pane
      send = new JButton("Send");
      connect = new JButton("Connect");
      chatBox = new JTextArea(35, 30);
      JScrollPane chatScroll = new JScrollPane(chatBox);
      sendBox = new JTextField(20);
      connectInfo = new JTextField(20);
      connectInfo.setText("localhost");
      name = new JTextField(20);
      
      //code panes
      jpCodePanel = new JPanel(new BorderLayout());
      jtaCodeSend = new JTextArea(20,40);
      jtaCodeReceive = new JTextArea(20,40);
      JScrollPane sendScroll = new JScrollPane(jtaCodeSend);
      JScrollPane receiveScroll = new JScrollPane(jtaCodeReceive);
      jpCodePanel.add(sendScroll, BorderLayout.NORTH);
      jpCodePanel.add(receiveScroll, BorderLayout.CENTER);
      
      //send and receive buttons
      jpCodeButtons = new JPanel(new FlowLayout());
      jbCodeSend = new JButton("Send");
      jbCodeClear = new JButton("Clear");
      jbCodeSend.addActionListener(this);
      jbCodeClear.addActionListener(this);
      jpCodeButtons.add(jbCodeSend);
      jpCodeButtons.add(jbCodeClear);
      jpCodePanel.add(jpCodeButtons, BorderLayout.SOUTH);
   
      //sets the code receive buttons to unusable until the user connects
      chatBox.setEditable(false);
      send.setEnabled(false);
      sendBox.setEditable(false);
      send.addActionListener(this);
      connect.addActionListener(this);
      jtaCodeSend.setEditable(false);
      jtaCodeReceive.setEditable(false);
      jbCodeSend.setEnabled(false);
      jbCodeClear.setEnabled(false);
      
      //Connection Pane
      north1.add(new JLabel("IP Address: ", JLabel.RIGHT));
      north1.add(connectInfo);
      northConnect.add(north1, BorderLayout.NORTH);
      north2.add(new JLabel("Username: ", JLabel.RIGHT));
      north2.add(name);
      northConnect.add(north2, BorderLayout.CENTER);
      north3.add(connect);
      northConnect.add(north3, BorderLayout.SOUTH);
      
      centerReceive.add(chatScroll);
      southSend.add(sendBox);
      southSend.add(send);
      
      //Add stuff to the WhiteBoard
      wbTools.add(wbBlack);
      wbTools.add(wbRed);
      wbTools.add(wbGreen);
      wbTools.add(wbBlue);
      wbTools.add(wbYellow);
      wbTools.add(erase);
      wbTools.add(oneB);
      wbTools.add(fiveB);
      wbTools.add(tenB);
      
      wbButtons.add(wbSend);
      wbButtons.add(wbClear);
      wbButtons.add(wbPrev);
      wbButtons.add(wbNext);
      wbButtons.add(newNoti);
      
      jpWhiteBoardNorth.setBorder(BorderFactory.createLineBorder(Color.black));
      jpWhiteBoardSouth.setBorder(BorderFactory.createLineBorder(Color.black));
      
      jpWhiteBoardNorth.add(wbNorth, BorderLayout.CENTER);
      jpWhiteBoardMain.add(jpWhiteBoardNorth, BorderLayout.NORTH);
      jpWhiteBoardMain.add(jpWhiteBoardSouth, BorderLayout.CENTER);
      jpWhiteBoardNorth.add(wbTools, BorderLayout.WEST);
      jpWhiteBoardSouth.add(wbButtons, BorderLayout.SOUTH);
      jpWhiteBoardSouth.add(jpWbSouthCenter, BorderLayout.CENTER);
      
      imageLabel = new JLabel();     
           
      jpMain.add(northConnect, BorderLayout.NORTH);
      jpMain.add(centerReceive, BorderLayout.CENTER);
      jpMain.add(southSend, BorderLayout.SOUTH);
      
      add(jpMain, BorderLayout.EAST);
      add(jpCodePanel, BorderLayout.CENTER);
      setJMenuBar(jmb);
      
      //Tabbed panes
      tabbedPane = new JTabbedPane();
      tabbedPane.addTab("Code", null, jpCodePanel);
      tabbedPane.addTab("Whiteboard", null, jpWhiteBoardMain);
      add(tabbedPane);
      
      //disable whiteboard buttons prior to connecting
      wbBlack.setEnabled(false);
      wbRed.setEnabled(false);
      wbBlue.setEnabled(false);
      wbYellow.setEnabled(false);
      wbGreen.setEnabled(false);
      wbClear.setEnabled(false);
      wbPrev.setEnabled(false);
      wbNext.setEnabled(false);
      wbSend.setEnabled(false);
      newNoti.setVisible(false);
      erase.setEnabled(false);
      oneB.setEnabled(false);
      fiveB.setEnabled(false);
      tenB.setEnabled(false);
      
      pack();
      setVisible(true);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
   }//end ChatClient constructor
   
   /**
   *  Action Preformed for all objects. This actionPerformed handles all JMenu items, buttons in the GUI for sending and clearing text and images,
      all tools on the whiteboard tab and chat section.
   */
   public void actionPerformed(ActionEvent ae)
   {
      Object choice = ae.getSource();
      
      if (choice == send)
      {
         try
         {
            sendObj = "CHATMSG87&^@1" + name.getText() + ": " + sendBox.getText();
            oos.writeObject((String)sendObj);
            sendBox.setText("");
         }
         catch(IOException ioe)
         {
            System.out.println("IO Exception");
         }
      }//end AP send
      
      else if (choice == connect)
      {
         try
         {
            s = new Socket(connectInfo.getText(), 14789);
            
            InputStream is = s.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            
            OutputStream os = s.getOutputStream();
            oos = new ObjectOutputStream(os);
            
            ois = new ObjectInputStream(is);
            
            ReadHandler read = new ReadHandler();
            read.start();
            
            //enable buttons and fields when connected
            wbBlack.setEnabled(true);
            wbRed.setEnabled(true);
            wbBlue.setEnabled(true);
            wbYellow.setEnabled(true);
            wbGreen.setEnabled(true);
            wbClear.setEnabled(true);
            wbPrev.setEnabled(true);
            wbNext.setEnabled(true);
            wbSend.setEnabled(true);
            send.setEnabled(true);
            sendBox.setEditable(true);
            connect.setEnabled(false);
            jtaCodeSend.setEditable(true);
            jbCodeSend.setEnabled(true);
            jbCodeClear.setEnabled(true);
            erase.setEnabled(true);
            oneB.setEnabled(true);
            fiveB.setEnabled(true);
            tenB.setEnabled(true);
            JOptionPane.showMessageDialog(null, "You have connected. Welcome to the session!");
         }
         catch(IOException ioe)
         {
            System.out.println("IO Exception");
         }
      }//end AP connect
      
      else if (choice == jbCodeSend)
      {
         try
         {
            sendObj = (String)jtaCodeSend.getText();
            oos.writeObject(sendObj);
         }
         catch(IOException ioe)
         {
            System.out.println("IO Exception");
         }
         
      }//end AP jbCodeSend
      
      else if (choice == jbCodeClear)
      {
         jtaCodeSend.setText("");
      }//end AP jbCodeClear
      
      else if (choice == jmiClose)
      {
         System.exit(0);
      }//end AP Close
      
      //send button for the whiteboard
      else if (choice == wbSend)
      {
         
         try{
            //creates an image of the whiteboard to send over the network
            BufferedImage bufImage = new BufferedImage(jpWhiteBoardNorth.getSize().width -90, jpWhiteBoardNorth.getSize().height,BufferedImage.TYPE_INT_RGB);
            wbNorth.paint(bufImage.createGraphics());
            jpWbSouthCenter.removeAll();
            
            //creates the final image, and sends it over the network
            loadImage = new ImageIcon(bufImage);
            oos.reset();
            oos.writeObject(loadImage);
            oos.flush();  
            
         }
         catch(Exception ioeS)
         {
            System.out.println("The Saving and loading of the image has encountered an IO problem");
         
         }
      }//end AP for wbSend
      
      //cycles through the whiteboard vector
      else if (choice == wbNext)
      {
         saveCounter++;
         
         //resets the new image notification
         newNoti.setVisible(false);
      
         //if the user is viewing the last image, the counter resets to 0
         if (saveCounter >= images.size())
         {
            saveCounter = 0;
         }
         
         //loads the image into the southern JPanel in the whiteboard area
         loadImage = images.get(saveCounter);
         imageLabel.setIcon(loadImage);
         jpWbSouthCenter.add(imageLabel, BorderLayout.CENTER );
         jpWbSouthCenter.validate();
      
      }//end AP wbNext
      
      //cycles through the whiteboard vector
      else if (choice == wbPrev)
      {
         saveCounter--;
         
         //resets the new image notification
         newNoti.setVisible(false);
         
         //if the user is viewing the first image, the counter resets to the last index
         if (saveCounter == -1)
         {
            saveCounter = images.size() - 1;
         }
      
         //loads the image into the southern JPanel in the whiteboard area
         loadImage = images.get(saveCounter);
         imageLabel.setIcon(loadImage);
         jpWbSouthCenter.add(imageLabel, BorderLayout.CENTER );   
         jpWbSouthCenter.validate();
      }//end AP wbPrev
      
      //about menu option
      else if (choice == jmiAbout)
      {
         JOptionPane.showMessageDialog(null, "Created by: Alex Kohl, Mohammed Suhail, Jason Carmichael and Gregory Ghiroli for ISTE-121 at RIT.");
      }//end AP about
      
      //Help menu option
      else if (choice == jmiHelp)
      {
         JOptionPane.showMessageDialog(null, "* First, to connect to the program specify the IP address of the server you wish to connect to, and then enter a username for the session which will define who\nyou are during chat.\n\n* The top section for both the code and whiteboard tabs is used for populating the code or image you wish to send to others on the program.\nOnce you wish to send whatever text or drawing you have created to other users, press the send button on the bottom of the screen in either tab.\n\n* To the right of the screen is a chat area that can be used to chat with other members in the program, simply type what you wish to send and then press send.\n\n* On the whiteboard tab you will find a variety of tools that can be used to when drawing an image in the upper portion of the whiteboard tab.\n\n* Once either the image or text is sent or received by any of the users, the bottom portion of both tabs will be populated with either the code that was sent or image respectively.");
      }//end jmiHelp action performed
      
      //buttons to change the color of the whiteboard
      else if (choice == wbBlack)
      {
         wbNorth.black();
      }
      else if (choice == wbBlue)
      {
         wbNorth.blue();
      }
      else if (choice == wbGreen)
      {
         wbNorth.green();
      }
      else if (choice == wbYellow)
      {
         wbNorth.yellow();
      }
      else if (choice == wbRed)
      {
         wbNorth.red();
      }
      
      //clears the whiteboard
      else if (choice == wbClear)
      {
         wbNorth.clear();
      }
      
      //allows the user to erase content
      else if ( choice == erase)
      {
         wbNorth.erase();
      }
      
      //buttons to change the width of the pen
      else if ( choice == oneB)
      {
         wbNorth.oneB();
      }
      else if ( choice == fiveB)
      {
         wbNorth.fiveB();
      }
      else if ( choice == tenB)
      {
         wbNorth.tenB();
      }


   }//end actionPerformed
   
   /**
   *  Threaded method used to accept all incoming objects from the server
   */
   public class ReadHandler extends Thread
   {
      public ReadHandler()
      {
      
      }//end constructor ReadHandler
      
      /**
      *  run method for read handler
      */
      public void run()
      {
         //runs until the program exits
         while (true)
         {
            try
            {
               //reads in an object from the server
               readObj = ois.readObject();
               
               //if the object is a string
               if(readObj instanceof String)
               {             
                  //casts the object to a string
                  readMsg = (String)readObj;
                  
                  //checks to see if this string is a chatmessage or not
                  if (readMsg.length() >= 13 && readMsg.substring(0,13).equalsIgnoreCase("CHATMSG87&^@1")) //checks for the chat message id attached by the server to denote a chat message
                  {  
                     //appends the string to the chat, after the chat message identifier
                     chatBox.append(readMsg.substring(13,readMsg.length()) + "\n");
                  }
                  //the string is from a client's code area
                  else
                  {
                     //appends it to the code area south pannel and makes a few newlines beneath it
                     jtaCodeReceive.append(readMsg + "\n");
                     jtaCodeReceive.append("\n ------------------------------ \n");
                  }
               }
               
               //the object from the server is a whiteboard image
               else if(readObj instanceof ImageIcon) 
               {
                  //sets the new whiteboard message to visible
                  newNoti.setVisible(true);
                  
                  //makes a new JLabel for the whiteboard image to be placed into
                  JLabel jLabel = new JLabel();
                  
                  //the JLabel is now a picture of the whiteboard
                  ImageIcon castedIcon = (ImageIcon)readObj;
                  jLabel.setIcon(castedIcon);
                  
                  //adds this picture to an array of pictures for the user to cycle through
                  images.add(castedIcon);
                  
                  //if this is the first whiteboard the client has gotten, display it in the southern panel for whiteboards
                  if (images.size() == 1)
                  {
                     newNoti.setVisible(false);
                     jpWbSouthCenter.add(new JLabel(loadImage), BorderLayout.CENTER );
                     jpWbSouthCenter.validate();
                  }
               }
            }
            // If the server abruptly stops, a message is displayed to the user, and then shuts down upon acknowledgement.
            catch(IOException ioe)
            {
               JOptionPane.showMessageDialog(null, "The client has been disconnected from the server. \nThe client will now shut down. Please contact the administrator.");
               
               System.exit(0);
            }
            catch (ClassNotFoundException cnfe)
            {
               System.out.println("Class not found exception");
            }
            
         }//end run while loop
         
      }//end ReadHandler run method
      
   }//end inner class ReadHandler
   
}//end TAClient class