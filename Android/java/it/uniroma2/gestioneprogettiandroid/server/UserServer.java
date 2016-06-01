package it.uniroma2.gestioneprogettiandroid.server;

import android.content.res.Resources;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import it.uniroma2.gestioneprogettiandroid.R;
import it.uniroma2.gestioneprogettiandroid.exception.ServiceUnavailableException;
import it.uniroma2.gestioneprogettiandroid.exception.WrongCredentialsException;

public final class UserServer implements IUserServer {

    private static UserServer instance;

    private final String host;
    private final int port;
    private final String loginUrl;
    private final String logoutUrl;

    private UserServer(String host, int port, String loginUrl, String logoutUrl) {
        this.host = host;
        this.loginUrl = loginUrl;
        this.port = port;
        this.logoutUrl = logoutUrl;
    }

    public static IUserServer getInstance(Resources resources) {
        if (instance != null) {
            return instance;
        }
        String appDirectory = resources.getString(R.string.restserver_appDirectory);

        instance = new UserServer(
                resources.getString(R.string.restserver_host),
                resources.getInteger(R.integer.restserver_port),
                appDirectory + "/" + resources.getString(R.string.restserver_loginUrl),
                appDirectory + "/" + resources.getString(R.string.restserver_logoutUrl)
        );

        return instance;
    }

    @Override
    public String createSession(final String username, final String password, int profileId) throws IOException, WrongCredentialsException, ServiceUnavailableException {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;
        BufferedReader reader = null;
        try {
            JSONArray jsonArray = new JSONArray();

            jsonArray.put(username);
            jsonArray.put(password);
            jsonArray.put(Integer.toString(profileId));

            StringBuilder result = new StringBuilder();

            URL url = new URL("http", host, port, loginUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(jsonArray.toString());

            int code = connection.getResponseCode();
            if (code == 400)
                throw new WrongCredentialsException();

            if (code != 200)
                throw new ServiceUnavailableException();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } catch(IOException e) {
            throw e;
        } finally {
            if(outputStream != null)
                outputStream.close();
            if(reader != null)
                reader.close();
            if(connection != null)
                connection.disconnect();
        }
    }

    @Override
    public void deleteSession(String token) throws IOException {
        URL url = new URL("http", host, port, logoutUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("charset", "utf-8");

        DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
        wr.writeBytes(token);
        wr.close();
    }
}
