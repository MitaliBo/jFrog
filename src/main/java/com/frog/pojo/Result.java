package com.frog.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * POJO to hold values for
 * List of Artifacts
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
	
	 private List<ResultDetail> results;
	    

	public void setResults(List<ResultDetail> results) {
		this.results = results;
	}


		public Result() {
	    }


	    @Override
	    public String toString() {
	        return "Result{" +
	                "results='" + results +'}';
	    }

	
		public List<ResultDetail> getResults() {
			return results;
		}

}
