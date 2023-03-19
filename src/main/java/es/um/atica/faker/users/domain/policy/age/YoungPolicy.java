package es.um.atica.faker.users.domain.policy.age;

import java.util.Arrays;
import java.util.List;

import es.um.atica.faker.users.domain.model.UserAge;
import es.um.atica.faker.users.domain.model.UserPreference;
import es.um.atica.faker.users.domain.policy.UserPolicy;
import es.um.atica.faker.users.domain.policy.UserPolicyData;

public class YoungPolicy implements UserPolicy {

    private static final int MAX_YOUNG_AGE = 28;

    // OldRange = (OldMax - OldMin)  
    // NewRange = (NewMax - NewMin)  
    // NewValue = (((OldValue - OldMin) * NewRange) / OldRange) + NewMin
    protected int likeProportional(int age) {
        int oldRange = UserAge.MAX_AGE - UserAge.MIN_AGE;
        int newRange = UserPreference.MAX_RANGE - UserPreference.MIN_RANGE;
        return Math.round((((age - UserAge.MIN_AGE) * newRange) / oldRange) + UserPreference.MIN_RANGE);
    }

    protected int likeInvProportional(int age) {
        return UserPreference.MAX_RANGE - likeProportional(age);
    }

    @Override
    public boolean isApplicable(UserPolicyData data) {
        return data.getAge() <= MAX_YOUNG_AGE;
    }

    @Override
    public List<UserPreference> applyDefaultPreferences(UserPolicyData data) {
        return Arrays.asList(
            UserPreference.of("twitch", likeInvProportional(data.getAge())),
            UserPreference.of("youtube", likeProportional(data.getAge())),
            UserPreference.of("twitter", likeProportional(data.getAge())),
            UserPreference.of("facebook", 0), // Do not like at all
            UserPreference.of("tiktok", likeInvProportional(data.getAge()))
        );
    }
    
}
