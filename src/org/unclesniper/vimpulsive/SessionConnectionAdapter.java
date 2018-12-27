package org.unclesniper.vimpulsive;

public abstract class SessionConnectionAdapter implements SessionConnectionListener {

	public SessionConnectionAdapter() {}

	public void sessionConnected(SessionConnectionEvent event) {}

	public void sessionDying(SessionConnectionEvent event) {}

}
