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

public class Entitlement {

	private String cToken;
	private String allEntitlements;
	
	public Entitlement(String cToken)
	{
		this.cToken = cToken;
	}
	
	public String getAllEntitlements(String customerCid) 
	{
		String requestUrl = String.format("%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", customerCid, "/Entitlements");
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		System.out.println("Request Url = " + requestUrl);
		HttpGet getRequest = new HttpGet(requestUrl.toString());
		try
		{
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", cToken));

			String guid = UUID.randomUUID().toString();
			getRequest.addHeader("x-ms-correlation-id", guid);
			getRequest.addHeader("x-ms-tracking-id", guid);
			getRequest.addHeader("api-version", "2015-03-31");
			
			response = client.execute(getRequest);
			allEntitlements = CrestApiUtilities.parseResponse(response);
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
		
		return allEntitlements;
	}
}
