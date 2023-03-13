package com.woxsen.studentinitiatives.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "images")
public class ImageProperties {
	
	private String location;
	private String locationClassResource;
	
	
	
	public String getLocationClassResource() {
		return locationClassResource;
	}

	public void setLocationClassResource(String locationClassResource) {
		this.locationClassResource = locationClassResource;
	}

	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
}
