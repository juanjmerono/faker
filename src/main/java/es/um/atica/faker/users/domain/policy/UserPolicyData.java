package es.um.atica.faker.users.domain.policy;

public class UserPolicyData {
    private int age;
    private UserPolicyData(int age) { this.age = age; }
    public static UserPolicyData of(int age) {
        return new UserPolicyData(age);
    }
    public int getAge() { return age; }
}
