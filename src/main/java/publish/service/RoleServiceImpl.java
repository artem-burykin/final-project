package publish.service;

import publish.db.dao.RoleDao;
import publish.db.entity.Role;

public class RoleServiceImpl {
    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public static Role getRole(String name){
        return Role.createRole(name);
    }
}
