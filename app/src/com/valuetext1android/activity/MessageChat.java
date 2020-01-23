package com.valuetext1android.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.accounts.UserAccountManager;
import com.salesforce.androidsdk.rest.ApiVersionStrings;
import com.salesforce.androidsdk.rest.RestClient;
import com.salesforce.androidsdk.rest.RestRequest;
import com.salesforce.androidsdk.rest.RestResponse;
import com.salesforce.androidsdk.smartstore.app.SmartStoreSDKManager;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.ui.SalesforceActivity;
import com.valuetext1android.Model.SMS;
import com.valuetext1android.Model.SingleMessage;
import com.valuetext1android.activity.MainActivity;
import com.valuetext1android.Model.Chat;
import com.valuetext1android.Model.SmsMsgBucket;
import com.valuetext1android.R;
import com.valuetext1android.adapter.MessageAdapter;
import com.valuetext1android.networkInterceptor.NoConnectivityException;
import com.valuetext1android.retrofit.ApiClient;
import com.valuetext1android.retrofit.ApiInterface;
import com.valuetext1android.utils.Constants;
import com.valuetext1android.utils.SmartStoreQueries;
import com.valuetext1android.utils.Utils;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.google.GoogleEmojiProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageChat extends SalesforceActivity {

    private Context mContext = this;

    private EditText emsg;
    private Boolean side = false;
    private MessageAdapter messageAdapter;
    private ListView messagesView;
    public static final int IMAGE_PICK_CODE=1002;
    public static final int PERMISSION_CODE=1003;
    private Toolbar actionBar;
    String chatData, relatedToId,senderToId,numberTOc;
    String messageToUser,relatedToUser;

    private Uri imageURL;
    private ImageView file, gallery;
    private ImageView m,contactlist,music;
    BottomSheetDialog dialog;
    BottomSheetBehavior bottomSheetBehavior;

    BottomSheetBehavior behavior;


    private ArrayList<Chat> listChat = new ArrayList<>();

    private RestClient client;

    // User Id
    private String userId;
    // Org Id
    private String orgId;

    // Smart Store
    private SmartStore smartStore;

    // Retrofit
    ApiInterface apiInterface;

    // JSONArray
    JSONArray chatDetailsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_message);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("username");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Emoji
        //EmojiManager.install(new GoogleEmojiProvider());

        // Smart Store
        smartStore = SmartStoreSDKManager.getInstance().getSmartStore();

        chatData = getIntent().getStringExtra(Constants.CHAT_DATA);
        relatedToId = getIntent().getStringExtra(Constants.RELATED_TO);
        senderToId = getIntent().getStringExtra(Constants.SENDER_TO);
        messageToUser = getIntent().getStringExtra(Constants.MESSAGE_C);
        relatedToUser = getIntent().getStringExtra(Constants.RELATED_USER_C);

        try {
            chatDetailsArray = new JSONArray(chatData);

            for(int i=0;i<chatDetailsArray.length();i++){

                JSONArray chatArray = chatDetailsArray.getJSONArray(i);

                JSONObject chatObject = new JSONObject();
                chatObject.put("rsplus__Related_To__c", chatArray.get(0));
                chatObject.put("rsplus__Sender_ID__c", chatArray.get(1));
                chatObject.put("rsplus__Number__c", chatArray.get(2));
                chatObject.put("rsplus__Message__c", chatArray.get(3));
                chatObject.put("rsplus__Channel__c", chatArray.get(4));
                chatObject.put("rsplus__Processed_Channel__c", chatArray.get(5));
                chatObject.put("rsplus__Type__c", chatArray.get(6));

                Chat chat = new Chat(chatObject);
                listChat.add(chat);

                //Chat chat = new Chat(records.getJSONObject(i));
                //listChat.add(chat);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        emsg = findViewById(R.id.edittext_chatbox);


        //google emojis
        final EmojiPopup popup = EmojiPopup.Builder.fromRootView(findViewById(R.id.rootview)).build(emsg);
        ImageButton emojibutton = findViewById(R.id.emojibtn);
        emojibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.toggle();
            }
        });
        ImageView img = findViewById(R.id.imagebtn);
        ImageButton imgadd = findViewById(R.id.imageadd);

        //bottom intializations
        file = findViewById(R.id.doc);
        gallery = findViewById(R.id.gal);
        m=findViewById(R.id.map);
        music=findViewById(R.id.audio);
        contactlist=findViewById(R.id.contacs);




        messageAdapter = new MessageAdapter(getApplicationContext(),R.layout.msgformat, listChat);
        messagesView = findViewById(R.id.messages_view);


        final View bottomSheet = findViewById(R.id.design_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(460);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.getHalfExpandedRatio();

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
                        break;

                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });


        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, Constants.PICK_PHOTO_FOR_AVATAR);
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                }, 1000);
            }


        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String[] mimeTypes =
                        {"application/pdf"};

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
                } else {
                    String mimeTypesStr = "";
                    for (String mimeType : mimeTypes) {
                        mimeTypesStr += mimeType + "|";
                    }
                    intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
                }
                startActivityForResult(Intent.createChooser(intent,"ChooseFile"), Constants.RC);


            }
        });
        contactlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(contactPickerIntent, Constants.RESULT_PICK_CONTACT);
            }
        });
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent;
                intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mpeg");
                startActivityForResult(Intent.createChooser(intent, getString(R.string.select_audio_file_title)), Constants.PICK_MUSIC_CODE);
            }


        });

        emsg.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction()==KeyEvent.ACTION_DOWN) && (keyCode==KeyEvent.KEYCODE_ENTER))
                {
                    return sendChatMessage();
                }
                return false;
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendChatMessage();
            }
        });
        messagesView.setTranscriptMode(messagesView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
       // messagesView.setStackFromBottom(true);
        messagesView.setAdapter(messageAdapter);
        messageAdapter.registerDataSetObserver(
                new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                messagesView.setSelection(messageAdapter.getCount()-1);
            }
        });
        messageAdapter.notifyDataSetChanged();


        imgadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(behavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }

               /* if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        String permissions=(Manifest.permission.READ_EXTERNAL_STORAGE);
                        requestPermissions(new String[]{permissions},PERMISSION_CODE);
                    }else{
                        addImageFromGallery();
                    }
                }else{
                    addImageFromGallery();
                }*/
            }
        });


        final List<UserAccount> users = UserAccountManager.getInstance().getAuthenticatedUsers();
        if(users!=null) {
            for (UserAccount userAccount : users) {
                this.userId = userAccount.getUserId();
                this.orgId = userAccount.getOrgId();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        Log.e("CALLED", "OnActivity Result");
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == Constants.PICK_PHOTO_FOR_AVATAR) {

            //Toast.makeText(mContext,"onActivityResult() for code <" + requestCode + ">",Toast.LENGTH_LONG).show();

            imageURL = data.getData();

            Chat chat = listChat.get(chatDetailsArray.length()-1);
            chat.setImageURI(imageURL);
            //Get image
            // Bitmap newProfilePic = extras.getParcelable("data");
            messageAdapter.add(chat);
            messageAdapter.notifyDataSetChanged();
            //
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);


        }else if(requestCode==Constants.RESULT_PICK_CONTACT)
        {
            //contactPicked(data);
        }else if(requestCode==Constants.RC)
        {
            if(data!=null)
            {

                Uri fileuri=data.getData();
                Chat chat=listChat.get(chatDetailsArray.length()-1);
                chat.setFileURI(fileuri);
                messageAdapter.add(chat);
                messageAdapter.notifyDataSetChanged();
            }
        }else if(requestCode==Constants.PICK_MUSIC_CODE)
        {
            if ((data != null) && (data.getData() != null))
            {
                Uri audioFileUri = data.getData();
                Chat chat=listChat.get(chatDetailsArray.length()-1);
                chat.setAudioURL(audioFileUri);
                //  messagesView.setAudioFile(audioFileUri);
                messageAdapter.add(chat);
                messageAdapter.notifyDataSetChanged();


                // Now you can use that Uri to get the file path, or upload it, ...
            }
        }else if(requestCode==Constants.MAPCODE)
        {
            if (resultCode == RESULT_OK)
            {
                //  Place place = PlacePicker.getPlace(this, data);
            }

        }

    }


    private void sendRequest(String soql) throws UnsupportedEncodingException {
        RestRequest restRequest = RestRequest.getRequestForQuery(ApiVersionStrings.getVersionNumber(this), soql);

        client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {
            @Override
            public void onSuccess(RestRequest request, final RestResponse result) {
                result.consumeQuietly(); // consume before going back to main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

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
                        Toast.makeText(mContext,
                                mContext.getString(R.string.sf__generic_error, exception.toString()),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }


   /* private void addImageFromGallery() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        // intent.setType("Imaage/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:
                if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                  //  addImageFromGallery();
                } else {
                    Toasty.error(getApplicationContext(),"permission denied").show();
                }
        }
    }

   private boolean sendChatMessage() {
       // sendRequest();
        //messageAdapter.add(new Chat(side,emsg.getText().toString()));
        //emsg.setText("");
        //side=!side;
       if (!emsg.getText().toString().isEmpty()) {
           //sendMessage();
           sendSMS(emsg.getText().toString());
       } else {
           Toast.makeText(mContext, "Please enter message", Toast.LENGTH_LONG).show();
       }

       return true;
    }

    @Override
    public void onResume(RestClient client) {
        // Keeping reference to rest client
        this.client = client;

        // Show everything
        //findViewById(R.id.root).setVisibility(View.VISIBLE);
    }

    /**
     * Send SMS
     */
    private void sendSMS(String message) {
        apiInterface = ApiClient.getApiClient(mContext, Constants.API_URL);

        Call<SingleMessage> sendSMSCall = apiInterface.sendMessage(listChat.get(chatDetailsArray.length()-1).getRsplus__Number__c(), message,
                listChat.get(chatDetailsArray.length()-1).getRsplus__Sender_ID__c(), "");
        sendSMSCall.enqueue(new Callback<SingleMessage>() {
            @Override
            public void onResponse(Call<SingleMessage> call, Response<SingleMessage> response) {
                if (response.isSuccessful()) {
                    SingleMessage singleMessage = response.body();
                    // Insert Message Response into SmartStore
                    try {
                        smartStore.create("Message_Response", prepareMessageResponse(singleMessage));
                        //int savedRecords = SmartStoreQueries.countMessageResponseRecords(smartStore);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    String status = singleMessage.getResult().get(0).getStatusMsg();
                    //Log.e("Status", status);
                    if (singleMessage.getResult().get(0).getStatusMsg().equalsIgnoreCase("Sent")){
                        Toasty.success(mContext, "Message sent successfully..!").show();
                    } else {
                        Toasty.error(mContext, singleMessage.getResult().get(0).getStatusDescription()).show();
                    }

                    // Save sent message in local DB
                    final Chat chat = listChat.get(chatDetailsArray.length()-1);
                    chat.setRsplus__Type__c("Outbound");
                    chat.setRsplus__Message__c(emsg.getText().toString());
                    chat.setRsplus__Submitted_On__c(Utils.getCurrentTimeInGMT());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //listChat.add(chat);
                            messageAdapter.add(chat);
                            messageAdapter.notifyDataSetChanged();
                            // Clear EditText
                            emsg.setText(" ");
                        }
                    });

                    new Thread(new Runnable() {
                        public void run(){
                            // Insert into DB
                            try {
                                // Insert into SMS Bucket
                                smartStore.create("SMS_Bucket", createSMSBucket(chat));
                                // Insert into Dashboard Bucket
                                SmartStoreQueries.validateAndInsertDashboardData(
                                        SmartStoreQueries.prepareDashboardBucketData(createSMSBucket(chat)), smartStore);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                } else {
                    // error case
                    //Constants.serverError(mContext, response.code());
                }
            }

            @Override
            public void onFailure(Call<SingleMessage> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.toString());
                if (t instanceof NoConnectivityException) {
                    // No internet connection
                }
            }
        });
    }

    /**
     * Prepare Message Response
     */
    private JSONObject prepareMessageResponse(SingleMessage singleMessage) {
        JSONObject messageResponseObject = new JSONObject();
        SMS sms = singleMessage.getResult().get(0);
        try {
            messageResponseObject.put("balance", singleMessage.getBalance());
            messageResponseObject.put("sms", sms.getSms());
            messageResponseObject.put("senderId", sms.getSenderId());
            messageResponseObject.put("smsId", sms.getSmsId());
            messageResponseObject.put("statusCode", sms.getStatusCode());
            messageResponseObject.put("statusMsg", sms.getStatusMsg());
            messageResponseObject.put("statusDescription", sms.getStatusDescription());
            messageResponseObject.put("mobileNumber", sms.getMobileNumber());
            messageResponseObject.put("clientId", sms.getClientId());
            messageResponseObject.put("messageCount", sms.getMessageCost());
            messageResponseObject.put("mediaCount", sms.getMediaCount());
            messageResponseObject.put("deliveryModes", sms.getDeliveryModes());
            messageResponseObject.put("messageCost", sms.getMessageCost());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return messageResponseObject;
    }

    /**
     * Send message to SFDC
     */
    private void sendMessage() {
        RestRequest restRequest = null;
        try {

            @SuppressWarnings("rawtypes")
            Map createEventInfo = new HashMap();

            createEventInfo.put("rsplus__Related_To__c", relatedToId);
            createEventInfo.put("rsplus__Sender_ID__c", listChat.get(0).getRsplus__Sender_ID__c());
            createEventInfo.put("rsplus__Number__c", listChat.get(0).getRsplus__Number__c());
            createEventInfo.put("rsplus__Message__c", emsg.getText().toString());
           // createEventInfo.put("rsplus__Template_ID__c", addCommentET.getText().toString());
            createEventInfo.put("rsplus__Related_User__c", this.userId);
            //String channel = Utils.isStringNotNullOrEmpty(listChat.get(0)
            createEventInfo.put("rsplus__Channel__c", Utils.isStringNotNullOrEmpty(listChat.get(0).getRsplus__Processed_Channel__c()) ?
                    listChat.get(0).getRsplus__Processed_Channel__c():listChat.get(0).getRsplus__Channel__c());
            //createEventInfo.put("rsplus__Processed_Channel__c", )


            //Format df = DateFormat.getDateFormat(this);
            //createEventInfo.put("ActivityDate", df.format(start));

            restRequest = RestRequest.getRequestForCreate(getString(R.string.api_version), "rsplus__SMS_Bucket__c" ,
                    createEventInfo);


            //Toast.makeText(mContext, "Send", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            //showError(mContext, e);
            return;
        }

        client.sendAsync(restRequest, new RestClient.AsyncRequestCallback() {

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                //showError(mContext, e);
                //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(RestRequest request, RestResponse response) {
                try {

                    JSONObject jsonObject = response.asJSONObject();

                    final Chat chat = listChat.get(chatDetailsArray.length()-1);
                    chat.setRsplus__Type__c("Outbound");
                    chat.setRsplus__Message__c(emsg.getText().toString());
                    chat.setRsplus__Submitted_On__c(Utils.getCurrentTimeInGMT());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //listChat.add(chat);
                            messageAdapter.add(chat);
                            messageAdapter.notifyDataSetChanged();
                            // Clear EditText
                            emsg.setText(" ");
                        }
                    });

                    new Thread(new Runnable() {
                        public void run(){
                            // Insert into DB
                            try {
                                // Insert into SMS Bucket
                                smartStore.create("SMS_Bucket", createSMSBucket(chat));
                                // Insert into Dashboard Bucket
                                SmartStoreQueries.validateAndInsertDashboardData(
                                        SmartStoreQueries.prepareDashboardBucketData(createSMSBucket(chat)), smartStore);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                } catch (Exception e) {
                    e.printStackTrace();
                    //showError(mContext, e);
                }
            }
        });
    }

    // Prepare SMSBucket JSONObject
    private JSONObject createSMSBucket(Chat chat) {

        JSONObject smsObject = new JSONObject();
        try {
            smsObject.put("rsplus__Related_To__c", chat.getRsplus__Related_To__c());
            smsObject.put("rsplus__Sender_ID__c", chat.getRsplus__Sender_ID__c());
            smsObject.put("rsplus__Number__c", chat.getRsplus__Number__c());
            smsObject.put("rsplus__Message__c", chat.getRsplus__Message__c());
            smsObject.put("rsplus__Related_User__c", chat.getRsplus__Related_User__c());
            smsObject.put("rsplus__Channel__c", chat.getRsplus__Channel__c());
            smsObject.put("rsplus__Processed_Channel__c", chat.getRsplus__Processed_Channel__c());
            smsObject.put("rsplus__Media_Json__c", chat.getRsplus__Media_Json__c());
            smsObject.put("rsplus__Submitted_On__c", chat.getRsplus__Submitted_On__c());
            smsObject.put("rsplus__Related_to_name__c", chat.getRsplus__Related_to_name__c());
            smsObject.put("rsplus__External_Id_Txt__c", chat.getRsplus__External_Id_Txt__c());
            smsObject.put("rsplus__Type__c", "Outbound");
            smsObject.put("rsplus__Status__c", chat.getRsplus__Status__c());
            smsObject.put("rsplus__SMS_ID__c", chat.getRsplus__SMS_ID__c());
            smsObject.put("rsplus__Read__c", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return smsObject;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        return  true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }


    public void setActionBar(Toolbar actionBar) {

        this.actionBar = actionBar;
    }

}
