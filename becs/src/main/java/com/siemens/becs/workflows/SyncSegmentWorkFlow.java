package com.siemens.becs.workflows;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.siemens.becs.objects.DataTable;
import com.siemens.becs.transformation.Transformation;

@Service
public class SyncSegmentWorkFlow implements Workflow {

	public static void main(String[] args) {
		new SyncSegmentWorkFlow().execute();
	}

	@Override
	public void execute() {

	}

	private List<Transformation> createTransformations() {
		List<Transformation> trnsfrm = new ArrayList<>();

		return trnsfrm;
	}

	private List<DataTable> createDataTable() {
		List<DataTable> tables = new ArrayList<DataTable>();

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
