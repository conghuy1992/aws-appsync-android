package com.amazonaws.postsapp;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.PersistentMutationsCallback;
import com.amazonaws.mobileconnectors.appsync.PersistentMutationsError;
import com.amazonaws.mobileconnectors.appsync.PersistentMutationsResponse;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicAPIKeyAuthProvider;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicCognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.appsync.sigv4.CognitoUserPoolsAuthProvider;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;

public class ClientFactory {
    private static volatile AWSAppSyncClient client;

    public synchronized static AWSAppSyncClient getInstance(Context context) {
        // TODO: Here
        if (client == null) {
            client = AWSAppSyncClient.builder()
                    .context(context)
//                    .credentialsProvider(getCredentialsProvider(context)) // For use with IAM/Cognito authorization
                    .apiKey(new BasicAPIKeyAuthProvider(Constants.APPSYNC_API_KEY))
                    .region(Constants.APPSYNC_REGION)
                    .serverUrl(Constants.APPSYNC_API_URL)
                    .persistentMutationsCallback(new PersistentMutationsCallback() {
                        @Override
                        public void onResponse(PersistentMutationsResponse response) {
                            Log.d("NOTERROR", response.getMutationClassName());
                        }

                        @Override
                        public void onFailure(PersistentMutationsError error) {
                            Log.e("TAG", error.getMutationClassName());
                            Log.e("TAG", "Error", error.getException());
                        }
                    })
                    .build();
        }
        return client;
    }

    // TODO: Here
    private static final AWSCredentialsProvider getCredentialsProvider(final Context context) {
        final CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                Constants.COGNITO_IDENTITY,
                Constants.COGNITO_REGION
        );
        return credentialsProvider;
    }
}