package com.twrental.twrent;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;


@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter
{

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    @Override
    @ConditionalOnMissingBean(HttpSessionManager.class)
    protected HttpSessionManager httpSessionManager(){
        return new HttpSessionManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        super.configure(http);
        http
                .authorizeRequests()
                .antMatchers("/api/v1/addcar").hasRole("Admin")
                .antMatchers("/api/v1/deletecar").hasRole("Admin")
                .antMatchers("/api/v1/updatecar").hasRole("Admin")
                .antMatchers("/api/v1/customers").hasRole("Admin")
                .antMatchers("/api/v1/cars").hasAnyRole("Admin","User")
                .antMatchers("/api/v1/ordercar").hasAnyRole("Admin","User")
                .antMatchers("/api/v1/updateorder").hasAnyRole("Admin","User")
                .antMatchers("/api/v1/myorders").hasAnyRole("Admin","User")
                .anyRequest().authenticated();
        http.csrf().disable();
    }
}
