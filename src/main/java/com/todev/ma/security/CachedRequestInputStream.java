package com.todev.ma.security;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;

class CachedRequestInputStream extends ServletInputStream {
    private final ByteArrayInputStream readableInputStream;

    CachedRequestInputStream(byte[] requestBody) {
        this.readableInputStream = new ByteArrayInputStream(requestBody);
    }

    @Override
    public boolean isFinished() {
        return readableInputStream.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        //No implementation needed
    }

    @Override
    public int read() {
        return readableInputStream.read();
    }
}
