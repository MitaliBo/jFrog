package com.jfrog.ArtifactoryRest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.frog.pojo.ArtifactDetails;


@RunWith(SpringRunner.class)
@RestClientTest(value={PopularArtifactService.class})
public class ConsumeArtifactoryServiceTest
{

	@Autowired
    private PopularArtifactService service;

    @Test
    public void getArtifactsFromRepoTest() throws Exception {

        List<String> artifacts = this.service.getArtifactsFromRepo("gradle-dev");
        assertNotNull(artifacts);
    }
    
    @Test
    public void getArtifactDetailsTest() throws Exception {
    	
    	
    	String path="jcenter-cache/junit/junit/4.11/junit-4.11.jar";
    	String expectedUri="http://104.155.149.184:80/artifactory/jcenter-cache/junit/junit/4.11/junit-4.11.jar";
        ArtifactDetails details = this.service.getArtifactDetails(path);
        assertNotNull(details);
        assertEquals(expectedUri, details.getUri());
    }
  
}
