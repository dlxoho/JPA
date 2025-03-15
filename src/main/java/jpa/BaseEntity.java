package jpa;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

// 공통으로 사용되는 컬럼을 모아놓고 다른 엔티티가 상속받아 사용할수있게한다
// 이때 DB는 각 테이블마다 공통되는 컬럼은 각각 가지고있는것!

// 엔티티 X, 테이블과 매핑되지않는다.
// 조회/검색이 불가능하다
// 직접 생성해서 사용 할 일이없기에 추상클래스로 사용하는 것을 권장.
// @Entity는 속성관계 매핑, @MappedSuperClass 는 속성만 상속받을때 사용하는 것의 차이가 존재한다.
@MappedSuperclass
public abstract class BaseEntity {
  private String createdBy;
  private String lastModifiedBy;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }
}
