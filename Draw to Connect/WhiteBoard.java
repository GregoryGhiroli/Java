import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
* White Board for the TaClient, this allows the client to draw on a panel with varying colors and styles of pens
*
* @author Gregory Ghiroli
* @version build 5/11/16
*/



public class WhiteBoard extends JPanel{

//Attributes
   private Image draw;
   private Graphics2D g2D;
   private int curY, curX, oldX, oldY;
   private int x = 0;

   /**
  * Creates a new whiteboard object with a mouse listener
  */
   public WhiteBoard()
   {
   
      //To prevent the code from getting the wrong number
      setDoubleBuffered(false);
   
      //To get the first coordinate of when the mouse is clicked
      addMouseListener(
            new MouseAdapter() {
               public void mousePressed(MouseEvent e) {
               
                  oldX = e.getX();
                  oldY = e.getY();
                  
                  
                  //System.out.println( oldX  +
                  
               }//end of mouse pressed
            });
   
   
      //Create the second mouse coordinates to paint 
      addMouseMotionListener(
            new MouseMotionAdapter() {
               public void mouseDragged(MouseEvent me) {
               // coord x,y when drag mouse
                  curX = me.getX();
                  curY = me.getY();
               
                  if (g2D != null) {
                  // draw line if g2 context not null
                  
                     for(int i = 0; i <= x; i++)
                     {
                        g2D.drawLine(oldX+ i , oldY + i, curX + i, curY+ i);
                     }
                     

                  // refresh draw area to repaint
                     repaint();
                  // store current coords x,y as olds x,y
                     oldX = curX;
                     oldY = curY;
                  }//end of mousdragged
                  
               }//end of mouse adapter
            });
   }//end of whiteboard constructor
 
 
  /**
  *paints on the whiteboard
  */
  protected void paintComponent(Graphics g) {
    if (draw == null) {
      // image to draw 
      draw = createImage(getSize().width, getSize().height);
      g2D = (Graphics2D) draw.getGraphics();
     
      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      // clear draw area
      clear();
    }
 
    g.drawImage(draw, 0, 0, null);
  }//end of paintcomponen
 
 
  /**
  * clears the whiteboard of all markings by painting the entire area white
  */
  public void clear() {
    g2D.setPaint(Color.white);
    // draw white on entire draw area to clear
    g2D.fillRect(0, 0, getSize().width, getSize().height);
    
    repaint();
  }
  
  
  
  /*
   Color Changer Methods
  */
  
  /**
  * Changes the pen color to red
  */
  public void red() {
       g2D.setPaint(Color.red);
  }
  /**
  * Changes the pen color to black
  */
  public void black() {
    g2D.setPaint(Color.black);
  }
  /**
  * Changes the pen color to magenta
  */
  public void magenta() {
    g2D.setPaint(Color.magenta);
  }
  /**
  * Changes the pen color to green
  */
  public void green() {
    g2D.setPaint(Color.green);
  }
  /**
  * Changes the pen color to blue
  */
  public void blue() {
   g2D.setPaint(Color.blue);
  
  }
  /**
  * Changes the pen color to orange
  */
   public void orange() {
   g2D.setPaint(Color.orange);
  
  }  
  /**
  * Changes the pen color to yellow
  */ 
  public void yellow() {
   g2D.setPaint(Color.yellow);
  
  }
  
  
  /*
   Width Changer Methods
  */
  
  /**
  * Sets the width of the pen to one pixel
  */
  public void oneB()
  {
   x = 1;
   
  }
  /**
  * Sets the width of the pen to five pixels
  */
  public void fiveB()
  {
   x = 5;
  
  }
  /**
  * Sets the width of the pen to ten pixels
  */
  public void tenB()
  {
   x = 10;
   
  }
  
  
  /**
  * Sets the pen color to white and changes its width to 10 to allow the user to erase markings
  */
  public void erase() {
   g2D.setPaint(Color.white);
   x = 10;
  
  }
  
  
}//end of public class