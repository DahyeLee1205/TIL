# Transaction

자세한 내용은 [notion 링크](https://continuous-bagel-d02.notion.site/Transaction-138c254b11b74a258c20f53848b99876) 참고

##  Partially Committed vs Committed
- COMMIT 요청이 들어오면, Partially Committed 상태가 된다.
- Partially Committed  :  COMMIT 요청이 들어왔을 때
- Committed  :  COMMIT 을 정상적으로 완료한 상태


## 병행제어 
여러 개의 트랜잭션이 실행될 때, 트랜잭션들이 DB의 일관성을 파괴하지 않고 다른 트랜잭션에 영향을 주지 않으면서 트랜잭션을 제어하는 것을 의미

## 병행제어 기법

**로킹(Locking)**

트랜잭션이 어떤 데이터에 접근하고자 할 때 로킹을 수행하며, 한 트랜잭션만이 로킹 해제 가능.

트랜잭션은 로킹이 된 데이터에 대해서만 연산을 수행할 수 있으며, 필드/레코드/파일/DB 모두 로킹의 단위 및 대상이 될 수 있음.

- 로킹 단위가 크면: 관리 용이(로킹 오버헤드 감소) but 동시성 수준 감소
- 로킹 단위가 작으면: 동시성 수준 증가 but 관리 어려움(로킹 오버헤드 증가)

**2단계 로킹 규약(Two-Phase Locking Protocol)**

Lock과 Unlock이 동시에 이루어지면 일관성이 보장되지 않으므로, Lock만 가능한 단계와 Unlock만 가능한 단계를 구분하여 직렬가능성을 보장한다.
교착상태가 발생할 수도 있다.

- 확장단계: 트랜잭션이 Lock 가능, Unlock 불가능
- 축소단계: 트랜잭션이 Unlock 가능, Lock 불가능
- ex) T1: write(A) read(B), T2: read(B) write(A) ⇒ dead lock 발생


**로킹의 종류**

1. **S-lock** (공유잠금)
    - 공유잠금을 설정한 트랜잭션은 데이터 항목에 대해 읽기 연산(read)만 가능
    - 하나의 데이터 항목에 대해 여러 개의 공유잠금(S-lock) 가능
    - 다른 트랜잭션도 읽기 연산(read)만을 실행
2. **X-lock** (배타잠금)
    - 배타잠금을 설정한 트랜잭션은 데이터 항목에 대해 읽기 연산(read)과 쓰기 연산(write) 모두 가능
    - 하나의 데이터 항목에 대해서는 하나의 배타잠금(X-lock)만 가능
    - 다른 트랜잭션은 읽기 연산(read)과 쓰기 연산(write) 모두 불가능


참고 URL

[https://velog.io/@shasha/Database-트랜잭션-정리](https://velog.io/@shasha/Database-%ED%8A%B8%EB%9E%9C%EC%9E%AD%EC%85%98-%EC%A0%95%EB%A6%AC)




