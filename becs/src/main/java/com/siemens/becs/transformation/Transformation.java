package com.siemens.becs.transformation;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.activity.InvalidActivityException;

public class Transformation {

	private String name;
	private ObjectInfo source;
	private ObjectInfo destination;
	List<Mapping> mapping = new ArrayList<>();

	public Transformation() {
		// TODO Auto-generated constructor stub
	}

	public Transformation(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ObjectInfo getSource() {
		return source;
	}

	public void setSource(ObjectInfo source) {
		this.source = source;
	}

	public ObjectInfo getDestination() {
		return destination;
	}

	public void setDestination(ObjectInfo destination) {
		this.destination = destination;
	}

	public List<Mapping> getMapping() {
		return mapping;
	}

	public void setMapping(List<Mapping> mapping) {
		this.mapping = mapping;
	}

	public void addMapping(Mapping mapping) {
		this.mapping.add(mapping);
	}

	public void addMapping(String srcColName, String destColName) {
		this.mapping.add(new Mapping(srcColName, destColName));
	}

	public String getConsumerColumnName(String srcColName) {
		requireNonNull(srcColName);
		return isMappingExist(srcColName) ? getDestinationColumnName(srcColName) : null;
	}

	private boolean isMappingExist(String colName) {
		boolean isExist = mapping.stream().anyMatch(mapping -> mapping.getSrcCol().equals(colName));
		if (!isExist)
			throw new NoSuchElementException("No mapping found for column " + colName);
		return isExist;
	}

	public void validate() throws InvalidActivityException {
		for (int i = 0; i < mapping.size(); i++) {
			if (!source.getColumns().contains(mapping.get(i).getSrcCol())
					|| !destination.getColumns().contains(mapping.get(i).getDestCol()))
				throw new InvalidActivityException("Tranformation is invalid " + name);
		}
	}

	public String getDestinationColumnName(String sourceColumnName) {
		String colName = null;
		for (int i = 0; i < mapping.size(); i++) {
			if (mapping.get(i).getSrcCol().equals(sourceColumnName)) {
				colName = mapping.get(i).getDestCol();
				break;
			}
		}
		return colName;
	}

	@Override
	public String toString() {
		return "Transformation [name=" + name + ", source=" + source + ", destination=" + destination + ", mapping="
				+ mapping + "]";
	}
}
