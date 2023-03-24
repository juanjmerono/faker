package es.um.atica.faker.users.application.service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface UsersSearchSpecificationService {

    // Multiple Search String
    // search=fieldA>2|fieldB<4,fieldC:3
    // ==> (fieldA > 2 or fieldB < 4) and fieldC = 3

    public Object defaultSpec();
    public Object buildAndSpec(Object element1, Object element2);
    public Object buildOrSpec(Object element1, Object element2);
    public Object buildSpecFor(String el1, String op, String el2);

    default Object genericSpec(String s) {
        // Parse pattern for individual spec
        Pattern p = Pattern.compile("(\\w+)([\\>\\<\\~])(\\w+)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return buildSpecFor(m.group(1),m.group(2),m.group(3));
        } else {
            return defaultSpec();
        }
    }

    default Object buildIndividualSpec(String search) {
        return Arrays.stream(search.split("\\|"))
            .map(f -> genericSpec(f))
            .reduce((subtotal,element)->buildOrSpec(element, subtotal))
            .get();
    }

    default Object buildSpecificationFromSearch(List<String> search) {
        return search.stream()
            .map(s->buildIndividualSpec(s))
            .reduce((subtotal,element)-> buildAndSpec(element,subtotal))
            .get();
    }

}
