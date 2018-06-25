package br.edu.ifspsaocarlos.sdm.workchat.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Tools {

    public String dataToJson(BufferedReader responseBuffer) throws IOException {
        int count = 0;
        String output;
        String realJson = null;
        while ((output = responseBuffer.readLine()) != null) {
            System.out.println(output);
            for (int i = 0; i < output.length(); i++) {
                String letter = Character.toString(output.charAt(i));
                if (letter.equals("{")) {
                    count++;
                    if (count == 3) {
                        realJson = output.substring(i, output.length() - 2);
                        break;
                    }
                }
            }
        }
        return realJson;
    }

    public String dataArrayToJson(BufferedReader responseBuffer) throws IOException {
        String output;
        String realJson = null;
        while ((output = responseBuffer.readLine()) != null) {
            System.out.println(output);
            int firstBracket = output.indexOf("[");
            realJson = "[" + output.substring(firstBracket + 1, output.length() - 3) + "]";
        }
        return realJson;
    }

    public String encrypt(String password) {
        String passworAux = null;
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            byte messageDigest[] = algorithm.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            passworAux = hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            System.out.println("erro " + e.getMessage());
        }
        return passworAux;
    }
}
