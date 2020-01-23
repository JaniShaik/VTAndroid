package com.valuetext1android.utils;

import android.util.Log;

import com.salesforce.androidsdk.smartstore.store.QuerySpec;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.valuetext1android.Model.SmsMsgBucket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JANI SHAIK on 13/01/2020
 */

public class SmartStoreQueries {

    // Prepare SMS_Bucket JSONObject
    public static JSONObject prepareSMSBucketData(SmsMsgBucket smsMsgBucket) {

        JSONObject smsObject = new JSONObject();
        try {
            smsObject.put("rsplus__Related_To__c", smsMsgBucket.getRsplus__Related_To__c());
            smsObject.put("rsplus__Sender_ID__c", smsMsgBucket.getRsplus__Sender_ID__c());
            smsObject.put("rsplus__Number__c", smsMsgBucket.getRsplus__Number__c());
            smsObject.put("rsplus__Message__c", smsMsgBucket.getRsplus__Message__c());
            smsObject.put("rsplus__Related_User__c", smsMsgBucket.getRsplus__Related_User__c());
            //smsObject.put("rsplus__Channel__c", smsMsgBucket.getCh);
            //smsObject.put("rsplus__Processed_Channel__c", smsMsgBucket);
            smsObject.put("rsplus__Media_Json__c", smsMsgBucket.getRsplus__Media_Json__c());
            smsObject.put("rsplus__External_Id_Txt__c", smsMsgBucket.getRsplus__External_Id_Txt__c());
            smsObject.put("rsplus__Type__c", smsMsgBucket.getRsplus__Type__c());
            smsObject.put("rsplus__Status__c", smsMsgBucket.getRsplus__Status__c());
            smsObject.put("rsplus__SMS_ID__c", smsMsgBucket.getRsplus__SMS_ID__c());
            smsObject.put("rsplus__Read__c", smsMsgBucket.getRsplus__Read__c());
            smsObject.put("rsplus__Status_Message__c", smsMsgBucket.getRsplus__Message__c());
            smsObject.put("rsplus__Status_Code__c", smsMsgBucket.getRsplus__Status_Code__c());
            smsObject.put("status", smsMsgBucket.getRsplus__Status__c());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return smsObject;
    }

    // Prepare Dashboard Bucket JSONObject
    public static JSONObject prepareDashboardBucketData(JSONObject smsBucketObject) {

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

    // Insert data into Dashboard Bucket
    public static void validateAndInsertDashboardData(JSONObject dashboardObject, SmartStore smartStore) {
        try {
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

    // Check Message Exists
    public static JSONArray checkSMSExists(SmartStore smartStore, String externalId, String smsId) {
        try {
            String query = "SELECT {SMS_Bucket:_soupEntryId}, {SMS_Bucket:rsplus__External_Id_Txt__c} FROM {SMS_Bucket}";
            if (Utils.isStringNotNullOrEmpty(externalId)) {
                query += " where {SMS_Bucket:rsplus__External_Id_Txt__c} = \'" + externalId + "\'";
            }
            if (Utils.isStringNotNullOrEmpty(smsId)) {
                query += " or {SMS_Bucket:rsplus__SMS_ID__c} = \'" + smsId + "\'";
            }
            query += " order by {SMS_Bucket:rsplus__Submitted_On__c} LIMIT 1";
            JSONArray result = smartStore.query(QuerySpec.buildSmartQuerySpec(query, 1), 0);
            if (result.getString(0) != null && !result.getString(0).isEmpty()) {
                //JSONArray jsonArray = (JSONArray) result.get(0);
                return result.getJSONArray(0);
                //return Integer.parseInt(result.getString(0));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Count Message Response
    public static int countMessageResponseRecords(SmartStore smartStore) {
        try {
            String query = "SELECT count({Message_Response:senderId}) FROM {Message_Response} order by {Message_Response:senderId}";
            JSONArray result = smartStore.query(QuerySpec.buildSmartQuerySpec(query, 1), 0);
            if (result.getString(0) != null && !result.getString(0).isEmpty()) {
                JSONArray jsonArray = (JSONArray) result.get(0);
                return jsonArray.getInt(0);
                //return Integer.parseInt(result.getString(0));
            }
            return 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
