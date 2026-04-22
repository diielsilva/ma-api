package com.todev.ma.security;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

class CacheableHttpServletRequest extends HttpServletRequestWrapper {
    private final byte[] cachedRequestBody;

    CacheableHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.cachedRequestBody = request.getInputStream().readAllBytes();
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedRequestInputStream(cachedRequestBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream body = new ByteArrayInputStream(cachedRequestBody);
        InputStreamReader byteReader = new InputStreamReader(body);

        return new BufferedReader(byteReader);
    }
}
