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
        TextField username = new TextField("Imię");
        TextField lastName = new TextField("Nazwisko");
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Hasło");
        PasswordField confirmpassword = new PasswordField("Potwierdź hasło");



        VerticalLayout layout = new VerticalLayout(
                new H2("Rejestracja"),
                username,
                lastName,
                email,
                password,
                confirmpassword,
                new Button("Zajerestruj", event -> register(
                        username.getValue(),
                        lastName.getValue(),
                        email.getValue(),
                        password.getValue(),
                        confirmpassword.getValue()
                        )),
                new RouterLink("Zaloguj się", LoginView.class)
        );

        layout.setSizeFull(); // zajmuje cały ekran
        layout.setAlignItems(FlexComponent.Alignment.CENTER); // poziomo
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER); //

        return layout;
    }

    private void register(String username, String lastname, String email, String password, String confirmPassword) {
        if(username.trim().isEmpty()) {
            Notification.show("Wpisz imię");
        }
        else if(lastname.trim().isEmpty()) {
            Notification.show("Wpisz nazwisko");
        }
        else if(email.isEmpty()) {
            Notification.show("Wpisz email");
        }
        else if(!email.endsWith("@zhp.net.pl")) {
            Notification.show("Email musi się kończyć odpowiednią domeną");
        }
        else if(password.isEmpty()) {
            Notification.show("Wpisz hasło");
        }
        else if(!password.equals(confirmPassword)) {
            Notification.show("Potwierdź hasło");
        }else{
            authService.register(username, lastname, email, password);
            Notification.show("Zajerestrowano pomyślnie");
        }
    }
}
