package lukebarnes14470620.feeder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.places.GeoDataApi;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private SwipePlaceHolderView mSwipeView;

    String locationCoordinates = "53.228672,-0.548804";
    int searchRadius = 500;
    String searchtype = "restaurant";

    JSONObject jsonSearch = null;
    JSONArray jA = null;

    //String[] names = new String[100];
    String name = null;

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> photoRefs = new ArrayList<>();

    protected GeoDataApi m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AsyncTaskParseJson().execute();

    }


    public class AsyncTaskParseJson extends AsyncTask<String, String, String> {

        // set the url of the web service to call

        String searchURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" + locationCoordinates +"&radius=" + searchRadius + "&type="+ searchtype + "&key=AIzaSyBNnqcwezjFIi0-xGy3HT4-jZheoyqDnqY";

        @Override
        // this method is used for......................
        protected void onPreExecute() {}

        @Override
        // this method is used for...................
        protected String doInBackground(String... arg0)  {

            try {
                httpConnect jParser = new httpConnect();

                String json = jParser.getJSONFromUrl(searchURL);

                jsonSearch = new JSONObject(json);
                jA = jsonSearch.getJSONArray("results");


                for(int i = 0; i < jA.length(); i++)
                {
                    //if not nulls n shit
                    names.add(jA.getJSONObject(i).getString("name"));

                    JSONObject obj = jA.getJSONObject(i);

                    JSONArray ja2 = obj.optJSONArray("photos");

                    for(int j = 0; j < ja2.length(); j++)
                    {
                        photoRefs.add(ja2.getJSONObject(j).getString("photo_reference"));
                    }

                }




            } catch (Exception e) {
                e.printStackTrace(); //problem here bro fix
            }

            httpConnect jParser = new httpConnect();

            for(int k = 0; k < photoRefs.size(); k++)
            {
                String photosURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=" + photoRefs.get(k) + "&key=AIzaSyBNnqcwezjFIi0-xGy3HT4-jZheoyqDnqY";
                String jsonPhotoSearch = jParser.getJSONFromUrl(photosURL);
                try {
                    jsonSearch = new JSONObject(jsonPhotoSearch);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        //there needs to be some serio error checking around here. if the two arrays Names and Photos dont match up something will fucking die.

        @Override
        // below method will run when service HTTP request is complete, will then bind tweet text in arrayList to ListView
        protected void onPostExecute(String strFromDoInBg) {

            //TextView tv1 = (TextView)findViewById(R.id.textView);

            //for (int j = 0; j < names.size(); j++){
            //    tv1.append(names.get(j) + "\n");
          //  }

            mSwipeView = (SwipePlaceHolderView)findViewById(R.id.swipeView);

            mSwipeView.getBuilder()
                    .setDisplayViewCount(3)
                    .setSwipeDecor(new SwipeDecor()
                            .setPaddingTop(20)
                            .setRelativeScale(0.01f));

            for (int i = 0; i < names.size(); i++)
            {
                mSwipeView.addView(new ProfileCard(mSwipeView, names.get(i)));
            }

        }
    }



}
