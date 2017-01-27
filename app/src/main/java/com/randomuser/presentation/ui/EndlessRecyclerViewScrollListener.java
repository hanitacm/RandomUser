package com.randomuser.presentation.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
  private RecyclerView.LayoutManager mLayoutManager;
  private int visibleThreshold = 5;
  private int currentPage = 0;
  private int previousTotalItemCount = 0;
  private boolean loading = true;
  private int startingPageIndex = 0;

  public <T extends RecyclerView.LayoutManager> EndlessRecyclerViewScrollListener(T layoutManager) {
    if (layoutManager instanceof LinearLayoutManager) {
      setLinearLayoutManager((LinearLayoutManager) layoutManager);
    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
      setStaggeredGridLayoutManager((StaggeredGridLayoutManager) layoutManager);
    } else if (layoutManager instanceof GridLayoutManager) {
      setGridLayoutManager((GridLayoutManager) layoutManager);
    }
  }

  private void setLinearLayoutManager(LinearLayoutManager layoutManager) {
    this.mLayoutManager = layoutManager;
  }

  private void setGridLayoutManager(GridLayoutManager layoutManager) {
    this.mLayoutManager = layoutManager;
    visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
  }

  private void setStaggeredGridLayoutManager(StaggeredGridLayoutManager layoutManager) {
    this.mLayoutManager = layoutManager;
    visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
  }

  @Override
  public void onScrolled(RecyclerView view, int dx, int dy) {
    int lastVisibleItemPosition = 0;
    int totalItemCount = mLayoutManager.getItemCount();

    if (mLayoutManager instanceof StaggeredGridLayoutManager) {
      int[] lastVisibleItemPositions =
          ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
      // get maximum element within the list
      lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
    } else if (mLayoutManager instanceof GridLayoutManager) {
      lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
    } else if (mLayoutManager instanceof LinearLayoutManager) {
      lastVisibleItemPosition =
          ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
    }

    if (totalItemCount < previousTotalItemCount) {
      this.currentPage = this.startingPageIndex;
      this.previousTotalItemCount = totalItemCount;
      if (totalItemCount == 0) {
        this.loading = true;
      }
    }

    if (loading && (totalItemCount > previousTotalItemCount)) {
      loading = false;
      previousTotalItemCount = totalItemCount;
    }

    if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
      currentPage++;
      onLoadMore(totalItemCount);
      loading = true;
    }
  }

  private int getLastVisibleItem(int[] lastVisibleItemPositions) {
    int maxSize = 0;
    for (int i = 0; i < lastVisibleItemPositions.length; i++) {
      if (i == 0) {
        maxSize = lastVisibleItemPositions[i];
      } else if (lastVisibleItemPositions[i] > maxSize) {
        maxSize = lastVisibleItemPositions[i];
      }
    }
    return maxSize;
  }

  public abstract void onLoadMore(int totalItemCount);
}

