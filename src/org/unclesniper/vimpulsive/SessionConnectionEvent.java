package org.unclesniper.vimpulsive;

public class SessionConnectionEvent extends VimEvent {

	private VimSession session;

	public SessionConnectionEvent(Vimpulsive server) {
		super(server);
	}

	public SessionConnectionEvent(VimSession session) {
		super(session.getServer());
		this.session = session;
	}

	public VimSession getSession() {
		return session;
	}

	public void setSession(VimSession session) {
		this.session = session;
	}

}
