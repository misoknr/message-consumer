package io.bootiq.consumer.persistence.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SUSER")
public class User {

    @Getter
    @Setter
    @Column(name = "USER_ID")
    @Id
    private Long id;

    @Getter
    @Setter
    @Column(name = "USER_GUID")
    private String guid;

    @Getter
    @Setter
    @Column(name = "USER_NAME")
    private String name;

}
