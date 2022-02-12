package publish.db.dao.mysql;

import publish.db.dao.DBException;
import publish.db.dao.RoleDao;
import publish.db.entity.Role;
import publish.service.RoleServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlRoleDao implements RoleDao {

    private static MysqlRoleDao instance;
    private static final Object synh = new Object();

    public static MysqlRoleDao getInstance() {
        System.out.println("MysqlRoleDao@getInstance() start");
        if (instance == null) {
            synchronized (synh) {
                instance = new MysqlRoleDao();
                System.out.println("MysqlRoleDao@getInstance() instance created: " + instance);
            }
        }
        System.out.println("ConnectionPool@getInstance() exit. Instance: " + instance);
        return instance;
    }

    private MysqlRoleDao() {}

    public void close (AutoCloseable con){
        System.out.println("MysqlRoleDao@close(AutoCloseable con) start");
        if (con != null){
            try {
                con.close();
                System.out.println("MysqlRoleDao@close(AutoCloseable con) was closed");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("MysqlRoleDao@close(AutoCloseable con) exception");
            }
        }
    }

    @Override
    public Role getRoleById(int id) throws DBException {
        System.out.println("MysqlRoleDao@getRoleById(int id) start");
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.GET_ROLE_BY_ID)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setInt(++i, id);
            try (ResultSet rs = stmt.executeQuery()) {
                Role role = null;
                while (rs.next()) {
                    role = RoleServiceImpl.getRole(rs.getString(DBConstant.F_ROLE_NAME));
                    role.setId(rs.getInt(DBConstant.F_ROLE_ID));
                    role.setDescription(rs.getString(DBConstant.F_ROLE_DESCRIPTION));
                }
                System.out.println("Role with this id: " + id + " is " + role);
                return role;
            }
        } catch (SQLException e) {
            System.out.println("MysqlRoleDao@getRoleById(int id) exception");
            e.printStackTrace();
            throw new DBException("This role not found", e);
        }
    }

    public Role getRoleByName(String name) throws DBException {
        System.out.println("MysqlRoleDao@getRoleByName(String name) start");
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.GET_ROLE_BY_NAME)) {
            con.setAutoCommit(false);
            int i = 0;
            stmt.setString(++i, name);
            try (ResultSet rs = stmt.executeQuery()) {
                Role role = null;
                while (rs.next()) {
                    role = RoleServiceImpl.getRole(rs.getString(DBConstant.F_ROLE_NAME));
                    role.setId(rs.getInt(DBConstant.F_ROLE_ID));
                    role.setDescription(rs.getString(DBConstant.F_ROLE_DESCRIPTION));
                }
                System.out.println("Role with this name: " + name + " is " + role);
                return role;
            }
        } catch (SQLException e) {
            System.out.println("MysqlRoleDao@getRoleByName(String name) exception");
            e.printStackTrace();
            throw new DBException("This users not found", e);
        }
    }

    public boolean insertRole (Role role) throws DBException {
        System.out.println("MysqlRoleDao@insertRole(Role role) start");
        Connection con = null;
        try{
            con = ConnectionPool.getInstance().getConnection();
            insertRole(con, role);
            System.out.println("MysqlRoleDao@insertRole(Role role) was inserted");
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("MysqlRoleDao@insertRole(Role role) exception");
            throw new DBException("Cannot add this account", e);
        }
        finally {
            close(con);
        }
    }
    private void insertRole(Connection con, Role role) throws SQLException {
        System.out.println("MysqlRoleDao@insertRole(Connection con, Role role) start");
        try (PreparedStatement st = con.prepareStatement(DBConstant.INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            st.setString(++i, role.getName());
            st.setString(++i, role.getDescription());
            int c = st.executeUpdate();
            if (c > 0) {
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        role.setId(keys.getInt(1));
                    }
                }
            }
        }
        System.out.println("MysqlRoleDao@insertRole(Connection con, Role role) exit");
    }

    public List<Role> findAllRoles() throws DBException{
        System.out.println("MysqlRoleDao@findAllRoles() start");
        try(Connection con = ConnectionPool.getInstance().getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(DBConstant.SELECT_USERS)){
            con.setAutoCommit(false);
            List<Role> result = new ArrayList<>();
            while(rs.next()){
                Role role = Role.createRole(rs.getString(DBConstant.F_ROLE_NAME));
                role.setId(rs.getInt(DBConstant.F_ROLE_ID));
                role.setDescription(rs.getString(DBConstant.F_ROLE_DESCRIPTION));
                result.add(role);
                System.out.println("Role: " + role);
            }
            System.out.println("MysqlRoleDao@findAllRoles() return list of roles: " + result);
            return result;
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("MysqlRoleDao@findAllRoles() exception");
            throw new DBException("There aren't roles in the database", e);
        }
    }

    public boolean deleteRoles(Role... roles) throws DBException {
        System.out.println("MysqlRoleDao@deleteRoles(Role... roles) start");
        try (Connection con = ConnectionPool.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(DBConstant.DELETE_ROLE, Statement.RETURN_GENERATED_KEYS)) {
            for (Role role : roles) {
                stmt.setString(1, role.getName());
                stmt.executeUpdate();
            }
            System.out.println("MysqlRoleDao@deleteRoles(Role... roles) roles were deleted");
            return true;
        } catch (SQLException e) {
            System.out.println("MysqlRoleDao@deleteRoles(Role... roles) exception");
            e.printStackTrace();
            throw new DBException("Cannot delete these roles", e);
        }
    }
}
