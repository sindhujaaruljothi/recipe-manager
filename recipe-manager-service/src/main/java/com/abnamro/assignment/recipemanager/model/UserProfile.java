package com.abnamro.assignment.recipemanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProfile {
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name="passcode")
    private String passcode;

}
