package fr.bretagne.ensta.jog.jarmadeus.talkers.i2ctalkers;

import fr.bretagne.ensta.jog.jarmadeus.Jarmadeus;

public class I2CTalker implements II2CTalker{
	
	private II2CTalker delegate;
	
	public I2CTalker(){
		switch(Jarmadeus.getInstance().getMode()){
		case Jarmadeus.HARDWARE:
			this.delegate=new HardI2CTalker();
			break;
		case Jarmadeus.VIRTUAL:
			this.delegate=new VirtualI2CTalker();
			break;
		}
	}

	@Override
	public int read(char addr, char reg){
		return this.delegate.read(addr, reg);
	}
	
	@Override
	public int write(char addr, char reg, char data){
		return this.delegate.write(addr, reg, data);
	}
	
	@Override
	public void setDebugMode(boolean mode){
		this.delegate.setDebugMode(mode);
	}
	
	@Override
	public boolean getDebugMode(){
		return this.delegate.getDebugMode();
	}
}
