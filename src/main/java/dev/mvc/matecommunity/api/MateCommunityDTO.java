package dev.mvc.matecommunity.api;

import dev.mvc.matecommunity.MateCommunityVO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class MateCommunityDTO {
    // 보내줄 게시물 목록
    private ArrayList<MateCommunityVO> mcList;
}
