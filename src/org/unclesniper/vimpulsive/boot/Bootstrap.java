package org.unclesniper.vimpulsive.boot;

import java.net.URL;
import java.io.File;
import java.util.List;
import java.io.IOException;
import java.util.LinkedList;
import java.net.URLClassLoader;
import java.io.FileNotFoundException;
import org.unclesniper.ogdl.Injection;
import org.unclesniper.ogdl.ClassRegistry;
import org.unclesniper.vimpulsive.Vimpulsive;
import org.unclesniper.ogdl.ObjectDescriptionException;

public class Bootstrap {

	private static final URL[] URL_ARRAY_TEMPLATE = new URL[0];

	private final List<File> wirings = new LinkedList<File>();

	private final List<ClasspathContributor> classpath = new LinkedList<ClasspathContributor>();

	public Bootstrap() {}

	public List<File> getWirings() {
		return wirings;
	}

	public void clearWirings() {
		wirings.clear();
	}

	public void addWiring(File wiring) {
		if(wiring != null)
			wirings.add(wiring);
	}

	public void addWiring(PathSpec wiring) {
		if(wiring != null) {
			File w = wiring.buildPath();
			if(w != null)
				wirings.add(w);
		}
	}

	public List<ClasspathContributor> getClasspath() {
		return classpath;
	}

	public void clearClasspath() {
		classpath.clear();
	}

	public void addClasspath(ClasspathContributor cp) {
		if(cp != null)
			classpath.add(cp);
	}

	public void goBabyGo() throws IOException, ObjectDescriptionException {
		File wiring = null;
		for(File w : wirings) {
			if(w != null && w.isFile()) {
				wiring = w;
				break;
			}
		}
		if(wiring == null)
			throw new FileNotFoundException("None of the wiring files exist");
		List<URL> cpSink = new LinkedList<URL>();
		for(ClasspathContributor cpc : classpath) {
			if(cpc != null)
				cpc.addToClasspath(cpSink);
		}
		URL[] urls = cpSink.toArray(Bootstrap.URL_ARRAY_TEMPLATE);
		URLClassLoader loader = new URLClassLoader(urls, Bootstrap.class.getClassLoader());
		Injection injection = new Injection(new ClassRegistry());
		injection.setConstructionClassLoader(loader);
		injection.registerBuiltinStringClassMappers();
		Vimpulsive engine = injection.readDescription(wiring).getRootObjectAs(Vimpulsive.class);
		engine.goBabyGo();
	}

}
