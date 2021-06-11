package com.edost.urlshortner;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "url_store")
public class UrlModel {

	@DynamoDBHashKey
	private String urlId;
	
	@DynamoDBAttribute
	private String longUrl;
	
	@DynamoDBAttribute
	private String dateCreated;
	
	@DynamoDBAttribute
	private String dateExpire;

	public UrlModel() {
		super();
	}

	public UrlModel(String urlId, String longUrl, String dateCreated, String dateExpire) {
		super();
		this.urlId = urlId;
		this.longUrl = longUrl;
		this.dateCreated = dateCreated;
		this.dateExpire = dateExpire;
	}

	public String getUrlId() {
		return urlId;
	}

	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateExpire() {
		return dateExpire;
	}

	public void setDateExpire(String dateExpire) {
		this.dateExpire = dateExpire;
	}

	@Override
	public String toString() {
		return "UrlModel [urlId=" + urlId + ", longUrl=" + longUrl + ", dateCreated="
				+ dateCreated + ", dateExpire=" + dateExpire + "]";
	}
	
}
