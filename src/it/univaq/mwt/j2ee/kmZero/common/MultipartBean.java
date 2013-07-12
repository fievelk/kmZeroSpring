package it.univaq.mwt.j2ee.kmZero.common;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;
 
public class MultipartBean {
 
	private List<MultipartFile> files;

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	
}
