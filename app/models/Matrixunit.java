package models;

import java.util.*;
import javax.persistence.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.*;


import com.avaje.ebean.*;


/**
 * Matrixunit entity managed by Ebean
 */
@Entity 
public class Matrixunit extends Model {

    @Id
    public Long id;
    
    @Constraints.Required
    public String name;
    
    /**
     * Generic query helper for entity Matrixunit with id Long
     */
    public static Model.Finder<Long,Matrixunit> find = new Model.Finder<Long,Matrixunit>(Long.class, Matrixunit.class);

    public static Map<String,String> options() {
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Matrixunit c: Matrixunit.find.orderBy("name").findList()) {
            options.put(c.id.toString(), c.name);
        }
        return options;
    }

}

