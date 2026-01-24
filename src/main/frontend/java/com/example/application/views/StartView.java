package com.example.application.views;

import com.example.application.entity.Role;
import com.example.application.security.SecurityService;
import com.example.application.views.list.ListView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.spring.security.AuthenticationContext;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;


@Route(value = "Start", layout = DefaultLayout.class)
@PermitAll
@PageTitle("Ekran startowy | Magazyn Harcerski")
public class StartView extends VerticalLayout {

    private SecurityService securityService;
    private AuthenticationContext auth;

    public StartView(SecurityService securityService, AuthenticationContext auth) {
        this.securityService = securityService;
        this.auth = auth;

        //przyciski kwatermistsza
        Button konta = new Button("Konta użytkowników", e -> UI.getCurrent().navigate("Konta"));
        Button raporty = new Button("Raporty");
        Button sprzet = new Button("Uszkodzony sprzęt");

        //przyciski drużynowego/przybocznego
        //Button historia = new Button("Historia wniosków");

        //wspólne przyciski
        //Button skrzynka = new Button("Skrzynka odbiorcza");
        //Button magazyn = new Button("Magazyn",
                //e -> UI.getCurrent().navigate(Magazine.class));
        //Button logout = new Button("Wyloguj", e -> securityService.logout());

        int buttonWidth = 250;
        //skrzynka.setWidth(buttonWidth + "px");
        //magazyn.setWidth(buttonWidth + "px");
        konta.setWidth(buttonWidth + "px");
        raporty.setWidth(buttonWidth + "px");
        sprzet.setWidth(buttonWidth + "px");
        //logout.setWidth(buttonWidth + "px");
        //historia.setWidth(buttonWidth + "px");

        VerticalLayout kwatermistsz = new VerticalLayout(
                skrzynkaButton(), magazynButton(), konta, raporty, sprzet, logoutButton()
        );

        VerticalLayout druzynowy = new VerticalLayout(
                magazynButton(), skrzynkaButton(), historiaButton(), logoutButton()
        );

        //wygląd przycisków
        kwatermistsz.setPadding(true);
        kwatermistsz.setSpacing(true);
        kwatermistsz.setAlignItems(FlexComponent.Alignment.CENTER);
        kwatermistsz.getStyle()
                .set("border", "1px solid var(--lumo-contrast-20pct)")
                .set("border-radius", "10px")
                .set("box-shadow", "var(--lumo-box-shadow-m)")
                .set("padding", "20px")
                .set("max-width", "300px")
                .set("width", "100%")
                .set("background-color", "var(--lumo-base-color)");

        druzynowy.setPadding(true);
        druzynowy.setSpacing(true);
        druzynowy.setAlignItems(FlexComponent.Alignment.CENTER);
        druzynowy.getStyle()
                .set("border", "1px solid var(--lumo-contrast-20pct)")
                .set("border-radius", "10px")
                .set("box-shadow", "var(--lumo-box-shadow-m)")
                .set("padding", "20px")
                .set("max-width", "300px")
                .set("width", "100%")
                .set("background-color", "var(--lumo-base-color)");

        //RouterLink linkMagazyn = new RouterLink("Test magazyn", Magazine.class);

        if(auth.hasRole("ADMIN")) {
            add(new H1("Czuwaj Kwatermistrz!"), kwatermistsz);
        }
        else if(auth.hasRole("USER")) {
            add(new H1("Czuwaj Drużynowy!"), druzynowy);
        }
        else if(auth.hasRole("PRZYBOCZNY")) {
            add(new H1("Czuwaj Przyboczny!"), druzynowy);
        }


        setSizeFull();
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
    }

    //fabryki przycisków, bo vaadin nie pozwala ich dzielić :(
    private Button magazynButton() {
        Button b = new Button("Magazyn",
                e -> UI.getCurrent().navigate(Magazine.class));
        b.setWidth("250px");
        return b;
    }

    private Button logoutButton() {
        Button b = new Button("Wyloguj",
                e -> securityService.logout());
        b.setWidth("250px");
        return b;
    }

    private Button skrzynkaButton() {
        Button b = new Button("Skrzynka odbiorcza");
        b.setWidth("250px");
        return b;
    }

    private Button historiaButton() {
        Button b = new Button("Historia wniosków");
        b.setWidth("250px");
        return b;
    }

}
