/*********************************************************
*                                                        * 
*   Copyright (C) Microsoft. All rights reserved.        * 
*                                                        * 
*********************************************************/ 
package microsoft.partner.csp.api.v1.samples;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class AADToken {

	private String appId;
	private String key;
	private String defaultDomain;
	private String aadToken;
	
	public AADToken(){
		
		appId          = PartnerAPiCredentialsProvider.getPropertyValue("AppId");
		key            = PartnerAPiCredentialsProvider.getPropertyValue("Key");
		defaultDomain  = PartnerAPiCredentialsProvider.getPropertyValue("DefaultDomain");
	}
	public String getAADToken()
	{
		List<NameValuePair> requestBody = new ArrayList<NameValuePair>();
		String requestUrl = String.format("%s%s%s", "https://login.microsoftonline.com/", defaultDomain, "/oauth2/token?api-version=1.0");
		HttpResponse response = null;
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		String responseBody;
		
		//Request type is POST
		HttpPost postRequest = new HttpPost(requestUrl); 
		
		System.out.println("Request Url = " + requestUrl);
		try
		{
			requestBody.add(new BasicNameValuePair("grant_type", "client_credentials"));
			requestBody.add(new BasicNameValuePair("resource","https://graph.windows.net"));
			requestBody.add(new BasicNameValuePair("client_id",appId));
			requestBody.add(new BasicNameValuePair("client_secret",key));
			postRequest.setEntity(new UrlEncodedFormEntity(requestBody));
			
			System.out.println("Request Body = " + requestBody);
			
			response = client.execute(postRequest);
			responseBody = CrestApiUtilities.parseResponse(response);
			
			JSONParser parser = new JSONParser();
			JSONObject jsonResponse = (JSONObject) parser.parse(responseBody);
			aadToken = (String)jsonResponse.get("access_token");
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
		
		System.out.println("AADToken = " + aadToken);
		return aadToken;
	}
}
