package com.valuetext1android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.valuetext1android.Model.SmsMsgBucket;
import com.valuetext1android.R;
import com.valuetext1android.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JANI SHAIK on 28/12/2019
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<SmsMsgBucket> listSms;
    private onChatListener monChatListener;

    public ChatAdapter(@NonNull Context context, ArrayList<SmsMsgBucket> listSms, onChatListener OnChatListener) {
        this.mContext = context;
        this.listSms = listSms;
        this.monChatListener = OnChatListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        return new ViewHolder(layout, monChatListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final SmsMsgBucket smsMsgBucket = listSms.get(position);

       /* Log.e("Related To", smsMsgBucket.getRsplus__Related_To__c());
        Log.e("Related To Name Formula", smsMsgBucket.getRsplus__Reated_To_Name_Formula__c());
        Log.e("Phone Number", smsMsgBucket.getRsplus__Number__c());*/

        /*String name = Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Related_to_name__c()) ? smsMsgBucket.getRsplus__Related_to_name__c() :
                (Utils.isStringNotNullOrEmpty(smsMsgBucket.getRsplus__Reated_To_Name_Formula__c()) ? smsMsgBucket.getRsplus__Reated_To_Name_Formula__c() :
                        smsMsgBucket.getRsplus__Number__c());*/

        holder.chatNameText.setText(smsMsgBucket.getRelatedToName());
        holder.chatDescriptionText.setText(smsMsgBucket.getRsplus__Message__c());
        holder.chatDateText.setText(smsMsgBucket.getRsplus__Submitted_On__c());
        if (getRandomNumber() < 2) {
            holder.chatDateText.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
            holder.chatNotifsText.setVisibility(View.VISIBLE);
        } else
            holder.chatNotifsText.setVisibility(View.INVISIBLE);
        // change the icon for Windows and iPhone
        /*if(position==4){
            holder.chatDescriptionText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
        }
        if(position==5){
            holder.chatDescriptionText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_photo_camera, 0, 0, 0);
        }*/
    }

    @Override
    public int getItemCount() {
        return listSms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView chatNameText, chatDateText, chatNotifsText, chatDescriptionText;
        CircleImageView icon;
        View container;
        onChatListener OnChatListener;
        RelativeLayout relative;

        public ViewHolder(@NonNull View itemView, onChatListener OnChatListener) {

            super(itemView);
            container = itemView;
            container.setClickable(true);
            container.setOnClickListener(this);
            icon = itemView.findViewById(R.id.icon);
            chatNameText = itemView.findViewById(R.id.chat_name_txt);
            chatDateText = itemView.findViewById(R.id.chat_date_txt);
            chatNotifsText = itemView.findViewById(R.id.chat_notifs_txt);
            chatDescriptionText = itemView.findViewById(R.id.chat_description);
            this.OnChatListener = OnChatListener;

            itemView.setOnClickListener(this);
            //this.onChatListener= onChatListener;
        }

        @Override
        public void onClick(View v) {
            // OnChatListener.onChatClick(getAdapterPosition());
            try {
                OnChatListener.onChatClick(getPosition());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    public interface onChatListener {
        void onChatClick(int position) throws UnsupportedEncodingException;
    }


    private int getRandomNumber() {
        Random random = new Random();
        int x = random.nextInt(5);
        if (x == 0) {
            return 0;
        } else if (x == 1) {
            return 1;
        } else if (x == 2) {
            return 2;
        } else
            return 3;

    }
}

