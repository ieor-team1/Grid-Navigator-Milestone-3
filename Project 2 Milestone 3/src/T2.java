
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import lejos.nxt.*;

import java.io.*;


/**
This class needs a higher level controller to implement the navigtion logic<br>
Responsibility: keep robot on the line till it senses a marker, then stop <br>
also controls turning to a new line at +- 90 or 180 deg<br>
Hardware: Two light sensors , shielded, 2 LU above floor.
Classes used:  Pilot, LightSensors<br>
Control Algorithm:  proportional control. estimate distance from centerline<br>
Calibrates both sensors to line, background
Updated 9/10/2007  NXT hardware
@author Roger Glassey
 */
public class T2
{

  /**
   * controls the motors
   */
  public DifferentialPilot pilot;
  /**
   *set by constructor , used by trackline()
   */
  private LightSensor leftEye;
  /**
   *set by constructor , used by trackline()
   */
  private LightSensor rightEye;
  
  private int _turnDirection = 1;
  
  int lval; //left sensor light value
  int rval; //right sensor light value
  int lval2 = 5; //Memory bank variables
  int rval2 = 5;
  int i;  //# of black marker 

  int control;
  /**
   *constructor - specifies which sensor ports are left and right
   */
  public T2(DifferentialPilot thePilot, LightSensor leftEye , LightSensor  rightEye) 
  {
    pilot = thePilot;
    pilot.setTravelSpeed(12);
    pilot.setRotateSpeed(180);
    pilot.setAcceleration(80);
    this.leftEye = leftEye;
    this.leftEye.setFloodlight(true);
    this.rightEye = rightEye;
    this.rightEye.setFloodlight(true);
  }

 

  /**
  follow line till intersection (a black marker) is detected
  uses proportional  control <br>
  Error signal is supplied by CLdistance()<br>
  uses CLdistance(), pilot.steer()
  loop execution about 65 times per second in 1 sec.<br>
   */
  public void trackLine()
  {  	
	  while(true){
	  	float gain = 0.5f; //gain to smooth the control 
        int error = CLDistance(lval, rval);
       //pilot.travel(200, true); //start the robot going forward
        lval = leftEye.getLightValue();
        rval = rightEye.getLightValue();
        control=lval-rval;
        pilot.steer(control*gain);         
        if((lval<-5 || rval<-5) && (rval2 > -8 && lval2 > -8)){//When it encounters black marker
        pilot.travel(6.5);
      	Sound.beep();
      	return;
        }
        lval2 = lval;
        rval2 = rval;
	  }
  }	

  public void print(){

      System.out.println("left "+ lval); //Output readings, so we can troubleshoot along the way.
      System.out.println("right "+ rval);
      System.out.println("control " +control*0.6);
      System.out.println ("i is: " + i);
      System.out.println("lval2 " + lval2);
      System.out.println("rval2 " + rval2);
      LCD.refresh();

  }

  public void turn(int angle)
  {
    pilot.rotate(angle * 90);
  }

  public boolean isMoving()
  {
	  return pilot.isMoving();
  }
  public void stop()
  {
    pilot.stop();
  }

 

  /**
   * helper method for Tracker; calculates distance from centerline, used as error by trackLine()
   * @param left light reading
   * @param right light reading
   * @return  distance
   */

  int CLDistance(int left, int right)
  {
     return 0;
  }

  

  


  /**
  calibrates for line first, then background, then marker with left sensor.  displays light sensor readings on LCD (percent)<br>
  Then displays left sensor (scaled value).  Move left sensor  over marker, press Enter to set marker value to sensorRead()/2
   */
  public void calibrate()
  {
	  LCD.clearDisplay();
      System.out.println("Calibrate T2");
    
      for (byte i = 0; i < 3; i++)
      {
        while (0 == Button.readButtons())//wait for press
        {
          LCD.drawInt(leftEye.getLightValue(), 4, 6, 1 + i);
          LCD.drawInt(rightEye.getLightValue(), 4, 12, 1 + i);
          if (i == 0)
          {
            LCD.drawString("LOW", 0, 1 + i);
          } else if (i == 1)
          {
            LCD.drawString("HIGH", 0, 1 + i);
          } 
        }
        Sound.playTone(1000 + 200 * i, 100);
        if (i == 0)
        {
          leftEye.calibrateLow();
          rightEye.calibrateLow();
        } else if (i == 1)
        {
          rightEye.calibrateHigh();
          leftEye.calibrateHigh();
        } 
        while (0 < Button.readButtons())
        {
          Thread.yield();//button released
        }
       
    }
    while (0 == Button.readButtons())// while no press
    {
      int lval = leftEye.getLightValue();
      int rval = rightEye.getLightValue();
      LCD.drawInt(lval, 4, 0, 5);
      LCD.drawInt(rval, 4, 4, 5);
      LCD.drawInt(CLDistance(lval, rval), 4, 12, 5);
      LCD.refresh();
    }
    LCD.clear();
  }
  }




