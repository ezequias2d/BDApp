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

import java.math.BigDecimal;
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
public class Consulta3 extends AConsulta {
    
    public long numPedidos;
    public BigDecimal percentual;
    
    public String toString(){
        return "SELECT COUNT(*) AS NumPedidos, 100.0 * COUNT(*) / ( SELECT COUNT(*)\n"
                + "                                                    FROM Pedido subPedido\n"
                + "                                                    WHERE subPedido.dataPedido BETWEEN (TIMESTAMP '2019-12-31' - INTERVAL '4 MONTH') AND (TIMESTAMP '2019-12-31')) AS Percentual\n"
                + "FROM Pedido pedido, Cliente cliente\n"
                + "WHERE (SELECT SUM (itemSub.precoTotal)\n"
                + "            FROM ItemPedido itemSub\n"
                + "            WHERE itemSub.IdPedido = pedido.IdPedido\n"
                + "	  		GROUP BY itemSub.idPedido) > 7000::MONEY AND\n"
                + "        UPPER(pedido.modoEncomenda) = 'PRESENCIAL' AND\n"
                + "        (Pedido.dataPedido BETWEEN (TIMESTAMP '2019-12-31' - INTERVAL '4 MONTH') AND (TIMESTAMP '2019-12-31')) AND\n"
                + "        pedido.idCliente = cliente.IdCliente AND\n"
                + "        cliente.limiteCredito > 5000::MONEY AND\n"
                + "		UPPER(cliente.pais) = 'CHILE'\n"
                + "GROUP BY UPPER(cliente.pais)";
    }
    
    public void show(Consulta3[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta3> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta3> table = new TableView();

        TableColumn<Consulta3, String> columnNumPedidos = new TableColumn<>();
        TableColumn<Consulta3, String> columnPercentual = new TableColumn<>();

        columnNumPedidos.setText("Número de pedidos");
        columnPercentual.setText("Percentual");

        columnNumPedidos.setCellValueFactory((param) -> new SimpleStringProperty(Long.toString(param.getValue().numPedidos)));
        columnPercentual.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().percentual.toString()));

        table.getColumns().add(columnNumPedidos);
        table.getColumns().add(columnPercentual);

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
        Consulta3 consulta = new Consulta3();
        consulta.numPedidos = result.getLong(1);
        consulta.percentual = result.getBigDecimal(2);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta3[] c = Arrays.copyOf(consulta, consulta.length, Consulta3[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        return new String[0];
    }
}
