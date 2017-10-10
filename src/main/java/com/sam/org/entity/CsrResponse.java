package com.sam.org.entity;

public class CsrResponse {


	private String csr;
	private String privateKey;
	private String fileName;
	
	
	public CsrResponse() {
	}
	
	public CsrResponse(String csr, String privateKey,String FileName) {
		this.csr = csr;
		this.privateKey = privateKey;
		this.fileName = FileName;
	}

	public String getCsr() {
		return csr;
	}
	public void setCsr(String csr) {
		this.csr = csr;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String FileName) {
		this.fileName = FileName;
	}

	
}
