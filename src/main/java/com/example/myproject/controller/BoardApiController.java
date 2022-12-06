package com.example.myproject.controller;

import java.util.List;
import java.util.Map;

import com.example.myproject.config.auth.PrincipalDetails;
import com.example.myproject.dto.CommentRequestDto;
import com.example.myproject.dto.HeartDto;
import com.example.myproject.entity.Comment;
import com.example.myproject.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myproject.dto.BoardRequestDto;
import com.example.myproject.dto.BoardResponseDto;
import com.example.myproject.paging.CommonParams;
import com.example.myproject.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController// = @Controller + @ResponseBpdy
@RequiredArgsConstructor
@RequestMapping("api")
public class BoardApiController {
	final private BoardService boardService;
	final private CommentService commentService;
		/**
		 * 게시글 리스트 조회
		 */
		@GetMapping("/boards")
		public Map<String, Object> findAll(final CommonParams params) {
		    return boardService.findAll(params);
		}
		// 조회
		@GetMapping(path="board/{id}")
		public BoardResponseDto listById(@PathVariable Long id){
			var dto = boardService.findIsHeartedById(id);
			log.info("id로 조회하는 api 요청응답:{}", dto);
			return dto;
		}
		//글 생성 create
		@PostMapping("board")
		public Long create(@RequestBody BoardRequestDto boardReq,
						   @AuthenticationPrincipal PrincipalDetails principalDetails) {
			String username = principalDetails.getUsername();
			log.info("글 생성 받은 데이터: {}, {}", boardReq.getTitle(), boardReq.getContent());
			return boardService.save(boardReq, username).getId();
		}
		//수정 update
		@PatchMapping("board/{id}")
		public Long create(@PathVariable("id") Long id, 
							@RequestBody BoardRequestDto boardReq) {
			boardService.update(id, boardReq);
			return id;
		}
		// 삭제
		@DeleteMapping("board/{id}")
		public Long delete(@PathVariable("id") Long id){
			boardService.delete(id);
			return id;
		}

		//좋아요 조회
	// HeartDto => 해당 글을 유저가 눌렀었다면 유저id를, 안눌렀다면 -1값을 가짐 <프론트에선 userid와 접속자의 id가 다르면 빈하트가 되는 원리>. total은 해당 게시글의 총 하트수를 의미함
	    @GetMapping("heart/{board_id}/{user_id}")// 글을 처음 들어갔을 때 필요한 정보 조회
		public HeartDto findHeartInfo(@PathVariable("board_id") Long boardId, @RequestBody Long userId){
			userId = boardService.findIsHeartedById(boardId, userId);
			Long total = boardService.countByBoardId(boardId);
			return new HeartDto(boardId, userId, total);
		}
		//좋아요 누르기
		@PatchMapping("heart")
		public HeartDto doHeart(@RequestBody HeartDto dto){
			boardService.createHeart(dto);
			Long total = boardService.countByBoardId(dto.getBoardId());
			return new HeartDto(dto.getBoardId(), dto.getUserId(), total);
		}
		//좋아요 취소
		@DeleteMapping("heart")
	    public HeartDto failHeart(@RequestBody HeartDto dto){
			boardService.deleteHeart(dto.getBoardId(), dto.getUserId());
			Long total = boardService.countByBoardId(dto.getBoardId());
			Long userId = (long)-1;
			// userid=-1, total-=1(삭제하고 count했으니 -1된거임) 해서 보냄
			return new HeartDto(dto.getBoardId(), userId, total);
		}


	// 댓글 조회
	@GetMapping("comment/{board_Id}")
	public List<Comment> findAll(@PathVariable("board_id")Long boardId){

		return commentService.findAll(boardId);
	}
	//댓글 작성
	@PostMapping("comment")
	public Long create(@RequestBody CommentRequestDto dto){
		// 댓글쓰면 보던 글id 리턴
		Long boardId = commentService.save(dto).getBoardId();
		return boardId;
	}
	//댓글 수정
	@PatchMapping("comment")
	public Long update(@RequestBody CommentRequestDto dto){
		// 댓글쓰면 보던 글id 리턴
		Long boardId = commentService.save(dto).getBoardId();
		return boardId;
	}
	// 댓글 삭제
	@DeleteMapping("comment/{id}")
	public Long deleteComment(@PathVariable Long id){
		Long boardId = commentService.deleteComment(id).getBoardId();
		return boardId;
	}



}
