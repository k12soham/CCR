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

	@Column(length = 5000)
	private String extractedCharacters;

	public OcrResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OcrResult(Long id, String name, byte[] imageData, String filePath, String extractedCharacters) {
		super();
		this.id = id;
		this.name = name;
		this.imageData = imageData;
		this.filePath = filePath;
		this.extractedCharacters = extractedCharacters;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getExtractedCharacters() {
		return extractedCharacters;
	}

	public void setExtractedCharacters(String extractedCharacters) {
		this.extractedCharacters = extractedCharacters;
	}

}
