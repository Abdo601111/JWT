package com.jwt.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    public  Role (String name){
        this.name=name;
    }

    public Role(int roleId) {
        this.id=roleId;
    }

    @Override
    public String toString() {
        return  name ;
    }
}
