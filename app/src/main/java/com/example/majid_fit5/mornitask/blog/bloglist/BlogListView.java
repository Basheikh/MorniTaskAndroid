package com.example.majid_fit5.mornitask.blog.bloglist;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.example.majid_fit5.mornitask.R;
import com.example.majid_fit5.mornitask.base.Injection;
import com.example.majid_fit5.mornitask.base.recyclerView.RecyclerEndlessScrollListener;
import com.example.majid_fit5.mornitask.blog.blogDetails.BlogDetailsActivity;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;
import java.util.ArrayList;
import java.util.List;

public class BlogListView extends AppCompatActivity implements BlogListContract.View {
    private RecyclerView mRcvBlogs;
    private BlogListContract.Presenter mPresenter;
    private int mPageId = 1;
    private BlogsAdapter mAdapter;
    private List<Blog> mBlogs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        Toast.makeText(this,"onPostCreate",Toast.LENGTH_SHORT).show();
        Log.d("onPostCreate","On onPostCreate");
        mPresenter=new BlogListPresenter(Injection.getDataRepository());
        mPresenter.onBind(this);
        Toast.makeText(this,"onBind",Toast.LENGTH_SHORT).show();
        Log.d("onBind","On Bind");
        mPresenter.getBlogs(mPageId);
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

    }

    private void init() {
        Log.d("init","init 1");
        Log.e("err","init 1");
        mRcvBlogs = findViewById(R.id.rcv_blogs_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRcvBlogs.setLayoutManager(layoutManager);
        Toast.makeText(this,"init 1",Toast.LENGTH_SHORT).show();

        mAdapter = new BlogsAdapter(mBlogs);
        Log.d("init","init 2");
        Toast.makeText(this,"init 2",Toast.LENGTH_SHORT).show();
        mAdapter.setLoading(true);
        mRcvBlogs.addOnScrollListener(new RecyclerEndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore() {
                if (mAdapter.isLoading()) return;
                mAdapter.setLoading(true);
                mPresenter.getBlogs(mPageId);
            }
        });

        mAdapter.setListener(new BlogsAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if (mBlogs.get(position)!= null){
                    Bundle bundle = new Bundle();
                    bundle.putInt("KEY_OBJECT_ID", mBlogs.get(position).getId());
                    Intent intent = new Intent(BlogListView.this, BlogDetailsActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"You should go the blog details activity",Toast.LENGTH_SHORT).show();
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
