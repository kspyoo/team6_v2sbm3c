package dev.mvc.tool;

import dev.mvc.openapi.OpenAPIDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class OpenAPI {

    /***
     * 공공데이터 데이터 요청
     * @param page 페이지번호
     * @param perPage 가져올 데이터 개수
     * @return
     */
    public static List<OpenAPIDTO> getData(int page, int perPage) {
        String apiurl= "https://api.odcloud.kr/api/15111389/v1/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc";
        String serviceKey = "4l2%2Bo5W6oxOLALy1WKLtPeknvt6D%2BsOla12UgtFc14qWUMI8Uonl20wzMsovpBQWj0QOXRnx1AiFbCNlviUk7g%3D%3D";

        /*
         * url 조합
         */
        StringBuilder urlBuilder = new StringBuilder(apiurl);
        urlBuilder.append("?serviceKey="+serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("page", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(page+"", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("perPage", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(perPage+"", StandardCharsets.UTF_8));

        /*
         * GET방식으로 전송해서 파라미터 받아오기
         */
        BufferedReader rd;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            System.out.println("Response code: " + conn.getResponseCode());
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            String line;
            /**
             * 한줄씩 문자열로 재조합
             */
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
        }catch (IOException e){
            System.out.print(e);
        }
        /**
         * 결과 JSON 객체로 변환 -> 키가 "data"인 값(JSON 배열) 추출후 문자로 형변환
         */
        ObjectMapper mapper = new ObjectMapper();
        JSONObject result = new JSONObject(sb.toString());
        String data = result.getJSONArray("data").toString();

        /**
         * data라는 이름의 JSON 배열안의 키를 DTO객체의 변수명끼리 매핑 시켜 저장함
         */
        List<OpenAPIDTO> list = null;
        try {
            list = Arrays.asList(mapper.readValue(data, OpenAPIDTO[].class));
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }

        System.out.println(list.get(0));

        return list;
    }
}
