/*********************************************************
*                                                        * 
*   Copyright (C) Microsoft. All rights reserved.        * 
*                                                        * 
*********************************************************/ 

package microsoft.partner.csp.api.v1.samples;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SuppressWarnings("unchecked")
public class Subscriptions {

	private String saToken;

	private List<String> subscriptionIds;
	private List<String> eTags;
	private List<String> orderIds;
	private List<String> offerUris;
	private List<String> entitlementUris;
	
	private String allSubscriptions;
	private String subscription;
	private String pageOfEvents;
	
	public Subscriptions(String saToken)
	{
		this.saToken 		= saToken;
		subscriptionIds 	= new ArrayList<String>();
		eTags 				= new ArrayList<String>();
		orderIds 			= new ArrayList<String>();
		offerUris 			= new ArrayList<String>();
		entitlementUris 	= new ArrayList<String>();
	}

    // summary
    // This method is to retrieve the subscriptions of a customer bought from the reseller
    // summary
    // param name="customerCid", cid of the customer
    // param name="resellerCid", cid of the reseller
    // returns: all of the subscriptions
	public String getAllSubscriptions(String resellerCid, String customerCid)
	{
		String requestUrl = String.format("%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid,"/subscriptions?recipient_customer_id=", customerCid);
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestUrl);
		
		try
		{
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			getRequest.addHeader("x-ms-correlation-id", guid);
			getRequest.addHeader("x-ms-tracking-id", guid);
			getRequest.addHeader("api-version", "2015-03-31");

			response = client.execute(getRequest);
			allSubscriptions = CrestApiUtilities.parseResponse(response);
			parseJSONResponse(allSubscriptions);
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
		catch (org.json.simple.parser.ParseException e) 
		{
			System.out.println("JSON Parse exception occured - " + e.getMessage());
		}
		return allSubscriptions;
	}

    // summary
    // This method returns the subscription given the subscription id
    // summary
    // param name="subscriptionId", subscription id
    // param name="resellerCid", cid of the reseller
    // returns: subscription object
	public String getSubscriptionById(String resellerCid, String subscriptionId)
	{
		String requestUrl = String.format("%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/subscriptions/", subscriptionId);
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet getRequest = new HttpGet(requestUrl);
		
		try
		{
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			getRequest.addHeader("x-ms-correlation-id", guid);
			getRequest.addHeader("x-ms-tracking-id", guid);
			getRequest.addHeader("api-version", "2015-03-31");
			response = client.execute(getRequest);
			subscription = CrestApiUtilities.parseResponse(response);
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
		return subscription;
	}

	public void updateSubscriptionFriendlyName(String resellerCid, String subscriptionId, String friendlyNameOfSubscription)
	{
		String requestUrl = String.format("%s%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/subscriptions/", subscriptionId, "/update-friendly-name");
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		System.out.println("Request Url = " + requestUrl);
		HttpPost postRequest = new HttpPost(requestUrl.toString());
		try
		{
			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID()+toString();
			postRequest.addHeader("x-ms-correlation-id", guid);
			postRequest.addHeader("x-ms-tracking-id", guid);
			postRequest.addHeader("api-version", "2015-03-31");
			postRequest.addHeader("Content-Type", "application/json");
			
			JSONObject JSONfriendlyName = new JSONObject();
			JSONfriendlyName.put("friendly_name", friendlyNameOfSubscription);

			//System.out.println("Request Params = " + JSONfriendlyName);
			StringEntity se = new StringEntity(JSONfriendlyName.toString());
			postRequest.setEntity(se);
			
			response = client.execute(postRequest);
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

	public void addSubscriptionSuspension(String resellerCid, String subscriptionId)
	{
		String requestUrl = String.format("%s%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/subscriptions/", subscriptionId, "/add-suspension");
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		System.out.println("Request Url = " + requestUrl);
		HttpPost postRequest = new HttpPost(requestUrl.toString());
		try
		{
			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("Authorization",String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			postRequest.addHeader("x-ms-correlation-id", guid);
			postRequest.addHeader("x-ms-tracking-id", guid);
			postRequest.addHeader("api-version", "2015-03-31");
			postRequest.addHeader("Content-Type", "application/json");
			
			StringEntity se = new StringEntity(addSuspensionRequestBody().toString());
			postRequest.setEntity(se);
			
			response = client.execute(postRequest);
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

	public void removeSubscriptionSuspension(String resellerCid, String subscriptionId)
	{
		String requestUrl = String.format("%s%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/subscriptions/", subscriptionId, "/remove-suspension");
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		
		System.out.println("Request Url = " + requestUrl);
		HttpPost postRequest = new HttpPost(requestUrl.toString());
		try
		{
			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("Authorization",String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			postRequest.addHeader("x-ms-correlation-id", guid);
			postRequest.addHeader("x-ms-tracking-id", guid);
			postRequest.addHeader("api-version", "2015-03-31");
			postRequest.addHeader("Content-Type", "application/json");
			
			StringEntity se = new StringEntity(removeSuspensionRequestBody().toString());
			postRequest.setEntity(se);
			
			response = client.execute(postRequest);
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
	
    // summary>
    // Transitions the existing subscription and returns the new subscription object
    // summary>
    // param name="resellerCid", reseller Cid
	// param name="customerCid", customer id
    // param name="quantity", Order quantity
    // param name="newOfferUri", transitioning to the new offer
	// param name="referEntitlementUri", existing reference entitlement uri
    // returns: subscription to the new offer
	public void transitionSubscription(String resellerCid, String customerCid, String quantity, String offerUri, String referEntitlementUri )
	{
		JSONObject lineItem = new JSONObject();
		
		lineItem.put("line_item_number", "0");
		lineItem.put("offer_uri", offerUri); 
		lineItem.put("quantity", quantity);
		JSONArray entitlementUris = new JSONArray();
		entitlementUris.add(referEntitlementUri); 
		lineItem.put("reference_entitlement_uris", entitlementUris);
		
		JSONArray lineItems = new JSONArray();
		lineItems.add(lineItem);
		
		JSONObject order = new JSONObject();
		order.put("line_items", lineItems);
		order.put("recipient_customer_id", customerCid);
		
		System.out.println("Request Body - Subscription Transition = " + order);
		
		new Order(saToken).placeOrderWithSingleLineItem(resellerCid, order);
	}
	
    // summary
    // This method is to create a stream for a reseller to hear all subscription events
    // summary
    // param name="resellerCid", cid of the reseller
    // param name="streamName", name for the stream to be created
    // returns: stream information
	public void createSubscriptionStream(String resellerCid, String streamName)
	{
		String requestUrl = String.format("%s%s%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/subscription-streams/", resellerCid, "/", streamName);
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPut putRequest = new HttpPut(requestUrl.toString());
		
		try
		{
			putRequest.addHeader("Accept", "application/json");
			putRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));
			
			String guid = UUID.randomUUID().toString();
			putRequest.addHeader("x-ms-correlation-id", guid);
			putRequest.addHeader("x-ms-tracking-id", guid);
			putRequest.addHeader("api-version", "2015-03-31");
			putRequest.addHeader("Content-Type", "application/json");
			
			Date curDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"); // format - 03/21/2015 03:40:22
			
			JSONObject jsonStream = new JSONObject();
			jsonStream.put("start_time", format.format(curDate));
			jsonStream.put("page_size", 100);
			
			StringEntity se = new StringEntity(jsonStream.toString());
			putRequest.setEntity(se);
			response = client.execute(putRequest);
			
			System.out.println("Create Stream Response = " + CrestApiUtilities.parseResponse(response));
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
	
    // summary
    // This method returns the subscription events associated with a stream for the reseller
    // summary
    // param name="resellerCid", cid of the reseller
    // param name="streamName", name of the stream to be retrieved
    // returns: subscription events for the reseller along with link to mark the page as completed
	public String getSubscriptionStream(String resellerCid, String streamName)
	{
		String requestUrl = String.format("%s%s%s%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/subscription-streams/", resellerCid, "/", streamName, "/pages");
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();

		System.out.println("Request Url = " + requestUrl);
		HttpGet getRequest = new HttpGet(requestUrl);
		try
		{
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			getRequest.addHeader("x-ms-correlation-id", guid);
			getRequest.addHeader("x-ms-tracking-id", guid);
			getRequest.addHeader("api-version", "2015-03-31");
			
			response = client.execute(getRequest);
			pageOfEvents = CrestApiUtilities.parseResponse(response);
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
		return pageOfEvents;
	}
	
    // summary
    // This method is to mark the page as read inorder to move to the next page in the stream
    // summary
    // param name="completedUri", uri to mark the stream as completed
	public void markPageAsCompleted(String completedUri)
	{
		String requestUrl = String.format("%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", completedUri);
		HttpResponse response = null;
		//HttpClient client = new DefaultHttpClient();
		CloseableHttpClient client = HttpClientBuilder.create().build();

		System.out.println("Request Url = " + requestUrl);
		HttpGet getRequest = new HttpGet(requestUrl);
		try
		{
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			getRequest.addHeader("x-ms-correlation-id", guid);
			getRequest.addHeader("x-ms-tracking-id", guid);
			getRequest.addHeader("api-version", "2015-03-31");
			
			response = client.execute(getRequest);
			System.out.println("Mark Page Completed Response = " + CrestApiUtilities.parseResponse(response));
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
		//client.getConnectionManager().shutdown();
	}
	
	public List<String> getSubscriptionIds() {
		return subscriptionIds;
	}

	public List<String> geteTags() {
		return eTags;
	}

	public List<String> getOrderIds() {
		return orderIds;
	}

	public List<String> getOfferUris() {
		return offerUris;
	}

	public List<String> getEntitlementUris() {
		return entitlementUris;
	}

	private void parseJSONResponse(String responseBody) throws org.json.simple.parser.ParseException
	{
		JSONParser parser = new JSONParser();

		JSONObject response = (JSONObject) parser.parse(responseBody);

		JSONArray items = new JSONArray();
		items = (JSONArray) response.get("items");
		Iterator<JSONObject> i = items.iterator();

		while(i.hasNext())
		{
			JSONObject singleItem = i.next();
			eTags.add((String) singleItem.get("etag"));
			//System.out.println("eTag = " + eTag);
			orderIds.add((String) singleItem.get("order_id"));
			//System.out.println("orderId = " + orderId);
			subscriptionIds.add((String) singleItem.get("id"));
			//System.out.println("subscriptionId = " + subscriptionId);
			offerUris.add((String) singleItem.get("offer_uri"));
			//System.out.println("offer_uri = " + offerUri);
			JSONObject links = (JSONObject) singleItem.get("links");
			JSONObject entitlement = (JSONObject) links.get("entitlement");
			entitlementUris.add((String) entitlement.get("href"));
			//System.out.println("Entitlement Href = " + entitlement);
		}
	}
	
	private JSONObject addSuspensionRequestBody()
	{
		//JSONObject<String,Object> reason = new JSONObject<String,Object>();
		JSONObject reason = new JSONObject();
		reason.put("state", "Suspended");
		reason.put("suspension_reason", "CustomerCancellation"); // PLEASE NOTE: This is the only valid suspension reason
		reason.put("suspension_reason_comment", "Customer wanted to cancel");

		System.out.println("Request Body - Add suspension on a subscription = " + reason);

		return reason;
	}
	public JSONObject removeSuspensionRequestBody()
	{
		JSONObject reason = new JSONObject();
		reason.put("state", "Suspended");
		reason.put("suspension_reason", "CustomerCancellation"); // PLEASE NOTE: This is the only valid suspension removal reason

		System.out.println("Request Body - Remove suspension on a subscription = " + reason);

		return reason;
	}
}
