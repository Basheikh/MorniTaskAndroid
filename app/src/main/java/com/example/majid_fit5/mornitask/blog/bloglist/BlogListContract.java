package com.example.majid_fit5.mornitask.blog.bloglist;

import com.example.majid_fit5.mornitask.base.BasePresenter;
import com.example.majid_fit5.mornitask.base.BaseView;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;

import java.util.List;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

// This interface has 2 interfaces to be implemented by the view and presenter of this sub-feature.
public interface BlogListContract {

    // view of the contract
    interface View extends BaseView {
        void onGetBlogs(List<Blog> blogs);
        void showBlogsError(MorniError error);
    }
    // presenter of the contract
    interface Presenter extends BasePresenter<View> {
        //Here is the main function of this sub-feature
        void getBlogs(int pageID);
    }
}
