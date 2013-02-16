package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.Page;

/**
 * Risk entity managed by Ebean
 */
@Entity
public class Risk extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    public String description;

    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date introduced;

    @ManyToOne
    public Matrixunit matrixunit;

    /**
     * Generic query helper for entity Risk with id Long
     */
    public static Finder<Long,Risk> find = new Finder<Long,Risk>(Long.class, Risk.class);

    /**
     * Return a page of risk
     *
     * @param page Page to display
     * @param pageSize Number of risks per page
     * @param sortBy Risk property used for sorting
     * @param order Sort order (either or asc or desc)
     * @param filter Filter applied on the name column
     */
    public static Page<Risk> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
            find.where()
                .ilike("name", "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .fetch("matrixunit")
                .findPagingList(pageSize)
                .getPage(page);
    }

}

