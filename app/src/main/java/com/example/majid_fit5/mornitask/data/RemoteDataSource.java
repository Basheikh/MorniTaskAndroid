package com.example.majid_fit5.mornitask.data;
import android.util.Log;
import com.example.majid_fit5.mornitask.BuildConfig;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

// Singleton class that responsible on firing retrofit calls.
public class RemoteDataSource implements DataSource {
    private static String BASE_URL = "https://sandbox.morniksa.com/api/v2/";//"https://www.morniksa.com/api/"
    private ApiEndPoints mEndpoints;
    private static RemoteDataSource INSTANCE = null;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RemoteDataSource();
        return INSTANCE;
    }

    private RemoteDataSource() {
        // Creating clientBuilder
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.readTimeout(30, TimeUnit.SECONDS);
        clientBuilder.connectTimeout(30, TimeUnit.SECONDS);

        //json
        Gson gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return false;
            }
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        }).create();

        // creating the client to use in Retrofit instance.
        OkHttpClient client = clientBuilder.build();

        // Retrofit instantiation
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        mEndpoints = retrofit.create(ApiEndPoints.class);
    }

    // My Part
    @Override
    public void getBlogs(String url, final GetBlogsCallBack callBack) {
        Call<List<Blog>> call= mEndpoints.getBlogs(url);
        call.enqueue(new Callback<List<Blog>>() {
            @Override
            public void onResponse(Call<List<Blog>> call, retrofit2.Response<List<Blog>> response) {
                if(response.isSuccessful()){
                    callBack.onGetBlogs(response.body());
                }else{
                    callBack.onFailure(getError(response.code()));
                }
            }
            @Override
            public void onFailure(Call<List<Blog>> call, Throwable t) {
                callBack.onFailure(getError(4077));
            }
        });
    }

    // Mohammed Part
    @Override
    public void getBlogDetails(String url, final GetBlogDetailsCallBack callBack) {
        Call<Blog> call = mEndpoints.getBlogDetails(url);
        call.enqueue(new Callback<Blog>() {
            @Override
            public void onResponse(Call<Blog> call, Response<Blog> response) {
                if (response.isSuccessful()){
                    callBack.onGetBlogDetails(response.body());}
                else {
                    callBack.onFailure(getError(response.code()));
                }
            }
            @Override
            public void onFailure(Call<Blog> call, Throwable t) {
                //Below line should take the throwable "t"
                callBack.onFailure(getError(400));
            }
        });
    }
    private MorniError getError(int errCode) {
        return new MorniError(errCode, "MORRNI ERROR");
    }
}
