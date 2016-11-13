package tech.paypal.app.ngo.vexcel.network.config;

import retrofit.RequestInterceptor;
import tech.paypal.app.ngo.vexcel.model.login.Login;

/**
 * Created by chokkar
 */

public class ApiRequestInterceptor implements RequestInterceptor {
    private Login user;
    @Override
    public void intercept(RequestFacade request) {
        if (user != null) {
            final String authorizationValue = encodeCredentialsForBasicAuthorization();
            request.addHeader("Authorization", authorizationValue);
        }
    }
    private String encodeCredentialsForBasicAuthorization() {
        final String userAndPassword = user.getUsername() + ":" + user.getPassword();
        return "Basic " + Base64.encodeToString(userAndPassword.getBytes(), Base64.NO_WRAP);
    }

    public Login getUser() {
        return user;
    }

    public void setUser(Login user) {
        this.user = user;
    }
}
