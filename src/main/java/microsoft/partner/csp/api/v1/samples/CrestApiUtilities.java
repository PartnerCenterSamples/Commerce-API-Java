/*********************************************************
*                                                        * 
*   Copyright (C) Microsoft. All rights reserved.        * 
*                                                        * 
*********************************************************/ 
package microsoft.partner.csp.api.v1.samples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class CrestApiUtilities {

	public static String parseResponse(HttpResponse response) throws IOException
	{
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		StringBuffer responseString = new StringBuffer();

		while((line = rd.readLine()) != null)
		{
			responseString.append(line);
		}

		return responseString.toString();
	}
}
