package jog;
import jarmadeus.I2CTalker;

public class compass {
	private I2CTalker i2ct;
	private final char HEAD_ADDR = 0x60;
	private final char HEAD_8BITS_ONEBYTE_REG = 0x01;
	private final char HEAD_16BITS_MSBYTE_REG = 0x02;
	private final char HEAD_16BITS_LSBYTE_REG = 0x03;

	public compass () {
		i2ct = new I2CTalker();
		i2ct.setDebugMode(false);
	}

	public double getByteHeading ()
	{
		double head = (double) (0xff & i2ct.read(HEAD_ADDR, HEAD_8BITS_ONEBYTE_REG));
		return (head*360.0)/256.;
	}

	public double getWordHeading ()
	{
		char headHi = (char) i2ct.read(HEAD_ADDR, HEAD_16BITS_MSBYTE_REG);
		char headLo = (char) i2ct.read(HEAD_ADDR, HEAD_16BITS_LSBYTE_REG);
		return  (double) ((headHi << 8) + headLo) / 10.0; 
	}

	public static void test () {
		double headB, headW;
		compass cmps = new compass();
		headB = cmps.getByteHeading();
		headW = cmps.getWordHeading();
		System.out.println ("Heading (Byte)  "+headB);
		System.out.println ("Heading (Word)  "+headW);
	}	
}
