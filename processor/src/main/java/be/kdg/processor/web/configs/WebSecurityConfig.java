package be.kdg.processor.web.configs;

import be.kdg.processor.web.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers("/", "/api*","/usapi/createUser","/usapi/user/TestUsername").permitAll().antMatchers("/usapi/user/test").authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/usapi/user/test",true)
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
    @Bean
    public DaoAuthenticationProvider authProvider(UserService userService) {
        DaoAuthenticationProvider authProvider= new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        //authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;}
}