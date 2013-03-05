import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * @author Olivier Reynet
 * @date 04/12/09
 */
/**
 * PseudoFileTalker is used to read and write linux pseudofiles.<br>
 * These pseudofiles are created by a linux module. Each pseudofile generally stands for a device register.<br>
 * Two methods are basically provided : read and write. 
 * A debug mode is avaliable.
 */
public class HardPseudoFileTalker extends PseudoFileTalker{

	/**
	 * @param pseudoFilePath   the path of the pseudofile to read 
	 * @return the string read in the pseudofile
	 */
	@Override
	public String read(String pseudoFilePath)
	{
		BufferedReader pseudoReader;
		String s = "";
		try {
			pseudoReader = new BufferedReader(new FileReader(pseudoFilePath));
			s=pseudoReader.readLine();
			pseudoReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}
	
    /**
	* @param pseudoFilePath the path of the pseudofile to write 
	* @param data  the string to write in the pseudofile
	* @return an integer to account for the success of the writing. In case of failure, this integer is negative.
	*/
	@Override
	public int write(String pseudoFilePath, String data)
	{
		FileWriter pseudoWriter;
		int ret=0;
		try {
			pseudoWriter = new FileWriter(pseudoFilePath);
			pseudoWriter.write(data);
			pseudoWriter.flush();
			pseudoWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
			ret=-1;
		}
		return ret; 
	}
}
