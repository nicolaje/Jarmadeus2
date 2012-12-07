package fr.bretagne.ensta.jog.jarmadeus.talkers.pseudofiletalkers;

interface IPseudoFileTalker {

	public String read(String pseudoFilePath);
	public int write(String pseudoFilePath, String data);
	public void setDebugMode(boolean mode);
	public boolean getDebugMode();
}
