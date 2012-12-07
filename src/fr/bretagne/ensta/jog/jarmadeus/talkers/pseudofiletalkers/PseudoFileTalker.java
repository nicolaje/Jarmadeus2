package fr.bretagne.ensta.jog.jarmadeus.talkers.pseudofiletalkers;

import fr.bretagne.ensta.jog.jarmadeus.Jarmadeus;

public class PseudoFileTalker implements IPseudoFileTalker {

	private IPseudoFileTalker delegate;
	
	public PseudoFileTalker(){
		switch(Jarmadeus.getInstance().getMode()){
		case Jarmadeus.HARDWARE:
			this.delegate=new HardPseudoFileTalker();
			break;
		case Jarmadeus.VIRTUAL:
			this.delegate=new VirtualPseudoFileTalker();
			break;
		}
	}
	
	@Override
	public String read(String pseudoFilePath) {
		return this.delegate.read(pseudoFilePath);
	}

	@Override
	public int write(String pseudoFilePath, String data) {
		return this.delegate.write(pseudoFilePath, data);
	}

	@Override
	public void setDebugMode(boolean mode) {
		this.delegate.setDebugMode(mode);
	}

	@Override
	public boolean getDebugMode() {
		return this.delegate.getDebugMode();
	}

}
