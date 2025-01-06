module ru.itis.firstjavafxproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires javafx.web;
    requires static lombok;


    opens ru.itis.nightindvoika to javafx.fxml;
    exports ru.itis.nightindvoika;
}