public class FPGATalker {

	private FPGATalker delegate;

	public FPGATalker() {
		if(Jarmadeus2.getInstance().isSimulation())delegate=new SoftFPGATalker();
		else delegate=new HardFPGATalker();
	}
}
