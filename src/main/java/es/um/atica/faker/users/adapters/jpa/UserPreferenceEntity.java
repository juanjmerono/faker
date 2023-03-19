package es.um.atica.faker.users.adapters.jpa;

import javax.persistence.Embeddable;

import es.um.atica.faker.users.domain.model.UserPreference;

@Embeddable
public class UserPreferenceEntity {
    
    private String name;
    private int quantity;

    public UserPreferenceEntity() {}
    public UserPreferenceEntity(String name, int quantity) {
        this.name = name; this.quantity = quantity;
    }

    public static UserPreferenceEntity of(UserPreference pref) {
        return new UserPreferenceEntity(pref.getName(), pref.getQuantity());
    }

    public UserPreference toModel() {
        return UserPreference.of(this.name, this.quantity);
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

}
