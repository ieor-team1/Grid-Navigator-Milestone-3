import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Sound;
import java.awt.Point;



/**
counts number of presses of Leftand Rightbuttons <br>
Subtracts 1 from count if ENTER button is pressed simultaneously
uses loop to monitor buttons
@author Roger Glassey 9/10/07
 */
public class ButtonCounter
{
	Point point = new Point();

   public void count(String msg)
   {
      LCD.clear();
      System.out.println(msg);
      boolean counting = true;
      while (counting)  // sometime, make this false to exit the loop
      {
    	 // print();
         int buttonID = Button.waitForAnyPress();
         if (buttonID > 0)
         {
        	 if(buttonID == 2){//when Left button is pressed
        		point.x++;
        		print(); 
        		
        	 }
        	 if(buttonID == 3){//when Left and Enter button is pressed
         		point.x--;
         		print(); 
         		
         	 }
        	 if(buttonID == 4){//when Right button is pressed
         		point.y++;
         		print(); 
         		
         	 }
        	 if(buttonID == 5){//when Right and Enter button is pressed
          		point.y--;
          		print(); 
          		
          	 }
        	 if(buttonID==8){//When enter is pressed
        		 LCD.drawString("Exit now?", 0, 5);
        		 int buttonID2 = Button.waitForAnyPress();
        		 if (buttonID2 == 2){
        			 counting = false;
        		 }
        		 if(buttonID2==4){
        			 counting = true;
        		 }
        	 }
            if (buttonID == 8)
            {	
               LCD.clear();
               
            } else
            {
               Sound.beep();
            }
         }


      }
   }
   
   public Point get(){
	   return point;
   }
   
   public void print(){
	//System.out.println("X: " + point.x);
	//System.out.println("Y: " + point.y);
	LCD.drawString("X", 0, 1);
	LCD.drawString("Y", 5, 1);
	LCD.drawInt(point.x,0, 2);
	LCD.drawInt(point.y, 5, 2);
   }
}
