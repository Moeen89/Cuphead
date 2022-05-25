module com.example.cuphead {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;
    opens com.example.cuphead;
    opens com.example.cuphead.view;
    opens com.example.cuphead.images;
    opens com.example.cuphead.icon;
    exports com.example.cuphead;
    exports com.example.cuphead.view;
    opens com.example.cuphead.model;
}