package com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase;

import com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram.API_Config;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gaby on 24/07/2016.
 */
public class API_Google_Adapter {
    public Google_Endpoint establecerConexionRestAPI(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Google_Config.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                ;

        //Log.d("FIREBASE_GOOGLE_ADAPTER", "ESTABLECIENDO CONEXION");
        return retrofit.create(Google_Endpoint.class);
    }
}
