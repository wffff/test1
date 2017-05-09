package cn.gomro.mid.core.service.sms;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;

/**
 * Created by momo on 2016/5/31.
 */
public class LuosimaoSmsAdapter implements SmsAdapter {

    private static final String API_KEY = "38844731277c325027646ca5e41a7f02";
    private static final String REST_URL = "http://sms-api.luosimao.com/v1/send.json";

    @Override
    public boolean send(String mobile, String msg) {

        try {
            String httpResponse = sendSms(mobile, msg);
            JSONObject json = new JSONObject(httpResponse);
            int error_code = json.getInt("error");
            String error_msg = json.getString("msg");
            if (error_code == 0) {
                return true;
            }
        } catch (JSONException e) {
        }

        return false;
    }

    private String sendSms(String mobile, String msg) {

//        Client client = ClientBuilder.newClient();
//        String result = client.target(REST_URL).request().post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE), String.class);
//        return result;

        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", API_KEY));
        WebResource webResource = client.resource(REST_URL);
        MultivaluedMapImpl data = new MultivaluedMapImpl();
        data.add("mobile", mobile);
        data.add("message", msg);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, data);
        String result = response.getEntity(String.class);
        int status = response.getStatus();

        return result;
    }
}