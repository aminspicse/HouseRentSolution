package com.myspeec.firebaseproject;

public class Helper {

    int subString(String str){
        int cnt=0;
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == '@'){
                break;
            }
            cnt++;
        }
        return cnt;
    }
}
