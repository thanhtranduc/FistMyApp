package com.qsoft.pilotproject.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.model.Feed;

import java.util.List;

/**
 * User: binhtv
 * Date: 10/15/13
 * Time: 8:35 AM
 */
public class ArrayFeedAdapter extends ArrayAdapter<Feed>
{
    private final Activity context;
    private final List<Feed> feeds;

    static class FeedHolder
    {
        public ImageView imProfile;
        public TextView tvTitle;
        public TextView tvComposer;
        public TextView tvLikeStatus;
        public TextView tvCommentStatus;
        public TextView tvLastUpdate;
    }

    public ArrayFeedAdapter(Activity context, List<Feed> feeds)
    {
        super(context, R.layout.feed, feeds);
        this.context = context;
        this.feeds = feeds;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View rowFeed = convertView;
        if (convertView == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            rowFeed = inflater.inflate(R.layout.feed, null);
            FeedHolder feedHolder = new FeedHolder();
            feedHolder.imProfile = (ImageView) rowFeed.findViewById(R.id.imProfile);
            feedHolder.tvTitle = (TextView) rowFeed.findViewById(R.id.tvTitleFeed);
            feedHolder.tvComposer = (TextView) rowFeed.findViewById(R.id.tvComposer);
            feedHolder.tvLikeStatus = (TextView) rowFeed.findViewById(R.id.tvLike);
            feedHolder.tvCommentStatus = (TextView) rowFeed.findViewById(R.id.tvComment);
            feedHolder.tvLastUpdate = (TextView) rowFeed.findViewById(R.id.tvLastUpdateStatus);
            rowFeed.setTag(feedHolder);
        }
        FeedHolder feedHolder = (FeedHolder) rowFeed.getTag();
        feedHolder.tvTitle.setText(feeds.get(position).getTitle());
        feedHolder.tvComposer.setText(feeds.get(position).getComposer());
        feedHolder.tvLikeStatus.setText(new StringBuilder().append("Like: ").append(feeds.get(position).getLikeNumber()));
        feedHolder.tvCommentStatus.setText(new StringBuilder().append("Comment: ").append(feeds.get(position).getCommentNumber()));
        feedHolder.tvLastUpdate.setText(feeds.get(position).getUpdateStatus());
        return rowFeed;
    }
}
