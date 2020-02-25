package com.siemens.becs.transformation;

public class Mapping {

	private String srcCol;
	private String destCol;
	
	public Mapping(String srcCol, String destCol) {
		super();
		this.srcCol = srcCol;
		this.destCol = destCol;
	}

	public String getSrcCol() {
		return srcCol;
	}

	public void setSrcCol(String srcCol) {
		this.srcCol = srcCol;
	}

	public String getDestCol() {
		return destCol;
	}

	public void setDestCol(String destCol) {
		this.destCol = destCol;
	}

}
