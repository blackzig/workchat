package br.edu.ifspsaocarlos.sdm.workchat.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifspsaocarlos.sdm.workchat.R;
import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zigui on 24/04/2018.
 */

public class Endpoint {

    private MensageiroApi mensageiroApi;
    private Gson gson;
    private Retrofit retrofit;

    public MensageiroApi mensageiroAPI() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("http://www.nobile.pro.br/sdm/mensageiro/");
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        mensageiroApi = retrofit.create(MensageiroApi.class);

        return mensageiroApi;
    }

}
