package com.example.digitalelections.UI.Vote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;

import java.util.HashMap;
import java.util.Map;

public class VoteActivity extends AppCompatActivity {
    Button btnCountry,btnCity,goback;
    Boolean[] CheckableVote ={false};
    Boolean[] CheckableVoteCity ={false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        btnCountry = findViewById(R.id.VoteCountryBtn);
        btnCity = findViewById(R.id.VoteCityBtn);
        goback = findViewById(R.id.BackfromVote);
        if(User.getVote() == 1)
            CheckableVote[0] =true;
        else
            CheckableVote[0]=false;
        if(User.getVoteCity()==1)
            CheckableVoteCity[0] = true;
        else
            CheckableVoteCity[0] = false;
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckableVote[0]){
                    VoteCountryDialog();
                }
                else {
                    Toast.makeText(VoteActivity.this, "אתה לא יכול להצביע,הצבעת כבר", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CheckableVoteCity[0]){
                    VoteCity();
                }
                else {
                    Toast.makeText(VoteActivity.this, "אתה לא יכול להצביע,הצבעת כבר", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void VoteCountryDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.countrydialog);
        dialog.setCancelable(false);
        Button white = dialog.findViewById(R.id.VoteWhiteCard);
        Button normal=dialog.findViewById(R.id.VoteNormal);
        Spinner spinner1 = dialog.findViewById(R.id.SCountry);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.VoteCountry_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modle modle = new modle();
                User.setVote(1);
                modle.Updatevote(VoteActivity.this, User.getVote(), User.getVoteCity(), User.getId(), new Repository.Completed() {
                    @Override
                    public void onComplete(boolean flag) {
                        CheckableVote[0] = true;
                    }
                });
                dialog.dismiss();
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spinner1.getSelectedItem().toString().equals("בחר מפלגה"))
                {
                    modle modle = new modle();
                    User.setVote(1);
                    modle.Updatevote(VoteActivity.this, User.getVote(), User.getVoteCity(), User.getId(), new Repository.Completed() {
                        @Override
                        public void onComplete(boolean flag) {
                            if (flag){
                                modle.UpdateNormal(VoteActivity.this,spinner1.getSelectedItem().toString());
                                CheckableVote[0] = true;
                            }
                        }
                    });
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(VoteActivity.this, "בחר מפלגה", Toast.LENGTH_SHORT).show();
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
    public void VoteCity(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.countrydialog);
        dialog.setCancelable(false);
        Button white = dialog.findViewById(R.id.VoteWhiteCard);
        Button normal=dialog.findViewById(R.id.VoteNormal);
        Spinner spinner = dialog.findViewById(R.id.SCountry);
        String city = User.getCity() ;
        Map<String,Integer>  map = new HashMap<>();
        map.put("אשדוד",R.array.אשדוד);
        map.put("ירושלים",R.array.ירושלים);
        map.put("אשקלון",R.array.אשקלון);
        map.put("נתניה",R.array.נתניה);
        map.put("תלאביב",R.array.תלאביב);
        map.put("לוד",R.array.לוד);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, map.get(city), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modle modle = new modle();
                User.setVoteCity(1);

                modle.Updatevote(VoteActivity.this, User.getVote(), User.getVoteCity(), User.getId(), new Repository.Completed() {
                    @Override
                    public void onComplete(boolean flag) {
                        CheckableVoteCity[0] = true;
                    }
                });
                dialog.dismiss();
            }
        });
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!spinner.getSelectedItem().toString().equals("בחר מקומי"))
                {
                    modle modle = new modle();
                    User.setVoteCity(1);
                    modle.Updatevote(VoteActivity.this, User.getVote(), User.getVoteCity(), User.getId(), new Repository.Completed() {
                        @Override
                        public void onComplete(boolean flag) {
                            if (flag){
                                modle.UpdateNormalCity(VoteActivity.this,spinner.getSelectedItem().toString());
                                CheckableVoteCity[0] = true;
                            }
                        }
                    });
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(VoteActivity.this, "בחר מקומי", Toast.LENGTH_SHORT).show();
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }
}
