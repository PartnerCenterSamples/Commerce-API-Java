/*********************************************************
*                                                        * 
*   Copyright (C) Microsoft. All rights reserved.        * 
*                                                        * 
*********************************************************/ 
package microsoft.partner.csp.api.v1.samples;

import java.util.ArrayList;
import java.util.List;

public class TestCrestApi {

	public static void main(String[] args) {
		try
		{
			List<String> subscriptionIds = new ArrayList<String>();
			List<String> eTags 			 = new ArrayList<String>();
			List<String> orderIds 		 = new ArrayList<String>();
			List<String> offerUris 		 = new ArrayList<String>();
			List<String> entitlementUris = new ArrayList<String>();
			
			String aadToken = new AADToken().getAADToken();
			String saToken = new SAToken(aadToken).getSAToken();
			String resellerMicrosoftId = PartnerAPiCredentialsProvider.getPropertyValue("MicrosoftId");

			String resellerCid = new Reseller(saToken).getResellerCid(resellerMicrosoftId);
			Customer customer = new Customer(saToken);
			String customerCid;
			Boolean newCustomer = false;

			if(newCustomer){
				customer.createCustomer(resellerCid);
				resellerMicrosoftId = customer.getEtid();
				customerCid = customer.getCustomerId(); //persist this value for a later usage
			} 
			else 
			{
				//Hard coding it for testing purpose, otherwise this must have been obtained from the persisted value
				customerCid = PartnerAPiCredentialsProvider.getPropertyValue("ExistingCustomerCid"); 
			}

			//Get the profile of the customer
			String customerToken = customer.getCustomerToken(customerCid, aadToken);
			String customerProfile = new Profile(customerCid, customerToken).getCustomerProfile();
			System.out.println("Retrieved Customer Profile = " + customerProfile);
			
			//Get the list of all the orders placed for this customer by the reseller
			Order order = new Order(saToken);
			order.getAllOrders(resellerCid,customerCid);

			//Create an Azure order for this customer
			String offerUri = "/fbf178a5-144e-46d1-aa81-612c2d3f97f4/offers/MS-AZR-0146P";
			String quantity = "1";
			order.placeOrderWithSingleLineItem(resellerCid, customerCid, offerUri, quantity);
			
			//Retrieve all the subscriptionids, corresponding order ids and entitlement uris for this customer
			Subscriptions customerSubscriptions = new Subscriptions(saToken);
			System.out.println("All subscriptions = "+customerSubscriptions.getAllSubscriptions(resellerCid, customerCid));
			subscriptionIds = customerSubscriptions.getSubscriptionIds();
			eTags  			= customerSubscriptions.geteTags();
			orderIds		= customerSubscriptions.getOrderIds();
			offerUris		= customerSubscriptions.getOfferUris();
			entitlementUris = customerSubscriptions.getEntitlementUris();
			
			for(int i = 0; i < subscriptionIds.size(); i++ )
			{
				System.out.println(	"Subscription id = "+subscriptionIds.get(i) + 
									"\teTag = "+eTags.get(i) 			+
									"\torderId = "+orderIds.get(i) 		+
									"\tofferUri = "+offerUris.get(i) 		+
									"\tEntitlement Uri = "+entitlementUris.get(i)     
									);
			}
			
			//===Increase the order quantity for this customer for the order====== 
			// String subscriptionId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			// String orderId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			// String eTag = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
			// offerUri = "/3c95518e-8c37-41e3-9627-0ca339200f53/offers/6FBAD345-B7DE-42A6-B6AB-79B363D0B371";
			// String entitlementUri = "/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx/Entitlements/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			
			// String quoteEtag = String.format("%s%s%s", "\"", eTag, "\"");
			// System.out.println("quoteEtag = " + quoteEtag);
			
			// order.patchOrder(resellerCid, orderId, quoteEtag, offerUri, entitlementUri, "10"); //increase the quantity
			//===Increase the order quantity for this customer for the order====== 
			
			//===Suspend a subscription
			// customerSubscriptions.addSubscriptionSuspension(resellerCid, subscriptionId);
			// System.out.println("All subscriptions = "+customerSubscriptions.getAllSubscriptions(resellerCid, customerCid));
			//===Suspend a subscription
			
			//Create a Stream to monitor the events
			String streamName = "My_Stream_Testing";
			customerSubscriptions.createSubscriptionStream(resellerCid, streamName);
			
			//Retrieve the events
			System.out.println("Page of events = " + customerSubscriptions.getSubscriptionStream(resellerCid, streamName));
		}
		catch(UnsupportedOperationException e)
		{
			System.out.println("Execption occured = " + e.getMessage());
		}
	}
}
