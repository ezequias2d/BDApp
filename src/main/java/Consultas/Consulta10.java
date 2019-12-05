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
import java.sql.Timestamp;
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
public class Consulta10 extends AConsulta{
    
    public long count;
    public String nomeProduto;
    public int idProduto;
    public Timestamp dataPedido;
    
    public String toString(){
        return "SELECT SUM(item.precototal), nomeP.nome, produto.idProduto\n"
                + "FROM Produto produto, NomeProduto nomeP, ItemPedido item, Pedido pedido\n"
                + "WHERE produto.idProduto = nomeP.idProduto\n"
                + "	AND produto.idProduto = item.idProduto\n"
                + "	AND item.idPedido = pedido.idPedido\n"
                + "	AND (EXTRACT(YEAR FROM(pedido.dataPedido)) = 2016\n"
                + "		OR EXTRACT(YEAR FROM(pedido.dataPedido)) = 2017\n"
                + "		OR EXTRACT(YEAR FROM(pedido.dataPedido)) = 2019)\n"
                + "	AND EXTRACT(MONTH FROM(pedido.dataPedido)) BETWEEN 1 AND 3\n"
                + "GROUP BY nomeP.nome, produto.idProduto, pedido.dataPedido\n"
                + "ORDER BY SUM DESC";
    }
    
    public void show(Consulta10[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta10> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta10> table = new TableView();

        TableColumn<Consulta10, String> columnCount = new TableColumn<>();
        TableColumn<Consulta10, String> columnNomeProduto = new TableColumn<>();
        TableColumn<Consulta10, String> columnIDProduto = new TableColumn<>();
        TableColumn<Consulta10, String> columnDataPedido = new TableColumn<>();

        columnCount.setText("Count");
        columnNomeProduto.setText("Nome produto");
        columnIDProduto.setText("ID Produto");
        columnDataPedido.setText("Data Pedido");

        columnCount.setCellValueFactory((param) -> new SimpleStringProperty(Long.toString((int)param.getValue().count)));
        columnNomeProduto.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().nomeProduto));
        columnIDProduto.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().idProduto)));
        columnDataPedido.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().dataPedido.toString()));

        table.getColumns().add(columnCount);
        table.getColumns().add(columnNomeProduto);
        table.getColumns().add(columnIDProduto);
        table.getColumns().add(columnDataPedido);

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
        Consulta10 consulta = new Consulta10();
        consulta.count = result.getLong(1);
        consulta.nomeProduto = result.getString(2);
        consulta.idProduto = result.getInt(3);
        consulta.dataPedido = result.getTimestamp(4);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta10[] c = Arrays.copyOf(consulta, consulta.length, Consulta10[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        return new String[0];
    }
}
