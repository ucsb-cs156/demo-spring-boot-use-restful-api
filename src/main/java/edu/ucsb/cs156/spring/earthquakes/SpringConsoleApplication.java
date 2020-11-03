package edu.ucsb.cs156.spring.earthquakes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import edu.ucsb.cs156.spring.earthquakes.services.EarthquakeQueryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;

// Bulk of code is from: https://www.baeldung.com/spring-boot-console-app

@SpringBootApplication
public class SpringConsoleApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(SpringConsoleApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");

		// For not starting webserver, see:
		// https://www.baeldung.com/spring-boot-no-web-server

		SpringApplication app = new SpringApplication(SpringConsoleApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		LOG.info("APPLICATION FINISHED");
	}

	/**
	 * This method will be executed after the application context is loaded and
	 * right before the Spring Application main method is completed.
	 */
	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");
		for (int i = 0; i < args.length; ++i) {
			LOG.info("args[{}]: {}", i, args[i]);
		}

		EarthquakeQueryService eqs = new EarthquakeQueryService();
		String json = eqs.getJSON(34.4140, -119.8489, 500, 1.0);
		LOG.info("json=",json);
	}

}