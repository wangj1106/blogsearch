package com.wangj.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

@RestController

public class SuggestController {
    @RequestMapping("/suggest")
    @ResponseBody
    public JSONArray suggest(@RequestParam(value="s",required=false) String keyword) throws IOException {
        RestClient restClient = RestClient.builder(
                new HttpHost("127.0.0.1", 9200, "http")
        ).build();
        System.out.println(keyword);
        HttpEntity entity = new NStringEntity("{\n" +
                "  \"my-suggest\": {\n" +
                "    \"prefix\": \"" + keyword + "\",\n" +
                "    \"completion\":{\n" +
                "       \"field\": \"suggest\",\"fuzzy\":{\n" +
                "           \"fuzziness\":2\n" +
                "       },\n" +
                "       \"size\":10\n" +
                "     }\n" +
                "  }\n" +
                "}", ContentType.APPLICATION_JSON);

        Response response = restClient.performRequest("POST", "/jobbole/_suggest?pretty", Collections.<String, String>emptyMap(), entity);
        String temp = EntityUtils.toString(response.getEntity());
        JSONArray optionsArray = JSONObject.fromObject(temp).getJSONArray("my-suggest").getJSONObject(0).getJSONArray("options");

        JSONArray result = new JSONArray();
        for (int i = 0; i < optionsArray.size(); i++){
            //System.out.println(optionsArray.getJSONObject(i).getJSONObject("_source").getString("title"));
            result.add(optionsArray.getJSONObject(i).getJSONObject("_source").getString("title"));
        }

        return result;
    }
}
