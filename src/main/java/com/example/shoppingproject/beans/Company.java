package com.example.shoppingproject.beans;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "companies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String email, userName, password;
    private boolean isVerified;
    @Column(unique = true)
    private String companyName;
    @OneToMany (mappedBy = "company")
    private List<Product> productList;

}
