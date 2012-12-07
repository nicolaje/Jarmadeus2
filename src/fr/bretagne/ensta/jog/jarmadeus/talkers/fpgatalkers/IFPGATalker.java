package fr.bretagne.ensta.jog.jarmadeus.talkers.fpgatalkers;

interface IFPGATalker {
	public int readChar(char reg);

	public int writeChar(char reg, char data);

	public int readShort(char reg);

	public void setDebugMode(boolean mode);

	public boolean getDebugMode();
}
