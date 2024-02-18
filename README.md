# 페이히어 백엔드 엔지니어 과제

## 목차

- [기능 요구사항](#기능-요구사항)
- [패키지 구조](#패키지-구조)
- [빌드 및 실행](#빌드-및-실행하기)
- [배운 점](#배운-점)
- [아쉬운 점](#아쉬운-점)
- [개발 환경](#개발-환경)

## 기능 요구사항

## 패키지 구조

이 프로젝트는 **기능별**로 구성된 패키지 구조로 가지고 있습니다.

- `auth`(인증/인가), `owner`(사장님/회원), `product`(상품), 그리고 전역적으로 사용되는 요소를 담당하는 `global`로 나뉩니다.

- 각 기능별 채키지 안에는 `application`, `domain`, `dto`, `exception`, `presentation`의 하위 구조로 세분화되어 있습니다.

<img src="/docs/image/docs-package-structure.png" width="500">

- `presentation`은 **외부와의 상호작용**을 담당하는 부분으로, 외부 요청과 응답 처리에 관련된 클래스들이 이 레이어에 속합니다.
  외부 의존성이 높으며 변화에 민감한 요소들이 위치합니다.

- `domain`은 핵심 비즈니스 로직과 관련된 **엔티티**, 엔티티와 연관된 클래스들, 그리고 데이터베이스와 연결된 **레포지토리** 클래스들이 이 레이어에 속합니다.
  이는 프로젝트의 **핵심 도메인**을 정의하며, 데이터의 구조와 관계를 담당합니다.

- `application`는 주로 **비즈니스 로직을 처리**합니다.
  이 레이어에는 서비스 클래스들이 위치하며, 사용자의 요청을 실제 동작으로 변환하는 중심적인 역할(**구현 로직**)을 담당하며 재사용성도 **높은 핵심 레이어**입니다.

  - 코드가 계속 성장해서 비즈니스 로직이 많아지면, 필요에 따라서 `Businness Layer`, `Implement layer`로 구분할 것입니다.

- `dto`는 계층 간 데이터 전송을 위해 사용되는 객체로, 단순한 데이터 컨테이너 역할을 합니다.
  주로 서비스 계층 간의 **데이터 교환**에 사용되며, 애플리케이션의 계층 사이에 데이터를 효율적으로 전달합니다.

- `exception`은 기능별로 발생할 수 있는 예외 상황들을 관리합니다. 예를 들어, 상품을 찾을 수 없을 때 발생하는 `NotFoundProductException` 과 같은 예외 클래스들이 이 레이어에 정의됩니다.

- `global`는 프로젝트 전반에 걸쳐 공통적으로 사용되는 요소들이 이 레이어에 속합니다.
  공통적으로 사용하는 `BaseEntity`나 `dto`, `error`, `config`에 대한 클래스들이 모여있습니다.

## 빌드 및 실행하기

### 터미널 환경

* Git, Java는 설치되어 있다고 가정합니다.

> 방법1. 터미널 창에서 java -jar 로 실행할 경우

```git
$ git clone https://github.com/devFancy/payhere.git
$ cd payhere
$ ./gradlew clean build
$ java -jar build/libs/Payhere-0.0.1-SNAPSHOT.jar
```

- 터미널 창에 `java -jar build/libs/Payhere-0.0.1-SNAPSHOT.jar` 입력하면 아래와 같은 창이 나옵니다.

  ![](/docs/image/docs-payhere-java-jar.png)

- 접속(Base) Url: `http://localhost:8080`

> 방법2. PayhereApplication.class 에서 실행할 경우

```git
$ git clone https://github.com/devFancy/payhere.git
$ cd payhere
$ ./gradlew clean build
```

- payhere 패키지 - `build.gradle` 파일 오른쪽 마우스 클릭 - `Link Gradle Project` 클릭합니다.
    - 오류) 다음와 같은 오류가 생길 경우 - `java.lang.IllegalStateException: Module entity with name: payhere should be available`
    - 해결방안) 최상단에 있는 프로젝트명(Payhere)과 `setting.gralde` 파일의 rootProject.name (Payhere)을 일치시킵니다.

- payhere 패키지 - `build.gradle` 파일 클릭 후 우측 상단에 `Gradle` - `Reload All Gradle Projects` 클릭합니다. -> `BUILD SUCCESSFUL` 이라는
  결과가 뜨게 됩니다.

  ![](/docs/image/docs-build-gralde.png)

- payhere 패키지 - src/main/java 에서 `PayhereApplication` 클래스에서 `Run` 버튼 클릭합니다. 그러면 아래와 같은 결과가 나오게 됩니다.

  ![](/docs/image/docs-payhereapplication-run.png)

- 접속(Base) Url: `http://localhost:8080`

## 배운 점

## 아쉬운 점

## 개발 환경

- 기본 환경

    - IDE: IntelliJ IDEA Ultimate Edition

    - OS: MacOS (Apple M1 Pro)

    - Git

- Backend

    - `Language`| Java 11

    - `Framework` | Spring Boot 2.7.1, Spring MVC 5.3.2

    - `ORM` | Spring Data JPA 2.7.1, JPA Hibernate 5.6.1

    - `Database` | H2 2.1.2 / MySQL 5.7

    - `Build Tool` | Gradle 7.4.1

    - `Jwt` | Jwt 0.11.5

    - `Test` | Junit5, Mockito 4.5.1

- Infra

    - `CI/CD` | Docker