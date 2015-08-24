/*********************************************************
*                                                        * 
*   Copyright (C) Microsoft. All rights reserved.        * 
*                                                        * 
*********************************************************/ 

package microsoft.partner.csp.api.v1.samples;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Reseller {

	private String saToken;
	private String resellerCid;
	
	public Reseller(String saToken)
	{
		this.saToken = saToken;
	}

	// This method is used to retrieve the reseller cid given the reseller microsoft id, and is used to perform any transactions by the reseller
	// param name="resellerMicrosoftId", Microsoft ID of the reseller
	// returns: Reseller cid that is required to use the partner apis
	public String getResellerCid(String resellerMicrosoftId)
	{
		String requestUrl = String.format("%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/customers/get-by-identity?provider=AAD&type=tenant&tid=", resellerMicrosoftId);
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String responseBody;
		HttpGet getRequest = new HttpGet(requestUrl);

		try
		{
			String guid = UUID.randomUUID().toString();
			String authorization = String.format("%s%s", "Bearer ",saToken);
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", authorization);
			getRequest.addHeader("api-version", "2015-03-31");
			getRequest.addHeader("x-ms-correlation-id", guid);
			getRequest.addHeader("x-ms-tracking-id", guid);
			
			response = client.execute(getRequest);
			responseBody = CrestApiUtilities.parseResponse(response);
			
			JSONParser parser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
			resellerCid = (String)jsonResponse.get("id");
			client.close();
		}
		catch (ClientProtocolException e)
		{
			System.out.println("Client Protocol Exception Occured - " + e.getMessage());
		}
		catch (UnsupportedEncodingException ue)
		{
			System.out.println("Exception occured while creating the request body - " + ue.getMessage());
		} 
		catch (IOException e) 
		{
			System.out.println("IO Exception occured while getting the response - " + e.getMessage());
		} 
		catch (ParseException e) 
		{
			System.out.println("JSON Parse exception occured - " + e.getMessage());
		}
		
		System.out.println("Reseller Cid = " + resellerCid);
		return resellerCid;
	}
}
