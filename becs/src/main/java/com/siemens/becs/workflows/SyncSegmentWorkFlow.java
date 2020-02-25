package com.siemens.becs.workflows;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.siemens.becs.objects.memory.Memory;
import com.siemens.becs.objects.webbfs.WeBFSDataTable;
import com.siemens.becs.objects.webbfs.SearchObject;
import com.siemens.becs.system.WebBFSConnector;
import com.siemens.becs.transformation.BfsToMemoryTransformation;
import com.siemens.becs.transformation.Mapping;
import com.siemens.becs.transformation.Transformation;

@Service
public class SyncSegmentWorkFlow implements Workflow {

	public static void main(String[] args) {
		new SyncSegmentWorkFlow().execute();
	}

	@Override
	public void execute() {
		WebBFSConnector connector = new WebBFSConnector();
		SearchObject search = new SearchObject();
		search.setName("FetchPlantItems");
		search.setDataTables(createDataTable());
		List<Transformation> transformations = createTransformations();
		search.setTransformation(transformations);
		connector.addSearchObject(search);
		connector.execute();

		transformations.forEach(trns -> {
			BfsToMemoryTransformation tr = (BfsToMemoryTransformation) trns;
			Memory mem = (Memory) tr.getDestination();
			System.out.println(mem);
			// .toString();
		});
	}

	private List<Transformation> createTransformations() {
		List<Transformation> trnsfrm = new ArrayList<>();

		Memory memory = new Memory("anlknz", "klaknz", "apmkla", "anlsta", "anlbez", "sync_to_apm");
		memory.setName("AnlMemory");

		Transformation searchToMemory = new BfsToMemoryTransformation();
		searchToMemory.setName("Anl");
		searchToMemory.addMapping(new Mapping("AnlKnz", "anlknz"));
		searchToMemory.addMapping(new Mapping("KlaKnz", "klaknz"));
		searchToMemory.addMapping(new Mapping("APMKla", "apmkla"));
		searchToMemory.addMapping(new Mapping("AnlSta", "anlsta"));
		searchToMemory.addMapping(new Mapping("AnlBez", "anlbez"));
		searchToMemory.addMapping(new Mapping("SyncToAPM", "sync_to_apm"));
		searchToMemory.setDestination(memory);
		trnsfrm.add(searchToMemory);

		memory = new Memory("asrnum");
		memory.setName("AsrMemory");
		searchToMemory = new BfsToMemoryTransformation();
		searchToMemory.setName("Asr");
		searchToMemory.addMapping(new Mapping("AsrNum", "asrnum"));
		searchToMemory.setDestination(memory);
		trnsfrm.add(searchToMemory);

		return trnsfrm;
	}

	private List<WeBFSDataTable> createDataTable() {
		List<WeBFSDataTable> tables = new ArrayList<WeBFSDataTable>();
		WeBFSDataTable table = new WeBFSDataTable("Anl");
		table.addColumn("AnlKnz");
		table.addColumn("KlaKnz");
		table.addColumn("APMKla");
		table.addColumn("AnlSta");
		table.addColumn("AnlBez");
		table.addColumn("SyncToAPM");
		tables.add(table);

		table = new WeBFSDataTable("Asr");
		table.addColumn("AsrNum");
		tables.add(table);
		return tables;
	}

	@Override
	public void setXslt() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRabbitMQ() {
		// TODO Auto-generated method stub

	}

}
