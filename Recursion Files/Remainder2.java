import java.util.*;
import java.io.*;




public class Remainder2{

//Attributes

   private double firstNum;
   private double secondNum;

   private double thirdNum = 2;

   private double remainder = 1;




   public static void main (String [] args){
   
      Remainder2 remain = new Remainder2();
   
   
       
   
   }//end of main
   Remainder2()
   {
   
      double firstNum = 431;
      double secondNum = 16;
   
   
   
      while(thirdNum > 1)
      {
      
      
         thirdNum = firstNum / secondNum;
      
      
         remainder = (thirdNum - (int)thirdNum)* secondNum;
      
      
      
         System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  "+ remainder);
         
         
         firstNum = (int)thirdNum;
         
      
      }//end of while
   
   
   
   }//end of Remainder2 method








}//end of public class