package org.unclesniper.vimpulsive;

import java.util.Set;
import java.util.HashSet;

public class Listeners<ListenerT> {

	public interface Fire<ListenerT, EventT> {

		void fireEvent(ListenerT listener, EventT event);

	}

	private final Set<ListenerT> listeners = new HashSet<ListenerT>();

	private volatile Set<ListenerT> cache;

	public Listeners() {}

	public void add(ListenerT listener) {
		if(listener == null)
			return;
		synchronized(listeners) {
			if(listeners.add(listener))
				cache = null;
		}
	}

	public void remove(ListenerT listener) {
		if(listener == null)
			return;
		synchronized(listeners) {
			if(listeners.remove(listener))
				cache = null;
		}
	}

	public <EventT> void fire(Fire<ListenerT, EventT> method, EventT event) {
		Set<ListenerT> local;
		synchronized(listeners) {
			if(cache == null) {
				cache = new HashSet<ListenerT>();
				cache.addAll(listeners);
			}
			local = cache;
		}
		for(ListenerT listener : local)
			method.fireEvent(listener, event);
	}

}
