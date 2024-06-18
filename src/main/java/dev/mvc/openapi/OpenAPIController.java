package dev.mvc.openapi;

import dev.mvc.culturefacility.CulturefacilityProcInter;
import dev.mvc.culturefacility.CulturefacilityVO;
import dev.mvc.tool.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/openapi")
public class OpenAPIController {

    @Autowired
    @Qualifier("dev.mvc.culturefacility.CulturefacilityProc")
    private CulturefacilityProcInter culturefacilityProc;

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
    public String getdata() throws InterruptedException {
        List<OpenAPIDTO> list = new ArrayList<OpenAPIDTO>();
        List<OpenAPIDTO> list_all = new ArrayList<OpenAPIDTO>();

        list = OpenAPI.getData(1, 500);
        CulturefacilityVO culturefacilityVO = new CulturefacilityVO();

        int result = 0;
        for (OpenAPIDTO openAPIDTO: list){
            culturefacilityVO.setCname(openAPIDTO.getFacilityName());
            if(openAPIDTO.getRoadAddr() == null){
                culturefacilityVO.setRaddress("주소 없음");
            }else {
                culturefacilityVO.setRaddress(openAPIDTO.getRoadAddr());
            }
            culturefacilityVO.setLatitude(openAPIDTO.getLatitude());
            culturefacilityVO.setLongitude(openAPIDTO.getLongitude());
            culturefacilityVO.setAddr_code(openAPIDTO.getAddrCode());
            culturefacilityVO.setPhone(openAPIDTO.getNumber());
            culturefacilityVO.setCloseddays(openAPIDTO.getCloseDay());
            culturefacilityVO.setOperatingtime(openAPIDTO.getOperatingTime());
            culturefacilityVO.setPa(openAPIDTO.getPa());
            culturefacilityVO.setChomepage(openAPIDTO.getHomepage());
            culturefacilityVO.setCulturecate(openAPIDTO.getCate2() + ", " + openAPIDTO.getCate3());
//            Thread.sleep(100);
            try{
                culturefacilityProc.create(culturefacilityVO);
            }catch(Exception e){
                System.out.println(culturefacilityVO);
            }
        }
//        for (int i = 1; i < 2; i++) { //23929
//            list = OpenAPI.getData(i, 2); // 3000개까지는 서버 에러 없이 출력 가능
//            list_all.addAll(list);
//        }

//        return list.size() + "개" + list_all.toString();
        return "저장된 데이터 개수 -> " + result;
    }


}
