package jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    // 로딩시점에 딱 한개만 있어야한다. ( DB당 1개 )
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    // 하나의 작업단위마다 꼭 선언해서 사용해줘야한다
    // 쓰레드간 공유하면 안된다 ( 사용하고 바로 버려줘야한다 )
    // - 쓰레드 안정성 문제 : 한개의 entity Manager를 가지고 두개의 엔티티를 update 한다고 가정할때, 마지막요소만 반영된다던지 장애가 발생
    // - 영속성 컨텍스트의 상태관리 : 영속성 컨텍스트는 엔테티를 캐싱하는데 여러 쓰레드가 캐시를 공유하면 하나의 쓰레드가 예전(다른) 데이터를 보게 될 수 있음.
    //   - 영속성 컨텍스트란? JPA에서 엔티티 객체를 관리하는 일종의 캐시(메모리저장소), DB와 엔티티 객체 사이에 존재
    //     Map 형태의 컬랙션을 사용 ( Map<Key,Value> ) 구조
    //   - 1차 캐싱의 장점 : db에서 조회된 데이터를 영속성 컨텍스트에 저장(Key 가 ID값)하고 같은 데이터를 다시 조회하면 db에 쿼리를 보내지않고 메모리에서 가져옴(1차 캐싱에서 조회되지않으면 db에서 가져와 영속상태로 관리)
    //   - 변경감지(dirty checking) : 엔티티 객체를 수정하면 변경사항을 감지하여 트랜잭션이 끝날때, db에 반영함
    //   실행했을시 처음 상태(값)을 스냅샷으로 떠놓게된다. commit 시점에 스냅샷과 현재값을 비교하게 된다.
    //   - 쓰기지연(buffering write) : insert update delete 쿼리를 한번에 모았다가 트랜잭션이 끝날때 한번에 batch 처리를 함
    //   - 동일성보장 : 같은 트랜잭션 안에선 같은 데이터를 조회하면 같은 객체를 반환
    // - 리소스관리의 문제 : 데이터베이스 연결과 메모리의 누수
    EntityManager em = emf.createEntityManager();

    // flush (플러시)
    // - 영속성 컨텍스트의 변경내용을 db에 반영
    // - flush 가 발생하게되면? 변경감지(dirty checking), 쓰기지연 SQL저장소에 등록 및 쿼리를 한번에 db에 전송
    // - flush 하는 방법 : em.flush(), 트랜잭션 커밋, jpql 쿼리 실행
    // => 결론 : 영속성 컨텍스트를 비우지않음. 영속성 컨텍스트의 변경 내용을 데이터베이스에 동기화

    // 단순 조회가 아닌이상, 데이터의 변경은 모두 트랜잭션 안에서 실행시켜줘야한다.
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    try {
      // create
//      Member member = new Member();
//      member.setId(1L);
//      member.setName("taeho");
//      em.persist(member);

      // update
//      Member findMember = em.find(Member.class, 1L); // detail
//      findMember.setName("hello");

      // 준영속상태 (영속성 컨텍스트에서 제거)
//    em.detach(findMember);
//    em.clear(); 영속 컨텍스트를 비워주기
//    em.close(); 영속 컨텍스트 종료

      // all list ( jpql )
      // select * 이 아니라 m 이유는 테이블이 아니라 Member(엔티티) 객체를 대상으로 조회하는것이기 때문
//      List<Member> result = em.createQuery("select m from Member as m", Member.class)
//        .setFirstResult(1) // limit 0,10
//        .setMaxResults(10)
//        .getResultList();
//
//      for (Member member : result) {
//        System.out.println("member = " + member.getName());
//      }

      // 연관관계 저장
      Team team = new Team("test");
      em.persist(team);

      Member member = new Member();
      member.setName("test");
      member.setTeam(team);
      em.persist(member);

      // 만약 영속콘텍스트의 1차 캐싱 데이터가 아니라 db에서 가져오고싶으면
      em.flush();// 영속콘텍스트안에 있는것들 db 동기화
      em.close(); // 영속콘텍스트 비워주기

      Member findMember = em.find(Member.class, member.getId());
      Team findTeam = findMember.getTeam();
      List<Member> members = findMember.getTeam().getMembers();

      // # 프록시
      // em.find() : 데이터베이스를 통해서 실제 엔티티 객체 조회
      // em.getReference() : 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체를 조회
      // 특징
      // - 실제 클래스(원본 엔티티)를 상속받아서 만들어진다
      // - 실제 클래스와 겉 모양은 같다
      // - 사용자입장에선 진짜 객체인지 가짜 프록시 객체인지 구분하지않아도 된다.
      // - 프록시 객체는 실제 객체의 참조(Target) 를 보관한다
      // - 프록시 객체는 처음 사용할 때 한번만 초기화 (영속성컨텐스트 -> DB 조회 -> Entity 객체생성)
      // - 프록시 객체가 초기화 된다고하고 실제 엔티티로 바뀌는 것이 아니라 실제 엔티티에 접근이 가능할 뿐(타입이 다르기에 == 비교가 아닌 instance of 를 사용)
      // - 만약 영속성컨텍스트 안에 엔티티가 이미 존재하는 경우에는 getReference() 를 사용해도 실제 엔티티를 반환한다. => 하나의 트랙잭션 안에서는 같음을 보장해준다.
      // - 위와 반대로 프록시로 조회하고 find() 로 조회하더라도 프록시로 조회된다.
      // - 준영속 상태(영속성컨텍스트에서 관리하지않는)경우 프록시를 가져오지 못함
      Member proxyMember = new Member();
      proxyMember.setName("tester");
      em.persist(proxyMember);

      em.flush();
      em.clear();

      Member proxyFindMember = em.find(Member.class, proxyMember.getId());
      Member proxyFindMember2 = em.getReference(Member.class, proxyMember.getId());

      System.out.println("proxyFindMember = " + proxyFindMember);

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
      throw new RuntimeException(e);
    } finally {
      em.close();
    }
    emf.close();
  }

}