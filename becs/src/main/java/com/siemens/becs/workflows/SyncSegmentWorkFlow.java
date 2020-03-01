package com.siemens.becs.workflows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.siemens.becs.objects.DataTable;
import com.siemens.becs.objects.ObjectService;
import com.siemens.becs.objects.webbfs.SearchObject;
import com.siemens.becs.system.WebBFSConnector;
import com.siemens.becs.transformation.Transformation;
import com.siemens.becs.utils.xml.SegmentConfigParser;

@Service
public class SyncSegmentWorkFlow implements Workflow {

	public static void main(String[] args) {
		new SyncSegmentWorkFlow().execute();
	}

	@Override
	public void execute() {
		WebBFSConnector connector = new WebBFSConnector();
		try {
			SegmentConfigParser processor = new SegmentConfigParser(
					"C:\\Users\\SANDEEP\\git\\integration\\becs\\sync-segment.xml");

			List<ObjectService> consumerObjects = loadSearchObjectMemories(processor);
			List<Transformation> trnsfo = loadSearchObjectTransformations(processor);
			List<DataTable> dataTables = processor.getDataTablesForSearchObject("FetchPlantItems");

			SearchObject searchObject = new SearchObject(consumerObjects);
			searchObject.setName("SearchObject");
			searchObject.setMainTableName("Anl");
			searchObject.setTransformation(trnsfo);
			searchObject.setDataTables(dataTables);
			connector.addSearchObject(searchObject);
			connector.execute();

			consumerObjects.forEach(object -> {
				System.out.println(object);
			});

		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setXslt() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRabbitMQ() {
		// TODO Auto-generated method stub

	}

	private List<Transformation> loadSearchObjectTransformations(SegmentConfigParser processor) {
		List<Transformation> trnsfo = new ArrayList<>();
		trnsfo.add(processor.getTransformationByID("SearchAnlToAnlMemory"));
		trnsfo.add(processor.getTransformationByID("SearchAsrToAsrMemory"));
		return trnsfo;
	}

	private List<ObjectService> loadSearchObjectMemories(SegmentConfigParser processor) {
		List<ObjectService> consumerObjects = new ArrayList<ObjectService>();
		consumerObjects.add(processor.getMemoryByID("AnlMemory"));
		consumerObjects.add(processor.getMemoryByID("AsrMemory"));
		return consumerObjects;
	}

}
