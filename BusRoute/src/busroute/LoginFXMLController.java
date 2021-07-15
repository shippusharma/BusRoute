/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busroute;

import ECUtils.GUIValidator;
import bus.bean.MyUser;
import bus.dao.UserDAO;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class LoginFXMLController implements Initializable, FXMLConst {

    GUIValidator v1 = new GUIValidator();
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        v1.addRequiredValidator(txtEmail);
        v1.addRequiredValidator(txtPass);
    }

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnBack) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(BUS_FXML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (v1.validateAll()) {
                    MyUser u1 = UserDAO.validate(txtEmail.getText(), txtPass.getText());
                    if (u1 != null) {
                        Stage stage = (Stage) btnBack.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getResource(ADMIN_FXML));
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.setMaximized(true);
                        stage.show();
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid user name or password!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
