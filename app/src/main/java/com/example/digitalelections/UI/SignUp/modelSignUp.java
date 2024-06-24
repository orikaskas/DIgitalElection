package com.example.digitalelections.UI.SignUp;

import android.content.Context;

import com.example.digitalelections.DB.FireBase;
import com.example.digitalelections.Repositry.Repository;


public class modelSignUp {
    private String sname; // שם המשתמש
    private String sid; // תעודת זהות
    private String semail; // כתובת האימייל
    private String phone; // מספר הטלפון
    private boolean check; // תיבת הסימון להסכמה
    private String age; // גיל המשתמש
    private String city; // העיר שבה מתגורר המשתמש
    private Repository r;//משתנה מסוג ריפוזיטורי

    // בנאי עם פרמטרים
    public modelSignUp(String sname, String sid, String semail, String phone, String age, String city, boolean check,Context context) {
        this.setSname(sname);
        this.setSid(sid);
        this.setSemail(semail);
        this.setPhone(phone);
        this.setAge(age);
        this.setCity(city);
        this.setCheck(check);
        this.r = new Repository(context);
    }

    // בנאי ריק
    public modelSignUp(){}

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSemail() {
        return semail;
    }

    public void setSemail(String semail) {
        this.semail = semail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    // פונקציה לביצוע הרשמה
    public void SingUp(Context context, Repository.Completed completed){
        FireBase fireBase = new FireBase();
        fireBase.SignUp(this.getSemail(), this.getSid(), this.getSname(), Integer.parseInt(this.getAge()), this.getPhone(), this.getCity(), context, this.isCheck(), completed);
    }

    // פונקציה לקבלת מידע
    public void GetInfo( String Email, Repository.Completed completed){
        r.getInfo(Email, completed);
    }

    //this method check if the input is valid
    public String[] checkData() {
        String[] s = new String[6];

        // בדיקת השם
        if (this.getSname().length() != 0) {
            String s1 = nameCheck(this.getSname());
            if (s1 != "1") {
                s[0] = s1;
            } else {
                s[0] = "";
            }
        } else {
            s[0] = "הזן שם";
        }

        // בדיקת הגיל
        if (this.getAge().length() != 0) {
            int num = Integer.parseInt(getAge());
            if (num > 120 || num < 18) {
                s[1] = "גיל לא תקין";
            } else {
                s[1] = "";
            }
        } else {
            s[1] = "הזן גיל";
        }

        // בדיקת מספר תעודת הזהות
        if (getSid().length() != 9) {
            s[2] = "האורך צריך להיות 9 ספרות";
        } else {
            s[2] = "";
        }

        // בדיקת הטלפון
        if (this.getPhone().length() != 0) {
            if(this.getPhone().length() == 10){
                if (this.getPhone().charAt(0) != '0' || this.getPhone().charAt(1) != '5') {
                    s[3] = "הטלפון מתחיל ב-05";
                } else {
                    s[3] = "";
                }
            }
            else {
                s[3] = "הטלפון חייב להיות 10 תווים";
            }
        } else {
            s[3] = "הזן טלפון";
        }
        // בדיקת האימייל
        if (getSemail().length() == 0) {
            s[4] = "הזן אימייל";
        } else if (!EmailCheck(getSemail())) {
            s[4] = "אימייל לא תקין";
        } else {
            s[4] = "";
        }

        // בדיקת העיר
        if (this.getCity().equals("תבחר עיר")) {
            s[5] = "בחר עיר";
        } else {
            s[5] = "";
        }

        return s;
    }

    // בדיקת תקינות האימייל
    public boolean EmailCheck(String semail) {
        // בדיקה שהנקודה וה-@ אינם בתחילת או בסופו של האימייל
        if (semail.charAt(semail.length() - 1) == '.' || semail.charAt(semail.length() - 1) == '@' || semail.charAt(0) == '.' || semail.charAt(0) == '@') {
            return false;
        }

        int counter = 0;
        for (int i = 0; i < semail.length() - 2; i++) {
            // בדיקה אם הנקודה מופיעה אחרי ה-@
            if (semail.charAt(i) == '.' && semail.charAt(i + 1) == '@') {
                return false;
            }
            // ספירת מספר ה-@
            if (semail.charAt(i) == '@') {
                counter++;
            }
            // בדיקה אם קיימים קידומת .com או .co.
            if (!semail.contains(".com") && !semail.contains(".co.")) {
                return false;
            }
            // בדיקה שהמרחק בין הנקודה ל-@ הוא לפחות 3
            if (semail.indexOf(".") - semail.indexOf("@") <= 3) {
                return false;
            }
            // בדיקה שה-@ אינו בתור התו השלישי
            if (semail.indexOf("@") < 3) {
                return false;
            }
        }
        // בדיקה שה-@ מופיע רק פעם אחת
        if (counter != 1) {
            return false;
        }
        return true;
    }

    // בדיקת תקינות השם
    private String nameCheck(String s) {
        // בדיקה שהתו הראשון הוא אות גדולה
        if (s.charAt(0) < 'A' || s.charAt(0) > 'Z') {
            return "האות הראשונה צריכה להיות אות גדולה";
        }
        // בדיקה שאין אותיות גדולות נוספות
        for (int i = 1; i < s.length(); i++) {
            if (!(s.charAt(i) >= 'a' && s.charAt(i) <= 'z')) {
                return "כל האותיות חוץ מהאות הראשונה צריכות להיות אותיות קטנות";
            }
        }
        return "1";
    }
}