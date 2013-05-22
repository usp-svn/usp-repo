package net.webHMI.pli.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface PLIService extends RemoteService {
	PLIData getData(String pipenr);
	void updateDB(String pipenr,String binnum, String warehousecode);
//	String getUser();
}
