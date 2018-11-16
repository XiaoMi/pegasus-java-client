package com.xiaomi.infra.pegasus.tools;

import com.github.luben.zstd.Zstd;
import com.github.luben.zstd.ZstdInputStream;
import com.xiaomi.infra.pegasus.client.PException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ZStdWrapper wraps the compress/decompress APIs of ZStd algorithm.
 * Since it holds an internal buffer, reuse this object whenever possible
 * to reduce memory allocation.
 * <p>
 * This implementation is NOT threadsafe.
 */
public class ZStdWrapper {

    public ZStdWrapper() {
    }

    /**
     * compress the `src` and return the compressed.
     */
    public byte[] compress(byte[] src) {
        return Zstd.compress(src);
    }

    /**
     * decompress the `src` and return the original.
     */
    public byte[] decompress(byte[] src) throws PException {
        // decompress in streaming way to avoid allocating a large buffer.

        byte[] ret;
        try {
            ZstdInputStream decompress = new ZstdInputStream(new ByteArrayInputStream(src));
            while (true) {
                int n = decompress.read(inBuf);
                if (n <= 0) {
                    break;
                }
                decompressOutBuf.write(inBuf, 0, n);
            }
            ret = decompressOutBuf.toByteArray();
            decompressOutBuf.reset();
        } catch (IOException e) {
            throw new PException("decompression failure: " + e.getMessage());
        }
        return ret;
    }

    // reuse the buffer for each decompression
    private byte[] inBuf = new byte[1024];
    private ByteArrayOutputStream decompressOutBuf = new ByteArrayOutputStream();
}
