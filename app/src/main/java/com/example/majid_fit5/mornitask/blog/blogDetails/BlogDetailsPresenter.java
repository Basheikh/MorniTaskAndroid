package com.example.majid_fit5.mornitask.blog.blogDetails;

import android.support.annotation.NonNull;

import com.example.majid_fit5.mornitask.data.DataRepository;
import com.example.majid_fit5.mornitask.data.DataSource;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;

import java.lang.ref.WeakReference;

/**
 * Created by BASH on 12/16/2017.
 */

public class BlogDetailsPresenter implements BlogDetailsContract.Presenter {
    private DataRepository mDataRepository;
    private WeakReference<BlogDetailsContract.View> mBlogDetailsView;

    public BlogDetailsPresenter(@NonNull DataRepository mDataRepository) {
        this.mDataRepository = mDataRepository;
    }

    //this method bind the view to the presenter as a weak Reference
    @Override
    public void onBind(@NonNull BlogDetailsContract.View view) {
        mBlogDetailsView = new WeakReference<BlogDetailsContract.View>(view);
    }

    //clear the view
    @Override
    public void onDestroy() {
        if (mBlogDetailsView.get() != null){
            mBlogDetailsView.clear();
        }
    }

    //get the blog content by providing "BlogID"
    @Override
    public void getBlogDetails(int blogID) {
        if (mBlogDetailsView !=null)
            mBlogDetailsView.get().showLoading();
        String url = "http://blog.sandbox.morniksa.com/posts/" +blogID;
        mDataRepository.getBlogDetails(url, new DataSource.GetBlogDetailsCallBack() {
            //this method will called after the content downloaded
            @Override
            public void onGetBlogDetails(Blog blog) {
                if (mBlogDetailsView != null){
                    mBlogDetailsView.get().hideLoading();
                    mBlogDetailsView.get().onGetBlogDetails(blog);
                }
            }
            //this method will called in download Failure case
            @Override
            public void onFailure(MorniError error) {
                if (mBlogDetailsView != null){
                    mBlogDetailsView.get().hideLoading();
                    mBlogDetailsView.get().showBlogDetailsError(error);
                }

            }
        });

    }
}
