ReadMe
# 프로젝트 명 'Bovo'

## 🚀 목차 {#top}

- [1. 프로젝트 소개](#1-프로젝트-소개)
- [2. 개발 인원 및 역할(FE)](#2-개발-인원-및-역할fe)
- [3. 프로젝트 일정](#3-프로젝트-일정)
- [4. 기술 스택](#4-기술-스택)
- [5. 시스템 아키텍쳐](#5-시스템-아키텍쳐)
- [6. 패키지 구조](#6-패키지-구조)
- [7. 서비스 기능](#7-서비스-기능)

## 1. 프로젝트 소개

Bovo는 독서 기록 습관 형성을 위한 플랫폼입니다.<br>
독서에 재미를 느낄 수 있도록, 독서 기록 템플릿 제공, 퀘스트, 독서 토론을 통해 독서에 흥미를 느끼는 분 모두 사용해보세요~

Netlify(테스트트) : [클릭하여 페이지를 방문하세요](https://bovo.netlify.app/)<br>

AWS S3 : [클릭하여 페이지를 방문하세요](http://bovo-client.s3-website.ap-northeast-2.amazonaws.com)

## 2. 개발 인원 및 역할(FE)

| **박정찬** | **정교원** | **서지영** |
|:----------:|:----------:|:----------:|
| 팀장(BE) | 팀원(BE) | 팀원(BE) |
| 프로젝트 관리<br> 프로젝트 발표<br> boilerplate 작성<br> 마이페이지<br> 마이페이지 수정<br> 서비스 정보<br> 퀘스트<br> 독서토론방 리스트<br> 독서토론방 만들기<br> 독서토론방(채팅)<br> 화면 배포| 로그인<br> 도서 검색<br> 메인 페이지<br> 독서 캘린더 | 서재<br> 독서기록<br> MSW 사용 |

## 3. 프로젝트 일정

| **항목** | **기간** |
|:----------:|:----------:|
| 프로젝트 주제 선정 및 기획 | 2025.02.03 ~ 2025.02.12 |
| 요구사항 명세서 작성 및 디자인 | 2025.02.13 ~ 2025.02.16 |
| API 명세서 작성 | 2025.02.19 ~ 2025.02.20 |
| boilerplate 작성 | 2025.02.21 ~ 2025.02.24 |
| 개발 | 2025.02.25 ~ 2025.03.19 |
| refactoring | 2025.03.21 ~ 2025.05.12 |
| 화면단 배포 | 2025.05.12 ~ 2025.05.21 |
| 디버깅 및 refactoring | 2025.05.21 ~ 현재 |

<img src="./docResource/img/develop_plan.jpg" alt="프로젝트 일정">

[맨 위로](#top)

## 4. 기술 스택

우리 프로젝트는 다음과 같은 기술 스택으로 개발되었습니다.

### 💻 프론트엔드 (Frontend)
#### 언어 및 라이브러리
![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=white)

![React Hook Form](https://img.shields.io/badge/React_Hook_Form-EC5990?style=for-the-badge&logo=reacthookform&logoColor=white)
![Redux Toolkit](https://img.shields.io/badge/Redux_Toolkit-764ABC?style=for-the-badge&logo=redux&logoColor=white)
![dnd kit](https://img.shields.io/badge/dnd_kit-6933F8?style=for-the-badge) 
![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white)
![MSW](https://img.shields.io/badge/MSW-FF4742?style=for-the-badge)
![STOMP](https://img.shields.io/badge/STOMP-4CAF50?style=for-the-badge) 

#### 디자인 시스템
![MUI](https://img.shields.io/badge/MUI-007FFF?style=for-the-badge&logo=mui&logoColor=white)

### ⚙️ 백엔드 (Backend)
#### 언어 및 프레임워크
![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring_Data_JPA-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)

#### 데이터베이스
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

### 🔗 API
![WebSocket](https://img.shields.io/badge/WebSocket-000000?style=for-the-badge&logo=socket.io&logoColor=white)
![Kakao Login API](https://img.shields.io/badge/Kakao_Login_API-FFCD00?style=for-the-badge&logo=kakaotalk&logoColor=black)
![Kakao Book API](https://img.shields.io/badge/Kakao_Book_API-FFCD00?style=for-the-badge&logo=kakaotalk&logoColor=black)

### ☁️ 인프라 및 배포 (Infrastructure & Deployment)
#### 화면
![AWS S3](https://img.shields.io/badge/AWS_S3-569A31?style=for-the-badge&logo=amazons3&logoColor=white)
![Netlify](https://img.shields.io/badge/Netlify-00C7B7?style=for-the-badge&logo=netlify&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![npm](https://img.shields.io/badge/npm-CB3837?style=for-the-badge&logo=npm&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-2671E5?style=for-the-badge&logo=githubactions&logoColor=white)

#### 서버
![AWS EC2](https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)

### 🛠️ 개발 환경 및 협업 도구 (Dev Environment & Collaboration Tools)
![Rollup Bundle Analyzer](https://img.shields.io/badge/Rollup_Bundle_Analyzer-EC4A3F?style=for-the-badge&logo=rollup&logoColor=white)
![ESLint](https://img.shields.io/badge/ESLint-4B32C3?style=for-the-badge&logo=eslint&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)
![Figma](https://img.shields.io/badge/Figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white)

![Jira](https://img.shields.io/badge/Jira-0052CC?style=for-the-badge&logo=jira&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=notion&logoColor=white)
![Discord](https://img.shields.io/badge/Discord-5865F2?style=for-the-badge&logo=discord&logoColor=white)

[맨 위로](#top)

## 5. 시스템 아키텍쳐

![system_architecture](./docResource/img/system_architecture.png)

[맨 위로](#top)

## 6. 패키지 구조


[맨 위로](#top)

## 7. 서비스 기능
### (1) 로그인 페이지
<img src="./docResource/img/loginPage.png" alt="로그인" style="width: 40%;" />

### (2) 회원 가입 페이지
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/signup.png" alt="회원가입 페이지">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/profileImgChoice.png" alt="프로필 이미지 선택 페이지">
        </td>
    </tr>
    <tr>
        <td align="center">이메일 회원가입</td>
        <td align="center">프로필 이미지 선택</td>
    </tr>
</table>

### (3) 메인 페이지
<img src="./docResource/img/mainPage.png" alt="메인 페이지" style="width: 40%;" />

### (4) 도서 검색 페이지
#### [도서 검색]
<img src="./docResource/img/searchPage.png" alt="도서 검색 페이지" style="width: 40%;" />

#### [도서 상세 페이지]
<img src="./docResource/img/searchDetailPage.png" alt="도서 상세 페이지" style="width: 40%;" />

#### [토론방 및 내 서재 추가]
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/forumRegistration.png" alt="토론방 도서 등록">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/archiveRegistration.png" alt="내 서재 추가">
        </td>
    </tr>
    <tr>
        <td align="center">
            도서 검색 페이지에서 토론방 도서 등록
        </td>
        <td align="center">
            도서 검색 페이지에서 내 서재에 도서 추가
        </td>
    </tr>
</table>

### (5) 내 서재 페이지
<img src="./docResource/img/archive.png" alt="내 서재 페이지" style="width: 40%;"/>

### (6) 도서 기록 페이지
#### [도서 기록 리스트]
<img src="./docResource/img/note.png" alt="도서 기록 리스트" style="width: 40%;" />

#### [도서 기록 작성 및 템플릿 제공]
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/writingNote.png" alt="도서 기록 작성">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/noteTemplate.png" alt="템플릿 질문">
        </td>
    </tr>
    <tr>
        <td align="center">
            도서 기록 작성 화면
        </td>
        <td align="center">
            도서 기록 템플릿 선택 화면
        </td>
    </tr>
</table>

#### [모아 보기]
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/gatheringNote.png" alt="도서 기록 모아 보기">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/changingNoteOrder.png" alt="도서 기록 순서 변경">
        </td>
    </tr>
    <tr>
        <td align="center">
            도서 기록 리스트 모아보기(하나의 감상문)
        </td>
        <td align="center">
            도서 기록 리스트 순서 변경
        </td>
    </tr>
</table>

### (7) 마이페이지
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/myPage.png" alt="마이페이지">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/myProfilePage.png" alt="마이프로필 페이지">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/myProfileEdit.png" alt="마이프로필 수정 페이지">
        </td>
    </tr>
    <tr>
        <td align="center">
            마이페이지
        </td>
        <td align="center">
            마이프로필
        </td>
        <td align="center">
            마이프로필 수정
        </td>
    </tr>
</table>

### (8) 퀘스트 및 독서성과 페이지
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/expPage.png" alt="퀘스트 및 독서성과 페이지">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/questInfo.png" alt="퀘스트 정보">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/rewardInfo.png" alt="독서 성과 메달 정보">
        </td>
    </tr>
    <tr>
        <td align="center">
            퀘스트 및 독서성과 전체 페이지
        </td>
        <td align="center">
            퀘스트 정보 modal
        </td>
        <td align="center">
            독서 성과 메달 수여 정보 modal
        </td>
    </tr>
</table>

### (9) 독서 토론방 리스트 페이지
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/entireForumPage.png" alt="전체 토론방 리스트">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/myForumList.png" alt="내 토론방 리스트">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/makeForum.png" alt="독서 토론방 만들기">
        </td>
    </tr>
    <tr>
        <td align="center">
            전체 독서 토론방 리스트
        </td>
        <td align="center">
            내 독서 토론방 리스트
        </td>
        <td align="center">
            독서 토론방 만들기
        </td>
    </tr>
</table>

### (9) 독서 토론방(채팅)
<table style="width: 100%;">
    <tr>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/forumChat.png" alt="독서 토론방">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/readingShareModal.png" alt="독서 기록 공유 모달">
        </td>
        <td align="center" style="width: 25%;">
            <img src="./docResource/img/userList.png" alt="독서 토론방 내 유저 리스트">
        </td>
    </tr>
    <tr>
        <td align="center">
            독서 토론방 내 화면
        </td>
        <td align="center">
            독서 기록 공유 modal
        </td>
        <td align="center">
            독서 토론방 내 유저 리스트
        </td>
    </tr>
</table>

