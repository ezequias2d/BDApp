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
public class Consulta2 extends AConsulta{

    public String produtoNome;
    public String fornecedorNome;
    public String descricao;
   

    public String toString() {
        return "SELECT nomeProduto.nome AS ProdutoNome, fornecedor.nome AS FornecedorNome, descricaoProduto.descricao As Descricao\n"
                + "FROM Produto produto, Fornecedor fornecedor, NomeProduto nomeProduto, DescricaoProduto descricaoProduto\n"
                + "WHERE produto.IdFornecedor = fornecedor.IdFornecedor AND nomeProduto.IdProduto = produto.IdProduto AND descricaoProduto.IdProduto = produto.IdProduto\n"
                + "ORDER BY nomeProduto.nome ASC";
    }

    public void show(Consulta2[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta2> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta2> table = new TableView();

        TableColumn<Consulta2, String> columnProdutoNome = new TableColumn<>();
        TableColumn<Consulta2, String> columnFornecedorNome = new TableColumn<>();
        TableColumn<Consulta2, String> columnDescricao = new TableColumn<>();

        columnProdutoNome.setText("Produto nome");
        columnFornecedorNome.setText("Fornecedor nome");
        columnDescricao.setText("Descrição");

        columnProdutoNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().produtoNome));
        columnFornecedorNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().fornecedorNome));
        columnDescricao.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().descricao));

        table.getColumns().add(columnProdutoNome);
        table.getColumns().add(columnFornecedorNome);
        table.getColumns().add(columnDescricao);

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
        Consulta2 consulta = new Consulta2();
        consulta.produtoNome = result.getString(1);
        consulta.fornecedorNome = result.getString(2);
        consulta.descricao = result.getString(3);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta2[] c = Arrays.copyOf(consulta, consulta.length, Consulta2[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        return new String[0];
    }
}

