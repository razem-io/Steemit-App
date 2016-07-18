package io.razem.steemitapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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
    final List<Discussion> discussions = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Discussion.class, new DiscussionDeserializer());
        final Gson gson = builder.create();

        setContentView(R.layout.activity_main);

        final ListView list = (ListView) findViewById(R.id.list);
        final DiscussionAdapter adapter = new DiscussionAdapter(this, discussions);

        list.setAdapter(adapter);

        final Runnable run = new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        };

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
                                "\"method\": \"get_discussions_by_trending\", " +
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

        list.setTextFilterEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(MainActivity.this, DiscussionActivity.class);
                Discussion discussion = discussions.get(position);
                intent.putExtra(DiscussionFragment.EXTRA_DISCUSSIONS, discussion);

                startActivity(intent);
            }
        });
    }
}
