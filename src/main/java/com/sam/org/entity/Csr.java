package com.sam.org.entity;

public class Csr {

	private String commonName;
	private String organization;
	private String organizationUnit;
	private String locality;
	private String state;
	private String country;
	
	private String keyAlgorithm;
	private String signatureAlgorithm;
	private String keySize;
	
	private String csrEmail;
	private String csrSan;
	private String csrIps;
	private String csrUri;
	
	public Csr(String commonName, String organization, String organizationUnit, String locality, String state,
			String country, String keyAlgorithm, String signatureAlgorithm, String keySize, String csrEmail,
			String csrSan, String csrIps, String csrUri) {
		this.commonName = commonName;
		this.organization = organization;
		this.organizationUnit = organizationUnit;
		this.locality = locality;
		this.state = state;
		this.country = country;
		this.keyAlgorithm = keyAlgorithm;
		this.signatureAlgorithm = signatureAlgorithm;
		this.keySize = keySize;
		this.csrEmail = csrEmail;
		this.csrSan = csrSan;
		this.csrIps = csrIps;
		this.csrUri = csrUri;
	}

	public Csr() {
	}
	
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getOrganizationUnit() {
		return organizationUnit;
	}
	public void setOrganizationUnit(String organizationUnit) {
		this.organizationUnit = organizationUnit;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}
	public void setKeyAlgorithm(String keyAlgorithm) {
		this.keyAlgorithm = keyAlgorithm;
	}
	public String getSignatureAlgorithm() {
		return signatureAlgorithm;
	}
	public void setSignatureAlgorithm(String signatureAlgorithm) {
		this.signatureAlgorithm = signatureAlgorithm;
	}
	public String getKeySize() {
		return keySize;
	}
	public void setKeySize(String keySize) {
		this.keySize = keySize;
	}
	public String getCsrEmail() {
		return csrEmail;
	}
	public void setCsrEmail(String csrEmail) {
		this.csrEmail = csrEmail;
	}
	public String getCsrSan() {
		return csrSan;
	}
	public void setCsrSan(String csrSan) {
		this.csrSan = csrSan;
	}
	public String getCsrIps() {
		return csrIps;
	}
	public void setCsrIps(String csrIps) {
		this.csrIps = csrIps;
	}
	public String getCsrUri() {
		return csrUri;
	}
	public void setCsrUril(String csrUri) {
		this.csrUri = csrUri;
	}
	@Override
	public String toString(){
		return "CSR {"
				+ "\n Key Algorithm          = "+ keyAlgorithm
				+ "\n Signature Algorithm = " + signatureAlgorithm
				+ "\n Key Size                   = " + keySize
				+ "\n Common Name        = " + commonName
				+ "\n Organization             = " + organization
				+ "\n Organizational Unit  = " + organizationUnit
				+ "\n Country                    = " + country
				+ "\n State                         = " + state
				+ "\n Locality                    = " + locality
				+ "\n SAN'S                     = " + csrSan
				+ "\n Email                        = " + csrEmail
				+ "\n IP's                           = " + csrIps
				+ "\n URI's                        = " + csrUri
				+
				"\n}";
	}
	
}
