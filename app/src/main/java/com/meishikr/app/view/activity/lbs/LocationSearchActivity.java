package com.meishikr.app.view.activity.lbs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.LocationSource;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiSearch;
import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.meishikr.app.R;
import com.sin2pi.brick.components.base.adapter.RecyclerAdapter;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ActivityLocationSearchBinding;
import com.meishikr.app.domain.entity.lbs.Location;
import com.meishikr.app.utils.LocationMapper;
import com.meishikr.app.base.BaseActivity;
import com.meishikr.app.view.activity.lbs.amap.LocationClient;
import com.meishikr.app.view.activity.lbs.amap.PoiSearchHelper;
import com.meishikr.app.view.viewholder.PoiItemViewHolder;

import java.util.List;


@BindLayout(R.layout.activity_location_search)
public class LocationSearchActivity extends BaseActivity<ActivityLocationSearchBinding> {

    private static final String EXTRAS_LOCATION = "com.meishikr.app.view.activity.lbs.EXTRAS_LOCATION";

    private static final int PAGE_SIZE = 12;
    private static final int MAX_PAGE_NUM = 3;

    private PoiSearchHelper poiSearchHelper;
    private Location myLocation;
    private AMapLocation aMapLocation;
    private LocationClient locationClient;
    private int page = 0;
    private String queryStr;

    public static void launch(Context context) {
        Intent intent = new Intent(context, LocationSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(binding.toolbar);

//        poiSearchHelper = PoiSearchHelper.getInstance();
        poiSearchHelper = new PoiSearchHelper(this);
        locationClient = new LocationClient(getApplicationContext(), true);

        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) binding.search.findViewById(R.id.search_src_text);
        if (null != searchAutoComplete) {

        }

        RecyclerAdapter adapter = new RecyclerAdapter();
        adapter.register(PoiItemViewHolder.class);
        binding.content.recycler.setAdapter(adapter);
        binding.content.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.content.recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        binding.content.spring.setHeader(new AliHeader(this, true));
        binding.content.spring.setFooter(new AliFooter(this, true));
        binding.content.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                searchNearbyLocations(myLocation, queryStr, page = 0);
            }

            @Override
            public void onLoadmore() {
                searchNearbyLocations(myLocation, queryStr, ++page);
            }
        });

        locationClient.activate(new LocationSource.OnLocationChangedListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                aMapLocation = (AMapLocation) location;
                myLocation = LocationMapper.newLocation(aMapLocation);
            }
        });

        poiSearchHelper.setListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(com.amap.api.services.poisearch.PoiResult result, int rCode) {
//                dissmissProgressDialog();// 隐藏对话框
                if (rCode == 1000) {
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        // 取得搜索到的poiitems有多少页
                        List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                        List<SuggestionCity> suggestionCities = result
                                .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                        if (poiItems != null && poiItems.size() > 0) {
                            if (page == 0) {
                                adapter.addAll(poiItems, true);
                            } else if (page > 0) {
                                adapter.addAll(poiItems);
                            }
                        } else if (suggestionCities != null
                                && suggestionCities.size() > 0) {


                        } else {



                        }
                    } else {


                    }
                } else {


                }
                binding.content.spring.onFinishFreshAndLoad();
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });

        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryStr = query;
                searchNearbyLocations(myLocation, query, page = 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        locationClient.deactivate();
        super.onDestroy();
    }

    private void searchNearbyLocations(Location location, String keyword, int page) {
        if (null == location || page < 0)
            return;
        poiSearchHelper.searchNearby(keyword, PoiSearchHelper.ALL_POI_TYPES, location.getCityCode(), location.getLatitude(), location.getLongitude(), 500, PAGE_SIZE, page);
    }

}
