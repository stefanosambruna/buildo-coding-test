package com.codingtest.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true)
    private String clientServiceId;

    private String name;


    @OneToMany(mappedBy = "client", cascade=CascadeType.ALL, orphanRemoval = true)
    @MapKey(name="name")
    private Map<String,Attribute> attributes;

    public Client(String clientServiceId, String name) {
        this.clientServiceId = clientServiceId;
        this.name = name;
        this.attributes = new HashMap<>();
    }

    public Client(Long id, String clientServiceId, String name) {
        this.id = id;
        this.clientServiceId = clientServiceId;
        this.name = name;
    }

    public void addAttribute(Attribute attr) {
        if(this.attributes == null){
            this.attributes = new HashMap<>();
        }
        this.attributes.put(attr.getName(), attr);
    }
}
