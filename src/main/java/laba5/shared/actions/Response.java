package laba5.shared.actions;

import java.io.Serializable;

public class Response implements Serializable {
    private final String response;
    private long currentId;
    private String requestID;
    public Response(String response, long currentId) {
        this.response = response;
        this.currentId = currentId;
        this.requestID = requestID;
    }
    public long getCurrentId() {
        return currentId;
    }

    public String getResponse() {
        return response;
    }

    public String getRequestID() {
        return requestID;
    }

    @Override
    public String toString() {
        return "Response[" + response+ "]";
    }
}
