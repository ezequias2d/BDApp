/* 
 * Copyright 2019 Ezequias Moises dos Santos Silva <ezequiasmoises@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Consultas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 *
 * @author Ezequias
 */
public class Consulta7 extends AConsulta {
    public double mes;
    public String total;
    
    public String toString(){
        return "SELECT EXTRACT (MONTH FROM pedido.datapedido), SUM (pedido.valor) AS total\n"
                + "FROM pedido AS pedido\n"
                + "WHERE pedido.idcliente IN (SELECT cliente.idcliente FROM cliente AS cliente WHERE cliente.pais != 'Brasil') AND EXTRACT (YEAR FROM pedido.datapedido) = '2018'\n"
                + "GROUP BY EXTRACT (MONTH FROM pedido.datapedido)\n"
                + "ORDER BY total DESC;";
    }
    
    public void show(Consulta7[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta7> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta7> table = new TableView();

        TableColumn<Consulta7, String> columnMes = new TableColumn<>();
        TableColumn<Consulta7, String> columnTotal = new TableColumn<>();

        columnMes.setText("Mês");
        columnTotal.setText("Total");

        columnMes.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString((int)param.getValue().mes)));
        columnTotal.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().total));

        table.getColumns().add(columnMes);
        table.getColumns().add(columnTotal);

        table.setItems(FXCollections.observableArrayList(c));
        table.refresh();

        dialog.setContentText(getClass().getSimpleName());
        dialog.setTitle(getClass().getSimpleName());
        dialog.getDialogPane().getButtonTypes().addAll(passButton, ButtonType.CANCEL);

        hBox.getChildren().add(table);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(table, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(table::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(passButton)) {
                return table.getSelectionModel().getSelectedItem();
            }
            return null;
        });
        dialog.showAndWait();
    }
    
    @Override
    public AConsulta getResult(ResultSet result) throws SQLException {
        Consulta7 consulta = new Consulta7();
        consulta.mes = result.getDouble(1);
        consulta.total = result.getString(2);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta7[] c = Arrays.copyOf(consulta, consulta.length, Consulta7[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        return new String[0];
    }
}
