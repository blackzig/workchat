package br.edu.ifspsaocarlos.sdm.workchat.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zigui on 22/03/2018.
 */

public class Contato {

    private String id;
    @SerializedName("nome_completo")
    private String nomeCompleto;
    private String apelido;

    public Contato() {
    }

    public Contato(String nomeCompleto, String apelido, String id) {
        this.nomeCompleto = nomeCompleto;
        this.apelido = apelido;
        this.id = id;
    }

    public Contato(String nomeCompleto, String apelido) {
        this.nomeCompleto = nomeCompleto;
        this.apelido = apelido;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Contato{" +
                "nomeCompleto='" + nomeCompleto + '\'' +
                ", apelido='" + apelido + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
