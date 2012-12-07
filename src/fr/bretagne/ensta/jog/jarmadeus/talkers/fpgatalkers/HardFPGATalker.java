package fr.bretagne.ensta.jog.jarmadeus.talkers.fpgatalkers;

/**
 * @author Olivier Reynet
 * @date 04/12/09
 */
/**
 *  FPGAtalker is used to dialog with the FPGA located on the APF27 board via memory mapping. <br>
 * Two methods are basically provided : read and write. A debug mode is avaliable.<br>
 *  <p>
 * Here is the description of the FPGA registers :<br>
 * STOP_ALL    :   0x0004<br>
 * PERIOD      :   0x0006<br>
 * PWM1_CYCLIC :   0x0008<br>
 * PWM1_PARAM  :   0x000A<br>
 * PWM2_CYCLIC :   0x000C<br>
 * PWM2_PARAM  :   0x000E<br>
 * PWM3_CYCLIC :   0x0010<br>
 * PWM3_PARAM  :   0x0012<br>
 * PWM4_CYCLIC :   0x0014<br>
 * PWM4_PARAM  :   0x0016<br>
 * LEDS        :   0x0018<br>
 * ADDR_ODO_1       :   0x0020<br>
 * ADDR_ODO_2       :   0x0022<br>
 * ADDR_TIMER       :   0x0024<br>
 * ADDR_DELTA_ODO   :   0x0026<br>
 * ADDR_DELTA_TIME  :   0x0028<br>
 * 
 */

class HardFPGATalker implements IFPGATalker{

	private boolean debugMode;
	
	/* Native Methods Declarations */
	private native char fpgareadU16(char regaddr, boolean debug);
	private native int  fpgawriteU16(char regaddr, char data, boolean debug);
	private native short fpgareadS16(char regaddr, boolean debug);
	/* Dynamic Library Loading  */
	static {
		System.out.println("load ...");
		// load libjfpga.so
		System.loadLibrary("jfpga");
		System.out.println("libjfpga.so SUCCEFULLY LOADED");
	}
	
	public HardFPGATalker(){
		this.debugMode=true;
	}
	
	@Override
	public int readChar(char reg) {
		return fpgareadU16(reg, this.debugMode);
	}

	@Override
	public int writeChar(char reg, char data) {
		return fpgawriteU16(reg, data, this.debugMode);
	}

	@Override
	public int readShort(char reg) {
		return fpgareadS16(reg, this.debugMode);
	}

	@Override
	public void setDebugMode(boolean mode) {
		this.debugMode = mode;		
	}

	@Override
	public boolean getDebugMode() {
		return this.debugMode;
	}
}
