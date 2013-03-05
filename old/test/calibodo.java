package test;
import jog.*;

public class calibodo {

	public static void main(String[] args) {

		odometers odo1 = new odometers();
		sonars snf = new sonars(sonars.SONAR_FRONT); 
		motors motorL = new motors();
		motorL.setLeft();
		motors motorR = new motors();
		motorR.setRight();
		motorL.setDirection(motorL.DIRECTION_FORWARD);
		motorR.setDirection(motorR.DIRECTION_FORWARD);

		double dRange = snf.getRange();
		double odoL = odo1.getOdo(odo1.ODO_LEFT);
		double odoR = odo1.getOdo(odo1.ODO_RIGHT);
		System.out.println("Range = "+dRange+", odoL = "+odoL+", odoR = "+odoR);
		motorL.setSignedSpeed(30);
		motorR.setSignedSpeed(30);
		timer.wait(1000);
		motorL.stop();
		motorR.stop();

		dRange = dRange - snf.getRange();
		double dOdo = (odo1.getOdo(odo1.ODO_LEFT) - odoL) * 0.5;
		dOdo = dOdo + (odo1.getOdo(odo1.ODO_RIGHT) - odoR) * 0.5;
		System.out.println("dRange = "+dRange+", dOdo = "+dOdo);
	}
}
