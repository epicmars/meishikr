package com.meishikr.app.view.activity.lbs.amap;

import android.content.Context;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;

/**
 * Created by yinhang on 2016/12/15.
 */

public class PoiSearchHelper {

    public static final String ALL_POI_TYPES = "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施";

    private Context context;
    private PoiSearch.OnPoiSearchListener listener;

    public PoiSearchHelper(Context context) {
        this.context = context;
    }

    public void setListener(PoiSearch.OnPoiSearchListener listener) {
        this.listener = listener;
    }

    /**
     * 根据关键字搜索兴趣点
     *
     * @param keyword  表示搜索字符串
     * @param type     POI搜索类型，二者选填其一，POI搜索类型共分为以下20种：汽车服务|汽车销售|
     *                 汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
     *                 住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
     *                 金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
     * @param cityCode POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     */
    public void search(String keyword, String type, String cityCode) {
        if (null == context || null == listener)
            return;
        PoiSearch.Query query = new PoiSearch.Query(keyword, type, cityCode);
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setOnPoiSearchListener(listener);
        poiSearch.searchPOIAsyn();
    }

    /**
     * 根据关键字搜索周边兴趣点
     *
     * @param keyword   表示搜索字符串
     * @param type      POI搜索类型，与关键字二者选填其一，POI搜索类型共分为以下20种：汽车服务|汽车销售|
     *                  汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
     *                  住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
     *                  金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
     * @param cityCode  POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
     * @param latitude  搜索中心经度
     * @param longitude 搜索中心纬度
     * @param radius    搜索范围半径
     */
    public void searchNearby(String keyword, String type, String cityCode, double latitude, double longitude, int radius, int pageSize, int page) {
        if (null == context || null == listener)
            return;
        PoiSearch.Query query = new PoiSearch.Query(keyword, type, cityCode);
        query.setPageSize(pageSize);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);//设置查询页码

        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(latitude, longitude), radius));
        poiSearch.setOnPoiSearchListener(listener);
        poiSearch.searchPOIAsyn();
    }
}
