package laba5.shared.actions;

import java.io.Serializable;

public class Response implements Serializable {
    private final String response;

    public Response(String response) {
        this.response = response;
    }


    public String getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return "Response[" + response+ "]";
    }
}
