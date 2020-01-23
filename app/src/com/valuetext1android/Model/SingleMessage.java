package com.valuetext1android.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by JANI SHAIK on 14/01/2020
 */
public class SingleMessage implements Serializable {

    @SerializedName("balance")
    private String balance;

    @SerializedName("result")
    private ArrayList<SMS> result;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public ArrayList<SMS> getResult() {
        return result;
    }

    public void setResult(ArrayList<SMS> result) {
        this.result = result;
    }
}
