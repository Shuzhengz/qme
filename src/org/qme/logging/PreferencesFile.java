package org.qme.logging;

import static org.qme.logging.QLoggingUtils.FS;
import static org.qme.logging.QLoggingUtils.QDATA;
import static org.qme.main.Main.displayError;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This gives an API for writing in and out
 * of the preferences file.
 * 
 * The preferences file looks like this:
 * 
 * squash: 2.0
 * tooltips: false
 * 
 * @author adamhutchings
 * @since pre3
 */
public final class PreferencesFile {
	
	public static final String PREFS_FILE = QDATA + FS + "preferences.txt";
	
	private static File PREFS;
	
	// Do this always, because ... yeah
	static {
		
		PREFS = new File(PREFS_FILE);
		
		// Make the prefs file if it doesn't exist
		if (!(PREFS.exists())) {
			try {
				PREFS.createNewFile();
			} catch (IOException e) {
				displayError("Error creating file " + PREFS_FILE + ". Make sure you have the necessary permissions.");
			}
		}
		
	}
	
	// This class should never be initialized
	private PreferencesFile() {}
	
	/**
	 * Get all of the preferences, in string format.
	 * @return each line from the file
	 * @author adamhutchings
	 * @since pre3
	 */
	private static String[] getPreferences() {
		
		String total = "";
		
		try {
			
			String individualLine;
			BufferedReader bufferedReader = new BufferedReader(new FileReader(PREFS));
			
			// Adding to the string
			while ((individualLine = bufferedReader.readLine()) != null) {
				total += (individualLine + "\n");
			}
			
			bufferedReader.close();
			
		} catch (IOException e) {
			displayError("For some reason, we made a preferences file and now can't read it. Huh?!");
		}
		
		return total.split("\n");
		
	}
	
	/**
	 * Get a particular preference, in string format.
	 * @author adamhutchings
	 * @throws Exception 
	 * @since pre3
	 */
	public static String getPreference(String key) throws Exception {
		
		for (String line : getPreferences()) {
			
			// 3 characters: [preference]: 0   , for example
			if (line.length() > key.length() + 3) {
				
				if (line.startsWith(key) && line.charAt(key.length()) == ':') {
					return line.substring(key.length() + 3);
				}
				
			}
			
		}
		
		throw new Exception("Preference " + key + " does not exist.");
		
	}
	
	/**
	 * Set a particular preference. Takes in preference as string.
	 * @author adamhutchings
	 * @since pre3
	 * @throws Exception
	 */
	public void setPreference(String key, String value) throws Exception {
		
		// We can just rewrite the entire file. There aren't many
		// preferences and we're not rewriting preferences 1000
		// times per second.
		
		String[] prefsCopy = getPreferences();
		
		boolean matchFound = false;
		
		for (String preference : prefsCopy) {
			
			if (preference.length() > key.length() + 3) {
			
				if (preference.startsWith(key) && preference.charAt(key.length()) == ':') {
					preference = preference.substring(0, key.length()) + ": " + value;
					matchFound = true;
					break;
				}
				
			}
			
		}
		
		if (!matchFound) {
			throw new Exception("Preference " + key + " does not exist.");
		}
		
		// Rewrite entire file
		String fileOut = "";
		for (String preference : prefsCopy) {
			fileOut += (preference + "\n");
		}
		
		FileWriter fileWriter = new FileWriter(PREFS, false);
		fileWriter.write(fileOut);
		fileWriter.close();
		
	}

}