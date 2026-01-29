package com.example.application.views;

import com.example.application.entity.User;
import com.vaadin.flow.component.grid.Grid;
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

@Route(value = "magazyn", layout = DefaultLayout.class)
@PermitAll
@PageTitle("Magazyn | Magazyn Harcerski")
public class Magazine extends VerticalLayout{
    public record Item(String id, String nazwa, String pomieszczenie, String wlasciciel, String stan) {}
    Grid<Item> grid = new Grid<>();


    public Magazine() {
        configureGrid();

        add(
                grid
        );
    }

    private void configureGrid() {
        grid.addClassName("item-grid");
        grid.setSizeFull();

        grid.addColumn(Item::id).setHeader("Id przedmiotu").setAutoWidth(true);
        grid.addColumn(Item::nazwa).setHeader("Nazwa").setAutoWidth(true);
        grid.addColumn(Item::pomieszczenie).setHeader("Pomieszczenie").setAutoWidth(true);
        grid.addColumn(Item::wlasciciel).setHeader("Właściciel").setAutoWidth(true);
        grid.addColumn(Item::stan).setHeader("Stan przedmiotu").setAutoWidth(true);

    }
}
