package com.hfad.avc.interactor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SendIteractor {
    @SuppressLint("IntentReset")
    public void smsSend(Context context, String toSms, String messageText) {
        Intent sms;
        try {
            sms = new Intent(Intent.ACTION_SEND, Uri.parse("smsto:" + art(toSms)));
            sms.setType("text/plain");
            sms.putExtra(Intent.EXTRA_TEXT, messageText);
            context.startActivity(Intent.createChooser(sms, "Отправить"));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i("AVc", String.valueOf(ex));
        }
    }

    public String art(String st) {
        String taboo = "+-";
        for (char c : taboo.toCharArray()) {
            st = st.replace(c, ' ');
            st = st.replaceAll(" ", "");
        }
        return st;
    }

    /*
     *//**
     * отправка смс без перехода в смс-менеджер
     *//*
    public void smsSend_avto() {
        try {
            SmsManager.getDefault()
                    .sendTextMessage(numberText, null, messageText.toString(), null, null);
            Toast.makeText(getActivity().getApplicationContext(),
                    "SMS отправлено!",Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }*/
}
