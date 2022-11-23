package com.example.myproject.service;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.myproject.dto.BoardResponseDto;
import com.example.myproject.paging.CommonParams;

@Mapper
public interface BoardMapper {// BoardMapper.xml 참고
	/**
     * 게시글 수 조회: 검색기준에 따라 totalCount 달라짐
     */
    int count(final CommonParams params);

    /**
     * 게시글 리스트 조회
     */
    List<BoardResponseDto> findAll(final CommonParams params);
}
