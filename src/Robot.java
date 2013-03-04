import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Robot {
	private boolean exitJVM;
	
	private String robotName;
	private String ipAdd;

	private PrintWriter writer;
	private BufferedReader reader;

	private double speedLeft, speedRight;

	private int irLeftPort,irFrontPort,irRightPort,irBackPort;
	private int sonarLeftLeftPort,sonarLeftPort,sonarFrontPort,sonarRightPort,sonarRightRightPort;
	private int posePort;
	private int actuatorPort;
	private BufferedReader irLeft,irFront,irRight,irBack;
	private BufferedReader sonarLeftLeft,sonarLeft,sonarFront,sonarRight,sonarRightRight;
	private BufferedReader pose;
	private PrintWriter actuator;
	
	private double irLeftVal,irFrontVal,irRightVal,irBackVal;
	private double sonarLeftLeftVal,sonarLeftVal,sonarFrontVal,sonarRightVal,sonarRightRightVal;
	private double heading;
	
	private int leftMotorSpeed;
	private int rightMotorSpeed;
	
	public Robot() throws UnknownHostException, IOException {
		exitJVM=false;
		robotName = Jarmadeus2.getInstance().getRobotName();
		ipAdd = Jarmadeus2.getInstance().getServerAddress();

		Socket socket = new Socket(ipAdd, 4000);
		writer = new PrintWriter(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		boolean gotAnswer = false;

		// Several clients can access the Simulation service,
		// therefore, we have to make sure that we parse the answer WE asked for !
		while (!gotAnswer) {
			// Retrieve all the parameters of this robot:
			writer.write(robotName + " simulation get_all_stream_ports");
			String answer=reader.readLine();
			
			// The answer looks like :
			// robotName SUCCESS {"robot.sensorName": portNumber}
			if(answer.contains(robotName)&&answer.contains("SUCCESS"))gotAnswer=true;
			else{
				// the received buffer should be emptied here
			}
		}

		
		
		speedLeft = 0;
		speedRight = 0;
	}
	
	/**
	 * Answer will look like:
	 * A SUCCESS {"r.sonarRight": 60001, "r.sonarFront": 60003, "r.sonarRightRight": 60004, "r.sonarLeftLeft": 60007, "r.irLeft": 60002, "r.sonarLeft": 60006, "r.irRight": 60000, "r.irBack": 60005, "r.irFront": 60008}
	 */
	private void parseStreamPorts(String answer){
		answer=answer.split("{")[1].replace("}", "");
		String[] sensors=answer.split(",");
		for(String s:sensors){
			
			// Several robots might be listed
			if(s.contains(robotName)){
				int portNum=Integer.parseInt(s.split(":")[1]);
				if(s.contains("sonarLeftLeft"))this.sonarLeftLeftPort=portNum;
				else if(s.contains("sonarLeft"))this.sonarLeftPort=portNum;
				else if(s.contains("sonarFront"))this.sonarFrontPort=portNum;
				else if(s.contains("sonarRight"))this.sonarRightPort=portNum;
				else if(s.contains("sonarRightRight"))this.sonarRightRightPort=portNum;
				else if(s.contains("irLeft"))this.irLeftPort=portNum;
				else if(s.contains("irRight"))this.irRightPort=portNum;
				else if(s.contains("irFront"))this.irFrontPort=portNum;
				else if(s.contains("irBack"))this.irBackPort=portNum;
				else if(s.contains("pose"))this.posePort=portNum;
				else if(s.contains("actuator"))this.actuatorPort=portNum;
			}
		}
		
		// updates values of sonar sensors
		// we will want all the sonars to have the same update frequency, otherwise this
		// strategy will not work
		
		// updates values of IR sensors
		// IR sensors should have the samed update frequency too (can be different from the frequency of the sonar sensors though)
		
		// updates the pose sensor
		
		// We make sure we close the sockets before exiting the program
		Runtime.getRuntime().addShutdownHook(new Thread(){
			
			@Override
			public void run(){
				exitJVM=true;
			}
		});
	}

	public double getIRBack() {
		writer.write("IRBACK " + robotName + "_irBack get_local_data");
		try {
			String answer = reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRBack data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getIRLeft() {
		writer.write("IRLEFT " + robotName + "_irLeft get_local_data");
		try {
			String answer = reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getIRRight() {
		writer.write("IRRight " + robotName + "_irRight get_local_data");
		try {
			String answer = reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getIRFront() {
		writer.write("IRFRONT " + robotName + "_irFront get_local_data");
		try {
			String answer = reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRFront data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarLeftLeft() {
		writer.write("SONARLEFTLEFT " + robotName
				+ "_sonarLeftLeft get_local_data");
		try {
			String answer = reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err
					.println("Couldn't retrieve SonarLeftLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarLeft() {
		writer.write("SONARLEFT " + robotName + "_sonarLeft get_local_data");
		try {
			String answer = reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarFront() {
		writer.write("SONARFRONT " + robotName + "_sonarFront get_local_data");
		try {
			String answer = reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarRight() {
		writer.write("SONARRIGHT " + robotName + "_sonarRight get_local_data");
		try {
			String answer = reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarRightRight() {
		writer.write("SONARRIGHTRIGHT " + robotName
				+ "_sonarRightRight get_local_data");
		try {
			String answer = reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err
					.println("Couldn't retrieve SonarRightRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getOdometerLeft() {
		writer.write("ODOLEFT " + robotName + "_odometerLeft get_local_data");
		try {
			String answer = reader.readLine();
			return parseOdometerData(answer);
		} catch (IOException e) {
			System.err
					.println("Couldn't retrieve OdometerLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getOdometerRight() {
		writer.write("ODORIGHT " + robotName + "_odometerRight get_local_data");
		try {
			String answer = reader.readLine();
			return parseOdometerData(answer);
		} catch (IOException e) {
			System.err
					.println("Couldn't retrieve OdometerRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getHeading() {
		writer.write("ORIENTATION " + robotName + "_orientation get_local_data");
		try {
			String answer = reader.readLine();
			return parseOrientation(answer);
		} catch (IOException e) {
			System.err
					.println("Couldn't retrieve OdometerRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public void setSpeedLeft(double speed) {

	}

	public void setSetSpeedRight(double speed) {

	}

	private double convertIRDistance(double distance) {

	}

	private double parseLaserData(String rawData) {

	}

	private double parseOdometerData(String rawData) {

	}

	private double parseOrientation(String rawData) {

	}
	
	/**
	 * Updates the IR Sensors values as long as the JVM exists, and at least one
	 * reader buffer is not empty
	 * @author vjog
	 *
	 */
	class IRUpdater extends Thread{
		
		@Override
		public void run(){
			try {
				while(!exitJVM){
					if((irBack.ready()||irLeft.ready()||irFront.ready()||irRight.ready())){
						
					}
				}
			} catch (IOException e) {
				System.err.println("Couldn't read IR sensors through sockets at: ");
				e.printStackTrace();
			}
		}
	}
	
	class SonarUpdater extends Thread{
		
		@Override
		public void run(){
			
		}
	}
	
	class PoseUpdater extends Thread{
		
		@Override
		public void run(){
			
		}
	}
}
