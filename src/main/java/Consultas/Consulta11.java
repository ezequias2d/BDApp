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
public class Consulta11 extends AConsulta {
    public Timestamp dataPedido;
    public String precoTotal;
    public String modoEncomenda;
    public int quantidade;
    public String nomeCliente;
    public String credito;
    
    public String toString(){
        return "SELECT pedido.dataPedido, item.precoTotal, pedido.modoEncomenda, item.quantidade, cliente.nome, SUM(cliente.limiteCredito - precoTotal)\n"
                + "FROM Cliente cliente, Pedido pedido, itemPedido item\n"
                + "WHERE (EXTRACT (YEAR FROM pedido.dataPedido) >= 2014 \n"
                + "	   AND EXTRACT (YEAR FROM pedido.dataPedido) < 2017)\n"
                + "AND pedido.idCliente = cliente.idCliente \n"
                + "AND item.idPedido = pedido.idPedido\n"
                + "GROUP BY pedido.dataPedido, item.precoTotal, pedido.modoEncomenda, item.quantidade, cliente.nome, cliente.limiteCredito";
    }
    
    public void show(Consulta11[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta11> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta11> table = new TableView();

        TableColumn<Consulta11, String> columnDataPedido = new TableColumn<>();
        TableColumn<Consulta11, String> columnPrecoTotal = new TableColumn<>();
        TableColumn<Consulta11, String> columnModoEncomenda = new TableColumn<>();
        TableColumn<Consulta11, String> columnQuantidade = new TableColumn<>();
        TableColumn<Consulta11, String> columnNomeCliente = new TableColumn<>();
        TableColumn<Consulta11, String> columnCredito = new TableColumn<>();

        columnDataPedido.setText("Data pedido");
        columnPrecoTotal.setText("Preço total");
        columnModoEncomenda.setText("Modo encomenda");
        columnQuantidade.setText("Quantidade");
        columnNomeCliente.setText("Nome cliente");
        columnCredito.setText("Credito");

        columnDataPedido.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().dataPedido.toString()));
        columnPrecoTotal.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().precoTotal));
        columnModoEncomenda.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().modoEncomenda));
        columnQuantidade.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().quantidade)));
        columnNomeCliente.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().nomeCliente));
        columnCredito.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().credito));

        table.getColumns().add(columnDataPedido);
        table.getColumns().add(columnPrecoTotal);
        table.getColumns().add(columnModoEncomenda);
        table.getColumns().add(columnQuantidade);
        table.getColumns().add(columnNomeCliente);
        table.getColumns().add(columnCredito);
        
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
        Consulta11 consulta = new Consulta11();
        consulta.dataPedido = result.getTimestamp(1);
        consulta.precoTotal = result.getString(2);
        consulta.modoEncomenda = result.getString(3);
        consulta.quantidade = result.getInt(4);
        consulta.nomeCliente = result.getString(5);
        consulta.credito = result.getString(6);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta11[] c = Arrays.copyOf(consulta, consulta.length, Consulta11[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        return new String[0];
    }
}
