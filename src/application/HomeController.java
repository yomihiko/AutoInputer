package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HomeController {

	@FXML
    private AnchorPane content;
    @FXML
    private PasswordField aa;

    @FXML
    private Label lbl1;

    @FXML
    private TextField txt2;

    @FXML
    private Button btn1;

    @FXML
    private ListView<String> asss;

    @FXML
    void onBtn(ActionEvent event) {
    	System.out.println(lbl1.getTextFill());
    }
    public static void json(Control c) {

    }

}
