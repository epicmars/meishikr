package com.meishikr.app.view.activity.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.meishikr.app.R;
import com.meishikr.app.base.BaseActivity;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityHomeBinding;
import com.meishikr.app.view.activity.auth.LoginActivity;
import com.meishikr.app.view.activity.auth.RegisterActivity;
import com.meishikr.app.view.activity.blog.BlogEditActivity;
import com.meishikr.app.view.activity.blog.MyBlogListActivity;
import com.meishikr.app.view.activity.lamp.LampEditActivity;
import com.meishikr.app.view.activity.lbs.MapActivity;
import com.meishikr.app.view.activity.setting.SettingsActivity;
import com.meishikr.app.view.adapter.HomeNavigationAdapter;

@BindLayout(R.layout.activity_home)
public class HomeActivity extends BaseActivity<ActivityHomeBinding> implements MineFragment.OnFragmentInteractionListener, PostTypeDialogFragment.OnPostTypeClickListener {
    private static final String TAG_HOME = "tab_home";
    private static final String TAG_RANK = "tab_rank";
    private static final String TAG_LAMP = "tab_lamp";
    private static final String TAG_TOPIC = "tag_topic";
    private static final String TAG_MINE = "tab_mine";
    private static final String TAG_MAP = "tag_map";
    //
    private static final String TAG_MEISHIKR = "tag_meishikr"; // 美食家
    private static final String TAG_POST_TYPE = "tag_post_type";

    private static final int OFF_SCREEN_PAGES = 3;

    private MenuItem currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);

        binding.navigationPager.setOffscreenPageLimit(OFF_SCREEN_PAGES);
        binding.navigationPager.setAdapter(new HomeNavigationAdapter(this, getSupportFragmentManager()));
        binding.navigationPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (null != currentItem) {
                    currentItem.setChecked(false);
                }
                currentItem = binding.navigationBottom.getMenu().getItem(position);
                currentItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.navigationBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btn_home:
                        binding.navigationPager.setCurrentItem(0, true);
                        return true;
                    case R.id.btn_topic:
                        binding.navigationPager.setCurrentItem(1, true);
                        return true;
                    case R.id.btn_mine:
                        binding.navigationPager.setCurrentItem(2, true);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        // TODO 若已退出登录，不再显示我的博客？
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatemet
        switch (id){
            case R.id.action_post: {
                new PostTypeDialogFragment().show(getSupportFragmentManager(), TAG_POST_TYPE);
                return true;
            }

            case R.id.action_map: {
                MapActivity.launch(this);
                return true;
            }

            case R.id.action_register:{
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_login:{
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_settings:{
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.action_my_articles:{
                MyBlogListActivity.launch(this);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // rx 传递事件
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPostTypeClick(String item, int position) {
        switch (position) {
            case 0:
                BlogEditActivity.launch(this);
                break;
            case 1:
            case 2:
                LampEditActivity.launch(this, item);
                break;
            default:
                break;
        }
    }
}
