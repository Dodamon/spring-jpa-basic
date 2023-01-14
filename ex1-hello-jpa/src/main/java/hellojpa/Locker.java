package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    /*
    일대일 양방향 Mapping
    Member가 주인
     */
    @OneToOne(mappedBy = "locker")
    private Member member;
}
