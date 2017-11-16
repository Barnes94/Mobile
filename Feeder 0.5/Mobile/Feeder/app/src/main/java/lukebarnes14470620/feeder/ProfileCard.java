package lukebarnes14470620.feeder;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;

/**
 * Created by computing on 16/11/2017.
 */

@Layout(R.layout.profile_card_view)

public class ProfileCard {

    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;


    private SwipePlaceHolderView mSwipeView;

    private String profileName;

    public ProfileCard(SwipePlaceHolderView swipeView, String name)
    {
        mSwipeView = swipeView;
        profileName = name;
    }

    @Resolve
    private void onResolved(){
        nameAgeTxt.setText(profileName);

       // profileImageView.setImageResource();
    }
    @SwipeOut
    private void onSwipedOut(){
        Log.d("EVENT", "onSwipedOut");
        mSwipeView.addView(this);

    }



}
