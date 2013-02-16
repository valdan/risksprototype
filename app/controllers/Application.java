package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;

import models.*;

/**
 * Manage a database of risks
 */
public class Application extends Controller {

    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Application.list(0, "name", "asc", "")
    );

    /**
     * Handle default path requests, redirect to risks list
     */
    @Security.Authenticated(Secured.class)
    public static Result index() {
        return GO_HOME;
    }

    /**
     * Display the paginated list of risks.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on risk names
     */
    @Security.Authenticated(Secured.class)
    public static Result list(int page, String sortBy, String order, String filter) {
        return ok(
            list.render(
                Risk.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }

    /**
     * Display the 'edit form' of a existing Risk.
     *
     * @param id Id of the risk to edit
     */
    @Security.Authenticated(Secured.class)
    public static Result edit(Long id) {
        Form<Risk> riskForm = form(Risk.class).fill(
            Risk.find.byId(id)
        );
        return ok(
            editForm.render(
            		id,
            		riskForm,
            		User.find.byId(request().username()))
        );
    }

    /**
     * Handle the 'edit form' submission
     *
     * @param id Id of the risk to edit
     */
    @Security.Authenticated(Secured.class)
    public static Result update(Long id) {
        Form<Risk> riskForm = form(Risk.class).bindFromRequest();
        if(riskForm.hasErrors()) {
            return badRequest(editForm.render(
            			id,
            			riskForm,
            			User.find.byId(request().username())));
        }
        riskForm.get().update(id);
        flash("success", "Risk " + riskForm.get().name + " has been updated");
        return GO_HOME;
    }

    /**
     * Display the 'new risk form'.
     */
    @Security.Authenticated(Secured.class)
    public static Result create() {
        Form<Risk> riskForm = form(Risk.class);
        return ok(
            createForm.render(riskForm)
        );
    }

    /**
     * Handle the 'new risk form' submission
     */
    @Security.Authenticated(Secured.class)
    public static Result save() {
        Form<Risk> riskForm = form(Risk.class).bindFromRequest();
        if(riskForm.hasErrors()) {
            return badRequest(createForm.render(riskForm));
        }
        riskForm.get().save();
        flash("success", "Risk " + riskForm.get().name + " has been created");
        return GO_HOME;
    }

    /**
     * Handle risk deletion
     */
    @Security.Authenticated(Secured.class)
    public static Result delete(Long id) {
        Risk.find.ref(id).delete();
        flash("success", "Risk has been deleted");
        return GO_HOME;
    }


// -- Authentication

    public static class Login {

        public String email;
        public String password;

        public String validate() {
            if(User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }

    }

    /**
     * Login page.
     */
    public static Result login() {
        return ok(
            login.render(form(Login.class))
        );
    }

    /**
     * Handle login form submission.
     */
    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(login.render(loginForm));
        } else {
            session("email", loginForm.get().email);
            return redirect(
                routes.Application.index()
            );
        }
    }

    /**
     * Logout and clean the session.
     */
    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.login()
        );
    }


}

