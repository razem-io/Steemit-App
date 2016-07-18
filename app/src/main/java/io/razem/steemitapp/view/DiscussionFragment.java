package io.razem.steemitapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import io.razem.steemitapp.R;
import io.razem.steemitapp.model.Discussion;
import us.feras.mdv.MarkdownView;

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

        MarkdownView markdownView = (MarkdownView) view.findViewById(R.id.markdownView);

        Intent intent = getActivity().getIntent();
        Discussion discussion = (Discussion) intent.getSerializableExtra(EXTRA_DISCUSSIONS);
        markdownView.loadMarkdown(discussion.getBody());

        return view;
    }
}
