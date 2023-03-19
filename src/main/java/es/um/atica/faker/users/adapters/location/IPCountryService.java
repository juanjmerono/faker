package es.um.atica.faker.users.adapters.location;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;

import es.um.atica.faker.users.application.service.UsersOriginCountryService;

@Service
public class IPCountryService implements UsersOriginCountryService {

    @Override
    public String getOriginCountry(String userIpAddress) {
        Faker faker = Faker.instance();
        return faker.country().name();
    }
    
}
