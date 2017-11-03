import java.io.*;
import java.util.*;
import java.math.*;


public class Remainder3{


public static void main(String [] args)
{


//Call printbase
Remainder3 r3 = new Remainder3();

//first number in argument
 int num1 = Integer.parseInt(args[0]);

//second number in argument
int num2 = Integer.parseInt(args[1]);



//run the printbase with the numbers
r3.Remainder3(num1 , num2);






}//end of main method
public void Remainder3(int num1 , int num2)
{
 if (num1 / num2 > 0) 
 {
			Remainder3(num1 / num2, num2);
		System.out.print(num1 % num2 );
	}//end of if

}//end of remainder3

}//end of public class