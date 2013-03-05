import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;


public class Jarmadeus2 {

	private static Jarmadeus2 instance;
	
	private final static String SETTINGS_FILE_NAME="settings.ini";
	
	private boolean isSimulation;
	private String serverAddress;
	private String robotName;
	
	private Robot robot;
	
	private Jarmadeus2(){
		doParseSettingsFile();
		this.robot=new Robot(robotName, serverAddress);
	}
	
	public static Jarmadeus2 getInstance(){
		if(instance==null)instance=new Jarmadeus2();
		return instance;
	}
	
	protected Robot getRobot(){
		return this.robot;
	}
	
	protected boolean isSimulation(){
		return isSimulation;
	}
	
	protected String getServerAddress(){
		return serverAddress;
	}
	
	protected String getRobotName(){
		return robotName;
	}
	
	/**
	 * There exist settings files parsers, but there is no need to add .jar dependencies for such basic task.
	 */
	private void doParseSettingsFile(){
		try {
			BufferedReader reader=new BufferedReader(new FileReader(SETTINGS_FILE_NAME));
			String line;
			while((line=reader.readLine())!=null){
				if(!line.contains("#")){
					String split[]=line.split("=");
					if(split[0].contains("server_ip"))serverAddress=split[1].replaceAll("\\s", "");
					else if(split[0].contains("simulation_mode"))isSimulation=Boolean.parseBoolean(split[1].replaceAll("\\s", ""));
					else if(split[0].contains("robot_name"))robotName=split[1].replaceAll("\\s", "");
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't find "+SETTINGS_FILE_NAME+" next to your program. Using default settings instead. At:");
			// TODO
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception when parsing "+SETTINGS_FILE_NAME+" file at: ");
			e.printStackTrace();
		}
	}
}
