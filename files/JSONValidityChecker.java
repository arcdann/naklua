package com.daniloff.jsonvaliditychecker;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class JSONValidityChecker {

	private static List<JSONCheckResult> objectsCheckResults = new ArrayList<JSONCheckResult>();

	public static void main(String[] args) {
		objectsCheckResults.add(new JSONCheckResult("amazon", "Amazon"));
		objectsCheckResults.add(new JSONCheckResult("googleplay", "Google Play"));
		objectsCheckResults.add(new JSONCheckResult("ios", "iOS"));

		for (int i = 0; i < objectsCheckResults.size(); i++) {
			String jsonFormatCheckResult = checkObject(objectsCheckResults.get(i).getFileName())[0];
			objectsCheckResults.get(i).setJsonFormatCheckResult(jsonFormatCheckResult);

			String maintenanceCheckResult = checkObject(objectsCheckResults.get(i).getFileName())[1];
			objectsCheckResults.get(i).setMaintenanceCheckResult(maintenanceCheckResult);

			System.out.println(objectsCheckResults.get(i).getObjAlias() + ": ");
			System.out.println("JSON format: " + objectsCheckResults.get(i).getJsonFormatCheckResult());
			System.out.println("maintenance: " + objectsCheckResults.get(i).getMaintenanceCheckResult());
			System.out.println();
		}

		String overallResult = "Ok";
		for (int i = 0; i < objectsCheckResults.size(); i++) {
			if (!objectsCheckResults.get(i).getJsonFormatCheckResult().equalsIgnoreCase("Ok")) {
				overallResult = "Bad";
				break;
			}
			if (!objectsCheckResults.get(i).getMaintenanceCheckResult().equalsIgnoreCase("Ok")) {
				overallResult = "Bad";
				break;
			}
		}
		System.out.println("Overall: " + overallResult);
	}

	private static String[] checkObject(String fileName) {
		String[] retArray = new String[] { "No valid JSON file", "Not checked maintenance" };

		String request = "http://s3.alisabingo.net/settings/" + fileName + ".json";
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(request);

		JSONObject jsonObj = null;

		try {
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String responseBody = httpClient.execute(httpget, responseHandler);
			jsonObj = new JSONObject(responseBody);
			retArray[0] = "Ok";

			String maintenance = jsonObj.optString("maintenance", null);
			if (maintenance.equalsIgnoreCase("false")) {
				retArray[1] = "Ok";
			}
		} catch (Exception e) {
			System.out.println("failed to get object " + e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return retArray;
	}
}

class JSONCheckResult {
	private String fileName;
	private String objAlias;
	private String jsonFormatCheckResult = "Bad";
	private String maintenanceCheckResult = "Bad";

	public JSONCheckResult(String fileName, String alias) {
		this.fileName = fileName;
		objAlias = alias;
	}

	public String getFileName() {
		return fileName;
	}

	public String getObjAlias() {
		return objAlias;
	}

	public String getJsonFormatCheckResult() {
		return jsonFormatCheckResult;
	}

	public void setJsonFormatCheckResult(String jsonFormatCheckResult) {
		this.jsonFormatCheckResult = jsonFormatCheckResult;
	}

	public String getMaintenanceCheckResult() {
		return maintenanceCheckResult;
	}

	public void setMaintenanceCheckResult(String maintenanceCheckResult) {
		this.maintenanceCheckResult = maintenanceCheckResult;
	}
}
