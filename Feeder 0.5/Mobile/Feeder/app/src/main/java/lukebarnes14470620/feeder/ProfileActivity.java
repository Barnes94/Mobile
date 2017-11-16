package lukebarnes14470620.feeder;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private ViewFlipper flip;
    private float initialX;


    final String TAG = "JsonParser.java";

    static String json = "";

    // array list to store tweet items from web service
    ArrayList<String> items = new ArrayList<String>();
    // json test string
    String jsonTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        flip = (ViewFlipper) findViewById(R.id.viewFlipper1);

        // Setting IN and OUT animation for view flipper
        flip.setInAnimation(this, R.anim.right_enter);
        flip.setOutAnimation(this, R.anim.left_out);

        new AsyncTaskParseJson().execute();

        //ImageView iV = new ImageView(ProfileActivity.this);
        //iV.setImageURI(Uri.parse("https://maps.googleapis.com/maps/AIzaSyBNnqcwezjFIi0-xGy3HT4-jZheoyqDnqY/Nandos/photo?parameters"));
    }


    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        // set the url of the web service to call
        String yourServiceUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=nandos+in+Lincoln+Lincolnshire&key=AIzaSyBNnqcwezjFIi0-xGy3HT4-jZheoyqDnqY";

        @Override
        // this method is used for......................
        protected void onPreExecute() {}

        @Override
        // this method is used for...................
        protected String doInBackground(String... arg0)  {

            try {
                // create new instance of the httpConnect class
                httpConnect jParser = new httpConnect();

                // get json string from service url
                String json = jParser.getJSONFromUrl(yourServiceUrl);

                // save returned json to your test string
                jsonTest = json.toString();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        // below method will run when service HTTP request is complete, will then bind tweet text in arrayList to ListView
        protected void onPostExecute(String strFromDoInBg) {
            TextView tv1 = (TextView)findViewById(R.id.jsonText);
            tv1.setText(jsonTest);
        }
    }



    //reference all of this?
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                // Getting intitial by event action down
                initialX = event.getX();
                break;

            case MotionEvent.ACTION_UP:

                // On action up the flipper will start and showing next item
                float finalX = event.getX();
                if (initialX > finalX) {

                    // Show items are 4
                    if (flip.getDisplayedChild() == 4)
                        break;

                    // Flip show next will show next item
                    flip.showNext();
                } else {

                    // If flip has no items more then it will display previous item
                    if (flip.getDisplayedChild() == 0)
                        break;
                    flip.showPrevious();
                }
                break;
        }
        return false;
    }

    // your android activity will call this method and pass in the url of the REST service



}
