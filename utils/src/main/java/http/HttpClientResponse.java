package http;

/**
 * HttpClient响应，包含http响应码和内容
 * @author peter
 * @Date Jun 21, 2018 5:28:27 PM
 */
public class HttpClientResponse {

    private int    responseCode;

    private Object responseBody;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public Object getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(Object responseBody) {
        this.responseBody = responseBody;
    }

}
