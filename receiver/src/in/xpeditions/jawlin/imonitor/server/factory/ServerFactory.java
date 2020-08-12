/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.factory;

import in.xpeditions.jawlin.imonitor.server.core.ImvgServer;
import in.xpeditions.jawlin.imonitor.server.intf.Server;

/**
 * @author Coladi
 * 
 */
public class ServerFactory {
	private static Server server;
	public static Server getServerObject() {
		if(ServerFactory.server == null){
			ServerFactory.server = new ImvgServer();
		}
		return ServerFactory.server;
	}
}
