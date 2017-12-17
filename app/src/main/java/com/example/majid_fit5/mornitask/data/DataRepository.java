package com.example.majid_fit5.mornitask.data;

import android.support.annotation.NonNull;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public class DataRepository implements DataSource {// Singleton class
    private static DataRepository INSTANCE = null;
    private final DataSource mRemoteDataSource;

    public static DataRepository getInstance(DataSource remoteDataSource){
        if(INSTANCE==null)
            INSTANCE= new DataRepository(remoteDataSource);
        return INSTANCE;
    }

    public DataRepository(@NonNull DataSource mRemoteDataSource){
        this.mRemoteDataSource=mRemoteDataSource;
    }

    @Override
    public void getBlogs(String url, GetBlogsCallBack callBack) {
        mRemoteDataSource.getBlogs(url,callBack);
    }

    @Override
    public void getBlogDetails(String url, GetBlogDetailsCallBack callBack) {
        mRemoteDataSource.getBlogDetails(url,callBack);
    }

}
