package com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gaby on 25/07/2016.
 */
public class API_Adapter {

    public API_Endpoints establecerConexionApiInstagram(Gson gson){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Config.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(API_Endpoints.class);
    }

    public API_Endpoints establecerConexionApiInstagram2(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_Config.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                ;

        return retrofit.create(API_Endpoints.class);
    }

    public Gson construyeGsonDeserializadorMediaRecent(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(API_model.class, new API_Deserializador());
        return gsonBuilder.create();
    }

}
