package io.razem.steemitapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.commonmark.Extension;
import org.commonmark.ext.autolink.AutolinkExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;

import java.util.Arrays;
import java.util.List;

import io.razem.steemitapp.R;
import io.razem.steemitapp.controller.Markdown;
import io.razem.steemitapp.model.Discussion;

/**
 * Created by julia on 18.07.2016.
 */
public class DiscussionFragment extends Fragment {
    public final static String EXTRA_DISCUSSIONS = "EXTRA_DISCUSSIONS";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_discussion, container, false);

        WebView markdownView = (WebView) view.findViewById(R.id.markdownView);

        Intent intent = getActivity().getIntent();
        Discussion discussion = (Discussion) intent.getSerializableExtra(EXTRA_DISCUSSIONS);



        String html = "<link rel=\"stylesheet\" type=\"text/css\" href=\"markdown.css\" />" +
                Markdown.toHtml(discussion.getBody());

        markdownView.loadDataWithBaseURL("file:///android_asset/", html, "text/html", "UTF-8", null);

        return view;
    }
}
