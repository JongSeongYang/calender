package com.himsoomzzin.calender.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String pwd;

    private String userName;

    private String phoneNumber;

    private String email;

    private String userGroup;

    private String img;

    private String nickName;
}