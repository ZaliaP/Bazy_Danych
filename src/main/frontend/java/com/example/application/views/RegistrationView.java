package com.example.application.views;

import com.example.application.services.AuthService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.component.html.H2;


@Route("registration")
@AnonymousAllowed
public class RegistrationView extends Composite{

    private final AuthService authService;

    public RegistrationView(AuthService authService) {
        this.authService = authService;

    }

    @Override
    protected Component initContent()  {
        TextField username = new TextField("Username");
        //EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        PasswordField confirmpassword = new PasswordField("Confirm Password");



        return new VerticalLayout(
                new H2("Register"),
                username,
                password,
                confirmpassword,
                new Button("Send", event -> register(
                        username.getValue(),
                        password.getValue(),
                        confirmpassword.getValue()
                        )),
                new RouterLink("Login", LoginView.class)
        );
    }

    private void register(String username, String password, String confirmPassword) {
        if(username.trim().isEmpty()) {
            Notification.show("Enter the username");
        }
        else if(password.isEmpty()) {
            Notification.show("Enter the password");
        }
        else if(!password.equals(confirmPassword)) {
            Notification.show("Passwords do not match");
        }else{
            Notification.show("Test");
            authService.register(username, password);
            Notification.show("Registration successful");
        }
    }
}
