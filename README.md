# Commerce-API-Java
Java sample code for calling Cloud Solution Provider CREST APIs

These are samples in Java for using the commerce APIs for Microsoft Partner Center. These CREST APIs are documented at https://msdn.microsoft.com/en-us/library/partnercenter/dn974944.aspx

A public forum for discussing the APIs is available at https://social.msdn.microsoft.com/Forums/en-US/home?forum=partnercenterapi

# Update CREST API Credentials
Before you build these samples, please update the following in the file - CrestApiCredentials.properties, for your integration sandbox account.
Go to https://partnercenter.microsoft.com/pc/AccountSettings/TenantProfile to get this information
Watch the youtube video which will guide you to fill the details - https://www.youtube.com/watch?v=8RRssasC2Ys&feature=youtu.be
DefaultDomain=xxxxxxxxxxxx.onmicrosoft.com        
MicrosoftId=xxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx

Go to https://partnercenter.microsoft.com/pc/APIIntegration to fill the below details
Watch the youtube video which will guide you to fill the details - https://www.youtube.com/watch?v=8RRssasC2Ys&feature=youtu.be
AppId=xxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
Key=xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

ApiEndpoint=https://api.cp.microsoft.com

#Build and run instructions

Follow these steps

1. Download the following jars and save them in a local folder
  * Apache HttpClient 4.5 is located at https://hc.apache.org/downloads.cgi
  * Download and extract the .zip file httpcomponents-client-4.5-bin.zip in to a folder
  * Download JSON parser - json_simple-1.1.jar from https://code.google.com/p/json-simple/downloads/detail?name=json_simple-1.1.jar&can=2&q= in to a folder
2. Download and extract the Commerce-API-Java source code zip file from GIT hub
3. Eclipse IDE is required to run the Commerce-API-Java project, please download the same from https://www.eclipse.org/downloads/
4. If JDK is not installed, download & install latest jdk from http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
5. Open the eclipse IDE and click on File->Import

  ![File Import Menu](https://raw.githubusercontent.com/PartnerCenterSamples/Commerce-API-Java/master/ReadmeImages/CREST-Java-Img-1.png "File Import Menu")
6. Select “Existing Projects into Workspace” option and click next

  ![Existing Projects into Workspace](https://raw.githubusercontent.com/PartnerCenterSamples/Commerce-API-Java/master/ReadmeImages/CREST-Java-Img-2.png "Existing Projects into Workspace")
7. Select the root directory option and browse for the extracted code, an example illustration is shown below. Click Finish

  ![Import Projects](https://raw.githubusercontent.com/PartnerCenterSamples/Commerce-API-Java/master/ReadmeImages/CREST-Java-Img-3.png "Import Projects")
8. The code along with the project should appear as below in the eclipse IDE

  ![Code in IDE](https://raw.githubusercontent.com/PartnerCenterSamples/Commerce-API-Java/master/ReadmeImages/CREST-Java-Img-4.png "Code in IDE")
9. Configure the Java Build Path 
  1.	Right click on the project and click on “Properties->Java Build Path”
  2.	Click “Add External JARs” and choose the following jar files from the local folders where they were downloaded earlier
    * httpclient-4.5.jar 
    * httpcore-4.4.1.jar
    * commons-logging-1.2.jar 
    * json-simple-1.1.1.jar

  ![Java Build Path](https://raw.githubusercontent.com/PartnerCenterSamples/Commerce-API-Java/master/ReadmeImages/CREST-Java-Img-5.png "Java Build Path")
10. Eclipse should now build the project with no errors.
11. To run the sample right click on the file TestCrestApi.java, select Debug-As, and select Java Application.
