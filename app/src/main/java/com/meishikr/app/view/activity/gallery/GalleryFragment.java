package com.meishikr.app.view.activity.gallery;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.meishikr.app.R;
import com.meishikr.app.base.adapter.RecyclerAdapter;
import com.meishikr.app.base.annotation.BindLayout;
import com.meishikr.app.databinding.FragmentGalleryBinding;
import com.meishikr.app.base.BaseFragment;
import com.meishikr.app.view.viewholder.ImageFileEntryViewHolder;
import com.meishikr.app.view.viewholder.TakePhotoItemViewHolder;
import com.meishikr.app.view.viewholder.items.TakePhotoItem;
import com.sin2pi.brick.components.gallery.ImageFileEntry;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@BindLayout(R.layout.fragment_gallery)
public class GalleryFragment extends BaseFragment<FragmentGalleryBinding> {
    public static final String ARG_IMAGE_FILE_ENTRIES = "ARG_IMAGE_FILE_ENTRIES";

    private List<ImageFileEntry> entries;

    private RecyclerAdapter adapter;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance(ArrayList<? extends Parcelable> entries) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_IMAGE_FILE_ENTRIES, entries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entries = getArguments().getParcelableArrayList(ARG_IMAGE_FILE_ENTRIES);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new RecyclerAdapter();
        adapter.register(TakePhotoItemViewHolder.class);
        adapter.register(ImageFileEntryViewHolder.class);
        binding.recycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
        binding.recycler.setAdapter(adapter);

        adapter.clear();
        adapter.addOne(new TakePhotoItem());
        adapter.addAll(entries);
        binding.recycler.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
//            int count = adapter.getItemCount();
//            for(int i = 0; i < count; i++){
//                RecyclerView.ViewHolder holder = binding.content.recycler.findViewHolderForAdapterPosition(i);
//            }
        });
    }

    public void updateImageFileEntries() {
        adapter.clear();
        adapter.addOne(new TakePhotoItem());
        adapter.addAll(entries);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

}
