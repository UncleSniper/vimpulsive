package org.unclesniper.vimpulsive.boot;

import java.io.File;

public class WorkingDirectory implements PathSpec {

	private static final String USER_DIR = System.getProperty("user.dir");

	public WorkingDirectory() {}

	public File buildPath() {
		return new File(WorkingDirectory.USER_DIR);
	}

}
