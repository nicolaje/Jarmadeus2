
public class Jarmadeus2 {

	private static Jarmadeus2 instance;
	
	private Jarmadeus2(){
	}
	
	public static Jarmadeus2 getInstance(){
		if(instance==null)instance=new Jarmadeus2();
		return instance;
	}
	
	public boolean isSimulation(){
		return true;
	}
	
	public String getServerAddress(){
		return "localhost";
	}
	
	public String getRobotName(){
		return "r";
	}
}
