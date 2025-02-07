- https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

### 벌크 업데이트
- 더티 체킹은 단일 업데이트이므로 다량의 업데이트 시에는 벌크 업데이트 사용
- 벌크 업데이트는 영속성 컨텍스트 반영을 해주지 않기 때문에 사용 후 영속성 컨텍스트를 Clear 해줘야함
  - 만약, 해주지 않았다면 이후 영속성 컨텍스트에 들어가 있는 객체의 값은 변경되지 않아, 이후 실행되는 로직에 데이터 정합성 문제 발생
- @Modify(clearAutomatically = true) 로 설정해주면, 업데이트 후 자동으로 영속성을 비워 줌
- 여담으로 JPQL 사용 시 영속성 컨텍스트에 반영되지 않으니 항상 유의하면서 사용

### Entity Graph
- JPARepository에서 JPQL 없이 Fetch Join을 하고 싶은 경우 사용
- @EntityGraph(attributePaths = {"team"})

### hint(readOnly)
- Entity를 가져올 때 100% 조회용으로만 사용할 때 사용
  - 만약, 조회용이 아니라면 영속성 컨텍스트에 원본과 복제된 엔티티 두개가 생성돼 더티 체킹도 되고 메모리 낭비도 발생하기 때문에 ReadOnly 선언을 해줌
- @QueryHint(name = "org.hibernate.readOnly", value = "true")

### Pageable
- ![Image](https://github.com/user-attachments/assets/480798f3-a1eb-47a5-a93a-64a0c9b31e05)

### save
- JPARepository에 save 메서드는 아이디가 null 여부에 따라 persist나 merge를 사용함
- 이로 인해 id가 자동으로 채번되는 것이 아니면, 인위적으로 아이디를 넣어줘야하는데 이때 merge를 사용하기에 문제 발생
- 이럴 경우 Persistable을 상속받고 isNew()를 재정의
  - createdDate로 새로운 객체 여부를 판단해도 좋음
  ```Java
  @Override
  public boolean isNew() {
    return createdDate == null;
  }
  ```