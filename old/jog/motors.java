package jog;
import jarmadeus.FPGATalker;

public class motors {
	private FPGATalker fpt;
	private char PWM_DUTY_REG;
	private char DIRECTION_REG;
	
	public final static char MOTOR_RIGHT = 1;
	public final static char MOTOR_LEFT = 2;
	
	public final char DIRECTION_FORWARD = 0x0;
	public final char DIRECTION_BACKWARD = 0x1;
	
	private final char PERIOD_REG = 0x0006;
	private final char STOP_ALL_REG = 0x0004;

	private final char PWM_SPEED_LEFT_REG = 0x0C;
	private final char PWM_DIREC_LEFT_REG = 0x0E;
	private final char PWM_SPEED_RIGHT_REG = 0x08;
	private final char PWM_DIREC_RIGHT_REG = 0x0A;

	
	public motors (char pwmreg, char pwmdir) {
		fpt = new FPGATalker();
		fpt.setDebugMode(false);
		PWM_DUTY_REG = pwmreg;
		DIRECTION_REG = pwmdir;
		fpt.writeChar(PERIOD_REG,(char)100);
		fpt.writeChar(STOP_ALL_REG,(char)0x0000);
		stop();		
	}

	public motors (int side) {		
		fpt = new FPGATalker();
		fpt.setDebugMode(false);
		if (side == MOTOR_RIGHT) {
			this.setRight();
		}
		if (side == MOTOR_LEFT) {
			this.setLeft();
		}
	}
	
	public motors () {		
		fpt = new FPGATalker();
		fpt.setDebugMode(false);
	}
	
	public void setLeft () {		
		PWM_DUTY_REG = PWM_SPEED_LEFT_REG;
		DIRECTION_REG = PWM_DIREC_LEFT_REG;
		fpt.writeChar(PERIOD_REG,(char)100);
		fpt.writeChar(STOP_ALL_REG,(char)0x0000);
		stop();		 
	}

	public void setRight () {		
		PWM_DUTY_REG = PWM_SPEED_RIGHT_REG;
		DIRECTION_REG = PWM_DIREC_RIGHT_REG;
		fpt.writeChar(PERIOD_REG,(char)100);
		fpt.writeChar(STOP_ALL_REG,(char)0x0000);
		stop();		 
	}
	
	public void stop () {
		setSpeed (0);
	}
	
	public void setSpeed (int speed) {
		int clipSpeed = speed;
		clipSpeed = (clipSpeed < 0) ? 0 : clipSpeed;
		clipSpeed = (clipSpeed > 99) ? 99 : clipSpeed;
		if (fpt.writeChar(PWM_DUTY_REG,(char)clipSpeed)<0)
			System.out.println("Motor Error : speed can not be assigned !");
	}
	
	public void setSignedSpeed (int signedSpeed) {
		int uspeed = 0;
		int direction = getDirection();
		//System.out.println("Direction = "+direction+", set signed speed to "+signedSpeed);
		if (signedSpeed < 0) {
			if (direction == DIRECTION_FORWARD)
				setDirection(DIRECTION_BACKWARD);
			uspeed = -signedSpeed;
		}
		if (signedSpeed >= 0) {
			if (direction == DIRECTION_BACKWARD)
				setDirection(DIRECTION_FORWARD);
			uspeed = signedSpeed;
		}
		uspeed = (uspeed < 0) ? 0 : uspeed;
		uspeed = (uspeed > 100) ? 100 : uspeed;
		setSpeed (uspeed);
		//System.out.println("Direction = "+getDirection()+", set speed to "+getSpeed());
		//if (fpt.writeChar(PWM_DUTY_REG,(char)speed)<0)
		//	System.out.println("Motor Error : speed can not be assigned !");
	}

	public int getSpeed () {
		int speed = fpt.readChar(PWM_DUTY_REG);
		if (getDirection() == DIRECTION_BACKWARD) {
			speed = -speed;
		}
		return speed;
	}

	public void setForward () {
		int direction = DIRECTION_FORWARD;
		if (fpt.writeChar(DIRECTION_REG,(char)direction)<0)
			System.out.println("Error : direction can not be assigned !");		
	}
	
	public void setBackward () {
		int direction = DIRECTION_BACKWARD;
		if (fpt.writeChar(DIRECTION_REG,(char)direction)<0)
			System.out.println("Error : direction can not be assigned !");
		
	}
	
	public void setDirection (int direction) {
		char setDirection = (char) DIRECTION_FORWARD;
		if (direction == DIRECTION_BACKWARD) {
			setDirection = (char) DIRECTION_BACKWARD;
		}
		if (fpt.writeChar(DIRECTION_REG,setDirection)<0)
			System.out.println("Error : direction can not be assigned !");	
	}
	
	public int getDirection () {
		return (fpt.readChar(DIRECTION_REG));
	}
	
	public static void test () {
		int i, iSpeed;

		motors motorL = new motors();
		motorL.setLeft();
		motors motorR = new motors();
		motorR.setRight();

		motorL.setDirection(motorL.DIRECTION_FORWARD);
		motorR.setDirection(motorR.DIRECTION_BACKWARD);

		for (i=0; i<100; i+=5) {
			iSpeed = i;
			System.out.println ("Speed = "+iSpeed);
			motorL.setSpeed(iSpeed);
			motorR.setSpeed(iSpeed);
			timer.wait((int)250);
		}
		for (i=90; i>=0; i-=5) {
			iSpeed = i;
			System.out.println ("Speed = "+iSpeed);
			motorL.setSpeed(iSpeed);
			motorR.setSpeed(iSpeed);
			timer.wait((int)250);
		}

		motorL.setDirection(motorL.DIRECTION_BACKWARD);
		motorR.setDirection(motorR.DIRECTION_FORWARD);

		for (i=0; i<100; i+=5) {
			iSpeed = i;
			System.out.println ("Speed = "+iSpeed);
			motorL.setSpeed(iSpeed);
			motorR.setSpeed(iSpeed);
			timer.wait((int)250);
		}
		for (i=90; i>=0; i-=5) {
			iSpeed = i;
			System.out.println ("Speed = "+iSpeed);
			motorL.setSpeed(iSpeed);
			motorR.setSpeed(iSpeed);
			timer.wait((int)250);
		}
		
		motorL.stop();
		motorR.stop();
	}

}
