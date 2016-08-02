package com.gendigital.gabywear.actividad_principal.fragment_instagram.API_Instagram;

import com.gendigital.gabywear.actividad_principal.modelo.PerfilInfo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Gaby on 09/07/2016.
 */
public class API_Deserializador  implements JsonDeserializer<API_model> {
    @Override
    public API_model deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();
        API_model apiModel = gson.fromJson(json, API_model.class);
        JsonArray apiModelData = json.getAsJsonObject().getAsJsonArray(API_JSON_Config.MEDIA_RESPONSE_ARRAY);

        apiModel.setListaInstagram(deserializarContactoDeJson(apiModelData));
        return apiModel;
    }

    private ArrayList<PerfilInfo> deserializarContactoDeJson(JsonArray apiModelData){
        ArrayList<PerfilInfo> listaMedios = new ArrayList<>();
        for (int i = 0; i < apiModelData.size() ; i++) {
            JsonObject apiModelDataObject = apiModelData.get(i).getAsJsonObject();

            JsonObject userJson     = apiModelDataObject.getAsJsonObject(API_JSON_Config.USER);

            if (i == 0) {
                // guarda los datos del usuario (presentes en cada imagen)
                String userID           = userJson.get(API_JSON_Config.USER_ID).getAsString();
                String userFullName     = userJson.get(API_JSON_Config.USER_FULLNAME).getAsString();
                String userPicture      = userJson.get(API_JSON_Config.USER_PICTURE).getAsString();
                PerfilInfo.userID = userID;
                PerfilInfo.userFullName = userFullName;
                PerfilInfo.userPicture = userPicture;
            }

            JsonObject imageJson            = apiModelDataObject.getAsJsonObject(API_JSON_Config.MEDIA_IMAGES);
            JsonObject stdResolutionJson    = imageJson.getAsJsonObject(API_JSON_Config.MEDIA_STANDARD_RESOLUTION);
            String urlFoto                  = stdResolutionJson.get(API_JSON_Config.MEDIA_URL).getAsString();
            String idFoto                   = apiModelDataObject.get(API_JSON_Config.MEDIA_ID).getAsString();
            boolean swLiked                   = apiModelDataObject.get(API_JSON_Config.MEDIA_LIKED).getAsBoolean();

            JsonObject likesJson = apiModelDataObject.getAsJsonObject(API_JSON_Config.MEDIA_LIKES);
            int likes = likesJson.get(API_JSON_Config.MEDIA_LIKES_COUNT).getAsInt();


            PerfilInfo perfilActual = new PerfilInfo(urlFoto, idFoto, likes, swLiked);
            //perfilActual.setUrlFoto(urlFoto);
            //perfilActual.setMeGusta(likes);

            listaMedios.add(perfilActual);

        }

        return listaMedios;
    }
}
