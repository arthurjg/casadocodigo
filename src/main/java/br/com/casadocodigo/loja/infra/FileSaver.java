package br.com.casadocodigo.loja.infra;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.ObjectMetadata;

public class FileSaver {
	
	@Inject
	private HttpServletRequest request;
	
	private static final String CONTENT_DISPOSITION = "content-disposition";

	private static final String FILENAME_KEY = "filename=";
	
	public String write(String baseFolder, Part multipartFile) {
		
		String serverPath = request.getServletContext().getRealPath("/" + baseFolder);
		String fileName = extractFilename(multipartFile.getHeader(CONTENT_DISPOSITION));
		String path = serverPath + "/" + fileName;
		try {
			multipartFile.write(path);
		} catch (IOException e){
			throw new RuntimeException(e);
		}
		return baseFolder + "/" + fileName;
	}
	
	public String writeOnExternal(String baseFolder, Part multipartFile) {
		AmazonS3Client s3Client = getClientConfig();		
		
		String fileName = extractFilename(multipartFile.getHeader(CONTENT_DISPOSITION));		
		try {
			s3Client.putObject("casadocodigo", fileName, 
					multipartFile.getInputStream(), new ObjectMetadata());
			return "https://s3.amazonaws.com/casadocodigo/" + fileName + "?noAuth=true";
		} catch (AmazonClientException | IOException e){
			throw new RuntimeException(e);
		}		
	}

	private String extractFilename(String contentDisposition) {
		if (contentDisposition == null) {
			return null;
		}
		int startIndex = contentDisposition.indexOf(FILENAME_KEY);
		if (startIndex == -1) {
			return null;
		}
		String filename = contentDisposition.substring(startIndex + FILENAME_KEY.length());
		if (filename.startsWith("\"")) {
			int endIndex = filename.indexOf("\"", 1);
			if (endIndex != -1) {
				return filename.substring(1, endIndex);
			}
		} else {
			int endIndex = filename.indexOf(";");
			if (endIndex != -1) {
				return filename.substring(0, endIndex);
			}
		}
		return filename;
	}
	
	private AmazonS3Client getClientConfig() {
		AWSCredentials credentials = 
				new BasicAWSCredentials("AKIAIOSFODNN7EXAMPLE","wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY");
		AmazonS3Client newClient = new AmazonS3Client(credentials, new ClientConfiguration());
		newClient.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
		newClient.setEndpoint("http://localhost:9444/s3");
		return newClient;
	}

}
