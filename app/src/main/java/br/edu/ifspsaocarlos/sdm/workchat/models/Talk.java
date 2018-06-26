package br.edu.ifspsaocarlos.sdm.workchat.models;

import com.google.gson.annotations.SerializedName;

public class Talk {

    private String id;
    @SerializedName("id_talk")
    private String idTalk;
    @SerializedName("id_user")
    private String idUser;
    @SerializedName("id_contact")
    private String idContact;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTalk() {
        return idTalk;
    }

    public void setIdTalk(String idTalk) {
        this.idTalk = idTalk;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdContact() {
        return idContact;
    }

    public void setIdContact(String idContact) {
        this.idContact = idContact;
    }
}
