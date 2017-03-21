package social_network_authentication;

import authentication.PasswordCreator;
import authentication.SimplePasswordCreator;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import registration.RegistrationBean;

@Stateless
@LocalBean
@ManagedBean
public class GoogleAuthorization extends SocialNetworkAuthorization implements Serializable {

    private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private GoogleAuthorizationCodeFlow flow;
    private final int PASSWORD_LENGTH = 20;

   
    
    @EJB
    RegistrationBean regBean;

    public GoogleAuthorization() {
        clientId = "917212436277-7f9l5b305t35ajrcvi1vgj8kojjk6102.apps.googleusercontent.com";
        clientSecret = "7AXawWJ-60S2zVBbM5JJHruL ";
       // redirectUri = "https://localhost:8443/VNFManagerApp-web/faces/authSocial/authGoogle.xhtml";
        redirectUri = "http://185.5.251.73:28080/Monitoring-web/faces/authSocial/authGoogle.xhtml";
        userInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo";
        isError = false;
    }

    @Override
    public String fetchPersonalInfo(String authCode) throws IOException {

        flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientId, clientSecret, SCOPE).build();
        final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(redirectUri).execute();
        final Credential credential = flow.createAndStoreCredential(response, null);
        final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
        // Make an authenticated request
        final GenericUrl url = new GenericUrl(userInfoUrl);
        final HttpRequest request = requestFactory.buildGetRequest(url);
        request.getHeaders().setContentType("application/json");
        final String jsonIdentity = request.execute().parseAsString();
        return jsonIdentity;
    }
    
    @Override
    public HashMap<String,String> getUser(String authCode) throws IOException, ParseException {
        String startJson = fetchPersonalInfo(authCode);
        if (!startJson.equals("")) {
            JSONParser jsonParser = new JSONParser();
            JSONObject json = (JSONObject) jsonParser.parse(startJson);
            String id = getJsonValue(json, "id");
            String nickname = "Google" + id;
            String email = getJsonValue(json, "email");
            String firstName = getJsonValue(json, "given_name");
            String lastName = getJsonValue(json, "family_name");
            PasswordCreator passwordCreator = new SimplePasswordCreator();
            String password = passwordCreator.createPassword(PASSWORD_LENGTH);

            HashMap<String,String> resultMap = new HashMap();
            resultMap.put("nickname", nickname);
            resultMap.put("email", email);
            resultMap.put("firstName", firstName);
            resultMap.put("lastName", lastName);
            resultMap.put("password", password);
            
            regBean.addSocialUser(nickname, password, firstName, lastName, email);
           // loginInApp(nickname, password,context,request);
           return resultMap;
        }
        return null;
    }


    public String authorize() {
        StringBuilder sb = new StringBuilder();
        sb.append("https://accounts.google.com/o/oauth2/auth?redirect_uri=");
        sb.append(redirectUri);
        sb.append("&response_type=code&client_id=");
        sb.append(clientId);
        sb.append("&scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile");
        return sb.toString();
    }
}