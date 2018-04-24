package br.edu.ifspsaocarlos.sdm.workchat.api;

import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by zigui on 23/04/2018.
 */

public interface MensageiroApi {

    @POST("contato")
    Call<ResponseBody> postNewContact(@Body RequestBody newContact);

    @GET("contato/{idContato}")
    Call<Contato> getContato(@Path("idContato") String idContato);

}
