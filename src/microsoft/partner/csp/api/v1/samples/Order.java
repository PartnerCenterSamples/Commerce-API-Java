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
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@SuppressWarnings("unchecked")
public class Order {

	private String saToken;
	private String allOrders;
	private String order;
	
	public Order(String saToken)
	{
		this.saToken = saToken;
	}


	// summary
	// This method is used to place order on behalf of a customer by a reseller
	// summary
	// param name="resellerCid", the cid of the reseller
	// param name="order", new Order that can contain multiple line items
	// param name="customerCid", customer cid for who the order is being placed
	// param name="offerUri", offer_uri of the product 
	// param name="quantity", order quantity
	// returns: order information that has references to the subscription uris and entitlement uri for the line items
	public void placeOrderWithSingleLineItem(String resellerCid, String customerCid, String offerUri, String quantity)
	{
		String requestUrl = String.format("%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/orders");
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String responseBody;

		System.out.println("Request Url = " + requestUrl);
		HttpPost postRequest = new HttpPost(requestUrl);
		try
		{
			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			postRequest.addHeader("x-ms-correlation-id", guid);
			postRequest.addHeader("x-ms-tracking-id", guid);
			postRequest.addHeader("api-version", "2015-03-31");
			postRequest.addHeader("Content-Type", "application/json");

			StringEntity se = new StringEntity(populateOrderWithSingleLineItem(customerCid, offerUri, quantity).toString());
			
			postRequest.setEntity(se);
			response = client.execute(postRequest);
			responseBody = CrestApiUtilities.parseResponse(response);
			System.out.println("Response Body = " + responseBody);
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
	// This method is used to place order on behalf of a customer by a reseller
	// summary
	// param name="resellerCid", the cid of the reseller
	// param name="order", new Order item
	// returns: order information that has references to the subscription uris and entitlement uri for the line items
	public void placeOrderWithSingleLineItem(String resellerCid, JSONObject order)
	{
		String requestUrl = String.format("%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/orders");
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String responseBody;

		System.out.println("Request Url = " + requestUrl);
		HttpPost postRequest = new HttpPost(requestUrl);
		try
		{
			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			postRequest.addHeader("x-ms-correlation-id", guid);
			postRequest.addHeader("x-ms-tracking-id", guid);
			postRequest.addHeader("api-version", "2015-03-31");
			postRequest.addHeader("Content-Type", "application/json");

			StringEntity se = new StringEntity(order.toString());
			
			postRequest.setEntity(se);
			response = client.execute(postRequest);
			responseBody = CrestApiUtilities.parseResponse(response);
			System.out.println("Response Body = " + responseBody);
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
	// This method is used to place an order with multiple line items on behalf of a customer by a reseller
	// PLEASE NOTE: You cannot place a single order that includes both (Office 365 or CRM Online) and Azure line items
	// summary
	// param name="resellerCid", the cid of the reseller
	// Internal param name="order", new Order that contains multiple line items
	// param name="customerCid", customer cid for who the order is being placed
	// returns: order information that has references to the subscription uris and entitlement uri for the line items
	public void placeOrderWithMultieLineItems(String resellerCid, String customerCid)
	{
		String requestUrl = String.format("%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/orders");
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String responseBody;

		System.out.println("Request Url = " + requestUrl);
		HttpPost postRequest = new HttpPost(requestUrl);
		try
		{
			postRequest.addHeader("Accept", "application/json");
			postRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));

			String guid = UUID.randomUUID().toString();
			postRequest.addHeader("x-ms-correlation-id", guid);
			postRequest.addHeader("x-ms-tracking-id", guid);
			postRequest.addHeader("api-version", "2015-03-31");
			postRequest.addHeader("Content-Type", "application/json");

			StringEntity se = new StringEntity(PopulateOrderWithMultipleLineItems(customerCid).toString());
			postRequest.setEntity(se);

			response = client.execute(postRequest);
			responseBody = CrestApiUtilities.parseResponse(response);

			System.out.println("Response Body = " + responseBody);
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
	// This method retrieves all the orders placed for a customer by a reseller
	// summary
	// param name="customerCid", cid of the customer
	// param name="resellerCid", cid of the reseller
	// returns: response that contains all orders
	public String getAllOrders(String resellerCid, String customerCid)
	{
		String requestUrl = String.format("%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/orders?recipient_customer_id=", customerCid);
		System.out.println("Request Url = " + requestUrl);
		
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
			allOrders = CrestApiUtilities.parseResponse(response);

			System.out.println("Response Body = " + allOrders);
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
		return allOrders;
	}
	
    // summary
    // This method is used to increase or decrease quantity of an existing order by a reseller
    // summary
    // param name="resellerCid", the cid of the reseller
    // param name="orderId", order_id of the existing order
    // param name="eTag", To get the most recent eTag, retrieve the order and persist the same
	// param name="offerUri", offer_uri of the order
	// param name="entitlementUri", Pass the subscription.links [“entitlement”].href from subscription to reference_entitlement_uris on the line_items property in the PATCH orders payload
	// param name="quantity", quantity of the order to be updated
	public void patchOrder(String resellerCid, String orderId, String eTag, String offerUri, String entitlementUri, String quantity)
	{
		String requestUrl = String.format("%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/orders/", orderId);
		System.out.println("Request Url = " + requestUrl);
		
		HttpResponse response = null;
		CloseableHttpClient client = HttpClientBuilder.create().build();
		String responseBody;
		HttpPatch patchRequest = new HttpPatch(requestUrl);
		
		try
		{
			patchRequest.addHeader("Accept", "application/json");
			patchRequest.addHeader("Authorization", String.format("%s%s", "Bearer ", saToken));
			
			String guid = UUID.randomUUID().toString();
			patchRequest.addHeader("x-ms-correlation-id", guid);
			patchRequest.addHeader("api-version", "2015-03-31");
			patchRequest.addHeader("Content-Type", "application/json");
			//patchRequest.addHeader("If-Match", eTag);
			
			StringEntity se = new StringEntity(patchOrderRequestBody(offerUri, entitlementUri, quantity).toString());
			patchRequest.setEntity(se);

			response = client.execute(patchRequest);
			responseBody = CrestApiUtilities.parseResponse(response);
			System.out.println("Response Body = " + responseBody);
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
    // This method returns the order given an orderId for a reseller
    // summary
    // param name="orderId", id of the order
    // param name="resellerCid", cid of the reseller
    // returns: order object
	public String getOrder(String resellerCid, String orderId)
	{
		String requestUrl = String.format("%s%s%s%s%s", PartnerAPiCredentialsProvider.getPropertyValue("ApiEndpoint"), "/", resellerCid, "/orders/", orderId);
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
			order = CrestApiUtilities.parseResponse(response);

			System.out.println("Response Body = " + order);
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
		return order;
	}
	
	// summary
	// Populates an order with single line item
	// summary
	// param name="customerCid", cid of the customer
	// param name="offerUri", off_uri of the product
	// param name="quantity", product quantity
	// returns: single line item order
	private JSONObject populateOrderWithSingleLineItem(String customerCid, String offerUri, String quantity) 
	{
		JSONObject lineItem = new JSONObject();
		lineItem.put("line_item_number", "0");

		lineItem.put("offer_uri", offerUri);
		lineItem.put("quantity", quantity);

		JSONArray lineItems = new JSONArray();
		lineItems.add(lineItem);

		JSONObject order = new JSONObject();
		order.put("recipient_customer_id", customerCid);
		order.put("line_items", lineItems);

		System.out.println("Request Body for the Single Line Item Order= " + order);

		return order;
	}

	// summary
	// Populates an order with Multiple line items
	// summary
	// param name="customerCid", cid of the customer</param>
	// returns: Multi line item order</returns>
	// Note: The below is an example only, end users have to put their own values for the offer_uri and quantity 
	private JSONObject PopulateOrderWithMultipleLineItems(String customerCid)
	{
		JSONObject lineItem1 = new JSONObject();
		lineItem1.put("line_item_number", "0");
		lineItem1.put("offer_uri", "/3c95518e-8c37-41e3-9627-0ca339200f53/offers/A3F4AB4E-6239-4ECB-A859-77369DCA1C08"); //Yammer
		lineItem1.put("quantity", "1");

		JSONObject lineItem2 = new JSONObject();
		lineItem2.put("line_item_number", "1");
		lineItem2.put("offer_uri", "/3c95518e-8c37-41e3-9627-0ca339200f53/offers/B4D4B7F4-4089-43B6-9C44-DE97B760FB11");  //Visio
		lineItem2.put("quantity", "2");

		JSONArray lineItems = new JSONArray();
		lineItems.add(lineItem1);
		lineItems.add(lineItem2);

		JSONObject order = new JSONObject();
		order.put("recipient_customer_id", customerCid);
		order.put("line_items", lineItems);

		System.out.println("Request Body for the Order Creation - Multiple Line Items = " + order);

		return order;
	}
	
	// summary
	// Patch order request body
	// summary
	// param name="offerUri", offer_uri of the order
	// param name="entitlementUri", subscription.links [“entitlement”].href from subscription to reference_entitlement_uris on the line_items property in the PATCH orders payload
	// param name="quantity", quantity of the order to be updated
	// returns: patch order request body
	private JSONObject patchOrderRequestBody(String offerUri, String entitlementUri, String quantity)
	{
		JSONObject lineItem = new JSONObject();
		lineItem.put("line_item_number", "0");
		lineItem.put("offer_uri", offerUri);
		lineItem.put("quantity", quantity);
		JSONArray entitlementUris = new JSONArray();
		entitlementUris.add(entitlementUri);
		lineItem.put("reference_entitlement_uris", entitlementUris);
		
		JSONArray lineItems = new JSONArray();
		lineItems.add(lineItem);
		
		JSONObject order = new JSONObject();
		order.put("line_items", lineItems);
		
		System.out.println("Request Body for the Patch Order - Update Quantity = " + order);
		
		return order;
	}
}
