/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ECUtils;

import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author computer
 */
public class GUIValidator {

    HashMap<ECValidattion.ECValidator, Control> vMap = new HashMap<>();

    public static void putValidMsg(Control tf, String msg) {
        if (msg == null || "".equals(msg)) {
            tf.setStyle("-fx-background-color: #FFffff;");
            tf.setTooltip(null);
        } else {
            tf.setStyle("-fx-background-color: #FF9999;");
            Tooltip tooltip = new Tooltip();
            tooltip.setText(msg);
            tf.setTooltip(tooltip);
        }
    }

    public void addRequiredValidator(Control tf) {
        ECValidattion.ReqValidator v1 = new ECValidattion.ReqValidator();
        addValidator(tf, v1);
    }

    public void addPNValidator(Control tf) {
        ECValidattion.PNValidator v1 = new ECValidattion.PNValidator();
        addValidator(tf, v1);
    }
    
    public void addAadharValidator(Control tf) {
        ECValidattion.PNValidator v1 = new ECValidattion.PNValidator();
        addValidator(tf, v1);
    }

    public void addPinCodeValidator(Control tf) {
        ECValidattion.PinCodeValidator v1 = new ECValidattion.PinCodeValidator();
        addValidator(tf, v1);
    }

    public void addNameValidator(Control tf) {
        ECValidattion.NameValidator v1 = new ECValidattion.NameValidator();
        addValidator(tf, v1);
    }

    public void addIntValidator(Control tf) {
        ECValidattion.IntValidator v1 = new ECValidattion.IntValidator();
        addValidator(tf, v1);
    }

    public void addDoubleValidator(Control tf) {
        ECValidattion.DoubleValidator v1 = new ECValidattion.DoubleValidator();
        addValidator(tf, v1);
    }

    public void addBooleanValidator(Control tf) {
        ECValidattion.BooleanValidator v1 = new ECValidattion.BooleanValidator();
        addValidator(tf, v1);
    }

    public void addEmailValidator(Control tf) {
        ECValidattion.EmailValidator v1 = new ECValidattion.EmailValidator();
        addValidator(tf, v1);
    }

    public void addDateValidator(Control tf) {
        ECValidattion.DateValidator v1 = new ECValidattion.DateValidator();
        addValidator(tf, v1);
    }

    public void addPassValidator(Control tf) {
        ECValidattion.PassValidator v1 = new ECValidattion.PassValidator();
        addValidator(tf, v1);
    }

    public void addValidator(final Control tf, final ECValidattion.ECValidator v1) {
        vMap.put(v1, tf);
        if (tf instanceof TextField) {
            tf.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    putValidMsg(tf, v1.validate(((TextField) tf).getText()));
                }
            });
        }
         if (tf instanceof ComboBox) {
            tf.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String msg = "";
                    if(((ComboBox) tf).getValue() !=null){
                        msg = ((ComboBox) tf).getValue().toString();
                    }
                    putValidMsg(tf, v1.validate(msg));
                }
            });
        }
    }

    public boolean validateAll() {
        boolean res = true;
        for (Map.Entry<ECValidattion.ECValidator, Control> entry : vMap.entrySet()) {
            ECValidattion.ECValidator key = entry.getKey();
            Control tf = entry.getValue();
            String str = "";
            if (tf instanceof TextField) {
                str = ((TextField) tf).getText();
            }
            if (tf instanceof ComboBox) {
                if (((ComboBox) tf).getValue() != null) {
                    str = ((ComboBox) tf).getValue().toString();
                }
            }
            String msg = key.validate(str);
            putValidMsg(tf, msg);
            if (msg != null && !"".equals(msg)) {
                res = false;
            }
        }
        if (res == false) {
            JOptionPane.showMessageDialog(null, "Pleasse check heighlighted fields!");
        }
        return res;
    }
}
