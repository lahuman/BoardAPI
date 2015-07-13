#게시판 API

## 개요
 - 작성자 : 임광규
 - 설명
     + 간단한 게시판 REST API 제작     

### 개발환경
- IDE
    + IntelliJ
    + JDK 1.7 이상
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
