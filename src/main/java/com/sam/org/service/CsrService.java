package com.sam.org.service;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.sam.org.constants.CsrConstants;
import com.sam.org.entity.Csr;
import com.sam.org.entity.CsrResponse;
import com.sam.org.utilty.ExeCommand;
import com.sam.org.utilty.FileUtility;
import com.sam.org.utilty.SubjectConstructor;
import com.sam.org.utilty.Utils;

@Service
public class CsrService {

	public FileUtility fileUtility;
	public ExeCommand exeCMD;
	public SubjectConstructor subjectConstructor;

	public CsrService() {
		fileUtility = new FileUtility();
		exeCMD = new ExeCommand();
		subjectConstructor = new SubjectConstructor();
	}

	/**
	 * Generate CSR with signature algorithm for RSA, DSA, or ECC encryption.
	 * Note**: DSA CSR requires openssl version 1.x and above.
	 * 
	 * @param csrCommonName
	 *            - common name
	 * @param csrKeySize
	 *            - key size. Key size for DSA should include sub prime i.e.
	 *            2048-256. The DSA parameter file for the key size should be
	 *            exists in the CSR directory.
	 * @param csrCountry
	 *            - country
	 * @param csrState
	 *            - state
	 * @param csrLocality
	 *            - locality or city
	 * @param csrOrg
	 *            - organization or company name
	 * @param csrOrgUnit
	 *            - organization unit or department
	 * @param signAlg
	 *            - signature algorithm i.e. sha1, sha256
	 * @param encryptionType
	 *            (can be RSA, DSA, or ECC)
	 * 
	 * @return CsrResponse
	 * @throws IOException 
	 * @throws Exception
	 */
	
	public CsrResponse genCSR(String csrCommonName, String csrKeySize, String csrCountry, String csrState,
			String csrLocality, String csrOrg, String csrOrgUnit, String signAlg, String encryptionType,String csrSAN,String csrIP,String csrURI,String csrEmail) throws IOException {
		
			String timeStamp = System.currentTimeMillis()+"";
			String csrInfo = CsrConstants.CSR_INFO+timeStamp+CsrConstants.OPEN_SSL_CONFIG; 
			String csrFile = CsrConstants.CSR_FILE + encryptionType.toUpperCase() + timeStamp+ CsrConstants.CSR_TXT_EXTENSION;
			String csrPrivKey = CsrConstants.CSR_PRIVATE_KEYS + encryptionType.toUpperCase()
					+ timeStamp + CsrConstants.CSR_PRIVATE_KEYS_EXTENSION;

			/*boolean configFileStatus = */
			subjectConstructor.constructOpensslConfig(csrCommonName, csrKeySize, csrCountry, csrState, csrLocality, csrOrg, csrOrgUnit, signAlg, encryptionType, csrSAN, csrIP, csrURI, csrEmail, csrInfo);
//			fileUtility.deleteFile(csrFile);
			Utils.wait(1);
				String newKeyParam = "";

				String opensslCommand = "";
				if (encryptionType.toUpperCase().contains(CsrConstants.ENCRYPTION_TYPE_ECC)) {
					String ecParamFile = CsrConstants.CSR_PARAMS + timeStamp + CsrConstants.PARAM_ECC;
					if (signAlg.isEmpty()) {
						signAlg = "-sha256";
					} else {
						signAlg = signAlg.startsWith("-") ? signAlg : "-" + signAlg;
					}
//					String opensslCommandECParam = "openssl ecparam -name prime256v1 -genkey -out " + ecParamFile;
					String opensslCommandECParam = "openssl ecparam -name "+csrKeySize+" -genkey -out " + ecParamFile;
					exeCMD.execCmd(opensslCommandECParam);
					Utils.wait(2);
					/*opensslCommand = "openssl req -nodes -passin pass:pass -passout pass:pass " + signAlg
							+ " -newkey ec:" + ecParamFile + " -keyout " + csrPrivKey + " -out " + csrFile + " -config "
							+ csrInfo;*/
					opensslCommand = "openssl req -nodes -utf8 -passin pass:pass -passout pass:pass " + signAlg
							+ " -newkey ec:" + ecParamFile + " -keyout " + csrPrivKey + " -out " + csrFile + " -config "
							+ csrInfo;
				} else {
					if (encryptionType.toUpperCase().contains(CsrConstants.ENCRYPTION_TYPE_DSA)) {
						if (csrKeySize.indexOf("-") < 0) {
							csrKeySize = csrKeySize + "-256";
						}
						String[] split = csrKeySize.split("-", -1);
						String dsaParamFile =  CsrConstants.CSR_PARAMS + timeStamp + CsrConstants.PARAM_DSA;
						File dsaFile = new File(dsaParamFile);
						if (!dsaFile.exists()) {
							System.out.println("DSA param file not exists");
							/*String dsaParamOpenSSLCommand = "openssl dsaparam -out " + dsaFile + " " + split[0] + "";
							exeCMD.execCmd(dsaParamOpenSSLCommand);*/
						}
						String dsaParamOpenSSLCommand = "openssl dsaparam -out " + dsaFile + " " + split[0] + "";
						exeCMD.execCmd(dsaParamOpenSSLCommand);
						newKeyParam = "dsa:" + dsaParamFile;
						if (signAlg.isEmpty()) {
							signAlg = "-sha256";
						} else {
							signAlg = signAlg.startsWith("-") ? signAlg : "-" + signAlg;
						}
					} else {
						newKeyParam = "rsa:" + csrKeySize;
						if (signAlg.isEmpty()) {
							signAlg = "-sha1";
						} else {
							signAlg = signAlg.startsWith("-") ? signAlg : "-" + signAlg;
						}
					}
					opensslCommand = "openssl req -nodes -days 365 -utf8 -nameopt utf8 -newkey " + newKeyParam + " "
							+ signAlg + " -out " + csrFile + " -keyout " + csrPrivKey + " -config " + csrInfo;
				}
				System.out.println("execute openssl command = " + opensslCommand + " for " + encryptionType);
				exeCMD.execCmd(opensslCommand);
				System.out.println("openssl command = " + opensslCommand + " is executed successfully ");
				return  new CsrResponse(fileUtility.readFile(csrFile),fileUtility.readFile(csrPrivKey),encryptionType.toUpperCase() + timeStamp);
			
	}

	
	/**
	 * @param csr : Object
	 * @return : Returns CSR String.
	 */
	public CsrResponse genCSR(Csr csr) {
		CsrResponse csrResponse = null;
		try{
			csrResponse =  genCSR(csr.getCommonName(), csr.getKeySize(), csr.getCountry(), csr.getState(), csr.getLocality(), csr.getOrganization(),
					csr.getOrganizationUnit(), csr.getSignatureAlgorithm(), csr.getKeyAlgorithm(),csr.getCsrSan(),csr.getCsrIps(),csr.getCsrUri(),csr.getCsrEmail());
		}catch (Exception e) {
			csrResponse =  new CsrResponse("Please Provide a valid CSR data\n Exception Occurred :\n" + csr, "Unable to create private Key due to CSR generated exception","No Files created");
		}
		return csrResponse;
		}
	
