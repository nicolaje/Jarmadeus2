package fr.bretagne.ensta.jog.jarmadeus.settings;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Settings {
	
	/**
	 * The file we will be looking for when instanciating the class.
	 * It is located in the current folder.
	 */
	public static final String FILE_NAME="settings.ini";
	
	public static final int HARDWARE=1;
	public static final int VIRTUAL_2D=2;
	public static final int VIRTUAL_3D=3;
	
	private static Settings settings;
	
	/**
	 * Either:
	 * - HARDWARE (talks to the Armadeus)
	 * - VIRTUAL_2D (talks to the 2D "VirtualJog2" Simulator)
	 * - VIRTUAL_3D (talks to the Blender Morse 3D simulator)
	 */
	private int mode;
	
	/**
	 * Chose if we want to actually "see" the simulator.
	 * If for whatever reason (not enough resources, working on a headless machine...)
	 * we want to simulate in text mode, it won't create the actual view.
	 * // TODO:
	 * It has to be tested with Blender Morse: can we not open the view? 
	 */
	private boolean headless;
	
	
	// TODO: handle concurrency correctly
	private Settings(){
		try {
			Configuration p=new PropertiesConfiguration(FILE_NAME);
			this.mode=p.getInt("mode");
			this.headless=p.getBoolean("headless");
		} catch (ConfigurationException e) {
			// TODO: File does not exist, or ini file not correctly written: default config!
		}
	}
	
	public static Settings getSettings(){
		if(settings==null)settings=new Settings();
		return settings;
	}
	
	public int getMode(){
		return this.mode;
	}
}
