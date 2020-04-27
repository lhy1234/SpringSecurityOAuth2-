package com.farinfo.auth.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * 认证服务器配置类
 * Created by: 李浩洋 on 2020-04-21
 **/
@Configuration
@EnableAuthorizationServer //当前应用是一个认证服务器
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private AuthenticationManager authenticationManager;




    //tokenStore是进行存取token的接口，默认内存的实现还有redis，jdbc，jwt的实现
    @Bean
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtTokenEnhancer());//jwt存取token
    }
    //Token增强，添加额外参数
    @Bean
    public CustomTokenEnhancer customTokenEnhancer(){
        return new CustomTokenEnhancer();
    }


    //  这里必须声明为 public的@Bean 才能把拿jwt解析的key的服务暴露出去，否则资源服务器会报异常
    @Bean
    public JwtAccessTokenConverter jwtTokenEnhancer() {
        /**
         * 对jwt进行签名的key，jwt是明文，签名防篡改。
         * 接收token的人需要用同样的key验签名，需要把这个key通过服务暴漏出去，使用服务的人才能拿到key
         */
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("lihaoyang");

        return converter;
    }



    /**
     * 1，配置客户端应用的信息，让认证服务器知道有哪些客户端应用来申请令牌。
     *
     * ClientDetailsServiceConfigurer：客户端的详情服务的配置
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //从数据库里读取客户端应用配置信息，需要一个数据源
        // spring会自动去  oauth_client_details 表里读取客户端信息
        clients.jdbc(dataSource);
    }


    /**
     *,2，配置用户信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        // 将增强的token设置到增强链中
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), jwtTokenEnhancer()));


        //传给他一个authenticationManager用来校验传过来的用户信息是不是合法的,注进来一个，自己实现
        endpoints
                //这里指定userDetailsService是专门给refresh_token用的，其他的四种授权模式不需要这个
                .userDetailsService(userDetailsService)
                .tokenStore(tokenStore()) //告诉服务器要用自定义的tokenStore里去存取token
                .tokenEnhancer(enhancerChain)//token增强
                .authenticationManager(authenticationManager)

                ;
    }


    /**
     * 3，配置资源服务器过来验token 的规则
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {

        security
                /**
                 * 暴漏验token的服务，过来验令牌有效性的请求，不是谁都能验的，只有经过身份认证的请求才能调。
                 * 所谓身份认证就是，必须携带clientId，clientSecret，否则随便一请求过来验token是不验的
                 */
                .checkTokenAccess("isAuthenticated()")
                /**
                 * 暴漏获取jwt签名key的服务，只有经过身份认证的请求才能调（同上）
                 * 上边 tokenStore里的signKey
                 */
                .tokenKeyAccess("isAuthenticated()");
    }
}
