/*
 * Copyright (c) 2012-present, salesforce.com, inc.
 * All rights reserved.
 * Redistribution and use of this software in source and binary forms, with or
 * without modification, are permitted provided that the following conditions
 * are met:
 * - Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * - Neither the name of salesforce.com, inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission of salesforce.com, inc.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.valuetext1android.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.salesforce.androidsdk.rest.files.FileRequests;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.accounts.UserAccountManager;
import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.mobilesync.app.MobileSyncSDKManager;
import com.salesforce.androidsdk.rest.ApiVersionStrings;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestClient.AsyncRequestCallback;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;
import com.salesforce.androidsdk.smartstore.app.SmartStoreSDKManager;
import com.salesforce.androidsdk.smartstore.store.QuerySpec;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.ui.SalesforceActivity;
import com.valuetext1android.Model.SmsMsgBucket;
import com.valuetext1android.R;
import com.valuetext1android.adapter.ChatAdapter;
import com.valuetext1android.utils.Constants;
import com.valuetext1android.utils.SFDCQueries;
import com.valuetext1android.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Main activity
 */
public class MainActivity extends SalesforceActivity implements ChatAdapter.onChatListener{

	private Context mContext = this;
    private RestClient client;
    private ArrayAdapter<String> listAdapter;
	// User Id
	private String userId;
	// Org Id
	private String orgId;
	private Toolbar toolbar;
	// RecyclerView

	private RecyclerView chatRV;
	private ChatAdapter chatAdapter;
	private ArrayList<SmsMsgBucket> listMsgs = new ArrayList<>();
	// RelatedToId
	private String relatedToId;
	// Smart Store
	private SmartStore smartStore;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup theme
		boolean isDarkTheme = MobileSyncSDKManager.getInstance().isDarkTheme();
		setTheme(isDarkTheme ? R.style.SalesforceSDK_Dark : R.style.SalesforceSDK);
		MobileSyncSDKManager.getInstance().setViewNavigationVisibility(this);

		// Setup view
		setContentView(R.layout.main);

		// File Upload
		//FileRequests.uploadFile("", "", "", "");


		toolbar=findViewById(R.id.toolbar_main);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("ValueText");


		final List<UserAccount> users = UserAccountManager.getInstance().getAuthenticatedUsers();
		if(users != null) {
			for (UserAccount userAccount : users) {
				this.userId = userAccount.getUserId();
				this.orgId = userAccount.getOrgId();
			}
		}

