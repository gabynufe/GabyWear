package com.gendigital.gabywear.actividad_principal.modelo;

import java.util.ArrayList;

/**
 * Created by Gaby on 10/07/2016.
 */
public class CuentaInstagram {
    private String userID;
    private String userName;
    private String userFullName;
    private String userPicture;
    private String userRegistro;
    public static ArrayList<Object> listaCuentas = new ArrayList<Object>();
    public static int actualIndice;
    public static int selectedIndice;
    public static int DISPOSITIVO_ACTUAL;
    public static int EMISOR;
    public static int RECEPTOR;

    public CuentaInstagram(String userID, String userName, String userFullName, String userPicture, String userRegistro) {

        this.userID = userID;
        this.userName = userName;
        this.userFullName = userFullName;
        this.userPicture = userPicture;
        this.userRegistro = userRegistro;
        setItem(this);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserRegistro() {
        return userRegistro;
    }

    public void setUserRegistro(String userRegistro) {
        this.userRegistro = userRegistro;
    }

    public static ArrayList<Object> getListaCuentas() {
        return listaCuentas;
    }

    public static void setListaCuentas(ArrayList<Object> listaCuentas) {
        CuentaInstagram.listaCuentas = listaCuentas;
    }

    public static void setItem(Object newObjeto) {
        listaCuentas.add(newObjeto);
    }

    public static CuentaInstagram getItem(int indice) {
        return (CuentaInstagram) listaCuentas.get(indice);
    }
}
