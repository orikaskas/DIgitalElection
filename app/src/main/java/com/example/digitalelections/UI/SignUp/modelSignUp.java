package com.example.digitalelections.UI.SignUp;

import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.digitalelections.Repositry.FireBase;
import com.example.digitalelections.Repositry.MyDataBaseHelper;
import com.example.digitalelections.Repositry.Repository;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.units.qual.C;

public class modelSignUp {
    private String sname;
    private String sid;
    private String semail;
    private String phone;

    private boolean check;
    private String age;
    private String city;



    public modelSignUp(String sname, String sid, String semail, String phone, String age, String city, boolean check) {
        this.setSname(sname);
        this.setSid(sid);
        this.setSemail(semail);
        this.setPhone(phone);
        this.setAge(age);
        this.setCity(city);
        this.setCheck(check);
    }
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

    public void SingUp(Context context, Repository.Completed completed){
        FireBase fireBase = new FireBase();
         fireBase.SignUp(this.getSemail(), this.getSid(), this.getSname(), Integer.parseInt(this.getAge()), this.getPhone(), this.getCity(), context, this.isCheck(), new Repository.Completed() {
             @Override
             public void onComplete(boolean flag) {
                 completed.onComplete(flag);
             }
         });

    }
    public boolean CheckId(Context context,String userid){
        MyDataBaseHelper myDataBaseHelper = new MyDataBaseHelper(context);
        Cursor c =  myDataBaseHelper.readAllData();
        c.moveToFirst();
        int k = c.getCount();
        for (int i = 0; i < k; i++) {
            if(c.getString(2).equals(userid)){
                return false;
            }
        }
        return true;
    }
    public void GetInfo(Context context){
        Repository r = new Repository(context);
        r.getInfo();
    }
    //this method check if the input is valid
   public String[] check() {
        String[] s = new String[6];

       if (this.getSname().length() != 0) {
           String s1 = nameCheck(this.getSname());
           if (s1 != "1") {
               s[0] = s1;
           }
           else{
               s[0] = "";
           }
       }
       else
       {
           s[0] = "enter name";
       }
       if ( this.getAge().length()!= 0) {
           int num = Integer.parseInt(getAge());
           if (num > 120 || num < 18) {
               s[1] = "Age not valid";

           }
           else
               s[1]= "";
       } else {
           s[1]="Enter age";
       }
       if (getSid().length() != 9) {
           s[2]="The length should be 9 digits";
       }
       else
           s[2] = "";
       if (this.getPhone().length() != 0) {
           if (this.getPhone().charAt(0) != '0' || this.getPhone().charAt(1) != '5') {

               s[3]="The phone start with 05";
           }
           else
               s[3]="";
       } else {
           s[3]="Enter Phone";

       }
       if (getSemail().length() == 0)
       {
          s[4]="Enter Email";

       }
       else if (!EmailCheck(getSemail()))
       {
           s[4]="Email not valid";
       }
       else
           s[4] = "";
       if (this.getCity().equals("תבחר עיר")) {
           s[5] = "choose city";
       }
       else
           s[5]="";

       return s;
   }
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
