package com.sam.org.utilty;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class SubjectConstructor {

	
	public boolean constructOpensslConfig(String csrCommonName, String csrKeySize, String csrCountry, String csrState,
		String csrLocality, String csrOrg, String csrOrgUnit, String signAlg, String encryptionType,String csrSAN,String csrIP,String csrURI,String csrEmail,String csrInfoFile){
		
		Writer out;
		boolean subAltStatus = ( (!csrSAN.isEmpty()) || (!csrIP.isEmpty()) || (!csrURI.isEmpty())  )? true:false; 
		try{
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				csrInfoFile), "UTF8"));

		out.append("[ req ]").append("\r\n");
		out.append("default_bits=2048").append("\r\n");
		out.append("prompt = no").append("\r\n");
		out.append("encrypt_key = no").append("\r\n");
		out.append("distinguished_name = dn").append("\r\n");
		out.append("default_md = sha256").append("\r\n");
		if(subAltStatus){
			out.append("req_extensions = req_ext").append("\r\n");
		}
		out.append("[ dn ]").append("\r\n");

		String subject = "";

		if (!csrCountry.isEmpty()) {
			subject = "C=" + csrCountry;
			out.append(subject).append("\r\n");
		}

		if (!csrCommonName.isEmpty()) {
			csrCommonName = new String(csrCommonName);
			subject = "CN=" + csrCommonName;
			out.append(subject).append("\r\n");
		}

		if (!csrOrgUnit.isEmpty()) {
			csrOrgUnit = new String(csrOrgUnit);
			subject = "0.OU=" + csrOrgUnit;
			out.append(subject).append("\r\n");
		}

		if (!csrOrg.isEmpty()) {
			csrOrg = new String(csrOrg);
			subject = "O=" + csrOrg;
			out.append(subject).append("\r\n");
		}

		if (!csrLocality.isEmpty()) {
			csrLocality = new String(csrLocality);
			subject = "L=" + csrLocality;
			out.append(subject).append("\r\n");
		}

		if (!csrState.isEmpty()) {
			csrState = new String(csrState.getBytes("UTF8"));
			subject = "ST=" + csrState;
			out.append(subject).append("\r\n");
		}
		if(!(csrEmail.isEmpty())){
			csrEmail = new String(csrEmail);
			out.append("emailAddress = "+csrEmail).append("\r\n");
		}
		if(subAltStatus){
			out.append("[ req_ext ]").append("\r\n");
			out.append("subjectAltName = @alt_names").append("\r\n");
			out.append("[alt_names]").append("\r\n");
		}
		if(!csrSAN.isEmpty()){
			String[] splitSan = csrSAN.split(",", -1);
			for(int i=0; i<splitSan.length; i++){
				out.append("DNS."+(i+1)+" = "+splitSan[i]).append("\r\n");
			}
		}
		if(!csrIP.isEmpty()){
			String[] splitIP= csrIP.split(",", -1);
			for(int i=0; i<splitIP.length; i++){
				out.append("IP."+(i+1)+" = "+splitIP[i]).append("\r\n");
			}
		}
		
		if(!csrURI.isEmpty()){
			String[] splitURI= csrURI.split(",", -1);
			for(int i=0; i<splitURI.length; i++){
				out.append("URI."+(i+1)+" = "+splitURI[i]).append("\r\n");
			}
		}
		
		out.flush();
		out.close();
		return true;
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}catch (FileNotFoundException e) {
			return false;
		}catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
