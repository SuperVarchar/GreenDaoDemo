package com.hnshituo.icore_map.okhttp.builder;


import com.hnshituo.icore_map.okhttp.request.PostFileRequest;
import com.hnshituo.icore_map.okhttp.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PostFormBuilder extends OkHttpRequestBuilder {
    private List<FileInput> files = new ArrayList<>();


    @Override
    public RequestCall build() {
        return new PostFileRequest.PostFormRequest(url, tag,objs,extras,headers, files,id).build();
    }

    public PostFormBuilder files(String key, Map<String, File> files) {
        for (String filename : files.keySet()) {
            this.files.add(new FileInput(key, filename, files.get(filename)));
        }
        return this;
    }

    public PostFormBuilder addFile(File file) {

        files.add(new FileInput("uploadfile", file.getName(), file));
        return this;
    }

    public static class FileInput {
        public String key;
        public String filename;
        public File file;

        public FileInput(String name, String filename, File file) {
            this.key = name;
            this.filename = filename;
            this.file = file;
        }

        @Override
        public String toString() {
            return "FileInput{" +
                    "key='" + key + '\'' +
                    ", filename='" + filename + '\'' +
                    ", file=" + file +
                    '}';
        }
    }
    //
    public PostFormBuilder url(String url) {
        this.url = url;
        return this;
    }

    public PostFormBuilder tag(Object tag) {
        this.tag = tag;
        return this;
    }


    public PostFormBuilder bodyObjects(Object[] objs) {
        this.objs = objs;
        return this;
    }
    public PostFormBuilder bodyExtras(String[] extras) {
        this.extras = extras;
        return this;
    }


//    public PostFormBuilder addParams(Map<String,String> params) {
//        this.params = params;
//        return this;
//    }

    public PostFormBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }


    public PostFormBuilder addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }


}
