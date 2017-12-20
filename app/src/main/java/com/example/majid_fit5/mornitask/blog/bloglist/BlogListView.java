package com.example.majid_fit5.mornitask.blog.bloglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.majid_fit5.mornitask.R;
import com.example.majid_fit5.mornitask.base.Injection;
import com.example.majid_fit5.mornitask.base.recyclerView.RecyclerEndlessScrollListener;
import com.example.majid_fit5.mornitask.blog.blogDetails.BlogDetailsActivity;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class BlogListView extends AppCompatActivity implements BlogListContract.View {
    private BlogListContract.Presenter mPresenter;
    private List<Blog> mBlogs = new ArrayList<>();
    private RecyclerView mRcvBlogs;
    private BlogAdapter mAdapter;
    private int mPageId = 1; // for paging..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        FirebaseMessaging.getInstance().subscribeToTopic("news");
    }
    private void init() {
        //presenter initiation..
        mPresenter=new BlogListPresenter(Injection.getDataRepository());
        mPresenter.onBind(this);
        mPresenter.getBlogs(mPageId);

        //Recycler view preparation...
        mRcvBlogs = findViewById(R.id.rcv_blogs_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRcvBlogs.setLayoutManager(layoutManager);
        mAdapter = new BlogAdapter(mBlogs);
        mAdapter.setLoading(true);
        mRcvBlogs.addOnScrollListener(new RecyclerEndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (mAdapter.isLoading()) return;
                mAdapter.setLoading(true);
                mPresenter.getBlogs(mPageId);
            }
        });
        // Mohammed Part
        mAdapter.setListener(new BlogAdapter.OnItemClickedListener() {
            @Override
            public void OnItemClicked(int position) {
                if (mBlogs.get(position)!= null){
                    Bundle bundle = new Bundle();
                    bundle.putInt("KEY_OBJECT_ID", mBlogs.get(position).getId());
                    Intent intent = new Intent(BlogListView.this, BlogDetailsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        mRcvBlogs.setAdapter(mAdapter);
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void onGetBlogs(List<Blog> blogs) {
         mPageId++;
         mAdapter.setLoading(false);
         mBlogs.addAll(blogs);
         mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showBlogsError(MorniError error) {
        Toast.makeText(this,"Error is "+error.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
