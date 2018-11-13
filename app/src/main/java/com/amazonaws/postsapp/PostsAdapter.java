package com.amazonaws.postsapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.demo.posts.AllPostsQuery;
import com.amazonaws.demo.posts.UpdatePostMutation;
import com.amazonaws.demo.posts.type.UpdatePostInput;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleView;
        public TextView mAuthorView;
        public TextView mContentView;
        public TextView mUpsView;
        public TextView mDownsView;
        public TextView mShareView;

        public ViewHolder(View v) {
            super(v);
            mTitleView = (TextView) v.findViewById(R.id.postTitle);
            mAuthorView = (TextView) v.findViewById(R.id.postAuthor);
            mContentView = (TextView) v.findViewById(R.id.postContent);
            mUpsView = (TextView) v.findViewById(R.id.postUps);
            mDownsView = (TextView) v.findViewById(R.id.postDowns);
            mShareView = (TextView) v.findViewById(R.id.postUrl);
        }
    }

    private List<AllPostsQuery.Item> mPosts;
    PostsActivity display;

    public PostsAdapter(PostsActivity display) {
        this.display = display;
        mPosts = new ArrayList<>(0);
    }

    public void setPosts(List<AllPostsQuery.Item> posts) {
        mPosts = posts;
    }

    public void addPost(final String id, final String title, final String author, final String content,
                        final String url, final int version, final int ups, final int downs) {
        AllPostsQuery.Item newPost = new AllPostsQuery.Item("Post", id, title, author, content, url, version, ups, downs);
        mPosts.add(newPost);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.post, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AllPostsQuery.Item post = mPosts.get(position);
        holder.mTitleView.setText(post.title());
        holder.mAuthorView.setText(post.author());
        holder.mContentView.setText(post.content());
        holder.mUpsView.setText(post.ups() + " Ups");
        holder.mDownsView.setText(post.downs() + " Downs");
        holder.mShareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent()
                        .setAction(Intent.ACTION_SEND)
                        .putExtra(Intent.EXTRA_SUBJECT, post.url())
                        .putExtra(Intent.EXTRA_TEXT, post.url())
                        .setType("text/plain");
                view.getContext().startActivity(sendIntent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdatePostActivity.startActivity(view.getContext(), post, position);
            }
        });
        holder.mUpsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(display, "Sending up vote", Toast.LENGTH_SHORT).show();
                upVotePost(view, position, post);
            }
        });
        holder.mDownsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(display, "Sending down vote", Toast.LENGTH_SHORT).show();
                downVotePost(view, position, post);
            }
        });
    }

    private void upVotePost(final View view, final int position, final AllPostsQuery.Item post) {
        UpdatePostMutation updatePostMutation = UpdatePostMutation.builder()
                .input(UpdatePostInput.builder()
                    .id(post.id())
                    .title(post.title())
                    .author(post.author())
                    .url(post.url())
                    .content(post.content())
                    .ups(post.ups() + 1)
                    .downs(post.downs())
                    .version(post.version() + 1)
                    .build())
                .build();
        ClientFactory.getInstance(view.getContext()).mutate(updatePostMutation).enqueue(new GraphQLCall.Callback<UpdatePostMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdatePostMutation.Data> response) {
                display.queryData();
            }

            @Override
            public void onFailure(@Nonnull final ApolloException e) {
                Toast.makeText(display, "Failed to upvote post", Toast.LENGTH_SHORT).show();
                Log.e("", e.getMessage());
            }
        });
    }

    private void downVotePost(final View view, final int position, final AllPostsQuery.Item post) {
        UpdatePostMutation updatePostMutation = UpdatePostMutation.builder()
                .input(UpdatePostInput.builder()
                    .id(post.id())
                    .title(post.title())
                    .author(post.author())
                    .url(post.url())
                    .content(post.content())
                    .ups(post.ups())
                    .downs(post.downs() + 1)
                    .version(post.version() + 1)
                    .build())
                .build();
        ClientFactory.getInstance(view.getContext()).mutate(updatePostMutation).enqueue(new GraphQLCall.Callback<UpdatePostMutation.Data>() {
            @Override
            public void onResponse(@Nonnull Response<UpdatePostMutation.Data> response) {
                display.queryData();
            }

            @Override
            public void onFailure(@Nonnull final ApolloException e) {
                Toast.makeText(display, "Failed to downvote post", Toast.LENGTH_SHORT).show();
                Log.e("", e.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }
}
