package fr.bretagne.ensta.jog.jarmadeus;

import fr.bretagne.ensta.jog.jarmadeus.hard.HardFPGATalker;
import fr.bretagne.ensta.jog.jarmadeus.virtual.VirtualFPGATalker;

public class FPGATalker implements IFPGATalker{
	
	public final static char DIRECTION_FORWARD=0x0;
	public final static char DIRECTION_BACKWARD=0x1;
	
	private final char PERIOD_REG=0x0006;
	private final char STOP_ALL_REG=0x0004;
	
	private final char PWM_SPEED_RIGHT_REG=0x0C;
	private final char PWM_DIREC_RIGHT_REG=0x0E;
	private final char PWM_SPEED_LEFT_REG=0x08;
	private final char PWM_DIREC_LEFT_REG=0x0A;
	
	private IFPGATalker delegate;
	
	public FPGATalker(){
		switch(Jarmadeus.getInstance().getMode()){
		case Jarmadeus.HARDWARE:
			this.delegate=new HardFPGATalker();
		case Jarmadeus.VIRTUAL:
			this.delegate=new VirtualFPGATalker();
		default:
			// TODO Bon choix?
			this.delegate=new VirtualFPGATalker();
		}
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
	
	public void setDebugMode(boolean mode) {
		delegate.setDebugMode(mode);
	}
	
	public boolean getDebugMode() {
		return delegate.getDebugMode();
	}
}
