# REAT API - 회원제커뮤니티

### rest api와 ajax 방식 게시판

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Thymeleaf

- Ajax
- MyBatis
- gradle
- Jasypt

---

### 요약

- 로그인은 **spring security**를 사용해 '**구글로 로그인 하기**' ‘**카카오톡으로 로그인하기**’기능을 완성
- 회원의 **비밀번호**를 오픈소스 ‘Jasypt’를 통해 db에 **암호화 하여 저장**하여 정보를 안전하게 보관
- 글작성 **CRUD**, 페이징과 검색기능, **spring security**를 이용한 로그인/회원가입으로 **접근권한 제한**
- **페이징 처리**와 **검색 기능**은 mybatis를 사용
- **좋아요**와 **사용자-글** 사이의 1:N 의 관계를 표현하는데 **jpa**를 사용
- 기능마다 테스트 수행

---
### ERD
![myproject ERD](https://github.com/jisoo615/myproject/assets/57720285/c4845a22-27b0-435b-ad37-b69500b18517)

---

### 🔻기능명과 간단한 설명, 사진

- 글 작성, 수정, 삭제 읽기 기능
- 페이징 처리
  <img src="https://github.com/jisoo615/myproject/assets/57720285/37abea35-9b08-4bc1-a785-8f70ef41bdb5" width="50%">


- 검색 기능
  <img src="https://github.com/jisoo615/myproject/assets/57720285/8d137623-d076-4739-8f0c-b41c88507adc" width="50%">

    
- 회원가입/로그인 기능 - 카카오/네이버 로그인

spring security로 비로그인 회원은 게시판 상세내역 확인 제한 + 글 작성 제한

|일반 회원가입|구글로그인, 카카오 로그인 구현|
|-----|-----|
|![3](https://github.com/jisoo615/myproject/assets/57720285/d8ba52db-02e9-43de-9b11-c02890a79815)|![4](https://github.com/jisoo615/myproject/assets/57720285/3dab5ece-3ee7-4614-8a3d-04ecc3a58925)|



- 좋아요 기능
회원이 접속한 글에 하트를 누르면, 채워진 하트로 바뀌고 숫자가 증가
<img src="https://github.com/jisoo615/myproject/assets/57720285/c0e3b629-010e-4bfb-ba70-e12a6a52c77b" width="50%">


- 오픈소스 Jasypt 를 이용한 비밀번호 암호화 복호화
  user DB의 password속성 값의 일부
  <img src="https://github.com/jisoo615/myproject/assets/57720285/3896ca45-bd42-4d88-8ee9-47d1dd2c0d65" width="50%">
   
    
- 댓글과 대댓글 구현
  | 댓글 | 대댓글 |
  |-----|-----|
  | ![7](https://github.com/jisoo615/myproject/assets/57720285/faef74dc-10b0-45c7-af20-4f58fc92290d) | ![8](https://github.com/jisoo615/myproject/assets/57720285/b6d73f4d-e8ff-4570-b7c4-40c1590906b6) |