	public String genCert(String fileName, String validityPeriod) throws IOException{
		String certPath = CsrConstants.CERT_PATH + System.currentTimeMillis() + "cert.crt";
		String csrPath = CsrConstants.CSR_FILE + fileName + CsrConstants.CSR_TXT_EXTENSION;
		String privateKeyPath = CsrConstants.CSR_PRIVATE_KEYS + fileName + CsrConstants.CSR_PRIVATE_KEYS_EXTENSION;
		String genCertCommand = "openssl x509 -req -days "+validityPeriod+" -in "+csrPath+" -signkey "+privateKeyPath+" -out "+certPath;
		exeCMD.execCmd(genCertCommand);
		return fileUtility.readFile(certPath);
	}
	
	public String deleteFilesPresentInCsrFolder(){
		String csrData = null;
		int csrFileCount = fileUtility.deleteInnerFiles(CsrConstants.CSR_FILE);
		int csrInfoCount = fileUtility.deleteInnerFiles(CsrConstants.CSR_INFO);
		int csrParamCount = fileUtility.deleteInnerFiles(CsrConstants.CSR_PARAMS);
		int csrPrivateKeyCount = fileUtility.deleteInnerFiles(CsrConstants.CSR_PRIVATE_KEYS);
		csrData = "Csr Files Status :>> Files Deleted in CsrFile = " + csrFileCount +", Files Deleted in CsrInfo = "+csrInfoCount+", Files Deleted in CsrParams = "+csrParamCount+", Files Deleted in CsrPrivateKey = "+csrPrivateKeyCount;
		return csrData;
		}
	
	
	
	}
	




/*-- Note that in [X9.62] the curves are referred to as 'ansiX9' as
-- opposed to 'sec'.  For example secp192r1 is the same curve as
-- ansix9p192r1.

-- Note that in [PKI-ALG] the secp192r1 curve was referred to as
-- prime192v1 and the secp256r1 curve was referred to as prime256v1.

-- Note that [FIPS186-3] refers to secp192r1 as P-192, secp224r1 as
-- P-224, secp256r1 as P-256, secp384r1 as P-384, and secp521r1 as
-- P-521.*/
