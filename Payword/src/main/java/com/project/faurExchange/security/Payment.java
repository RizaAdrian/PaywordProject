package com.project.faurExchange.security;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Payment {

    private byte[] bytes;

    public Payment(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Pay getPayword() {
        Pay pay = new Pay();
        pay.setValue(bytes);
        return pay;
    }

    public int getPaywordNo() {
        byte[] paywordNoBytes = new byte[4];

        for (int i = 20; i < 24; ++i)
            paywordNoBytes[i - 20] = this.bytes[i];

        return ByteBuffer.wrap(paywordNoBytes).getInt();
    }

    public int getPaywordValue() {
        byte[] paywordValue = new byte[4];

        for (int i = 20; i < 24; ++i)
            paywordValue[i - 20] = this.bytes[i];

        return ByteBuffer.wrap(paywordValue).getInt();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return Arrays.equals(bytes, payment.bytes);

    }

    @Override
    public int hashCode() {
        return bytes != null ? Arrays.hashCode(bytes) : 0;
    }

}
