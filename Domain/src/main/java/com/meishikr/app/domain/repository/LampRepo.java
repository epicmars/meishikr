package com.meishikr.app.domain.repository;

import com.meishikr.app.domain.dto.res.ResLatestLamps;
import com.meishikr.app.domain.entity.post.Lamp;

import java.util.List;

import rx.Observable;

/**
 * Created by yinhang on 2016/12/20.
 */

public interface LampRepo {

    Observable<ResLatestLamps> latestLamps(int page, int count);

    Observable<Long> save(Lamp lamp);

    Observable<Lamp> load(long id);

    Observable<Lamp> load(String uuid);

    Observable<List<Lamp>> myLocalLamps();
}
