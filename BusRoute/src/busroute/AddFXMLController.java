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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class AddFXMLController implements Initializable, FXMLConst {

    GUIValidator v1 = new GUIValidator();

    @FXML
    private TextField txtRouteNo;
    @FXML
    private TextField txtStopageName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnBack;
    @FXML
    private ComboBox<?> cmbInsertAfter;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        v1.addRequiredValidator(txtRouteNo);
        v1.addRequiredValidator(txtStopageName);
        
    }

    @FXML
    private void kr(KeyEvent event) {
            cmbInsertAfter.getItems().clear();
            LinkedList res = RouteDAO.searchByRouteId(txtRouteNo.getText());
            res.add(0, "Make First Stop");
            cmbInsertAfter.getItems().addAll(res);
    }

    @FXML
    private void he(ActionEvent event) {
        if (event.getSource() == btnBack) {
            try {
                Stage stage = (Stage) btnBack.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(ADMIN_FXML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (v1.validateAll()) {
                    Route p1 = new Route();
                    p1.setRouteNo(txtRouteNo.getText());
                    p1.setStopage(txtStopageName.getText());
                    if(cmbInsertAfter.getValue()==null || "".equals(cmbInsertAfter.getValue().toString())){
                        int sid = RouteDAO.maxStopageNoByRouteId(p1.getRouteNo())+1;
                        p1.setStopageNo(sid);                        
                    }
                    else {
                        int sno = RouteDAO.getStopageNo(txtRouteNo.getText(), cmbInsertAfter.getValue().toString());
                        RouteDAO.updateStopageNos(txtRouteNo.getText(), sno, 1);
                        p1.setStopageNo(sno + 1);                                                
                    }
                    
                    //p1.setRouteNo();
                    RouteDAO.insert(p1);
                    Stage stage = (Stage) btnBack.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getResource(ADMIN_FXML));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}