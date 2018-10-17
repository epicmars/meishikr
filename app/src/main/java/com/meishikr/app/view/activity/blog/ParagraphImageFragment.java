package com.meishikr.app.view.activity.blog;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.meishikr.app.R;
import com.sin2pi.brick.components.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentParagraphImageBinding;
import com.meishikr.app.base.BaseFragment;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

/**
 * Created by yinhang on 2016/11/13.
 */

@BindLayout(R.layout.fragment_paragraph_image)
public class ParagraphImageFragment extends BaseFragment<FragmentParagraphImageBinding> {

    private static final String ARGS_IMAGE_ENTRY = "ARGS_IMAGE_ENTRY";

    private ImageFileEntry entry;

    public ParagraphImageFragment() {
    }

    public static ParagraphImageFragment newInstance(ImageFileEntry entry) {
        ParagraphImageFragment fragment = new ParagraphImageFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_IMAGE_ENTRY, entry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        entry = getArguments().getParcelable(ARGS_IMAGE_ENTRY);
        if(null == entry)
            return;
        Glide.with(this).load(Uri.fromFile(entry.getFile())).into(binding.image);
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.fromFile(entry.getFile()))
//                .setResizeOptions(new ResizeOptions(300, 200))
//                .build();
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setOldController(binding.image.getController())
//                .setImageRequest(request)
//                .build();
//        binding.image.setController(controller);
    }
}
