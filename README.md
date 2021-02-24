## 기능
- 게시글 목록 조회
- 게시글 조회
- 게시글 등록
- 게시글 수정
- 게시글 삭제
- 구글 / 네이버 로그인
- 로그인된 사용자의 등록, 수정, 삭제 권한 부여
- 로그아웃

## REST API
- 게시글 목록 조회 - GET  /api/v1/posts
- 게시글 조회 - GET  /api/v1/posts/{id}
- 게시글 등록 - POST  /api/v1/posts
- 게시글 수정 - PUT  /api/v1/posts/{id}
- 게시글 삭제 - DELETE  /api/v1/posts/{id}
- 구글 로그인 - GET  /oauth2/authorization/google
- 네이버 로그인 - GET  /oauth2/authorization/naver
- 로그아웃 - GET  /logout

## Server & DevOps
- AWS EC2
- AWS RDS
- AWS S3
- AWS CodeDeploy
- Travis CI
- Nginx - reverse proxy