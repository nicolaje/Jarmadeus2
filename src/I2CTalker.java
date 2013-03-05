
public class I2CTalker {

	private boolean debugMode;
	private I2CTalker delegate;
	
	public I2CTalker(){
		this.debugMode=true;
		if(Jarmadeus2.getInstance().isSimulation())delegate=new SoftI2CTalker();
		else delegate=new HardI2CTalker();
	}
	
	public int read(char addr,char reg){
		return delegate.read(addr, reg);
	}
	
	public int write(char addr,char reg, char data){
		return delegate.write(addr, reg, data);
	}
	
	public void setDebugMode(boolean mode){
		this.debugMode=mode;
	}
	
	public boolean getDebugMode(){
		return this.debugMode;
	}
}
