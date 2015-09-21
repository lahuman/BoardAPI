#게시판 API

## 2015.09.21 추가 내용
 - 백기선님의 강의를 듣고 제가 잘 모르던 JPA 와 jUnit 관련 리펙토링을 진행 하였습니다.
 - 참고 URL : https://github.com/keesun/amugona
 - 추가적으로 JDK 1.8 이상을 지원 하도록 변경 되었습니다.
 
## 개요
 - 작성자 : 임광규
 - 설명
     + 간단한 게시판 REST API 제작     

### 개발환경
- IDE
    + IntelliJ
    + JDK 1.8 이상
    + 필수 Plugin
        - Lombok Plugin

### 사용 프레임워크
- Gradle
- Spring
    + Spring Boot
    + Spring MVC
    + Spring DATA JPA
- Lombok
- SLF4j + logback
- DBMS
    + H2
    + c3p0 DB POOL

### 기동전 확인 사항
- JDK 1.7 이상 필요
- JAVA_HOME 설정 필요

### 서버 기동 방법
+ gradle 이용하여, distZip, 또는 distTar 실행후 결과 파일을 압축 플고 다음과 같이 실행

	- WINDOWS
```
> .\bin\Board.bat
```
	- LINUX
```
$ ./bin/Board    	
```    	 

### 게시물 호출 방법
- 등록<br />
```
$ curl -H "Content-Type: application/json" -X POST -d '{"title": "안녕", "body": "안녕하세요", "author": "나"}' http://localhost:8080/api/posts
```
- 목록<br />
```
$ curl -X GET http://localhost:8080/api/posts
```
- 상세<br />
```
$ curl -X GET http://localhost:8080/api/posts/1
```

- 변경<br />
```
$ curl -H "Content-Type: application/json" -X PUT -d '{"title": "안녕", "body": "안녕하세요", "author": "나"}' http://localhost:8080/api/posts/1
```

- 삭제<br />
```
$ curl -X DELETE http://localhost:8080/api/posts/1
```
