package br.com.casadocodigo.loja.infra;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

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

}
