package cn.gomro.mid.api.rest;

import javax.ws.rs.core.MediaType;

/**
 * Created by momo on 2016/5/11.
 */
public class RestMediaType {

    //public static final String JSON_HEADER = MediaType.APPLICATION_JSON + "; " + MediaType.CHARSET_PARAMETER + "=UTF-8";
    public static final String JSON_HEADER = MediaType.APPLICATION_JSON + ";charset=UTF-8";
    public static final String FORM_HEADER = MediaType.APPLICATION_FORM_URLENCODED + ";charset=UTF-8";
    public static final String MULTIPART_FORM_DATA = MediaType.MULTIPART_FORM_DATA + ";charset=UTF-8";
}
