import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

	private int irLeftPort, irFrontPort, irRightPort, irBackPort;
	private int sonarLeftLeftPort, sonarLeftPort, sonarFrontPort,
			sonarRightPort, sonarRightRightPort;
	private int posePort;
	private int actuatorPort;
	private int odometerLeftPort,odometerRightPort;
	private BufferedReader irLeft, irFront, irRight, irBack;
	private BufferedReader sonarLeftLeft, sonarLeft, sonarFront, sonarRight,
			sonarRightRight;
	private BufferedReader pose;
	private BufferedReader odometerLeft,odometerRight;
	private PrintWriter actuator;

	private double irLeftVal, irFrontVal, irRightVal, irBackVal;
	private double sonarLeftLeftVal, sonarLeftVal, sonarFrontVal,
			sonarRightVal, sonarRightRightVal;
	private double heading;

	private int leftMotorSpeed;
	private int rightMotorSpeed;
	
	private double leftOdometer,rightOdometer;

	public Robot() throws UnknownHostException, IOException {
		exitJVM = false;
		robotName = Jarmadeus2.getInstance().getRobotName();
		ipAdd = Jarmadeus2.getInstance().getServerAddress();

		Socket socket = new Socket(ipAdd, 4000);
		writer = new PrintWriter(socket.getOutputStream());
		reader = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));

		boolean gotAnswer = false;

		// Several clients can access the Simulation service,
		// therefore, we have to make sure that we parse the answer WE asked for
		// !
		while (!gotAnswer) {
			// Retrieve all the parameters of this robot:
			writer.write(robotName + " simulation get_all_stream_ports");
			String answer = reader.readLine();

			// The answer looks like :
			// robotName SUCCESS {"robot.sensorName": portNumber}
			if (answer.contains(robotName) && answer.contains("SUCCESS"))
				gotAnswer = true;
			else {
				// the received buffer should be emptied here
			}
		}

		speedLeft = 0;
		speedRight = 0;
	}

	/**
	 * Answer will look like: A SUCCESS {"r.sonarRight": 60001, "r.sonarFront":
	 * 60003, "r.sonarRightRight": 60004, "r.sonarLeftLeft": 60007, "r.irLeft":
	 * 60002, "r.sonarLeft": 60006, "r.irRight": 60000, "r.irBack": 60005,
	 * "r.irFront": 60008}
	 */
	private void parseStreamPorts(String answer) {
		answer = answer.split("{")[1].replace("}", "");
		String[] sensors = answer.split(",");
		for (String s : sensors) {

			// Several robots might be listed
			if (s.contains(robotName)) {
				int portNum = Integer.parseInt(s.split(":")[1]);
				if (s.contains("sonarLeftLeft"))
					this.sonarLeftLeftPort = portNum;
				else if (s.contains("sonarLeft"))
					this.sonarLeftPort = portNum;
				else if (s.contains("sonarFront"))
					this.sonarFrontPort = portNum;
				else if (s.contains("sonarRight"))
					this.sonarRightPort = portNum;
				else if (s.contains("sonarRightRight"))
					this.sonarRightRightPort = portNum;
				else if (s.contains("irLeft"))
					this.irLeftPort = portNum;
				else if (s.contains("irRight"))
					this.irRightPort = portNum;
				else if (s.contains("irFront"))
					this.irFrontPort = portNum;
				else if (s.contains("irBack"))
					this.irBackPort = portNum;
				else if (s.contains("pose"))
					this.posePort = portNum;
				else if (s.contains("actuator"))
					this.actuatorPort = portNum;
				else if(s.contains("odometerLeft"))
					this.odometerLeftPort=portNum;
				else if(s.contains("odometerRight"))
					this.odometerRightPort=portNum;
			}
		}

		// Open the sockets to the sensors
		try {
			// Channels to the sonar sensors
			Socket sonarLeftLeftSocket=new Socket(this.ipAdd, sonarLeftLeftPort);
			Socket sonarLeftSocket=new Socket(this.ipAdd, sonarLeftPort);
			Socket sonarFrontSocket=new Socket(this.ipAdd, sonarFrontPort);
			Socket sonarRightSocket=new Socket(this.ipAdd, sonarRightPort);
			Socket sonarRightRightSocket=new Socket(this.ipAdd, sonarRightRightPort);
			
			sonarLeftLeft=new BufferedReader(new InputStreamReader(sonarLeftLeftSocket.getInputStream()));
			sonarLeft=new BufferedReader(new InputStreamReader(sonarLeftSocket.getInputStream()));
			sonarFront=new BufferedReader(new InputStreamReader(sonarFrontSocket.getInputStream()));
			sonarRight=new BufferedReader(new InputStreamReader(sonarRightSocket.getInputStream()));
			sonarRightRight=new BufferedReader(new InputStreamReader(sonarRightRightSocket.getInputStream()));
			
			// Channels to the IR sensors
			Socket irLeftSocket=new Socket(this.ipAdd, irLeftPort);
			Socket irFrontSocket=new Socket(this.ipAdd, irFrontPort);
			Socket irRightSocket=new Socket(this.ipAdd, irRightPort);
			Socket irBackSocket=new Socket(this.ipAdd, irBackPort);
			
			irLeft=new BufferedReader(new InputStreamReader(irLeftSocket.getInputStream()));
			irFront=new BufferedReader(new InputStreamReader(irFrontSocket.getInputStream()));
			irRight=new BufferedReader(new InputStreamReader(irRightSocket.getInputStream()));
			
			// Channels to the Compass sensor:
			Socket poseSocket=new Socket(this.ipAdd,posePort);
			
			// Channels to the Odometer sensors:
			Socket odoLeftSocket=new Socket(this.ipAdd,odometerLeftPort);
			Socket odoRightSocket=new Socket(this.ipAdd,odometerRightPort);
			
			odometerLeft=new BufferedReader(new InputStreamReader(odoLeftSocket.getInputStream()));
			odometerRight=new BufferedReader(new InputStreamReader(odoRightSocket.getInputStream()));
			
			// Channel to the actuator:
			Socket actuatorSocket=new Socket(this.ipAdd,actuatorPort);
			
			actuator=new PrintWriter(new OutputStreamWriter(actuatorSocket.getOutputStream()));
			
			// Start the threads that will update the state of the robot !
			new IRUpdater().start();
			new SonarUpdater().start();
			new PoseUpdater().start();
			new OdometerUpdater().start();
		} catch (UnknownHostException e) {
			System.err.println("Wrong Simulation server IP Adress was given when initializing the sensors, at: ");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception when initializing the sensors at: ");
			e.printStackTrace();
		}
		
		// updates values of sonar sensors
		// we will want all the sonars to have the same update frequency,
		// otherwise this
		// strategy will not work

		// updates values of IR sensors
		// IR sensors should have the samed update frequency too (can be
		// different from the frequency of the sonar sensors though)

		// updates the pose sensor

		// We make sure we close the sockets before exiting the program
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				exitJVM = true;
				actuator.close();
			}
		});
	}

	public double getIRBack() {
		return irBackVal;
	}

	public double getIRLeft() {
		return irLeftVal;
	}

	public double getIRRight() {
		return irRightVal;
	}

	public double getIRFront() {
		return irFrontVal;
	}

	public double getSonarLeftLeft() {
		return sonarLeftLeftVal;
	}

	public double getSonarLeft() {
		return sonarLeftVal;
	}

	public double getSonarFront() {
		return sonarFrontVal;
	}

	public double getSonarRight() {
		return sonarRightVal;
	}

	public double getSonarRightRight() {
		return sonarRightRightVal;
	}

	public double getOdometerLeft() {
		return leftOdometer;
	}

	public double getOdometerRight() {
		return rightOdometer;
	}

	public double getHeading() {
		return heading;
	}

	public void setSpeedLeft(double speed) {

	}

	public void setSetSpeedRight(double speed) {

	}

	private double convertIRDistance(double distance) {

	}

	/**
	 * Raw data comes like this:
	 * {"point_list": [[0.8092199563980103, -0.14268717169761658, 6.868503987789154e-09],
	 * [0.7967934608459473, -0.12619972229003906, -1.8742866814136505e-08],
	 * [0.7848089933395386, -0.11029776930809021, -1.57160684466362e-09],
	 * [0.7732341885566711, -0.09494107216596603, 3.026798367500305e-09],
	 * [0.7620454430580139, -0.08009418100118637, -1.4319084584712982e-08],
	 * [0.7512136101722717, -0.0657225176692009, 2.019805833697319e-08],
	 * [0.7407174706459045, -0.05179598182439804, 1.6880221664905548e-08],
	 * [0.7305356860160828, -0.038285549730062485, 6.170012056827545e-09],
	 * [0.7168877124786377, -0.025034211575984955, -6.51925802230835e-09],
	 * [0.7021054625511169, -0.01225524116307497, 1.123407855629921e-08],
	 * [0.6903769373893738, 1.5861587598919868e-07, 7.566995918750763e-09],
	 * [0.6815443634986877, 0.011896495707333088, -1.5308614820241928e-08],
	 * [0.675529956817627, 0.023590171709656715, 4.249159246683121e-09],
	 * [0.6723352074623108, 0.03523562476038933, 1.979060471057892e-09],
	 * [0.6720442771911621, 0.04699396342039108, 2.2584572434425354e-08],
	 * [0.7007721662521362, 0.06130960211157799, 2.2526364773511887e-08],
	 * [0.7198054790496826, 0.07565464079380035, 4.540197551250458e-09], 
	 * [0.739972710609436, 0.09085726737976074, 1.4319084584712982e-08],
	 * [0.7614074349403381, 0.10700886696577072, -3.3760443329811096e-09],
	 * [0.7842307686805725, 0.12420985847711563, 1.8510036170482635e-08],
	 * [0.8086032271385193, 0.1425786167383194, 5.587935447692871e-09]],
	 * "range_list": [0.8217033828442815, 0.8067254581430171,
	 * 0.7925216147564504, 0.7790408381371291, 0.7662429105364056,
	 * 0.754082914018034, 0.7425260507029732, 0.7315380616241877,
	 * 0.7173245886189182, 0.7022122434754218, 0.6903767703344194,
	 * 0.6816480319288748, 0.6759415933179908, 0.6732576944812676,
	 * 0.6736852452993471, 0.7034488529933621, 0.7237702662767634,
	 * 0.7455296460364584, 0.7688900589333294, 0.7940062184400948,
	 * 0.8210772384791676]}
	 * @param rawData
	 * @return
	 */
	private double parseLaserData(String rawData) {
		String range_list=rawData.split("\"range_list\": [")[1].replace("]", "").replace("}", "");
		String list[]=range_list.split(",");
		
		// Find the shortest distance, it will be our detection
		double min=Double.parseDouble(list[0]);
		for(String val:list){
			if(Double.parseDouble(val)<min)min=Double.parseDouble(val);
		}
		return min;
	}

	
	private double parseOdometerData(String rawData) {

	}

	/**
	 * Raw data comes like this:
	 * {"x": -5.147782802581787,
	 * "y": 3.084372043609619,
	 * "z": 0.18506832420825958,
	 * "yaw": 0.7329784035682678,
	 * "pitch": 9.254169708583504e-05,
	 * "roll": -0.0001192293711937964}
	 * 
	 * And we only want "yaw" angle (around Z axis)
	 * @param rawData
	 * @return
	 */
	private double parseOrientation(String rawData) {
		return Double.parseDouble(rawData.split("\"yaw\":")[1].split(",")[0])*180./Math.PI;
	}

	/**
	 * Updates the IR Sensors values as long as the JVM does not want to quit.
	 * 
	 * @author vjog
	 * 
	 */
	class IRUpdater extends Thread {

		@Override
		public void run() {
			try {
				while (!exitJVM) {
					
					// Do not go in a blocking state (readLine() is blocking) if
					// not at least one buffer
					// is not empty. If we go in a blocking sate while the JVM
					// is exiting, we might never close the socket.
					if (irBack.ready()) {
						irBackVal=convertIRDistance(parseLaserData(irBack.readLine()));
					}
					if (irLeft.ready()) {
						irLeftVal=convertIRDistance(parseLaserData(irLeft.readLine()));
					}
					if (irFront.ready()) {
						irFrontVal=convertIRDistance(parseLaserData(irFront.readLine()));
					}
					if (irRight.ready()) {
						irRightVal=convertIRDistance(parseLaserData(irRight.readLine()));
					}
				}
			} catch (IOException e) {
				System.err
						.println("Couldn't read IR sensors through sockets at: ");
				e.printStackTrace();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println("Couldn't sleep in IRUpdater at: ");
				e.printStackTrace();
			}
			try {
				irLeft.close();
				irFront.close();
				irRight.close();
				irBack.close();
			} catch (IOException e) {
				System.err.println("Couldn't close IR streams at: ");
				e.printStackTrace();
			}
		}
	}

	class SonarUpdater extends Thread {

		@Override
		public void run() {
			while(!exitJVM){
				try {
					if(sonarLeftLeft.ready()){
						sonarLeftLeftVal=parseLaserData(sonarLeftLeft.readLine());
					}
					if(sonarLeft.ready()){
						sonarLeftVal=parseLaserData(sonarLeft.readLine());
					}
					if(sonarFront.ready()){
						sonarFrontVal=parseLaserData(sonarFront.readLine());
					}
					if(sonarRight.ready()){
						sonarRightVal=parseLaserData(sonarRight.readLine());
					}
					if(sonarRightRight.ready()){
						sonarRightRightVal=parseLaserData(sonarRightRight.readLine());
					}
				} catch (IOException e) {
					System.err
					.println("Couldn't read Sonar sensors through sockets at: ");
					e.printStackTrace();
				}
				// Don't overload the JVM by always looping. Let's sleep a little.
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Couldn't sleep in SonarUpdater at: ");
					e.printStackTrace();
				}
			}
			try {
				sonarLeftLeft.close();
				sonarLeft.close();
				sonarFront.close();
				sonarRight.close();
				sonarRightRight.close();
			} catch (IOException e) {
				System.err.println("Couldn't close sonar streams at: ");
				e.printStackTrace();
			}
		}
	}

	class PoseUpdater extends Thread {

		@Override
		public void run() {
			while(!exitJVM){
				try {
					if(pose.ready()){
						heading=parseOrientation(pose.readLine());
					}
				} catch (IOException e1) {
					System.err
					.println("Couldn't read Compass sensors through sockets at: ");
					e1.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Couldn't sleep in PoseUpdater at: ");
					e.printStackTrace();
				}
			}
			try {
				pose.close();
			} catch (IOException e) {
				System.err.println("Couldn't pose sonar streams at: ");
				e.printStackTrace();
			}
		}
	}
	
	class OdometerUpdater extends Thread{
		@Override
		public void run(){
			while(!exitJVM){
				try {
					if(odometerLeft.ready()){
						leftOdometer=parseOdometerData(odometerLeft.readLine());
					}
					if(odometerRight.ready()){
						rightOdometer=parseOdometerData(odometerRight.readLine());
					}
				} catch (IOException e1) {
					System.err
					.println("Couldn't read Odometer sensors through sockets at: ");
					e1.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					System.err.println("Couldn't sleep in Odometer at: ");
					e.printStackTrace();
				}
			}
			try {
				odometerRight.close();
				odometerLeft.close();
			} catch (IOException e) {
				System.err.println("Couldn't close odometer streams at: ");
				e.printStackTrace();
			}
		}
	}
}
