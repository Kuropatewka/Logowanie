package pl.camp.it.database;

import pl.camp.it.model.Sex;
import pl.camp.it.model.User;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final List<User> users = new ArrayList<>();
    private static final Repository repository = new Repository(); //SigleTon nie da sie podmienic, stworzyc kolejnego

    private Repository() {
        this.users.add(new User(1,
                "Janusz",
                "77f869401de682f60e0e749493ab793d",
                Sex.OTHER));
    }

    public List<User> getUsers() {
        return users;
    }

    public static Repository getRepository() { // statyczny getter do statycznego pola
        return repository;
    }

}
