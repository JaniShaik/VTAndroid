package com.valuetext1android.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JANI SHAIK on 14/01/2020
 */

public class SMS implements Serializable {

    @SerializedName("sms")
    private String sms;

    @SerializedName("senderId")
    private String senderId;

    @SerializedName("smsId")
    private String smsId;

    @SerializedName("statusCode")
    private String statusCode;

    @SerializedName("statusMsg")
    private String statusMsg;

    @SerializedName("statusDescription")
    private String statusDescription;

    @SerializedName("mobileNumber")
    private String mobileNumber;

    @SerializedName("clientId")
    private String clientId;

    @SerializedName("messageCount")
    private String messageCount;

    @SerializedName("mediaCount")
    private String mediaCount;

    @SerializedName("deliveryModes")
    private String deliveryModes;

    @SerializedName("messageCost")
    private String messageCost;

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(String messageCount) {
        this.messageCount = messageCount;
    }

    public String getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(String mediaCount) {
        this.mediaCount = mediaCount;
    }

    public String getDeliveryModes() {
        return deliveryModes;
    }

    public void setDeliveryModes(String deliveryModes) {
        this.deliveryModes = deliveryModes;
    }

    public String getMessageCost() {
        return messageCost;
    }

    public void setMessageCost(String messageCost) {
        this.messageCost = messageCost;
    }
}
