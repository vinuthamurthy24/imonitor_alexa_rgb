package in.imonitorapi.mobile.login;

public class OAuth2Exception extends Exception {

    protected OAuth2Exception() {
    }

    public OAuth2Exception(int statusCode, String message) {
        super(statusCode + ":" + message);
    }

}
