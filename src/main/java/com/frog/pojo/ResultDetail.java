package com.frog.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * POJO to hold values of an artifact
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultDetail {

	private String repo;
	private String path;
	private String name;
	private String size;
	private String type;

	public String getRepo() {
		return repo;
	}

	public void setRepo(String repo) {
		this.repo = repo;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString()
	{
	    return "ResultDetail{" +
	            " repo='" + repo + '\'' +
	            " path'= " + path + '\'' +
	            " name'= " + name + '\'' +
	            " size'= " + size + '\'' +
	            " type'= " + type + '\'' +
	            '}';
	}

}
