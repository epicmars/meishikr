package com.meishikr.app.view.activity.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.liaoinstan.springview.container.AliFooter;
import com.liaoinstan.springview.container.AliHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.meishikr.app.R;
import com.meishikr.app.base.BaseFragment;
import com.meishikr.app.base.adapter.RecyclerAdapter;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentLampBinding;
import com.meishikr.app.domain.dto.res.ResLatestLamps;
import com.meishikr.app.domain.interactor.post.GetLatestLampsCase;
import com.meishikr.app.view.viewholder.LampViewHolder;

import javax.inject.Inject;

import rx.Subscriber;

@BindLayout(R.layout.fragment_lamp)
public class LampFragment extends BaseFragment<FragmentLampBinding> {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Inject
    GetLatestLampsCase getLatestLampsCase;

    RecyclerAdapter adapter;

    private int page = 1;

    public static LampFragment newInstance(String param1, String param2) {
        LampFragment fragment = new LampFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public LampFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        component.inject(this);

        binding.spring.setHeader(new AliHeader(this.getContext(), true));
        binding.spring.setFooter(new AliFooter(this.getContext(), true));

        adapter = RecyclerAdapter.newInstance();
        adapter.register(LampViewHolder.class);
        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.spring.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                load(page = 1);
            }

            @Override
            public void onLoadmore() {
                load(++page);
            }
        });

        load(page = 1);
    }

    private void load(final int currentPage) {
        getLatestLampsCase.setPage(currentPage)
                .setCount(10)
                .execute(new Subscriber<ResLatestLamps>() {
                    @Override
                    public void onCompleted() {
                        binding.spring.onFinishFreshAndLoad();
                    }

                    @Override
                    public void onError(Throwable e) {
                        binding.spring.onFinishFreshAndLoad();
                    }

                    @Override
                    public void onNext(ResLatestLamps resLatestLamps) {
                        binding.spring.onFinishFreshAndLoad();
                        if (null == resLatestLamps)
                            return;
                        adapter.addAll(resLatestLamps.lamps, currentPage == 1);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
