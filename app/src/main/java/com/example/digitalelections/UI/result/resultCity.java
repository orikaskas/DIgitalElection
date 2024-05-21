package com.example.digitalelections.UI.result;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.Repositry.User;

import java.util.HashMap;
import java.util.Map;

public class resultCity extends Fragment {
    TextView[] city;
    FrameLayout frameLayout;
    Button btn;
    Spinner spinner;
    LinearLayout layout;

    public resultCity() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_result_city, container, false);
        btn = v.findViewById(R.id.Changefragment1);
        spinner = new Spinner(requireContext());
        layout= new LinearLayout(requireContext());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.citys1_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,100);
        layoutParams.setMargins(0,100,0,30);
        spinner.setLayoutParams(layoutParams);
        frameLayout = v.findViewById(R.id.Flayout);
        if ("orikaskas@gmail.com".equals(User.getEmail())){
            frameLayout.addView(spinner);
        }
        else {addresult(User.getCity());
            frameLayout.addView(layout);}
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
               {
                   frameLayout.removeView(layout);
                   layout.removeAllViewsInLayout();
                   addresult(spinner.getSelectedItem().toString().trim());
                   frameLayout.addView(layout);
               }
                   @Override
                   public void onNothingSelected(AdapterView<?> parent)
                   {
                        Toast.makeText(requireActivity(), "Choose somthing", Toast.LENGTH_SHORT).show();
                   }
        });
        frameLayout.addView(layout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultsCountry workoutPlanHomeFragment = new resultsCountry();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ContainerView,workoutPlanHomeFragment)
                        .addToBackStack(null).commit();
            }
        });
        return v;
    }
    public void addresult(String city1){
        Resultmodle e=new Resultmodle();
        Map<String,Integer> map = new HashMap<>();
        map.put("אשדוד",R.array.אשדוד);
        map.put("ירושלים",R.array.ירושלים);
        map.put("אשקלון",R.array.אשקלון);
        map.put("נתניה",R.array.נתניה);
        map.put("תלאביב",R.array.תלאביב);
        map.put("לוד",R.array.לוד);
        int arrayLength = getResources().getStringArray(map.get(city1)).length;
        String[] scity=getResources().getStringArray(map.get(city1));
        city = new TextView[arrayLength-1];
        LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(-1,-1);
        layout.setLayoutParams(layoutParams1);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1,100);
        layoutParams1.setMargins(20,150,20,0);
        layoutParams.setMargins(10,50,20,0);
        for (int i = 1; i < arrayLength; i++) {
            city[i-1] = new TextView(requireContext());
            city[i-1].setText(scity[i]+ " :");
            city[i-1].setId(i);
            city[i-1].setTextSize(20);
            city[i-1].setTypeface(null, Typeface.BOLD);
            city[i-1].setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);;
            layout.addView(city[i-1]);
            city[i-1].setLayoutParams(layoutParams);
        }
        for (int i = 1; i < arrayLength; i++) {
            int finalI = i;
            e.resultCity(requireContext(), scity[i],city1,new Repository.CompletedString() {
                @Override
                public void onCompleteString(String flag) {
                    city[finalI-1].setText(city[finalI-1].getText().toString()+flag);
                }
            });
        }

    }
}