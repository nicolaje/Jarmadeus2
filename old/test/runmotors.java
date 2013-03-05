package test;
import jog.*;

public class runmotors {
	public static void main(String[] args) {
		motors motorL = new motors();
		motorL.setLeft();
		motors motorR = new motors();
		motorR.setRight();
		motorL.setDirection(motorL.DIRECTION_FORWARD);
		motorR.setDirection(motorR.DIRECTION_FORWARD);
		motorL.setSignedSpeed(30);
		motorR.setSignedSpeed(30);
		
		timer.wait(10000);
		
		motorL.stop();
		motorR.stop();
	}
}
