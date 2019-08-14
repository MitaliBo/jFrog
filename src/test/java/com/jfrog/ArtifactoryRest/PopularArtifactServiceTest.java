package com.jfrog.ArtifactoryRest;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,properties = {"security.basic.enabled=false"})
@RunWith(SpringRunner.class)
public class PopularArtifactServiceTest {

	final static Logger log = Logger.getLogger(PopularArtifactServiceTest.class);

	@LocalServerPort
	int randomServerPort;

	@Test
	public void getPopularTopTwoJarsTest() throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/getPopularTopTwoJars/jcenter-cache";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<Map> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Map.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(2,result.getBody().size());

	}
	
	@Test
	public void getPopularTopNJarsTest() throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		final String baseUrl = "http://localhost:" + randomServerPort + "/api/getPopularTopNJars/jcenter-cache/5";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<Map> result = restTemplate.exchange(baseUrl, HttpMethod.GET, request, Map.class);
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(5,result.getBody().size());

	}

}
