package com.epam.rd.autocode.map;

import java.io.*;
import java.util.*;

public class Config {

	private Properties config;

	public Config() {
		this.config = new Properties();
		reset();
	}

	public void reset() {
		config.clear();
		String[] defaultFiles = getDefaultFilenames();
		for (int i = defaultFiles.length - 1; i >= 0; i--) {
			loadPropertiesFromFile(defaultFiles[i] + ".properties");
		}
		loadPropertiesFromFile("config.properties");
	}

	public String get(String key) {
		String value = config.getProperty(key);
		if (value == null) {
			String[] defaultFiles = getDefaultFilenames();
			for (String filename : defaultFiles) {
				Properties defaultProps = new Properties();
				try {
					defaultProps.load(new FileInputStream(filename + ".properties"));
					value = defaultProps.getProperty(key);
					if (value != null) break;
				} catch (IOException e) {
					// Handle exception as needed
				}
			}
		}
		return value;
	}

	public void set(String key, String value) {
		config.setProperty(key, value);
	}

	public void save() throws IOException {
		try (OutputStream outputStream = new FileOutputStream("config.properties")) {
			config.store(outputStream, null);
		}
	}

	public void remove(String key) {
		config.remove(key);
	}

	// Private methods for loading properties

	private String[] getDefaultFilenames() {
		String defaultFilenames = config.getProperty("default.filenames");
		if (defaultFilenames != null && !defaultFilenames.isEmpty()) {
			return defaultFilenames.split(",");
		}
		return new String[0];
	}

	private void loadPropertiesFromFile(String filename) {
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(filename)) {
			properties.load(inputStream);
			config.putAll(properties);
		} catch (IOException e) {
			// Handle exception as needed
		}
	}
}
