package com.example.majid_fit5.mornitask.blog.blogDetails;

import com.example.majid_fit5.mornitask.base.BasePresenter;
import com.example.majid_fit5.mornitask.base.BaseView;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;

/**
 * Created by BASH on 12/16/2017.
 */

public interface BlogDetailsContract {
    interface View extends BaseView{
        void onGetBlogDetails(Blog blog);
        void showBlogDetailsError(MorniError morniError);
    }
    interface Presenter extends BasePresenter<BlogDetailsContract.View>{
        void getBlogDetails(int blogID);
    }
}
