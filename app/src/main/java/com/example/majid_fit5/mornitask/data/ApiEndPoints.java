package com.example.majid_fit5.mornitask.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;
/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public interface ApiEndPoints {

    //Retrofit call to get list of blogs.
    @GET
    Call<List<Blog>> getBlogs(@Url String url);

    //Using Retrofit to get Blog Details
    @GET
    Call<Blog> getBlogDetails(@Url String url);

}
