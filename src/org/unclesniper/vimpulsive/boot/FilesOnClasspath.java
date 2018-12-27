package org.unclesniper.vimpulsive.boot;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.LinkedList;
import java.net.MalformedURLException;

public class FilesOnClasspath implements ClasspathContributor {

	private final List<File> files = new LinkedList<File>();

	public FilesOnClasspath() {}

	public List<File> getFiles() {
		return files;
	}

	public void clearFiles() {
		files.clear();
	}

	public void addFile(File file) {
		if(file != null)
			files.add(file);
	}

	public void addFile(PathSpec file) {
		if(file != null) {
			File f = file.buildPath();
			if(f != null)
				files.add(f);
		}
	}

	public void addToClasspath(List<URL> classpath) throws MalformedURLException {
		for(File file : files) {
			if(file != null)
				classpath.add(file.toURI().toURL());
		}
	}

}
