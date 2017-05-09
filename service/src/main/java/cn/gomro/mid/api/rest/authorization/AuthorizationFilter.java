/*
package cn.gomro.mid.api.rest.authorization;

import cn.gomro.mid.core.common.utils.TokenUtils;
import org.apache.commons.lang.StringUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;

*/
/**
 * Created by Adam on 2017/4/12.
 *//*

public class AuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        //获取客户端Header中提交的token
        String token = requestContext.getHeaderString("Authorization");
        if (StringUtils.isEmpty(token)) {
            // TODO:拦截响应
        }
        //判断该用户是否已登陆
        User user = TokenUtils.sign(token);
        if (user == null) {
            // TODO:拦截响应
        }
    }

}
*/
