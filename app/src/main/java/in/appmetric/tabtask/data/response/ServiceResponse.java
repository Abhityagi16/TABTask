package in.appmetric.tabtask.data.response;

import in.appmetric.tabtask.data.response.Data;

/**
 * Created by abhishektyagi on 18/08/17.
 */

public class ServiceResponse<T> {
    private int code;
    private String status;
    private Data<T> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Data<T> getData() {
        return data;
    }

    public void setData(Data<T> data) {
        this.data = data;
    }
}
