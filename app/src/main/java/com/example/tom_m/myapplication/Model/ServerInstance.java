package com.example.tom_m.myapplication.Model;

public class ServerInstance  {

    private String host;
    private String user;
    private String password;
    private int port;
    private Double lat;
    private Double lon;
    private static ServerInstance serverInstance = null;

    private ServerInstance(){
    }

    public String getHost(){ return this.host;}
    public String getUser(){ return this.user;}
    public String getPassword(){ return this.password;}
    public int getPort(){ return this.port;}
    public Double getLat() {return lat;}
    public Double getLon() {return lon;}

    public void setHost(String host){this.host = host;}
    public void setUser(String user){this.user = user;}
    public void setPassword(String password){this.password = password;}
    public void setPort(int port){this.port = port;}
    public void setLat(Double lat) {this.lat = lat;}
    public void setLon(Double lon) {this.lon = lon;}

    public static ServerInstance getInstance(){
        if (ServerInstance.serverInstance == null)ServerInstance.serverInstance = new ServerInstance();
        return ServerInstance.serverInstance;
    }
}
