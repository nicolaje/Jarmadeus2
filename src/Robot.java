import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Robot {
	private String robotName;
	private String ipAdd;

	private PrintWriter writer;
	private BufferedReader reader;

	private double speedLeft, speedRight;
	
	public Robot() throws UnknownHostException, IOException {
		robotName = Jarmadeus2.getInstance().getRobotName();
		ipAdd = Jarmadeus2.getInstance().getServerAddress();

		Socket socket = new Socket(ipAdd, 4000);
		writer=new PrintWriter(socket.getOutputStream());
		reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		speedLeft=0;
		speedRight=0;
	}

	public double getIRBack() {
		writer.write("IRBACK "+robotName+"_irBack get_local_data");
		try {
			String answer=reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRBack data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getIRLeft() {
		writer.write("IRLEFT "+robotName+"_irLeft get_local_data");
		try {
			String answer=reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getIRRight() {
		writer.write("IRRight "+robotName+"_irRight get_local_data");
		try {
			String answer=reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getIRFront() {
		writer.write("IRFRONT "+robotName+"_irFront get_local_data");
		try {
			String answer=reader.readLine();
			return convertIRDistance(parseLaserData(answer));
		} catch (IOException e) {
			System.err.println("Couldn't retrieve IRFront data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarLeftLeft() {
		writer.write("SONARLEFTLEFT "+robotName+"_sonarLeftLeft get_local_data");
		try {
			String answer=reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarLeftLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarLeft() {
		writer.write("SONARLEFT "+robotName+"_sonarLeft get_local_data");
		try {
			String answer=reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarFront() {
		writer.write("SONARFRONT "+robotName+"_sonarFront get_local_data");
		try {
			String answer=reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarRight() {
		writer.write("SONARRIGHT "+robotName+"_sonarRight get_local_data");
		try {
			String answer=reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getSonarRightRight() {
		writer.write("SONARRIGHTRIGHT "+robotName+"_sonarRightRight get_local_data");
		try {
			String answer=reader.readLine();
			return parseLaserData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve SonarRightRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getOdometerLeft() {
		writer.write("ODOLEFT "+robotName+"_odometerLeft get_local_data");
		try {
			String answer=reader.readLine();
			return parseOdometerData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve OdometerLeft data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getOdometerRight() {
		writer.write("ODORIGHT "+robotName+"_odometerRight get_local_data");
		try {
			String answer=reader.readLine();
			return parseOdometerData(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve OdometerRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public double getHeading() {
		writer.write("ORIENTATION "+robotName+"_orientation get_local_data");
		try {
			String answer=reader.readLine();
			return parseOrientation(answer);
		} catch (IOException e) {
			System.err.println("Couldn't retrieve OdometerRight data sensor at: ");
			e.printStackTrace();
		}
		return 0;
	}

	public void setSpeedLeft(double speed) {

	}

	public void setSetSpeedRight(double speed) {

	}
	
	private double convertIRDistance(double distance){
		
	}
	
	private double parseLaserData(String rawData){
		
	}
	
	private double parseOdometerData(String rawData){
		
	}
	
	private double parseOrientation(String rawData){
		
	}
}
