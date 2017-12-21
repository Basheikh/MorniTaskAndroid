package com.example.majid_fit5.mornitask.blog.bloglist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.majid_fit5.mornitask.R;
import com.example.majid_fit5.mornitask.data.models.blog.Blog;

import java.util.List;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/17/2017.
 */
public class BlogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Blog> mblogs;
    private boolean isLoading;
    private final static int VIEW_TYPE_PROGRESS = 1,VIEW_TYPE_BLOG = 0;
    private OnItemClickedListener mListener;

    // used to pass actions of clicking to the caller class which is the BlogListView.
    public interface OnItemClickedListener{
        void OnItemClicked(int position);
    }

    public BlogAdapter(List<Blog> mblogs){
        this.mblogs=mblogs;
    }

    /**
     To create a new RecyclerView.ViewHolder and initializes some private fields to be used by RecyclerView.
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder= null;
        switch (viewType) {
            case VIEW_TYPE_BLOG:
                viewHolder = new BlogAdapter.BlogViewHolder(layoutInflater.inflate(R.layout.item_blog, parent, false));
                break;
            case VIEW_TYPE_PROGRESS:
                viewHolder = new ProgressViewHolder(layoutInflater.inflate(R.layout.lay_progress_holder, parent, false));// ProgressViewHolder used in other place
                break;
        }
        return viewHolder;
    }

    //Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mblogs.get(position)!=null)
            ((BlogAdapter.BlogViewHolder)holder).bindData(position, mblogs.get(position));
    }

    @Override
    public int getItemCount() {
        return mblogs.size();
    }

    //Return the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        Blog blog = mblogs.get(position);
        if (blog == null) return VIEW_TYPE_PROGRESS ;
        else return VIEW_TYPE_BLOG;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(Boolean loading){
        if (!loading){ // to delete the null item which is responsible on showing loading progress bar.
            isLoading = loading;
            int pos = mblogs.size() - 1;
            if (pos >= 0 && mblogs.get(pos) == null) {
                mblogs.remove(pos);
                notifyDataSetChanged();}
        }else {
            isLoading = loading;
            mblogs.add(null);
            notifyDataSetChanged();
        }}

    public void setListener(BlogAdapter.OnItemClickedListener listener) {
        this.mListener = listener;
    }

    // class view holder
    private class BlogViewHolder extends RecyclerView.ViewHolder {
        ImageView mImgPhoto;
        TextView mTxtTitle,mTxtDate,mTxtDesc;
        Button mBtnMore;

        public BlogViewHolder(View itemView) {// CREATED 1
            super(itemView);
            mImgPhoto = itemView.findViewById(R.id.img_item_blog_image);
            mTxtTitle= itemView.findViewById(R.id.txt_item_blog_title);
            mTxtDate = itemView.findViewById(R.id.txt_item_blog_date);
            mTxtDesc = itemView.findViewById(R.id.txt_item_blog_smallDesc);
            mBtnMore = itemView.findViewById(R.id.btn_item_blog_more);
            // add listener to VIEW except BUTTON
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogViewHolder.this.onClick();
                }
            });

            // add Button listener as well.. this is special case
            mBtnMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BlogViewHolder.this.onClick();
                }
            });
        }// end of constructor
        private void onClick() { // to pass the action to blog activity... // CREATED on ACTION..
            int position;
            if(mTxtTitle.getTag(R.id.rcv_item_position) != null &&
                    mTxtTitle.getTag(R.id.rcv_item_position) instanceof Integer){
                position= Integer.parseInt(mTxtTitle.getTag(R.id.rcv_item_position).toString());
            }else{
                position=-1;
            }
            mListener.OnItemClicked(position);
        }

        private void bindData(int position, Blog blog) { // CREATED 2
            mTxtTitle.setTag(R.id.rcv_item_position, position);
            mTxtTitle.setText(blog.getTitle());
            mTxtDesc.setText(blog.getSmallDesc());
            mTxtDate.setText(blog.getPublishDate());
            Glide.with(mImgPhoto.getContext()).load((blog.getCoverImage())).asBitmap()
                    .centerCrop().placeholder(R.drawable.ic_map_place_holder)
                    .error(R.drawable.ic_map_place_holder).into(mImgPhoto);
            /**
             Glide is a fast and efficient open source media management and image loading framework for Android that wraps media
             decoding, memory and disk caching, and resource pooling into a simple and easy to use interface.
             */
        }
    }
}
