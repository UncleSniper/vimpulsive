package org.unclesniper.vimpulsive;

import com.neovim.Neovim;
import java.io.IOException;
import org.unclesniper.ogdl.Injection;
import com.neovim.msgpack.MessagePackRPC;
import org.unclesniper.ogdl.ClassRegistry;
import java.util.concurrent.atomic.AtomicLong;
import org.unclesniper.vimpulsive.boot.Bootstrap;
import org.unclesniper.ogdl.ObjectDescriptionException;

public class Vimpulsive {

	public static class Handler extends Thread {

		private final VimSession session;

		public Handler(VimSession session) {
			this.session = session;
		}

		public void run() {
			try(VimSession s = session) {
				s.waitForFinish();
				s.fireSessionEnding();
			}
			catch(IOException ioe) {
				ioe.printStackTrace();
			}
			finally {
				session.getServer().fireSessionDying(session);
			}
		}

	}

	private Connector connector;

	private final Listeners<SessionConnectionListener> sessionConnectionListeners
			= new Listeners<SessionConnectionListener>();

	private final AtomicLong nextSessionID = new AtomicLong();

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
		VimSession session = new VimSession(this, Neovim.connectTo(connection), nextSessionID.getAndIncrement());
		session.echom("Vimpulsive at your beck and call.");
		fireSessionConnected(session);
		new Handler(session).start();
	}

	public void addService(VimService service) {
		if(service != null)
			service.bind(this);
	}

	public void addSessionConnectionListener(SessionConnectionListener listener) {
		sessionConnectionListeners.add(listener);
	}

	public void removeSessionConnectionListener(SessionConnectionListener listener) {
		sessionConnectionListeners.remove(listener);
	}

	private void fireSessionConnected(VimSession session) {
		sessionConnectionListeners.fire(SessionConnectionListener::sessionConnected,
				new SessionConnectionEvent(session));
	}

	private void fireSessionDying(VimSession session) {
		sessionConnectionListeners.fire(SessionConnectionListener::sessionDying,
				new SessionConnectionEvent(session));
	}

}
