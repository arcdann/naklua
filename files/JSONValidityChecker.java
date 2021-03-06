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

	public static void main(String[] args) {
		List<JSONCheckResult> objectsCheckResults = new ArrayList<JSONCheckResult>();

		objectsCheckResults.add(new JSONCheckResult("amazon", "Amazon"));
		objectsCheckResults.add(new JSONCheckResult("googleplay", "Google Play"));
		objectsCheckResults.add(new JSONCheckResult("ios", "iOS"));

		for (JSONCheckResult res : objectsCheckResults) {
			String jsonFormatCheckResult = checkObject(res.getFileName())[0];
			res.setJsonFormatCheckResult(jsonFormatCheckResult);

			String maintenanceCheckResult = checkObject(res.getFileName())[1];
			res.setMaintenanceCheckResult(maintenanceCheckResult);

			System.out.println(res.getObjAlias() + ": ");
			System.out.println("JSON format: " + res.getJsonFormatCheckResult());
			System.out.println("maintenance: " + res.getMaintenanceCheckResult());
			System.out.println();
		}

		String overallResult = "Ok";
		for (JSONCheckResult res : objectsCheckResults) {
			if (!res.getJsonFormatCheckResult().equalsIgnoreCase("Ok")) {
				overallResult = "Bad";
				break;
			}
			if (!res.getMaintenanceCheckResult().equalsIgnoreCase("Ok")) {
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

			boolean maintenance = jsonObj.optBoolean("maintenance", true);
			if (!maintenance) {
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
