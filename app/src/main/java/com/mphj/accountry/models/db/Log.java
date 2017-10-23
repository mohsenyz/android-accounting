package com.mphj.accountry.models.db;


import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mphj on 10/20/2017.
 */

@Parcel(value = Parcel.Serialization.BEAN, analyze = {Log.class})
public class Log extends RealmObject {

    @PrimaryKey
    private int id;
    private String command;
    private String body;
    private boolean done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public static class Builder{
        String className;
        int id;
        String body;
        String methodName;
        public static Builder update(Class cl){
            Builder builder = new Builder();
            builder.className = cl.getSimpleName();
            builder.methodName = "update";
            return builder;
        }

        public static Builder create(Class cl){
            Builder builder = new Builder();
            builder.className = cl.getSimpleName();
            builder.methodName = "create";
            return builder;
        }

        public Builder id(int id){
            this.id = id;
            return this;
        }

        public Builder object(String json){
            body = json;
            return this;
        }

        public Log build(){
            Log log = new Log();
            log.setDone(false);
            log.setBody(body);
            log.setCommand(
                    new StringBuilder(methodName)
                            .append("$")
                            .append(className.toLowerCase())
                            .append("$")
                            .append(id).
                            toString()
            );
            return log;
        }
    }
}
