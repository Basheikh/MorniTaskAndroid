package com.example.majid_fit5.mornitask.base.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.majid_fit5.mornitask.R;


public class LoadMoreListView extends ListView implements OnScrollListener {

    int mLastFirstVisibleItem = 0;
    private boolean mIsScrollingUp = false;

    private static final String TAG = "LoadMoreListView";

    /**
     * Listener that will receive notifications every time the categories scrolls.
     */
    private OnScrollListener mOnScrollListener;
    private LayoutInflater mInflater;

    // footer view
    private RelativeLayout mFooterView;
    // private TextView mLabLoadMore;
    private ProgressBar mProgressBarLoadMore;

    // Listener to process load more items when user reaches the end of the categories
    private OnLoadMoreListener mOnLoadMoreListener;

    // Listener to process load more items when user ScrollUp

	/*private OnScrollUpListener mOnScrollUpListener;*/

    // To know if the categories is loading more items
    private boolean mIsLoadingMore = false;
    private int mCurrentScrollState;

    public LoadMoreListView(Context context) {
        super(context);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // footer
        mFooterView = (RelativeLayout) mInflater.inflate(
                R.layout.lay_progress_holder, this, false);
		/*
		 * mLabLoadMore = (TextView) mFooterView
		 * .findViewById(R.id.load_more_lab_view);
		 */
        mProgressBarLoadMore = (ProgressBar) mFooterView
                .findViewById(R.id.progressBar);

        addFooterView(mFooterView);

        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    /**
     * Set the listener that will receive notifications every time the categories
     * scrolls.
     *
     * @param l
     *            The scroll listener.
     */
    @Override
    public void setOnScrollListener(OnScrollListener l) {
        mOnScrollListener = l;
    }

    /**
     * Register a callback to be invoked when this categories reaches the end (last
     * item be visible)
     *
     * @param onLoadMoreListener
     *            The callback to run.
     */

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mOnLoadMoreListener = onLoadMoreListener;
    }


	/*public void setOnLoadScrolledUp(OnScrollUpListener onScrollUpListener) {
		mOnScrollUpListener = onScrollUpListener;
	}*/

    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {

        if (mOnScrollListener != null) {
            mOnScrollListener.onScroll(view, firstVisibleItem,
                    visibleItemCount, totalItemCount);
        }

        if (mOnLoadMoreListener != null) {

            if (visibleItemCount == totalItemCount) {
                mProgressBarLoadMore.setVisibility(View.GONE);
                // mLabLoadMore.setVisibility(View.GONE);
                return;
            }

            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

            if (!mIsLoadingMore && loadMore
                    && mCurrentScrollState != SCROLL_STATE_IDLE) {
                mProgressBarLoadMore.setVisibility(View.VISIBLE);
                // mLabLoadMore.setVisibility(View.VISIBLE);
                mIsLoadingMore = true;
                onLoadMore();
            }

			/*final int currentFirstVisibleItem = view.getFirstVisiblePosition();
			if (currentFirstVisibleItem > mLastFirstVisibleItem) {
				mIsScrollingUp = false;
				Log.i("a", "scrolling down...");
			} else if (currentFirstVisibleItem < mLastFirstVisibleItem
					&& !mIsLoadingMore) {
				mIsScrollingUp = true;
				Log.i("a", "scrolling up...");
				if(mCurrentScrollState == SCROLL_STATE_IDLE)
					onScrollUp();
			}
			mLastFirstVisibleItem = currentFirstVisibleItem;*/
        }

    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mCurrentScrollState = scrollState;

        if (mOnScrollListener != null) {
            mOnScrollListener.onScrollStateChanged(view, scrollState);
        }

    }

    public void onLoadMore() {
        Log.d(TAG, "onLoadMore");
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMore();
        }
    }


    /**
     * Notify the loading more operation has finished
     */
    public void onLoadMoreComplete() {
        mIsLoadingMore = false;
        mProgressBarLoadMore.setVisibility(View.GONE);
    }

    /**
     * Interface definition for a callback to be invoked when categories reaches the
     * last item (the user load more items in the categories)
     */
    public interface OnLoadMoreListener {
        /**
         * Called when the categories reaches the last item (the last item is visible
         * to the user)
         */
        public void onLoadMore();

    }

    /**
     * Interface definition for a callback to be invoked when categories scrolled up
     * by user
     */
	/*public interface OnScrollUpListener {
		*//**
     * Called when the categories scrolled up
     *//*
		public void onScrollUp();
	}*/


}