package es.um.atica.faker.users.domain.policy;

import java.util.List;

import es.um.atica.faker.users.domain.model.UserPreference;

public interface UserPolicy {
    // When a policy is applicable
    public boolean isApplicable(UserPolicyData data);
    // The policy rule
    public List<UserPreference> applyDefaultPreferences(UserPolicyData data);
}
