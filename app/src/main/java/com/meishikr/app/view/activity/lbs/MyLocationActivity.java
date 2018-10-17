package com.meishikr.app.view.activity.lbs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.LocationSource;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.RecyclerAdapter;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityMyLocationBinding;
import com.meishikr.app.domain.entity.lbs.Location;
import com.meishikr.app.utils.LocationMapper;
import com.meishikr.app.base.BaseActivity;
import com.meishikr.app.view.activity.lbs.amap.LocationClient;
import com.meishikr.app.view.activity.lbs.amap.PoiSearchHelper;
import com.meishikr.app.view.viewholder.AMapLocationViewHolder;
import com.meishikr.app.view.viewholder.AoiItemViewHolder;
import com.meishikr.app.view.viewholder.CityItemViewHolder;
import com.meishikr.app.view.viewholder.PoiItemViewHolder;
import com.meishikr.app.view.viewholder.items.AoiItem;
import com.meishikr.app.view.viewholder.items.CityItem;

import java.io.Serializable;
import java.util.List;


@BindLayout(R.layout.activity_my_location)
public class MyLocationActivity extends BaseActivity<ActivityMyLocationBinding> {

    private static final int REQUEST_CODE_SEARCHED_LOCATION = 101;
    private static final int PAGE_SIZE = 12;
    private static final int MAX_PAGE_NUM = 3;

    private PoiSearchHelper poiSearchHelper;
    private Location myLocation;
    private AMapLocation aMapLocation;
    private LocationClient locationClient;
    private int page = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);
        poiSearchHelper = new PoiSearchHelper(this);
        locationClient = new LocationClient(getApplicationContext(), true);

        RecyclerAdapter adapter = new RecyclerAdapter();
        adapter.register(AMapLocationViewHolder.class);
        adapter.register(PoiItemViewHolder.class);
        adapter.register(CityItemViewHolder.class);
        adapter.register(AoiItemViewHolder.class);
        binding.content.recycler.setAdapter(adapter);
        binding.content.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.content.recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        binding.content.spring.setHeader(new AliHeader(this, true));
        binding.content.spring.setFooter(new AliFooter(this, true));
        binding.content.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                searchNearbyLocations(myLocation, page = 0);
            }

            @Override
            public void onLoadmore() {
                if (page < 0) {
                    return;
                }
                searchNearbyLocations(myLocation, ++page);
            }
        });

        locationClient.activate(new LocationSource.OnLocationChangedListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                aMapLocation = (AMapLocation) location;
                myLocation = LocationMapper.newLocation(aMapLocation);
                searchNearbyLocations(myLocation, page = 0);
            }
        });

        poiSearchHelper.setListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int errorCode) {
                if (LBSConsts.AMap.ErrorCode.SUCCEED == errorCode) {
                    List<PoiItem> poiItems = poiResult.getPois();
                    if (null == poiItems || poiItems.isEmpty()) {

                    }
                    Location location = LocationMapper.newLocation(aMapLocation);
                    if (page == 0) {
                        adapter.clear();
                        if (!TextUtils.isEmpty(aMapLocation.getCity())) {
                            adapter.addOne(new CityItem(location, aMapLocation.getCity()));
                        }
                        if (!TextUtils.isEmpty(aMapLocation.getAoiName())) {
                            adapter.addOne(new AoiItem(location, aMapLocation.getAoiName(), aMapLocation.getAddress()));
                        }
                        adapter.addOne(aMapLocation);
                        adapter.addAll(poiItems);
                    } else if (page > 0) {
                        adapter.addAll(poiItems);
                    }
                } else {

                }
                // 停止刷新
                binding.content.spring.onFinishFreshAndLoad();
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(context, LocationSearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SEARCHED_LOCATION);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        locationClient.deactivate();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (RESULT_OK == resultCode && REQUEST_CODE_SEARCHED_LOCATION == requestCode) {
            Serializable serializable = data.getSerializableExtra(LBSConsts.Extras.SEARCHED_LOCATION);
            if (null == serializable)
                return;
            Intent intent = new Intent();
            intent.putExtra(LBSConsts.Extras.SEARCHED_LOCATION, serializable);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    private void searchNearbyLocations(Location location, int page) {
        if (null == location || page < 0)
            return;
        poiSearchHelper.searchNearby("", PoiSearchHelper.ALL_POI_TYPES, location.getCityCode(), location.getLatitude(), location.getLongitude(), 500, PAGE_SIZE, page);
    }
}
