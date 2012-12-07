package fr.bretagne.ensta.jog.jarmadeus.talkers.i2ctalkers;

/**
 * @author Olivier Reynet
 * @date 04/12/09
 */
/**
 * I2Ctalker is used to dialog on a I2C bus via the Lmsensor linux module. <br>
 * This class needs the ji2c.c C library to communicate on the bus. This library
 * is given together with the jarmadeus package. <br>
 * Two methods are basically provided : read and write.<br>
 * A debug mode is avaliable, which writes out some low level messages.
 */

class HardI2CTalker implements II2CTalker {

	private boolean debugMode;

	/* Native Methods Declarations */
	private native char i2cread(char addr, char reg, boolean debug);

	private native int i2cwrite(char addr, char reg, char data, boolean debug);

	/* Dynamic Library Loading */
	static {
		System.out.println("load ...");
		// load libji2c.so
		System.loadLibrary("ji2c");
		System.out.println("libji2c.so SUCCEFULLY LOADED");
	}

	public HardI2CTalker() {
		this.debugMode = true;
	}

	/**
	 * @param addr
	 *            Sensor's address on the i2c bus. This address must follow the
	 *            lm_sensor i2c address format, i.e. it only takes into account
	 *            the 7 highest bits of the i2c adress. For example, if the i2c
	 *            address is 0xE0, then the lm_sensor i2c address is 0x70. This
	 *            way of addressing may be justified by the fact that the lowest
	 *            bit of i2c address is used by i2c bus to specify a write or a
	 *            read order.
	 * @param reg
	 *            address of the sensor's register to read
	 * @return the value read in the sensor's register converted to an int
	 */
	@Override
	public int read(char addr, char reg) {
		return i2cread(addr, reg, this.debugMode);
	}

	/**
	 * @param addr
	 *            Sensor's address on the i2c bus. This address must follow the
	 *            lm_sensor i2c address format, i.e. it only takes into account
	 *            the 7 highest bits of the i2c adress. For example, if the i2c
	 *            address is 0xE0, then the lm_sensor i2c address is 0x70. This
	 *            way of addressing may be justified by the fact that the lowest
	 *            bit of i2c address is used by i2c bus to specify a write or a
	 *            read order.
	 * @param reg
	 *            address of the sensor's register to write
	 * @param data
	 *            data to be written in the sensor's register
	 * @return an integer to account for the success of the writing. In case of
	 *         failure, this integer is negative.
	 */
	@Override
	public int write(char addr, char reg, char data) {
		return i2cwrite(addr, reg, data, this.debugMode);
	}

	/**
	 * @param a
	 *            boolean to select the debug mode : true ==> debug, false ==>
	 *            no debug.
	 */
	@Override
	public void setDebugMode(boolean mode) {
		this.debugMode = mode;
	}

	/**
	 * @return the boolean debugMode value : true ==> debug, false ==> no debug.
	 */
	@Override
	public boolean getDebugMode() {
		return this.debugMode;
	}

}
