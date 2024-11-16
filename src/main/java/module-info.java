module org.example.javafxhibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires static lombok;
    requires jakarta.persistence;
    requires java.naming;

    opens org.example.javafxhibernate.models;


    opens org.example.javafxhibernate to javafx.fxml;
    exports org.example.javafxhibernate;
    exports org.example.javafxhibernate.controllers;
    opens org.example.javafxhibernate.controllers to javafx.fxml;
}