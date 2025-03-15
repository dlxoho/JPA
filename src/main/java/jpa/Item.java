package jpa;

import javax.persistence.*;

@Entity
// 조인전략
// 다중 테이블 InheritanceType.JOINED
// 단일 테이블 InheritanceType.SINGLE_TABLE
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// DTYPE - 구분자
@DiscriminatorColumn
public class Item {
  @Id @GeneratedValue
  private Long id;

  private String name;
  private int price;

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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }
}
