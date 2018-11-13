package com.amazonaws.postsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.demo.posts.AddPostMutation;
import com.amazonaws.demo.posts.AllPostsQuery;
import com.amazonaws.demo.posts.type.CreatePostInput;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

public class AddPostActivity extends AppCompatActivity {
    private static final String TAG = AddPostActivity.class.getSimpleName();

    public static void startActivity(Context context) {
        Intent updatePostIntent = new Intent(context, AddPostActivity.class);
        context.startActivity(updatePostIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        ((EditText) findViewById(R.id.updateTitle)).setText("Salmon on Sale!");
        ((EditText) findViewById(R.id.updateAuthor)).setText("Fred the Fisherman");
        ((EditText) findViewById(R.id.updateUrl)).setText("https://fredfish.com");
        ((EditText) findViewById(R.id.updateContent)).setText("On Sale for $4.99 per lb. Get it while it's fresh!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void save() {
        // TODO: Add post mutation
    }

    // TODO: Add "AddPost" callback

    // TODO Add post to query while offline

}
