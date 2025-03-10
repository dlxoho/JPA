package jpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Member {
  // 기본키 매핑
  // @Id - 직접할당
  // @GeneratedValue - 자동할당
  // - Identity : 기본키 생성을 데이터베이스에 위임 (Auto_increment)
  //   일반적으로 entityManager 의 persist (생성) 영속상태에서 하나의 작업단위 (트랜잭션) 이 끝나는 (commit) 단계에서 insert SQL 이 실행되지만 Identity 전략에선 persist 와 함께 Insert SQL이 실행.
  // - Sequence : 데이터베이스의 시퀀스를 사용, 시퀀스 객체에서 값을 획득한 후 엔티티에 할당
  //   시퀀스 객체란 별도의 테이블이 아닌 데이터베이스에서 제공하는 특별한 객체로 일련번호를 생성
  //   Identity 전략과 차이점은 Identity 전략은 db에 저장된 후 ID 값을 가져오지만 시퀀스 전략은 저장 전에 시퀀스 객체에서 값을 가져옴으로써 ID 를 미리 알수있고 JDBC 배치 기능 활용이 가능하다.
  //   call next value 를 여러번 반복하면 성능 이슈가 발생할 수 있다. 이것을 allocationSize 를 사용해서 한번에 호출할때 50개씩 호출하여 가져와놓고 메모리에서 호출
  // - Table : 키 생성 전용 테이블을 만들어서 데이터베이스 시퀀스 전략을 흉내. 모든 데이터베이스에서 적용이 가능하지만 성능이 단점
  // - Auto : 자동으로 설정 db에 따라서
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // DATABASE의 컬럼과 매핑
  // 1. insertable, updatable - 등록 변경 가능 여부
  // 2. nullable - false를 주게되면 not null, true 는 nullable
  // 3. unique - 유니크 제약조건 ( 일반적으로 테이블에 건다. 필드에 걸게되면 이름을 찾기가 어려움 )
  // 4. columnDefinition - 데이터베이스의 컬럼정보를 직접 정의한다. ( ex. varchar(100) default 'Empty' )
  // 5. length - 길이제한
  // 6. BigDecimal - 정밀한 소수점을 다룰때 사용
  @Column(name = "test")
  private String name;

  private Integer age;

  // enum 타입을 사용
  // ORDINAL - 순서를 저장 (integer)
  //  - 순서를 가지고 관리하게되면 운영상에서 위험하다. ( 추가/삭제하게 되는경우 순서로 관리하는것은 위험 )
  // STRING - 이름을 저장 (string)
  @Enumerated(EnumType.STRING)
  private RoleType roleType;

  // 날짜 타입
  // 최신 하이버네이트에선 LocalDate, LocalDateTime 으로 Temporal 대체가능
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;
  private LocalDate newCreatedDate;
  private LocalDateTime newUpdatedDate;

  // large object 의 준말, varchar 를 넘어서는 큰 데이터를 저장하기위한 가변길이 데이터 유형
  // 매핑하는 필드의 타입이 문자열(String) 이면 clob, 나머지(이미지,오디오,비디 같은 이진 데이터)는 blob(binary large object) 으로 매핑
  @Lob
  private String description;

  // DB와 관련없이 사용하고싶을때, 메모리에서만 사용하고싶을때? 사용
  @Transient
  private int temp;

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public RoleType getRoleType() {
    return roleType;
  }

  public void setRoleType(RoleType roleType) {
    this.roleType = roleType;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public Date getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Date lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public LocalDate getNewCreatedDate() {
    return newCreatedDate;
  }

  public void setNewCreatedDate(LocalDate newCreatedDate) {
    this.newCreatedDate = newCreatedDate;
  }

  public LocalDateTime getNewUpdatedDate() {
    return newUpdatedDate;
  }

  public void setNewUpdatedDate(LocalDateTime newUpdatedDate) {
    this.newUpdatedDate = newUpdatedDate;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
