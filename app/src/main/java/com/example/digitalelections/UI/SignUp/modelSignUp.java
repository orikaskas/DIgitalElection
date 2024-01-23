package com.example.digitalelections.UI.SignUp;

import android.widget.EditText;

import com.example.digitalelections.Repositry.FireBase;
import com.example.digitalelections.Repositry.User;
import com.google.firebase.auth.FirebaseAuth;

public class modelSignUp {
    User user;
    public modelSignUp(User user){
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public boolean SingUp(FirebaseAuth firebaseAuth){
        FireBase fireBase = new FireBase();
        boolean b =  fireBase.SignUp(this.user,firebaseAuth);
        return b;
    }
   // public boolean check(String email,String )
    private boolean EmailCheck(String semail)
    {
        //check if . and @ is not the first char or the last char
        if (semail.charAt(semail.length() - 1) == '.' || semail.charAt(semail.length() - 1) == '@' || semail.charAt(0) == '.' || semail.charAt(0) == '@') {
            return false;
        }
        int counter = 0;
        for (int i = 0; i < semail.length() - 2; i++) {
            //checks if . come after @
            if (semail.charAt(i) == '.' && semail.charAt(i + 1) == '@') {
                return false;
            }
            //count every @ symbol
            if (semail.charAt(i) == '@')
                counter++;
            //check if exist .com or .co.
            if (!semail.contains(".com") && !semail.contains(".co.")) {
                return false;
            }
            //check if the distance between . and @ is less than 3
            if (semail.indexOf(".") - semail.indexOf("@") <= 3) {
                return false;
            }
            //check if @ is the third char
            if (semail.indexOf("@") < 3) {
                return false;
            }
        }
        //check if @ exist more than one time
        if (counter != 1) {
            return false;
        }
        return true;
    }
    public String nameCheck(String s) {
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
