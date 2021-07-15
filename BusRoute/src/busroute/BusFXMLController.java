/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busroute;

import ECUtils.GUIValidator;
import bus.bean.Route;
import bus.dao.RouteDAO;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 *
 * @author computer
 */
public class BusFXMLController implements Initializable, FXMLConst {

    GUIValidator v1 = new GUIValidator();
    @FXML
    private Button btnSearch;
    @FXML
    private Hyperlink btnLogin;
    @FXML
    private ComboBox<?> cmbTo;
    @FXML
    private ComboBox<?> cmbFrom;
    @FXML
    private TextArea taRes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LinkedList stps = RouteDAO.searchStopages();
        cmbFrom.getItems().addAll(stps);
        cmbTo.getItems().addAll(stps);
        v1.addRequiredValidator(cmbTo);
        v1.addRequiredValidator(cmbFrom);
    }

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnSearch) {
            if (v1.validateAll()) {
                LinkedList<Route> res = RouteDAO.getRouts(cmbFrom.getValue().toString(), cmbTo.getValue().toString());
                if (res.size() > 0) {
                    taRes.setText("Direct Routes::\n");
                    for (Route r1 : res) {
                        taRes.appendText(r1.getRouteNo() + "\n");
                    }
                } else {
                    LinkedList<String> res2 = RouteDAO.getConnectingStopages(cmbFrom.getValue().toString(), cmbTo.getValue().toString());
                    if (res2.size() > 0) {
                        taRes.setText("No Direct Route, Connecting Stopages\n");
                        for (String s1 : res2) {
                            taRes.appendText(s1 + "\n");
                        }
                    } else {
                        taRes.setText("No Result Found!!");
                    }
                }
            }
        }
    }

    @FXML
    private void linkLogin(ActionEvent event
    ) {
        try {
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(LOGIN_FXML));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
