# swith
S-with
장애인들을 위한 운동기반 커뮤니티
팀 노션
https://www.notion.so/SPORTS-MANIA-76af72e9433f446785e6678cc0017a0d

목차
1. 팀소개
2. 개발환경/기간
3. 프로젝트 주제
4. 설계
5. 기능소개
6. 트러블슈팅


<h2>팀소개</h2>

![image](https://github.com/hongsoonho0723/swith/assets/116165465/ea4379a0-5527-4ff6-97d7-ba7bef0cccb7)



<h2>개발환경/기간</h2>

![image](https://github.com/hongsoonho0723/swith/assets/116165465/5d47dc3b-684a-4698-aee6-e672b3952a2b)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/fe076948-e26b-4729-973d-83c75aac3aeb)


초록색부분이 원래 계획했던 부분, 빨간색부분이 실제 기간

<h2>프로젝트 주제</h2>

![image](https://github.com/hongsoonho0723/swith/assets/116165465/084c5986-588d-403a-8b35-35d0506bfec6)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/f8ef8829-79e6-4fdf-8a83-31d59d2ed046)


<h2>설계</h2>
restapi설계

https://www.notion.so/REST-API-92dde656f2094637ab9b616af2c33562


화면 설계

![image](https://github.com/hongsoonho0723/swith/assets/116165465/c1dcfd3a-f5af-4d30-963c-554507b07417)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/27d61ac8-07bd-419b-b19e-f8f7d9eda83d)


DATABASE설계

![image](https://github.com/hongsoonho0723/swith/assets/116165465/a8c3765e-cb89-4ae7-abef-2637091c81fb)



<h2>기능 소개</h2>

![image](https://github.com/hongsoonho0723/swith/assets/116165465/63d1a8d9-1ff7-42f9-9792-ba59a2761c76)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/1ab309ce-ee3c-4783-af09-be5d2680acaf)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/c9d0258a-8a70-4278-88d2-17912b6a92d5)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/bb921abe-069b-4d35-94fd-98ade50d4e7c)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/bc278c4a-5364-4fd9-a983-1ef9f3fe8157)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/8515d0cf-c5b8-49de-9e64-3b2d2f72a555)

![image](https://github.com/hongsoonho0723/swith/assets/116165465/65687bfc-1a08-4983-b6ff-a8d2a5b8e285)




<h2>트러블 슈팅</h2>

<h4>****[[Spring] Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported]</h4>(https://abbo.tistory.com/330)****

스프링에서는 지원하지않는다함 되는사람도있긴한데 ,, 일단 나는안됨 

@RequestBody사용시 발생하는데 @RequestParam 으로 직접매핑 시켜주거나 

@ModelAttribute로 바인딩 하면 해결됨


<h4> **Data truncation: Incorrect string**</h4>

**Data truncation: Incorrect string value:'\xED\x85\x8C\xEC\x8A\xA4...' for column ~~ at row 1**

쿼리문에 한글이 들어가지 않아 발생함

이유는 모르겠으나 ,, 로컬에선 잘되던게 공인 ip사용 데이터베이스를만들어서 그런것같음

데이터 베이스 기본설정문제 

ALTER TABLE 테이블 convert to charset UTF8; 테이블생성후 작성하거나

CREATE TABLE 테이블 (
...
) DEFAULT CHARACTER SET UTF8; 생성할때 설정
