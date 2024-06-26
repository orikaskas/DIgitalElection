package com.example.digitalelections.UI.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.digitalelections.R;
import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.UI.result.Resultmodle;
import com.example.digitalelections.UI.result.resultCity;

public class resultsCountry extends Fragment {
    TextView[] textMiplaga = new TextView[6]; // מערך של TextViews להצגת התוצאות
    Button btn; // כפתור להחלפת פרגמנטים

    public resultsCountry() {
        // בנאי ריק
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // אין פעולות מסוימות בזמן היצירה
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // הפונקציה המוצגת למשתמש
        View v = inflater.inflate(R.layout.fragment_results, container, false); // הגדרת הממשק הגרפי
        textMiplaga[0] = v.findViewById(R.id.tvlicud); // איתור ה-TextViews בקוד
        textMiplaga[1] = v.findViewById(R.id.tvyahadot);
        btn = v.findViewById(R.id.Changefragment); // איתור הכפתור בקוד
        textMiplaga[2] = v.findViewById(R.id.tvavoda);
        textMiplaga[3] = v.findViewById(R.id.tvMahane);
        textMiplaga[4] = v.findViewById(R.id.tvZionot);
        textMiplaga[5] = v.findViewById(R.id.tvyesh);

        Resultmodle m = new Resultmodle(requireContext()); // יצירת מופע של מודל התוצאות
        String[] Country = {textMiplaga[0].getText().toString().trim(),textMiplaga[1].getText().toString().trim(),textMiplaga[2].getText().toString().trim(),textMiplaga[3].getText().toString().trim(),textMiplaga[4].getText().toString().trim(),textMiplaga[5].getText().toString().trim()}; // מערך של הקטגוריות
        String[] ans={"","","","","",""} ; // מערך תוצאות מקומי

        for (int i = 0; i < Country.length; i++) {
            // לולאה על כל הקטגוריות
            int finalI = i;
            m.result( Country[finalI], new Repository.CompletedString() {
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
                // לחיצה על הכפתור: החלפת הפרגמנט ל- resultCity
                resultCity workoutPlanHomeFragment = new resultCity();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.ContainerView,workoutPlanHomeFragment)
                        .addToBackStack(null).commit();
            }
        });

        return v; // החזרת התצוגה למשתמש
    }
}
