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
public class Consulta8 extends AConsulta {
    public int idProduto;
    public String preco;
    public String precoMinimo;
    public String max;
    public String min;
    
    public String toString(){
        return "SELECT produto.idproduto, produto.preco, produto.precominimo, MAX(item.precovenda), MIN(item.precovenda)\n"
                + "FROM produto AS produto INNER JOIN itempedido AS item ON produto.idproduto = item.idproduto\n"
                + "WHERE item.idpedido IN (SELECT idpedido FROM pedido AS pedido WHERE EXTRACT (YEAR FROM pedido.datapedido) = ANY (SELECT * FROM Years))\n"
                + "GROUP BY produto.idproduto;";
    }
    
    public void show(Consulta8[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta8> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta8> table = new TableView();

        TableColumn<Consulta8, String> columnIDProduto = new TableColumn<>();
        TableColumn<Consulta8, String> columnPreco = new TableColumn<>();
        TableColumn<Consulta8, String> columnPrecoMinimo = new TableColumn<>();
        TableColumn<Consulta8, String> columnMax = new TableColumn<>();
        TableColumn<Consulta8, String> columnMin = new TableColumn<>();

        columnIDProduto.setText("ID Produto");
        columnPreco.setText("Preço");
        columnPrecoMinimo.setText("Preço Mínimo");
        columnMax.setText("Max");
        columnMin.setText("Min");

        columnIDProduto.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString((int)param.getValue().idProduto)));
        columnPreco.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().preco));
        columnPrecoMinimo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().precoMinimo));
        columnMax.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().max));
        columnMin.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().min));

        table.getColumns().add(columnIDProduto);
        table.getColumns().add(columnPreco);
        table.getColumns().add(columnPrecoMinimo);
        table.getColumns().add(columnMax);
        table.getColumns().add(columnMin);

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
        Consulta8 consulta = new Consulta8();
        consulta.idProduto = result.getInt(1);
        consulta.preco = result.getString(2);
        consulta.precoMinimo = result.getString(3);
        consulta.max = result.getString(4);
        consulta.min = result.getString(5);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta8[] c = Arrays.copyOf(consulta, consulta.length, Consulta8[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        String[] temp = {"DROP TABLE IF EXISTS Years;",
            "CREATE TEMPORARY TABLE Years (year DOUBLE PRECISION);",
            "INSERT INTO Years VALUES (2017), (2018), (2019);"};
        return temp;
    }
}
