module ru.itis.firstjavafxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires javafx.web;
    requires static lombok;

    opens ru.itis.nightindvoika to javafx.fxml;
    opens ru.itis.nightindvoika.controllers to javafx.fxml;
    opens ru.itis.nightindvoika.util to javafx.fxml;
    opens ru.itis.nightindvoika.mainClasses to javafx.fxml;
    opens ru.itis.nightindvoika.entites to javafx.fxml;
    opens ru.itis.nightindvoika.players to javafx.fxml;
    exports ru.itis.nightindvoika;
    exports ru.itis.nightindvoika.controllers;
    exports ru.itis.nightindvoika.util;
    exports ru.itis.nightindvoika.mainClasses;
    exports ru.itis.nightindvoika.entites;
    exports ru.itis.nightindvoika.players;
}