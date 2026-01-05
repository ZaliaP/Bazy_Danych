package com.example.application.views;

import com.example.application.services.AuthService;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Route("login") 
@PageTitle("Login | Vaadin CRM")
@AnonymousAllowed
/*
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	private final LoginForm login = new LoginForm();

	public LoginView(){
		addClassName("login-view");
		setSizeFull();
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");

		add(new H1("Vaadin CRM"));
		add(new Span("Username: user, Password: password"));
		add(new Span("Username: admin, Password: password"));
		add(login, new RouterLink("Register", RegistrationView.class));
	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()
				.getQueryParameters()
				.getParameters()
				.containsKey("error")) {
			login.setError(true);
		}
	}
}*/
public class LoginView extends VerticalLayout {

	private final AuthenticationManager authManager;

	public LoginView(AuthenticationManager authManager) {
		this.authManager = authManager;

		TextField username = new TextField("Username");
		PasswordField password = new PasswordField("Password");
		Button loginButton = new Button("Login", e -> doLogin(username.getValue(), password.getValue()));

		add(new H1("Vaadin CRM"), username, password, loginButton, new RouterLink("Register", RegistrationView.class));
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setSizeFull();
	}

	private void doLogin(String username, String password) {
		if (username == null || username.isBlank()
				|| password == null || password.isBlank()) {
			Notification.show("Enter login and password");
			return;
		}

		try {
			Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(auth);
			UI.getCurrent().navigate(""); // np. główny widok po zalogowaniu
		} catch (BadCredentialsException e) {
			Notification.show("Wrong password or login");
			System.out.println("Wrong password or login" + e.getMessage());
		} catch (AuthenticationException e) {
			Notification.show("Login failed");
			System.out.println("Login failed" + e.getMessage());
		} catch (Exception e){
			Notification.show("Something went wrong, please try again");
			System.out.println("Something went wrong" + e.getMessage());
		}
	}
}
