package org.unclesniper.vimpulsive;

public abstract class VimEvent {

	private final Vimpulsive server;

	public VimEvent(Vimpulsive server) {
		this.server = server;
	}

	public Vimpulsive getServer() {
		return server;
	}

}
