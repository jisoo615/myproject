package com.example.myproject;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.myproject.dto.HeartDto;
import com.example.myproject.entity.Heart;
import com.example.myproject.entity.User;
import com.example.myproject.repository.HeartRepository;
import com.example.myproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.myproject.dto.BoardRequestDto;
import com.example.myproject.entity.enums.LockLevel;
import com.example.myproject.repository.BoardRepository;
import com.example.myproject.service.BoardService;

@SpringBootTest
public class BoardTests {
	@Autowired BoardService boardService;
	@Autowired BoardRepository boardRepo;
	@Autowired HeartRepository heartRepository;
	@Autowired UserRepository userRepository;

	BoardRequestDto createTestReq() {//테스트용 생성 요청
		return BoardRequestDto.builder()
				.writer("user1")
				.title("board save test")
				.content("test success")
				.lockLevel(LockLevel.ALL)
				.build();
	}
	BoardRequestDto updateTestReq() {//테스트용 수정 요청
		return BoardRequestDto.builder()
				.writer("user2")
				.title("board update test")
				.content("update test success")
				.lockLevel(LockLevel.FRIENDS)
				.build();
	}
	HeartDto createHeartReq(){
		return HeartDto.builder().boardId((long)1).userId((long)1).total((long)0).build();
	}

	@Test
	void saveUser(){
		userRepository.save(User.builder()
				.provider("test").providerId("0000").password("test_password").username("test_0000")
				.build());
		var entity = userRepository.findByUsername("test_0000");
		assertThat(entity.getProvider()).isEqualTo("test");
		assertThat(entity.getId()).isEqualTo((long)1);
	}
	
	@Test
	void saveBoard() {
		saveUser();
		var request = createTestReq();
		var username = "test_0000";// 유저 더미 만들고 지정하기
		boardService.save(request, username);
		var entity = boardRepo.findById((long) 1).get();
		
		assertThat(entity.getId()).isEqualTo(1);
		assertThat(entity.getUser().getUsername()).isEqualTo("test_0000");
		assertThat(entity.getWriter()).isEqualTo("user1");
		assertThat(entity.getTitle()).isEqualTo("board save test");
		assertThat(entity.getContent()).isEqualTo("test success");
		assertThat(entity.getLockLevel()).isEqualTo("ALL");
		assertThat(entity.getDeleteYn()).isEqualTo('N');
	}
	
	@Test
	void updateBoard() {
		//이미 저장된 상태에서
		var request = createTestReq();
		var username = "test_0000";// 유저 더미 만들고 지정하기
		boardService.save(request, username);// id=1
		// 수정 요청 받아 처리
		var updateReqeust = updateTestReq();
		boardService.update((long) 1, updateReqeust);
		var entity = boardRepo.findById((long) 1).get();
		
		assertThat(entity.getId()).isEqualTo(1);
		assertThat(entity.getWriter()).isEqualTo("user2");
		assertThat(entity.getTitle()).isEqualTo("board update test");
		assertThat(entity.getContent()).isEqualTo("update test success");
		assertThat(entity.getLockLevel()).isEqualTo("FRIENDS");
		System.out.println(entity.getLockLevel());
		assertThat(entity.getDeleteYn()).isEqualTo('N');
		assertThat(entity.getModifiedDate()).isNotNull();
	}

	HeartDto createHeartRequest(){
		return HeartDto.builder().boardId((long)1).userId((long)1).build();
	}
	@Test
	void saveHeart(){
		saveBoard();
		var request = createHeartRequest();
		boardService.createHeart(request);
		var entity = heartRepository.findByBoardIdAndUserId(request.getBoardId(), request.getUserId());
		if(entity.isEmpty()) return;
		Heart heart = entity.get();
		assertThat(heart.getBoardId()).isEqualTo((long)1);
		assertThat(heart.getUser().getId()).isEqualTo((long)1);
		assertThat(boardService.countByBoardId(request.getBoardId())).isEqualTo((long)1);
	}
	@Test
	void deleteHeart(){
		saveHeart();
		boardService.deleteHeart((long)1, (long)1);
		assertThat(boardService.countByBoardId((long)1)).isEqualTo((long)0);
	}

}
