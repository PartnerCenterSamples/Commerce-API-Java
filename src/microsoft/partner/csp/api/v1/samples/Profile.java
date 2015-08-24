/*********************************************************
*                                                        * 
*   Copyright (C) Microsoft. All rights reserved.        * 
*                                                        * 
*********************************************************/ 

package microsoft.partner.csp.api.v1.samples;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SuppressWarnings("unchecked")
public class Profile {

	private String customerId;
	private String customerToken;
	private String profileId;
	private String etag;
	private String customerProfile;
	
	public Profile(String customerId, String customerToken)
	{
		this.customerId = customerId;
		this.customerToken = customerToken;
	}
	public String getCustomerProfile()
	{
		String requestUrl = String.format("%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", customerId, "/profiles");
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestUrl);
		
		try
		{
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", String.format("%s%s", "Bearer ",customerToken));
			getRequest.addHeader("x-ms-correlation-id", UUID.randomUUID().toString());
			getRequest.addHeader("x-ms-tracking-id", UUID.randomUUID().toString());
			getRequest.addHeader("api-version", "2015-03-31");
			
			response = client.execute(getRequest);
			customerProfile = CrestApiUtilities.parseResponse(response);
			System.out.println("Customer Profile = " + customerProfile);
			parseCustomerProfileJSON();
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
		return customerProfile;
	}
	private void parseCustomerProfileJSON() throws ParseException
	{
		JSONParser parser = new JSONParser();
		JSONObject response = (JSONObject) parser.parse(customerProfile);
		JSONArray items = (JSONArray)response.get("items");
		Iterator<JSONObject> i = items.iterator();
		
		JSONObject item = i.next();
		
		profileId = (String)item.get("id");
		etag = (String)item.get("etag");
		
		System.out.println("Profile Id = " + profileId);
		System.out.println("ETag = " + etag);
	}
	public String getProfileId()
	{
		return profileId;
	}
	
	public String getETag()
	{
		return etag;
	}
	public void updateCustomerProfile()
	{
		String requestUrl = String.format("%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", customerId, "/profiles");
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPut putRequest = new HttpPut(requestUrl);
		
		try
		{
			putRequest.addHeader("Accept", "application/json");
			putRequest.addHeader("Authorization", String.format("%s%s", "Bearer ",customerToken));
			
			String guid = UUID.randomUUID().toString();
			putRequest.addHeader("x-ms-correlation-id", guid);
			putRequest.addHeader("x-ms-tracking-id", guid);
			putRequest.addHeader("api-version", "2015-03-31");
			putRequest.addHeader("Content-Type", "application/json");
			putRequest.addHeader("If-Match", getETag());
			
			JSONObject profileJson = getUpdatedCustomerProfileJSONObject();
			StringEntity se = new StringEntity(profileJson.toString());
			putRequest.setEntity(se);
			
			response = client.execute(putRequest);
			CrestApiUtilities.parseResponse(response);
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
	}
	
	private JSONObject getUpdatedCustomerProfileJSONObject()
	{
		JSONObject customerAddress = new JSONObject();
		customerAddress.put("first_name", "First Name Updated");
		customerAddress.put("last_name", "Last Name Updated");
		customerAddress.put("address_line1", "Coffee Street");
		customerAddress.put("address_line3", "");
		customerAddress.put("city", "Bellevue");
		customerAddress.put("region", "WA");
		customerAddress.put("postal_code", "98007");
		customerAddress.put("country", "US");
		
		JSONObject customerProfile = new JSONObject();
		customerProfile.put("email", "admin@sonoto.com");
		customerProfile.put("type", "organization");
		customerProfile.put("company_name", "Company Name Changed");
		customerProfile.put("culture", "en-US");
		customerProfile.put("language", "en");
		customerProfile.put("id", getProfileId());
		customerProfile.put("default_address", customerAddress);
		
		System.out.println("Profile update Request Body = " + customerProfile);
		return customerProfile;
	}
}
