package final_backend.Member.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String uid;

    @Column(nullable = false)
    private String name;
    private String email;
    private String picture;
    private String role = "ROLE_USER";
    private String lover;
}