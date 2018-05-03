package br.edu.ifspsaocarlos.sdm.workchat.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifspsaocarlos.sdm.workchat.api.MensageiroApi;
import br.edu.ifspsaocarlos.sdm.workchat.api.NameGenerator;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zigui on 24/04/2018.
 */

public class Endpoint {

    private MensageiroApi mensageiroApi;
    private NameGenerator nameGenerator;
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

    public NameGenerator nameGenerator() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        gson = gsonBuilder.create();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl("https://name-generator-black.herokuapp.com/");
       // builder.baseUrl("http://192.168.10.16:8080/");
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofit = builder.build();
        nameGenerator = retrofit.create(NameGenerator.class);

        return nameGenerator;
    }

}
