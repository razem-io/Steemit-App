package io.razem.steemitapp.view;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.razem.steemitapp.R;
import io.razem.steemitapp.model.Discussion;

/**
 * Created by julia on 18.07.2016.
 */
public class DiscussionAdapter extends ArrayAdapter<Discussion> {
    private final Context context;
    private final List<Discussion> discussions;
    private final NumberFormat numberFormat;



    public DiscussionAdapter(Context context, List<Discussion> discussions){
        super(context, -1, discussions);

        this.context = context;
        this.discussions = discussions;
        this.numberFormat = NumberFormat.getInstance(Locale.getDefault());
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
            viewHolder.shortBody = (TextView) convertView.findViewById(R.id.short_body);
            viewHolder.payout = (TextView) convertView.findViewById(R.id.payout);
            viewHolder.votes = (TextView) convertView.findViewById(R.id.votes);
            viewHolder.comments = (TextView) convertView.findViewById(R.id.comments);

            viewHolder.previewImage = (ImageView) convertView.findViewById(R.id.preview_image);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }



        Discussion discussion = discussions.get(position);

        String payout = "$" + numberFormat.format(Double.valueOf(discussion.getTotalPendingPayoutValue()
                .split(" ")[0]));

        viewHolder.title.setText(discussion.getTitle());
        viewHolder.shortBody.setText(discussion.getBodyShort());
        viewHolder.payout.setText(payout);
        viewHolder.votes.setText(numberFormat.format(discussion.getNetVotes()));
        viewHolder.comments.setText(numberFormat.format(discussion.getChildren()));

        String previewImageUrl = discussion.getPreviewImageUrl();
        if(previewImageUrl != null){
            viewHolder.previewImage.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(previewImageUrl)
                    .into(viewHolder.previewImage);
        }else{
            viewHolder.previewImage.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView shortBody;
        TextView payout;
        TextView votes;
        TextView comments;

        ImageView previewImage;
    }
}
