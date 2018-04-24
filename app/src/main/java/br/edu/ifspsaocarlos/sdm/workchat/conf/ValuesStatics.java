package br.edu.ifspsaocarlos.sdm.workchat.conf;

/**
 * Created by zigui on 24/04/2018.
 */

public class ValuesStatics {

    private static String ID_USER;
    private static String NICKNAME;

    public static String getIdUser() {
        return ID_USER;
    }

    public static void setIdUser(String idUser) {
        ID_USER = idUser;
    }

    public static String getNICKNAME() {
        return NICKNAME;
    }

    public static void setNICKNAME(String NICKNAME) {
        ValuesStatics.NICKNAME = NICKNAME;
    }
}
