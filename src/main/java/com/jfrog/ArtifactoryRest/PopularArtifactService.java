package com.jfrog.ArtifactoryRest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.jfrog.pojo.ArtifactDetails;
import com.jfrog.pojo.Result;
import com.jfrog.pojo.ResultDetail;
import com.jfrog.util.ArtifactCountComp;

/**
 * Rest API service to produce top 2 or
 * TOP N most downloaded jars in a maven repo
 * in Artifactoy
 * @author mitali
 *
 */

@RestController
@RequestMapping("/api")
public class PopularArtifactService {

	final static Logger log = Logger.getLogger(PopularArtifactService.class);

	@Autowired
	RestTemplate restTemplate;

	@Value("${endpointArtifactDetail}")
	String endpointArtifactDetail;

	@Value("${endpointArtifactSearch}")
	String endpointArtifactSearch;

	@Value("${user}")
	String user;

	@Value("${pwd}")
	String pwd;
	
	/**
	 * Rest GET API to produce top 2 most donwloaded jars provided repoName Ex:
	 * api/getPopularTopNJars/jcenter-cache
	 * 
	 * ex: jcenter-cache/acme/groovy/acmehttp/20180225/acmehttp-20180225.jar
	 * @param PathVariable RepoName,toNjars 
	 * @return Map of Jar URI and DownloadCount
	 */
	@GetMapping(path = "/status", produces = "application/json")
	public String status() {

		
		return "Welcome to Popular Artifact Microservice!!";
	}


	/**
	 * Rest GET API to produce top 2 most donwloaded jars provided repoName Ex:
	 * api/getPopularTopNJars/jcenter-cache
	 * 
	 * ex: jcenter-cache/acme/groovy/acmehttp/20180225/acmehttp-20180225.jar
	 * @param PathVariable RepoName,toNjars 
	 * @return Map of Jar URI and DownloadCount
	 */
	@GetMapping(path = "/getPopularTopTwoJars/{repo}", produces = "application/json")
	public Map<String, String> getPopularTopTwoJars(@PathVariable("repo") final String repoName) {

		log.info("****getPopularTopTwoJars API being called *****");

		return getPopularTopNJars(repoName,2);

	}

	/**
	 * Rest GET API to produce top N(ex:3,4,etc) jars most downloaded provided
	 * repoName and count for Top Ex: api/getPopularTopNJars/jcenter-cache/5
	 * ex:jcenter-cache/acme/groovy/acmehttp/20180225/acmehttp-20180225.jar
	 * 
	 * @param PathVariable RepoName,toNjars 
	 * @return Map of Jar URI and DownloadCount
	 */

	@GetMapping(path = "/getPopularTopNJars//{repo}/{topN}", produces = "application/json")
	public Map<String, String> getPopularTopNJars(@PathVariable("repo") final String repoName,
			@PathVariable("topN") final Integer topNjars) {

		log.info("****getPopularTopNJars API being called *****");

		TreeSet<ArtifactDetails> set = new TreeSet<ArtifactDetails>(new ArtifactCountComp());
		try {

			List<String> artifactList = getArtifactsFromRepo(repoName);
			if (null != artifactList && !artifactList.isEmpty()) {
				for (String artifact : artifactList) {
					ArtifactDetails artifactDetail = getArtifactDetails(artifact);
					if (null != artifactDetail) {
						log.debug("Download count of artifact : " + artifactDetail.getUri() + "is : "
								+ artifactDetail.getDownloadCount());
						set.add(artifactDetail);
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception occured :" + e.getMessage());
		}
		
		log.debug("Download count of artifact first***** : " + set.first().getDownloadCount());

		Map<String, String> popularJarsMap = null;
		if (!set.isEmpty()) {		
			popularJarsMap = set.stream().limit(topNjars)
					.collect(Collectors.toMap(ArtifactDetails::getUri, 
							ArtifactDetails::getDownloadCount,
							(e1, e2) -> e2,
							LinkedHashMap::new));
		}


		popularJarsMap.forEach((k,v)->log.info("jarName** : " + k + " **Count** : " + v));
		return popularJarsMap;

	}

	/**
	 * Consumes Artifactory Stats Rest API to get artifact details
	 * 
	 * @param artifactPath 
	 * ex: jcenter-cache/acme/groovy/acmehttp/20180225/acmehttp-20180225.jar
	 * @return ArtifactDetails
	 */
	public ArtifactDetails getArtifactDetails(String artifactPath) {

		final String uri = endpointArtifactDetail + artifactPath + "?stats";

		HttpEntity<String> request = new HttpEntity<String>(credHeader());
		ResponseEntity<ArtifactDetails> response = restTemplate.exchange(uri, HttpMethod.GET, request,
				ArtifactDetails.class);
		ArtifactDetails details = response.getBody();
		return details;
	}

	/**
	 * Consumes Artifactory search Rest API to get all the jar Names
	 * 
	 * @param repoName ex: jcenter-cache
	 * @return List<String> , List of Artifact Names
	 */
	public List<String> getArtifactsFromRepo(String repoName) {

		final String uri = endpointArtifactSearch;
		String data = "items.find({\"repo\":{\"$eq\":\"" + repoName + "\"}})";
		HttpHeaders headers = credHeader();
		headers.setContentType(MediaType.TEXT_PLAIN);

		HttpEntity<String> request = new HttpEntity<String>(data, headers);
		restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		ResponseEntity<Result> response = restTemplate.exchange(uri, HttpMethod.POST, request, Result.class);
		List<String> artifactList = new ArrayList<String>();

		if (null != response) {
			List<ResultDetail> results = response.getBody().getResults();
			if (null != results && !results.isEmpty()) {
				String seperator = "/";
				for (ResultDetail result : results) {
					if (result.getName().endsWith(".jar")) {
						StringBuilder fullPath = new StringBuilder();
						fullPath.append(result.getRepo()).append(seperator).append(result.getPath()).append(seperator)
								.append(result.getName());

						artifactList.add(fullPath.toString());
					}
				}
			}
		}
		return artifactList;
	}

	/**
	 * Returns HttpHeader with Basic Auth
	 * 
	 * @return HttpHeader
	 */
	private HttpHeaders credHeader() {
		StringBuilder sb = new StringBuilder();
		sb.append(user);
		sb.append(":");
		sb.append(pwd);

		String plainCreds = sb.toString();
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		return headers;

	}

}
