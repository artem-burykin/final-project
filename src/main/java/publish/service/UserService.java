package publish.service;

import publish.db.entity.User;

public class UserService {
        public User getUser(int id, String login, String password){
            return new User(id, login, password);
        }
}
