package jpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
// 부모 테이블에 DTYPE 의 값을 어떤것으로 넣을것인가
// 예로 넣은것처럼 A로 설정하면 Item 의 DTYPE 컬럼에 A 라는 값이 저장된다.
@DiscriminatorValue("A")
public class Album extends Item{
  private String artist;
}
