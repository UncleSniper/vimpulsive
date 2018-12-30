package org.unclesniper.vimpulsive;

public class SessionLifecycleEvent extends VimEvent {

	private VimSession session;

	public SessionLifecycleEvent(VimSession session) {
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
