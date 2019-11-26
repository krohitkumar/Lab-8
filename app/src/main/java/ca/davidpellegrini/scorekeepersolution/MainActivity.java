package ca.davidpellegrini.scorekeepersolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;

import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.View.OnKeyListener;
import android.widget.Toast;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MainActivity extends AppCompatActivity  implements OnEditorActionListener, OnClickListener,
        OnCheckedChangeListener, OnKeyListener{

    private TextView teamA,teamB;
    private Button teamAPlus,teamAMinus,teamBPlus,teamBMinus;
    private RadioGroup incrementBy;
    private RadioButton inc1, inc5, inc10;
    private String scoreAString = "";
    private String scoreBString = "";
    private int increment = 1;

    private String incOrDecStringA = "";
    private String incOrDecStringB = "";

    private SharedPreferences savedValues,prefs;
    private boolean rememberUserChnages =true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teamA = (TextView) findViewById(R.id.teamA_score_textview);
        teamB = (TextView) findViewById(R.id.teamB_score_textview);
        teamAPlus  = (Button) findViewById(R.id.teanA_Inc);
        teamAPlus.setOnClickListener(this);
        teamBPlus  = (Button) findViewById(R.id.teamB_Inc);
        teamBPlus.setOnClickListener(this);
        teamAMinus = (Button) findViewById(R.id.teamA_Dec);
        teamAMinus.setOnClickListener(this);
        teamBMinus = (Button) findViewById(R.id.teamB_Dec);
        teamBMinus.setOnClickListener(this);
        incrementBy = (RadioGroup) findViewById(R.id.roundingRadioGroup);
        incrementBy.setOnCheckedChangeListener(this);

        inc1 = (RadioButton) findViewById(R.id.ID_by1);
        inc5 = (RadioButton) findViewById(R.id.ID_by5);
        inc10=  (RadioButton) findViewById(R.id.ID_by10);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void calculateAndDisplayA(){
        scoreAString = teamA.getText().toString();
       // scoreBString = teamB.getText().toString();

        int scoreA,scoreB;
        if(scoreAString.equals("")){
            scoreA = 0;
        }
        else{
            scoreA = Integer.parseInt(scoreAString);
        }
        /*if(scoreBString.equals("")){
            scoreB = 0;
        }
        else{
            scoreB = Integer.parseInt(scoreBString);
        }*/

            if (incOrDecStringA == "increment") {
                scoreA = scoreA + increment;

                teamA.setText(Integer.toString(scoreA));
                incOrDecStringA="";
            } else if(incOrDecStringA == "decrement") {
                scoreA = scoreA - increment;
                if(scoreA < 0)
                    teamA.setText("0");
                else
                teamA.setText(Integer.toString(scoreA));
                incOrDecStringA="";
            }


    }

    public void calculateAndDisplayB(){
        scoreBString = teamB.getText().toString();
        int scoreB;
        if(scoreBString.equals("")){
            scoreB = 0;
        }
        else{
            scoreB = Integer.parseInt(scoreBString);
        }
        /*if(scoreBString.equals("")){
            scoreB = 0;
        }
        else{
            scoreB = Integer.parseInt(scoreBString);
        }*/

            if (incOrDecStringB == "increment") {
                scoreB = scoreB + increment;
                teamB.setText(Integer.toString(scoreB));
                incOrDecStringB = "";
            } else if(incOrDecStringB == "decrement") {

                scoreB = scoreB - increment;
                if(scoreB < 0)
                    teamB.setText("0");
                else
                teamB.setText(Integer.toString(scoreB));
                incOrDecStringB="";
            }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.teanA_Inc:
                incOrDecStringA = "increment";
                calculateAndDisplayA();
                break;
            case R.id.teamA_Dec:
                incOrDecStringA = "decrement";
                calculateAndDisplayA();
                break;
            case R.id.teamB_Inc:
                incOrDecStringB = "increment";
                calculateAndDisplayB();
                break;
            case R.id.teamB_Dec:
                incOrDecStringB = "decrement";
                calculateAndDisplayB();
                break;
            /*default:
                Default is like the else in an if statement
                With onClick it can be dangerous because it
                    will run when something other than our
                    button is clicked
                break;
             */
        }

    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.e("checkedId: ", Integer.toString(checkedId));
        switch (checkedId){
            case R.id.ID_by1:
            default:
                increment = 1;
                break;
            case R.id.ID_by5:
                increment = 5;
                break;
            case R.id.ID_by10:
                increment = 10;
                break;
        }
        //calculateAndDisplayA();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.activity_menu, menu
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:
                startActivity(new Intent(
                        getApplicationContext(), SettingsActivity.class
                ));
                return true;
            case R.id.menu_about:
                Toast.makeText(this, R.string.m_about, Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Editor editor = savedValues.edit();

        rememberUserChnages= prefs.getBoolean("pref_changes_saved",true);
        if(rememberUserChnages) {
            editor.putString("scoreAString", scoreAString);
            editor.putString("scoreBString", scoreBString);
            editor.putInt("increment", increment);
        }
        editor.commit();


    }

    @Override
    protected void onStop() {
        super.onStop();
        Editor editor = savedValues.edit();

        rememberUserChnages= prefs.getBoolean("pref_changes_saved",true);
        if(rememberUserChnages) {
            editor.putString("scoreAString", scoreAString);
            editor.putString("scoreBString", scoreBString);
            editor.putInt("increment", increment);
        }
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

       rememberUserChnages= prefs.getBoolean("pref_changes_saved",true);
        if(rememberUserChnages) {
            String scoreAStr = savedValues.getString("scoreAString", "10");
           String scoreBStr = savedValues.getString("scoreBString", "10");
            int incrementValue = savedValues.getInt("increment", 1);



            if (incrementValue == 1){
                incrementBy.check(R.id.ID_by1);
                increment=1;
            }
            else if (incrementValue == 5) {
                incrementBy.check(R.id.ID_by5);
                increment=5;
            }
            else {
                incrementBy.check(R.id.ID_by10);
                increment=10;
            }

            teamA.setText(scoreAStr);
            teamB.setText(scoreBStr);
            scoreAString = scoreAStr;
            scoreBString = scoreBStr;

            //calculateAndDisplayA();
            //calculateAndDisplayB();
        }
    }
}
