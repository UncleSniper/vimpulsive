package org.unclesniper.vimpulsive.boot;

import java.io.File;

public class StaticPath implements PathSpec {

	private File path;

	public StaticPath() {}

	public StaticPath(File path) {
		this.path = path;
	}

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}

	public File buildPath() {
		return path;
	}

}
