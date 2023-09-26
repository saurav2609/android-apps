package fr.centralesupelec.galtier.block08;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // display the GUI defined in the activity_first.xml file
        setContentView(R.layout.activity_first);
    }

    /**
     * called when the button from activity_first.xml is clicked
     * start the second activity and pass the rating to it
     * @param v the View which triggered the method call: should refer to the button "enter"
     */
    public void enter(View v) {
        // retrieve a reference to the View defined in the activity_first.xml
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        // get the rating set by the user
        float rating = ratingBar.getRating();
        // create an Intent to launch the second activity
        Intent goToSecond = new Intent();
        goToSecond.setClass(this, SecondActivity.class);
        // pass the rating value to the second activity
        goToSecond.putExtra("nbStars", rating);
        // start the second activity
        startActivity(goToSecond);
        // remove this current activity
        finish();
    }
}
