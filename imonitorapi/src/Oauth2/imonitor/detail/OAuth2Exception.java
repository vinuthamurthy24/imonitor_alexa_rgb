package Oauth2.imonitor.detail;

public class OAuth2Exception extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 884765512979646640L;

	protected OAuth2Exception() {
    }

    public OAuth2Exception(int statusCode, String message) {
        super(statusCode + ":" + message);
    }

}
