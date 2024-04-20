package com.example.digitalelections.UI.profile;

import android.content.Context;

import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.UI.SignUp.modelSignUp;

public class profilemodle {

    public String checkphone(String phone){
        String answer = "";
        boolean b = false;
        if(phone.length() != 10){
            answer = " phone number have to be at 10 digits";
            b=true;
        } else if (phone.charAt(0) != '0'&& phone.charAt(1) != '5') {
            answer = " phone number have to start with 05";
            b=true;
        } else if (!b) {
            for (int i = 0; i < 8; i++) {
                if(phone.charAt(i)<'0' ||phone.charAt(i)>'9')
                {
                    answer = " all characters have to be digits";
                }
            }
        } else {
            answer = "good";
        }
        return answer;
    }
    public void Signout(Context context){
        Repository r = new Repository(context);
        r.SignOut();
    }
    public String checkName(String name){
        String anser="";
        if (name.length() != 0) {
            String s1 = nameCheck(name);
            if (s1 != "1") {
                anser= s1;
            }
            else{
               anser = "";
            }
        }
        else
        {
            anser = "enter name";
        }
        return anser;
    }
    private String nameCheck(String s) {
        //check if first letter is upper case
        if (s.charAt(0) < 'A' || s.charAt(0) > 'Z') {
            return "The first letter is upper-case letter";
        }
        //check if there any other upper case letter
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') && !(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                return "all letter except the first letter are lower-case letters";
            }
        }
        return "1";
    }
}
