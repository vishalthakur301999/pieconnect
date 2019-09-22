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
import java.lang.ref.WeakReference;
import java.util.Properties;

public class SSHConnectToRemoteHost {

    public static String TAG = "";

    public boolean startConnection(ServerInstance server) {
        connectToRemoteHost con = new connectToRemoteHost(this, server);
        boolean result = false;
        try {
            result = con.execute().get();
        }   catch (Exception e){
            Log.e("SSHD", "Exception while executing con.execute "+ e.getLocalizedMessage());
        }
        return result;
    }

    private static class connectToRemoteHost extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<SSHConnectToRemoteHost> activityWeakReference;
        private ServerInstance server;
        private JSch jsch = null;
        private Session session = null;
        private ChannelExec channel = null;
        private int exitCode;

        connectToRemoteHost(SSHConnectToRemoteHost activity, ServerInstance server){
            this.jsch = new JSch();
            this.server = server;
            activityWeakReference = new WeakReference<SSHConnectToRemoteHost>(activity);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                Log.i("CNC", "Starting connection");
                // Connect
                session = jsch.getSession(server.getUser(), server.getHost(), server.getPort());
                session.setPassword(server.getPassword());
                Properties prop = new Properties();
                prop.put("StrictHostKeyChecking", "no");
                session.setConfig(prop);
                session.connect();

                // Channel config
                channel = (ChannelExec) session.openChannel("exec");
                ((ChannelExec)channel).setCommand("id");
                channel.setInputStream(null);
                ((ChannelExec)channel).setErrStream(System.err);
                InputStream in  = channel.getInputStream();
                channel.connect();


                byte[] tmp=new byte[1024];
                while(true){
                    while(in.available()>0){
                        int i=in.read(tmp, 0, 1024);
                        if(i<0)break;
                    }
                    if(channel.isClosed()){
                        if(in.available()>0) continue;
                        break;
                    }
                }

                // Close
                channel.disconnect();
                session.disconnect();

                // Verify connection
                exitCode = channel.getExitStatus();

                if (exitCode == 0) {
                    Log.i("SSHD_CONNECTION", "Exit Status 0 - Connection successful");
                    return true;
                } else {
                    Log.e("SSHD_CONNECTION", "Exit Status " + exitCode + " Connection failed");
                    return false;
                }
            } catch (JSchException je) {
                Log.e("SSHD_CONNECTION", je.getLocalizedMessage());
                return false;
            } catch (IOException e) {
                Log.e("SSHD_CONNECTION", e.getLocalizedMessage());
                return false;
            }
        }
    }
}
