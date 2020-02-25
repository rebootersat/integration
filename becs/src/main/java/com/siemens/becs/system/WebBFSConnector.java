package com.siemens.becs.system;

import java.util.ArrayList;
import java.util.List;

import com.siemens.becs.objects.webbfs.SearchObject;

public class WebBFSConnector {

	private WebBFSSoapEndPoint webBFSEndPoint;
	private String wsdlUrl;
	private String dbLabel;
	private String username;
	private String password;
	private List<SearchObject> search = new ArrayList<>();

	public WebBFSConnector() {
		webBFSEndPoint = new WebBFSSoapEndPoint();
		webBFSEndPoint.intIt();
	}

	public String getDbLabel() {
		return dbLabel;
	}

	public void setDbLabel(String dbLabel) {
		this.dbLabel = dbLabel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWsdlUrl() {
		return wsdlUrl;
	}

	public void setWsdlUrl(String wsdlUrl) {
		this.wsdlUrl = wsdlUrl;
	}

	public void addSearchObject(SearchObject search) {
		this.search.add(search);
	}

	public void execute() {
		search.forEach(obj -> {
			obj.execute(webBFSEndPoint);
		});
	}

}
