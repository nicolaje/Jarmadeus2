package jog;
import jarmadeus.PseudoFileTalker;

public class infrareds {

	private PseudoFileTalker psft;
	static int CHAN_IFRD_FRONT = 6;
	static int CHAN_IFRD_REAR = 2;
	static int CHAN_IFRD_LEFT = 4;
	static int CHAN_IFRD_RIGHT = 5;

	public static int ifrdFront ;
	public static int ifrdRear;
	public static int ifrdLeft ;
	public static int ifrdRight ;

	public infrareds() {
		psft = new PseudoFileTalker();
	}
	
	public String getSetup() {
		return  psft.read("/sys/bus/spi/devices/spi0.0/setup");
	}

	public void setSetup(String s) {
		psft.write("/sys/bus/spi/devices/spi0.0/setup",
				Integer.valueOf(s.substring(2), 16).toString());
	}

	public String getConversion() {
		return  psft.read("/sys/bus/spi/devices/spi0.0/conversion");
	}

	public void setConversion(String s) {
		psft.write("/sys/bus/spi/devices/spi0.0/conversion",
				Integer.valueOf(s.substring(2), 16).toString());
	}

	public String getAveraging() {
		return  psft.read("/sys/bus/spi/devices/spi0.0/averaging");
	}

	public void setAveraging(String s) {
		psft.write("/sys/bus/spi/devices/spi0.0/averaging",
				Integer.valueOf(s.substring(2), 16).toString());
	}

	public double getTemperature () {
		return (double) Integer.parseInt(psft.read(
				"/sys/bus/spi/devices/spi0.0/temp1_input"))/1000.0;
	}

	public int getInput(int i) {
		String chan = Integer.toString(i);
		int inp =  Integer.parseInt(psft.read("/sys/bus/spi/devices/spi0.0/in"+chan+"_input"));
		return inp;
	}

	public void getInfrareds () {
		startAdc();
		ifrdFront = getInput(CHAN_IFRD_FRONT);
		ifrdRear = getInput(CHAN_IFRD_REAR);
		ifrdLeft = getInput(CHAN_IFRD_LEFT);
		ifrdRight = getInput(CHAN_IFRD_RIGHT);
	}
	public void printInfrareds () {
		System.out.println ("Ifrared Front "+ifrdFront);
		System.out.println ("Ifrared Rear  "+ifrdRear);
		System.out.println ("Ifrared Left  "+ifrdLeft);
		System.out.println ("Ifrared Right "+ifrdRight);
		
	}

	public void startAdc() {
		setSetup("0x62");
		setAveraging("0x3C");
		setConversion("0xB9");
	}

	public static void test () {
		infrareds ir = new infrareds();
		ir.getInfrareds();
		ir.printInfrareds();
		System.out.println ("Temperature is "+ir.getTemperature ()+" degrees Celsius");
	}
}
