package com.sam.org.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;

import javax.websocket.server.PathParam;
import com.sam.org.entity.Csr;
import com.sam.org.entity.CsrResponse;
import com.sam.org.service.CsrService;

@RestController
public class CsrController {

	
	@Autowired
	CsrService csrService;
	
@RequestMapping ( method = RequestMethod.POST, value = "/genCsr")
public CsrResponse genCSR(@RequestBody Csr csr){
	return csrService.genCSR(csr);
	}

@RequestMapping ( method = RequestMethod.GET, value = "/getCsrFormat")
public Csr getCSRParam(){
	return new Csr("xyz.bbtest.net", "Org", "OU", "Banglore", "Karnataka", "IN", "rsa", "SHA256", "2048", "someone@gmail.com", "abc.bbtest.net", "10.212.212.156", "csrUri");
}


@RequestMapping ( method = RequestMethod.GET, value = "/downloadCsr")
public ResponseEntity<byte[]> downloadCsr(@PathParam(value = "csr") String csr){
	byte[] output = csr.getBytes();
	HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("charset", "utf-8");
    responseHeaders.setContentType(MediaType.valueOf("text/plain"));
    responseHeaders.setContentLength(output.length);
    responseHeaders.set("Content-disposition", "attachment; filename=csr.txt");
    return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
}

@RequestMapping ( method = RequestMethod.GET, value = "/downloadPrivateKey")
public ResponseEntity<byte[]> downloadPrivateKey(@PathParam(value = "privateKey") String privateKey){
	byte[] output = privateKey.getBytes();
	HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("charset", "utf-8");
    responseHeaders.setContentType(MediaType.valueOf("text/html"));
    responseHeaders.setContentLength(output.length);
    responseHeaders.set("Content-disposition", "attachment; filename=privateKey.txt");
    return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
}

@RequestMapping(method = RequestMethod.GET, value = "/clear")
public String clearCsr(){
	return csrService.deleteFilesPresentInCsrFolder();
}

@RequestMapping ( method = RequestMethod.GET, value = "/downloadCert")
public ResponseEntity<byte[]> downloadCert(@PathParam(value = "fileName") String fileName, @PathParam(value = "validityPeriod") String validityPeriod ) throws IOException {
byte[] output = csrService.genCert(fileName, validityPeriod).getBytes();
HttpHeaders responseHeaders = new HttpHeaders();
responseHeaders.set("charset", "utf-8");
responseHeaders.setContentType(MediaType.valueOf("text/html"));
responseHeaders.setContentLength(output.length);
responseHeaders.set("Content-disposition", "attachment; filename=cert.crt");
return new ResponseEntity<byte[]>(output, responseHeaders, HttpStatus.OK);
}

}


/*{
    "commonName": "commonName",
    "organization": "organization",
    "organizationUnit": "organizationUnit",
    "locality": "locality",
    "state": "state",
    "country": "IN",
    "keyAlgorithm": "rsa",
    "signatureAlgorithm": "SHA256",
    "keySize": "2048",
    "csrEmail": "csrEmail",
    "csrSan": "csrSan",
    "csrIps": "10.212.124.225",
    "csrUri": "csrUri"
}*/
