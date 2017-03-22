/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package social_network_authentication;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ArsenyPC
 */
public abstract class SocialNetworkAuthorization implements Serializable {

    protected String clientId;
    protected String clientSecret;
    protected String userUrl;
    protected String redirectUri;
    protected String userInfoUrl;
    protected boolean isError;
    private final int PASSWORD_LENGTH = 20;

    public abstract String fetchPersonalInfo(String userCode) throws IOException, ParseException;

    public abstract HashMap<String,String> getUser(String authCode) throws IOException, ParseException;


    public String getJsonValue(String json, String parameter) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        Object obj = jsonObject.get(parameter);
        if (obj == null) {
            return null;
        }
        String result = obj.toString();
        return result;
    }

    public String getJsonValue(JSONObject jsonObject, String parameter) throws ParseException {
        Object obj = jsonObject.get(parameter);
        if (obj == null) {
            return null;
        }
        String json = obj.toString();
        return json;
    }

}
