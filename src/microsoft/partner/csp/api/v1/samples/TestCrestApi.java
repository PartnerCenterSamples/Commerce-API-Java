/*********************************************************
 *                                                        * 
 *   Copyright (C) Microsoft. All rights reserved.        * 
 *                                                        * 
 *********************************************************/ 

package microsoft.partner.csp.api.v1.samples;

import org.json.simple.parser.ParseException;

public class TestCrestApi {

	public static void main(String[] args) {
		try
		{
			String aadToken = new AADToken().getAADToken();
			String saToken = new SAToken().getSAToken(aadToken);
			String resellerMicrosoftId = PartnerAPiCredentialsProvider.getPropertyValue("MicrosoftId");

			String resellerCid = new Reseller().getResellerCid(resellerMicrosoftId, saToken);
			Customer customer = new Customer();
			String customerCid;
			Boolean newCustomer = false;
			String customerMicrosoftId = PartnerAPiCredentialsProvider.getPropertyValue("ExistingCustomerCid");
			String offerUri = "";
			String quantity = "0";

			if(newCustomer){
				customer.createCustomer(resellerCid, saToken);
				resellerMicrosoftId = customer.getEtid();
				customerCid = customer.getCustomerId(); //persist this value for using it later 
			} 
			else 
			{
				customer.getCustomerbyIdentity(customerMicrosoftId, resellerMicrosoftId, saToken);
				customerCid = "xxx-xxx-xxx-xxx"; //get this value by parsing the response from the above API and persist this value for later use
			}
			
			//Get the profile of the customer
			String customerToken = customer.getCustomerToken(customerCid, aadToken);
			String customerProfile = new Profile().getCustomerProfile(customerCid, customerToken);
			System.out.println("Retrieved Customer Profile = " + customerProfile);
				
			//Delete a customer
			//customer.deleteCustomer(resellerCid, customerCid, saToken);
			
			//Get the list of all the orders placed for this customer by the reseller
			Order order = new Order();
			order.getAllOrders(resellerCid,customerCid,saToken);

			//Create an Azure order for this customer
			offerUri = "/fbf178a5-144e-46d1-aa81-612c2d3f97f4/offers/MS-AZR-0146P";
			quantity = "2";
			order.placeOrderWithSingleLineItem(resellerCid, customerCid, offerUri, quantity, saToken);

			//Create an Office 365 Enterprise K1 for this customer
			offerUri = "/3c95518e-8c37-41e3-9627-0ca339200f53/offers/6FBAD345-B7DE-42A6-B6AB-79B363D0B371";
			quantity = "5";
			order.placeOrderWithSingleLineItem(resellerCid, customerCid, offerUri, quantity, saToken);
			
			Subscriptions customerSubscriptions = new Subscriptions();

			//===Increase the order quantity for this customer for the order====== 
			String subscriptionId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			String orderId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			offerUri = "/3c95518e-8c37-41e3-9627-0ca339200f53/offers/6FBAD345-B7DE-42A6-B6AB-79B363D0B371"; //Office 365 Enterprise K1
			String entitlementUri = "/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx/Entitlements/xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			quantity = "2";
			order.patchOrder(resellerCid, orderId, offerUri, entitlementUri, quantity, saToken);
			
			//Transition a subscription
			String newOfferUri = "/3c95518e-8c37-41e3-9627-0ca339200f53/offers/796B6B5F-613C-4E24-A17C-EBA730D49C02"; //to new offer Office 365 Enterprise E3
			quantity = "3";
			customerSubscriptions.transitionSubscription(resellerCid, customerCid, quantity, newOfferUri, entitlementUri, saToken);

			//Retrieve all the subscriptionids, corresponding order ids and entitlement uris for this customer
			System.out.println("All subscriptions before suspension = "+customerSubscriptions.getAllSubscriptions(resellerCid, customerCid, saToken));
			
			//===Suspend a subscription
			subscriptionId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx";
			customerSubscriptions.suspendSubscription(resellerCid, subscriptionId, saToken);
			System.out.println("All subscriptions = "+customerSubscriptions.getAllSubscriptions(resellerCid, customerCid, saToken));
			//===Suspend a subscription

			System.out.println("All subscriptions after suspension = "+customerSubscriptions.getAllSubscriptions(resellerCid, customerCid, saToken));

			//Create a Stream to monitor the events
			String streamName = "Provide_a_stream_name_here";
			customerSubscriptions.createSubscriptionStream(resellerCid, streamName, saToken);

			//Retrieve the events
			String events = customerSubscriptions.getSubscriptionStream(resellerCid, streamName, saToken);
			System.out.println("Page of events = " + events);
			
			String completedUri = customerSubscriptions.getCompletionLink(events);
			System.out.println("Completion Link = " + completedUri);
			
			//Mark page as completed
			customerSubscriptions.markPageAsCompleted(completedUri, saToken);

			events = customerSubscriptions.getSubscriptionStream(resellerCid, streamName, saToken);
			System.out.println("Page of events after mark page completion = " + events);

		}
		catch(UnsupportedOperationException e)
		{
			System.out.println("Execption occured = " + e.getMessage());
		}
		catch (ParseException e) 
		{
			System.out.println("JSON Parse exception occured, not able to proceed further");
			System.exit(0);
		}
		catch(NullPointerException ne)
		{
			System.out.println("Null pointer exception occured, not able to proceed further");
			System.exit(0);
		} 
	}
}
