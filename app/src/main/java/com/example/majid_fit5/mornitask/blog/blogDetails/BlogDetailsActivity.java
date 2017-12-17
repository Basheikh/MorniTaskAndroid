package com.example.majid_fit5.mornitask.blog.blogDetails;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.example.majid_fit5.mornitask.R;
import com.example.majid_fit5.mornitask.base.Injection;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;

/**
 * Created by BASH on 12/16/2017.
 */

public class BlogDetailsActivity extends AppCompatActivity implements BlogDetailsContract.View {

    private WebView mWvBlogDetails;
    private Toolbar mToolbar;
    private int mBlogID;
    private BlogDetailsContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog; //deprecated

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("");
        init();
    }

    private void init() {
        mPresenter = new BlogDetailsPresenter(Injection.getDataRepository());
        mWvBlogDetails = findViewById(R.id.wv_blog_details);
        //the key should be taken from constants
        mBlogID = getIntent().getExtras().getInt("KEY_OBJECT_ID");
        //Sets whether the WebView should use its built-in zoom mechanisms.
        mWvBlogDetails.getSettings().setBuiltInZoomControls(true);
        //Enable javaScript, in this activity we need to enable javaScript for videos
        mWvBlogDetails.getSettings().setJavaScriptEnabled(true);
        //handle back button in the toolbar
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mPresenter.onBind(this);
        mPresenter.getBlogDetails(mBlogID);
    }

    /*this method used in "getBlogDetails" method that located in "BlogDetailsPresenter" class to show
    progressDialog during download webView content*/
    @Override
    public void showLoading() {
        //taken as is it from NewMorniKSA
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "", "", false, false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.setContentView(R.layout.progress_dialog);
        } else {
            mProgressDialog.show();
        }
    }

    //to hide progressDialog after WebView Content downloaded successfully
    @Override
    public void hideLoading() {
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    //display html content in the webView, will called by "onGetBlogDetails" that located in the presenter
    @Override
    public void onGetBlogDetails(Blog blog) {
        //to display "html" content
        mWvBlogDetails.loadData(blog.body,"text/html; charset=UTF-8",null);
    }

    //show the error in a snackBar
    @Override
    public void showBlogDetailsError(MorniError morniError) {
        Snackbar.make(mWvBlogDetails, "BIG ERROR", Snackbar.LENGTH_INDEFINITE).show();
    }
}
