package com.siemens.soap.bfs.objects.webbfs;

public class WebBFSColumn {

	private String name;
	private String value;

	public WebBFSColumn(String colNam, String value) {
		this.name = colNam;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
