package com.example.myproject;

import static org.assertj.core.api.Assertions.assertThat;

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
	
	@Test
	void save() {
		var request = createTestReq();
		boardService.save(request);
		var entity = boardRepo.findById((long) 1).get();
		
		assertThat(entity.getId()).isEqualTo(1);
		assertThat(entity.getWriter()).isEqualTo("user1");
		assertThat(entity.getTitle()).isEqualTo("board save test");
		assertThat(entity.getContent()).isEqualTo("test success");
		assertThat(entity.getLockLevel()).isEqualTo(LockLevel.ALL);
		assertThat(entity.getDeleteYn()).isEqualTo('N');
	}
	
	@Test
	void update() {
		//이미 저장된 상태에서
		var request = createTestReq();
		boardService.save(request);// id=1
		// 수정 요청 받아 처리
		var updateReqeust = updateTestReq();
		boardService.update((long) 1, updateReqeust);
		var entity = boardRepo.findById((long) 1).get();
		
		assertThat(entity.getId()).isEqualTo(1);
		assertThat(entity.getWriter()).isEqualTo("user2");
		assertThat(entity.getTitle()).isEqualTo("board update test");
		assertThat(entity.getContent()).isEqualTo("update test success");
		assertThat(entity.getLockLevel()).isEqualTo(LockLevel.FRIENDS);
		System.out.println(entity.getLockLevel());
		assertThat(entity.getDeleteYn()).isEqualTo('N');
		assertThat(entity.getModifiedDate()).isNotNull();
	}
	

}
