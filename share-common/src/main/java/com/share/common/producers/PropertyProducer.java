package com.share.common.producers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import com.share.common.annotations.Property;

public class PropertyProducer {

	private Properties properties;

	@Property
	@Produces
	public String produceString(final InjectionPoint ip) {
		return this.properties.getProperty(getKey(ip));
	}

	@Property
	@Produces
	public int produceInt(final InjectionPoint ip) {
		return Integer.valueOf(this.properties.getProperty(getKey(ip)));
	}

	@Property
	@Produces
	public boolean produceBoolean(final InjectionPoint ip) {
		return Boolean.valueOf(this.properties.getProperty(getKey(ip)));
	}

	private String getKey(final InjectionPoint ip) {
        return (ip.getAnnotated().isAnnotationPresent(Property.class) && 
                !ip.getAnnotated().getAnnotation(Property.class).value().isEmpty()) ? 
                		ip.getAnnotated().getAnnotation(Property.class).value() 
                      : ip.getMember().getName();
    }

	@PostConstruct
	public void init() {
		this.properties = new Properties();
		final InputStream streamModel = PropertyProducer.class.getResourceAsStream("share.model.cfg");
		final InputStream streamSecurityController = PropertyProducer.class.getResourceAsStream("share.security.controller.cfg");
		final InputStream streamView = PropertyProducer.class.getResourceAsStream("share.view.cfg");
		try {
			if (streamModel != null) {
				properties.load(streamModel);
			}
			if (streamSecurityController != null) {
				properties.load(streamSecurityController);
			}
			if (streamView != null) {
				properties.load(streamView);
			}
		} catch (final IOException e) {
			throw new RuntimeException("Configuration could not be loaded!");
		}
	}
}
