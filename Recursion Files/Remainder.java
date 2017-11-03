//Gregory Ghiroli
//4/1/16
//Recursion
//Thisd program finds the remainder of two numbers from the argument line



import java.util.*;
import java.io.*;
import java.text.*;
import java.math.*;





public class Remainder{

//Attributes

   private static double firstNum;
   private static double secondNum;

   private  static double thirdNum = 2;

   private double remainder = 1;




   public static void main (String [] args){
   
      try{
      
         int valid = args.length;
         
         if(valid > 0 && valid < 3)
         {
         
            for (int i = 0; i < args.length; i++) {
            
               firstNum = Integer.parseInt(args[0]);
               secondNum = Integer.parseInt(args[1]);
            
            
            
            }//end of args for
         
            System.out.println(" The numbers chosen are " + firstNum + " " + secondNum);
         
            //create method
            Remainder rm = new Remainder();
            
            //call method with variables
            rm.Remainder( firstNum, secondNum, thirdNum);
         
         }//end of valid if
         
         else
         {
         
            System.out.println("Please write only two numbers");
         
            return;
         }//end of else
      
      }
      catch(Exception e)
      {
      
         System.out.println(" You did not write two integers, write them again");
      
         return;
      }
   
   }//end of main
   public void Remainder(double firstNum, double secondNum, double thirdNum)
   {
   
   
      while(thirdNum > 1)
      {
      
      
         thirdNum = firstNum / secondNum;
      
      
         remainder = (thirdNum - (int)thirdNum)* secondNum;
         
         
         
         if( secondNum == 16)
         {
         
            if( remainder == 10){
            
               System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  A");
               firstNum = (int)thirdNum;
            
              
            }
           else if( remainder == 11){
            
               System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  B");
               firstNum = (int)thirdNum;
            
            
            }
           else if( remainder == 12){
            
               System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  C");
               firstNum = (int)thirdNum;
            
            
            }
           else if( remainder == 13){
            
               System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  D");
               firstNum = (int)thirdNum;
            
            
            }
            else if( remainder == 14){
            
               System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  E");
               firstNum = (int)thirdNum;
            
            
            }
            else if( remainder == 15){
            
               System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  F");
               firstNum = (int)thirdNum;
            
            
            }
            else
            {
               System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  "+ remainder);
            
            
            
               firstNum = (int)thirdNum;
            
            
            
            }
         
         
         
         
         
         
         }//end of if hexdecimal seconenum == 16
         
         else{
         
         
            System.out.println(" The first number divided by the second number equals  "+(int)thirdNum + " The remainder of that equation is  "+ remainder);
         
         
         
            firstNum = (int)thirdNum;
         
         }//end of regular else
         
         
      }   //end of while
   
   
   }//end of Remainder method








}//end of public class