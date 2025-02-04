# BTONGTALK

<p align="center"><img width="300" alt="로고" src="https://github.com/user-attachments/assets/99960daa-8c87-4fc0-8e44-fbcc41223a4d"></p>

> **CS 공부를 위한 플래시카드 프로그램**
<br> **개발 기간: 2024-07 ~ 2024-08**

## URL
> **배포 URL: https://bttcleanup.store/posts/-OI6lAF4I7N2vz_jd7p7** <br>
> **프로젝트 구현 기록 URL: https://noon-chance-53f.notion.site/BTONGTALK-123cf60ac914819b83d3fba3be117ae3?pvs=4**

## 프로젝트 소개

배통톡은 개발자를 위한 CS 공부 지원 플랫폼으로, 네트워크, 알고리즘, 데이터베이스 등 다양한 카테고리의 CS 질문을 제공하고, 사용자가 질문에 답변을 녹음하고 확인할 수 있도록 돕습니다. 또한, 사용자가 자신의 학습 상태를 관리하고 반복 학습할 수 있는 기능을 제공합니다.

## 개발 환경

### 백엔드
<img src="https://img.shields.io/badge/java-%23ED8B00?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">

### 프론트엔드
<img src="https://img.shields.io/badge/react-61DAFB?style=for-the-badge&logo=react&logoColor=black">

### 데이터베이스
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">

### 서버 및 배포 환경
<img src="https://img.shields.io/badge/amazonwebservices-E95420?style=for-the-badge&logo=amazonwebservices&logoColor=white"> <img src="https://img.shields.io/badge/ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white"> <img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"> <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white"> <img src="https://img.shields.io/badge/openssl-721412?style=for-the-badge&logo=openssl&logoColor=white">

### 버전 관리
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">

## 주요 기능
### Oauth 로그인
- 카카오와 네이버 계정으로 간편하게 로그인할 수 있습니다.

### 플래시카드
- 카테고리별 질문을 플래시카드 형식으로 제공하며, 각 질문에 대한 답변을 녹음하고 재생할 수 있습니다.

### 문제 풀이 분포
- 지금까지 푼 문제의 카테고리별 정답 분포를 그래프로 시각화해 제공합니다.

### 맞은 문제와 틀린 문제 관리
- 맞은 문제와 틀린 문제를 한눈에 확인하고, 틀린 문제를 다시 풀어볼 수 있는 기능을 제공합니다.

## ERD
<img src="https://github.com/user-attachments/assets/535d5acf-d3d6-446f-9a8e-34443c5be924">

## 유저 플로우
<table>
    <tr>
        <th style="text-align:center">로그인 & 회원가입</th>
        <th style="text-align:center">플래시카드</th>
    </tr>
    <tr>
        <td><img width="1470" alt="로그인 & 회원가입" src="https://github.com/user-attachments/assets/b534bc95-f1a3-4e35-84ec-a1aaa364ddc8"></td>
        <td><img width="1470" alt="플래시카드" src="https://github.com/user-attachments/assets/3bf80334-54df-49d7-ab89-e022fe732ec2"></td>
    </tr>
    <tr>
        <th style="text-align:center">문제 검색</th>
        <th style="text-align:center">마이페이지</th>
    </tr>
    <tr>
        <td><img width="1470" alt="문제 검색" src="https://github.com/user-attachments/assets/dc43c55c-ee48-4320-b21b-97c1b5572947"></td>
        <td><img width="1470" alt="마이페이지" src="https://github.com/user-attachments/assets/b22f1fa2-0894-4c9b-8f65-ddb590a343ca"></td>
    </tr>
</table>

## 화면 구성
<table>
    <tr>
        <th style="text-align:center">로그인 화면</th>
        <th style="text-align:center">메인 화면</th>
    </tr>
    <tr>
        <td><img width="1470" alt="로그인" src="https://github.com/user-attachments/assets/4a6b8495-1ab1-4569-8c4c-f64ec76bd1b9"></td>
        <td><img width="1470" alt="메인" src="https://github.com/user-attachments/assets/d9b256a3-10aa-4bb8-adeb-7cf0e123fadf"></td>
    </tr>
    <tr>
        <th style="text-align:center">카테고리 리스트</th>
        <th style="text-align:center">플래시카드</th>
    </tr>
    <tr>
        <td><img width="1470" alt="카테고리" src="https://github.com/user-attachments/assets/6a9aff60-45e2-4637-ad0f-7d1382587cb9"></td>
        <td><img width="1470" alt="플래시카드" src="https://github.com/user-attachments/assets/d98ffbf2-ccdd-42dd-978f-2c8608b934f9"></td>
    </tr>
    <tr>
        <th style="text-align:center">플래시카드 완료 결과</th>
        <th style="text-align:center">카테고리별 분포</th>
    </tr>
    <tr>
        <td><img width="1470" alt="플래시카드 완료 결과" src="https://github.com/user-attachments/assets/ae24703d-04ab-4355-a569-53dd3c6af6a1"></td>
        <td><img width="1470" alt="카테고리별 분포" src="https://github.com/user-attachments/assets/128bd53d-7d0a-4b5c-b675-a6122d845ce9"></td>
    </tr>
    <tr>
        <th style="text-align:center">맞은 문제</th>
        <th style="text-align:center">틀린 문제</th>
    </tr>
    <tr>
        <td><img width="1470" alt="맞은 문제" src="https://github.com/user-attachments/assets/40f7b07b-44e2-4868-897c-e9462f57a031"></td>
        <td><img width="1470" alt="틀린 문제" src="https://github.com/user-attachments/assets/158a7ee7-3e0c-492a-ae9b-69448801ec18"></td>
    </tr>
</table>
