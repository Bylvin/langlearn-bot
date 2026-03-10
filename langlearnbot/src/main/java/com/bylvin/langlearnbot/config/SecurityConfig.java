package com.bylvin.langlearnbot.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		http
		// 1. Aktifkan CORS dengan konfigurasi yang kita buat di bawah
		.cors(Customizer.withDefaults())

		// 2. Disable CSRF jika Anda membangun REST API (biasanya menggunakan Token/JWT)
		.csrf(csrf -> csrf.disable())

		// 3. Atur otorisasi request
		.authorizeHttpRequests(auth -> auth
				// .requestMatchers("/api/public/**").permitAll() // Endpoint publik
				// .anyRequest().authenticated()                  // Sisanya harus login
				.anyRequest().permitAll()
				)

		// 3. Atur otorisasi logout
		.logout(LogoutConfigurer::permitAll);

		return http.build();
	}

	@Bean
	WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
				.allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
				.allowedHeaders("*")
				.allowedOrigins("*");
			}
		};
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		// Atur domain yang diizinkan (ganti dengan URL frontend Anda)
		configuration.setAllowedOrigins(List.of("/**"));

		// Atur HTTP Method yang diizinkan
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		// Atur Header yang diizinkan
		configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));

		// Izinkan pengiriman kredensial (seperti Cookie atau Auth Header)
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		// Terapkan konfigurasi ini ke semua path (/**)
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
