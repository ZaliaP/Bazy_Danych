package com.example.application.views;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.RolesAllowed;

@Route(value = "Konta", layout = DefaultLayout.class)
@RolesAllowed("ADMIN")
@PageTitle("Konta | Magazyn Harcerski")
public class AccountView {


}
