package com.gendigital.gabywear.actividad_principal.servicios_notificacion;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Gaby on 23/07/2016.
 *
 * Servicio permanente que obtiene un identificador de nuestro dispositivo
 * cada vez que se actualiza
 */
public class NotificacionTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "FIREBASE_TOKEN_UPDATED";
    @Override
    public void onTokenRefresh() {
        /*
        *  llamado si el token es actualizado.
        *  Esto puede ocurrir si la seguridad del token anterior fue comprometida.
        *  Tambien es llamado cuando se genera por primera vez el token
         */

        //super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        almacenaToken(refreshedToken);
    }
    private void almacenaToken(String token) {
        // codigo para almacenar el token
        Log.d(TAG, "Token [" + token + "] guardado");
    }
}
