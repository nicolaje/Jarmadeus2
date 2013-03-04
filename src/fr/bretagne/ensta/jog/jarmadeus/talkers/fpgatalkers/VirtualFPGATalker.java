package fr.bretagne.ensta.jog.jarmadeus.talkers.fpgatalkers;


class VirtualFPGATalker implements IFPGATalker{

	private char PWM_DUTY_REG;
	private char DIRECTION_REG;
	
	@Override
	public int readChar(char reg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int writeChar(char reg, char data) {
		
		return 0;
	}

	@Override
	public int readShort(char reg) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setDebugMode(boolean mode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDebugMode() {
		// TODO Auto-generated method stub
		return false;
	}

}
