package com.gendigital.gabywear.actividad_principal.servicios_notificacion;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.view.Gravity;

import com.gendigital.gabywear.R;
import com.gendigital.gabywear.actividad_principal.Config_Acciones;
import com.gendigital.gabywear.actividad_principal.MainActivity;
import com.gendigital.gabywear.actividad_principal.modelo.CuentaInstagram;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Gaby on 24/07/2016.
 *
 * Servicio permanente que recibe notificaciones desde la consola de Firebase
 */
public class NotificationService extends FirebaseMessagingService {
    private static final String TAG = "FIREBASE_NOTIFY";
    public static final int NOTIFICATION_ID = 001;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        if(remoteMessage.getData().containsKey("EMISOR") && remoteMessage.getData().containsKey("RECEPTOR")){
            CuentaInstagram.EMISOR = Integer.parseInt(remoteMessage.getData().get("EMISOR"));
            CuentaInstagram.RECEPTOR = Integer.parseInt(remoteMessage.getData().get("RECEPTOR"));
            Log.d("EMISOR",remoteMessage.getData().get("EMISOR").toString());
            Log.d("RECEPTOR",remoteMessage.getData().get("RECEPTOR").toString());
        }
        //Log.d(TAG, "DATA: " + remoteMessage.getData());

        enviarNotificacion(remoteMessage);

    }

    public void enviarNotificacion(RemoteMessage remoteMessage){

        Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Creates an explicit intent for an Activity in your app
        Intent iMain = new Intent(this, MainActivity.class);
        iMain.putExtra(Config_Acciones.EMISOR, CuentaInstagram.DISPOSITIVO_ACTUAL);
        iMain.putExtra(Config_Acciones.RECEPTOR, CuentaInstagram.actualIndice);

        /*
        TaskStackBuilder tsb = TaskStackBuilder.create(getApplicationContext());
        tsb.addParentStack(MainActivity.class);
        tsb.addNextIntent(iMain);
        */
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, iMain, PendingIntent.FLAG_UPDATE_CURRENT);

        //PendingIntent pendingIntent = tsb.getPendingIntent(123, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent actionIntent1 = new Intent(this, MainActivity.class);
        actionIntent1.setAction(Config_Acciones.ACTION_KEY_SEGUIR);
        PendingIntent actionPendingIntent1 = PendingIntent.getActivity(this, 221, actionIntent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent actionIntent2 = new Intent(this, MainActivity.class);
        actionIntent2.setAction(Config_Acciones.ACTION_KEY_VER);
        PendingIntent actionPendingIntent2 = PendingIntent.getActivity(this, 222, actionIntent2, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent actionIntent3 = new Intent(this, MainActivity.class);
        actionIntent3.setAction(Config_Acciones.ACTION_KEY_YO);
        PendingIntent actionPendingIntent3 = PendingIntent.getActivity(this, 223, actionIntent3, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.WearableExtender wearableExtender =
                new NotificationCompat.WearableExtender()
                        .setHintHideIcon(true)
                        .setBackground(BitmapFactory.decodeResource(getResources(),
                                R.drawable.mariposa_morada))
                        .setGravity(Gravity.CENTER_VERTICAL)
                ;


        // define acciones para phone
        NotificationCompat.Action action1 =
                new NotificationCompat.Action.Builder(R.drawable.ic_add_user,
                        getString(R.string.notify_follow), actionPendingIntent1)
                        .build();

        NotificationCompat.Action action2 =
                new NotificationCompat.Action.Builder(R.drawable.ic_see_user,
                        getString(R.string.notify_see), actionPendingIntent2)
                        .build();

        NotificationCompat.Action action3 =
                new NotificationCompat.Action.Builder(R.drawable.ic_me,
                        getString(R.string.notify_me), actionPendingIntent3)
                        .build();

        // define acciones para watch
        NotificationCompat.Action action4 =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_follow,
                        getString(R.string.notify_follow), actionPendingIntent1)
                        .build();

        NotificationCompat.Action action5 =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_see,
                        getString(R.string.notify_see), actionPendingIntent2)
                        .build();

        NotificationCompat.Action action6 =
                new NotificationCompat.Action.Builder(R.drawable.ic_full_me,
                        getString(R.string.notify_me), actionPendingIntent3)
                        .build();



        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Notificacion")
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.drawable.infopopup48)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSound(sonido)
                .addAction(action1)
                .addAction(action2)
                .addAction(action3)
                .extend(wearableExtender.addAction(action4))
                .extend(wearableExtender.addAction(action5))
                .extend(wearableExtender.addAction(action6))

                ;
       NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, nBuilder.build());
    }
}