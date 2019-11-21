package com.example.abc.homeautomation;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by abc on 2/1/17.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "######";

    //public static final String tokens="dgnsQQC-dtk:APA91bGkQS-sNyToVxbnD_wMUpmIfXl9ngUgR_rCBj-IVU7lhqWkNUcEarcg7oRnGw9w627Fkqrm82a5_1Aq5kl50nc9z-Iru3rp1euF2hxP0yrr3DQ9VAmcqsx8CDjgqovaxqpB7aU0";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        //String token="dgnsQQC-dtk:APA91bGkQS-sNyToVxbnD_wMUpmIfXl9ngUgR_rCBj-IVU7lhqWkNUcEarcg7oRnGw9w627Fkqrm82a5_1Aq5kl50nc9z-Iru3rp1euF2hxP0yrr3DQ9VAmcqsx8CDjgqovaxqpB7aU0";

        SharedPreferences preferences=this.getSharedPreferences("FCMtoken",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("Token",refreshedToken);
        editor.commit();
    }
}
