# BTONGTALK

<p align="center"><img width="300" alt="로고" src="https://github.com/user-attachments/assets/99960daa-8c87-4fc0-8e44-fbcc41223a4d"></p>

> **면접 준비를 위한 플래시카드 프로그램** 
<br> **개발 기간: 2024-07-23 ~ 2024-08-29**

## URL
> **배포 URL: https://btongtalk.store** <br>
> **프로젝트 구현 기록 URL: https://noon-chance-53f.notion.site/BTONGTALK-123cf60ac914819b83d3fba3be117ae3?pvs=4**

## 프로젝트 소개

배통톡은 개발자를 위한 면접 준비 플랫폼으로, 네트워크, 알고리즘, 데이터베이스 등 다양한 카테고리의 면접 질문을 제공하고, 사용자가 실제 면접처럼 질문에 답변을 녹음하고 확인할 수 있도록 돕습니다. 또한, 사용자가 자신의 학습 상태를 관리하고 반복 학습할 수 있는 기능을 제공합니다.

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

## 디렉토리 구조

```
📦 btongtalk-back
├─ .github
│  └─ workflows
│     back-ci-cd.yml
├─ Dockerfile
└─ src
   └─ main
      ├─ resources
      │  └─ application.properties
      └─ java
         └─ btongtong
            └─ btongtalkback
               ├─ BtongtalkbackApplication.java
               ├─ config
               │  ├─ SecurityConfig.java
               │  └─ beanConfig.java
               ├─ constant
               │  ├─ ErrorCode.java
               │  ├─ Provider.java
               │  ├─ RecordStatus.java
               │  └─ Role.java
               ├─ controller
               │  ├─ CategoryController.java
               │  ├─ FlashcardController.java
               │  ├─ MemberController.java
               │  └─ RecordController.java
               ├─ domain
               │  ├─ Category.java
               │  ├─ Flashcard.java
               │  ├─ Member.java
               │  └─ Record.java
               ├─ dto
               │  ├─ auth
               │  │  ├─ AuthDto.java
               │  │  ├─ OauthAttributes.java
               │  │  └─ ReissueDto.java
               │  ├─ category
               │  │  └─ response
               │  │     ├─ CategoryDto.java
               │  │     └─ SubCategoryDto.java
               │  ├─ error
               │  │  └─ response
               │  │     └─ ErrorResponse.java
               │  ├─ flashcard
               │  │  └─ response
               │  │     ├─ FlashcardDto.java
               │  │     ├─ FlashcardWithCategoryDto.java
               │  │     ├─ FlashcardWithProgressDto.java
               │  │     ├─ FlashcardsWithProgressAndCategoryDto.java
               │  │     ├─ SearchFlashcardDto.java
               │  │     └─ SearchFlashcardsWithTotalPagesDto.java
               │  ├─ member
               │  │  └─ response
               │  │     └─ MemberDto.java
               │  └─ record
               │     ├─ request
               │     │  ├─ CategoryIdAndProgressDto.java
               │     │  └─ FlashcardIdAndStatusDto.java
               │     └─ response
               │        ├─ RecordDto.java
               │        ├─ RecordStatisticsByFlashcardDto.java
               │        ├─ RecordStatisticsDto.java
               │        └─ RecordsByStatusWithTotalPages.java
               ├─ filter
               │  └─ JwtFilter.java
               ├─ handler
               │  ├─ CustomAccessDeniedHandler.java
               │  ├─ CustomAuthenticationHandler.java
               │  ├─ GlobalExceptionHandler.java
               │  ├─ Oauth2FailureHandler.java
               │  ├─ Oauth2SuccessHandler.java
               │  └─ exception
               │     └─ CustomException.java
               ├─ repository
               │  ├─ CategoryRepository.java
               │  ├─ FlashCardRepository.java
               │  ├─ MemberRepository.java
               │  └─ RecordRepository.java
               ├─ service
               │  ├─ CategoryService.java
               │  ├─ FlashcardService.java
               │  ├─ MemberService.java
               │  ├─ Oauth2UserService.java
               │  └─ RecordService.java
               └─ util
                  ├─ FilterUtil.java
                  ├─ JwtUtil.java
                  └─ OauthUtil.java
```

