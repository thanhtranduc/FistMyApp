package com.qsoft.pilotproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.adapter.ArrayFeedAdapter;
import com.qsoft.pilotproject.adapter.CommentAdapter;
import com.qsoft.pilotproject.adapter.SideBarItemAdapter;
import com.qsoft.pilotproject.model.Comment;
import com.qsoft.pilotproject.model.Feed;

import java.util.ArrayList;
import java.util.List;

/**
 * User: binhtv
 * Date: 10/14/13
 * Time: 10:47 AM
 */
public class HomeFragment extends FragmentActivity
{
    public static final String[] SIDE_BAR_ITEMS = new String[]{"Home", "Favorite", "Following", "Audience",
            "Genres", "Setting", "Help Center", "Sign Out"};
    public static final Integer[] SIDE_BAR_ICONS = new Integer[]{
            R.drawable.sidebar_imageicon_home,
            R.drawable.sidebar_image_icon_favorite,
            R.drawable.sidebar_image_icon_following,
            R.drawable.sidebar_image_icon_audience,
            R.drawable.sidebar_image_icon_genres,
            R.drawable.sidebar_image_icon_setting,
            R.drawable.sidebar_image_icon_helpcenter,
            R.drawable.sidebar_image_icon_logout
    };
    private ListView lvFeeds;
    private ListView lvSlideBar;
    private View drawerView;
    private DrawerLayout dlSlideBar;
    Button btMenu;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        lvFeeds = (ListView) findViewById(R.id.lvFeeds);
        ArrayFeedAdapter arrayFeedAdapter = new ArrayFeedAdapter(this, getModel());
        lvFeeds.setAdapter(arrayFeedAdapter);
        dlSlideBar = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvSlideBar = (ListView) findViewById(R.id.lvSlideBar);
        drawerView = findViewById(R.id.left_drawer);
        setListViewSlideBar();
        lvSlideBar.setOnItemClickListener(itemSideBarClickListner);
        lvFeeds.setOnItemClickListener(feedClickListener);
        btMenu = (Button) findViewById(R.id.btMenu);
        btMenu.setOnClickListener(btMenuClickListener);

    }
    AdapterView.OnItemClickListener feedClickListener = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            Intent intent = new Intent(HomeFragment.this,ProgramFragment.class);
            startActivity(intent);
        }
    };

    AdapterView.OnItemClickListener itemSideBarClickListner = new AdapterView.OnItemClickListener()
    {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            // on item click
        }
    };

    public View.OnClickListener btMenuClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            dlSlideBar.openDrawer(drawerView);
        }
    };

    public void setListViewSlideBar()
    {
        SideBarItemAdapter sideBarItemAdapter = new SideBarItemAdapter(this, R.layout.menu, SIDE_BAR_ITEMS);
        lvSlideBar.setAdapter(sideBarItemAdapter);
    }

    private List<Feed> getModel()
    {
        List<Feed> feeds = new ArrayList<Feed>();
        for (int i = 0; i < 20; i++)
        {
            feeds.add(getFeed());
        }
        return feeds;
    }

    public Feed getFeed()
    {
        Feed feed = new Feed();
        feed.setTitle("Sound of Silence");
        feed.setComposer("Mr. Bean");
        feed.setLikeNumber(100);
        feed.setCommentNumber(9);
        feed.setUpdateStatus("5 days ago");
        return feed;
    }
}