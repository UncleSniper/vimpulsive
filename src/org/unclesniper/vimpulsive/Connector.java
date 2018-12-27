package org.unclesniper.vimpulsive;

import java.io.IOException;
import com.neovim.msgpack.MessagePackRPC;

public interface Connector {

	MessagePackRPC.Connection accept() throws IOException;

}
