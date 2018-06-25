package br.edu.ifspsaocarlos.sdm.workchat.api;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.models.Contato;
import br.edu.ifspsaocarlos.sdm.workchat.models.Mensagem;
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

    @POST("mensagem")
    Call<ResponseBody> postMensagem(@Body RequestBody newMessage);

    @GET("contato/{idContato}")
    Call<Contato> getContato(@Path("idContato") String idContato);

    @POST("contato/{idContato}")
    Call<Contato> updateNameMyPerfil(@Path("idContato") String idContato,
                                     @Body Contato contato);

    @GET("rawmensagens/{ultimaMensagemId}/{origemId}/{destinoId}")
    Call<List<Mensagem>> getMensagems(@Path("ultimaMensagemId") String ultimaMensagemId,
                                      @Path("origemId") String origemId, @Path("destinoId") String destinoId);

    @GET("rawmensagens/{ultimaMensagemId}/{destinoId}/{origemId}")
    Call<List<Mensagem>> getMensagensDoDestinatario(@Path("ultimaMensagemId") String ultimaMensagemId,
                                                    @Path("destinoId") String destinoId, @Path("origemId") String origemId);
}
