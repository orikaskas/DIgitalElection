package com.example.digitalelections.UI.profile;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.digitalelections.Repositry.Repository;
import com.example.digitalelections.UI.SignUp.modelSignUp;
import com.example.digitalelections.User.User;

public class profilemodle {
    private Repository r;
    public profilemodle(Context context){
        r = new Repository(context);
    }

    // בדיקת תקינות מספר הטלפון
    public String checkphone(String phone) {
        String answer = "";
        boolean b = false;
        if (phone.length() != 10) {
            answer = "מספר הטלפון חייב להיות בעל 10 ספרות";
            b = true;
        }
        if (phone.charAt(0) != '0' && phone.charAt(1) != '5') {
            answer = "מספר הטלפון חייב להתחיל ב-05";
            b = true;
        }
        for (int i = 0; i < 8; i++) {
                if (phone.charAt(i) < '0' || phone.charAt(i) > '9') {
                    answer = "כל התווים חייבים להיות ספרות";
                    b=true;
                }
        }
        if(!b)
        {
            answer = "good";
        }
        return answer;
    }

    // התנתקות מהמשתמש הנוכחי
    public void Signout() {
        r.SignOut();
    }

    // בדיקת תקינות שם המשתמש
    public String checkName(String name) {
        String anser = "";
        if (name.length() != 0) {
            String s1 = nameCheck(name);
            if (!s1.equals("1")) {
                anser = s1;
            } else {
                anser = "";
            }
        } else {
            anser = "נא להזין שם";
        }
        return anser;
    }

    // בדיקה נוספת של השם
    private String nameCheck(String s) {
        // בדיקה האם האות הראשונה היא אות גדולה
        if (s.charAt(0) < 'A' || s.charAt(0) > 'Z') {
            return "האות הראשונה חייבת להיות אות גדולה";
        }
        // בדיקה האם כל האותיות האחרות הן אותיות קטנות
        for (int i = 0; i < s.length(); i++) {
            if (!((s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') || (s.charAt(i) >= 'a' && s.charAt(i) <= 'z'))) {
                return "כל האותיות, למעט האות הראשונה, חייבות להיות אותיות קטנות";
            }
        }
        return "1";
    }

    public void SavePhoto(Bitmap photo) {
        r.SavePhotoInStorage(photo);
    }
    public void getPhoto( Repository.CompletedUri uri){
        r.getPhotoFromStorage(uri);
    }
    // עדכון המידע בבסיס הנתונים
    public void Update() {
        r.UpdateUser(User.getId(), User.getEmail(), User.getUsername(), User.getAge(), User.getPhone());
    }

    public void DeleteUser(Repository.Completed completed) {
        r.DeleteUser(completed);
    }
}
