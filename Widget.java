package com.example.hp.alert;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class Widget extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        try {
            Log.d("widget", "created");
            for (int i = 0; i < appWidgetIds.length; i++) {

                //for texts you have to do simple ... but for buttons u have to use intents ....

                //this has the name of the class . which we want to use

                Log.d("widget", "functioning");
                Intent intent = new Intent(context, SendMessage.class);

                //set the pending intent .....

                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                //Action to be performed when the intent is clicked. .. . . . ...
                Log.d("widget", "functioning2");

                RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.activity_send_message);
                Log.d("widget", "functioning3");

                remoteView.setOnClickPendingIntent(R.id.SendMsg, pendingIntent);
                Log.d("widget", "functioning4");
                //tell app widget manager to enter the database.. . . ....

                appWidgetManager.updateAppWidget(appWidgetIds, remoteView);
                Log.d("widget", "functioning5");
            }
        } catch (Exception e) {
            Log.d("error_widget", String.valueOf(e));
        }
    }
}
