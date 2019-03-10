package com.jss.abhijeet.zealicon.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jss.abhijeet.zealicon.R;
import com.jss.abhijeet.zealicon.model.EventData;

import java.lang.reflect.Type;
import java.util.List;

import static com.jss.abhijeet.zealicon.utils.TitleCaseConverter.toTitleCase;

public class EventDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    private EventData eventData;
    private TextView eventTime, eventDate, eventVenue;
    private TextView eventDescription;
    private TextView prize1, prize2, contactName, contactNumber;
    private FloatingActionButton callButton, bookmarkButton;
    private boolean isBookMark = false;
    private ConstraintLayout moreDetails;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.white));
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventVenue = findViewById(R.id.locationTextView);
        eventDate = findViewById(R.id.eventDateTextView);
        eventDescription = (TextView) findViewById(R.id.descriptionTextView);
        prize1 = (TextView) findViewById(R.id.prize1);
        prize2 = (TextView) findViewById(R.id.prize2);
        contactNumber = (TextView) findViewById(R.id.organizerNumber1);
        contactName = (TextView) findViewById(R.id.organizerName1);
        callButton = (FloatingActionButton) findViewById(R.id.callButton1);
        bookmarkButton = (FloatingActionButton) findViewById(R.id.bookmark_fab);
        eventTime = findViewById(R.id.eventTimeTV);
        moreDetails = findViewById(R.id.more_details_layout);

        // getSupportActionBar().setDisplayShowHomeEnabled(true);
        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.app_white));
        if (getIntent() != null) {
            eventData = (EventData) getIntent().getSerializableExtra("eventData");
        }

        isBookMark = getSharedPreferences("bookmarks", 0)
                .getInt(eventData.getId(), 0) != 0;
        if (isBookMark) {
            bookmarkButton.setImageDrawable(ContextCompat.getDrawable(EventDetailActivity.this, R.drawable.ic_bookmark));
        } else {
            bookmarkButton.setImageDrawable(ContextCompat.getDrawable(EventDetailActivity.this, R.drawable.ic_bookmark_border));
        }

        collapsingToolbarLayout.setTitle(toTitleCase(eventData.getName()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            eventDescription.setText(Html.fromHtml(eventData.getDescription(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            eventDescription.setText(Html.fromHtml(eventData.getDescription()));
        }
        //eventDescription.setText(eventData.getDescription());
        boolean details = (eventData.getWinner1().equals(""));
        if (details) {
            moreDetails.setVisibility(View.GONE);
        }
        else {
            moreDetails.setVisibility(View.VISIBLE);
        }
        prize1.setText((eventData.getWinner1() != null || !eventData.getWinner1().equals("")) ? String.format("₹ %s", eventData.getWinner1()) : "Prize not updated");
        prize2.setText((eventData.getWinner2() != null || !eventData.getWinner1().equals("")) ? String.format("₹ %s", eventData.getWinner2()) : "Prize not updated");
        contactName.setText(eventData.getContact_name() != null ? toTitleCase(eventData.getContact_name()) : "Contact not updated");
        contactNumber.setText(eventData.getContact_no());
        eventVenue.setText(toTitleCase(eventData.getVenue()));
        eventDate.setText(eventData.getFullDate());
        eventTime.setText(eventData.getTiming());

        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences s = getSharedPreferences("bookmarks", 0);
                String bookmarked_events = s.getString("list_bookmarked", "[]");
                Gson gson = new Gson();
                Type type = new TypeToken<List<EventData>>() {
                }.getType();

                if (isBookMark) {
                    // delete and unflag


                    List<EventData> oldArrayList = gson.fromJson(bookmarked_events, type);
                    // unflag this event using event id to not show in ui
                    s.edit().putInt(eventData.getId(), 0).apply();
                    // add this event object to array list of bookmarks
                    s.edit().putString("list_bookmarked", gson.toJson(oldArrayList)).apply();
                    isBookMark = false;
                    bookmarkButton.setImageDrawable(ContextCompat.getDrawable(EventDetailActivity.this, R.drawable.ic_bookmark_border));

                    Snackbar.make(view, "The event is no longer bookmarked", Snackbar.LENGTH_SHORT)
                            .show();


                } else {
                    // add and show flag

                    List<EventData> oldArrayList = gson.fromJson(bookmarked_events, type);
                    oldArrayList.add(eventData);

                    //JSONArray bookMarkArray = new JSONArray(bookmarked_events);
                    //bookMarkArray.put(gson.toJson(eventData));
                    // flag this event using event id to show in ui
                    s.edit().putInt(eventData.getId(), 1).apply();
                    // add this event object to array list of bookmarks
                    s.edit().putString("list_bookmarked", gson.toJson(oldArrayList)).apply();
                    isBookMark = true;
                    bookmarkButton.setImageDrawable(ContextCompat.getDrawable(EventDetailActivity.this, R.drawable.ic_bookmark));
                    Snackbar.make(view, "Event Bookmarked Successfully", Snackbar.LENGTH_SHORT)
                            .show();


                }
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("onClick Call Fab", "" + eventData.getContact_no());
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(EventDetailActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            0);
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + eventData.getContact_no()));
                startActivity(intent);
            }
        });


    }
}

