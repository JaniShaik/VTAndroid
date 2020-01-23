package com.valuetext1android.Model;

import android.net.Uri;

import com.valuetext1android.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class Chat {


    public boolean right;
    private Uri fileURI;
    private Uri ImageURI;
    private Uri AudioURL;
    private JSONObject attributes;
    private String Id;
    private String Name;
    private String rsplus__Media_Json__c;
    private String rsplus__Message__c;
    private String rsplus__Submitted_On__c;
    private String rsplus__Type__c;
    private String rsplus__Status__c;
    private String rsplus__SMS_ID__c;
    private String rsplus__External_Id_Txt__c;
    private String rsplus__Read__c;
    private String rsplus__Related_To__c;
    private String rsplus__Number__c;
    private String rsplus__Reated_To_Name_Formula__c;
    private String rsplus__Related_to_name__c;
    private String rsplus__Related_User__c;
    private String rsplus__Sender_ID__c;
    private String rsplus__Channel__c;
    private String rsplus__Processed_Channel__c;

    public boolean left;
    public String message;
    private Uri ImageUri;


    public void setImageUri(Uri imageUri) {
        ImageUri = imageUri;
    }

    public Chat(JSONObject mchat) throws JSONException {
        if (mchat.has("attributes")) {
            setAttributes(mchat.getJSONObject("attributes"));
        }
        if(mchat.has("id")){
            setId(Utils.checkString(mchat.getString("Id")));
        }
        if (mchat.has("Name")) {
            setName(Utils.checkString(mchat.getString("Name")));
        }
        if (mchat.has("rsplus__Media_Json__c")) {
            setRsplus__Media_Json__c(Utils.checkString(mchat.getString("rsplus__Media_Json__c")));
        }
        if (mchat.has("rsplus__Message__c")) {
            setRsplus__Message__c(Utils.checkString(mchat.getString("rsplus__Message__c")));
        }
        if (mchat.has("rsplus__Submitted_On__c")) {
            setRsplus__Submitted_On__c(Utils.checkString(mchat.getString("rsplus__Submitted_On__c")));
        }
        if (mchat.has("rsplus__Type__c")) {
            setRsplus__Type__c(Utils.checkString(mchat.getString("rsplus__Type__c")));
        }
        if (mchat.has("rsplus__Status__c")) {
            setRsplus__Status__c(Utils.checkString(mchat.getString("rsplus__Status__c")));
        }
        if(mchat.has("rsplus__SMS_ID__c")){
            setRsplus__SMS_ID__c(Utils.checkString(mchat.getString("rsplus__SMS_ID__c")));
        }
        if(mchat.has("rsplus__External_Id_Txt__c")){
            setRsplus__External_Id_Txt__c(Utils.checkString(mchat.getString("rsplus__External_Id_Txt__c")));
        }
        if(mchat.has("rsplus__Read__c")){
            setRsplus__Read__c(Utils.checkString(mchat.getString("rsplus__Read__c")));
        }

        if (mchat.has("rsplus__Related_To__c")) {
            setRsplus__Related_To__c(Utils.checkString(mchat.getString("rsplus__Related_To__c")));
        }
        if (mchat.has("rsplus__Number__c")) {
            setRsplus__Number__c(Utils.checkString(mchat.getString("rsplus__Number__c")));
        }

        if (mchat.has("rsplus__Reated_To_Name_Formula__c")) {
            setRsplus__Reated_To_Name_Formula__c(Utils.checkString(mchat.getString("rsplus__Reated_To_Name_Formula__c")));
        }
        if (mchat.has("rsplus__Related_to_name__c")) {
            setRsplus__Related_to_name__c(Utils.checkString(mchat.getString("rsplus__Related_to_name__c")));
        }
        if (mchat.has("rsplus__Related_User__c")) {
            setRsplus__Related_User__c(Utils.checkString(mchat.getString("rsplus__Related_User__c")));
        }
        if (mchat.has("rsplus__Sender_ID__c")) {
            setRsplus__Sender_ID__c(Utils.checkString(mchat.getString("rsplus__Sender_ID__c")));
        }
        if (mchat.has("rsplus__Channel__c")) {
            setRsplus__Channel__c(Utils.checkString(mchat.getString("rsplus__Channel__c")));
        }
        if (mchat.has("rsplus__Processed_Channel__c")) {
            setRsplus__Processed_Channel__c(Utils.checkString(mchat.getString("rsplus__Processed_Channel__c")));
        }
        if(mchat.has("Image")){
            setImageURI((Uri) mchat.get("Image"));
        }
        if (mchat.has("Audio")){
            setAudioURL((Uri)mchat.get("Audio"));
        }
        if (mchat.has("File")){
            setFileURI((Uri)mchat.get("File"));
        }

    }


    public JSONObject getAttributes() {
        return attributes;
    }

    public String getRsplus__Channel__c() {
        return rsplus__Channel__c;
    }

    public void setRsplus__Channel__c(String rsplus__Channel__c) {
        this.rsplus__Channel__c = rsplus__Channel__c;
    }

    public String getRsplus__Processed_Channel__c() {
        return rsplus__Processed_Channel__c;
    }

    public void setRsplus__Processed_Channel__c(String rsplus__Processed_Channel__c) {
        this.rsplus__Processed_Channel__c = rsplus__Processed_Channel__c;
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

    public String getRsplus__Related_To__c() {
        return rsplus__Related_To__c;
    }

    public void setRsplus__Related_To__c(String rsplus__Related_To__c) {
        this.rsplus__Related_To__c = rsplus__Related_To__c;
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

    public String getRsplus__Status__c() {
        return rsplus__Status__c;
    }

    public void setRsplus__Status__c(String rsplus__Status__c) {
        this.rsplus__Status__c = rsplus__Status__c;
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

    public Uri getFileURI() {
        return fileURI;
    }

    public void setFileURI(Uri fileURI) {
        this.fileURI = fileURI;
    }

    public Uri getImageURI() {
        return ImageURI;
    }

    public void setImageURI(Uri imageURI) {
        ImageURI = imageURI;
    }

    public Uri getAudioURL() {
        return AudioURL;
    }

    public void setAudioURL(Uri audioURL) {
        AudioURL = audioURL;
    }

    public Uri getImageUri() {
        return ImageUri;
    }

    public Chat(Boolean left, String message) {
            super();
            this.left=left;
            this.message=message;


        }
    }



