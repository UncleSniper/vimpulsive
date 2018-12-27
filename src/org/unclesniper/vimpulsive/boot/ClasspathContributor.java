package org.unclesniper.vimpulsive.boot;

import java.net.URL;
import java.util.List;
import java.io.IOException;

public interface ClasspathContributor {

	void addToClasspath(List<URL> classpath) throws IOException;

}
