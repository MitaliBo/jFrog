package com.jfrog.util;

import java.util.Comparator;

import com.jfrog.pojo.ArtifactDetails;

/*
 * Custom Comparator to sort
 * list of artifacts based on 
 * downloadedCount in decreasing order
 * @author Mitali
 */
public class ArtifactCountComp  implements Comparator<ArtifactDetails>{
 
	/**
	 * Compare DownloadCount if 2 ArtifactDetails
	 * 
	 * @param ArtifactDeatails1,ArtifactDetails2
	 * @return Integer
	 */
    @Override
    public int compare(ArtifactDetails ad1, ArtifactDetails ad2) {
        if(Integer.valueOf(ad1.getDownloadCount()) < Integer.valueOf(ad2.getDownloadCount())){
            return 1;
        } else {
            return -1;
        }
    }
}


