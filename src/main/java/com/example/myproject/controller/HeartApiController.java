package com.example.myproject.controller;

import com.example.myproject.config.auth.PrincipalDetails;
import com.example.myproject.dto.CommentRequestDto;
import com.example.myproject.dto.HeartDto;
import com.example.myproject.entity.Comment;
import com.example.myproject.service.BoardService;
import com.example.myproject.service.CommentService;
import com.example.myproject.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.imageio.plugins.tiff.TIFFDirectory;

@Slf4j
@RestController// = @Controller + @ResponseBpdy
@RequiredArgsConstructor
@RequestMapping("api/heart")
public class HeartApiController {
    private final HeartService heartService;
    //좋아요 조회
    // HeartDto => 해당 글을 유저가 눌렀었다면 유저id를, 안눌렀다면 -1값을 가짐 <프론트에선 userid와 접속자의 id가 다르면 빈하트가 되는 원리>. total은 해당 게시글의 총 하트수를 의미함
    @GetMapping("{board_id}")// 글을 처음 들어갔을 때 필요한 정보 조회
    public HeartDto findHeartInfo(@PathVariable("board_id") Long boardId,
                                  @AuthenticationPrincipal PrincipalDetails principalDetails){
        HeartDto dto = heartService.findIsHeartedById(boardId, principalDetails.getUsername());
        Long total = heartService.countByBoardId(boardId);
        dto.setTotal(total);
        return dto;
    }
    //좋아요 누르기
    @PostMapping("{board_id}")
    public HeartDto doHeart(@PathVariable("board_id") Long boardId,
                            @AuthenticationPrincipal PrincipalDetails principalDetails){
        HeartDto dto = heartService.createHeart(boardId, principalDetails.getUsername());
        dto.setTotal(heartService.countByBoardId(boardId));
        return dto;
    }
    //좋아요 취소
    @DeleteMapping("{board_id}}")
    public Long failHeart(@PathVariable("board_id") Long boardId,
                          @AuthenticationPrincipal PrincipalDetails principalDetails){
        heartService.deleteHeart(boardId, principalDetails.getUsername());
        Long total = heartService.countByBoardId(boardId);
        return total;
    }
}
