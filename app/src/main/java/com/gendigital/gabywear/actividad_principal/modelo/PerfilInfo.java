package com.gendigital.gabywear.actividad_principal.modelo;

/**
 * Created by Gaby on 07/07/2016.
 */
public class PerfilInfo {
    private String urlFoto;
    private String idPicture;
    private int meGusta;
    private boolean liked;
    public static String userID;
    public static String userFullName;
    public static String userPicture;

    public PerfilInfo(String urlFoto, String idPicture, int meGusta, boolean liked) {
        this.urlFoto = urlFoto;
        this.idPicture = idPicture;
        this.meGusta = meGusta;
        this.liked = liked;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public int getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(int meGusta) {
        this.meGusta = meGusta;
    }

    public String getIdPicture() {
        return idPicture;
    }

    public void setIdPicture(String idPicture) {
        this.idPicture = idPicture;
    }

    public void incrementaLikes() {
        this.meGusta++;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
