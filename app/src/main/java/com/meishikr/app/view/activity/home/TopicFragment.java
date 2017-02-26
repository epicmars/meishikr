package com.meishikr.app.view.activity.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.base.adapter.RecyclerAdapter;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentTopicBinding;
import com.meishikr.app.domain.entity.post.Topic;
import com.meishikr.app.base.BaseFragment;
import com.meishikr.app.view.viewholder.TopicViewHolder;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
@BindLayout(R.layout.fragment_topic)
public class TopicFragment extends BaseFragment<FragmentTopicBinding> {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopicFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TopicFragment newInstance(int columnCount) {
        TopicFragment fragment = new TopicFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Set the adapter
        if (mColumnCount <= 1) {
            binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            binding.recycler.setLayoutManager(new GridLayoutManager(getContext(), mColumnCount));
        }

        RecyclerAdapter adapter = new RecyclerAdapter();
        adapter.register(TopicViewHolder.class);
        binding.recycler.setAdapter(adapter);

        adapter.addOne(new Topic());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
