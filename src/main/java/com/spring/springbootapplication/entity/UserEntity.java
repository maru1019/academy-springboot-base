package com.spring.springbootapplication.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class UserEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
  @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", allocationSize = 1)
  @Column(name = "id")
  private Integer id;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "email", nullable = false, length = 255)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "biography", nullable = false, length = 200)
  private String biography;

  @Column(name = "image_url", nullable = false, length = 255)
  private String image_url;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date createdAt;

  @Column(name = "updated_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @UpdateTimestamp
  private Date updatedAt;

}
