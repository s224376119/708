package com.example.llama2chatbot;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> implements TextToSpeech.OnInitListener {
    List<Message> messageList;
    Context context;

    RecyclerView recyclerViewSubCategories;

    BottomSheetDialog bottomSheetDialog;



    int Position;
    RecyclerView recyclerView;

    public static TextToSpeech textToSpeech;
    String textToConvert;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int charIndex = 0;
    String username;

    public MessageAdapter(List<Message> messageList, Context context,String username) {
        this.messageList = messageList;
        this.context=context;
        this.username=username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_chat_activity, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Position = position;
        holder.recMessageLayout.setVisibility(View.GONE);
        holder.username.setText(username.substring(0,1));
        if (messageList.size() > 0) {
            Message message = messageList.get(position);
            holder.rr_top.setVisibility(View.GONE);

            holder.username.setText(username.substring(0,1));
            if (!message.getMessageQuestion().equals("")) {
                holder.rr_top.setVisibility(View.VISIBLE);
                holder.tvQuestion.setText(message.getMessageQuestion());
            }
            if (!message.getMessageAnswer().equals("")) {
                holder.recMessageLayout.setVisibility(View.VISIBLE);
                holder.tvRecMessage.setText(message.getMessageAnswer());
                StringBuilder delayedText = new StringBuilder();
                final String originalText = message.getMessageAnswer();
                if (messageList.size() - 1 == position) {
                    holder.tvRecMessage.setText("");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            holder.rr_top.setVisibility(View.GONE);
                            holder.tvRecMessage.setText(delayedText.toString());
                            if (delayedText.length() < originalText.length()) {
                                holder.tvQuestion.setText(message.getMessageQuestion());
                                delayedText.append(originalText.charAt(holder.tvRecMessage.length()));
                                handler.postDelayed(this, 15); // Schedule the next character after a 25-millisecond delay
                                MainActivity.iv_send.setVisibility(View.VISIBLE);
                            }
                        }
                    }, 0);
                }
            }
        }


    }



    @Override
    public int getItemCount() {
        if (messageList.size() == 0) {
            return 1;
        } else {
            return messageList.size();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.UK);

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Handle language not supported or missing data
            } else {
                convertTextToSpeech();
            }
        } else {
            // Handle TTS initialization failure
        }
    }

    private void convertTextToSpeech() {
        textToSpeech.speak(textToConvert, TextToSpeech.QUEUE_FLUSH, null, null);
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecMessage,username;
        TextView tvQuestion;

        RelativeLayout recMessageLayout, rr_top;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecMessage = itemView.findViewById(R.id.tvRecMessage);
            recMessageLayout = itemView.findViewById(R.id.recMessageLayout);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            rr_top = itemView.findViewById(R.id.rr_top);
            username = itemView.findViewById(R.id.username);

        }
    }
}
