package de.dicos.springboot.repairservice.restful.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration for the Verwaltungssystem rest calls
 */
@Configuration
public class RestTemplateConfig {
	
    @Value("${verwaltung.rest.connect-timeout-millis:1000}")
    private int connectTimeoutMillis;

    @Value("${verwaltung.rest.read-timeout-millis:3000}")
    private int readTimeoutMillis;
    
    /**
     * Configures a rest-template for the Verwaltungssystem
     *
	 * @return RestTemplate
	 */
    @Bean(name = "verwaltungRT")
    public RestTemplate verwaltungRestTemplate() {
    	var builder = new RestTemplateBuilder();
		var restTemplate = builder.requestFactory(
				requestFactory((int) TimeUnit.MILLISECONDS.toSeconds(connectTimeoutMillis),
						(int) TimeUnit.MILLISECONDS.toSeconds(readTimeoutMillis)))
				.build();
		
		return restTemplate;
    }
    
	/**
	 * Erstellt eine RequestFactory für den RestTemplateBuilder mit folgenden
	 * Eigenschaften:
	 * <li>read timeout für die Connection</li>
	 * <li>connect timeout für die Connection</li>
	 * <li>connection request timeout für die Connection</li>
	 * <li>Berücksichtigung von Systemparametern wie z.B. http_proxy, http_nonproxy etc. </li>
	 * <li>Cookie specification wird auf Standard (The RFC 6265 compliant policy) gesetzt</li>
	 * 
	 * @param connectTimeout Connect timeout in seconds
	 * @param readTimeout    Read timeout in seconds
	 * @return eine RequestFactory vom Typ HttpComponentsClientHttpRequestFactory
	 *         mit einem PoolingHttpClientConnectionManager
	 */
    private Supplier<ClientHttpRequestFactory> requestFactory(int connectTimeout, int readTimeout) {
        var requestConfig = RequestConfig.custom() //
                .setCookieSpec(CookieSpecs.STANDARD) //
                .setSocketTimeout(readTimeout * 1000)//
                .setConnectTimeout(connectTimeout * 1000).build();

        return createClientFactory(requestConfig);
    }
    
    private Supplier<ClientHttpRequestFactory> createClientFactory(RequestConfig requestConfig) {
        var httpClient = HttpClientBuilder.create()//
        		.useSystemProperties()
                .setConnectionManager(new PoolingHttpClientConnectionManager()) //
                .setDefaultRequestConfig(requestConfig).build();
        return () -> new HttpComponentsClientHttpRequestFactory(httpClient);
    }

}
