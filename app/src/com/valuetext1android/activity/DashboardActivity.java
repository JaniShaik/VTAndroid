package com.valuetext1android.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ArrayAdapter;

import com.salesforce.androidsdk.rest.RestClient;
import com.valuetext1android.adapter.ChatAdapter;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    // Context
    private Context mContext = this;
    // Rest Client
    private RestClient client;
    // RecyclerView
    private RecyclerView chatRV;
    private ChatAdapter chatAdapter;

    String[] values = new String[]{"Jan",
            "Mark",
            "Sundar",
            "Rahul",
            "Trivago",
            "Kevin"
    };
    String[] chatDescription = new String[]{"Hey There! Are you using whatsapp?",
            "All data is...ummm...safe",
            "How is the scholarship going on?",
            "This morning i woke up at night",
            "Kya aapne kabhi online hotel booking kiya hai?",
            "Photo"
    };

    String[] chatDates = new String[]{"08:58",
            "YESTERDAY",
            "05/03/2018",
            "03/03/2018",
            "27/02/2018",
            "26/02/2018"
    };

    final ArrayList<String> chatNameList = new ArrayList<String>();
    final ArrayList<String> chatDescriptionList = new ArrayList<String>();
    final ArrayList<String> chatDatesList = new ArrayList<String>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

