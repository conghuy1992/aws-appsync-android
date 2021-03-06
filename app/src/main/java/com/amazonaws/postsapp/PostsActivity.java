package com.amazonaws.postsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.amazonaws.demo.posts.AllPostsQuery;
import com.amazonaws.demo.posts.OnCreatePostSubscription;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import javax.annotation.Nonnull;

public class PostsActivity extends AppCompatActivity {
    private static final String TAG = PostsActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private PostsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private AWSAppSyncClient mAWSAppSyncClient;
    // TODO: Here for subscriptions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PostsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryData();
            }
        });

        FloatingActionButton fabAddPost = (FloatingActionButton) findViewById(R.id.addPost);
        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPostActivity.startActivity(PostsActivity.this);
            }
        });

        // TODO: Here for query
        // TODO: Here for subscriptions
    }



    // TODO: Here for subscriptions
    private AppSyncSubscriptionCall.Callback subCallback = new AppSyncSubscriptionCall.Callback<OnCreatePostSubscription.Data>() {
        @Override
        public void onResponse(@Nonnull final Response<OnCreatePostSubscription.Data> response) {
            Log.d("Response", response.data().toString());
            // Further code can update UI or act upon this new comment
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Error", "Subscription failure", e);
        }

        @Override
        public void onCompleted() {
            Log.d("Completed", "Completed");
        }
    };

    @Override
    protected void onStop() {
        super.onStop();

        // TODO: Here for subscriptions
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO: Here for query
        queryData();
    }

    public void queryData() {
        // TODO: Here for query

    }

    // TODO: Here for query

}
