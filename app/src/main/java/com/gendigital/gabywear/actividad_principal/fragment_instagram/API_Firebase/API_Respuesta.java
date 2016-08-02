package com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase;

/**
 * Created by Gaby on 25/07/2016.
 */
public class API_Respuesta {
    public String id_registro;
    public String id_dispositivo;
    public String id_usuario_instagram;

    public API_Respuesta(String id_registro, String id_dispositivo, String id_usuario_instagram) {
        this.id_registro = id_registro;
        this.id_dispositivo = id_dispositivo;
        this.id_usuario_instagram = id_usuario_instagram;
    }


    public String getIdRegistro() {
        return id_registro;
    }

    public void setIdRegistro(String id_registro) {
        this.id_registro = id_registro;
    }

    public String getIdDispositivo() {
        return id_dispositivo;
    }

    public void setIdDispositivo(String id_dispositivo) {
        this.id_dispositivo = id_dispositivo;
    }

    public String getIdUsuario() {
        return id_usuario_instagram;
    }

    public void setIdUsuario(String id_usuario_instagram) {
        this.id_usuario_instagram = id_usuario_instagram;
    }
}
