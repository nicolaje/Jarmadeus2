package jog;
import jarmadeus.FPGATalker;

public class odometers {
	private FPGATalker fp;
	public final char ODO_RIGHT = 1;
	public final char ODO_LEFT = 2;
	private final char ODO_RIGHT_REG  = 0x0020;
	private final char ODO_LEFT_REG  = 0x0022;
	public int memOdoL, memOdoR, cntOdoL, cntOdoR;
	public int acuOdoL, acuOdoR; 

	public odometers()
	{
		fp = new FPGATalker();
		fp.setDebugMode(false);
		reset();
	}

	public void reset ()
	{
		memOdoL = getOdo(ODO_LEFT);
		memOdoR = getOdo(ODO_RIGHT);
		acuOdoL = 0;
		acuOdoR = 0;
	}

	
	public int getOdo(int side)
	{
		int valOdo = 0;
		if (side == ODO_RIGHT) {
			valOdo = fp.readShort(ODO_RIGHT_REG);
		} else if (side == ODO_LEFT){
			valOdo = fp.readShort(ODO_LEFT_REG);			
		}
		if (valOdo < 0) {
			valOdo += 65536;
		}
		if (side == ODO_RIGHT) {
			cntOdoR = valOdo-memOdoR;
			if (cntOdoR < 0)
				cntOdoR = cntOdoR + 65536;
			memOdoR = valOdo;
			acuOdoR = acuOdoR + cntOdoR;
		} else if (side == ODO_LEFT) {
			cntOdoL = valOdo-memOdoL;
			if (cntOdoL < 0)
				cntOdoL = cntOdoL + 65536;
			memOdoL = valOdo;
			acuOdoL = acuOdoL + cntOdoL;
		}
		return valOdo;
	} 

	public static void test ()
	{
		odometers odo = new odometers();
		int cntLeft,cntRight;
		int dcntLeft,dcntRight;
		cntLeft = odo.getOdo(odo.ODO_LEFT);
		cntRight = odo.getOdo(odo.ODO_RIGHT);
		System.out.println ("Counters, left = "+cntLeft+", right = "+cntRight);
		timer.wait((int)500);
		dcntLeft = odo.getOdo(odo.ODO_LEFT);
		dcntRight = odo.getOdo(odo.ODO_RIGHT);
		System.out.println ("Counters, left = "+dcntLeft+", right = "+dcntRight);
		dcntLeft -= cntLeft;
		dcntRight -= cntRight;
		System.out.println ("Delta Counters, left = "+dcntLeft+", right = "+dcntRight);
	}
}
