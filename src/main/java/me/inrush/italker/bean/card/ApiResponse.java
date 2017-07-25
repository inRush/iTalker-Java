package me.inrush.italker.bean.card;

import com.google.gson.annotations.Expose;

/**
 * @author inrush
 * @date 2017/7/25.
 * @package me.inrush.italker.bean.card
 */
public class ApiResponse {
    @Expose
    private int error;
    @Expose
    private String message;
    @Expose
    private Object data;

    public ApiResponse(int error, String message, Object data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
