package com.example.majid_fit5.mornitask.data;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;

import java.util.List;

public interface DataSource {

    interface BaseCallBack{
        void onFailure(MorniError error);
    }

    // any call which implements this interface, will call retrofit.
    interface GetBlogsCallBack extends BaseCallBack{
        void onGetBlogs(List<Blog> blogs);
    }
    //Real implement for this method will found in RemoteDataSource Class, because all blog data located in the server
    void getBlogs(String url, GetBlogsCallBack callBack);

    interface GetBlogDetailsCallBack extends BaseCallBack{
        void onGetBlogDetails(Blog blog);
    }

    //Real implement for this method will found in RemoteDataSource Class, because all blog data located in the server
    void getBlogDetails(String url, GetBlogDetailsCallBack callBack);
}
