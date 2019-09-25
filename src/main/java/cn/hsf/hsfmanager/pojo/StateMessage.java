package cn.hsf.hsfmanager.pojo;

public class StateMessage {
    private String message;
    private String errCode;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public StateMessage(String message) {
        this.message = message;
    }

    public StateMessage() {
    }
}
