package com.meishikr.app.view.viewholder;

import android.app.Activity;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.meishikr.app.R;
import com.meishikr.app.base.adapter.BaseViewHolder;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.ViewHolderImageFileEntryBinding;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.util.List;

/**
 * Created by yinhang on 2016/11/12.
 */

@BindLayout(R.layout.view_holder_image_file_entry)
public class ImageFileEntryViewHolder extends BaseViewHolder<ImageFileEntry, ViewHolderImageFileEntryBinding> {

    private OnImageFileEntryOperationListener mListener;

    public ImageFileEntryViewHolder(View view) {
        super(view);
        if (context instanceof ImageFileEntryViewHolder.OnImageFileEntryOperationListener) {
            mListener = (ImageFileEntryViewHolder.OnImageFileEntryOperationListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void render(ImageFileEntry data, int position) {
        if (null == data)
            return;
        List<ImageFileEntry> childEntries = data.getChildEntries();
        if (data.isDirectory() && (null == childEntries || childEntries.isEmpty()))
            return;
        ImageFileEntry show = data.isDirectory() ? childEntries.get(0) : data;
        if (null == show || null == show.getFile() || !show.getFile().exists())
            return;
        //
        if (data.isDirectory()) {
            binding.vFolder.setVisibility(View.VISIBLE);
            binding.tvName.setText(data.getFile().getName());
            binding.tvNum.setText(String.valueOf(data.getChildEntries().size()));
        } else {
            binding.cbChooseImage.setVisibility(View.VISIBLE);
            binding.cbChooseImage.setChecked(false);
            if (data.getSelectIndex() > 0)
                binding.cbChooseImage.setChecked(true);
        }
        //
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) itemView.getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // todo 这里直接获取binding.ivImage.getWidth()为0
        // todo params设置layout_width = 'match_parent'后，params.width = -1, 'wrap_content' 为 -2
//        ViewGroup.LayoutParams params = binding.ivImage.getLayoutParams();
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(show.getFile()))
                .setResizeOptions(new ResizeOptions(300, 300))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(binding.ivImage.getController())
                .setImageRequest(request)
                .build();

        binding.ivImage.setController(controller);
//        binding.ivImage.setImageURI();

        itemView.setOnClickListener((v) -> mListener.onImageFileEntryClick(data));

        binding.cbChooseImage.setOnCheckedChangeListener((buttonView, isChecked) -> mListener.onImageFileEntryCheck(data, isChecked));
    }

    public interface OnImageFileEntryOperationListener {
        void onImageFileEntryClick(ImageFileEntry entry);

        void onImageFileEntryCheck(ImageFileEntry entry, boolean isChecked);
    }
}
