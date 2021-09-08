package toyproject.toyshop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String password;
    private String email;
    private int age;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @Embedded
    private Address address;

    public Member() {
    }

    public Member(String name) {
        this.name = name;
    }

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Member(String name, String password, String email, Address address) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ReviewComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ItemReview> reviews = new ArrayList<>();
}
