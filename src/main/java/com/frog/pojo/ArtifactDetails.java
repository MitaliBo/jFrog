package com.frog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * POJO to hold values for
 * stats of an Artifact
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtifactDetails {

	private String uri;
	private String downloadCount;
	private String lastDownloadedBy;
	private String lastDownloaded;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLastDownloaded() {
		return lastDownloaded;
	}

	public void setLastDownloaded(String lastDownloaded) {
		this.lastDownloaded = lastDownloaded;
	}

	public String getDownloadCount() {
		return downloadCount;
	}

	public void setDownloadCount(String downloadCount) {
		this.downloadCount = downloadCount;
	}

	public String getLastDownloadedBy() {
		return lastDownloadedBy;
	}

	public void setLastDownloadedBy(String lastDownloadedBy) {
		this.lastDownloadedBy = lastDownloadedBy;
	}

	public String toString() {
		return "ArtifactDetails{" + " uri='" + uri + '\'' + " downloadCount'= " + downloadCount + '\''
				+ " lastDownloadedBy'= " + lastDownloadedBy + '\'' + " lastDownloaded'= " + lastDownloaded + '\'' + '}';
	}

}
