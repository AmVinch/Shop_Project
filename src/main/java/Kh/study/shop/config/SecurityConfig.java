package Kh.study.shop.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //컨피규레이션 객체 생성 어노테이션
@EnableWebSecurity //내부적으로 이 객체를 가져가 파일을 실행해주는 어노테이션
public class SecurityConfig {

	@Bean 
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception{
		security.csrf().disable()
//						.authorizeRequests()
//						.antMatchers("/member/login", "/board/list","/member/join").permitAll()
//						.anyRequest().authenticated()
//					.and()
						.formLogin()
						.loginPage("/member/login")
						.defaultSuccessUrl("/member/loginResult")
						.failureUrl("/member/loginResult")
						.loginProcessingUrl("/member/login")// 실제 로그인을 진행할  요청 정보
						// 아래 두개를 사용하면, 별도로 name값 username, password 값을 수정할 필요가 없다
						.usernameParameter("memberId")
						.passwordParameter("memberPw")
					.and()
						.logout()
						.invalidateHttpSession(true)
						.logoutSuccessUrl("/item/list")
					.and()
						.exceptionHandling()
						.accessDeniedPage("/member/accessDenied")
						;
		return security.build();
		
	}
	//------------------------- 스프링 암호화 -------------------------------//
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//------css,js 요청 인증 무시 -------------------------------------------------//
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/js/**",  "/css/**");
	}
}
