package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Collection;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamA.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);


            em.flush();
            em.clear();

            //String query = "select m From Member m join fetch m.team";
            String query = "select t From Team t join fetch t.members";

            List<Team> result = em.createQuery(query, Team.class)
                            .getResultList();

            for (Team team : result) {
                System.out.println("team = " + team.getName() + " | members = " + team.getMembers().size());
                //회원1, 팀A(SQL)
                //회원2, 팀A(영속성)
                //회원3, 팀B(SQL)
                //쿼리가 총 3번 나간다
                //회원 100명 -> N + 1 ( 1: 회원을 가져오기 윈한 쿼리 , 그 결과로 team에 접근하기 위해 N번을 퀴리를 날리는 것)
                //join fetch 하게되면 -> 1번
            }
            
            tx.commit();

        } catch ( Exception e ) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
