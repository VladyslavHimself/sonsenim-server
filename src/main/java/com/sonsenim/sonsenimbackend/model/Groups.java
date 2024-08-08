package com.sonsenim.sonsenimbackend.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


// TODO: Change name from plural form to single
@Entity
@Table(name = "groups")
public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "group_name", nullable = false, length = 128)
    private String groupName;

    @ManyToOne(optional = false)
    private LocalUser localUser;

    @OneToMany(mappedBy = "groups", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Deck> decks = new ArrayList<>();

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public LocalUser getLocalUser() {
        return localUser;
    }

    public void setLocalUser(LocalUser localUser) {
        this.localUser = localUser;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}