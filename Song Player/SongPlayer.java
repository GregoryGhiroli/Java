//Gregory Ghiroli
//ISTE 121
//Java sound midi

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.sound.midi.*;
import java.util.Vector;
import java.io.*;

import java.io.File;
import java.io.IOException;





public class SongPlayer {


//Attrbiutes
   JFrame frame;
   JLabel label;
   JPanel pane, pane2;
   JButton button1, button2, button3;
   Icon icon;
   File file;






   public static void main(String[] args) throws Exception {
   
      new SongPlayer();
   
   }//end of main
   public SongPlayer(){    
      //GUI SET UP
      try{
         frame = new JFrame("Song Player"); 
         frame.setLayout((new BorderLayout()));               
         pane = new JPanel();  
         pane2 = new JPanel();                       
         button1 = new JButton("Play"); 
         button2 = new JButton("Stop");
         button3 = new JButton("Next"); 
         
      
         icon = new ImageIcon("desert.gif");
         label = new JLabel(icon);
         pane2.add(label);
                  
         pane.setLayout(new GridLayout(0,2));
         frame.add(pane2, BorderLayout.CENTER);
                  
         frame.getContentPane().add(pane, BorderLayout.SOUTH);                   
         pane.add(button1);  
         pane.add(button2);
         pane.add(button3);
      
         
         frame.setSize( 1000,1000);
         frame.setLocationRelativeTo(null);                                  
         frame.pack();                                       
         frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      
      //Create the sequencer
      
         Sequencer sequencer = MidiSystem.getSequencer();
      
       
      //Opens the sequencer for playing
         sequencer.open();
      
      
      
      //Obtain the file
      
         file = new File("radioactive.mid");
         InputStream in = new BufferedInputStream(new FileInputStream(file));
      
      
      //Sets the sequence for the sequencer to run
         sequencer.setSequence(in);
      
      
      
      //Plays the midi file
         button1.addActionListener(
            new ActionListener() {
               public void actionPerformed(ActionEvent e) { 
                  sequencer.start();
               
               }
            });
            
      //Stops the midi file
         button2.addActionListener(
            new ActionListener() {
               public void actionPerformed(ActionEvent e) { 
                  sequencer.stop();
               
               }
            });
            
         button3.addActionListener(
            new ActionListener() {
               public void actionPerformed(ActionEvent e) { 
                  try{
                     icon = new ImageIcon("scencery.gif");
                     label = new JLabel(icon);
                     pane2.removeAll();
                     pane2.add(label);
                     frame.add(pane2, BorderLayout.CENTER);
                     frame.validate();
                     frame.pack();
                     file = new File("SweetHomeAlabama.mid");
                     InputStream in = new BufferedInputStream(new FileInputStream(file));
                     sequencer.setSequence(in);
                  
                     sequencer.start();
                  }
                  catch(Exception fe)
                  {
                     System.out.println(" file not found");
                  
                  }
               
               }
            });
      
            
            
            
      }//end of try
      catch(Exception e)
      {
         System.out.println(" Something went wrong");
      
      
      }//end of catch
   
   }//end of public song player

}//end of public
