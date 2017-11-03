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

import java.io.File;
import java.io.IOException;



/**
This class is a demo of playing a midi note and to record that note

*/


public class MidiDemo{

//Attrbiutes
   JFrame frame;
   JPanel pane;
   JButton button1;
   Sequencer mySequencer;
   Sequence  sequence;



   public static void main(String [] args)
   {
      new MidiDemo();
   
   }//end of main method
   public MidiDemo()
   {
   
      try 
      
      {
         //GUI SET UP
         frame = new JFrame("Sound1");                
         pane = new JPanel();                         
         button1 = new JButton("Click me!"); 
       
      
                  
         pane.setLayout(new GridLayout(0,2));         
         frame.getContentPane().add(pane);                   
         pane.add(button1);
          
                 
         frame.setSize( 500,500);
         frame.setLocationRelativeTo(null);                                  
         frame.pack();                                       
         frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
         frame.setVisible(true);
      
         //Create a Synthesizer
         Synthesizer synth = MidiSystem.getSynthesizer();
         synth.open();  
      
         //Create a Channel
         MidiChannel[] mc = synth.getChannels();
      
         //Create a Instrument Array list
         Instrument[] instr = synth.getDefaultSoundbank().getInstruments();
      
         //Your choice for the instrument ( modify this number to change instruments) 
         synth.loadInstrument(instr[89]);
      
                           
         button1.addActionListener(
            new ActionListener() {
               public void actionPerformed(ActionEvent e) { 
                  // The first number is the note, the second number is how hard to hit
                  mc[4].noteOn(58,592);
               }
            });
                     
         
      
      
         
         
      }
      catch (MidiUnavailableException e)
      {
         e.printStackTrace();
         System.exit(1);
      }
   
   
   
   
   }//end of midi constructor













}//end of midiDemo