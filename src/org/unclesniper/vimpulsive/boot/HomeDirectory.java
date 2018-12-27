package org.unclesniper.vimpulsive.boot;

import java.io.File;

public class HomeDirectory implements PathSpec {

	private static final String USER_HOME = System.getProperty("user.home");

	private static final boolean IS_WINDOWS = System.getProperty("os.name").startsWith("Windows ")
			&& File.separatorChar == '\\';

	public static final String DEFAULT_WINDOWS_TAIL = "AppData\\Roaming";

	private String windowsTail;

	public HomeDirectory() {}

	public HomeDirectory(String windowsTail) {
		this.windowsTail = windowsTail;
	}

	public String getWindowsTail() {
		return windowsTail;
	}

	public void setWindowsTail(String windowsTail) {
		this.windowsTail = windowsTail;
	}

	public File buildPath() {
		if(!HomeDirectory.IS_WINDOWS || windowsTail == null || windowsTail.trim().length() == 0)
			return new File(HomeDirectory.USER_HOME);
		return new File(HomeDirectory.USER_HOME, windowsTail);
	}

}
