package com.example.majid_fit5.mornitask.blog.bloglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.majid_fit5.mornitask.base.ProgressViewHolder;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;
import com.example.majid_fit5.mornitask.R;
import java.util.List;

public class BlogsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<Blog> mBlogs;
    private boolean isLoading;
    private final static int VIEW_TYPE_PROGRESS = 1;
    private final static int VIEW_TYPE_BLOG = 0;
    private OnItemClickedListener mListener;

    interface OnItemClickedListener {
        void onItemClicked(int position);
    }

    public BlogsAdapter(List<Blog> blogs) {
        this.mBlogs = blogs;
    }

    @Override
    public int getItemViewType(int position) {
        Blog blog = mBlogs.get(position);
        if (blog == null)
            return VIEW_TYPE_PROGRESS;
        else
            return VIEW_TYPE_BLOG ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case VIEW_TYPE_PROGRESS:
                //This class is used in other places.
                holder = new ProgressViewHolder(inflater.inflate(R.layout.lay_progress_holder, parent, false));// ProgressViewHolder used in other place
                break;
            case VIEW_TYPE_BLOG:
                holder = new BlogsAdapter.BlogsViewHolder(inflater.inflate(R.layout.item_blog, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mBlogs.get(position) != null)
            ((BlogsViewHolder)holder).bindData(position, mBlogs.get(position));
    }

    @Override
    public int getItemCount() {
        return mBlogs.size();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        if (loading) {
            isLoading = loading;
            mBlogs.add(null);
            notifyDataSetChanged();
        } else {
            isLoading = loading;
            int pos = mBlogs.size() - 1;
            if (pos >= 0 && mBlogs.get(pos) == null) {
                mBlogs.remove(pos);
                notifyDataSetChanged();
            }
        }
    }

    public void setListener(OnItemClickedListener listener) {
        this.mListener = listener;
    }

    class BlogsViewHolder extends RecyclerView.ViewHolder {
        ImageView mImCover;
        TextView mTxtTitle;
        TextView mTxtDate;
        TextView mTxtDesc;
        Button mBtnMore;

        public BlogsViewHolder(View itemView) {
            super(itemView);
            mImCover = itemView.findViewById(R.id.img_item_blog_image);
            mTxtTitle= itemView.findViewById(R.id.txt_item_blog_title);
            mTxtDate = itemView.findViewById(R.id.txt_item_blog_date);
            mTxtDesc = itemView.findViewById(R.id.txt_item_blog_smallDesc);
            mBtnMore = itemView.findViewById(R.id.btn_item_blog_more);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BlogsViewHolder.this.onClick();
                }
            });

            mBtnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  BlogsViewHolder.this.onClick();
                }
            });
        }

        void onClick(){
            int position = mTxtTitle.getTag(R.id.service_item_position) != null &&
                    mTxtTitle.getTag(R.id.service_item_position) instanceof Integer ?
                    Integer.parseInt(mTxtTitle.getTag(R.id.service_item_position).toString()) : -1;
            mListener.onItemClicked(position);
        }

        void bindData(int position, Blog blog){
            mTxtTitle.setTag(R.id.service_item_position, position);
            mTxtTitle.setText(blog.getTitle());
            mTxtDesc.setText(blog.getSmallDesc());
            mTxtDate.setText(blog.getPublishDate());
            Glide.with(mImCover.getContext()).load((blog.getCoverImage())).asBitmap()
                    .centerCrop().placeholder(R.drawable.ic_map_place_holder)
                    .error(R.drawable.ic_map_place_holder).into(mImCover);

        }
    }
}
