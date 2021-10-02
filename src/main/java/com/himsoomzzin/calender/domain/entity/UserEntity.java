package com.himsoomzzin.calender.domain.entity;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usersId;

    private String pwd;

    private String userName;

    private String phoneNumber;

    private String email;

    @OneToMany(mappedBy = "userEntity")
    private List<TeamUserEntity> userGroup;

    private String img;

    private String nickName;
}
