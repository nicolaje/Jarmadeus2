package fr.bretagne.ensta.jog.jarmadeus.talkers.i2ctalkers;

interface II2CTalker {
	public int read(char addr, char reg);
	
	public int write(char addr, char reg, char data);
	
	public void setDebugMode(boolean mode);
	
	public boolean getDebugMode();
}