```
📦 btongtalk-front
├─ .github
│  └─ workflows
│     back-ci-cd.yml
├─ Dockerfile
├─ nginx.conf
├─ package-lock.json
├─ package.json
├─ public
│  ├─ index.html
│  ├─ logo.png
│  └─ robots.txt
└─ src
   ├─ App.css
   ├─ App.js
   ├─ apis
   │  ├─ api.js
   │  └─ url.js
   ├─ assets
   │  ├─ default-profile-image.jpeg
   │  ├─ kakao.png
   │  ├─ logo.png
   │  └─ naver.png
   ├─ components
   │  ├─ category
   │  │  ├─ category.css
   │  │  └─ category.jsx
   │  ├─ flashcard
   │  │  ├─ flashcard.css
   │  │  └─ flashcard.jsx
   │  ├─ flashcardBtn
   │  │  ├─ flascardBtn.jsx
   │  │  └─ flashcardBtn.css
   │  ├─ flashcardStatistics
   │  │  ├─ flashcardStatistics.css
   │  │  └─ flashcardStatistics.jsx
   │  ├─ footer
   │  │  ├─ footer.css
   │  │  └─ footer.jsx
   │  ├─ header
   │  │  ├─ header.css
   │  │  └─ header.jsx
   │  ├─ heading
   │  │  ├─ heading.css
   │  │  └─ heading.jsx
   │  ├─ login
   │  │  ├─ login.css
   │  │  └─ login.jsx
   │  ├─ logoutWithdraw
   │  │  ├─ logoutWithdraw.css
   │  │  └─ logoutWithdraw.jsx
   │  ├─ navbar
   │  │  ├─ navbar.css
   │  │  └─ navbar.jsx
   │  ├─ noCategory
   │  │  ├─ noCategory.css
   │  │  └─ noCategory.jsx
   │  ├─ pagination
   │  │  ├─ pagination.css
   │  │  └─ pagination.jsx
   │  ├─ profile
   │  │  ├─ profile.css
   │  │  └─ profile.jsx
   │  ├─ profileImg
   │  │  ├─ profileImg.css
   │  │  └─ profileImg.jsx
   │  ├─ statistics
   │  │  ├─ statistics.css
   │  │  └─ statistics.jsx
   │  ├─ table
   │  │  ├─ table.css
   │  │  └─ table.jsx
   │  └─ voiceRecord
   │     ├─ voiceRecord.css
   │     └─ voiceRecord.jsx
   ├─ constant
   │  ├─ chartConfig.js
   │  ├─ chartStatus.js
   │  ├─ flashcardStatus.js
   │  ├─ recordStatus.js
   │  └─ url.js
   ├─ hooks
   │  ├─ useFillColor.js
   │  ├─ useFlashcard.js
   │  ├─ useRecord.js
   │  └─ useSetChart.js
   ├─ index.js
   ├─ layout
   │  ├─ base
   │  │  ├─ base.css
   │  │  └─ base.jsx
   │  └─ navbar
   │     └─ navbar.jsx
   ├─ reset.css
   ├─ stores
   │  ├─ useAuthStore.js
   │  ├─ useCategoryStore.js
   │  ├─ useFlashcardStore.js
   │  ├─ useMemberStore.js
   │  ├─ useRecordStore.js
   │  ├─ useSearchFlashcardStore.js
   │  ├─ useStatisticStore.js
   │  └─ useSubCategoryStore.js
   └─ views
      ├─ flashcard
      │  ├─ flashcard.css
      │  └─ flashcard.jsx
      ├─ login
      │  ├─ login.css
      │  └─ login.jsx
      ├─ main
      │  ├─ main.css
      │  └─ main.jsx
      ├─ member
      │  └─ member.jsx
      ├─ record
      │  ├─ record.jsx
      │  └─ records.css
      ├─ searchFlashcard
      │  ├─ searchFlashcard.css
      │  └─ searchFlashcard.jsx
      └─ subcategory
         ├─ subcategory.css
         └─ subcategory.jsx
```

## 주요 기능
### Oauth 로그인
- 카카오와 네이버 계정으로 간편하게 로그인할 수 있습니다.

### 질문 플래시카드
- 카테고리별 질문을 플래시카드 형식으로 제공하며, 각 질문에 대한 답변을 녹음하고 재생할 수 있습니다.

### 문제 풀이 분포
- 지금까지 풀어본 문제의 카테고리별 정답 분포를 그래프로 시각화해 제공합니다.

### 맞은 문제와 틀린 문제 관리
- 맞은 문제와 틀린 문제를 한눈에 확인하고, 틀린 문제를 다시 풀어볼 수 있는 기능을 제공합니다.

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
