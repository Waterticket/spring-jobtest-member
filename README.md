# 기술 과제
Spring Boot를 활용하여 제작한 기술과제입니다.

- [Specification](./SPECIFICATION.md)

<br>

## 설계

### `SYSTEM_USER` 테이블
Structure단에서 INDEX나 UNIQUE같은 컬럼 속성을 설정하고 싶었으나, 제공해주신 DDL을 사용해야 했기 떄문에 별도로 설정을 바꾸지는 않았습니다.

다만 엔티티 설계단에서 이름은 중복이 가능하도록 설계하였는데, 동명이인이 가입할 수 있는 상황이 존재하기 때문에 이를 고려하였습니다.

```
entity "SYSTEM_USER" {
  + idx: Integer [PK]
  --
  id: String [Unique]
  password: String
  name: String
  auth: String
}
```

<br>

### 비밀번호 암호화
비밀번호는 타입이 제시되지 않았기 때문에, BCrypt를 도입하였습니다.  
BCrypt는 비밀번호 암호화에 주로 사용되는 단방향 암호화 알고리즘이며, Spring Security에서 기본적으로 제공하고 있기 때문에 유지보수에 용이할것이라 판단하였습니다.  
또한, BCrypt 암호화 결과값의 최대 길이가 72 byte 이며, user_pw 컬럼의 최대 길이가 100 byte 이기 때문에 도입하는데 문제가 없습니다.

<br>

### 회원 관리 API
회원 조회, 추가, 수정, 삭제 API를 제작하였으며, 요구사항을 전부 만족하였습니다.  
모든 API는 HTTP Basic 인증이 필요하며, 요청/응답 명세서는 [Specification](./SPECIFICATION.md) 문서에 작성하였습니다.

다만 회원 조회 API에서 회원 이름으로 검색할 경우, 회원 이름 중복이 가능하기에 List 형태로 반환하도록 설계하였습니다.

중복과 관련된 처리는 Service 단에서 관리하며, `user/service/UserService`를 확인해보시면 됩니다.

<br>

### 로그인/로그아웃 및 페이지 구현
로그인/로그아웃은 Spring Security를 활용하여 구현하였습니다.  
DML을 추가하고 나서는 바로 로그인이 불가능하기 때문에, `/signup` 페이지에서 회원가입이 필요하며, 회원가입 후 `/login` 페이지에서 로그인이 가능합니다.  
다만 이 경우 일반 회원만 가입이 가능하기 때문에, 회원 관리 API 혹은 하단의 테스트 코드를 통해서 관리자 회원을 추가해 줄 필요가 있습니다.  

또한 요구사항 중에 로그인 페이지를 제외한 모든 페이지는 로그인이 필요하다고 되어있는데, 회원 가입 페이지로 이동할 수 있는 메인 페이지가 필요하다고 판단하여 `/` 페이지와 `/signup` 페이지를 예외로 두었습니다.

회원 관리 페이지는 `SYSTEM_ADMIN` 권한을 가진 계정만 접속이 가능하며, 회원 전체의 목록을 확인할 수 있습니다.  
회원 전체 조회 페이지는 DB의 부하를 줄이기 위해 Pagenation을 적용할 수도 있었지만, 테스트 데이터가 적기 때문에 불필요하다고 판단하여 적용하지 않았습니다.

<br>

### 회원 정보 관리 히스토리
히스토리 기능은 회원 추가, 수정, 삭제시에 등록되도록 구현하였으며, 전부 `user/service/UserHistoryService`에서 관리하고 있습니다.

`UserService`에서 Create, Update, Delete를 수행할 때, `UserHistoryService`를 호출하여 히스토리를 등록하도록 설계하였습니다.

<br>

### 테스트 코드
기능 테스트를 위해 테스트코드를 작성하였으며, 공통 요구사항에 테스트 계정 데이터 삽입이 있었기에 계정 생성후 CleanUp 작업은 진행하지 않았습니다.  
이로 인해 두번째 테스트 시도부터 테스트가 실패하니 주의 부탁드립니다.  
생성되는 관리자 계정의 로그인 정보는 `user1 : user1_pw`이며, CleanUp 코드는 `UserTest` 테스트 파일에 작성 후 주석처리 해두었습니다.

추가로 REST API 테스트 코드도 작성하였으며, `UserRESTApiTest` 파일에서 확인하실 수 있습니다.

<br>

### 보안
보안을 위해 에러시에 stacktrace를 꺼둔 상태이며, 필요하시다면 `application.properties` 파일에서 `server.error.include-stacktrace=always`로 변경하시면 됩니다.