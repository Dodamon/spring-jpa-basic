package jpabook.jpashop;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Movie;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        // application 로딩 시점에 한개 만들어 져야한다
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // 하나의 트렌잭션 단위 마다 만들어져야한다 == ? DataBase Connection을 받았다고 생각하면 쉽다
        EntityManager em = emf.createEntityManager();
        // 트랜잭션 시작
        // jpa에서는 트렌잭션 단위가 중요 - 데이터 변경단위
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            // 비지니스 로직
            Book book = new Book();
            book.setName("A");
            book.setAuthor("B");

            em.persist(book);

            tx.commit();

        } catch ( Exception e ) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
