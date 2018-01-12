package com.share.common.producers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.share.common.annotations.Property;

@ApplicationScoped
public class PropertyProducer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3424492562357233821L;

	private Properties properties;
	
	private final static Logger LOG = LoggerFactory.getLogger(PropertyProducer.class);
	
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
	public void init() throws FileNotFoundException {
		LOG.info("*************init property producer *******************");
		this.properties = new Properties();
		
		final InputStream streamModel = new FileInputStream(System.getProperty("jboss.server.config.dir") + "/share.model.cfg");
		final InputStream streamSecurityController = new FileInputStream(System.getProperty("jboss.server.config.dir") + "/share.security.controller.cfg");
		final InputStream streamView = new FileInputStream(System.getProperty("jboss.server.config.dir") + "/share.view.cfg");
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
