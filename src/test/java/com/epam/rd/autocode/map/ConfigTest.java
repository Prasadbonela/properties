package com.epam.rd.autocode.map;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;

/**
 * @author D.Kolesnikov
 */
public class ConfigTest {
	
	private Config config;
	
	@BeforeEach
	void setUp() throws IOException {
		String fileName;
		Properties props;
		
		fileName = "config.properties";
		props = new Properties();
		props.setProperty("default.filenames", "default1,default2");
		props.setProperty("key1", "A");
		props.store(new FileOutputStream(fileName), "");

		fileName = "default1.properties";
		props = new Properties();
		props.setProperty("key1", "B");
		props.store(new FileOutputStream(fileName), "");

		fileName = "default2.properties";
		props = new Properties();
		props.setProperty("key1", "C");
		props.setProperty("key2", "D");
		props.store(new FileOutputStream(fileName), "");
	}

	@AfterAll
	static void tearDown() throws IOException {
		Files.deleteIfExists(Paths.get("config.properties"));
		Files.deleteIfExists(Paths.get("default1.properties"));
		Files.deleteIfExists(Paths.get("default2.properties"));
	}

	@Test
	public void resetShouldDiscardAllChanges() throws IOException {
		config = new Config();
		config.set("key3", "E");
		
		String expected = "E";
		String actual = config.get("key3");
		assertEquals(expected, actual);

		config.reset();
		assertNull(config.get("key3"));
	}

	@Test
	public void getShouldReturnProperlyValues() throws IOException {
		config = new Config();
		String expected;
		String actual;

		expected = "A";
		actual = config.get("key1");
		assertEquals(expected, actual);

		expected = "D";
		actual = config.get("key2");
		assertEquals(expected, actual);

		config.remove("key1");
		expected = "B";
		actual = config.get("key1");
		assertEquals(expected, actual);

		config.save();
		expected = "B";
		actual = config.get("key1");
		assertEquals(expected, actual);
				
		config.set("default.filenames", "default2");
		config.save();
		config.reset();

		expected = "C";
		actual = config.get("key1");
		assertEquals(expected, actual);

		actual = config.get("key7");
		assertNull(actual);
	}
	
	@Test
	void configShouldBeProperlyInitWhenNoDefaults() throws FileNotFoundException, IOException {
		config = new Config();
		String fileName = "config.properties";
		Properties props = new Properties();
		props.setProperty("key1", "A");
		props.store(new FileOutputStream(fileName), "");
		
		config.reset();
		
		String expected;
		String actual;
		
		expected = "A";
		actual = config.get("key1");
		assertEquals(expected, actual);

		actual = config.get("key2");
		assertNull(actual);
	}

	@Test
	void configShouldBeProperlyInitWhenDefaultsIsEmpty() throws FileNotFoundException, IOException {
		config = new Config();
		String fileName = "config.properties";
		Properties props = new Properties();
		props.setProperty("key1", "A");
		props.store(new FileOutputStream(fileName), "");
		
		config.reset();
		
		String expected;
		String actual;
		
		expected = "A";
		actual = config.get("key1");
		assertEquals(expected, actual);

		actual = config.get("key2");
		assertNull(actual);
	}
	
	@Test
	void appShouldUseOnlyOptionalFromJavaUtilPackage() {
		SpoonAPI spoon = new Launcher();
		spoon.addInputResource("src/main/java/");
		spoon.buildModel();

		java.util.List<String> types = spoon.getModel()
				.getElements(new TypeFilter<>(CtTypeReference.class))
				.stream()
				.filter(r -> r.toString().startsWith("java.util."))
				.map(CtTypeReference::getQualifiedName).distinct()
				.toList();

		assertIterableEquals(
				Arrays.asList("java.util.Properties"),
				types,
				() -> "You must use exactly one type from java.util package and subpackages: "
						+ "java.util.Properties, but found:" + types);
	}
	
}
