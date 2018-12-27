package org.unclesniper.vimpulsive.boot;

import java.io.File;

public class StaticTail implements PathSpec {

	private PathSpec head;

	private String tail;

	public StaticTail() {}

	public StaticTail(PathSpec head, String tail) {
		this.head = head;
		this.tail = tail;
	}

	public PathSpec getHead() {
		return head;
	}

	public void setHead(PathSpec head) {
		this.head = head;
	}

	public String getTail() {
		return tail;
	}

	public void setTail(String tail) {
		this.tail = tail;
	}

	public File buildPath() {
		return new File(head.buildPath(), tail);
	}

}
