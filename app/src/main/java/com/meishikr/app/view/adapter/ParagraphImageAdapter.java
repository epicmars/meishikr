package com.meishikr.app.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.meishikr.app.R;
import com.meishikr.app.databinding.FragmentParagraphImageBinding;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinhang on 2016/11/13.
 */

public class ParagraphImageAdapter extends PagerAdapter {


    private List<ImageFileEntry> imageFileEntries;
    private final LayoutInflater layoutInflater;

    public ParagraphImageAdapter(Context context) {
        imageFileEntries = new ArrayList<>();
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ParagraphImageAdapter(Context context, List<ImageFileEntry> imageFileEntries) {
        if(null == imageFileEntries)
            this.imageFileEntries = new ArrayList<>();
        this.imageFileEntries = imageFileEntries;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View itemView = layoutInflater.inflate(R.layout.fragment_paragraph_image, container, false);
        FragmentParagraphImageBinding binding = DataBindingUtil.bind(itemView);
        ImageFileEntry entry = imageFileEntries.get(position);
//        Glide.with(itemView.getContext()).load(Uri.fromFile(entry.getFile())).into(binding.image);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(entry.getFile()))
                .setResizeOptions(new ResizeOptions(300, 200))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(binding.image.getController())
                .setImageRequest(request)
                .build();
        binding.image.setController(controller);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageFileEntries.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void addAll(List<ImageFileEntry> entries) {
        if(null == entries || entries.isEmpty())
            return;
        imageFileEntries.addAll(entries);
        notifyDataSetChanged();
    }

    public void clear() {
        imageFileEntries.clear();
        notifyDataSetChanged();
    }

    public void setImageFileEntries(List<ImageFileEntry> entries) {
        if(null == entries)
            return;
        imageFileEntries = entries;
        notifyDataSetChanged();
    }
}
