package com.project.flik.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductDto2 {
	private Integer id;
	private String productName;
	private String companyName;
	private String productType;
	private String productSubType;
	private String productDescription;
	private MultipartFile[] productImage;
	private String[] fileSource;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductSubType() {
		return productSubType;
	}
	public void setProductSubType(String productSubType) {
		this.productSubType = productSubType;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	
	public MultipartFile[] getProductImage() {
		return productImage;
	}
	public void setProductImage(MultipartFile[] productImage) {
		this.productImage = productImage;
	}
	public String[] getFileSource() {
		return fileSource;
	}
	public void setFileSource(String[] fileSource) {
		this.fileSource = fileSource;
	}
	@Override
	public String toString() {
		return "ProductDto2 [id=" + id + ", productName=" + productName + ", companyName=" + companyName
				+ ", productType=" + productType + ", productSubType=" + productSubType + ", productDescription="
				+ productDescription + ", productImage=" + productImage + ", fileSource=" + fileSource + "]";
	}
}


//public class ProductDto2 {
//	private Integer id;
//	private String name;
//	private File file;
//	private byte[] fileSource;
//	public Integer getId() {
//		return id;
//	}
//	public void setId(Integer id) {
//		this.id = id;
//	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
//	public File getFile() {
//		return file;
//	}
//	public void setFile(File file) {
//		this.file = file;
//	}
//	public byte[] getFileSource() {
//		return fileSource;
//	}
//	public void setFileSource(byte[] fileSource) {
//		this.fileSource = fileSource;
//	}
//	@Override
//	public String toString() {
//		return "ProductDto2 [id=" + id + ", name=" + name + ", file=" + file + ", fileSource="
//				+ Arrays.toString(fileSource) + "]";
//	}
	
	

//}