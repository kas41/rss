package com.example.rss.view;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.rss.R;
import com.example.rss.connect.Connect;
import com.example.rss.connect.ImageLoader;
import com.example.rss.model.Event;
import com.squareup.picasso.Picasso;
import java.util.List;

public class EventsActivity extends AppCompatActivity {

    private static final String TITLE = "title";
    private static final String URL = "url";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_events_recycler_view);
        mProgress = (ProgressBar) findViewById(R.id.activity_events_bar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String title = getIntent().getStringExtra(TITLE);
        String url = getIntent().getStringExtra(URL);
        setTitle(title);

        new DownloadTask().execute(url);
    }

    public static Intent newInstance(Context context, String title, String url){
        Intent intent = new Intent(context, EventsActivity.class);
        intent.putExtra(TITLE, title);
        intent.putExtra(URL, url);
        return intent;
    }

    class DownloadTask extends AsyncTask<String, Void, List> {

        @Override
        protected void onPostExecute(List res) {
            super.onPostExecute(res);
            mProgress.setVisibility(View.INVISIBLE);
            mProgress = null;
            EventAdapter adapter = new EventAdapter(res);
            mRecyclerView.setAdapter(adapter);
        }

        @Override
        protected List doInBackground(String[] params) {
            String url = params[0];
            List eventsList = new Connect().connect(url);
            return eventsList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);
        }
    }

    class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Event mEvent;
        private ImageView mImage;
        private TextView mTitle;

        public EventHolder(View view){
            super(view);
            view.setOnClickListener(this);
            mImage = (ImageView) view.findViewById(R.id.recycler_event_image);
            mTitle = (TextView) view.findViewById(R.id.recycler_event_title);
        }

        public void bind(Event event){
            mEvent = event;
            //ручная загрузка изображений, очень кривая
            //ImageLoader.fetchImage(mEvent.getImageURL(), mImage);
            Picasso.with(getApplicationContext()).load(mEvent.getImageURL()).into(mImage);
            mTitle.setText(mEvent.getTitle());
        }

        @Override
        public void onClick(View view) {
            String url = mEvent.getLink();
            Intent intent = WebActivity.newInstance(getApplicationContext(), url);
            startActivity(intent);
        }
    }

    class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        private List mEvents;

        public EventAdapter(List list){
            mEvents = list;
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            Event event = (Event) mEvents.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.recycler_event, parent, false);
            return new EventHolder(view);
        }
    }
}
