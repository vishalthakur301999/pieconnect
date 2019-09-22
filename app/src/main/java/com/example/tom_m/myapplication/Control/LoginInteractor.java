package com.example.tom_m.myapplication.Control;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tom_m.myapplication.SSHDaemon.SSHConnectToRemoteHost;
import com.example.tom_m.myapplication.Model.ServerInstance;
import com.google.android.gms.maps.GoogleMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginInteractor implements ILoginInteractor {

    ServerInstance server;
    public static String TAG = "INPUT";

    public boolean validateHostname(String host){
        if (isValidIp(host) || isValidHostName(host))   return true;
        else                                            return false;
    }

    public boolean validatePort(int port){
        if (port < 65.535 && port > 0)  {
            Log.i(TAG, "Port valid -> " + port );
            return true;
        } else  {
            Log.i(TAG, "Port invalid -> " + port);
            return false;
        }
    }

    public boolean validateLogin(String host, String user, String password, int port ){
        setServer(host, user, password, port);
        SSHConnectToRemoteHost sshConnection = new SSHConnectToRemoteHost();
        if (sshConnection.startConnection(server))  return true;
        else                                        return false;
    }

    private void setServer(String host, String user, String password, int port){
        this.server = ServerInstance.getInstance();
        this.server.setHost(host);
        this.server.setPassword(password);
        this.server.setUser(user);
        this.server.setPort(port);
    }

    /**
     * Validates if the Parameter is a valid ipv4.
     * @param ip
     * @return true, if valid Host
     */
    private boolean isValidIp(String ip){
        String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
        if (ip.matches(PATTERN)) Log.i(TAG, "Hostname is valid IP ");
        return ip.matches(PATTERN);
    }

    /**
     * Returns true if a hostname is valid
     * @param hostName
     * @return
     */
    private boolean isValidHostName(String hostName) {
        boolean status = true;

        if(hostName==null) status =  false;

        hostName = hostName.trim();
        if("".equals(hostName) || hostName.indexOf(" ")!=-1) status = false;
        else {
            // Use regular expression to verify host name
            Pattern p = Pattern.compile("[a-zA-Z0-9\\.\\-\\_]+");
            Matcher m = p.matcher(hostName);
            status =  m.matches();

            if(status){
                String tmp = hostName;
                if(hostName.length()>15) tmp = hostName.substring(0, 15);

                // Use another regular expression to verify the first 15 charactor.
                p = Pattern.compile("((.)*[a-zA-Z\\-\\_]+(.)*)+");
                m = p.matcher(tmp);
                status = m.matches();
            }
        }
        Log.i(TAG, "Hostname is valid");
        return status;
    }

}
