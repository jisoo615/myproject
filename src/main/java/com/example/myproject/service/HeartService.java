package com.example.myproject.service;

import com.example.myproject.dto.HeartDto;
import com.example.myproject.entity.Heart;
import com.example.myproject.entity.User;
import com.example.myproject.repository.HeartRepository;
import com.example.myproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class HeartService {
    final private UserRepository userRepository;
    final private HeartRepository heartRepository;


    public HeartDto createHeart(Long boardId, String username){// 하트 누름
        User userEntity = userRepository.findByUsername(username);
        Heart HeartEntity = heartRepository.save(Heart.builder().boardId(boardId).user(userEntity).build());
        HeartDto dto = HeartDto.builder().boardId(boardId).userId(userEntity.getId()).isFull(true).build();
        return dto;
    }
    @Transactional
    public void deleteHeart(Long boardId, String username){// 하트 취소(userId가 있어야만 취소가능하게)
        Long userId = userRepository.findByUsername(username).getId();
        log.info(userRepository.findByUsername(username).getId().toString());
        Heart deletedEntity = heartRepository.deleteByBoardIdAndUserId(boardId, userId);
        log.info(deletedEntity.toString());
        return;
    }

    public HeartDto findIsHeartedById(Long boardId, String username){// 해당 게시글에 접속자가 하트 눌렀는지
        Long userId = userRepository.findByUsername(username).getId();
        Optional<Heart> optional = heartRepository.findByBoardIdAndUserId(boardId, userId);
        HeartDto dto = HeartDto.builder().boardId(boardId).build();
        if(optional.isEmpty()){
            dto.setFull(false);
            return dto;
        };// 누르지 않았으면 false
            dto.setFull(true);
            dto.setUserId(optional.get().getUser().getId());
        return dto;// 눌렀으면 true
    }

    public Long countByBoardId(Long boardId){// 게시글의 총 하트 수
        Long total = heartRepository.countByBoardId(boardId);
        return total;
    }

}
