package com.example.eventDemo.EventDemo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;



@RestController
public class EventApiController 
{
	/*
	 * @Bean public RestTemplate getRestTemplate() {
	 * 
	 * return new RestTemplate();
	 * 
	 * }
	 */
	
	@Autowired
	RestTemplate restTemplate;

	@Value("${event_url}")
	private String eventUrl;

	@Value("${client_id}")
	private String clientId;

	@Value("${client_secret}")
	private String secretId;

	//Returning all events
	@RequestMapping(value = "/template/events")
	public ResponseEntity<String> getEventsList() 
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		// Adding query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(eventUrl)				
				.queryParam("client_id",clientId)
				.queryParam("client_secret",secretId);

		System.out.println(builder.toUriString());

		ResponseEntity<String> response = null;

		try 
		{
			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
			//response.getStatusCode();
			System.out.println("response.getStatusCode() :: "+response.getStatusCode());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}	


		return response;
	}

	//Response for specific Event ID
	@RequestMapping(value = "/template/events/{id}")
	public ResponseEntity<String> getEventForId(@PathVariable("id") String id) 
	{

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity< String> response = null;

		
		eventUrl = eventUrl + "/" +id;
		/*
		 * try { eventUrl = eventUrl + "/" +id; } catch (NullPointerException e) {
		 * System.out.println("Id should not be null!"); }
		 */

		//Adding query parameters
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(eventUrl)
				.queryParam("client_id",clientId)
				.queryParam("client_secret",secretId)
				;


		System.out.println(builder.toUriString());
		try 
		{
			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
			//response.getStatusCode();
			System.out.println("response.getStatusCode() :: "+response.getStatusCode());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}	


		return response;
	}
	
	//Search result for a string/keyword
	@RequestMapping(value = "/template/events/search/{query}") 
	public ResponseEntity<String> getEventsListForQuery(@PathVariable("query") String query) 
	{
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = null;
		
				
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(eventUrl)
				.queryParam("client_id",clientId)
				.queryParam("client_secret",secretId)
				.queryParam("q", query)
				;


		System.out.println(builder.toUriString());
		try 
		{
			response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class);
			//response.getStatusCode();
			System.out.println("response.getStatusCode() :: "+response.getStatusCode());
		} 
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}	
		
		
		return response;
	}

		
}





