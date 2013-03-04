
public class Jarmadeus2 {

	private static Jarmadeus2 instance;
	
	private Jarmadeus2(){
	}
	
	public static Jarmadeus2 getInstance(){
		if(instance==null)instance=new Jarmadeus2();
		return instance;
	}
	
	public boolean isSimulation(){
		
	}
	
	public String getServerAddress(){
		
	}
	
	public String getRobotName(){
		
	}
}
