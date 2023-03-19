package es.um.atica.faker.users.adapters.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import es.um.atica.faker.users.domain.factory.UsersFactory;
import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;

@Entity
@Table(name="USERS",schema="FAKER")
public class UserEntity {
    private String id;
    private String name;
    private int age;
    private List<UserPreferenceEntity> preferences;

    private UserEntity() {}
    private UserEntity(String id, String name,int age,List<UserPreferenceEntity> preferences) {
        this.id = id; this.name = name; this.age = age; this.preferences = preferences;
    }

    public static UserEntity of (User usr) {
        return new UserEntity(
            usr.getId().getValue(),
            usr.getName().getValue(),
            usr.getAge().getValue(),
            usr.getPreferences().stream().map(UserPreferenceEntity::of).collect(Collectors.toList()));
    }

    public User toModel() {
        return UsersFactory.createUser(
            UserId.of(this.id), 
            UserName.of(this.name),
            UserAge.of(this.age),
            preferences.stream().map(UserPreferenceEntity::toModel).collect(Collectors.toList()));
    }

    @Id
    @Column(name = "identificador")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Column(name = "nombre")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(name = "edad")
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="USERS_PREFS", schema = "FAKER", joinColumns = @JoinColumn(name = "idusuario"))
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "nombre")),
            @AttributeOverride(name = "quantity", column = @Column(name = "cantidad"))
    })    
    public List<UserPreferenceEntity> getPreferences() { return preferences; }
    public void setPreferences(List<UserPreferenceEntity> preferences) { this.preferences = preferences; }

}
