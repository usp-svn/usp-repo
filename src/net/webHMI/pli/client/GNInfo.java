package net.webHMI.pli.client;


import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.info.InfoConfig;

public class GNInfo  extends Info {
	@Override
	protected void onShowInfo() {
		super.onShowInfo();
	}

	/**
	 * Mostra uma mensagem por um período de tempo.
	 * 
	 * @param title
	 * @param message
	 */
	public static void show(String message) {
		show(message, 25000);
	}

	
	@SuppressWarnings("null")
	public static void show(String message, int miliseconds) {

		
		GNInfo info = new GNInfo();

	
		
		InfoConfig config = null ;
		config.setDisplay(miliseconds);

		//info.setStyleName("infosign");//setBodyStyle("background-color: #f9ff4f; text-align: center; border: 0x solid black; padding: 3px; font-size: 11px; font-weight: bold;");
		//info.setFrame(false);
		//info.setAutoHeight(true);
		//info.setAnimCollapse(true);
		info.getElement().applyStyles("background-color:#f9ff4f;text-align:center;" + "border:0x solid black; padding: 3px; font-size:11px;font-weight: bold;");
		
		info.show(config);

		
		//info.setWidth(false);
		//info.setWidth(info.getOffsetWidth() + 30);
		//Point p = info.position();
		//p.setX(((p.getX() + info.getOffsetWidth()) / 2) - (info.getOffsetWidth() / 2));
		//info.setPosition(p.getX(), 0);

	}



}
