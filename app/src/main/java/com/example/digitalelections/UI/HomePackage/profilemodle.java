package com.example.digitalelections.UI.HomePackage;

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
                if(!(phone.charAt(i)>'0' &&phone.charAt(i)<'9'))
                {
                    answer = " all characters have to be digits";
                }
            }
        } else {
            answer = "good";
        }
        return answer;
    }
}
