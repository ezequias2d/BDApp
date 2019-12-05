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
public class Consulta1 extends AConsulta {
    public String nome;
    public double ano;
    public double mes;
    public String total;
    
    @Override
    public String[] getTemp(){
        String[] temp = {"CREATE TEMPORARY TABLE IF NOT EXISTS temp_years (year INTEGER);",
                "INSERT INTO temp_years VALUES(2018), (2017),(2015);"};
        return temp;
    }
    
    @Override
    public String toString(){
        return "SELECT nomeProduto.nome, EXTRACT(YEAR FROM pedido.dataPedido), EXTRACT(MONTH FROM pedido.dataPedido), SUM(precoTotal) AS total\n" +
"FROM ItemPedido item, Pedido pedido, NomeProduto nomeProduto\n" +
"WHERE item.IdProduto = nomeProduto.IdProduto AND item.IdPedido = pedido.IdPedido AND EXTRACT(YEAR FROM pedido.dataPedido) IN (SELECT * FROM temp_years)\n" +
"GROUP BY nomeProduto.nome, nomeProduto.IdProduto, EXTRACT(MONTH FROM pedido.dataPedido), EXTRACT(YEAR FROM pedido.dataPedido)\n" +
"ORDER BY (SELECT SUM(itemSub.precoTotal)\n" +
"        	           FROM ItemPedido itemSub\n" +
"         	           WHERE itemSub.IdProduto = nomeProduto.IdProduto\n" +
"	           GROUP BY itemSub.IdProduto) DESC;";
    }
    
   public void show(Consulta1[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta1> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Consulta1> table = new TableView();
        
        TableColumn<Consulta1, String> columnAno = new  TableColumn<>();
        TableColumn<Consulta1, String> columnNome = new  TableColumn<>();
        TableColumn<Consulta1, String> columnMes = new  TableColumn<>();
        TableColumn<Consulta1, String> columnTotal = new  TableColumn<>();
        
        columnNome.setText("Nome");
        columnAno.setText("Ano");
        columnMes.setText("Tamanho");
        columnTotal.setText("Endereço");
        
        columnNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().nome));
        columnAno.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString((int)param.getValue().ano)));
        columnMes.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString((int)param.getValue().mes)));
        columnTotal.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().total));
        
        table.getColumns().add(columnNome);
        table.getColumns().add(columnAno);
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
        Consulta1 consulta = new Consulta1();
        consulta.nome = result.getString(1);
        consulta.ano = result.getDouble(2);
        consulta.mes = result.getDouble(3);
        consulta.total = result.getString(4);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta1[] c = Arrays.copyOf(consulta, consulta.length, Consulta1[].class);
        show(c);
    }
}
