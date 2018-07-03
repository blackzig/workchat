package br.edu.ifspsaocarlos.sdm.workchat.service;

import android.os.AsyncTask;
import android.util.Log;

public class VerifyMessages extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        verifyContacts();
        return null;
    }

    private void verifyContacts() {

    }

    private void verifyMessagesRangeOneMinute() {
        for (; ; ) {
            try {
                Log.i("TESTE>>>>", "TESTE");
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
