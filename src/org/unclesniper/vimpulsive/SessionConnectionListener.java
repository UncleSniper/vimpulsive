package org.unclesniper.vimpulsive;

public interface SessionConnectionListener {

	void sessionConnected(SessionConnectionEvent event);

	void sessionDying(SessionConnectionEvent event);

}
