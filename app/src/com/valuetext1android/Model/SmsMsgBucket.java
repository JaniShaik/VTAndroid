package com.valuetext1android.Model;


import com.valuetext1android.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JANI SHAIK on 28/12/2019
 */

public class SmsMsgBucket {

    private JSONObject attributes;
    private Long entryId;
    private String Id;
    private String Name;
    private String relatedToName;
    private String keyValue;
    private String query_field;
    private String OwnerId;
    private String rsplus__Media_Json__c;
    private String rsplus__Message__c;
    private String rsplus__Number__c;
    private String rsplus__Reated_To_Name_Formula__c;
    private String rsplus__Related_to_name__c;
    private String rsplus__Related_To__c;
    private String rsplus__Related_User__c;
    private String rsplus__Sender_ID__c;
    private String rsplus__Status__c;
    private String rsplus__Submitted_On__c;
    private String rsplus__Type__c;
    private String rsplus__SMS_ID__c;
    private String rsplus__External_Id_Txt__c;
    private String rsplus__Read__c;
    private String rsplus__Status_Code__c;
    private String rsplus__Status_Message__c;
    private String source;


    public SmsMsgBucket(JSONObject mSmsMsgBucket) throws JSONException {

        if (mSmsMsgBucket.has("attributes")) {
            setAttributes(mSmsMsgBucket.getJSONObject("attributes"));
        }
        if (mSmsMsgBucket.has("Id")) {
            setId(Utils.checkString(mSmsMsgBucket.getString("Id")));
        }
        if (mSmsMsgBucket.has("Name")) {
            setName(Utils.checkString(mSmsMsgBucket.getString("Name")));
        }
        if (mSmsMsgBucket.has("related_to_name")) {
            setRelatedToName(Utils.checkString(mSmsMsgBucket.getString("related_to_name")));
        }
        if (mSmsMsgBucket.has("key_value")) {
            setKeyValue(Utils.checkString(mSmsMsgBucket.getString("key_value")));
        }
        if (mSmsMsgBucket.has("query_field")) {
            setQuery_field(Utils.checkString(mSmsMsgBucket.getString("query_field")));
        }
        if (mSmsMsgBucket.has("OwnerId")) {
            setOwnerId(Utils.checkString(mSmsMsgBucket.getString("OwnerId")));
        }
        if (mSmsMsgBucket.has("rsplus__Media_Json__c")) {
            setRsplus__Media_Json__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Media_Json__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Message__c")) {
            setRsplus__Message__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Message__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Number__c")) {
            setRsplus__Number__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Number__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Reated_To_Name_Formula__c")) {
            setRsplus__Reated_To_Name_Formula__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Reated_To_Name_Formula__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Related_to_name__c")) {
            setRsplus__Related_to_name__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Related_to_name__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Related_To__c")) {
            setRsplus__Related_To__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Related_To__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Related_User__c")) {
            setRsplus__Related_User__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Related_User__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Sender_ID__c")) {
            setRsplus__Sender_ID__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Sender_ID__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Status__c")) {
            setRsplus__Status__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Status__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Submitted_On__c")) {
            setRsplus__Submitted_On__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Submitted_On__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Type__c")) {
            setRsplus__Type__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Type__c")));
        }
        if (mSmsMsgBucket.has("rsplus__SMS_ID__c")) {
            setRsplus__SMS_ID__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__SMS_ID__c")));
        }
        if (mSmsMsgBucket.has("rsplus__External_Id_Txt__c")) {
            setRsplus__External_Id_Txt__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__External_Id_Txt__c")));
        }
        if (mSmsMsgBucket.has("rsplus__Read__c")) {
            setRsplus__Read__c(Utils.checkString(mSmsMsgBucket.getString("rsplus__Read__c")));
        }
        // Inbound/Outbound Messages
        if (mSmsMsgBucket.has("vtype")) {
            setRsplus__Type__c(Utils.checkString(mSmsMsgBucket.getString("vtype")));
        }
        if (mSmsMsgBucket.has("vmsg")) {
            setRsplus__Message__c(Utils.checkString(mSmsMsgBucket.getString("vmsg")));
        }
        if (mSmsMsgBucket.has("vmediaJson")) {
            setRsplus__Media_Json__c(Utils.checkString(mSmsMsgBucket.getString("vmediaJson")));
        }
        if (mSmsMsgBucket.has("vuserId")) {
            setRsplus__Related_User__c(Utils.checkString(mSmsMsgBucket.getString("vuserId")));
        }
        if (mSmsMsgBucket.has("vDateStamp")) {
            setRsplus__Submitted_On__c(Utils.checkString(mSmsMsgBucket.getString("vDateStamp")));
        }
        if (mSmsMsgBucket.has("vrelatedId")) {
            setRsplus__Related_To__c(Utils.checkString(mSmsMsgBucket.getString("vrelatedId")));
        }
        if (mSmsMsgBucket.has("vnumber")) {
            setRsplus__Number__c(Utils.checkString(mSmsMsgBucket.getString("vnumber")));
        }
        if (mSmsMsgBucket.has("vsenderId")) {
            setRsplus__Sender_ID__c(Utils.checkString(mSmsMsgBucket.getString("vsenderId")));
        }
        if (mSmsMsgBucket.has("vsmsId")) {
            setRsplus__SMS_ID__c(Utils.checkString(mSmsMsgBucket.getString("vsmsId")));
        }
        if (mSmsMsgBucket.has("vsfId")) {
            setRsplus__External_Id_Txt__c(Utils.checkString(mSmsMsgBucket.getString("vsfId")));
        }
        if (mSmsMsgBucket.has("vstatus")) {
            setRsplus__Status__c(Utils.checkString(mSmsMsgBucket.getString("vstatus")));
        }
        if (mSmsMsgBucket.has("vstatusMsg")) {
            setRsplus__Status_Message__c(Utils.checkString(mSmsMsgBucket.getString("vstatusMsg")));
        }
        if (mSmsMsgBucket.has("vstatusCode")) {
            setRsplus__Status_Code__c(Utils.checkString(mSmsMsgBucket.getString("vstatusCode")));
        }
        if (mSmsMsgBucket.has("vsource")) {
            setSource(Utils.checkString(mSmsMsgBucket.getString("vsource")));
        }
        if (mSmsMsgBucket.has("vrelatedtoname")) {
            setRsplus__Related_to_name__c(Utils.checkString(mSmsMsgBucket.getString("vrelatedtoname")));
        }
        // SmartStore Entry Id
        if (mSmsMsgBucket.has("entryId")) {
            setEntryId(mSmsMsgBucket.getLong("entryId"));
        }

    }


