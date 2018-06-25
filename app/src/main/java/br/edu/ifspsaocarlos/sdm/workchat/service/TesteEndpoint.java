package br.edu.ifspsaocarlos.sdm.workchat.service;

import android.os.Build;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.workchat.models.Mensagem;
import br.edu.ifspsaocarlos.sdm.workchat.tool.DateDeserializer;
import br.edu.ifspsaocarlos.sdm.workchat.tool.Tools;

public class TesteEndpoint {


    public static List<Mensagem> list(String urlSpec) {

        List<Mensagem> list = new ArrayList<>();

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            URL url = new URL(urlSpec);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            String line = null;
           // StringBuilder out = new StringBuilder();
            try (BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (urlConnection.getInputStream())))) {
              /*  for (String line = responseBuffer.readLine(); line != null; line = responseBuffer.readLine()) {
                    Log.i("Line>>>", line);
                    out.append(line);
                }*/
                line = responseBuffer.readLine();
            }
            Log.i("Line>>>", line);

           /* String value = new String(line.getBytes("UTF-8"));
            Log.i("value>>>", value);

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(value);
            wr.flush();
            wr.close();*/

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (urlConnection.getInputStream()), StandardCharsets.UTF_8));

            Tools t = new Tools();
            String realJson = t.dataArrayToJson(responseBuffer);

            Log.i("realJson>>>", realJson);

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
            Gson gson = gsonBuilder.create();

            TypeToken<List<Message>> token = new TypeToken<List<Message>>() {
            };

            list = gson.fromJson(realJson, token.getType());

        } catch (IOException e) {
            System.out.println("ERROR>>>>" + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    Log.i("ERROR>>>", ex.getMessage());
                }
            }
        }
        return list;
    }
}
