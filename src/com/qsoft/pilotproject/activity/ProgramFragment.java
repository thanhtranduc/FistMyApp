package com.qsoft.pilotproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import com.qsoft.pilotproject.R;
import com.qsoft.pilotproject.adapter.SideBarItemAdapter;
import com.qsoft.pilotproject.model.ProgramTab;

/**
 * User: binhtv
 * Date: 10/17/13
 * Time: 8:59 AM
 */
public class ProgramFragment extends FragmentActivity
{

    FragmentManager manager;
    private ListView lvSlideBar;
    private View drawerView;
    private ImageButton ibProgramBack;
    private DrawerLayout dlSlideBar;

    ProgramTab currentTab = ProgramTab.THUMB_NAIL;
    private RadioGroup rgProgramTab;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);
        manager = getSupportFragmentManager();
        rgProgramTab = (RadioGroup) findViewById(R.id.rgProgramTab);
        rgProgramTab.setOnCheckedChangeListener(programTabOnCheckChangeListener);
        rgProgramTab.check(R.id.rbThumbnail);
        dlSlideBar = (DrawerLayout) findViewById(R.id.drawer_layout);
        lvSlideBar = (ListView) findViewById(R.id.lvSlideBar);
        drawerView = findViewById(R.id.left_drawer);
        setListViewSlideBar();
        lvSlideBar.setOnItemClickListener(itemSideBarClickListner);
        ibProgramBack = (ImageButton) findViewById(R.id.ibProgramBack);

        ibProgramBack.setOnClickListener(ibProgramBackOnClickListener);
        startContentPlayerFragment();
    }
    View.OnClickListener ibProgramBackOnClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View view)
        {
            Intent intent = new Intent(ProgramFragment.this, HomeFragment.class);
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

    public void setListViewSlideBar()
    {
        SideBarItemAdapter sideBarItemAdapter = new SideBarItemAdapter(this, R.layout.menu, HomeFragment.SIDE_BAR_ITEMS);
        lvSlideBar.setAdapter(sideBarItemAdapter);
    }

    RadioGroup.OnCheckedChangeListener programTabOnCheckChangeListener = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i)
        {
            int checkedRbTab = rgProgramTab.getCheckedRadioButtonId();
            switch (checkedRbTab)
            {
                case R.id.rbThumbnail:
                    currentTab = ProgramTab.THUMB_NAIL;
                    break;
                case R.id.rbDetail:
                    currentTab = ProgramTab.DETAIL;
                    break;
                case R.id.rbComment:
                    currentTab = ProgramTab.COMMENT;
                    break;
            }
            updateProgramFragment();
        }
    };

    private void startContentPlayerFragment()
    {
        Fragment playerFragment = manager.findFragmentById(R.id.contentPlayerFragment);
        if (playerFragment == null)
        {
            playerFragment = new ContentPlayerFragment();
            manager.beginTransaction().add(R.id.contentPlayerFragment, playerFragment).commit();
        }

    }

    private void updateProgramFragment()
    {
        Fragment fragmentContainer = manager.findFragmentById(R.id.fragmentContainer);
        switch (currentTab)
        {
            case DETAIL:
                fragmentContainer = new DetailFragment();
                break;
            case COMMENT:
                fragmentContainer = new CommentFragment();
                break;
            case THUMB_NAIL:
                fragmentContainer = new ThumbnailFragment();
                break;
        }

        manager.beginTransaction().replace(R.id.fragmentContainer, fragmentContainer).commit();

    }


}