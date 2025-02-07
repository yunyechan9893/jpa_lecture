### 쿼리 방식 선택 권장 순서
1. 우선 엔티티를 DTO로 변환하는 방법을 선택
2. 필요하면 FetchJoin()으로 성능을 최적화 => 대부분 성능 이슈 해결
3. 그래도 안되면 DTO로 직접 조회하는 방법 선택
4. 최후의 방법은 JPA가 제공하는 네이티브 SQL이나 스프링 JDBC Template를 사용해서 SQL을 직접 사용

### 주의사항
1. 1:N 관계에서 FetchJoin 시 페이징 처리할 때 DB 데이터를 전부 가져온 후 메모리에서 페이징 처리한다. 절대 사용 금지
2. 1:N, N:M 관계에선 FetchJoin 금지
   - 어떤 기준으로 데이터를 가져와야할지 내부적으로 정하지 못해 부정합 가능성 증가
3. default_batch_fetch_size는 100~1000으로 설정하는 것이 바람직함
   - 1000개 이상부터는 지원을 안하는 데이터베이스가 있음

### OSIV
- Hibernate에서 Open Session In View 였지만, JPA는 Open EntityManager In View로 사용
- 관례상 OSIV로 불림
- 애플리케이션 값 설정 방법 
  1. spring.jpa.open-in-view : true
     - 이 설정으로 인해 영속성 컨텍스트가 세션이 끝날 때까지 살아있게 되므로 Controller에서 Mapping 할 때 Lazy 로딩이 가능해짐
     - 단점은 DB 커넥션을 오랫동안 사용하기 때문에 장애로 발전할 수 있음
     - ![Image](https://github.com/user-attachments/assets/236bfbdb-84e6-4af1-83b7-5ca6ec358190)
  2. spring.jpa.open-in-view : false
     - ![Image](https://github.com/user-attachments/assets/ab2f6d83-5ca0-42af-b8c5-c5eaa23a0ddc)
- 즉, 고객 서비스는 트래픽 양이 많기에 OSIV를 끄고, 어드민 같이 트래픽이 적은 곳에선 OSIV를 켬
