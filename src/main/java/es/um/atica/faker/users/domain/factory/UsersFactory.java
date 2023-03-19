package es.um.atica.faker.users.domain.factory;

import java.util.List;
import java.util.stream.Collectors;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserId;
import es.um.atica.faker.users.domain.model.UserName;
import es.um.atica.faker.users.domain.model.UserPreference;
import es.um.atica.faker.users.domain.policy.UserPolicyData;
import es.um.atica.faker.users.domain.policy.UserPolicyList;

public class UsersFactory {

    public static User createUser(UserId id, UserName name, UserAge age, List<UserPreference> prefs) {
        return User.of(id, name, age, prefs);
    }

    public static User createUserWithDefaultPreferences(UserId id, UserName name, UserAge age) {
        // Apply Policy Criteria
        UserPolicyData policyData = UserPolicyData.of(age.getValue());
        List<UserPreference> prefs = UserPolicyList
            .applicablePolicies(policyData)
            .stream()
            .map(p->p.applyDefaultPreferences(policyData))
            .flatMap(List::stream)
            .collect(Collectors.toList());
        return User.of(id, name, age, prefs);
    }

}
