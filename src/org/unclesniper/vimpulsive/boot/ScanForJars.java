package org.unclesniper.vimpulsive.boot;

import java.net.URL;
import java.io.File;
import java.util.List;
import java.io.FileFilter;
import java.util.LinkedList;
import java.net.MalformedURLException;

public class ScanForJars implements ClasspathContributor {

	private final class ScanFilter implements FileFilter {

		public ScanFilter() {}

		public boolean accept(File file) {
			return file.isDirectory() ? recursive : file.getName().toLowerCase().endsWith(".jar");
		}

	}

	private final List<File> directories = new LinkedList<File>();

	private boolean recursive = true;

	private ScanFilter scanFilter = new ScanFilter();

	public ScanForJars() {}

	public List<File> getDirectories() {
		return directories;
	}

	public void clearDirectories() {
		directories.clear();
	}

	public void addDirectory(File directory) {
		if(directory != null)
			directories.add(directory);
	}

	public void addDirectory(PathSpec directory) {
		if(directory != null) {
			File d = directory.buildPath();
			if(d != null)
				directories.add(d);
		}
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public void addToClasspath(List<URL> classpath) throws MalformedURLException {
		for(File directory : directories) {
			if(directory.isDirectory() || directory.getName().toLowerCase().endsWith(".jar"))
				scan(directory, classpath);
		}
	}

	private void scan(File dir, List<URL> classpath) throws MalformedURLException {
		if(!dir.isDirectory()) {
			classpath.add(dir.toURI().toURL());
			return;
		}
		for(File child : dir.listFiles(scanFilter))
			scan(child, classpath);
	}

}
