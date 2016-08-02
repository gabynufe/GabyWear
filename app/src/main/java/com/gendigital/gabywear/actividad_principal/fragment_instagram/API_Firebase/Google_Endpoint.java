package com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Firebase;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Gaby on 24/07/2016.
 */
public interface Google_Endpoint {

    @FormUrlEncoded
    @POST(API_Google_Config.KEY_GUARDA_TOKEN)
    Call<API_Respuesta> registrarTokenID(@Field("id_dispositivo") String id_dispositivo, @Field("id_usuario_instagram") String id_usuario_instagram, @Field("indice") String indice);

    @FormUrlEncoded
    @POST(API_Google_Config.KEY_GUARDA_LIKE)
    Call<ResponseBody> registrarLike(@Field("indice") String indice, @Field("user_id") String user_id, @Field("reg_id") String reg_id, @Field("foto_id") String foto_id);

    /*
    @GET(API_Google_Config.KEY_REG_LIKE)
    Call<ResponseBody> registrarLike(@Path("user_id") String idUser, @Path("reg_id") String idReg, @Path("foto_id") String idFoto);
    */
}
