import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Test {
	public static void main(String[] args){
		
		Robot r=new Robot();
		
		// Let's see some sensors outputs
		for(int i=0; i<10; i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("==========");
			System.out.println("Heading: "+r.getHeading());
			System.out.println("==========");
			System.out.println("IRBack: "+r.getIRBack());
			System.out.println("IRLeft: "+r.getIRLeft());
			System.out.println("IRFront: "+r.getIRFront());
			System.out.println("IRRight: "+r.getIRRight());
			System.out.println("==========");
			System.out.println("Sonar LeftLeft: "+r.getSonarLeftLeft());
			System.out.println("Sonar Left: "+r.getSonarLeft());
			System.out.println("Sonar Front: "+r.getSonarFront());
			System.out.println("Sonar Right: "+r.getSonarRight());
			System.out.println("Sonar RightRight: "+r.getSonarRightRight());
			System.out.println("==========");
			System.out.println("Odometer Left: "+r.getOdometerLeft());
			System.out.println("Odometer Right: "+r.getOdometerRight());
		}
		
		// Now let's turn around the right wheel
		r.setSpeedLeft(0.5);
		r.setSpeedRight(0);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// And turn around the left wheel
		r.setSpeedLeft(0);
		r.setSpeedRight(0.5);
		try {
			Thread.sleep(4500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// Now run into the cube
		r.setSpeedLeft(0.5);
	}
}
