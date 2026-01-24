package com.example.application.views;

import com.example.application.data.Contact;
import com.example.application.entity.User;
import com.example.application.services.CrmService;
import com.example.application.services.UserService;
import com.example.application.views.list.ContactForm;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "Konta", layout = DefaultLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Konta | Magazyn Harcerski")
public class AccountView extends VerticalLayout {
    Grid<User> grid = new Grid<>(User.class, false);
    TextField filterText = new TextField();
    private UserService userService;
    UserForm form;

    public AccountView(UserService userService) {
        this.userService = userService;

        setSizeFull(); //wielkość strony

        configureGrid();
        configureForm();

        add(
          new H1("Konta użytkowników"),
          getToolBar(),
          getContent()
        );

        updateGrid();
        closeEditor();

        /*
        grid.addColumn(User::getUsername).setHeader("Imię");
        grid.addColumn(User::getLastname).setHeader("Nazwisko");
        grid.addColumn(User::getEmail).setHeader("Email");
        grid.addColumn(user -> user.getRole().getDisplayName())
                .setHeader("Funkcja");

        grid.setItems(userService.findAllNonAdmins());



        add(new H1("Konta użytkowników"),grid);
         */
    }


    //logika strony
    private void closeEditor() {
        form.setUser(null);
        form.setVisible(false);
        removeClassName("editing");
    }


    private void updateGrid() {
        grid.setItems(userService.findAllNonAdmins(filterText.getValue())); //sprawdzić czy to działa
    }

    private void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            form.setUser(user);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveUser(UserForm.SaveEvent event) {
        userService.save(event.getUser());
        updateGrid();
        closeEditor();
    }

    private void deleteUser(UserForm.DeleteEvent event) {
        userService.delete(event.getUser());
        updateGrid();
        closeEditor();
    }



    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setSizeFull();
        return content;
    }

    private Component getToolBar() {
        filterText.setPlaceholder("Filtruj");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateGrid());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    //konfigutacja jak będzie wyglądać strona
    private void configureForm() {
        form = new UserForm();
        form.setWidth("25em");

        form.addListener(UserForm.SaveEvent.class, this::saveUser);
        form.addListener(UserForm.DeleteEvent.class, this::deleteUser);
        form.addListener(UserForm.CloseEvent.class, e -> closeEditor());
    }

    private void configureGrid() {
        grid.addClassName("user-grid");
        grid.setSizeFull();

        grid.addColumn(User::getUsername).setHeader("Imię").setAutoWidth(true);
        grid.addColumn(User::getLastname).setHeader("Nazwisko").setAutoWidth(true);
        grid.addColumn(User::getEmail).setHeader("Email").setAutoWidth(true);
        grid.addColumn(user -> user.getRole().getDisplayName())
                .setHeader("Rola")
                .setAutoWidth(true);

        grid.asSingleSelect()
                .addValueChangeListener(event -> editUser(event.getValue()));
    }

}
