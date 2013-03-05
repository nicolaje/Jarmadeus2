public class FPGATalker {

	private FPGATalker delegate;
	private boolean debugMode;

	public FPGATalker() {
		this.debugMode=true;
		
		if (Jarmadeus2.getInstance().isSimulation())
			delegate = new SoftFPGATalker();
		else
			delegate = new HardFPGATalker();
	}

	public int readChar(char reg) {
		return delegate.readChar(reg);
	}

	public int writeChar(char reg, char data) {
		return delegate.writeChar(reg, data);
	}

	public int readShort(char reg) {
		return delegate.readShort(reg);
	}
	
	public void setDebugMode(boolean mode){
		this.debugMode=mode;
	}
	
	public boolean getDebugMode(){
		return this.debugMode;
	}
}
