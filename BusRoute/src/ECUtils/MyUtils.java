package ECUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javafx.beans.property.*;
import javafx.beans.value.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.util.*;

public class MyUtils {

    public static double pw = 200;

    public static <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns()) {
            if (col.getText().equalsIgnoreCase(name)) {
                return col;
            }
        }
        return null;
    }

    public static String getSelColValue(String col, TableView tableView) {
        String res = null;
        try {
            int rowIndex = tableView.getSelectionModel().getSelectedIndex();
            TableColumn column = getTableColumnByName(tableView, col);
            if (column != null && column.getCellData(rowIndex) != null) {
                res = column.getCellData(rowIndex).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String getSelColValueByIndex(int columnIndex, TableView tableView) {
        String res = null;
        try {
            int rowIndex = tableView.getSelectionModel().getSelectedIndex();
            ObservableList rowList
                    = (ObservableList) tableView.getItems().get(rowIndex);
            res = rowList.get(columnIndex).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void populateColumnNames(ComboBox cmb, String tname) {
        cmb.getItems().clear();
        cmb.getItems().addAll(getColumnNames(tname));
        cmb.getSelectionModel().selectFirst();
    }

    public static void selectComboBoxValue(ComboBox comboBox, String value) {
        for (Object t : comboBox.getItems()) {
            if (value.compareTo(t.toString()) == 0) {
                comboBox.setValue(t);
            }
        }
    }

    public static List getColumnNames(String tname) {
        return getColumnNames("select * from " + tname, null);
    }

    public static List getColumnNames(String sql, String[] pl) {
        LinkedList res = new LinkedList();
        Connection con = null;
        try {
            con = BaseDAO.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            if (pl != null && pl.length > 0) {
                for (String s1 : pl) {
                    st.setString(i++, s1);
                }
            }
            ResultSet rs = st.executeQuery();
            ResultSetMetaData rmd = rs.getMetaData();
            for (int j = 1; j <= rmd.getColumnCount(); j++) {
                res.add(rmd.getColumnLabel(j));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.closeCon(con);
        }
        return res;
    }

    public static void populateTable(TableView tableview, String sql, String[] pl) {
        Connection con = null;
        try {
            con = BaseDAO.getCon();
            PreparedStatement st = con.prepareStatement(sql);
            int i = 1;
            if (pl != null && pl.length > 0) {
                for (String s1 : pl) {
                    st.setString(i++, s1);
                }
            }
            ResultSet rs = st.executeQuery();
            populateTable(tableview, rs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDAO.closeCon(con);
        }
    }

    public static void populateTable(TableView tableview, ResultSet rs) {
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        try {
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            tableview.getColumns().clear();
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(toTitleCase(rs.getMetaData().getColumnName(i + 1)));
                if (rs.getMetaData().getColumnType(i + 1) == 4) {
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Number>, ObservableValue<Number>>() {
                        public ObservableValue<Number> call(TableColumn.CellDataFeatures<ObservableList, Number> param) {
                            if (param != null && param.getValue() != null && param.getValue().get(j) != null) {
                                return new SimpleIntegerProperty(new Integer(param.getValue().get(j).toString()));
                            } else {
                                return new SimpleIntegerProperty(0);
                            }
                        }
                    });
                } else {
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            if (param != null && param.getValue() != null && param.getValue().get(j) != null) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            } else {
                                return new SimpleStringProperty("");
                            }
                        }
                    });
                }
                col.setPrefWidth(pw);
                tableview.getColumns().addAll(col);
            }
            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            while (rs.next()) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public static void populateTable(TableView tableview, List res, Class c1) {
        ObservableList<ObservableList> data;
        data = FXCollections.observableArrayList();
        try {
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY * ********************************
             */
            tableview.getColumns().clear();
            int i = 0;
            Field fa[] = c1.getDeclaredFields();
            for (Field f1 : fa) {
                final int j = i++;
                System.out.println("ECUtils.MyUtils.populateTable()" + f1.getName());
                TableColumn col = new TableColumn(toTitleCase(f1.getName()));
                if (f1.getType().getName().equals("Integer")) {
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, Number>, ObservableValue<Number>>() {
                        public ObservableValue<Number> call(TableColumn.CellDataFeatures<ObservableList, Number> param) {
                            if (param != null && param.getValue() != null && param.getValue().get(j) != null) {
                                return new SimpleIntegerProperty(new Integer(param.getValue().get(j).toString()));
                            } else {
                                return new SimpleIntegerProperty(0);
                            }
                        }
                    });
                } else {
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            if (param != null && param.getValue() != null && param.getValue().get(j) != null) {
                                return new SimpleStringProperty(param.getValue().get(j).toString());
                            } else {
                                return new SimpleStringProperty("");
                            }
                        }
                    });
                }
                col.setPrefWidth(pw);
                tableview.getColumns().addAll(col);
            }
            /**
             * ******************************
             * Data added to ObservableList * ******************************
             */
            for (Object o1 : res) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (Field f1 : fa) {
                    f1.setAccessible(true);
                    Object fieldValue = f1.get(o1);
                    if (fieldValue != null) {
                        row.add(fieldValue.toString());
                    } else {
                        row.add("");
                    }
                }
                data.add(row);
            }
            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public static String toTitleCase(String givenString) {
        if (givenString != null) {
            givenString = givenString.replaceAll("_", " ");
            String[] arr = givenString.split(" ");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                sb.append(Character.toUpperCase(arr[i].charAt(0)))
                        .append(arr[i].substring(1)).append(" ");
            }
            return sb.toString().trim();
        } else {
            return null;
        }
    }

    public static Date getToday() {
        Date d1 = null;
        d1 = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
        return d1;
    }

    public static java.sql.Date getDate(String datStr) {
        java.sql.Date res = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date tDate = sdf.parse(datStr);
            res = new Date(tDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String dateToStr(Date d1) {
        String res = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            res = sdf.format(d1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public static boolean isOldDate(Date d1) {
            boolean res = false;
            int t = compDate(d1, getToday());
            if(t<0){
                res = true;
            }
            return res;
    }
    

    public static int compDate(Date d1, Date d2) {
        int res = 0;
        if (d1.getYear() != d2.getYear()) {
            res = d1.getYear() - d2.getYear();
        } else if (d1.getMonth() != d2.getMonth()) {
            res= d1.getMonth() - d2.getMonth();
        }else if (d1.getDate() != d2.getDate()) {
            res= d1.getDate() - d2.getDate();
        }
        return  res;
    }
}
