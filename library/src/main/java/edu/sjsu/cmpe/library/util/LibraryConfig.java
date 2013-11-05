package edu.sjsu.cmpe.library.util;

import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;

public class LibraryConfig {
	private static LibraryServiceConfiguration libraryConfig;

	public static LibraryServiceConfiguration getLibraryConfig() {
		return libraryConfig;
	}

	public static void setLibraryConfig(
			LibraryServiceConfiguration libraryConfig) {
		LibraryConfig.libraryConfig = libraryConfig;
	}
}
