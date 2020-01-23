package com.valuetext1android.utils;

/**
 *
 */

public class SFDCQueries {

    // rsplus__Type__c='Inbound' and

    public static String HOME_SCREEN_QUERY = "SELECT Id,Name,OwnerId,rsplus__Media_Json__c,rsplus__Message__c,rsplus__SMS_ID__c,rsplus__External_Id_Txt__c,rsplus__Number__c," +
            "rsplus__Reated_To_Name_Formula__c,rsplus__Related_to_name__c,rsplus__Read__c,rsplus__Related_To__c,rsplus__Related_User__c," +
            "rsplus__Sender_ID__c,rsplus__Status__c,rsplus__Submitted_On__c,rsplus__Type__c FROM rsplus__SMS_Bucket__c " +
            "where rsplus__Submitted_On__c != null order by rsplus__Submitted_On__c DESC limit 200";

    public static String CHAT_CONSOLE_QUERY = "SELECT Id,Name,OwnerId,rsplus__Media_Json__c,rsplus__Message__c,rsplus__Number__c," +
            "rsplus__Reated_To_Name_Formula__c,rsplus__Related_to_name__c,rsplus__Related_To__c,rsplus__Read__c,rsplus__SMS_ID__c,rsplus__External_Id_Txt__c,rsplus__Related_User__c," +
            "rsplus__Sender_ID__c,rsplus__Status__c,rsplus__Submitted_On__c,rsplus__Type__c,rsplus__Channel__c, rsplus__Processed_Channel__c FROM rsplus__SMS_Bucket__c ";

}
