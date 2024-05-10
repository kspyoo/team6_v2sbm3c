package dev.mvc.openapi;

import dev.mvc.tool.OpenAPI;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/openapi")
public class OpenAPIController {

//    @GetMapping("/getdata")
//    @ResponseBody
//    public String getdata() throws URISyntaxException, MalformedURLException {
//        RestTemplate restTemplate = new RestTemplate();
//
//        UriComponents url = UriComponentsBuilder.fromHttpUrl("https://api.odcloud.kr/api/15111389/v1/uddi:41944402-8249-4e45-9e9d-a52d0a7db1cc")
//                .queryParam("serviceKey", "4l2%2Bo5W6oxOLALy1WKLtPeknvt6D%2BsOla12UgtFc14qWUMI8Uonl20wzMsovpBQWj0QOXRnx1AiFbCNlviUk7g%3D%3D")
//                .queryParam("page", "1")
//                .queryParam("perPage", "1")
//                .build();
//
//        System.out.println(url.toString());
//
//        ResponseEntity<String> responseBody = restTemplate.getForEntity(url.toUriString(), String.class);
//        return responseBody.toString();
//    }

    /***
     * API 작동 확인용, 추후에 DB삽입 로직은 여기에 작성 하면됨
     * @return
     */
    @GetMapping("/getdata")
    @ResponseBody
    public String getdata() {
        List<OpenAPIDTO> list = OpenAPI.getData(1, 3000); // 3000개까지는 서버 에러 없이 출력 가능

        return list.size() + "개" + list.toString();
    }

}
