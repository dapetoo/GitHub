package com.iamdeejay.github.controller;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iamdeejay.github.R;
import com.squareup.picasso.Picasso;

public class ActivityDetail extends AppCompatActivity {

    TextView userName, githubLink;
    Toolbar toolbar;
    ImageView githubAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        githubAvatar = findViewById(R.id.user_image);
        userName = findViewById(R.id.username);
        githubLink = findViewById(R.id.githubLink);

        String username = getIntent().getExtras().getString("login");
        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        String link = getIntent().getExtras().getString("html_url");

        githubLink.setText(link);
        Linkify.addLinks(githubLink, Linkify.WEB_URLS);

        userName.setText(username);
//        Picasso.get()
//                .load(avatarUrl)
//                .centerCrop()
//                .placeholder(R.drawable.ic_launcher_background)
//                .into(githubAvatar);

        Glide.with(this)
                .load(avatarUrl)
                .into(githubAvatar);

        getSupportActionBar().setTitle("Detail Activity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail, menu);
        MenuItem menuItem =menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForcastIntent());
        return true;
    }

    private Intent createShareForcastIntent(){
        String username = getIntent().getExtras().getString("login");
        String link = getIntent().getExtras().getString("link");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Check out this awesome developer @" + username + ", " + link)
                .getIntent();
        return shareIntent;
    }
}
