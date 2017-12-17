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
        mWvBlogDetails.getSettings().setBuiltInZoomControls(true);
        mWvBlogDetails.getSettings().setJavaScriptEnabled(true);
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

    @Override
    public void hideLoading() {
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onGetBlogDetails(Blog blog) {
        //to display "html" content
        mWvBlogDetails.loadData(blog.body,"text/html; charset=UTF-8",null);

    }

    @Override
    public void showBlogDetailsError(MorniError morniError) {
        Snackbar.make(mWvBlogDetails, "BIG ERROR", Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

        }
        return super.onOptionsItemSelected(item);
    }

}
