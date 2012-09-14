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
	T2 t1 = new T2 (pilot, leftEye, rightEye);
	
	Point position = new Point(0,0);
	Point error = new Point();
	int i;
	int direction = 1;
	int ndirection = 0;
	
	public void calibrate(){
		t1.calibrate();
	}

	public void goTo(Point point){
		System.out.println("current: " + position.x + position.y);
		System.out.println("go: " + point.x + point.y);
		double xerror = point.x - position.x;
		int yerror = point.y - position.y;
		
		if(xerror!=0){
			if (direction == 2){
				if (xerror > 0){
					t1.turn(1);
					direction = 1;
				}
				if (xerror < 0) {
					t1.turn(-1);
					direction = 3;
					xerror = -xerror;
				}
			}
			else if (direction == 4){
				if (xerror > 0){
					t1.turn(-1);
					direction = 1;
				}
				if (xerror < 0) {
					t1.turn(1);
					direction = 3;
					xerror = -xerror;
				}
			}
			else if (direction == 3 &&xerror > 0) {
				t1.turn(2);
				direction = 1;
				
			}
			else if (direction == 1 && xerror < 0 ) {
				t1.turn(2);
				direction = 3;
				xerror = -xerror;
			}

		for(int i =0; i< xerror; i++){
			if (direction == 1 ){
				point.x++;
			}
			else if (direction == 3) {
				point.x--;
			}
			t1.trackLine();
		}
		}
		//Get ready to move in the y direction

		if(yerror!=0){
		if (direction == 1) {
			if (yerror > 0) {
			t1.turn(-1);//turn left
			direction = 2 ;
			}
			if (yerror <0) {
				t1.turn(1);
				direction = 4;
			}
		}
		else if(direction == 3) {
			if (yerror > 0) {
				t1.turn(1);//turn right
				direction = 2 ;
			}
			if (yerror <0) {
				t1.turn(-1);
				direction = 4;
				yerror = -yerror;
			}
		}
		else if (direction == 4 && yerror > 0) {
			t1.turn(2);
			direction = 2;
		}
		else if (direction == 2 && yerror < 0 ) {
			t1.turn(2);
			direction = 4;
			yerror = -yerror;
		}
		for(int i =0; i< yerror; i++){
			if (direction == 2 ){
				point.y++;
			}
			else if (direction == 4) {
				point.y--;
			}
			t1.trackLine();
		}
		
		
		}
		position.x=point.x;
		position.y=point.y;
		System.out.println("Direction is: "+ direction);
	}
	
	

}
