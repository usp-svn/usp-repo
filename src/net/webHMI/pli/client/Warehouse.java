package net.webHMI.pli.client;

public class Warehouse {
	private String abbr;
	private String desc;
	private String name;	
	
	public Warehouse(){
	
	}
	
	public Warehouse(String abbr,String desc, String name){
		this();
		setAbbr(abbr);
		setDesc(desc);
		setName(name);
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
