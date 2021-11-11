package com.portfolio.tracker.util;
import java.nio.charset.StandardCharsets;
import javax.crypto.Mac;

public class FtxDigest extends BaseParamsDigest {

    private FtxDigest(byte[] secretKey) {
        super(secretKey, HMAC_SHA_256);
    }

    public static FtxDigest createInstance(String secretKey) {

        if (secretKey != null) {
            return new FtxDigest(secretKey.getBytes());
        } else return null;
    }

    public String digestParams(String key) throws Exception {
        Mac mac256 = getMac();
        try {
            mac256.update(key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new Exception("Digest encoding exception", e);
        }
        return DigestUtils.bytesToHex(mac256.doFinal()).toLowerCase();
    }
}
