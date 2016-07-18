package io.razem.steemitapp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import io.razem.steemitapp.R;

/**
 * Created by julia on 18.07.2016.
 */
public class DiscussionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_discussion);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        DiscussionFragment discussionFragment = new DiscussionFragment();
//        fragmentTransaction.add(R.id.fr_discussion, discussionFragment);
//        fragmentTransaction.commit();
    }
}
