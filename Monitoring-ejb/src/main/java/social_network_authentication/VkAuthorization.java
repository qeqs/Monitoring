package social_network_authentication;


import authentication.PasswordCreator;
import authentication.SimplePasswordCreator;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.ClientParamsStack;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import registration.RegistrationBean;

@Stateless
@LocalBean
@ManagedBean
public class VkAuthorization extends SocialNetworkAuthorization implements Serializable {

    private final int PASSWORD_LENGTH = 20;
    private static final String VK_AUTHORIZATION_URL = "https://oauth.vk.com/authorize";
    private static final String ACCESS_TOKEN_URL = "https://oauth.vk.com/access_token";

    @EJB
    RegistrationBean regBean;

    public VkAuthorization() {
        clientId = "5933225";
        clientSecret = "mD4sT0rbqE6FxPDmqKTK";
       // redirectUri = "https://localhost:8443/VNFManagerApp-web/faces/authSocial/authVk.xhtml";
        redirectUri = "http://185.5.251.73:28080/Monitoring-web/faces/authSocial/authVk.xhtml";
        userInfoUrl = "https://api.vk.com/method/users.get";
        isError = false;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public String authorize() {
        String plainUrl = "http://oauth.vk.com/authorize?"
                + "client_id=" + clientId
                + "&scope=notify,status"
                + "&redirect_uri=" + redirectUri
                + "&response_type=code";
        return plainUrl;
    }

    @Override
    public String fetchPersonalInfo(String authCode) throws IOException, ParseException {
        String urlAccessToken = ACCESS_TOKEN_URL
                + "?client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&code=" + authCode
                + "&redirect_uri=" + redirectUri;
        String json = getResponseJson(urlAccessToken);
        if (getJsonValue(json, "error") != null) {
            isError = true;
        } else {
            String accessToken = getJsonValue(json, "access_token");
            String userId = getJsonValue(json, "user_id");
            String urlUserInfo = userInfoUrl
                    + "?uids=" + userId
                    + "&fields=uid,first_name,last_name,nickname,screen_name,sex,bdate,city,country,timezone,photo_max"
                    + "&access_token=" + accessToken;
            json = getResponseJson(urlUserInfo);
            return json;
        }
        return "";
    }

    private String getResponseJson(String url) throws IOException {
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

    @Override
    public HashMap<String, String> getUser(String authCode) throws IOException, ParseException {
        String startJson = fetchPersonalInfo(authCode);
        if (!startJson.equals("")) {

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(startJson);
            JSONArray jsonArray = (JSONArray) jsonObject.get("response");
            JSONObject json = (JSONObject) jsonArray.get(0);
            String id = getJsonValue(json, "uid");
            //        return id;
            String nickname = "VK" + id;
            String firstName = getJsonValue(json, "first_name");
            String lastName = getJsonValue(json, "last_name");
            String name = firstName + " " + lastName;
            String photo = getJsonValue(json, "photo_max");

            PasswordCreator passwordCreator = new SimplePasswordCreator();
            String password = passwordCreator.createPassword(PASSWORD_LENGTH);

            HashMap<String, String> resultMap = new HashMap();
            resultMap.put("nickname", nickname);
            resultMap.put("email", "unavailable");
            resultMap.put("firstName", firstName);
            resultMap.put("lastName", lastName);
            resultMap.put("password", password);

            regBean.addSocialUser(nickname, password, firstName, lastName, "unavailable");  
            return resultMap;
        }

        return null;
    }
}