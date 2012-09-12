import java.awt.Point;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Sound;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;
import lejos.nxt.*;

public class GridNavigator {
	
	static float wheelDiameter =  5.6f;
    static float trackWidth = 11.4f;
    static LightSensor leftEye = new LightSensor(SensorPort.S4);
    static LightSensor rightEye = new LightSensor(SensorPort.S1);
	DifferentialPilot pilot = new DifferentialPilot(wheelDiameter, trackWidth, Motor.A, Motor.C);
	Tracker t1 = new Tracker (pilot, leftEye, rightEye);
	
	Point position = new Point(0,0);
	Point error = new Point();
	public void goTo(Point() point){
		error = point - position;
	}
	
	public void xDiff(int x){
		int xerror = error.x - x;
		if(xerror<0){
			t1.turn(180);
		}
		for(int i =0; i< xerror; i++){
			t1.trackLine();
		}
		t1.turn(1); //Get ready for moving in the y direction
	}
	
	public void yDiff(int y){
		int yerror = error.y - y;
		if(yerror<0){
			t1.turn(-2);
		}
		for(int i =0; i< yerror; i++){
			t1.trackLine();
		}
		t1.turn(-1);
	}
}
