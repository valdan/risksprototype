package models;

import java.util.*;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;

/**
 * User entity managed by Ebean
 */
@Entity
@Table(name="account")
public class User extends Model {


	@Id
    public Long id;

    @Constraints.Required
    @Formats.NonEmpty
    public String email;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String password;

    // -- Queries

    public static Model.Finder<Long,User> find = new Model.Finder(Long.class, User.class);

    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }

    /**
     * Retrieve a User from email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }


    /**
     * Retrieve a User from name.
     */
    public static User findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    /**
     * Authenticate a User.
     */
    public static User authenticate(String name, String password) {
        return find.where()
            .eq("name", name)
            .eq("password", password)
            .findUnique();
    }

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(User c: User.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.email);
        }
        return options;
    }


    // --

    @Override
	public String toString() {
        return "User(" + email + ")";
    }

}

