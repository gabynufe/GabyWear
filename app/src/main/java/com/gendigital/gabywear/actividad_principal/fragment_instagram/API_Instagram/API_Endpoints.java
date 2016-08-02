package com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Gaby on 09/07/2016.
 */
public interface API_Endpoints {
    @FormUrlEncoded
    @POST(API_Config.URL_POST_MEDIA_LIKES)
    Call<ResponseBody> registrarLikes(@Path("media-id") String idFoto, @Field("access_token") String token);

    @FormUrlEncoded
    @POST(API_Config.URL_POST_FOLLOW)
    Call<ResponseBody> seguirUsuario(@Path("user-id") String userID, @Field("access_token") String token, @Field("action") String action);

    @GET(API_Config.URL_GET_RECENT_MEDIA_USER)
    Call<API_model> getRecentMedia(@Path("user") String user);
}
