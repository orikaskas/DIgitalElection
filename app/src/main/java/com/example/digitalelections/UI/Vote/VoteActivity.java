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

public class VoteActivity extends AppCompatActivity {
    Spinner spinner;
    Button btnCountry;
    Boolean[] CheckableVote ={false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);
        btnCountry = findViewById(R.id.VoteCountryBtn);
        modle modle = new modle();
        modle.GetVote(this, User.getId(), new Repository.Completed() {
            @Override
            public void onComplete(boolean flag) {
                CheckableVote[0] =flag;
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
    }
    public void VoteCountryDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.countrydialog);
        dialog.setCancelable(false);
        Button white = dialog.findViewById(R.id.VoteWhiteCard);
        Button normal=dialog.findViewById(R.id.VoteNormal);
        spinner = dialog.findViewById(R.id.SCountry);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.VoteCountry_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Boolean b= false;
        if(User.getVoteCity()==1)
            b=true;
        spinner.setAdapter(adapter);
        Boolean finalB = b;
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modle modle = new modle();
                modle.Updatevote(VoteActivity.this, true, finalB, User.getId(), new Repository.Completed() {
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
                if(!spinner.getSelectedItem().toString().equals("בחר מפלגה"))
                {
                    modle modle = new modle();
                    modle.Updatevote(VoteActivity.this, true, finalB, User.getId(), new Repository.Completed() {
                        @Override
                        public void onComplete(boolean flag) {
                            if (flag){
                                modle.UpdateNormal(VoteActivity.this,spinner.getSelectedItem().toString());
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
}
