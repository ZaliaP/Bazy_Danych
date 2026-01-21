package com.example.application.views;

import com.example.application.security.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class DefaultLayout extends AppLayout {
    private final SecurityService securityService;

    public DefaultLayout(SecurityService securityService) {
        this.securityService = securityService;
        createHeader();
    }

    private void createHeader() {
        H1 logo = new H1("Magazyn Harcerski");
        logo.getStyle().set("margin", "0");

        Button accountButton = new Button("Konto", e ->
                UI.getCurrent().navigate(StartView.class) // widok główny
        );

        HorizontalLayout header = new HorizontalLayout(logo, accountButton);
        header.setWidthFull();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo); // logo po lewej, przycisk Konto po prawej
        header.getStyle().set("padding", "0 20px");

        addToNavbar(header);
    }

}
