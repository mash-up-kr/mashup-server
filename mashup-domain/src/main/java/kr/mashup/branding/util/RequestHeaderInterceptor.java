package kr.mashup.branding.util;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestHeaderInterceptor implements ClientHttpRequestInterceptor {
    private final String headerName;
    private final String headerValue;

    @Override
    public ClientHttpResponse intercept(
        HttpRequest request,
        byte[] body,
        ClientHttpRequestExecution execution
    ) throws IOException {
        request.getHeaders().add(headerName, headerValue);
        return execution.execute(request, body);
    }
}
