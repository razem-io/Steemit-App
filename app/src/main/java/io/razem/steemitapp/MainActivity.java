package io.razem.steemitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import java.util.ArrayList;
import java.util.List;

import io.razem.steemitapp.controller.DiscussionDeserializer;
import io.razem.steemitapp.model.Discussion;
import io.razem.steemitapp.model.DiscussionResults;
import io.razem.steemitapp.view.DiscussionActivity;
import io.razem.steemitapp.view.DiscussionAdapter;
import io.razem.steemitapp.view.DiscussionFragment;

public class MainActivity extends AppCompatActivity {
    private final static Gson gson = getGson();

    private final static String[] values = new String[] { "trending", "created", "active",
            "cashout", "payout", "votes", "children", "hot" };

    private final List<Discussion> discussions = new ArrayList<>();

    private DiscussionAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Spinner spinner = (Spinner) findViewById(R.id.filter_spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                R.layout.sp_filter_item, values);
        spinnerAdapter.setDropDownViewResource(R.layout.sp_filter_item_dropdown);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(spinnerListener);

        final ListView list = (ListView) findViewById(R.id.list);
        adapter = new DiscussionAdapter(this, discussions);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this, DiscussionActivity.class);
                Discussion discussion = discussions.get(position);
                intent.putExtra(DiscussionFragment.EXTRA_DISCUSSIONS, discussion);

                startActivity(intent);
            }
        });

        fetchData("trending");
    }

    private void fetchData(final String method){
        AsyncHttpClient.getDefaultInstance().websocket("wss://this.piston.rocks", "",
                new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(Exception ex, WebSocket webSocket) {
                        if (ex != null) {
                            ex.printStackTrace();
                            return;
                        }
                        webSocket.send(
                                "{" +
                                        "\"jsonrpc\": \"2.0\", " +
                                        "\"method\": \"get_discussions_by_" + method + "\", " +
                                        "\"params\": [{\"tag\": \"\", \"limit\": 20, " +
                                        "\"filter_tags\": []}], \"id\": 1" +
                                        "}");

                        webSocket.setStringCallback(new WebSocket.StringCallback() {
                            public void onStringAvailable(String s) {
                                DiscussionResults discussionResults = gson.fromJson(s, DiscussionResults.class);

                                discussions.clear();
                                discussions.addAll(discussionResults.getResult());

                                runOnUiThread(run);
                            }
                        });
                    }
                });
    }

    private final Runnable run = new Runnable() {
        public void run() {
            adapter.notifyDataSetChanged();
        }
    };

    private final AdapterView.OnItemSelectedListener spinnerListener = new AdapterView
            .OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            fetchData(values[pos]);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };


    private static Gson getGson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Discussion.class, new DiscussionDeserializer());
        return builder.create();
    }
}
