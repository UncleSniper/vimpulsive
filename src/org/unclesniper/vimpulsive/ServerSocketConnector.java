package org.unclesniper.vimpulsive;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import com.neovim.msgpack.MessagePackRPC;

public class ServerSocketConnector implements Connector {

	public static final int DEFAULT_PORT = 55667;

	private InetAddress bindAddress;

	private int port;

	private ServerSocket server;

	public ServerSocketConnector() {}

	public InetAddress getBindAddress() {
		return bindAddress;
	}

	public void setBindAddress(InetAddress bindAddress) {
		this.bindAddress = bindAddress;
	}

	public void setBindAddress(String bindAddress) throws UnknownHostException {
		this.bindAddress = bindAddress == null ? null : InetAddress.getByName(bindAddress);
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public MessagePackRPC.Connection accept() throws IOException {
		if(server == null)
			server = new ServerSocket(port <= 0 ? ServerSocketConnector.DEFAULT_PORT : port,
					8, bindAddress == null ? InetAddress.getLocalHost() : bindAddress);
		return new SocketMessagePackRPCConnection(server.accept());
	}

}
