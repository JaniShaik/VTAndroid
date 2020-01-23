package com.valuetext1android.Model;

import com.valuetext1android.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by JANI SHAIK on 21/01/2020
 */

public class InboundMessage {

    private String type;
    private String msg;
    private String mediaJson;
    private String userId;
    private String datestamp;
    private String relatedId;
    private String number;
    private String senderId;
    private String smsId;
    private String sfId;
    private String status;
    private String statusMsg;
    private String statusCode;
    private String source;

    public InboundMessage(JSONObject inboundObject) throws JSONException {

        if (inboundObject.has("type")) {
            setType(Utils.checkString(inboundObject.getString("type")));
        }
        if (inboundObject.has("msg")) {
            setMsg(Utils.checkString(inboundObject.getString("msg")));
        }
        if (inboundObject.has("mediaJson")) {
            setMediaJson(Utils.checkString(inboundObject.getString("mediaJson")));
        }
        if (inboundObject.has("userId")) {
            setUserId(Utils.checkString(inboundObject.getString("userId")));
        }
        if (inboundObject.has("DateStamp")) {
            setDatestamp(Utils.checkString(inboundObject.getString("DateStamp")));
        }
        if (inboundObject.has("realtedId")) {
            setRelatedId(Utils.checkString(inboundObject.getString("realtedId")));
        }
        if (inboundObject.has("number")) {
            setNumber(Utils.checkString(inboundObject.getString("number")));
        }
        if (inboundObject.has("senderId")) {
            setSenderId(Utils.checkString(inboundObject.getString("senderId")));
        }
        if (inboundObject.has("smsId")) {
            setSmsId(Utils.checkString(inboundObject.getString("smsId")));
        }
        if (inboundObject.has("sfId")) {
            setSfId(Utils.checkString(inboundObject.getString("sfId")));
        }
        if (inboundObject.has("status")) {
            setStatus(Utils.checkString(inboundObject.getString("status")));
        }
        if (inboundObject.has("statusMsg")) {
            setStatusMsg(Utils.checkString(inboundObject.getString("statusMsg")));
        }
        if (inboundObject.has("statusCode")) {
            setStatusCode(Utils.checkString(inboundObject.getString("statusCode")));
        }
        if (inboundObject.has("source")) {
            setSource(Utils.checkString(inboundObject.getString("source")));
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMediaJson() {
        return mediaJson;
    }

    public void setMediaJson(String mediaJson) {
        this.mediaJson = mediaJson;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDatestamp() {
        return datestamp;
    }

    public void setDatestamp(String datestamp) {
        this.datestamp = datestamp;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public String getSfId() {
        return sfId;
    }

    public void setSfId(String sfId) {
        this.sfId = sfId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
