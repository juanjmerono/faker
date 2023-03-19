package es.um.atica.faker.users.domain.policy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import es.um.atica.faker.users.domain.policy.age.SeniorPolicy;
import es.um.atica.faker.users.domain.policy.age.YoungPolicy;

public class UserPolicyList {
    
    private static List<UserPolicy> policies;

    static {
        policies = Arrays.asList(
            new SeniorPolicy(),
            new YoungPolicy()
        );
    }

    public static List<UserPolicy> applicablePolicies(UserPolicyData data) {
        return policies.stream().filter(p->p.isApplicable(data)).collect(Collectors.toList());
    }

}
