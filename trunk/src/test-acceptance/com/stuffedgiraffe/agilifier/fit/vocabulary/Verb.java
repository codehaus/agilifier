package com.stuffedgiraffe.agilifier.fit.vocabulary;

public abstract class Verb {
    protected String arg1;
    protected String arg2;
    protected String arg3;
    protected String info = "";
    protected String result = "OK";

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }

    public abstract void execute() throws Exception;

    public String info() {
        return info;
    }

    public String result() {
        return result;
    }

}
