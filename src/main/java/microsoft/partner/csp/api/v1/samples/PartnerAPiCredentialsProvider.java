/*********************************************************
*                                                        * 
*   Copyright (C) Microsoft. All rights reserved.        * 
*                                                        * 
*********************************************************/ 

package microsoft.partner.csp.api.v1.samples;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PartnerAPiCredentialsProvider {

	public static String getPropertyValue(String propName)
	{
		Properties prop = new Properties();
		
		try {
			InputStream input = new FileInputStream("config/CrestApiCredentials.properties");
			prop.load(input);
		} 
		catch (IOException e) {
			System.out.println("Exception occured in opening the file Crest Api credential properties file "+ e.getMessage());
		}
		
		return prop.getProperty(propName);
	}
}