    public JSONObject getAttributes() {
        return attributes;
    }

    public String getRelatedToName() {
        return relatedToName;
    }

    public void setRelatedToName(String relatedToName) {
        this.relatedToName = relatedToName;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getQuery_field() {
        return query_field;
    }

    public void setQuery_field(String query_field) {
        this.query_field = query_field;
    }

    public void setAttributes(JSONObject attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public String getRsplus__Media_Json__c() {
        return rsplus__Media_Json__c;
    }

    public void setRsplus__Media_Json__c(String rsplus__Media_Json__c) {
        this.rsplus__Media_Json__c = rsplus__Media_Json__c;
    }

    public String getRsplus__Message__c() {
        return rsplus__Message__c;
    }

    public void setRsplus__Message__c(String rsplus__Message__c) {
        this.rsplus__Message__c = rsplus__Message__c;
    }

    public String getRsplus__Number__c() {
        return rsplus__Number__c;
    }

    public void setRsplus__Number__c(String rsplus__Number__c) {
        this.rsplus__Number__c = rsplus__Number__c;
    }

    public String getRsplus__Reated_To_Name_Formula__c() {
        return rsplus__Reated_To_Name_Formula__c;
    }

    public void setRsplus__Reated_To_Name_Formula__c(String rsplus__Reated_To_Name_Formula__c) {
        this.rsplus__Reated_To_Name_Formula__c = rsplus__Reated_To_Name_Formula__c;
    }

    public String getRsplus__Related_to_name__c() {
        return rsplus__Related_to_name__c;
    }

    public void setRsplus__Related_to_name__c(String rsplus__Related_to_name__c) {
        this.rsplus__Related_to_name__c = rsplus__Related_to_name__c;
    }

    public String getRsplus__Related_To__c() {
        return rsplus__Related_To__c;
    }

    public void setRsplus__Related_To__c(String rsplus__Related_To__c) {
        this.rsplus__Related_To__c = rsplus__Related_To__c;
    }

    public String getRsplus__Related_User__c() {
        return rsplus__Related_User__c;
    }

    public void setRsplus__Related_User__c(String rsplus__Related_User__c) {
        this.rsplus__Related_User__c = rsplus__Related_User__c;
    }

    public String getRsplus__Sender_ID__c() {
        return rsplus__Sender_ID__c;
    }

    public void setRsplus__Sender_ID__c(String rsplus__Sender_ID__c) {
        this.rsplus__Sender_ID__c = rsplus__Sender_ID__c;
    }

    public String getRsplus__Status__c() {
        return rsplus__Status__c;
    }

    public void setRsplus__Status__c(String rsplus__Status__c) {
        this.rsplus__Status__c = rsplus__Status__c;
    }

    public String getRsplus__Submitted_On__c() {
        return rsplus__Submitted_On__c;
    }

    public void setRsplus__Submitted_On__c(String rsplus__Submitted_On__c) {
        this.rsplus__Submitted_On__c = rsplus__Submitted_On__c;
    }

    public String getRsplus__Type__c() {
        return rsplus__Type__c;
    }

    public void setRsplus__Type__c(String rsplus__Type__c) {
        this.rsplus__Type__c = rsplus__Type__c;
    }

    public String getRsplus__SMS_ID__c() {
        return rsplus__SMS_ID__c;
    }

    public void setRsplus__SMS_ID__c(String rsplus__SMS_ID__c) {
        this.rsplus__SMS_ID__c = rsplus__SMS_ID__c;
    }

    public String getRsplus__External_Id_Txt__c() {
        return rsplus__External_Id_Txt__c;
    }

    public void setRsplus__External_Id_Txt__c(String rsplus__External_Id_Txt__c) {
        this.rsplus__External_Id_Txt__c = rsplus__External_Id_Txt__c;
    }

    public String getRsplus__Read__c() {
        return rsplus__Read__c;
    }

    public void setRsplus__Read__c(String rsplus__Read__c) {
        this.rsplus__Read__c = rsplus__Read__c;
    }

    public String getRsplus__Status_Code__c() {
        return rsplus__Status_Code__c;
    }

    public void setRsplus__Status_Code__c(String rsplus__Status_Code__c) {
        this.rsplus__Status_Code__c = rsplus__Status_Code__c;
    }

    public String getRsplus__Status_Message__c() {
        return rsplus__Status_Message__c;
    }

    public void setRsplus__Status_Message__c(String rsplus__Status_Message__c) {
        this.rsplus__Status_Message__c = rsplus__Status_Message__c;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }
}

