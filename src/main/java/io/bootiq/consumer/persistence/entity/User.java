package io.bootiq.consumer.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Entity
@Table(name = "SUSER")
public class User {

    public User(String name) {
        this.name = name;
    }

    @Getter
    @Setter
    @Column(name = "USER_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name = "USER_GUID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID guid;

    @Getter
    @Setter
    @Column(name = "USER_NAME")
    private String name;

}
