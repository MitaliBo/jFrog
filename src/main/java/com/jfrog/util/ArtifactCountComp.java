package com.jfrog.util;

import java.math.BigInteger;
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
    	Integer totalDownloadCountAd1= Integer.valueOf(ad1.getDownloadCount())+ Integer.valueOf(ad1.getRemoteDownloadCount());
    	Integer totalDownloadCountAd2= Integer.valueOf(ad2.getDownloadCount())+ Integer.valueOf(ad2.getRemoteDownloadCount());
    	
    	if( totalDownloadCountAd1 < totalDownloadCountAd2){
            return 1;
        } else if  (totalDownloadCountAd1 == totalDownloadCountAd2){
        	if( (new BigInteger(ad1.getLastDownloaded())).compareTo(new BigInteger(ad2.getLastDownloaded()))== -1){
        		return 1;}
        		else {
        	return -1;
        		}
        	
        }else
            return -1;
        }
    }



