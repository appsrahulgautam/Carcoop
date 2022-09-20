package com.carcoop.helpme.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.carcoop.helpme.Constance;

import java.util.List;

public class SendMailTask extends AsyncTask {

	private ProgressDialog statusDialog;
	private Activity sendMailActivity;

	public SendMailTask(Activity activity) {
		sendMailActivity = activity;

	}

	protected void onPreExecute() {
//		statusDialog = new ProgressDialog(sendMailActivity);
//		statusDialog.setMessage("Getting ready...");
//		statusDialog.setIndeterminate(false);
//		statusDialog.setCancelable(false);
//		statusDialog.show();
	}

	@Override
	protected Object doInBackground(Object... args) {
		try {
			Log.e("SendMailTask", "About to instantiate GMail...");
			Log.e("SendMailTask", "doInBackground: " + args[0] + " - " + args[1] + " - " + args[2]);
			publishProgress("Processing input....");
			GMail androidEmail = new GMail(args[0].toString(),
					args[1].toString(), (List) args[2], args[3].toString(),
					args[4].toString());
			publishProgress("Preparing mail message....");
			androidEmail.createEmailMessage();
			publishProgress("Sending email....");
			androidEmail.sendEmail();
			publishProgress("Email Sent.");
			Log.e("SendMailTask", "Mail Sent.");
		} catch (Exception e) {
			publishProgress(e.getMessage());
			Log.e("SendMailTask", e.getMessage(), e);
		}
		return null;
	}

	@Override
	public void onProgressUpdate(Object... values) {
//		statusDialog.setMessage(values[0].toString());

	}

	@Override
	public void onPostExecute(Object result) {
//		statusDialog.dismiss();
		LocalBroadcastManager.getInstance(sendMailActivity).sendBroadcast(new Intent(Constance.PROGRESS_STOP_ACTION));
	}

}