		// Smart Store
		smartStore = SmartStoreSDKManager.getInstance().getSmartStore();
	}
	
	@Override 
	public void onResume() {
		// Hide everything until we are logged in
		findViewById(R.id.root).setVisibility(View.INVISIBLE);

		// Create list adapter
		listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		((ListView) findViewById(R.id.contacts_list)).setAdapter(listAdapter);


		chatRV =  findViewById(R.id.chatRV);

		chatAdapter = new ChatAdapter(mContext,this.listMsgs, this);
		chatRV.setAdapter(chatAdapter);
		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
		chatRV.setLayoutManager(linearLayoutManager);
		chatAdapter.notifyDataSetChanged();

		super.onResume();
		//
		//getDashboardData();

	}		
	
	@Override
	public void onResume(RestClient client) {
        // Keeping reference to rest client
        this.client = client; 

		// Show everything
		findViewById(R.id.root).setVisibility(View.VISIBLE);

		// Fetch Dashboard Data
		selectDashboardData();
	}

	/**
	 * Called when "Logout" button is clicked. 
	 * 
	 * @param v
	 */
	public void onLogoutClick(View v) {
		SalesforceSDKManager.getInstance().logout(this);
	}
	
	/**
	 * Called when "Clear" button is clicked. 
	 * 
	 * @param v
	 */
	public void onClearClick(View v) {
		listAdapter.clear();
	}	

	/**
	 * Called when "Fetch Contacts" button is clicked.
	 *
	 * @param v
	 * @throws UnsupportedEncodingException 
	 */
	public void onFetchContactsClick(View v) throws UnsupportedEncodingException {
        //sendRequest("SELECT Name FROM Contact");
	}

	/**
	 * Called when "Fetch Accounts" button is clicked
	 * 
	 * @param v
	 * @throws UnsupportedEncodingException 
	 */
	public void onFetchAccountsClick(View v) throws UnsupportedEncodingException {
		//sendRequest("SELECT Name FROM Account");
		getDashboardData();
		selectSMSBucket();
	}

	/**
	 *
	 * @param
	 * @throws UnsupportedEncodingException
	 */
	public void onFetchMainDashboard(View v) throws UnsupportedEncodingException {
		selectDashboardData();
		//countRecords();
		//selectSMSBucket();
		//sendRequest(SFDCQueries.HOME_SCREEN_QUERY, Constants.HOME_SCREEN);
	}

	private void sendRequest(String soql, final int type) throws UnsupportedEncodingException {
		RestRequest restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(this), soql);

		client.sendAsync(restRequest, new AsyncRequestCallback() {
			@Override
			public void onSuccess(RestRequest request, final RestResponse result) {
				result.consumeQuietly(); // consume before going back to main thread
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						try {
							if (type == Constants.HOME_SCREEN) {
								listAdapter.clear();
								chatAdapter.notifyDataSetChanged();

								JSONArray records = result.asJSONObject().getJSONArray("records");

								// Insert SMSBucket
								insertSMSBucket(records);

								// Select
								selectDashboardData();

								/*HashMap<String, SmsMsgBucket> filterMap = new HashMap<>();
								for (int i = 0; i < records.length(); i++) {
									SmsMsgBucket smsMsgBucket = new SmsMsgBucket(records.getJSONObject(i));
									String key = Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Related_To__c()) ?
											smsMsgBucket.getRsplus__Related_To__c() : smsMsgBucket.getRsplus__Number__c();

									if (!filterMap.containsKey(key)) {
										filterMap.put(key, smsMsgBucket);
									}
								}

								listMsgs.addAll(filterMap.values());
								chatAdapter.notifyDataSetChanged();*/
							}
							else if (type == Constants.CHAT_SCREEN) {
								JSONArray records = result.asJSONObject().getJSONArray("records");
								Intent intent = new Intent(mContext, MessageChat.class);
								intent.putExtra(Constants.CHAT_DATA, records.toString());
								intent.putExtra(Constants.RELATED_TO, relatedToId);
								startActivity(intent);
							}
						} catch (Exception e) {
							onError(e);
						}
					}
				});
			}
			
			@Override
			public void onError(final Exception exception) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(MainActivity.this,
								MainActivity.this.getString(R.string.sf__generic_error, exception.toString()),
								Toast.LENGTH_LONG).show();
					}
				});
			}
		});
	}


	@Override
	public void onChatClick(int position) throws UnsupportedEncodingException {
			//startActivity(new Intent(this,MessageChat.class));
		SmsMsgBucket smsMsgBucket = listMsgs.get(position);

		relatedToId = smsMsgBucket.getRsplus__Related_To__c();

		String smartQuery = "select {SMS_Bucket:rsplus__Related_To__c}, {SMS_Bucket:rsplus__Sender_ID__c}, {SMS_Bucket:rsplus__Number__c}, " +
				"{SMS_Bucket:rsplus__Message__c}, {SMS_Bucket:rsplus__Channel__c}, {SMS_Bucket:rsplus__Processed_Channel__c}, " +
				"{SMS_Bucket:rsplus__Type__c} from {SMS_Bucket} where {SMS_Bucket:" +
				smsMsgBucket.getQuery_field() + "} = \'" + smsMsgBucket.getKeyValue() + "\' order by {SMS_Bucket:rsplus__Submitted_On__c} ASC";
		try {
			JSONArray result = smartStore.query(QuerySpec.buildSmartQuerySpec(
					smartQuery, 200), 0);

			Log.e("Result size", String.valueOf(result.length()));
			JSONArray data = result.getJSONArray(0);

			Intent intent = new Intent(mContext, MessageChat.class);
			intent.putExtra(Constants.CHAT_DATA, result.toString());
			intent.putExtra(Constants.RELATED_TO, data.getString(0));
			startActivity(intent);

		} catch (JSONException e) {
			e.printStackTrace();
		}





		/*String query = "";
		if (Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Related_To__c())) {
			query = SFDCQueries.CHAT_CONSOLE_QUERY + "where rsplus__Related_To__c= \'" + relatedToId + "\' order by rsplus__Submitted_On__c DESC limit 200";
		} else {
			query = SFDCQueries.CHAT_CONSOLE_QUERY + "where rsplus__Sender_ID__c=\'" + smsMsgBucket.getRsplus__Sender_ID__c() + "\' and rsplus__Number__c=\'" +
					smsMsgBucket.getRsplus__Number__c()+ "\' order by rsplus__Submitted_On__c DESC limit 200";
		}
		sendRequest(query, Constants.CHAT_SCREEN);*/
	}

	/**
	 * Insert SMSBucket
	 */
	private void insertSMSBucket(JSONArray smsBucket) {
		if (smsBucket != null) {
			for (int i = 0; i < smsBucket.length(); i++) {
				try {
					JSONObject smsBucketObject = smsBucket.getJSONObject(i);
					if (smsBucketObject != null) {
						try {
							smartStore.upsert("SMS_Bucket", smsBucketObject);
							// Prepare DashboardData
							JSONObject dashboardObject = prepareDashboardData(smsBucketObject);
							// Validate Data
							validateAndInsertData(dashboardObject);
						} catch (JSONException e) {
							Log.e("Insert Error",
									"Error occurred while attempting "
											+ "to insert account. Please verify "
											+ "validity of JSON data set.");
						}
					}
				} catch (JSONException e) {
					Log.e("JSON Exception", "Error occurred while attempting to "
							+ "insert SMS Bucket. Please verify validity "
							+ "of JSON data set.");
				}
			}
			// Dashboard Data
			selectDashboardData();
		}
	}

	/**
	 * Preparing Dashboard Data
	 * @param smsBucketObject
	 * @return
	 */
	private JSONObject prepareDashboardData(JSONObject smsBucketObject) {

		JSONObject dashboardObject = new JSONObject();
		try {
			SmsMsgBucket smsMsgBucket = new SmsMsgBucket(smsBucketObject);

			String key = Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Related_To__c()) ?
					smsMsgBucket.getRsplus__Related_To__c() : smsMsgBucket.getRsplus__Number__c();

			String queryField = Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Related_To__c()) ?
					"rsplus__Related_To__c" : "rsplus__Number__c";

			dashboardObject.put("key_value", key);
			dashboardObject.put("rsplus__Message__c", smsMsgBucket.getRsplus__Message__c());
			dashboardObject.put("rsplus__Submitted_On__c", smsMsgBucket.getRsplus__Submitted_On__c());

			String name = Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Related_to_name__c()) ? smsMsgBucket.getRsplus__Related_to_name__c() :
					(Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Reated_To_Name_Formula__c()) ? smsMsgBucket.getRsplus__Reated_To_Name_Formula__c() :
							smsMsgBucket.getRsplus__Number__c());

			dashboardObject.put("name", name);

			dashboardObject.put("query_field", queryField);

		} catch (JSONException e) {
			e.printStackTrace();
			//Log.e(e.getMessage())
		}
		return dashboardObject;
	}

	// Validate Dashboard Data
	private void validateAndInsertData(JSONObject dashboardObject) {
		try {
			JSONArray allResult = smartStore.query(QuerySpec.buildSmartQuerySpec(
					"SELECT {Dashboard_Bucket:rsplus__Submitted_On__c} FROM {Dashboard_Bucket} " +
							"where {Dashboard_Bucket:key_value} = \'" +  dashboardObject.getString("key_value") + "\' " +
							" order by {Dashboard_Bucket:rsplus__Submitted_On__c} DESC", 200), 0);

			JSONArray result =
					smartStore.query(QuerySpec.buildSmartQuerySpec(
							"SELECT {Dashboard_Bucket:rsplus__Submitted_On__c}, {Dashboard_Bucket:_soupEntryId} FROM {Dashboard_Bucket} " +
									"where {Dashboard_Bucket:key_value} = \'" +  dashboardObject.getString("key_value") + "\' " +
									" order by {Dashboard_Bucket:rsplus__Submitted_On__c} DESC limit 1", 1), 0);

			boolean isData = false;

			if (result.length() > 0) {
				isData = true;

				JSONArray submittedOn = result.getJSONArray(0);

				if (Utils.convertStringToDate(dashboardObject.getString("rsplus__Submitted_On__c")).
						after(Utils.convertStringToDate(submittedOn.getString(0)))) {
					smartStore.delete("Dashboard_Bucket", result.getLong(1));
					smartStore.create("Dashboard_Bucket", dashboardObject);
				}
			}
			//
			if (!isData) {
				smartStore.create("Dashboard_Bucket", dashboardObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// Count Dashboard Records
	private int getCountofDashboardRecords() {
		try {
			JSONArray result =
					smartStore.query(QuerySpec.buildSmartQuerySpec(
							"SELECT count({Dashboard_Bucket:rsplus__Submitted_On__c}) FROM {Dashboard_Bucket} order by {Dashboard_Bucket:rsplus__Submitted_On__c}" +
									" DESC limit 200", 1), 0);
			//Log.e("Count", result.getString(0));
			return Integer.parseInt(result.getString(0));
		} catch (JSONException e) {
			Log.e("Error", "Error occurred while counting the number of account records. "
					+  "Please verify validity of JSON data set.");
		}
		return 0;
	}

	private void countRecords() {
		try {
			JSONArray result =
					smartStore.query(QuerySpec.buildSmartQuerySpec(
							"SELECT count({SMS_Bucket:rsplus__Number__c}) FROM {SMS_Bucket} order by {SMS_Bucket:rsplus__Submitted_On__c}" +
									" DESC limit 200", 1), 0);
			Log.e("Count", result.getString(0));
			// result should be [[ n ]] if there are n employees
			//Toast.makeText(mContext, "Insert Count " + String.valueOf(result.length()), Toast.LENGTH_SHORT).show();

			//Log.println(Log.INFO, "REST Success!", "\nFound " +
			//		result.getString(0) + " accounts.");
		} catch (JSONException e) {
			Log.e("Error", "Error occurred while counting the number of account records. "
					+  "Please verify validity of JSON data set.");
		}
	}

	/**
	 * DashboardBucket
	 */
	private void getDashboardData() {
		try {
			JSONArray result =
					smartStore.query(QuerySpec.buildSmartQuerySpec(
							"SELECT {Dashboard_Bucket:key_value}, {Dashboard_Bucket:rsplus__Message__c}, {Dashboard_Bucket:rsplus__Submitted_On__c}, " +
									"{Dashboard_Bucket:name}, {Dashboard_Bucket:query_field}, {Dashboard_Bucket:_soupEntryId} FROM {Dashboard_Bucket}" +
									" order by {Dashboard_Bucket:rsplus__Submitted_On__c} DESC limit 200", 200), 0);

			if (result.length() > 0) {
				for (int i=0;i<result.length();i++) {
					JSONArray smsArray = result.getJSONArray(i);

					String entryId = smsArray.getString(5);

					Log.e("Entry Id", entryId);

					smartStore.delete("Dashboard_Bucket", Long.parseLong(entryId));

					/*JSONObject smsObject = new JSONObject();
					smsObject.put("key_value", smsArray.get(0));
					smsObject.put("rsplus__Message__c", smsArray.get(1));
					smsObject.put("rsplus__Submitted_On__c", smsArray.get(2));
					smsObject.put("related_to_name", smsArray.get(3));
					smsObject.put("query_field", smsArray.get(4));*/


				}
			}
		} catch (JSONException e) {
			Log.e("error", e.getMessage());
		}
	}

	/**
	 * Select DashboardBucket
	 */
	private void selectDashboardData() {
		try {
			JSONArray result =
					smartStore.query(QuerySpec.buildSmartQuerySpec(
							"SELECT {Dashboard_Bucket:key_value}, {Dashboard_Bucket:rsplus__Message__c}, {Dashboard_Bucket:rsplus__Submitted_On__c}, " +
									"{Dashboard_Bucket:name}, {Dashboard_Bucket:query_field} FROM {Dashboard_Bucket}" +
									" order by {Dashboard_Bucket:rsplus__Submitted_On__c} DESC limit 200", 200), 0);

			listMsgs.clear();
			listAdapter.clear();
			chatAdapter.notifyDataSetChanged();

			if (result.length() > 0) {
				for (int i=0;i<result.length();i++) {
					JSONArray smsArray = result.getJSONArray(i);

					JSONObject smsObject = new JSONObject();
					smsObject.put("key_value", smsArray.get(0));
					smsObject.put("rsplus__Message__c", smsArray.get(1));
					smsObject.put("rsplus__Submitted_On__c", smsArray.get(2));
					smsObject.put("related_to_name", smsArray.get(3));
					smsObject.put("query_field", smsArray.get(4));

					SmsMsgBucket smsMsgBucket = new SmsMsgBucket(smsObject);
					listMsgs.add(smsMsgBucket);
					chatAdapter.notifyDataSetChanged();
				}
			}// Get Data From Salesforce
			else {
				sendRequest(SFDCQueries.HOME_SCREEN_QUERY, Constants.HOME_SCREEN);
			}
		} catch (JSONException e) {
			Log.e("error", e.getMessage());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Select SMSBucket
	 */
	private void selectSMSBucket() {
		try {
			JSONArray result =
					smartStore.query(QuerySpec.buildSmartQuerySpec(
							"SELECT {SMS_Bucket:_soupEntryId}, {SMS_Bucket:rsplus__Number__c}, {SMS_Bucket:rsplus__Message__c}, {SMS_Bucket:rsplus__Submitted_On__c}," +
									"{SMS_Bucket:rsplus__Related_To__c}, {SMS_Bucket:rsplus__Related_User__c}, {SMS_Bucket:rsplus__Related_to_name__c}, " +
									"{SMS_Bucket:rsplus__Channel__c}, {SMS_Bucket:rsplus__External_Id_Txt__c}, {SMS_Bucket:rsplus__Type__c}, " +
									"{SMS_Bucket:rsplus__Status__c}, {SMS_Bucket:rsplus__SMS_ID__c}, {SMS_Bucket:rsplus__External_Id_Txt__c}, " +
									"{SMS_Bucket:rsplus__Read__c} FROM {SMS_Bucket} order by {SMS_Bucket:rsplus__Submitted_On__c}" +
									" DESC limit 200", 10000), 0);
			// result should be [[ n ]] if there are n employees

			Log.e("Size", String.valueOf(result.length()));

			for (int i=0; i<result.length(); i++) {
				JSONArray smsArray = result.getJSONArray(i);
				String entryId = smsArray.getString(0);
				Log.e("Entry", entryId);
				smartStore.delete("SMS_Bucket", Long.parseLong(entryId));
			}

			HashMap<String, JSONArray> filterMap = new HashMap<>();

			/*for (int i = 0; i < result.length(); i++) {
				JSONArray smsBucketArray = result.getJSONArray(i);
				//String key = Utils.isStringNotNullOrEmpty()

				SmsMsgBucket smsMsgBucket = new SmsMsgBucket(records.getJSONObject(i));
				String key = Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Related_To__c()) ?
						smsMsgBucket.getRsplus__Related_To__c() : smsMsgBucket.getRsplus__Number__c();

				if (!filterMap.containsKey(key)) {
					filterMap.put(key, smsMsgBucket);
				}
				//Log.e("Record", records.get(i).toString());
				//listAdapter.add(records.getJSONObject(i).getString("Name"));
			}*/


			//Log.println(Log.INFO, "REST Success!", "\nFound " +
			//		result.getString(0) + " accounts.");
		} catch (JSONException e) {
			Log.e("Error", "Error occurred while counting the number of account records. "
					+  "Please verify validity of JSON data set.");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu,menu);
		MenuItem menuItem=menu.findItem(R.id.action_search);
		SearchView searchView=(SearchView)menuItem.getActionView();
		searchView.setQueryHint("Search Here");
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				listAdapter.getFilter().filter(newText);
				return true;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()){
			case R.id.settings:
				SettingFun();
				return true;
			case R.id.logout:
				logoutClick();
				return true;
			case R.id.action_search:
				return true;
				default:
					return super.onOptionsItemSelected(item);
		}
	}

	private void logoutClick() {

	}

	private void SettingFun() {


	}
}
