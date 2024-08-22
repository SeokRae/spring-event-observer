# 스프링 Observer Pattern에 대한 개념

해당 개념은 비동기 이벤트를 처리 하는 방법으로 찾아보다가 접하게 되었다.

일반적으로 A 서버가 B 서버에 요청을 보내면, 이에 대한 응답을 동기적 방식으로 응답받아 처리하게 된다.

여기서 A 서버가 B 서버에 보내는 요청 및 내부 시스템 처리가 동기적인 처리가 필요하지 않은 경우, 비동기적인 요청이라는 선택지를 떠올려볼 수 있다. 

이러한 비동기적인 요청을 처리하는 방법 중 하나가 Observer Pattern이다.

## 이벤트(Event)

- 이벤트는 시스템 내에서 발생한 `특정 상황`을 나타낸다.
- 이벤트는 ApplicationEvent를 상속받아 정의되며, 이 클래스는 `인스턴스`가 `이벤트의 데이터(Payload)`를 포함하게 된다.

## 퍼블리셔(Publisher)

- 이벤트를 발생시키고 이를 전달하는 역할을 한다.
- 스프링에서는 ApplicationEventPublisher 인터페이스를 사용하여 이벤트를 퍼블리시 한다.

## 리스너(Listener)

- 퍼블리셔에 의해 발생된 이벤트를 수신하고 처리하는 역할을 한다.
- 스프링에서는 @EventListener 어노테이션을 사용하여 리스너 메서드를 정의할 수 있다.
- 리스너는 이벤트가 발생했을 때, 특정 작업을 수행하는 메서드로 구현된다.

## 컨텍스트(Context)

- 이벤트와 리스너는 동일한 애플리케이션 컨텍스트(ApplicationContext) 내에서 동작한다.
- 컨텍스트는 이벤트를 리스너에게 전달하는 중재자 역할을 한다.

## Observer Pattern 동작 방식

1. 이벤트 발생
2. 이벤트를 퍼블리셔에게 전달
3. 퍼블리셔는 이벤트를 컨텍스트에게 전달
4. 컨텍스트는 이벤트를 리스너에게 전달
5. 리스너는 이벤트를 수신하고 처리
6. 이벤트 처리 완료



- [Migrate custom Pub/Sub pattern to ApplicationEventPublisher](https://medium.com/@bgpark82/migrate-custom-pub-sub-pattern-to-applicationeventpublisher-e39fa2146e1d)