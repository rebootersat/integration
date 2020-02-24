package com.siemens.soap.bfs.endpoints;

import java.sql.SQLException;
import java.util.List;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import bfs.soap.siemens.com.webbfs_soap_server.ArrayOfBfsObj;
import bfs.soap.siemens.com.webbfs_soap_server.ArrayOfLinkInfos;
import bfs.soap.siemens.com.webbfs_soap_server.ArrayOfObjVal;
import bfs.soap.siemens.com.webbfs_soap_server.BfsObj;
import bfs.soap.siemens.com.webbfs_soap_server.LinkInfo;
import bfs.soap.siemens.com.webbfs_soap_server.ObjVal;
import bfs.soap.siemens.com.webbfs_soap_server.ObjectFactory;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjectsResponse;
import bfs.soap.siemens.com.webbfs_soap_server.SearchObjectsResult;

@Endpoint
public class SearchEndPoints {

	private static final String NAMESPACE_URI = "http://com.siemens.soap.bfs/webbfs-soap-server";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchObjects")
	@ResponsePayload
	public SearchObjectsResponse searchObject() throws SQLException {

		SearchObjectsResponse response = new SearchObjectsResponse();
		SearchObjectsResult result = getFactory().createSearchObjectsResult();
		result.setDBServer("BENTLEYSYSTEM");
		result.setDBUser("DBUser");
		ArrayOfBfsObj arrayOfBfsObj = getFactory().createArrayOfBfsObj();
		arrayOfBfsObj.getBfsObj().add(createAnlObjectVal());
		arrayOfBfsObj.getBfsObj().add(createAsrObjectVal());
		result.setListOfBfsObj(arrayOfBfsObj);
		result.setListOfLinkInfos(createLinkInfo());
		result.setTabNam("Anl");
		result.setTotal(2);
		response.setSearchObjectsResult(result);
		return response;
	}

	private BfsObj createAnlObjectVal() {
		BfsObj bfsObj = getFactory().createBfsObj();
		bfsObj.setTabNam("Anl");
		List<ArrayOfObjVal> arrayOfObjVals = bfsObj.getObjVal();

		ArrayOfObjVal objVals = getFactory().createArrayOfObjVal();

		ObjVal objVal = getFactory().createObjVal();
		objVal.setColNam("AnlKnz");
		objVal.setValue("70ADA");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("KlaKnz");
		objVal.setValue("Motor");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("APMKla");
		objVal.setValue("MOTOR");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("AnlSta");
		objVal.setValue("2");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("AnlBez");
		objVal.setValue("Plant item designation");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("SyncToAPM");
		objVal.setValue("YES");
		objVals.getObjVal().add(objVal);
		arrayOfObjVals.add(objVals);

		objVals = getFactory().createArrayOfObjVal();

		objVal = getFactory().createObjVal();
		objVal.setColNam("AnlKnz");
		objVal.setValue("70ADAGH");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("KlaKnz");
		objVal.setValue("Valve");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("APMKla");
		objVal.setValue("VALVE");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("AnlSta");
		objVal.setValue("1");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("AnlBez");
		objVal.setValue("Plant item designation Valve");
		objVals.getObjVal().add(objVal);

		objVal = getFactory().createObjVal();
		objVal.setColNam("SyncToAPM");
		objVal.setValue("YES");
		objVals.getObjVal().add(objVal);
		arrayOfObjVals.add(objVals);
		return bfsObj;
	}

	private BfsObj createAsrObjectVal() {
		BfsObj bfsObj = getFactory().createBfsObj();
		bfsObj.setTabNam("Asr");
		List<ArrayOfObjVal> arrayOfObjVals = bfsObj.getObjVal();

		ArrayOfObjVal objVals = getFactory().createArrayOfObjVal();

		ObjVal objVal = getFactory().createObjVal();
		objVal.setColNam("AsrNum");
		objVal.setValue("10");
		objVals.getObjVal().add(objVal);
		arrayOfObjVals.add(objVals);

		objVals = getFactory().createArrayOfObjVal();

		objVal = getFactory().createObjVal();
		objVal.setColNam("AsrNum");
		objVal.setValue("20");
		objVals.getObjVal().add(objVal);
		arrayOfObjVals.add(objVals);
		return bfsObj;
	}

	private ArrayOfLinkInfos createLinkInfo() {
		ArrayOfLinkInfos arrayOfLinkInfos = getFactory().createArrayOfLinkInfos();
		LinkInfo linkInfo = getFactory().createLinkInfo();
		linkInfo.setDBServer("true");
		linkInfo.setDBUser("true");
		linkInfo.setLinkName("1");
		linkInfo.setSourceColumn("AsrNum");
		linkInfo.setSourceTable("Anl");
		linkInfo.setTargetClone("0");
		linkInfo.setTargetColumn("AsrNum");
		linkInfo.setTargetTable("Asr");
		arrayOfLinkInfos.getLinkInfo().add(linkInfo);
		return arrayOfLinkInfos;
	}

	private ObjectFactory getFactory() {
		ObjectFactory factory = new ObjectFactory();
		return factory;
	}

}
