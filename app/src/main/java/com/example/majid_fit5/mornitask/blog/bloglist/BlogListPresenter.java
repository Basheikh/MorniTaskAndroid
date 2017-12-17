package com.example.majid_fit5.mornitask.blog.bloglist;
import android.support.annotation.NonNull;
import com.example.majid_fit5.mornitask.data.DataSource;
import com.example.majid_fit5.mornitask.data.DataRepository;
import com.example.majid_fit5.mornitask.data.models.MorniError;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;
import java.lang.ref.WeakReference;
import java.util.List;
/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */
public class BlogListPresenter implements BlogListContract.Presenter {
    private WeakReference<BlogListContract.View> mView;
    private DataRepository mDataRepository;

    public BlogListPresenter(DataRepository mDataRepository){
        this.mDataRepository=mDataRepository;
    }
    @Override
    public void onBind(@NonNull BlogListContract.View view) {
        mView= new WeakReference<BlogListContract.View>(view);
    }

    @Override
    public void onDestroy() {
        if(mView!=null){
            mView.clear();
        }
    }

    @Override
    public void getBlogs(int pageID) {
        if(mView!=null){
            String url = "http://blog.sandbox.morniksa.com/posts?page=" +pageID ;
            mDataRepository.getBlogs(url, new DataSource.GetBlogsCallBack() {
                @Override
                public void onGetBlogs(List<Blog> blogs) {
                    if(mView.get() != null) {
                        mView.get().hideLoading();
                        mView.get().onGetBlogs(blogs);
                    }
                }

                @Override
                public void onFailure(MorniError error) {
                    if(mView.get() != null) {
                        mView.get().hideLoading();
                        mView.get().showBlogsError(error);
                    }
                }
            });


        }
    }
}
