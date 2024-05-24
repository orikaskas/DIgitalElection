package com.example.digitalelections.UI.Admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.digitalelections.R;
import com.example.digitalelections.UI.HomePackage.HomePage;

import org.checkerframework.common.subtyping.qual.Bottom;

public class AdminActivity extends AppCompatActivity {
    Button buttondeleteAll;
    modleadmin module;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        module = new modleadmin();
        buttondeleteAll= findViewById(R.id.btndeleteall);
        buttondeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(AdminActivity.this);
                dialog.setCancelable(false);
                dialog.setMessage("Are you sure you want to proceed, this will delete all accounts!")
                        .setPositiveButton("Yes, I am sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                module.deleteAllData();
                                Intent intent = new Intent(AdminActivity.this, HomePage.class);
                                Toast.makeText(getBaseContext(), "All data has been successfully deleted", Toast.LENGTH_SHORT).show();
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No Don't", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        }).show();
            }
        });
    }
}