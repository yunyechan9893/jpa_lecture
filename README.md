## 정리

1. orderBy

- nulls first : 정렬 시 null을 1순위로 분류
    - member.name.desc().nullsFirst()
- nulls last : 정렬 시 null을 마지막 순위로 분류
    - member.name.desc().nullsLast()

2. fetchResults
    - where절은 필터링하지 않고 그대로 카운트 적용. 무한스크롤에서 적용하기 어려울 듯
3. 세타 조인
    - 관계가 없는 컬럼끼리 조인할 때 사용
    ```java
   List<Member> members = jpaQueryFactory.select(member)
        .from(member, team)
        .where(member.name.eq(team.name))
        .fetch();
   ```

4. On절
    - 내부 조인으로 사용 시 Where절과 동일함
    - 외부 조인으로 활용
5. from절 서브쿼리
   - 하이버네이트에서 지원하지 않기에, QueryDSL에서도 당연히 지원하지 않음
   - 해결 방법
     - 서브쿼리를 Join으로 변경
     - 애플리케이션에서 두번에 걸쳐 쿼리 요청
     - native SQL을 사용
6. stringValue()
    - 결과 타입을 스트링으로 변환
    - enum 처리할 때 많이 사용