package fr.bretagne.ensta.jog.jarmadeus;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Jarmadeus {
	
	/**
	 * The file we will be looking for when instanciating the class.
	 * It is located in the current folder.
	 */
	public static final String FILE_NAME="settings.ini";
	
	public static final int HARDWARE=1;
	public static final int VIRTUAL=2;
	
	private static Jarmadeus settings;
	
	/**
	 * Either:
	 * - HARDWARE (talks to the Armadeus)
	 * - VIRTUAL (talks to the a simulator over a socket connection, using a defined protocol)
	 */
	private int mode;
	private boolean isRealisticTiming;
	
	private String simulatorAddress;
	
	
	// TODO: handle concurrency correctly (synchronization)
	private Jarmadeus(){
		try {
			Configuration p=new PropertiesConfiguration(FILE_NAME);
			this.mode=p.getInt("mode");
			this.isRealisticTiming=p.getBoolean("realistic_timing");
		} catch (ConfigurationException e) {
			// TODO: File does not exist, or ini file not correctly written: default config!
			// throw error
			System.exit(1);
		}
	}
	
	public static Jarmadeus getInstance(){
		if(settings==null)settings=new Jarmadeus();
		return settings;
	}
	
	public int getMode(){
		return this.mode;
	}
	
	public boolean isRealisticTiming(){
		return this.isRealisticTiming;
	}
}
