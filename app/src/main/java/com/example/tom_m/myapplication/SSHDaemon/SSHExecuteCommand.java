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

public class SSHExecuteCommand {

    public String startConnection(ServerInstance server, String command) {
        executeCommand con = new executeCommand(this, server, command);
        String result = null;
        try {
            result = con.execute().get();
        }   catch (Exception e){
            Log.e("SSHD", "Exception while executing executecommand "+ e.getLocalizedMessage());
        }
        return result;
    }

    private static class executeCommand extends AsyncTask<Void, Void, String> {
        private String command;
        private WeakReference<SSHExecuteCommand> activityWeakReference;
        private ServerInstance server;
        private JSch jsch = null;
        private Session session = null;
        private ChannelExec channel = null;
        private int exitCode;
        private StringBuilder result = new StringBuilder();

        executeCommand(SSHExecuteCommand activity, ServerInstance server, String command){
            this.jsch = new JSch();
            this.server = server;
            activityWeakReference = new WeakReference<SSHExecuteCommand>(activity);
            this.command = command;
        }

        @Override
        protected String doInBackground(Void... voids) {
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

                // Close
                channel.disconnect();
                session.disconnect();

                // Verify connection
                exitCode = channel.getExitStatus();

                if (exitCode == 0) {
                    Log.i("SSHD_CONNECTION", "Exit Status 0 - Connection successful");
                    return result.toString();
                } else {
                    Log.e("SSHD_CONNECTION", "Exit Status " + exitCode + " Connection failed");
                    return result.toString();
                }
            } catch (JSchException je) {
                Log.e("SSHD_CONNECTION", je.getLocalizedMessage());
                return result.toString();
            } catch (IOException e) {
                Log.e("SSHD_CONNECTION", e.getLocalizedMessage());
                return result.toString();
            }
        }
    }
}
