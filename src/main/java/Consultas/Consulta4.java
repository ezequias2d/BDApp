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
public class Consulta4 extends AConsulta {
    
    public String nomeCompleto;
    public String territorio;
    
    public String toString(){
        return "SELECT cliente.nome AS NomeCompleto, cliente.pais AS Territorio\n"
                + "FROM Cliente cliente\n"
                + "WHERE cliente.pais != ALL(SELECT * FROM temp_countries) AND\n"
                + "        9000 < ALL(    SELECT COUNT(*)\n"
                + "                        FROM Pedido pedido\n"
                + "                        WHERE pedido.IdCliente = cliente.IdCliente AND EXTRACT(YEAR FROM pedido.dataPedido) IN (SELECT * FROM temp_years)\n"
                + "                        GROUP BY EXTRACT(YEAR FROM pedido.dataPedido));";
    }
    
    public void show(Consulta4[] c) {
        HBox hBox = new HBox();
        Dialog<Consulta4> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        TableView<Consulta4> table = new TableView();

        TableColumn<Consulta4, String> columnNomeCompleto = new TableColumn<>();
        TableColumn<Consulta4, String> columnTerritorio = new TableColumn<>();

        columnNomeCompleto.setText("Nome completo");
        columnTerritorio.setText("Territorio");

        columnNomeCompleto.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().nomeCompleto));
        columnTerritorio.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().territorio));

        table.getColumns().add(columnNomeCompleto);
        table.getColumns().add(columnTerritorio);

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
        Consulta4 consulta = new Consulta4();
        consulta.nomeCompleto = result.getString(1);
        consulta.territorio = result.getString(2);
        return consulta;
    }

    @Override
    public void show(AConsulta[] consulta) {
        Consulta4[] c = Arrays.copyOf(consulta, consulta.length, Consulta4[].class);
        show(c);
    }

    @Override
    public String[] getTemp() {
        String[] temp = {
            "CREATE TEMPORARY TABLE IF NOT EXISTS temp_countries (country VARCHAR(255));", 
            "INSERT INTO temp_countries VALUES ('Canadá'),\n"
            + "                                ('Estados Unidos da América'),\n"
            + "                                ('México'),\n"
            + "                                ('Antígua e Barbuda'),\n"
            + "                                ('Bahamas'),\n"
            + "                                ('Barbados'),\n"
            + "                                ('Belize'),\n"
            + "                                ('Costa Rica'),\n"
            + "                                ('Cuba'),\n"
            + "                                ('Dominica'),\n"
            + "                                ('El Salvador'),\n"
            + "                                ('Granada'),\n"
            + "                                ('Guatemala'),\n"
            + "                                ('Haiti'),\n"
            + "                                ('Honduras'),\n"
            + "                                ('Jamaica'),\n"
            + "                                ('Nicarágua'),\n"
            + "                                ('Panamá'),\n"
            + "                                ('República Dominicana'),\n"
            + "                                ('Santa Lúcoa'),\n"
            + "                                ('São Cristóvão e Névis'),\n"
            + "                                ('São Vicente e Granadinas'),\n"
            + "                                ('Trinidad e Tobago'),\n"
            + "                                ('Argentina'),\n"
            + "                                ('Bolívia'),\n"
            + "                                ('Brasil'),\n"
            + "                                ('Chile'),\n"
            + "                                ('Colômbia'),\n"
            + "                                ('Equador'),\n"
            + "                                ('Guiana'),\n"
            + "                                ('Guiana Francesa'),\n"
            + "                                ('Paraguai'),\n"
            + "                                ('Peru'),\n"
            + "                                ('Suriname'),\n"
            + "                                ('Uruguai'),\n"
            + "                                ('Venezuela');\n", 
            "CREATE TEMPORARY TABLE IF NOT EXISTS temp_years (year INTEGER)",
            "INSERT INTO temp_years VALUES(2016), (2017), (2019);"
        };
        
        return temp;
    }
}
