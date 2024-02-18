# 페이히어 백엔드 엔지니어 과제 (Developed by Jun Yong, Moon)

> 컨벤션 및 협업에 대한 문서는 [wiki](https://github.com/devFancy/payhere/wiki)를 참고해 주시면 감사하겠습니다 😌.

## 목차

- [기능 요구사항](#기능-요구사항)
- [개발 환경](#개발-환경)
- [빌드 및 실행](#빌드-및-실행하기)
- [패키지 구조](#패키지-구조)
- [프로젝트를 통한 기술적 성장](#프로젝트를-통한-기술적-성장)
- [느낀 점](#느낀-점)

## 기능 요구사항

1. 회원 가입
   - [x] 사장님이 시스템이 휴대폰번호와 비밀번호 입력을 통해서 회원가입을 하는 API 기능 구현

2. 로그인, 로그아웃
   - [x] 회원가입한 사장님이 로그인과 로그아웃을 하는 API 기능 구현 (JWT 토큰을 발행해서 인증을 제어하는 방식)

3. 로그인한 사장님은 상품 관련 아래의 행동을 할 수 있어야 함
   - [x] 상품을 등록하는 API 기능 구현
   - [x] 상품의 속성을 부분 수정할 수 있는 API 기능 구현
   - [x] 상품을 삭제하는 API 기능 구현
   - [x] 등록한 상품의 리스트를 조회하는 API 기능 구현
   - [x] 등록한 상품의 상세 내력을 조회하는 API 기능 구현
   - [x] 상품 이름을 기반으로 검색하는 API 기능 구현(like 검색, 초성 검색)

4. 로그인하지 않는 사장님은 상품 관련 API에 대한 접근 제한 처리
   - [x] 로그인(인증된)한 사장님만 상품 관련 API 처리할 수 있도록 함

### 결과 예시

#### 상품을 등록하는 API

- 예시) 카페 라떼 - 성공한 경우

- Request: `http://localhost:8080/products/new`

- Response:

    ```json
    {
        "meta": {
            "code": 201,
            "message": "Created"
        },
        "data": {
            "id": 1,
            "productCategory": "HANDMADE",
            "price": 3000,
            "cost": 1000,
            "name": "카페 라떼",
            "description": "장인이 직접 제조하는 카페 라떼",
            "barcode": "9788966 2841213",
            "expirationDate": "2024-12-31T23:59:00",
            "productSize": "SMALL"
        }
    }
    ```

- 예시) 카페 라떼 - 실패한 경우 (가격을 음수로 입력한 경우)

- Response:

    ```json
    {
        "meta": {
            "code": 400,
            "message": "상품 가격은 0원 이상이어야 합니다."
        },
        "data": null
    }
    ```

#### 상품 이름을 기반으로 검색하는 API(키워드, 초성)

- 예시) 카페 라떼 -> 검색 가능한 키워드: 카페 라떼, 카페, 라떼, ㅋㅍ, ㄹㄸ

- Request : `http://localhost:8080/products/search?query=ㄹㄸ&page=0&size=5`

- Response

    ```json
    {
      "meta": {
        "code": 200,
        "message": "OK"
      },
      "data": {
        "products": [
          {
            "id": 1,
            "price": 3000,
            "name": "카페 라떼",
            "expirationDate": "2024-12-31T23:59:00",
            "productSize": "SMALL",
            "createdAt": "2024-02-18T17:29:36.703934"
          }
        ]
      }
    }
    ```

## 개발 환경

- 기본 환경

    - IDE: IntelliJ IDEA Ultimate Edition

    - OS: MacOS (Apple M1 Pro)

    - Git

- Backend

    - `Language`| Java 11

    - `Framework` | Spring Boot 2.7.1, Spring MVC 5.3.2

    - `ORM` | Spring Data JPA 2.7.1, JPA Hibernate 5.6.1

    - `Database` | H2 2.1.2, MySQL 5.7

    - `Build Tool` | Gradle 7.4.1

    - `Jwt` | Jwt 0.11.5

    - `Test` | Junit5, Mockito 4.5.1

- Infra

    - `CI/CD` | Docker

![](/docs/image/docs-payhere-tech-stack.png)

## 빌드 및 실행하기

### 터미널 환경

- Git, Java는 설치되어 있다고 가정합니다.

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
    - 해결방안) 최상단에 있는 프로젝트명(Payhere)과 `settings.gralde` 파일의 rootProject.name (Payhere)을 일치시킵니다.

- payhere 패키지 - `build.gradle` 파일 클릭 후 우측 상단에 `Gradle` - `Reload All Gradle Projects` 클릭합니다. -> `BUILD SUCCESSFUL` 이라는
  결과가 뜨게 됩니다.

  ![](/docs/image/docs-build-gralde.png)

- payhere 패키지 - src/main/java 에서 `PayhereApplication` 클래스에서 `Run` 버튼 클릭합니다. 그러면 아래와 같은 결과가 나오게 됩니다.

  ![](/docs/image/docs-payhereapplication-run.png)

- 접속(Base) Url: `http://localhost:8080`

## 패키지 구조

이 프로젝트는 **기능별**로 구성된 패키지 구조로 가지고 있습니다.

- `auth`(인증/인가), `owner`(사장님/회원), `product`(상품), 그리고 전역적으로 사용되는 요소를 담당하는 `global`로 나뉩니다.

- 각 기능별 패키지 안에는 `application`, `domain`, `dto`, `exception`, `presentation`의 하위 구조로 세분화되어 있습니다.

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

## 프로젝트를 통한 기술적 성장

* 지속 성장 가능한 소프트웨어를 만들기 위해 **`Business Logic` 과 `Layer`** 에 대해 고민하며 개발했습니다.

* 테스트 케이스를 BDD 스타일로 작성할 때 **`BDDMockito` 문법을 사용하는 방법**에 대해 알게 됐습니다.

* 모든 API 엔드포인트에 일관된 응답 구조를 제공하기 위해 `custom response json` 형식으로 반환하는 방법에 대해 알게 됐습니다.

* 상품 이름을 기반으로 검색할 때 **초성 검색**을 지원할 수 있는 방법에 대해 알게 됐습니다.

* 비밀번호를 안전하게 보관하기 위해 **해싱**으로 처리하는 방법에 대해 알게 됐습니다.

## 느낀 점

* 해당 과제를 진행하면서 백엔드 코드의 일관성을 유지하고 팀 간 협업을 원활하게 하기 위해 `코드 컨벤션`과 `GitHub Flow`를 철저히 준수하며 작업했습니다.

    또한 동료 개발자가 쉽게 이해하고 협업할 수 있도록 Commit Convention부터 Issue와 Pull request 부분을 명확하고 간결하게 작성하려고 노력했습니다.

    요구 사항에 따라 개발을 순차적으로 진행하면서, 동료 개발자들이 **프로젝트의 진행 상황**을 쉽게 파악할 수 있도록 [Milestones](https://github.com/devFancy/payhere/milestone/1)에 기록했습니다.

    (협업을 위한 문서는 [wiki](https://github.com/devFancy/payhere/wiki)를 통해 확인하실 수 있습니다)

* 과제를 진행하면서 가장 아쉬웠던 점은 프로덕션 코드의 안정성과 신뢰성을 더욱 강화하기 위해 테스트 코드 작성에 많은 시간을 할애했음에도 불구하고, 모든 코드를 완벽하게 커버하지 못했다는 것입니다.

    이 부분에 대해서는 만약 시간이 더 있다면, Jacoco를 사용하여 코드 커버리지를 최소 80%까지 끌어올려 프로덕션 코드의 안정성과 신뢰성을 보다 확실하게 보장하고자 했습니다.

* 그럼에도 불구하고 이번 과제를 통해 새롭게 습득한 지식으로 과제를 즐겁게 진행하였습니다.

    이러한 학습 경험은 아쉬움에도 불구하고, 이 과제를 저에게 **가치 있고 의미 있는 도전**으로 만들어 주었습니다.

    감사합니다.