package es.um.atica.faker.users.application.query;

import es.um.atica.faker.users.domain.model.User;
import es.um.atica.shared.domain.cqrs.Query;

public class GetUsersQuery extends Query<Iterable<User>> {
    private int page;
    private int pageSize;

    private GetUsersQuery(int page, int pageSize) {
        this.page = page; this.pageSize = pageSize;
    }
    public static GetUsersQuery of(int page, int pageSize) {
        return new GetUsersQuery(page,pageSize);
    }

    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
}
