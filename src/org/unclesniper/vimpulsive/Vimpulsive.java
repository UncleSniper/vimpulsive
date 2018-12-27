package org.unclesniper.vimpulsive;

import java.io.IOException;
import org.unclesniper.ogdl.Injection;
import com.neovim.msgpack.MessagePackRPC;
import org.unclesniper.ogdl.ClassRegistry;
import org.unclesniper.vimpulsive.boot.Bootstrap;
import org.unclesniper.ogdl.ObjectDescriptionException;

public class Vimpulsive {

	private Connector connector;

	public Vimpulsive() {}

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

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

	public void goBabyGo() throws IOException {
		if(connector == null)
			throw new NullPointerException("Vimpulsive.connector may not be null");
		for(;;)
			handleConnection(connector.accept());
	}

	private void handleConnection(MessagePackRPC.Connection connection) {
		//TODO
	}

}
