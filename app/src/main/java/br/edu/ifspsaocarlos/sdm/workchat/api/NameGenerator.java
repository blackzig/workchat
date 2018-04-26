package br.edu.ifspsaocarlos.sdm.workchat.api;

import br.edu.ifspsaocarlos.sdm.workchat.models.FullName;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by zigui on 26/04/2018.
 */

public interface NameGenerator {

    @GET("name")
    Call<String> getFullNameSTR();

    @GET("name/fullname")
    Call<FullName> getFullName();

    @GET("name/fullnameraw")
    Call<FullName> getFullNameRaw();
}