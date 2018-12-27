package org.unclesniper.vimpulsive;

import java.io.IOException;
import org.unclesniper.ogdl.Injection;
import org.unclesniper.ogdl.ClassRegistry;
import org.unclesniper.vimpulsive.boot.Bootstrap;
import org.unclesniper.ogdl.ObjectDescriptionException;

public class Vimpulsive {

	public static void main(String[] args) throws IOException, ObjectDescriptionException {
		if(args.length != 1) {
			System.err.println("Usage: java " + Vimpulsive.class.getName() + " <boot-wiring-file>");
			System.exit(1);
		}
		Injection injection = new Injection(new ClassRegistry());
		injection.registerBuiltinStringClassMappers();
		Bootstrap boot = injection.readDescription(args[0]).getRootObjectAs(Bootstrap.class);
		boot.goBabyGo();
	}

	public void goBabyGo() {
		System.out.println("Hello, world!");
	}

}
