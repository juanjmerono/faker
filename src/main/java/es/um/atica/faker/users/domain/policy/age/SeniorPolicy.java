package es.um.atica.faker.users.domain.policy.age;

import java.util.Arrays;
import java.util.List;

import es.um.atica.faker.users.domain.model.UserPreference;
import es.um.atica.faker.users.domain.policy.UserPolicyData;

public class SeniorPolicy extends YoungPolicy {

    private static final int MIN_SENIOR_AGE = 29;

    @Override
    public boolean isApplicable(UserPolicyData data) {
        return data.getAge() >= MIN_SENIOR_AGE;
    }

    @Override
    public List<UserPreference> applyDefaultPreferences(UserPolicyData data) {
        return Arrays.asList(
            UserPreference.of("twitch", likeInvProportional(data.getAge())),
            UserPreference.of("youtube", likeProportional(data.getAge())),
            UserPreference.of("twitter", likeProportional(data.getAge())),
            UserPreference.of("facebook", likeProportional(data.getAge())),
            UserPreference.of("tiktok", 0) // Do no like at all
        );
    }
    
}
