package edu.ucsb.cs156.spring.earthquakes.services;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class EarthquakeQueryService {
    private Logger logger = LoggerFactory.getLogger(EarthquakeQueryService.class);

    /**
     * Retrieve Earthquake Data from USGS
     * 
     * @param lat      latitude
     * @param lon      longitude
     * @param distance minimum distance in km
     * @param minmag   minimum magnitude
     * @return json string with earthquake information
     */
    public String getJSON(double lat, double lon, int distance, double minmag) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        String uri = "https://earthquake.usgs.gov/fdsnws/event/1/query";

        String params = String.format("?format=geojson&minmagnitude=%f&maxradiuskm=%d&latitude=%f&longitude=%f", minmag,
                distance, lat, lon);

        String url = uri + params;
        logger.info("url=" + url);

        String retVal = "";
        try {
            ResponseEntity<String> re = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            MediaType contentType = re.getHeaders().getContentType();
            HttpStatus statusCode = re.getStatusCode();
            logger.info("contentType="+contentType);
            logger.info("statusCode="+statusCode);
            retVal = re.getBody();
        } catch (HttpClientErrorException e) {
            retVal = "{\"error\": \"401: Unauthorized\"}";
        }
        logger.info("from EarthquakeQueryService.getJSON: " + retVal);
        return retVal;
    }

}