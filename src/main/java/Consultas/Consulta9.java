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
public class Consulta9 extends AConsulta{
 
    public String somaDeVendas;
    public String nomeCliente;
    public double mes;
    
    public String toString(){
        return "SELECT SUM (item.precototal), cliente.nome, EXTRACT (MONTH FROM (pedido.dataPedido))\n"
                + "FROM Pedido pedido, Cliente cliente, itemPedido item\n"
                + "WHERE EXTRACT (YEAR FROM pedido.dataPedido) = 2017\n"
                + "AND pedido.idCliente = cliente.idCliente\n"
                + "AND cliente.pais = 'Estados Unidos'\n"
                + "AND item.idPedido = pedido.idPedido\n"
                + "GROUP BY cliente.nome, pedido.dataPedido\n"
                + "ORDER BY pedido.dataPedido DESC";
    }
    
    public void show(Consulta9[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta9> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta9> table = new TableView();

        TableColumn<Consulta9, String> columnSomaVendas = new TableColumn<>();
        TableColumn<Consulta9, String> columnNomeCliente = new TableColumn<>();
        TableColumn<Consulta9, String> columnMes = new TableColumn<>();

        columnSomaVendas.setText("Soma de Vendas");
        columnNomeCliente.setText("Nome cliente");
        columnMes.setText("Mês");

        columnSomaVendas.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().somaDeVendas));
        columnNomeCliente.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().nomeCliente));
        columnMes.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString((int)param.getValue().mes)));

        table.getColumns().add(columnSomaVendas);
        table.getColumns().add(columnNomeCliente);
        table.getColumns().add(columnMes);

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
        Consulta9 consulta = new Consulta9();
        consulta.somaDeVendas = result.getString(1);
        consulta.nomeCliente = result.getString(2);
        consulta.mes = result.getDouble(3);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta9[] c = Arrays.copyOf(consulta, consulta.length, Consulta9[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        return new String[0];
    }
}
