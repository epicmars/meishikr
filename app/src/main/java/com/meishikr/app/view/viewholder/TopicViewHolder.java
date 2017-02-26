package com.meishikr.app.view.viewholder;

import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.base.adapter.BaseViewHolder;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderTopicItemBinding;
import com.meishikr.app.domain.entity.post.Topic;

/**
 * Created by yinhang on 16/11/8.
 */
@BindLayout(R.layout.view_holder_topic_item)
public class TopicViewHolder extends BaseViewHolder<Topic, ViewHolderTopicItemBinding>{

    public TopicViewHolder(View view) {
        super(view);
    }

    @Override
    public void render(Topic data, int position) {

    }
}
