package es.um.atica.faker.users.application.query;

import java.util.List;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.cqrs.Query;

public class GetUsersSpecificationQuery extends Query<Iterable<User>> {
    private List<String> search;
    private int page;
    private int pageSize;

    private GetUsersSpecificationQuery(List<String> search, int page, int pageSize) {
        this.search = search; this.page = page; this.pageSize = pageSize;
    }
    public static GetUsersSpecificationQuery of(List<String> search, int page, int pageSize) {
        return new GetUsersSpecificationQuery(search, page,pageSize);
    }

    public List<String> getSearchList() { return search; }
    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
}
