package cn.hsf.hsfmanager.pojo;

/**
 * @author kaituozhe
 */
public class ResultData {

    private boolean flag;

    private String errorMsg;

    public ResultData() {
    }

    public ResultData(boolean flag) {
        this.flag = flag;
    }

    public ResultData(boolean flag, String errorMsg) {
        this.flag = flag;
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "flag=" + flag +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
