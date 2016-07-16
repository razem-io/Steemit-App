package io.razem.steemitapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final List<String> titles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Gson gson = new Gson();

        setContentView(R.layout.activity_main);

        final ListView list = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, titles);

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
                        Map discussionResults = gson.fromJson(s, Map.class);
                        List entries = (List)discussionResults.get("result");

                        titles.clear();

                        for(Object entry : entries){
                            Map entryMap = (Map)entry;
                            titles.add((String) entryMap.get("title"));
                        }

                        runOnUiThread(run);
                    }
                });
            }
        });

        list.setTextFilterEnabled(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
