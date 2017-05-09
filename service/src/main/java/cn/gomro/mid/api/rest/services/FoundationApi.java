package cn.gomro.mid.api.rest.services;

import cn.gomro.mid.api.rest.RestMediaType;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.service.sms.GomroSmsMessageTemplate;
import cn.gomro.mid.core.service.sms.SmsMessage;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by momo on 2016/5/31.
 */
@Path("/foundation")
@Produces(RestMediaType.JSON_HEADER)
@Consumes(RestMediaType.JSON_HEADER)
public class FoundationApi {

    private final static Logger LOGGER = Logger.getLogger(FoundationApi.class.toString());

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/jms/queue/gomroSmsQueue")
    private Queue queueSms;

    @GET
    @Path("/sms/send/{mobile}/{code}/{expire}")
    public ReturnMessage smsSend(@PathParam("mobile") String mobile,
                                 @PathParam("code") String code,
                                 @PathParam("expire") String expire) {

        String msg = GomroSmsMessageTemplate.registerValidateCode(code, expire);

        final Destination destination = queueSms;

        try {
            SmsMessage sms = new SmsMessage(mobile, msg);
            context.createProducer().send(destination, context.createObjectMessage(sms));
            LOGGER.info("Send sms message to queue, Mobile:" + sms.getMobile());

            return ReturnMessage.success();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return ReturnMessage.failed();
    }

    @GET
    @Path("/sms/send/batch")
    public ReturnMessage batchNotice(@QueryParam("mobiles") String mobiles, @QueryParam("msg") String msg) {

        final Destination destination = queueSms;

        try {
            if(mobiles!=null && !"".equals(mobiles.trim()) && msg!=null && !"".equals(msg.trim())){
                msg = GomroSmsMessageTemplate.registerValidate(msg);
                String[] split = mobiles.split(",");
                HashSet<String> hString = new HashSet<>();
                for (String mobi:split) {
                    hString.add(mobi);
                }
                for (String mobi:hString) {

                    String reg="^(13|14|15|17|18)\\d{9}$";
                    Pattern pattern = Pattern.compile(reg);
                    Matcher matcher = pattern.matcher(mobi);
                    if(matcher.matches()){
                        SmsMessage smsMessage = new SmsMessage(mobi, msg);
                        context.createProducer().send(destination, context.createObjectMessage(smsMessage));
                        LOGGER.info("Send sms message to queue, Mobile:" + smsMessage.getMobile());
                    }
                }

            return ReturnMessage.success("success");
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        } finally {

        }
        return ReturnMessage.failed();
    }
}
