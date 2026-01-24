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
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.validation.constraints.Email;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Route("login") 
@PageTitle("Login | Magazyn Harcerski")
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

		//TextField username = new TextField("Username");
		EmailField email = new EmailField("Email");
		PasswordField password = new PasswordField("Hasło");
		Button loginButton = new Button("Zaloguj się", e -> doLogin(email.getValue(), password.getValue()));

		add(new H1("Logowanie"), email, password, loginButton, new RouterLink("Zajerestruj się", RegistrationView.class));
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setSizeFull();
	}

	private void doLogin(String email, String password) {
		if (email == null || email.isBlank()
				|| password == null || password.isBlank()) {
			Notification.show("Wpisz login i hasło");
			return;
		}

		try {
			Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			SecurityContextHolder.getContext().setAuthentication(auth);
			//potrzebne do zatrzymania sesji zalogowanego użytkownika!!!!!!!!!!!!
			//a dokładniej potrzebne ponieważ robię to ręcznie, jakby nie było ręcznie to by spring sam to ogranął :\
			SecurityContextRepository repo = new HttpSessionSecurityContextRepository();
			repo.saveContext(SecurityContextHolder.getContext(),
					VaadinServletRequest.getCurrent().getHttpServletRequest(),
					VaadinServletResponse.getCurrent().getHttpServletResponse());
			//
			UI.getCurrent().navigate("Start"); // np. główny widok po zalogowaniu
		} catch (BadCredentialsException e) {
			Notification.show("Niepoprawne login lub hasło");
			System.out.println("Wrong password or login" + e.getMessage());
		} catch (AuthenticationException e) {
			Notification.show("Logowanie nieudane");
			System.out.println("Login failed" + e.getMessage());
		} catch (Exception e){
			Notification.show("Coś poszło nie tak, spróbuj ponownie");
			System.out.println("Something went wrong" + e.getMessage());
		}
	}
}