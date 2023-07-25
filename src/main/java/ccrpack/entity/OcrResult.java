package ccrpack.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class OcrResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	@Lob
    private byte[] imageData;
	
	private String filePath;
	
	@Column(length=5000)
	private String extractedCharacters;

	
	public OcrResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OcrResult(Long id, String extractedText, byte[] imageData) {
		super();
		this.id = id;
		this.imageData = imageData;
	}
	
	public String getFilePath() {
		return filePath;
	}



	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImageData() {
		return imageData;
	}



	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}

	public String getExtractedCharacters() {
		return extractedCharacters;
	}

	public void setExtractedCharacters(String extractedCharacters) {
		this.extractedCharacters = extractedCharacters;
	}
	
	
}
