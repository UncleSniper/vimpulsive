package org.unclesniper.vimpulsive;

import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import com.neovim.msgpack.MessagePackRPC;

public class SocketMessagePackRPCConnection implements MessagePackRPC.Connection {

	private final Socket socket;

	private final InputStream inputStream;

	private final OutputStream outputStream;

	public SocketMessagePackRPCConnection(Socket socket) throws IOException {
		this.socket = socket;
		inputStream = socket.getInputStream();
		outputStream = socket.getOutputStream();
	}

	public Socket getSocket() {
		return socket;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void close() throws IOException {
		socket.close();
	}

}
