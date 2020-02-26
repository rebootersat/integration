/*package com.siemens.soap.bfs.connector;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.siemens.becs.objects.webbfs.SearchObject;
import com.siemens.becs.system.WebBFSConnector;

public class WebBFSConnectorTest {

	private WebBFSConnector connector;

	@Before
	public void init() {
		connector = new WebBFSConnector();
	}

	@Test
	public void getSearchObject_searchObjectEmpty_shouldBeNull() {
		// connector.addSearchObject(new Search());
	}

	@Test
	public void getSearchObject_searchObjectNotNull_shouldNotBeNull() {
		connector.addSearchObject(new SearchObject());
		connector.execute();
	}

}
*/