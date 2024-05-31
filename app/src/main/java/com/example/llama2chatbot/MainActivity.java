package com.example.llama2chatbot;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    MessageAdapter adapter;
    String questionUser = "";
    RecyclerView recyclerView;
    ImageView ivClear;
    EditText edtMessageSend;
    public static ImageView iv_send;
    List<Message> messageList;

    String username;

    private String stringToken = "LL-tVOCERB8kpdywb4lsVcZbBCGJmOZDVH70LrPUTX98Kqa5MoK9HsJzc9kyc3AwR02";
    private String stringURLEndPoint = "https://api.llama-api.com/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.chatRecyclerView);
        iv_send = findViewById(R.id.iv_send);
        edtMessageSend = findViewById(R.id.edtMessageSend);
        messageList = new ArrayList<>();
        messageList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        username = getIntent().getStringExtra("name");
        adapter = new MessageAdapter(messageList, this,username);
        recyclerView.setAdapter(adapter);

        iv_send.setOnClickListener(v -> {
            String getUserMessage = edtMessageSend.getText().toString().trim();
            if (getUserMessage.equals("") || getUserMessage.isEmpty()) {
                Toast.makeText(this, "Please write Question", Toast.LENGTH_SHORT).show();
            } else {
                questionUser = getUserMessage;
                addToChatWithOutResponse(getUserMessage + " ", Message.SENT_BY_ME, "");
                addToChatWithOutResponse("", Message.SENT_BY_BOT, "typing...");
                getResponse(questionUser);
                edtMessageSend.setText("");
                iv_send.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtMessageSend.getWindowToken(), 0);
            }
        });



    }

    private void addToChatWithOutResponse(String messageQuestion, String me, String messageAnswer) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(messageQuestion, me, messageAnswer));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });


    }

    public void getResponse(String questionUser) {
        String stringInputText = questionUser;

        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectMessage = new JSONObject();
        JSONArray jsonObjectMessageArray = new JSONArray();
        try {
            jsonObjectMessage.put("role", "user");
            jsonObjectMessage.put("content", stringInputText);

            jsonObjectMessageArray.put(0, jsonObjectMessage);
            jsonObject.put("messages", jsonObjectMessageArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                stringURLEndPoint,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String stringOutput = response.getJSONArray("choices")
                                    .getJSONObject(0)
                                    .getJSONObject("message")
                                    .getString("content");

                            Log.d("stringOutput", stringOutput);
                            runOnUiThread(() -> {
                                addToChat("", Message.SENT_BY_BOT, stringOutput);
                            });

                        } catch (JSONException e) {
                            runOnUiThread(() -> {
                                addToChat("", Message.SENT_BY_BOT, "No Response found");
                            });
                            throw new RuntimeException(e);

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                runOnUiThread(() -> {
                    addToChat("", Message.SENT_BY_BOT, "No Response found");
                });
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapHeader = new HashMap<>();
                mapHeader.put("Content-Type", "application/json");
                mapHeader.put("Authorization", "Bearer " + stringToken);
                return mapHeader;
            }
        };
        int intTimeoutPeriod = 60000; //60 seconds
        RetryPolicy retryPolicy = new DefaultRetryPolicy(intTimeoutPeriod,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        jsonObjectRequest.setRetryPolicy(retryPolicy);
        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }

    private void addToChat(String messageQuestion, String me, String messageAnswer) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.remove(messageList.size() - 1);
                messageList.add(new Message(messageQuestion, me, messageAnswer));
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }
        });
    }

}