/*
 * Copyright (c) 2011-present, salesforce.com, inc.
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
package com.valuetext1android;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.salesforce.androidsdk.app.SalesforceSDKManager;
import com.salesforce.androidsdk.mobilesync.app.MobileSyncSDKManager;
import com.salesforce.androidsdk.push.PushNotificationInterface;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.valuetext1android.Model.InboundMessage;
import com.valuetext1android.Model.SmsMsgBucket;
import com.valuetext1android.activity.MainActivity;
import com.valuetext1android.utils.NotificationHelper;
import com.salesforce.androidsdk.smartstore.app.SmartStoreSDKManager;
import com.valuetext1android.utils.SmartStoreQueries;
import com.valuetext1android.utils.Utils;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;



/**
 * Application class for our application.
 */
public class MainApplication extends Application{

	Context mContext = this;
	Activity activity;

	@Override
	public void onCreate(){
		super.onCreate();
		EmojiManager.install(new GoogleEmojiProvider());

		//activity = (Activity) mContext;
		/* Realm.init(getApplicationContext());
		RealmConfiguration config =
				new RealmConfiguration.Builder()
						.deleteRealmIfMigrationNeeded()
						.build();

		Realm.setDefaultConfiguration(config); */

		MobileSyncSDKManager.initNative(getApplicationContext(), MainActivity.class);
		MobileSyncSDKManager.getInstance().setupUserStoreFromDefaultConfig();
        /*
         * Uncomment the following line to enable IDP login flow. This will allow the user to
         * either authenticate using the current app or use a designated IDP app for login.
         * Replace 'idpAppURIScheme' with the URI scheme of the IDP app meant to be used.
         */
		// MobileSyncSDKManager.getInstance().setIDPAppURIScheme(idpAppURIScheme);

		/*
		 * Un-comment the line below to enable push notifications in this app.
		 * Replace 'pnInterface' with your implementation of 'PushNotificationInterface'.
		 * Add your Google package ID in 'bootonfig.xml', as the value
		 * for the key 'androidPushNotificationClientId'.
		 * MobileSyncSDKManager.getInstance().setPushNotificationReceiver(pnInterface);
		 */


		 MobileSyncSDKManager.getInstance().setPushNotificationReceiver(new PushNotificationInterface() {
			 @Override
			 public void onPushMessageReceived(Map<String, String> data) {
				 //Toast.makeText(getApplicationContext(),String.valueOf(data.toString()), Toast.LENGTH_LONG).show();
				 Log.e("Received data", data.toString());

				 try {
				 	JSONObject received = new JSONObject(data.toString());
					 // Inbound/Outbound
					 if (received.has("message")) {
						 for (int i=0; i<received.getJSONArray("message").length(); i++) {
							 //JSONObject smsObject = data.getJSONArray("inbound").getJSONObject(i);

							 // SmartStore
							 SmartStore smartStore = SmartStoreSDKManager.getInstance().getSmartStore();

							 SmsMsgBucket smsMsgBucket = new SmsMsgBucket(received.getJSONArray("message").getJSONObject(i));

							 JSONArray smsDetailsArray = SmartStoreQueries.checkSMSExists(smartStore, smsMsgBucket.getRsplus__External_Id_Txt__c(),
									 smsMsgBucket.getRsplus__SMS_ID__c());

							 if (smsDetailsArray != null && smsDetailsArray.length() > 0) {

								 JSONArray existedDetails = SmartStoreQueries.checkSMSExists(smartStore,
										 smsMsgBucket.getRsplus__External_Id_Txt__c(), smsMsgBucket.getRsplus__SMS_ID__c());

								 if (existedDetails != null) {
									 smsMsgBucket.setEntryId(existedDetails.getLong(0));
								 }
								 // Upsert
								 smartStore.upsert("SMS_Bucket", SmartStoreQueries.prepareSMSBucketData(smsMsgBucket));
								 new NotificationHelper(getApplicationContext()).createNotification(smsMsgBucket.getRsplus__Related_to_name__c(),
										 smsMsgBucket.getRsplus__Message__c());
							 } else {
								 smartStore.create("SMS_Bucket", SmartStoreQueries.prepareSMSBucketData(smsMsgBucket));
							 }
						 }
					 }
					 // Log out/License Expiry
					 if (received.has("type")) {
					 	 // Log out
						 if (received.getString("type").equalsIgnoreCase("logout")) {
						 	// Notify User
						 	if (Utils.isStringNotNullOrEmpty(received.getString("alertMsg"))) {
								new NotificationHelper(getApplicationContext()).createNotification("Alert", received.getString("alertMsg"));
							}// Log out
						 	else {
								//SalesforceSDKManager.getInstance().logout(activity, true);
							}
						 }
						 // License
						 if (received.getString("type").equalsIgnoreCase("licence")) {
						 	// Saving in session CLASS: OAuthWebviewHelper.java (BaseFinishAuthFlowTask)
						 }
					 }

				 } catch (JSONException e) {
					 e.printStackTrace();
				 }
				 Toast.makeText(mContext,"sdfc recieved data",Toast.LENGTH_LONG).show();

				 //notifyThis("Testing", "Notification Description");

			 }

			 @Override
			 public void onSalesforcePushMessageReceived(JSONObject data) {
				 //Toast.makeText(getApplicationContext(),data.toString(), Toast.LENGTH_LONG).show();
				 Log.e("SFDC Received data", data.toString());

				 try {
				 	// Inbound
				 	if (data.has("message")) {
				 		for (int i=0; i<data.getJSONArray("message").length(); i++) {
				 			//JSONObject smsObject = data.getJSONArray("inbound").getJSONObject(i);

							// SmartStore
							SmartStore smartStore = SmartStoreSDKManager.getInstance().getSmartStore();

							SmsMsgBucket smsMsgBucket = new SmsMsgBucket(data.getJSONArray("message").getJSONObject(i));

							JSONArray smsDetailsArray = SmartStoreQueries.checkSMSExists(smartStore, smsMsgBucket.getRsplus__External_Id_Txt__c(),
									smsMsgBucket.getRsplus__SMS_ID__c());

							if (smsDetailsArray != null && smsDetailsArray.length() > 0) {

								JSONArray existedDetails = SmartStoreQueries.checkSMSExists(smartStore,
										smsMsgBucket.getRsplus__External_Id_Txt__c(), smsMsgBucket.getRsplus__SMS_ID__c());

								if (existedDetails != null) {
									smsMsgBucket.setEntryId(existedDetails.getLong(0));
								}
								// Upsert
								smartStore.upsert("SMS_Bucket", SmartStoreQueries.prepareSMSBucketData(smsMsgBucket));
							} else {
								smartStore.create("SMS_Bucket", SmartStoreQueries.prepareSMSBucketData(smsMsgBucket));
							}
						}
					}
				 } catch (JSONException e) {
					 e.printStackTrace();
				 }
				 Toast.makeText(mContext,"sdfc recieved data",Toast.LENGTH_LONG).show();
			 }
		 });



		/**
		 * https://developer.salesforce.com/docs/atlas.en-us.mobile_sdk.meta/mobile_sdk/offline_query.htm
		 * https://developer.salesforce.com/docs/atlas.en-us.noversion.mobile_sdk.meta/mobile_sdk/offline_query.htm
		 * https://developer.salesforce.com/docs/atlas.en-us.noversion.mobile_sdk.meta/mobile_sdk/offline_smart_sql.htm
		 * https://developer.salesforce.com/docs/atlas.en-us.noversion.mobile_sdk.meta/mobile_sdk/offline_cursor.htm#offline_cursor
		 */
		/**
		 * SmartStore
		 */

		SmartStoreSDKManager sdkManager = SmartStoreSDKManager.getInstance();

		final SmartStore smartStore = sdkManager.getSmartStore(); // Creates a default store for the current user
	}


}





