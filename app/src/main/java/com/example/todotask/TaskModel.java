package com.example.todotask;

import java.io.Serializable;

public class TaskModel implements Serializable {
    String name ,dic;
    int taskKey;
    long currentTimeStamp;
    public TaskModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDic() {
        return dic;
    }

    public void setDic(String dic) {
        this.dic = dic;
    }

    public int getTaskKey() {
        return taskKey;
    }

    public void setTaskKey(int taskKey) {
        this.taskKey = taskKey;
    }

    public long getCurrentTimeStamp() {
        return currentTimeStamp;
    }

    public void setCurrentTimeStamp(long currentTimeStamp) {
        this.currentTimeStamp = currentTimeStamp;
    }

    public TaskModel(String name, String dic) {
        this.name = name;
        this.dic = dic;
    }
}
