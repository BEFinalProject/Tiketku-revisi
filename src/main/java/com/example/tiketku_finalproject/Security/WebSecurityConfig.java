package com.example.tiketku_finalproject.Security;

import com.example.tiketku_finalproject.Filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig{

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("diva").password("juan").roles("ADMIN").build();
//        UserDetails user = User.withUsername("j").password("j").roles("USER").build();
//        return new InMemoryUserDetailsManager(admin,user);
        //atas ini kalo secara manual, yang bawah secara Database
        return new UserInfoUserDetailsService();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/Film","/Jadwal","/Seats","/Studio",
//                        "/Film/Judul-Film/{film_name}","/Film/Tayang","/Film/Jadwal/{film_name}","/Seats/Studios/{studio}/{nomor_kursi}","/swagger-ui/**","/v3/api-docs/**","/Auth/**","/Kafka/**","/Kafka/Post").permitAll()
////                .anyRequest().authenticated().and().httpBasic() formLogin()
//                .and().authorizeHttpRequests().requestMatchers("/Users/**","/Film/**","/Jadwal/**","/Report/**","/Transaction/**","/DataTransaction/**").authenticated().and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationProvider(authenticationProvider())
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
        return httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/Airplane/**","/Airport/**","/Schedules/**","/TempTransacation/**","/HistoryTransaction/**","/Users/**","/swagger-ui/**","/v3/api-docs/**").permitAll()
//                .anyRequest().authenticated().and().httpBasic() formLogin()
                .and().authorizeHttpRequests().requestMatchers("/City/findCityTicket","/Film/**","/Jadwal/**","/Report/**","/Transaction/**","/DataTransaction/**").authenticated().and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    //untuk encode password
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
