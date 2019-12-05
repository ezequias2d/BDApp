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
public class Consulta5 extends AConsulta {
    public long quantidade;
    public double porcentagem;
    
    public String toString(){
        return "SELECT COUNT(*)::decimal AS valor, (COUNT(*)::decimal / total.total * 100) AS percentual\n"
                + "FROM cliente AS cliente, (SELECT COUNT(*)::decimal AS total FROM cliente AS cliente) AS total\n"
                + "WHERE cliente.idcliente IN (SELECT pedido.idcliente FROM pedido AS pedido WHERE pedido.idcliente = cliente.idcliente AND EXTRACT (YEAR FROM pedido.dataPedido) = '2018' AND cliente.pais = 'Estados Unidos' AND cliente.limitecredito < 3000::MONEY)\n"
                + "GROUP BY total.total";
    }
    
    public void show(Consulta5[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta5> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta5> table = new TableView();

        TableColumn<Consulta5, String> columnQuantidade = new TableColumn<>();
        TableColumn<Consulta5, String> columnPorcentagem = new TableColumn<>();

        columnQuantidade.setText("Quantiade");
        columnPorcentagem.setText("Porcentagem");

        columnQuantidade.setCellValueFactory((param) -> new SimpleStringProperty(Long.toString(param.getValue().quantidade)));
        columnPorcentagem.setCellValueFactory((param) -> new SimpleStringProperty(Double.toString(param.getValue().porcentagem)));

        table.getColumns().add(columnQuantidade);
        table.getColumns().add(columnPorcentagem);

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
        Consulta5 consulta = new Consulta5();
        consulta.quantidade = result.getLong(1);
        consulta.porcentagem = result.getDouble(2);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta5[] c = Arrays.copyOf(consulta, consulta.length, Consulta5[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        return new String[0];
    }
}
