package com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram;


/**
 * Created by Gaby on 09/07/2016.
 */
public final class API_Config {
    //https://api.instagram.com/v1/
    public static final String ROOT_URL = "https://api.instagram.com/v1/";
    //public static final String ACCESS_TOKEN = "?access_token=3259702353.aa0d0f4.e61865afc9144ecc8ffc8f6dc84aa17b"; // Perrito Mike
    public static final String ACCESS_TOKEN = "?access_token=3470121575.e91c373.c9c9d75d5bb442ad800da010af91b7d0";
    public static final String ACCESS_TOKEN2 = "3470121575.e91c373.c9c9d75d5bb442ad800da010af91b7d0";
    //public static final String URL_GET_RECENT_MEDIA_USER = ROOT_URL + KEY_GET_RECENT_MEDIA_USER + ACCESS_TOKEN;
    public static final String URL_GET_RECENT_MEDIA_USER = "users/{user}/media/recent/" + ACCESS_TOKEN;
    public static final String URL_POST_MEDIA_LIKES = "media/{media-id}/likes";
    public static final String URL_POST_FOLLOW = "users/{user-id}/relationship";
    //https://api.instagram.com/v1/users/self/media/recent/?access_token=3470121575.e91c373.c9c9d75d5bb442ad800da010af91b7d0
}
