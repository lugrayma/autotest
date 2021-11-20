package model;

import lombok.Data;

@Data
public class bicyclingbean {
    private int id;
    private String module;
    private String caseNumber;
    private String caseDescribe;
    private String URI;
    private String requestMethod;
    private String requestParameter;
    private String requestHeader;
    private String associationParameter;
    private String isAuto;
    private String veriftyPoints;
    //需要解析的数据类型有三种String（包含或等于）；JSONObject（需要遍历找出包含或者等于）；JSONArray（需要遍历找出包含或等于）
    private String Data_type; 
	public int getId() {
		return id;
	}
	public String getData_type() {
		return Data_type;
	}
	public void setData_type(String data_type) {
		Data_type = data_type;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getCaseDescribe() {
		return caseDescribe;
	}
	public void setCaseDescribe(String caseDescribe) {
		this.caseDescribe = caseDescribe;
	}
	public String getURI() {
		return URI;
	}
	public void setURI(String uRI) {
		URI = uRI;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getRequestParameter() {
		return requestParameter;
	}
	public void setRequestParameter(String requestParameter) {
		this.requestParameter = requestParameter;
	}
	public String getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(String requestHeader) {
		this.requestHeader = requestHeader;
	}
	public String getAssociationParameter() {
		return associationParameter;
	}
	public void setAssociationParameter(String associationParameter) {
		this.associationParameter = associationParameter;
	}
	public String getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}
	public String getVeriftyPoints() {
		return veriftyPoints;
	}
	public void setVeriftyPoints(String veriftyPoints) {
		this.veriftyPoints = veriftyPoints;
	}
    
}

