package com.codingtest.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"id", "name"})
})
@Getter
@Setter
@NoArgsConstructor
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name="pvalue")
    private String value;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Attribute(String name, String value, Client client) {
        this.name = name;
        this.value = value;
        this.client = client;
    }


}
