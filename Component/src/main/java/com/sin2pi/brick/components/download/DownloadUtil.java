package com.sin2pi.brick.components.download;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.sin2pi.brick.components.utils.FileUtil;

import java.io.File;

/**
 * Created by yinhang on 16/2/1.
 */
public class DownloadUtil {

    private Context context;
    private DownloadManager manager;


    public DownloadUtil(Context context){
        this.context = context;
        manager =(DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    /**
     * 下载文件，并以指定名称保存到默认文件夹下，默认文件夹为sd卡的下载文件夹
     * @param url       the url of the file to be downloaded
     * @param name      the saved name of the downloaded file
     */
    public void download(String url, String name){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File savedDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            download(url, savedDir.getPath(), name);
        } else {

        }
    }

    /**
     * Download file from specific url, and save the downloaded file to specific
     * directory with specific name.
     * @param url         the url of the file to be downloaded
     * @param savedDir    the saved directory of the downloaded file
     * @param name        the saved name of the downloaded file
     */
    public void download(String url, String savedDir, String name){
        //
        FileUtil.mkdir(savedDir);
        //
        Uri uri = Uri.parse(url);
        try{
            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setDestinationUri(Uri.fromFile(new File(savedDir, name)));
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            long id = manager.enqueue(request);
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
