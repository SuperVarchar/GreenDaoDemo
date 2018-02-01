package com.hnshituo.icore_map.okhttp.request;


import com.hnshituo.icore_map.okhttp.OkHttpUtils;
import com.hnshituo.icore_map.okhttp.body.RequetFormBody;
import com.hnshituo.icore_map.okhttp.builder.PostFormBuilder;
import com.hnshituo.icore_map.okhttp.callback.Callback;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;


public class PostFormRequest extends OkHttpRequest {
    private List<PostFormBuilder.FileInput> files;

    protected String[] extras;
    protected Object[] objects;
    public PostFormRequest(String url, Object tag, Object[] objects, String[] extras, Map<String, String> headers, List<PostFormBuilder.FileInput> files, int id) {
        super(url, tag, null, headers,id);
        this.objects = objects;
        this.extras = extras;
        this.files = files;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (files == null || files.isEmpty()) {
//            FormBody.Builder builder = new FormBody.Builder();
//            addParams(builder);
            RequetFormBody body = new RequetFormBody(objects,extras);
            return body;
        } else {
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
//            addParams(builder);
            for (int i = 0; i < files.size(); i++) {
                PostFormBuilder.FileInput fileInput = files.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
            }
            return builder.build();
        }
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {

                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten * 1.0f / contentLength,contentLength,0);
                    }
                });

            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

//    private void addParams(MultipartBody.Builder builder) {
//        if (params != null && !params.isEmpty()) {
//            for (String key : params.keySet()) {
//                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
//                        RequestBody.create(null, params.get(key)));
//            }
//        }
//    }



}
