package controllers;

import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.*;
import models.*;
import com.typesafe.plugin.*;
import javax.mail.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.apache.poi.xssf.usermodel.*;

/**
 * Manage a database of risks
 */
public class Application extends Controller {

    /**
     * This result directly redirect to application home.
     */
    public static Result GO_HOME = redirect(
        routes.Application.list(
        		0,
        		"name",
        		"asc",
        		"")
    );

    public static Result GO_HOME_USERS = redirect(
		 routes.Application.listUsers()
    );

    /**
     * Handle default path requests, redirect to risks list
     */
    @Security.Authenticated(Secured.class)
    public static Result index() {
        return GO_HOME;
    }

    @Security.Authenticated(Secured.class)
    public static Result admin() {
    	return ok(
            admin.render(User.findByName(request().username()))
        );
    }


    @Security.Authenticated(Secured.class)
    public static Result reports() {
    	return ok(
            reports.render(User.findByName(request().username()))
        );
    }


    // Sample risks report
    public static Result risksReports() {

    	File file = new File("test.xlsx");

    	try {
    		List<Risk> risks = Risk.find.orderBy("name").findList();

	    	FileOutputStream fileOut = new FileOutputStream(file);
	    	XSSFWorkbook wb = new XSSFWorkbook();
	    	XSSFSheet sheet = wb.createSheet("Sheet1");

	    	XSSFRow row = sheet.createRow(0);
	    	XSSFCell cell = row.createCell(0);
	    	cell.setCellValue("Name");
	    	cell = row.createCell(1);
	    	cell.setCellValue("Description");
	    	cell = row.createCell(2);
	    	cell.setCellValue("introduced");
	    	cell = row.createCell(3);
	    	cell.setCellValue("stage");
	    	cell = row.createCell(4);
	    	cell.setCellValue("Risk Ass");
	    	cell = row.createCell(5);
	    	cell.setCellValue("service");
	    	cell = row.createCell(6);
	    	cell.setCellValue("location");
	    	cell = row.createCell(7);
	    	cell.setCellValue("developer");
	    	cell = row.createCell(8);
	    	cell.setCellValue("host");
	    	cell = row.createCell(9);
	    	cell.setCellValue("manager");
	    	cell = row.createCell(10);
	    	cell.setCellValue("confidential");
	    	cell = row.createCell(11);
	    	cell.setCellValue("imported");
	    	cell = row.createCell(12);
	    	cell.setCellValue("exported");
	    	cell = row.createCell(13);
	    	cell.setCellValue("comment");
	    	cell = row.createCell(14);
	    	cell.setCellValue("criticaldate");

	    	row = sheet.createRow(1);

	    	int i = 0;
	    	for (Risk risk : risks) {
	    		row = sheet.createRow(2 + i);
	    		cell = row.createCell(0);
		    	cell.setCellValue(risk.name!=null?risk.name:"");
		    	cell = row.createCell(1);
		    	cell.setCellValue(risk.description!=null?risk.description:"");
		    	cell = row.createCell(2);
		    	cell.setCellValue(risk.introduced!=null?risk.introduced.toString():"");
		    	cell = row.createCell(3);
		    	cell.setCellValue(risk.stage!=null?risk.stage:"");
		    	cell = row.createCell(4);
		    	cell.setCellValue(risk.ra!=null?risk.ra:"");
		    	cell = row.createCell(5);
		    	cell.setCellValue(risk.service!=null?risk.service:"");
		    	cell = row.createCell(6);
		    	cell.setCellValue(risk.location!=null?risk.location:"");
		    	cell = row.createCell(7);
		    	cell.setCellValue(risk.developer!=null?risk.developer:"");
		    	cell = row.createCell(8);
		    	cell.setCellValue(risk.host!=null?risk.host:"");
		    	cell = row.createCell(9);
		    	cell.setCellValue(risk.manager!=null?risk.manager:"");
		    	cell = row.createCell(10);
		    	cell.setCellValue(risk.confidential!=null?risk.confidential:"");
		    	cell = row.createCell(11);
		    	cell.setCellValue(risk.imported!=null?risk.imported:"");
		    	cell = row.createCell(12);
		    	cell.setCellValue(risk.exported!=null?risk.exported:"");
		    	cell = row.createCell(13);
		    	cell.setCellValue(risk.comment!=null?risk.comment:"");
		    	cell = row.createCell(14);
		    	cell.setCellValue(risk.criticaldate!=null?risk.criticaldate:"");
		    	i++;
			}
	    	wb.write(fileOut);
	    	fileOut.close();
	    	return ok(file);
    	} catch(Exception e) {
    		flash("success", "Error generating report: " + e.getMessage());
            return GO_HOME;
    	}
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
                sortBy, order, filter,
                User.findByName(request().username())
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
            		User.findByName(request().username()))
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
            			User.findByName(request().username())));
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
            createForm.render(riskForm, User.findByName(request().username()))
        );
    }

    /**
     * Handle the 'new risk form' submission
     */
    @Security.Authenticated(Secured.class)
    public static Result save() {
        Form<Risk> riskForm = form(Risk.class).bindFromRequest();
        if(riskForm.hasErrors()) {
            return badRequest(createForm.render(riskForm, User.findByName(request().username())));
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

        public String name;
        public String password;

        public String validate() {
            if(User.authenticate(name, password) == null) {
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
            session("name", loginForm.get().name);
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


	@Security.Authenticated(Secured.class)
	public static Result listUsers() {
		 return ok(
            usersList.render(
                User.findAll(),
                User.findByName(request().username()))
        );
	  }


	@Security.Authenticated(Secured.class)
	public static Result createUser() {
	    return TODO;
	}

	public static class EmailForm {

        public Long userId;

    }


	@Security.Authenticated(Secured.class)
	public static Result email(Long id) {
		Form<EmailForm> ef = form(EmailForm.class);

		return ok(
            emailForm.render(
        		id,
        		ef)
        );
	}


	@Security.Authenticated(Secured.class)
	public static Result sendEmail(Long id) {
		Form<EmailForm> ef = form(EmailForm.class).bindFromRequest();

		try {
			String baseUrl = play.Play.application().configuration().getString("application.baseUrl");

			User user = User.find.byId(ef.get().userId);
			Risk risk = Risk.find.byId(id);
			MailerAPI mail = play.Play.application().plugin(MailerPlugin.class).email();
			mail.setSubject("Please Inspect Risk ...");
			mail.addRecipient(user.email);
			mail.addFrom("VAL-DAN <daniel.vladescu@gmail.com>");
			//sends html
//			mail.sendHtml("<html>html</html>" );
			//sends text/text
			mail.send( "Please check following Risk (" + risk.name + ") : " + baseUrl + "/risks/" + risk.id);
			//sends both text and html
//			mail.send( "text", "<html>html</html>");
			flash("success", "Mail Sent! Verification of risk: " + risk.name);
		} catch(Exception ex ) {
			// TODO: inspect what is happening here
			flash("success", "Sent Failed. Please check Your recipient!");
		}

        return GO_HOME;
	}


//	@Security.Authenticated(Secured.class)
	public static Result editUser(Long id) {
		Form<User> userForm = form(User.class).fill(
			 User.find.byId(id)
        );
        return ok(
            editUserForm.render(
            		id,
            		userForm)
        );
	 }


	@Security.Authenticated(Secured.class)
	public static Result updateUser(Long id) {
		Form<User> userForm = form(User.class).bindFromRequest();
        if(userForm.hasErrors()) {
            return badRequest(editUserForm.render(
            		id,
            		userForm));
        }

        userForm.get().update(id);
        flash("success", "User " + userForm.get().name + " has been updated");
        return GO_HOME_USERS;
	 }


//	@Security.Authenticated(Secured.class)
	 public static Result deleteUser(Long id) {
		User.find.byId(id).delete();
        flash("success", "User has been deleted");
        return GO_HOME_USERS;
	 }


}
