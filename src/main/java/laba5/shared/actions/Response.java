package laba5.shared.actions;

import java.io.Serializable;

public class Response implements Serializable {
    private final String response;
    private long currentId;

    public Response(String response, long currentId) {
        this.response = response;
        this.currentId = currentId;
    }
    public long getCurrentId() {
        return currentId;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "Response[" + response+ "]";
    }
}
