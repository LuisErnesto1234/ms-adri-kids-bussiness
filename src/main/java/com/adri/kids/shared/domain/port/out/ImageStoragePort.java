package com.adri.kids.shared.domain.port.out;

public interface ImageStoragePort {

    String upload(byte[] bytes, String key, String contentType);

    void delete(String key);
}
