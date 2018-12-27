package org.unclesniper.vimpulsive.boot;

import java.net.URL;
import java.util.List;
import java.util.LinkedList;

public class URLsOnClasspath implements ClasspathContributor {

	private final List<URL> urls = new LinkedList<URL>();

	public URLsOnClasspath() {}

	public List<URL> getUrls() {
		return urls;
	}

	public void clearUrls() {
		urls.clear();
	}

	public void addUrl(URL url) {
		if(url != null)
			urls.add(url);
	}

	public void addToClasspath(List<URL> classpath) {
		for(URL url : urls) {
			if(url != null)
				classpath.add(url);
		}
	}

}
