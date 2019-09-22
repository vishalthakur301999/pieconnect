package com.example.tom_m.myapplication.SSHDaemon;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tom_m.myapplication.Model.ServerInstance;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SSH {

    public static String TAG = "SSH";
    private static JSch jsch;
    private static Session session;

    public String executeCommandOnRemoteHost(String command){
        String output = null;
        try {
            output = new executeCommandAsyncTask(command).execute().get();
        } catch (Exception e){
            Log.e(TAG, e.getLocalizedMessage());
        }
        return output;
    }

    public Session getSession(ServerInstance server){
        try { if (this.session == null) this.session = new openSessionAsyncTask(server).execute().get();
        } catch (Exception e){ Log.e(TAG, e.getLocalizedMessage());}
        return this.session;
    }

    private static class openSessionAsyncTask extends AsyncTask<ServerInstance, Void, Session>{
        private ServerInstance server;
        private Session session;

        private openSessionAsyncTask(ServerInstance server){
            this.server = server;
        }

        @Override
        protected Session doInBackground(ServerInstance... server) {
            try {
                Log.i(TAG, "Starting connection");
                session = jsch.getSession(server[0].getUser(), server[0].getHost(), server[0].getPort());
                session.setPassword(server[0].getPassword());
                Properties prop = new Properties();
                prop.put("StrictHostKeyChecking", "no");
                session.setConfig(prop);
                session.connect();
                return session;
            } catch (JSchException je) {
                Log.e(TAG, "error while returning session");
                return null;
            }
        }
    }

    private static class executeCommandAsyncTask extends AsyncTask<String, Void, String> {
        private String command;
        private ChannelExec channel = null;
        private int exitCode;
        private StringBuilder result = new StringBuilder();

        private executeCommandAsyncTask(String command){
            this.command = command;
        }

        @Override
        protected String doInBackground(String... command) {
            try {
                channel = (ChannelExec) session.openChannel("exec");
                ((ChannelExec)channel).setCommand(this.command);
                channel.setInputStream(null);
                ((ChannelExec)channel).setErrStream(System.err);
                InputStream in  = channel.getInputStream();
                channel.connect();

                byte[] tmp=new byte[1024];
                while(true){
                    while(in.available()>0){
                        int i=in.read(tmp, 0, 1024);
                        if(i<0)break;
                        result.append(new String(tmp, 0, 1024));
                    }
                    if(channel.isClosed()){
                        if(in.available()>0) continue;
                        break;
                    }
                }

                channel.disconnect();
                exitCode = channel.getExitStatus();

                if (exitCode == 0) {
                    Log.i(TAG, "Exit Status 0 - Connection successful");
                    return result.toString();
                } else {
                    Log.e(TAG, "Exit Status " + exitCode + " Connection failed");
                    return result.toString();
                }
            } catch (JSchException je) {
                Log.e(TAG, je.getLocalizedMessage());
                return result.toString();
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
                return result.toString();
            }
        }
    }
}
