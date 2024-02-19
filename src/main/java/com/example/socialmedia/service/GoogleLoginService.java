package com.example.socialmedia.service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.Collections;

public class GoogleLoginService {

    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    public static String authenticate(String idToken) {
        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    .setAudience(Collections.singletonList("306719217943-9blspj08tfagshq56bjgutfjvn57ska2.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idTokenObj = verifier.verify(idToken);
            if (idTokenObj != null) {
                // The user is authenticated. You can retrieve user information from idTokenObj.
                return idTokenObj.getPayload().getEmail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
