package com.example.digitalelections.UI.HomePackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;

public class resultsCountry extends Fragment {
    TextView[] textMiplaga = new TextView[6];
    Button btn;

    public resultsCountry() {
        // Required empty public constructor
    }
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
        View v = inflater.inflate(R.layout.fragment_results, container, false);
        textMiplaga[0] = v.findViewById(R.id.tvlicud);
        textMiplaga[1] = v.findViewById(R.id.tvyahadot);
        btn = v.findViewById(R.id.Changefragment);
        textMiplaga[2] = v.findViewById(R.id.tvavoda);
        textMiplaga[3] = v.findViewById(R.id.tvMahane);
        textMiplaga[4] = v.findViewById(R.id.tvZionot);
        textMiplaga[5] = v.findViewById(R.id.tvyesh);
        Resultmodle m = new Resultmodle();
        String[] Country = {textMiplaga[0].getText().toString().trim(),textMiplaga[1].getText().toString().trim(),textMiplaga[2].getText().toString().trim(),textMiplaga[3].getText().toString().trim(),textMiplaga[4].getText().toString().trim(),textMiplaga[5].getText().toString().trim()};
        String[] ans={"","","","","",""} ;
        for (int i = 0; i < Country.length; i++) {
            int finalI = i;
            m.result(requireContext(), Country[finalI], new Repository.CompletedString() {
                @Override
                public void onCompleteString(String flag) {
                    ans[finalI] = flag;
                    textMiplaga[finalI].setText(Country[finalI] +" : "+ ans[finalI]);
                }
            });
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultCity workoutPlanHomeFragment = new resultCity();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ContainerView,workoutPlanHomeFragment)
                        .addToBackStack(null).commit();
            }
        });
        return v;
    }
}