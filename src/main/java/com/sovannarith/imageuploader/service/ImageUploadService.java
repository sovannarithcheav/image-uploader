package com.sovannarith.imageuploader.service;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.apache.http.entity.mime.MIME.UTF8_CHARSET;

@Service
public class ImageUploadService {
    private static String CLIENT_ID = "Client-ID 4ed857da2507eef";
    private static String UPLOAD_URL = "https://api.imgur.com/3/image";

    public String upload(MultipartFile multipart) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        HttpPost post = new HttpPost(UPLOAD_URL);
        FileBody fileBody = new FileBody(convFile, org.apache.http.entity.ContentType.DEFAULT_BINARY);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("image", fileBody);
        HttpEntity entity = builder.build();
//
        Header header = new BasicHeader("Authorization", CLIENT_ID);
        post.setEntity(entity);
        post.setHeader(header);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(post);
        String responseJSON = EntityUtils.toString(response.getEntity(), UTF8_CHARSET);
        convFile.delete();
        return new JSONObject(responseJSON).getJSONObject("data").getString("link");
    }


}
