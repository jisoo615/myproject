package com.example.myproject.service;

import java.util.*;
import java.util.stream.Collectors;

import com.example.myproject.dto.HeartDto;
import com.example.myproject.entity.Heart;
import com.example.myproject.entity.User;
import com.example.myproject.repository.HeartRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.myproject.dto.BoardRequestDto;
import com.example.myproject.dto.BoardResponseDto;
import com.example.myproject.entity.Board;
import com.example.myproject.paging.CommonParams;
import com.example.myproject.paging.Pagination;
import com.example.myproject.repository.BoardRepository;
import com.example.myproject.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
	private final BoardRepository boardRepository;
	private final BoardMapper boardMapper;
	private final UserRepository userRepository;
	private final HeartRepository heartRepository;
	
	//최신글 위로가게 전체 조회
	public List<BoardResponseDto> findAll() {
		Sort sort = Sort.by(Direction.DESC, "createdDate");
		List<Board> list = boardRepository.findAll(sort);
		//없으면 size=0인 반환함 : 빈BoardResponseDto 반환할 수 있도록 @NoArgsCons..추가함
		return list.stream().map(BoardResponseDto::new).collect(Collectors.toList());
	}
	// 게시글 전체 삭제여부 기준
	public List<BoardResponseDto> findAllByDeleteYn(final char deleteYn) {
		Sort sort = Sort.by(Direction.DESC, "id", "createdDate");
		List<Board> boards = boardRepository.findAllByDeleteYn(deleteYn, sort);
		return boards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
	}
	
	//상세조회 id로
	public BoardResponseDto findIsHeartedById(Long id) {
		var entity = boardRepository.findById(id).orElseThrow();
		entity.increaseViews();// 조회수 증가
		boardRepository.save(entity);
		BoardResponseDto response = new BoardResponseDto(entity);
		log.info("id로 조회하기 id:{}", response);
		return response;
	}
	//작성자 이름으로 조회
	public List<BoardResponseDto> findByWriter(String writer) {
		var response = boardRepository.findByWriter(writer);
		return response.stream().map(BoardResponseDto::new).collect(Collectors.toList());
		//jpa는 list형 반환시, 조회 안되면 사이즈0인 list반환함
	}
	
	@Transactional
	public Board save(BoardRequestDto req, String username) {// 생성저장
		Board entity = Board.builder()
				.content(req.getContent())
				.lockLevel(req.getLockLevel())
				.title(req.getTitle())
				.writer(req.getWriter())
				.user(userRepository.findByUsername(username))
				.views(0)
				.user(userRepository.findByUsername(username))
				.build();
		log.info("저장될 글{}", entity);
		return boardRepository.save(entity);
	}
	
	@Transactional
	public Board update(Long id, BoardRequestDto req) {// 수정
		Board entity = boardRepository.findById(id).orElseThrow();
		entity.update(req.getWriter(),req.getTitle(),req.getContent(),req.getLockLevel());
		return boardRepository.save(entity);
	}

	@Transactional
	public void delete(Long id) {// 삭제
		var entity = boardRepository.findById(id).get();
		entity.delete();
	}
	
	/**
	 * 게시글 리스트 조회 - (With. pagination information) 이 메소드에선 boardMapper씀 xml참고
	 */
	public Map<String, Object> findAll(CommonParams params) {

	    // 게시글 수 조회
	    int count = boardMapper.count(params);

	    // 등록된 게시글이 없는 경우, 로직 종료
	    if (count < 1) {
	        return Collections.emptyMap();
	    }

	    // 페이지네이션 정보 계산
	    Pagination pagination = new Pagination(count, params);
	    params.setPagination(pagination);

	    // 게시글 리스트 조회
	    List<BoardResponseDto> list = boardMapper.findAll(params);

	    // 데이터 반환
	    Map<String, Object> response = new HashMap<>();
	    response.put("params", params);
	    response.put("list", list);
	    return response;
	}

	//TODO: 화면에서 한번에 필요한 정보는, 게시글의 하트수, 접속자의 하트 누름 여부임
	//TODO: heart entity 생성해서 게시글id, 유저id로 속성 정하기. 하트 클릭시 좋아요, 한번 더 클릭시 취소 + jpa 콜렉션
	public void createHeart(HeartDto dto){// 하트 누름
		User entity = userRepository.findById(dto.getUserId()).get();
		heartRepository.save(Heart.builder().boardId(dto.getBoardId()).user(entity).build());
	}
	@Transactional
	public void deleteHeart(Long boardId, Long userId){// 하트 취소
		heartRepository.deleteByBoardIdAndUserId(boardId, userId);
		return;
	}

	public Long findIsHeartedById(Long boardId, Long userId){// 해당 게시글에 접속자가 하트 눌렀는지
		Optional<Heart> optional = heartRepository.findByBoardIdAndUserId(userId, boardId);
		if(optional.isEmpty()) return userId = (long)-1;// 누르지 않았으면 return -1
		return optional.get().getUser().getId();// 눌렀으면 return userId;
	}

	public Long countByBoardId(Long boardId){// 게시글의 총 하트 수
		Long total = heartRepository.countByBoardId(boardId);
		return total;
	}

}
