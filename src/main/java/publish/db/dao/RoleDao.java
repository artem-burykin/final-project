package publish.db.dao;

import publish.db.entity.Role;

import java.util.List;

public interface RoleDao {
    Role getRoleById(int id) throws DBException;
    Role getRoleByName(String name) throws DBException;
    boolean insertRole (Role role) throws DBException;
    List<Role> findAllRoles() throws DBException;
    boolean deleteRoles(Role... roles) throws DBException;
}
