package com.hnshituo.icore_map.okhttp.body;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;

/**
 * Created by Administrator on 2016/6/20.
 */
public class RequetFormBody extends RequestBody {

    private Object[] mObjects;
    private String[] mExtras;
    private static final MediaType CONTENT_TYPE =
            MediaType.parse("application/json");

    @Override
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    public RequetFormBody(Object[] objects, String[] extras) {
        this.mExtras = extras;
        this.mObjects = objects;
    }

    @Override
    public long contentLength() {
        return writeOrCountBytes(null, true);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        writeOrCountBytes(sink, false);
    }

    private long writeOrCountBytes(BufferedSink sink, boolean countBytes) {
        long byteCount = 0L;

        Buffer buffer;
        if (countBytes) {
            buffer = new Buffer();
        } else {
            buffer = sink.buffer();
        }
        buffer.writeByte('[');
//        for (int i = 0, size = encodedNames.size(); i < size; i++) {
//            if (i > 0) buffer.writeByte('&');
//            buffer.writeUtf8(encodedNames.get(i));
//            buffer.writeByte('=');
//            buffer.writeUtf8(encodedValues.get(i));
//        }
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        if (mObjects != null) {

            for (int i = 0, size = mObjects.length; i < size; i++) {
                String json = gson.toJson(mObjects[i]);
                buffer.writeUtf8(json);
                //如果不是最后一个就需要添加逗号
                if (i != mObjects.length - 1) {
                    buffer.writeByte(',');
                }
            }
        }
        if (mExtras != null) {
            for (int i = 0, size = mExtras.length; i < size; i++) {
                //如果对象的集合为null,并且时第一个就不需要加","
                if (!(mObjects == null && i == 0)) {
                    buffer.writeByte(',');
                }
                buffer.writeByte('\"');
                if (mExtras[i] == null) {
                    buffer.writeUtf8("");
                } else {

                    buffer.writeUtf8(StringEscapeUtils.escapeJava(mExtras[i]));
                }
                buffer.writeByte('\"');
            }
        }
        buffer.writeByte(']');


        if (countBytes) {
            byteCount = buffer.size();
            buffer.clear();
        }

        return byteCount;
    }


}
