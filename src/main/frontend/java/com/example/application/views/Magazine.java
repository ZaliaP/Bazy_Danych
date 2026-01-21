package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.annotation.SpringComponent;
import jakarta.annotation.security.PermitAll;

import java.awt.*;

@Route(value = "magazyn")
@PermitAll
@PageTitle("Magazyn | Magazyn Harcerski")
public class Magazine extends VerticalLayout{

    public Magazine() {
        add(new H1("Magazine"));
    }
}
