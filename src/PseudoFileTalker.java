
public class PseudoFileTalker {

	private boolean debugMode;
	
	private PseudoFileTalker delegate;
	
	public PseudoFileTalker(){
		this.debugMode=false;
		if(Jarmadeus2.getInstance().isSimulation())this.delegate=new SoftPseudoFileTalker();
		else this.delegate=new HardPseudoFileTalker();
	}
	
	public String read(String pseudoFilePath){
		return delegate.read(pseudoFilePath);
	}
	
	public int write(String pseudoFilePath, String data){
		return delegate.write(pseudoFilePath, data);
	}
}
