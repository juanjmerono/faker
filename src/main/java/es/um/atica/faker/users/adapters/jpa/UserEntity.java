package es.um.atica.faker.users.adapters.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;

@Entity
@Table(name="USERS",schema="FAKER")
public class UserEntity {
    private String id;
    private String name;

    private UserEntity() {}
    private UserEntity(String id, String name) {
        this.id = id; this.name = name;
    }

    public static UserEntity of (User usr) {
        return new UserEntity(usr.getId().getValue(),usr.getName().getValue());
    }

    public User toModel() {
        return User.of(UserId.of(this.id), UserName.of(this.name));
    }

    @Id
    @Column(name = "identificador")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @Column(name = "nombre")
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
