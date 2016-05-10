package com.example.otoniel.sistemaept;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Otoniel on 06/03/2016.
 */
public class ServicioDeNotoficaciones extends Service {

    private static final String tagNoficaciones = "Servicio";
    NotificationManager notificationManager;
    String titulo = "Sistema CFg";
    String contenido = "Tiene nuevos mensajes por favor entre al sistema";
    static final int ID_NOTIFICACION = 1;

    public ServicioDeNotoficaciones() {

    }

    @SuppressWarnings("deprecation")

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        Log.d(tagNoficaciones, "Servicio creado...");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.notificacion, "Sistemas", 0);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(ServicioDeNotoficaciones.this, MainActivity.class), 0);

        notificationManager.notify(ID_NOTIFICACION, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID) {
        Log.d(tagNoficaciones, "Servicio Iniciado");

        return super.onStartCommand(intent, flags, startID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(ID_NOTIFICACION);
    }
}
