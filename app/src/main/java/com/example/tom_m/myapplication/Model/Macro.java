package com.example.tom_m.myapplication.Model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import android.os.Parcel;
import android.os.Parcelable;

public class Macro implements Parcelable {

    private static final DateFormat df = new SimpleDateFormat("HH:mm dd.MM.yy");
    private String title;
    private String command;
    private int exitCode;
    private String lastExecution;
    private int amountsExecuted;
    private int imageRessource;

    public Macro(int imageRessource, String title, String command){
        this.imageRessource = imageRessource;
        this.title = title;
        this.command = command;
        this.exitCode = 0;
        this.lastExecution = "not executed yet";
        this.amountsExecuted = -1;
    }

    protected Macro(Parcel in){
        imageRessource = in.readInt();
        title = in.readString();
        command = in.readString();
        lastExecution = in.readString();
        amountsExecuted = in.readInt();
        exitCode = in.readInt();
    }

    public static final Creator<Macro> CREATOR = new Creator<Macro>() {
        @Override
        public Macro createFromParcel(Parcel in) {
            return new Macro(in);
        }

        @Override
        public Macro[] newArray(int size) {
            return new Macro[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.imageRessource);
        dest.writeString(this.title);
        dest.writeString(this.command);
        dest.writeString(this.lastExecution);
        dest.writeInt(this.amountsExecuted);
        dest.writeInt(this.exitCode);
    }

    public int getImageResource() {return this.imageRessource;}
    public String getTitle(){return this.title;}
    public String getCommand(){return this.command;}
    public int getExitCode(){return this.exitCode;}
    public int getAmountsExecuted(){return this.amountsExecuted;}
    public String getLastExecution(){return this.lastExecution;}

    public void setTitle(String title){this.title = title;}
    public void setCommand(String command){this.command = command;}
    public void setExitCode(int exitCode){this.exitCode = exitCode;}
    public void setLastExecution(String lastExecution){this.lastExecution = lastExecution;}
    public void setAmountsExecuted(int amountsExecuted){this.amountsExecuted = amountsExecuted;}
}


