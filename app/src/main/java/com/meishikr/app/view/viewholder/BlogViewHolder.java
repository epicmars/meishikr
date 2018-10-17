package com.meishikr.app.view.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.application.Consts;
import com.sin2pi.brick.components.base.adapter.BaseViewHolder;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderBlogItemBinding;
import com.meishikr.app.domain.entity.post.Blog;
import com.meishikr.app.view.activity.blog.BlogPageActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by yinhang on 16/7/3.
 */
@BindLayout(R.layout.view_holder_blog_item)
public class BlogViewHolder extends BaseViewHolder<Blog, ViewHolderBlogItemBinding> {

    public BlogViewHolder(View view){
        super(view);
    }

    @Override
    public void render(final Blog data, int position) {
        if(null != data.getPhotos() && data.getPhotos().size() > 0){
            Uri uri = Uri.parse(data.getPhotos().get(0).getUrl());
            binding.ivCover.setImageURI(uri);
        }
        if(null != data.getBodyHtml()) {
            Document html = Jsoup.parse(data.getBodyHtml());
            Elements elements = html.select("img[src]");
            if(!elements.isEmpty()) {
                Element element = elements.first();
                String imageUri = element.attr("src");
                if(null != imageUri)
                    binding.ivCover.setImageURI(imageUri);
            }
        }

        binding.tvTitle.setText(data.getTitle());
        binding.tvContent.setText(data.getBodyHtml());
        //
        itemView.setOnClickListener((View v) -> {
                // TODO to be change
                Context context = binding.getRoot().getContext();
                Intent intent = new Intent(context, BlogPageActivity.class);
                intent.putExtra(Consts.Extras.BLOG_UUID, data.getUuid());
                context.startActivity(intent);
            });
    }
}
