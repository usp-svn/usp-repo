package net.webHMI.pli.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface PLIServiceAsync {

	void getData(String pipenr, AsyncCallback<PLIData> callback);

	void updateDB(String pipenr, String binnum, String warehousecode,
			AsyncCallback<Void> callback);

//	void getUser(AsyncCallback<String> callback);
	
}
