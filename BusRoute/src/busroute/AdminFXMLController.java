/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package busroute;

import ECUtils.MyUtils;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author computer
 */
public class AdminFXMLController implements Initializable, FXMLConst {

    static String sc = "id";
    static String si = "";

    @FXML
    private Button btnBack;
    @FXML
    private Button btnDelete;
    @FXML
    private TextField txtSi;
    @FXML
    private ComboBox<?> cmbSc;
    @FXML
    private TableView<?> tblList;
    @FXML
    private Button btnAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MyUtils.populateColumnNames(cmbSc, "route");
        txtSi.setText(si);
        MyUtils.selectComboBoxValue(cmbSc, sc);
        refreshTbl();

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
        } else if (event.getSource() == btnAdd) {
            try {
                Stage stage = (Stage) btnAdd.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource(ADD_FXML));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.getSource() == btnDelete) {
            try {
                String id = MyUtils.getSelColValue("id", tblList);
                if (id == null || "".equals(id)) {
                    JOptionPane.showMessageDialog(null, "Please select a value!!");
                } else {
                    int ch = JOptionPane.showConfirmDialog(null, "R u sure!");
                    if (ch == 0) {
                        String rNoStr = MyUtils.getSelColValue("RouteNo", tblList);
                        String stNoStr = MyUtils.getSelColValue("StopageNo", tblList);
                        int stNo = Integer.parseInt(stNoStr);
                        RouteDAO.delete(id);
                        RouteDAO.updateStopageNos(rNoStr, stNo, -1);
                        refreshTbl();
                        JOptionPane.showMessageDialog(null, "Deleted!!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void kr(KeyEvent event) {
        si = txtSi.getText();
        refreshTbl();
    }

    @FXML
    private void cmbHe(ActionEvent event) {
        sc = cmbSc.getValue().toString();
        refreshTbl();
    }

    private void refreshTbl() {
        LinkedList<Route> res = RouteDAO.search(cmbSc.getValue().toString(), txtSi.getText());
        MyUtils.populateTable(tblList, res, Route.class);
    }
}
