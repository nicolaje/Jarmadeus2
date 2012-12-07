package fr.bretagne.ensta.jog.jarmadeus.talkers.i2ctalkers;

class VirtualI2CTalker implements II2CTalker{
	
	private boolean debugMode;

	public VirtualI2CTalker(){
		this.debugMode=true;
	}
	
	@Override
	public int read(char addr, char reg) {
		return 0; // TODO
	}

	@Override
	public int write(char addr, char reg, char data) {
		return 0; // TODO
	}

	@Override
	public void setDebugMode(boolean mode) {
		this.debugMode=true;
	}

	@Override
	public boolean getDebugMode() {
		return this.debugMode;
	}

}
