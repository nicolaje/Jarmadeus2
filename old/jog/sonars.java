package jog;
import jarmadeus.I2CTalker;

public class sonars {
	private I2CTalker i2ct;
	public static final char SONAR_ADDR_BASE = 0x70;
	public static final char SONAR_ADDR_RIGHT = 0x75;
	public static final char SONAR_ADDR_HALF_RIGHT = 0x74;
	public static final char SONAR_ADDR_FRONT = 0x73;
	public static final char SONAR_ADDR_HALF_LEFT = 0x72;
	public static final char SONAR_ADDR_LEFT = 0x71;

	private char sonarAddr;
	private final char SONAR_CMD_REG = 0x00;
	private final char SONAR_MSBYTE_REG = 0x02;
	private final char SONAR_LSBYTE_REG = 0x03;

	public static final char SONAR_RIGHT = 5;
	public static final char SONAR_HALF_RIGHT = 4;
	public static final char SONAR_FRONT = 3;
	public static final char SONAR_HALF_LEFT = 2;
	public static final char SONAR_LEFT = 1;

	public double distFront;
	public double distLeft;
	public double distHalfLeft;
	public double distRight;
	public double distHalfRight;

	public sonars (int sonarNum) {
		i2ct = new I2CTalker();
		i2ct.setDebugMode(false);
		if ((sonarNum >= 1) && (sonarNum <= 5)) {
			this.sonarAddr=(char)((int)SONAR_ADDR_BASE+sonarNum);
		}
		
	}

	public sonars () {
		i2ct = new I2CTalker();
		i2ct.setDebugMode(false);		
	}

	public double getRange()
	{
		int iStat = i2ct.write(sonarAddr, SONAR_CMD_REG, (char)0x51);
		if (iStat != 0) {
			System.out.println ("Cannot start sonar at addr "+sonarAddr);
		}
		timer.wait(65);
		int rangeHi;
		int rangeLo;
		rangeHi = i2ct.read(sonarAddr, SONAR_MSBYTE_REG);
		rangeLo = i2ct.read(sonarAddr, SONAR_LSBYTE_REG);
		rangeHi = rangeHi << 8;
		return (double) (rangeHi+rangeLo);
	}
	public void getAllRange () {
		int iStat = i2ct.write(SONAR_ADDR_FRONT, SONAR_CMD_REG, (char)0x51);
		if (iStat != 0) {
			System.out.println ("Cannot start front sonar");
		}
		iStat = i2ct.write(SONAR_ADDR_LEFT, SONAR_CMD_REG, (char)0x51);
		if (iStat != 0) {
			System.out.println ("Cannot start left sonar");
		}
		iStat = i2ct.write(SONAR_ADDR_HALF_LEFT, SONAR_CMD_REG, (char)0x51);
		if (iStat != 0) {
			System.out.println ("Cannot start halt left sonar");
		}
		iStat = i2ct.write(SONAR_ADDR_RIGHT, SONAR_CMD_REG, (char)0x51);
		if (iStat != 0) {
			System.out.println ("Cannot start right sonar");
		}
		iStat = i2ct.write(SONAR_ADDR_HALF_RIGHT, SONAR_CMD_REG, (char)0x51);
		if (iStat != 0) {
			System.out.println ("Cannot start halt right sonar");
		}
		timer.wait(65);
		int rangeHi;
		int rangeLo;
		rangeHi = i2ct.read(SONAR_ADDR_FRONT, SONAR_MSBYTE_REG);
		rangeLo = i2ct.read(SONAR_ADDR_FRONT, SONAR_LSBYTE_REG);
		rangeHi = rangeHi << 8;
		distFront = (double) (rangeHi+rangeLo);
		rangeHi = i2ct.read(SONAR_ADDR_LEFT, SONAR_MSBYTE_REG);
		rangeLo = i2ct.read(SONAR_ADDR_LEFT, SONAR_LSBYTE_REG);
		rangeHi = rangeHi << 8;
		distLeft = (double) (rangeHi+rangeLo);
		rangeHi = i2ct.read(SONAR_ADDR_HALF_LEFT, SONAR_MSBYTE_REG);
		rangeLo = i2ct.read(SONAR_ADDR_HALF_LEFT, SONAR_LSBYTE_REG);
		rangeHi = rangeHi << 8;
		distHalfLeft = (double) (rangeHi+rangeLo);
		rangeHi = i2ct.read(SONAR_ADDR_RIGHT, SONAR_MSBYTE_REG);
		rangeLo = i2ct.read(SONAR_ADDR_RIGHT, SONAR_LSBYTE_REG);
		rangeHi = rangeHi << 8;
		distRight = (double) (rangeHi+rangeLo);
		rangeHi = i2ct.read(SONAR_ADDR_HALF_RIGHT, SONAR_MSBYTE_REG);
		rangeLo = i2ct.read(SONAR_ADDR_HALF_RIGHT, SONAR_LSBYTE_REG);
		rangeHi = rangeHi << 8;
		distHalfRight = (double) (rangeHi+rangeLo);

	}
	
	public void showAllRange () {
		System.out.println ("Distance Sonar Left = "+distLeft+" cm");
		System.out.println ("Distance Sonar Half Left = "+distHalfLeft+" cm");
		System.out.println ("Distance Sonar Front = "+distFront+" cm");
		System.out.println ("Distance Sonar Half Right = "+distHalfRight+" cm");
		System.out.println ("Distance Sonar Right = "+distRight+" cm");
	}

	public static void test () {
		for (int i=1; i<=5; i++) {
			sonars mySonar = new sonars(i);
			System.out.println ("Distance Sonar "+i+" = "+mySonar.getRange());
		}	
		sonars allSonars = new sonars();
		allSonars.getAllRange();
		allSonars.showAllRange();
	}	
	
}
