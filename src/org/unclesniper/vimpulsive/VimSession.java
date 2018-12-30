package org.unclesniper.vimpulsive;

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import com.neovim.Neovim;
import java.util.HashMap;
import java.io.IOException;

public class VimSession implements AutoCloseable {

	private final Vimpulsive server;

	private final Neovim binding;

	private final Set<Object> hooks = new HashSet<Object>();

	private final Map<Class<?>, Object> attributes = new HashMap<Class<?>, Object>();

	private final Listeners<SessionLifecycleListener> sessionLifecycleListeners
			= new Listeners<SessionLifecycleListener>();

	private final long sessionID;

	public VimSession(Vimpulsive server, Neovim binding, long sessionID) {
		this.server = server;
		this.binding = binding;
		this.sessionID = sessionID;
	}

	public Vimpulsive getServer() {
		return server;
	}

	public Neovim getBinding() {
		return binding;
	}

	public long getSessionID() {
		return sessionID;
	}

	public void addHook(Object hook) {
		if(hook == null)
			return;
		synchronized(hooks) {
			hooks.add(hook);
		}
	}

	public void removeHook(Object hook) {
		if(hook == null)
			return;
		synchronized(hooks) {
			if(!hooks.remove(hook))
				return;
			if(hooks.isEmpty())
				hooks.notifyAll();
		}
	}

	public void waitForFinish() {
		for(;;) {
			try {
				synchronized(hooks) {
					if(hooks.isEmpty())
						return;
					hooks.wait();
				}
			}
			catch(InterruptedException ie) {}
		}
	}

	public void close() throws IOException {
		binding.close();
	}

	public <T> void setAttribute(Class<T> key, T value) {
		if(key == null)
			throw new NullPointerException("Attribute key cannot be null");
		attributes.put(key, value);
	}

	public <T> T getAttribute(Class<T> key) {
		if(key == null)
			return null;
		return key.cast(attributes.get(key));
	}

	public void addSessionLifecycleListener(SessionLifecycleListener listener) {
		sessionLifecycleListeners.add(listener);
	}

	public void removeSessionLifecycleListener(SessionLifecycleListener listener) {
		sessionLifecycleListeners.remove(listener);
	}

	void fireSessionEnding() {
		sessionLifecycleListeners.fire(SessionLifecycleListener::sessionEnding, new SessionLifecycleEvent(this));
	}

	public void echo(String message) {
		StringBuilder builder = new StringBuilder("echo ");
		VimUtils.singleString(message, builder, true);
		binding.sendVimCommand(builder.toString());
	}

	public void echom(String message) {
		StringBuilder builder = new StringBuilder("echom ");
		VimUtils.singleString(message, builder, true);
		binding.sendVimCommand(builder.toString());
	}

	public void echoerr(String message) {
		StringBuilder builder = new StringBuilder("echoerr ");
		VimUtils.singleString(message, builder, true);
		binding.sendVimCommand(builder.toString());
	}

}
