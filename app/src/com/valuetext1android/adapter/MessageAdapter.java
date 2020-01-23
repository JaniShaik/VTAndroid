package com.valuetext1android.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.valuetext1android.Model.Chat;
import com.valuetext1android.R;
import com.valuetext1android.activity.MessageChat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Chat>{

    private List<Chat> msglist = new ArrayList<Chat>();
    private LinearLayout layout;
    private Context mContext;

    public MessageAdapter(@NonNull Context context, int resource, ArrayList<Chat> msglist) {
        super(context, resource);
        this.mContext = context;
        this.msglist.addAll(msglist);
    }

    public void add(Chat object) {
        msglist.add(object);
        notifyDataSetChanged();
        super.add(object);
    }


    @Override
    public int getCount()
    {
        return this.msglist.size();
    }

    public Chat getItem(int index)
    {
        return this.msglist.get(index);
    }

    public View getView(int position, View  ConvertView, ViewGroup parent){

        View v=ConvertView;
        if(v==null){
            LayoutInflater inflater=(LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.msgformat,parent,false);
        }
        layout = v.findViewById(R.id.msg1);
        Chat chatMessage = getItem(position);
        TextView e=  v.findViewById(R.id.text_message_body);
        TextView e1 = v.findViewById(R.id.text_message_time);
        e.setText(chatMessage.getRsplus__Message__c());

        //e.setBackgroundResource(chatMessage.left ? R.drawable.sendmsg:R.drawable.receive_msg);

        e1.setText(chatMessage.getRsplus__Submitted_On__c());


        TextView e2=v.findViewById(R.id.text_message_time1);

        e2.setText(chatMessage.getRsplus__Submitted_On__c());

        final ImageView imagesend = v.findViewById(R.id.imageview);
        final LinearLayout imageLL = v.findViewById(R.id.imageLL);

        if(chatMessage.getImageURI()!=null) {
            imageLL.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(chatMessage.getImageURI()).into(imagesend);
        }
        else{
            imageLL.setVisibility(View.GONE);
        }

        final ImageView filesend = v.findViewById(R.id.file);
        final LinearLayout filell = v.findViewById(R.id.filell);
        if(chatMessage.getFileURI()!=null) {
            filell.setVisibility(View.VISIBLE);
            generateImageFromPdf(chatMessage.getFileURI(), filesend);
            //
            // GenerateImageFromPDF(mContext)
        }
        else{
            filell.setVisibility(View.GONE);
        }

        e.setText(chatMessage.getRsplus__Message__c());


        //Audio picking reference
        final ImageView audiosend=v.findViewById(R.id.audioView);
        final LinearLayout audioLL=v.findViewById(R.id.audioLL);
        if(chatMessage.getAudioURL()!=null) {
            audioLL.setVisibility(View.VISIBLE);
        } else {
            audioLL.setVisibility(View.GONE);
        }

        if (chatMessage.getRsplus__Type__c().equalsIgnoreCase("inbound")) {
            layout.setGravity(Gravity.LEFT);
            //layout.setGravity(chatMessage.left? Gravity.LEFT:Gravity.RIGHT);
        } else {
            layout.setGravity(Gravity.RIGHT);
           // layout.setGravity(chatMessage.right? Gravity.RIGHT:Gravity.LEFT);
        }

        return v;

    }
    public Bitmap decodeToBitmap(byte[] decodeByte){
        return BitmapFactory.decodeByteArray(decodeByte,0,decodeByte.length);
    }

    public void generateImageFromPdf(Uri pdfUri, ImageView imageView) {
        int pageNumber = 0;
        PdfiumCore pdfiumCore = new PdfiumCore(mContext);
        Toast.makeText(mContext, "Gen", Toast.LENGTH_LONG).show();
        try {
            //http://www.programcreek.com/java-api-examples/index.php?api=android.os.ParcelFileDescriptor
            ParcelFileDescriptor fd = mContext.getContentResolver().openFileDescriptor(pdfUri, "r");
            PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
            pdfiumCore.openPage(pdfDocument, pageNumber);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
            saveImage(bmp, imageView);
            pdfiumCore.closeDocument(pdfDocument); // important!
        } catch(Exception e) {
            //todo with exception
        }
    }

    public final static String FOLDER = Environment.getExternalStorageDirectory() + "/PDF";

    private void saveImage(Bitmap bmp, ImageView imageView) {
        FileOutputStream out = null;
        Toast.makeText(mContext, "Save", Toast.LENGTH_LONG).show();
        try {
            File folder = new File(FOLDER);
            if (!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "PDF.png");
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            //imageView.setImageBitmap(bmp);
            imageView.setImageDrawable(mContext.getDrawable(R.drawable.filepdf));
            notifyDataSetChanged();
        } catch (Exception e) {
            //todo with exception
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (Exception e) {
                //todo with exception
            }
        }
    }

}

