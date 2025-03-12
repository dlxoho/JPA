package jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
  @Id
  @GeneratedValue
  @Column(name = "TEAM_ID")
  private Long id;
  private String name;

  // Member Entity 의 필드명을 mappedBy 에 적어준다.
  @OneToMany(mappedBy = "team")
  private List<Member> members = new ArrayList<>();

  // setter 대신 생성사 사용
  public Team (String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public List<Member> getMembers() {
    return members;
  }

  public void setMembers(List<Member> members) {
    this.members = members;
  }
}
