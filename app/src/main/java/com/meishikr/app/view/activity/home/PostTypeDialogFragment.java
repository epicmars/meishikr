package com.meishikr.app.view.activity.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.meishikr.app.R;

/**
 * Created by yinhang on 2016/12/16.
 */

public class PostTypeDialogFragment extends DialogFragment {

    public static interface OnPostTypeClickListener{
        void onPostTypeClick(String item, int position);
    }

    private OnPostTypeClickListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnPostTypeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implements OnPostTypeClickListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] postTypes = getResources().getStringArray(R.array.post_types);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(postTypes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onPostTypeClick(postTypes[i], i);
            }
        });

        return builder.create();
    }
}
