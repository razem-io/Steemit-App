package io.razem.steemitapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import io.razem.steemitapp.R;
import io.razem.steemitapp.model.Discussion;

/**
 * Created by julia on 18.07.2016.
 */
public class DiscussionAdapter extends ArrayAdapter<Discussion> {
    private final Context context;
    private final List<Discussion> discussions;



    public DiscussionAdapter(Context context, List<Discussion> discussions){
        super(context, -1, discussions);

        this.context = context;
        this.discussions = discussions;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder viewHolder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.le_discussion, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.shortBody =(TextView) convertView.findViewById(R.id.short_body);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Discussion discussion = discussions.get(position);

        viewHolder.title.setText(discussion.getTitle());
        viewHolder.shortBody.setText(discussion.getBody());

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView shortBody;
    }
}
