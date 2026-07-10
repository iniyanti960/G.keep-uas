package com.keep;

import com.keep.database.DatabaseInitializer;
import com.keep.ui.Menu;

public class Main {

    public static void main(String[] args) {

        DatabaseInitializer.initializeDatabase();

        Menu menu = new Menu();

        menu.showMenu();

    }

}