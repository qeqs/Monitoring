/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social_network_authentication;

import authentication.PasswordCreator;
import authentication.SimplePasswordCreator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import registration.RegistrationBean;

@Stateless
@LocalBean
@ManagedBean
public class FacebookAuthorization extends SocialNetworkAuthorization implements Serializable {

    private static final String FACEBOOK_AUTHORIZATION_URL = "https://www.facebook.com/dialog/oauth";
    private static final String ACCESS_TOKEN_URL = "https://graph.facebook.com/oauth/access_token";
    private final int PASSWORD_LENGTH = 20;
    @EJB
    RegistrationBean regBean;

    public FacebookAuthorization() {
        clientId = "277534266015132";
        clientSecret = "8274e7a1ea7c271f98c857a026496fa4";
       // redirectUri = "https://localhost:8443/VNFManagerApp-web/faces/authSocial/authFacebook.xhtml";
        redirectUri = "https://185.5.251.73:28543/VNFManagerApp-web/faces/authSocial/authFacebook.xhtml";
        userInfoUrl = "https://graph.facebook.com/me";
        isError = false;
    }

    public String authorize() {
        String state = UUID.randomUUID().toString().replaceAll("-", "");
        String plainUrl = "https://www.facebook.com/dialog/oauth?client_id=" + clientId
                + "&scope=user_about_me,user_status&redirect_uri=" + redirectUri
                + "&state=" + state;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(plainUrl);
        } catch (IOException ex) {
            Logger.getLogger(FacebookAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String fetchPersonalInfo(String authCode) throws IOException, ParseException {
        String urlAccessToken = ACCESS_TOKEN_URL
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&code=" + authCode
                + "&redirect_uri=" + redirectUri;
        String response = getResponseString(urlAccessToken);
        try {
            if (getJsonValue(response, "error") != null) {
                isError = true;
            }
        } catch (ParseException e) {
        }
        if (!isError) {
            String accessToken = getAccessToken(response);
            //        String userId=getAccessToken();
            String urlUserInfo = userInfoUrl
                    + "?access_token=" + accessToken;
            String json = getResponseString(urlUserInfo);
            String personalFacebookId = getJsonValue(json, "id");
            String pictureUrl = "https://graph.facebook.com/" + personalFacebookId + "/picture?type=large";
            json = addPictureUrl(json, pictureUrl);
            return json;
        }
        return "";
    }

    private String getAccessToken(String response) {
        int first = response.indexOf("=") + 1;
        int last = response.indexOf("&expires");
        return response.substring(first, last);
    }

    private String getResponseString(String url) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        ClientParamsStack httpParams = (ClientParamsStack) response.getParams();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    private String addPictureUrl(String json, String pictureUrl) {
        JSONParser jsonParser = new JSONParser();
        String result = "";
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            jsonObject.put("picture", pictureUrl);
            result = jsonObject.toJSONString();
        } catch (ParseException ex) {
            Logger.getLogger(FacebookAuthorization.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public HashMap<String, String> getUser(String authCode) throws IOException, ParseException {
        String startJson = fetchPersonalInfo(authCode);
        if (!startJson.equals("")) {
            JSONParser jsonParser = new JSONParser();
            JSONObject json = (JSONObject) jsonParser.parse(startJson);
            String id = getJsonValue(json, "id");
            String nickname = "Facebook" + id;
            String name = getJsonValue(json, "name");
            String photo = getJsonValue(json, "picture");

            PasswordCreator passwordCreator = new SimplePasswordCreator();
            String password = passwordCreator.createPassword(PASSWORD_LENGTH);

            HashMap<String, String> resultMap = new HashMap();
            resultMap.put("nickname", nickname);
            resultMap.put("email", "unavailable");
            resultMap.put("firstName", name);
            resultMap.put("lastName", "NONE");
            resultMap.put("password", password);

            regBean.addSocialUser(nickname, password, name, "NONE", "unavailable");
            return resultMap;

        }
        return null;
    }

}