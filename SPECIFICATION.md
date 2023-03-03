# 개발환경
- Java 17
- Spring Boot 3.0.3
- MariaDB 10.8

<br>

## UserResponse 객체

유저 정보를 반환하는 객체입니다.  
회원 관리 API를 사용했을 때 응답은 이 객체를 사용합니다.

| 필드   | 타입       | 설명                                                                                |
|------|----------|-----------------------------------------------------------------------------------|
| `idx`  | `Integer` | 유저 고유번호입니다.                                                                       |
| `id`   | `String` | 유저 ID입니다. 사이트 내부에서 중복될 수 없으며, 해당 값을 이용하여 로그인을 진행합니다.  <br/>최소 길이는 4자이며, 최대 길이는 30자입니다. |
| `name` | `String` | 유저 이름입니다. 최소 길이는 2자이며, 최대 길이는 100자입니다.                                            |
| `auth` | `String` | 유저 권한입니다. `SYSTEM_ADMIN`과 `USER`중 하나입니다.                                          |


<br>

---

## 회원 관리 API [/api/user]

해당 API는 HTTP Basic 인증을 사용합니다.  
Authorization 헤더에 `basic id:pass`로 인증 정보를 포함하여 요청해야 하며, 인증 실패시 `401 Unauthorized`를 반환합니다.  
인증에 성공하더라도 `SYSTEM_ADMIN` 권한을 가지고 있지 않은 경우 `403 Forbidden`을 반환합니다.

파라메터가 부족하다면 `400 Bad Request`를 반환합니다.

<br>

### 전체 회원 목록 [GET]

전체 회원의 목록을 가져옵니다.

#### Response 200 (application/json)
- 성공했다면 UserResponse List가 돌아옵니다.

      [
          {
              "idx": 1,
              "id": "admin",
              "name": "관리자",
              "auth": "SYSTEM_ADMIN"
          },
          {
              "idx": 2,
              "id": "user",
              "name": "사용자",
              "auth": "USER"
          }
      ]

#### Response 404
- 유저가 없다면 UserNotFoundException이 돌아옵니다.

<br>

---

### 회원 추가 [POST]

회원을 추가합니다.

#### Request (application/json)
| 필드   | 타입       | 설명                                                                                |
|------|----------|-----------------------------------------------------------------------------------|
| `id`   | `String` | 유저 ID입니다. 최소 길이는 4자이며, 최대 길이는 30자입니다. |
| `name` | `String` | 유저 이름입니다. 최소 길이는 2자이며, 최대 길이는 100자입니다.                                            |
| `auth` | `String` | 유저 권한입니다. `SYSTEM_ADMIN`과 `USER`중 하나입니다.                                          |


    {
        "id": "user",
        "name": "사용자",
        "auth": "USER"
    }

#### Response 201 Created (application/json)
- 성공했다면 UserResponse 객체가 돌아옵니다.
- 계정의 비밀번호는 id와 동일합니다.

#### Response 409 Conflict
- id가 중복되어 회원을 추가할 수 없다면 SameIDExistsException이 돌아옵니다.

<br>

---

### 회원 수정 [PUT]

회원을 수정합니다. 회원 이름만 변경 가능합니다.

#### Request (application/json)
| 필드   | 타입       | 설명                                                                                |
|------|----------|-----------------------------------------------------------------------------------|
| `id`   | `String` | 유저 ID입니다. 최소 길이는 4자이며, 최대 길이는 30자입니다. |
| `name` | `String` | 유저 이름입니다. 최소 길이는 2자이며, 최대 길이는 100자입니다.                                            |

    {
        "id": "user",
        "name": "사용자"
    }

#### Response 200 (application/json)
- 성공했다면 UserResponse 객체가 돌아옵니다.

#### Response 404
- id와 일치하는 유저가 없다면 UserNotFoundException이 돌아옵니다.

<br>

---

### 회원 삭제 [DELETE]

회원을 삭제합니다.

#### Request (application/json)
| 필드   | 타입       | 설명                                                                                |
|------|----------|-----------------------------------------------------------------------------------|
| `id`   | `String` | 유저 ID입니다. 최소 길이는 4자이며, 최대 길이는 30자입니다. |

    {
        "id": "user"
    }

#### Response 200 (application/json)
- 성공했다면 삭제한 UserResponse 객체가 돌아옵니다.

#### Response 404
- id와 일치하는 유저가 없다면 UserNotFoundException이 돌아옵니다.

<br>

---

### 회원 번호 검색 [GET /api/user/idx/{idx}]

회원 번호와 일치하는 회원을 가져옵니다.

#### Response 200 (application/json)
- 성공했다면 UserResponse 객체가 돌아옵니다.

#### Response 404
- 일치하는 유저가 없다면 UserNotFoundException이 돌아옵니다.

<br>

---

### 회원 ID 검색 [GET /api/user/id/{id}]

회원 ID와 일치하는 회원을 가져옵니다.

#### Response 200 (application/json)
- 성공했다면 UserResponse 객체가 돌아옵니다.

#### Response 404
- 일치하는 유저가 없다면 UserNotFoundException이 돌아옵니다.

<br>

---

### 회원 이름 검색 [GET /api/user/name/{name}]

회원 이름이 일치하는 회원 들을 가져옵니다.

#### Response 200 (application/json)
- 성공했다면 UserResponse List가 돌아옵니다.

#### Response 404
- 일치하는 유저가 없다면 UserNotFoundException이 돌아옵니다.

<br>
