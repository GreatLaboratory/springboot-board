## Preview
![image](https://user-images.githubusercontent.com/46255148/109003542-eb7a0780-76ea-11eb-8e8f-aa3e551867b6.png)


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

## 운영 구조
![image](https://user-images.githubusercontent.com/46255148/108992904-2c6b1f80-76dd-11eb-8b5f-4ffa78094d5e.png)
1. 로컬 PC에서 springboot 웹어플리케이션 개발
2. github으로 push
3. travis에서 gihub으로 push된 repository의 코드를 기반으로 테스트 및 빌드 진행
4. 테스트 및 빌드 진행 결과를 슬랙 또는 이메일로 전달 후 (성공한다면,) aws s3로 빌드파일을 압축하여 업로드 (.zip 파일 생성)
5. aws s3에 업로드된 압축파일을 aws codedeploy가 연동된 aws ec2인스턴스에다가 압축을 풀어서 배포 (.jar 파일 생성)
6. 현재 서비스하고 있는 profile과 port_number를 확인 + 배포를 진행할 profile과 port_number를 확인
7. 배포를 진행할 port_number의 pid를 죽이고 해당 port_number에 배포를 진행할 profile환경으로 jar파일을 실행
8. 새롭게 배포된 springboot 웹어플리케이션이 /profile로 헬스체크가 완료되면 nginx reverse proxy설정에 연결시킬 port_number를 바꿔서 저장
9. nginx의 restart가 아닌 reload로 빠르게 nginx proxy가 바라보는 port_number를 바꿔서 사용자는 아무런 이질감 없이 배포된 버전을 nginx 기본 포트로 접근할 수 있다. (무중단 배포)