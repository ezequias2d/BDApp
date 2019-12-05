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
package com.grr.bdapp;

import com.grr.bdapp.Repositorios.Tools.Mode;
import com.grr.bdapp.objects.Armazem;
import com.grr.bdapp.objects.Cliente;
import com.grr.bdapp.objects.Estoque;
import com.grr.bdapp.objects.Fornecedor;
import com.grr.bdapp.objects.Item;
import com.grr.bdapp.objects.Pedido;
import com.grr.bdapp.objects.Produto;
import java.sql.Timestamp;
import java.util.LinkedList;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import java.util.Collections;

/**
 * GUI com usuario usando JavaFX
 * @author Ezequias Moises dos Santos Silva
 * @version 0.1.0
 */
public class UserInputFX {
    
    public static Mode showMode(String title){
        HBox hBox = new HBox();
        Dialog<Mode> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        ComboBox<Mode> combo = new ComboBox<>();
        combo.autosize();
        
        Mode[] mode = { Mode.SELECT, Mode.INSERT, Mode.DELETE, Mode.UPDATE };
        combo.setItems(FXCollections.observableArrayList(mode));
      
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        hBox.getChildren().add(combo);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(combo, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(combo::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                return combo.getSelectionModel().getSelectedItem();
            }
            return Mode.NONE;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    public static int showConsultas(String title){
        HBox hBox = new HBox();
        Dialog<Integer> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        ComboBox<Integer> combo = new ComboBox<>();
        combo.autosize();
        
        Integer[] mode = { 1, 2, 3, 4, 5, 7, 8, 9, 10, 11 };
        combo.setItems(FXCollections.observableArrayList(mode));
      
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        hBox.getChildren().add(combo);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(combo, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(combo::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                return combo.getSelectionModel().getSelectedItem();
            }
            return 0;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    
    public static boolean editArmazem(Armazem armazem, String title){
        VBox root = new VBox();
        Dialog<Boolean> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        HBox hboxNome = new HBox();
        HBox hboxTamanho = new HBox();
        HBox hboxEndereco = new HBox();
        
        Label lNome = new Label();
        Label lTamanho = new Label();
        Label lEndereco = new Label();
        
        TextField nome = new TextField();
        TextField tamanho = new TextField();
        TextField endereco = new TextField();
        
        hboxNome.getChildren().addAll(lNome, nome);
        lNome.setText("Nome:");
        nome.setText(armazem.getNome());
        
        hboxTamanho.getChildren().addAll(lTamanho, tamanho);
        lTamanho.setText("Tamanho:");
        tamanho.setText(Integer.toString(armazem.getTamanho()));
        
        hboxEndereco.getChildren().addAll(lEndereco, endereco);
        lEndereco.setText("Endereço:");
        endereco.setText(armazem.getEndereco());
        
        onlyNumberTextField(tamanho);
      
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        root.getChildren().addAll(hboxNome, hboxTamanho, hboxEndereco);
        root.setPadding(new Insets(16));

        dialog.getDialogPane().setContent(root);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                armazem.setNome(nome.getText());
                armazem.setEndereco(endereco.getText());
                armazem.setTamanho(Integer.parseInt(tamanho.getText()));
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    public static boolean editCliente(Cliente cliente, String title){
        VBox root = new VBox();
        Dialog<Boolean> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        HBox hboxNome = new HBox();
        HBox hboxCpf = new HBox();
        HBox hboxPais = new HBox();
        HBox hboxEstado = new HBox();
        HBox hboxCidade = new HBox();
        HBox hboxGarantia = new HBox();
        HBox hboxLimiteCredito = new HBox();
        
        Label lNome = new Label();
        Label lCpf = new Label();
        Label lPais = new Label();
        Label lEstado = new Label();
        Label lCidade = new Label();
        Label lGarantia = new Label();
        Label lLimiteCredito = new Label();
        
        
        TextField nome = new TextField();
        TextField cpf = new TextField();
        TextField pais = new TextField();
        TextField estado = new TextField();
        TextField cidade = new TextField();
        DatePicker garantia = new DatePicker();
        TextField limiteCredito = new TextField();
        
        hboxNome.getChildren().addAll(lNome, nome);
        lNome.setText("Nome:");
        nome.setText(cliente.getNome());
        
        hboxCpf.getChildren().addAll(lCpf, cpf);
        lCpf.setText("CPF:");
        cpf.setText(cliente.getCpf());
        
        hboxPais.getChildren().addAll(lPais, pais);
        lPais.setText("Pais:");
        pais.setText(cliente.getPais());
        
        hboxEstado.getChildren().addAll(lEstado, estado);
        lEstado.setText("Estado:");
        estado.setText(cliente.getEstado());
        
        hboxCidade.getChildren().addAll(lCidade, cidade);
        lCidade.setText("Cidade:");
        cidade.setText(cliente.getCidade());
        
        hboxGarantia.getChildren().addAll(lGarantia, garantia);
        lGarantia.setText("Garantia:");
        garantia.setValue(cliente.getGarantia().toLocalDateTime().toLocalDate());
        
        hboxLimiteCredito.getChildren().addAll(lLimiteCredito, limiteCredito);
        lLimiteCredito.setText("Limite Credito:");
        limiteCredito.setText(cliente.getLimiteCredito());
      
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        root.getChildren().addAll(hboxNome, hboxCpf, hboxPais, hboxEstado, hboxCidade, hboxGarantia, hboxLimiteCredito);
        root.setPadding(new Insets(16));

        dialog.getDialogPane().setContent(root);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                cliente.setNome(nome.getText());
                cliente.setCpf(cpf.getText());
                cliente.setPais(pais.getText());
                cliente.setEstado(estado.getText());
                cliente.setCidade(cidade.getText());
                cliente.setGarantia(Timestamp.valueOf(garantia.getValue().atStartOfDay()));
                cliente.setLimiteCredito(limiteCredito.getText());
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    static Produto p;
    
    public static boolean editEstoque(Estoque estoque, Armazem[] armazens, Produto[] produtos, String title){
        VBox root = new VBox();
        Dialog<Boolean> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        HBox hboxCodigo = new HBox();
        HBox hboxQuantidade = new HBox();
        HBox hboxProduto = new HBox();
        HBox hboxIDArmazem = new HBox();
        
        Label lCodigo = new Label();
        Label lQuantidade = new Label();
        Label lProduto = new Label();
        Label lIDArmazem = new Label();
        
        TextField codigo = new TextField();
        TextField quantidade = new TextField();
        Button produto = new Button();
        Button armazem = new Button();
        
        p = estoque.getProduto();
        
        hboxCodigo.getChildren().addAll(lCodigo, codigo);
        lCodigo.setText("Código:");
        codigo.setText(Integer.toString(estoque.getCodigo()));
        
        hboxQuantidade.getChildren().addAll(lQuantidade, quantidade);
        lQuantidade.setText("Quantidade:");
        quantidade.setText(Integer.toString(estoque.getQuantidade()));
        
        hboxProduto.getChildren().addAll(lProduto, produto);
        lProduto.setText("Produto:");
        produto.setText(Integer.toString(estoque.getProduto().getIdentity()));
        produto.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Produto s = UserInputFX.showProdutos(produtos, "Selecione o produto para o estoque");
                    if (s != null){
                        p = s;
                        produto.setText(Integer.toString(p.getIdentity()));
                    }
                }
            }
        );
        
        hboxIDArmazem.getChildren().addAll(lIDArmazem, armazem);
        lIDArmazem.setText("ID Armazem:");
        armazem.setText(Integer.toString(estoque.getIDArmazem()));
        armazem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Armazem s = UserInputFX.showArmazem(armazens, "Selecione o armazem para o estoque");
                    if (s != null){
                        armazem.setText(Integer.toString(s.getIdentity()));
                    }
                }
            }
        );
        onlyNumberTextField(codigo);
        onlyNumberTextField(quantidade);
      
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        root.getChildren().addAll(hboxCodigo, hboxQuantidade, hboxProduto, hboxIDArmazem);
        
        root.setPadding(new Insets(16));

        dialog.getDialogPane().setContent(root);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                estoque.setCodigo(Integer.parseInt(codigo.getText()));
                estoque.setQuantidade(Integer.parseInt(quantidade.getText()));
                estoque.setIDArmazem(Integer.parseInt(armazem.getText()));
                estoque.setProduto(p);
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    public static boolean editFornecedor(Fornecedor fornecedor, String title){
        VBox root = new VBox();
        Dialog<Boolean> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        HBox hboxNome = new HBox();
        HBox hboxLocalidade = new HBox();
        HBox hboxTipo = new HBox();
        HBox hboxCpf = new HBox();
        HBox hboxCnpj = new HBox();
        
        Label lNome = new Label();
        Label lLocalidade = new Label();
        Label lTipo = new Label();
        Label lCpf = new Label();
        Label lCnpj = new Label();
        
        TextField nome = new TextField();
        TextField localidade = new TextField();
        TextField tipo = new TextField();
        TextField cpf = new TextField();
        TextField cnpj = new TextField();
        
        
        hboxNome.getChildren().addAll(lNome, nome);
        lNome.setText("Nome:");
        nome.setText(fornecedor.getNome());
        
        hboxLocalidade.getChildren().addAll(lLocalidade, localidade);
        lLocalidade.setText("Localidade:");
        localidade.setText(fornecedor.getLocalidade());
        
        hboxTipo.getChildren().addAll(lTipo, tipo);
        lTipo.setText("Tipo:");
        tipo.setText(fornecedor.getTipo());
        
        hboxCpf.getChildren().addAll(lCpf, cpf);
        lCpf.setText("CPF:");
        cpf.setText(fornecedor.getCPF());
        
        hboxCnpj.getChildren().addAll(lCnpj, cnpj);
        lCnpj.setText("CNPJ:");
        cnpj.setText(fornecedor.getCNPJ());
        
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        root.getChildren().addAll(hboxNome, hboxLocalidade, hboxTipo, hboxCpf, hboxCnpj);
        root.setPadding(new Insets(16));

        dialog.getDialogPane().setContent(root);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                fornecedor.setNome(nome.getText());
                fornecedor.setLocalidade(localidade.getText());
                fornecedor.setTipo(tipo.getText());
                fornecedor.setCPF(cpf.getText());
                fornecedor.setCNPJ(cnpj.getText());
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    public static boolean editItem(Item item, Produto[] produtos, Pedido[] pedidos, String title){
        VBox root = new VBox();
        Dialog<Boolean> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        HBox hboxProduto = new HBox();
        HBox hboxPedido = new HBox();
        HBox hboxQuantidade = new HBox();
        HBox hboxPrecoVenda = new HBox();
        HBox hboxPrecoTotal = new HBox();
        
        Label lProduto = new Label();
        Label lPedido = new Label();
        Label lQuantidade = new Label();
        Label lPrecoVenda = new Label();
        Label lPrecoTotal = new Label();
        
        Button produto = new Button();
        Button pedido = new Button();
        TextField quantidade = new TextField();
        TextField precoVenda = new TextField();
        TextField precoTotal = new TextField();
        
        onlyNumberTextField(quantidade);
        
        hboxProduto.getChildren().addAll(lProduto, produto);
        lProduto.setText("Produto:");
        produto.setText(Integer.toString(item.getIDProduto()));
        produto.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Produto s = UserInputFX.showProdutos(produtos, "Selecione o produto para o Item");
                    if (s != null){
                        produto.setText(Integer.toString(s.getIdentity()));
                    }
                }
            }
        );
        
        hboxPedido.getChildren().addAll(lPedido, pedido);
        lPedido.setText("Pedido:");
        pedido.setText(Integer.toString(item.getIDPedido()));
        pedido.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Pedido s = UserInputFX.showPedido(pedidos, "Selecione o pedido para o Item");
                    if (s != null){
                        pedido.setText(Integer.toString(s.getIdentity()));
                    }
                }
            }
        );
        
        hboxQuantidade.getChildren().addAll(lQuantidade, quantidade);
        lQuantidade.setText("Quantidade:");
        quantidade.setText(Integer.toString(item.getQuantidade()));
        
        hboxPrecoVenda.getChildren().addAll(lPrecoVenda, precoVenda);
        lPrecoVenda.setText("Preço Venda:");
        precoVenda.setText(item.getPrecoVenda());
        
        hboxPrecoVenda.getChildren().addAll(lPrecoTotal, precoTotal);
        lPrecoTotal.setText("Preço Total:");
        precoTotal.setText(item.getPrecoTotal());
        
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        root.getChildren().addAll(hboxProduto, hboxPedido, hboxQuantidade, hboxPrecoVenda, hboxPrecoTotal);
        root.setPadding(new Insets(16));

        dialog.getDialogPane().setContent(root);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                item.setIDProduto(Integer.parseInt(produto.getText()));
                item.setIDPedido(Integer.parseInt(pedido.getText()));
                item.setQuantidade(Integer.parseInt(quantidade.getText()));
                item.setPrecoVenda(precoVenda.getText());
                item.setPrecoTotal(precoTotal.getText());
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    public static boolean editPedido(Pedido pedido, Cliente[] clientes, String title){
        VBox root = new VBox();
        Dialog<Boolean> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        HBox hboxDataPedido = new HBox();
        HBox hboxModoEncomenda = new HBox();
        HBox hboxStatus = new HBox();
        HBox hboxDataPrazo = new HBox();
        HBox hboxValor = new HBox();
        HBox hboxIDCliente = new HBox();
        
        Label lDataPedido = new Label();
        Label lModoEncomenda = new Label();
        Label lStatus = new Label();
        Label lDataPrazo = new Label();
        Label lValor = new Label();
        Label lIDCliente = new Label();
        
        DatePicker dataPedido = new DatePicker();
        TextField modoEncomenda = new TextField();
        TextField status = new TextField();
        DatePicker dataPrazo = new DatePicker();
        TextField valor = new TextField();
        Button idCliente = new Button();
        
        hboxDataPedido.getChildren().addAll(lDataPedido, dataPedido);
        lDataPedido.setText("Data Pedido:");
        dataPedido.setValue(pedido.getDataPedido().toLocalDateTime().toLocalDate());
        
        hboxModoEncomenda.getChildren().addAll(lModoEncomenda, modoEncomenda);
        lModoEncomenda.setText("Modo encomenda");
        modoEncomenda.setText(pedido.getModoEncomenda());
        
        hboxStatus.getChildren().addAll(lStatus, status);
        lStatus.setText("Status:");
        status.setText(pedido.getStatusPedido());
        
        hboxDataPrazo.getChildren().addAll(lDataPrazo, dataPrazo);
        lDataPrazo.setText("Data Prazo:");
        dataPrazo.setValue(pedido.getDataPrazo().toLocalDateTime().toLocalDate());
      
        hboxValor.getChildren().addAll(lValor, valor);
        lValor.setText("Valor:");
        valor.setText(pedido.getValor());
        
        hboxIDCliente.getChildren().addAll(lIDCliente, idCliente);
        lIDCliente.setText("Cliente:");
        idCliente.setText(Integer.toString(pedido.getIDCliente()));
        idCliente.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Cliente s = UserInputFX.showCliente(clientes, "Selecione o cliente para o pedido");
                    if (s != null){
                        idCliente.setText(Integer.toString(s.getIdentity()));
                    }
                }
            }
        );
        
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        root.getChildren().addAll(hboxDataPedido, hboxModoEncomenda, hboxStatus, hboxDataPrazo, hboxValor, hboxIDCliente);
        root.setPadding(new Insets(16));

        dialog.getDialogPane().setContent(root);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                pedido.setDataPedido(Timestamp.valueOf(dataPedido.getValue().atStartOfDay()));
                pedido.setDataPrazo(Timestamp.valueOf(dataPrazo.getValue().atStartOfDay()));
                pedido.setModoEncomenda(modoEncomenda.getText());
                pedido.setStatusPedido(status.getText());
                pedido.setValor(valor.getText());
                pedido.setIDCliente(Integer.parseInt(idCliente.getText()));
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    public static boolean editProduto(Produto produto, Fornecedor[] fornecedores, String title){
        VBox root = new VBox();
        Dialog<Boolean> dialog = new Dialog<>();
        ButtonType backButton = new ButtonType("OK!", ButtonBar.ButtonData.OK_DONE);
        
        HBox hboxStatus = new HBox();
        HBox hboxPreco = new HBox();
        HBox hboxPrecoMinimo = new HBox();
        HBox hboxGarantia = new HBox();
        HBox hboxFornecedor = new HBox();
        HBox hboxNomes = new HBox();
        HBox hboxDescricoes = new HBox();
        
        Label lStatus = new Label();
        Label lPreco = new Label();
        Label lPrecoMinimo = new Label();
        Label lGarantia = new Label();
        Label lFornecedor = new Label();
        Label lNomes = new Label();
        Label lDescricoes = new Label();
        
        TextField status = new TextField();
        TextField preco = new TextField();
        TextField precoMinimo = new TextField();
        DatePicker garantia = new DatePicker();
        TextField idFornecedor = new TextField();
        
        
        LinkedList<String> nomes = new LinkedList<>();
        ComboBox<String> nomeList = new ComboBox<>();
        HBox buttonsNome = new HBox();
        Button addNome = new Button();
        Button removeNome = new Button();
        Button editNome = new Button();
        
        buttonsNome.getChildren().addAll(addNome, removeNome, editNome);
        addNome.setText("+");
        removeNome.setText("-");
        editNome.setText("E");
        
        addNome.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String text = getText("Nome", "");
                    if (text != null){
                        nomes.add(text);
                        nomeList.setItems(FXCollections.observableArrayList(nomes));
                    }
                }
            }
        );
        
        removeNome.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String text = nomeList.getSelectionModel().getSelectedItem();
                    if (text != null){
                        nomes.remove(text);
                        nomeList.setItems(FXCollections.observableArrayList(nomes));
                    }
                }
            }
        );
        
        editNome.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = nomeList.getSelectionModel().getSelectedItem();
                    if (selected != null){
                        String text = getText("Nome", selected);
                        if (text != null){
                            nomes.remove(selected);
                            nomes.add(text);
                            nomeList.setItems(FXCollections.observableArrayList(nomes));
                        }
                    }
                }
            }
        );
        
        //Descrições
        
        LinkedList<String> descricoes = new LinkedList<>();
        ComboBox<String> descricaoList = new ComboBox<>();
        HBox buttonsDescricao = new HBox();
        Button addDescricao = new Button();
        Button removeDescricao = new Button();
        Button editDescricao = new Button();
        
        buttonsDescricao.getChildren().addAll(addDescricao, removeDescricao, editDescricao);
        addDescricao.setText("+");
        removeDescricao.setText("-");
        editDescricao.setText("E");
        
        addDescricao.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String text = getText("Descrição", "");
                    if (text != null){
                        descricoes.add(text);
                        descricaoList.setItems(FXCollections.observableArrayList(descricoes));
                    }
                }
            }
        );
        
        removeDescricao.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String text = descricaoList.getSelectionModel().getSelectedItem();
                    if (text != null){
                        descricoes.remove(text);
                        descricaoList.setItems(FXCollections.observableArrayList(descricoes));
                    }
                }
            }
        );
        
        editDescricao.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selected = descricaoList.getSelectionModel().getSelectedItem();
                    if (selected != null){
                        String text = getText("Descrição", selected);
                        if (text != null){
                            descricoes.remove(selected);
                            descricoes.add(text);
                            descricaoList.setItems(FXCollections.observableArrayList(descricoes));
                        }
                    }
                }
            }
        );
        
        Button idCliente = new Button();
        
        hboxStatus.getChildren().addAll(lStatus, status);
        lStatus.setText("Status:");
        status.setText(produto.getStatus());
        
        hboxPreco.getChildren().addAll(lPreco, preco);
        lPreco.setText("Preço:");
        preco.setText(produto.getPreco());
        
        hboxPrecoMinimo.getChildren().addAll(lPrecoMinimo, precoMinimo);
        lPrecoMinimo.setText("Preço mínimo:");
        precoMinimo.setText(produto.getPrecoMinimo());
        
        hboxGarantia.getChildren().addAll(lGarantia, garantia);
        lGarantia.setText("Garantia:");
        garantia.setValue(produto.getGarantia().toLocalDateTime().toLocalDate());
        
        hboxNomes.getChildren().addAll(lNomes, nomeList, buttonsNome);
        lNomes.setText("Nomes:");
        Collections.addAll(nomes, produto.getNomes());
        nomeList.setItems(FXCollections.observableArrayList(nomes));
        
        hboxDescricoes.getChildren().addAll(lDescricoes, descricaoList, buttonsDescricao);
        lDescricoes.setText("Descrições:");
        Collections.addAll(descricoes, produto.getDescricoes());
        descricaoList.setItems(FXCollections.observableArrayList(descricoes));
        
        hboxFornecedor.getChildren().addAll(lFornecedor, idFornecedor);
        lFornecedor.setText("Fornecedor");
        idFornecedor.setText(Integer.toString(produto.getIDFornecedor()));
        idFornecedor.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Fornecedor fornecedor = showFornecedor(fornecedores, "Fornecedor do produto");
                    if (fornecedor != null){
                        idFornecedor.setText(Integer.toString(fornecedor.getIdentity()));
                    }
                }
            }
        );
        
        dialog.setContentText(title);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(backButton, ButtonType.CANCEL);

        root.getChildren().addAll(hboxStatus, hboxPreco, hboxPrecoMinimo, hboxGarantia, hboxFornecedor, hboxNomes, hboxDescricoes);
        root.setPadding(new Insets(16));

        dialog.getDialogPane().setContent(root);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(backButton)) {
                produto.setStatus(status.getText());
                produto.setPreco(preco.getText());
                produto.setPrecoMinimo(precoMinimo.getText());
                produto.setGarantia(Timestamp.valueOf(garantia.getValue().atStartOfDay()));
                produto.setIDFornecedor(Integer.parseInt(idFornecedor.getText()));
                produto.setNomes(nomes);
                produto.setDescricoes(descricoes);
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }

    public static Armazem showArmazem(Armazem[] armazens, String title) {
        HBox hBox = new HBox();
        Dialog<Armazem> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Armazem> table = new TableView();
        
        TableColumn<Armazem, String> columnID = new  TableColumn<>();
        TableColumn<Armazem, String> columnNome = new  TableColumn<>();
        TableColumn<Armazem, String> columnTamanho = new  TableColumn<>();
        TableColumn<Armazem, String> columnEndereco = new  TableColumn<>();
        
        columnID.setText("ID");
        columnNome.setText("Nome");
        columnTamanho.setText("Tamanho");
        columnEndereco.setText("Endereço");
        
        columnID.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIdentity())));
        columnNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()));
        columnTamanho.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getTamanho())));
        columnEndereco.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEndereco()));
        
        table.getColumns().add(columnID);
        table.getColumns().add(columnNome);
        table.getColumns().add(columnTamanho);
        table.getColumns().add(columnEndereco);
        
        table.setItems(FXCollections.observableArrayList(armazens));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static Estoque showEstoque(Estoque[] estoques, String title) {
        HBox hBox = new HBox();
        Dialog<Estoque> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Estoque> table = new TableView();
        
        TableColumn<Estoque, String> columnID = new  TableColumn<>();
        TableColumn<Estoque, String> columnCodigo = new  TableColumn<>();
        TableColumn<Estoque, String> columnQuantidade = new  TableColumn<>();
        TableColumn<Estoque, String> columnIDArmazem = new TableColumn<>();
        TableColumn<Estoque, String> columnProduto = new  TableColumn<>();
        
        columnID.setText("ID");
        columnCodigo.setText("Código");
        columnQuantidade.setText("Quantidade");
        columnIDArmazem.setText("ID Armazem");
        columnProduto.setText("Produto");
        
        columnID.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIdentity())));
        columnCodigo.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getCodigo())));
        columnQuantidade.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getQuantidade())));
        columnIDArmazem.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIDArmazem())));
        columnProduto.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getProduto().getIdentity())));
        
        table.getColumns().addAll(columnID, columnCodigo, columnQuantidade, columnIDArmazem, columnProduto);
        
        table.setItems(FXCollections.observableArrayList(estoques));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static Cliente showCliente(Cliente[] clientes, String title) {
        HBox hBox = new HBox();
        Dialog<Cliente> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Cliente> table = new TableView();
        
        TableColumn<Cliente, String> columnID = new  TableColumn<>();
        TableColumn<Cliente, String> columnNome = new  TableColumn<>();
        TableColumn<Cliente, String> columnCPF = new  TableColumn<>();
        TableColumn<Cliente, String> columnPais = new  TableColumn<>();
        TableColumn<Cliente, String> columnEstado = new  TableColumn<>();
        TableColumn<Cliente, String> columnCidade = new  TableColumn<>();
        TableColumn<Cliente, String> columnGarantia = new  TableColumn<>();
        TableColumn<Cliente, String> columnDataCadastro = new  TableColumn<>();
        TableColumn<Cliente, String> columnLimiteCredito = new  TableColumn<>();
        
        columnID.setText("ID");
        columnNome.setText("Nome");
        columnCPF.setText("CPF");
        columnPais.setText("Pais");
        columnEstado.setText("Estado");
        columnCidade.setText("Cidade");
        columnGarantia.setText("Garantia");
        columnDataCadastro.setText("Data cadastro");
        columnLimiteCredito.setText("Limite credito");

        columnID.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIdentity())));
        columnNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()));
        columnCPF.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCpf()));
        columnPais.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getPais()));
        columnEstado.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getEstado()));
        columnCidade.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCidade()));
        columnGarantia.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getGarantia().toString()));
        columnDataCadastro.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDataCadastro().toString()));
        columnLimiteCredito.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getLimiteCredito()));
        
        table.getColumns().add(columnID);
        table.getColumns().add(columnNome);
        table.getColumns().add(columnCPF);
        table.getColumns().add(columnPais);
        table.getColumns().add(columnEstado);
        table.getColumns().add(columnCidade);
        table.getColumns().add(columnGarantia);
        table.getColumns().add(columnDataCadastro);
        table.getColumns().add(columnLimiteCredito);
        
        table.setItems(FXCollections.observableArrayList(clientes));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static Fornecedor showFornecedor(Fornecedor[] fornecedores, String title) {
        HBox hBox = new HBox();
        Dialog<Fornecedor> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Fornecedor> table = new TableView();
        
        TableColumn<Fornecedor, String> columnID = new  TableColumn<>();
        TableColumn<Fornecedor, String> columnNome = new  TableColumn<>();
        TableColumn<Fornecedor, String> columnLocalidade = new  TableColumn<>();
        TableColumn<Fornecedor, String> columnTipo = new  TableColumn<>();
        TableColumn<Fornecedor, String> columnCpf = new  TableColumn<>();
        TableColumn<Fornecedor, String> columnCnpj = new  TableColumn<>();
        
        columnID.setText("ID");
        columnNome.setText("Nome");
        columnLocalidade.setText("Localidade");
        columnTipo.setText("Tipo");
        columnCpf.setText("CPF");
        columnCnpj.setText("CNPJ");
        
        columnID.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIdentity())));
        columnNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getNome()));
        columnLocalidade.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getLocalidade()));
        columnTipo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTipo()));
        columnCpf.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCPF()));
        columnCnpj.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getCNPJ()));
        
        table.getColumns().add(columnID);
        table.getColumns().add(columnNome);
        table.getColumns().add(columnLocalidade);
        table.getColumns().add(columnTipo);
        table.getColumns().add(columnCpf);
        table.getColumns().add(columnCnpj);
        
        table.setItems(FXCollections.observableArrayList(fornecedores));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static Item showItem(Item[] itens, String title) {
        HBox hBox = new HBox();
        Dialog<Item> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Item> table = new TableView();
        
        TableColumn<Item, String> columnIDProduto = new  TableColumn<>();
        TableColumn<Item, String> columnIDPedido = new  TableColumn<>();
        TableColumn<Item, String> columnQuantidade = new  TableColumn<>();
        TableColumn<Item, String> columnPrecoVenda = new  TableColumn<>();
        TableColumn<Item, String> columnPrecoTotal = new  TableColumn<>();
        
        columnIDProduto.setText("ID Produto");
        columnIDPedido.setText("ID Pedido");
        columnQuantidade.setText("Quantiade");
        columnPrecoVenda.setText("Preço Venda");
        columnPrecoTotal.setText("Preço Total");
        
        columnIDProduto.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIDProduto())));
        columnIDPedido.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIDPedido())));
        columnQuantidade.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getQuantidade())));
        columnPrecoVenda.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getPrecoVenda()));
        columnPrecoTotal.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getPrecoTotal()));
        
        table.getColumns().add(columnIDProduto);
        table.getColumns().add(columnIDPedido);
        table.getColumns().add(columnQuantidade);
        table.getColumns().add(columnPrecoVenda);
        table.getColumns().add(columnPrecoTotal);
        
        table.setItems(FXCollections.observableArrayList(itens));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static Pedido showPedido(Pedido[] pedidos, String title) {
        HBox hBox = new HBox();
        Dialog<Pedido> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Pedido> table = new TableView();
        
        TableColumn<Pedido, String> columnID = new  TableColumn<>();
        TableColumn<Pedido, String> columnDataPedido = new  TableColumn<>();
        TableColumn<Pedido, String> columnModoEncomenda = new  TableColumn<>();
        TableColumn<Pedido, String> columnStatus = new  TableColumn<>();
        TableColumn<Pedido, String> columnPrazo = new  TableColumn<>();
        TableColumn<Pedido, String> columnValor = new  TableColumn<>();
        TableColumn<Pedido, String> columnIDCliente = new  TableColumn<>();
        
        columnID.setText("ID");
        columnDataPedido.setText("Data pedido");
        columnModoEncomenda.setText("Modo encomenda");
        columnStatus.setText("Status");
        columnPrazo.setText("Prazo");
        columnValor.setText("Valor");
        columnIDCliente.setText("ID Cliente");
        
        columnID.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIdentity())));
        columnIDCliente.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIDCliente())));
        
        columnDataPedido.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDataPedido().toString()));
        columnModoEncomenda.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getModoEncomenda()));
        columnStatus.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getStatusPedido()));
        columnPrazo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getDataPrazo().toString()));
        columnValor.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getValor()));
        
        table.getColumns().addAll(columnID, columnIDCliente, columnDataPedido, columnModoEncomenda, columnStatus, columnPrazo, columnValor);
        
        table.setItems(FXCollections.observableArrayList(pedidos));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static Produto showProdutos(Produto[] produtos, String title) {
        HBox hBox = new HBox();
        Dialog<Produto> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<Produto> table = new TableView();
        
        TableColumn<Produto, String> columnID = new  TableColumn<>();
        TableColumn<Produto, String> columnStatus = new  TableColumn<>();
        TableColumn<Produto, String> columnPreco = new  TableColumn<>();
        TableColumn<Produto, String> columnPrecoMinimo = new  TableColumn<>();
        TableColumn<Produto, String> columnGarantia = new  TableColumn<>();
        TableColumn<Produto, String> columnFornecedor = new  TableColumn<>();
        
        columnID.setText("ID");
        columnStatus.setText("Status");
        columnPreco.setText("Preço");
        columnPrecoMinimo.setText("Preço minimo");
        columnGarantia.setText("Garantia");
        columnFornecedor.setText("Fornecedor");
        
        columnID.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIdentity())));
        columnStatus.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getStatus()));
        columnPreco.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getPreco()));
        columnPrecoMinimo.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getPrecoMinimo()));
        columnGarantia.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getGarantia().toString()));
        columnFornecedor.setCellValueFactory((param) -> new SimpleStringProperty(Integer.toString(param.getValue().getIDFornecedor())));
        
        table.getColumns().addAll(columnID, columnStatus, columnPreco, columnPrecoMinimo, columnGarantia, columnFornecedor);
        
        table.setItems(FXCollections.observableArrayList(produtos));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static String showNomes(Produto produto, String title) {
        HBox hBox = new HBox();
        Dialog<String> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<String> table = new TableView();
        
        TableColumn<String, String> columnNome = new  TableColumn<>();
        
        columnNome.setText("Nome");
        columnNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue()));
        table.getColumns().add(columnNome);
        
        table.setItems(FXCollections.observableArrayList(produto.getNomes()));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }
    
    public static String showDescricoes(Produto produto, String title) {
        HBox hBox = new HBox();
        Dialog<String> dialog = new Dialog<>();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);
        
        TableView<String> table = new TableView();
        
        TableColumn<String, String> columnNome = new  TableColumn<>();
        
        columnNome.setText("Nome");
        columnNome.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue()));
        table.getColumns().add(columnNome);
        
        table.setItems(FXCollections.observableArrayList(produto.getDescricoes()));
        table.refresh();
        
        dialog.setContentText(title);
        dialog.setTitle(title);
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
        return dialog.getResult();
    }

    /**
     * Cria janela pedindo um texto
     * @param mensager Mensagem
     * @return Texto
     */
    public static String getText(String mensager) {
        return getText(mensager,"");
    }

    /**
     * Cria janela pedindo um texto e preenche o textField com o content
     * @param mensager Mensagem
     * @param content Conteudo
     * @return Texto
     */
    public static String getText(String mensager, String content) {
        HBox hBox = new HBox();
        Dialog<String> dialog = new Dialog<String>();
        TextField textField = new TextField();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText(mensager);
        dialog.setTitle(mensager);
        dialog.getDialogPane().getButtonTypes().addAll(passButton, ButtonType.CANCEL);

        textField.setPromptText(mensager);
        textField.setText(content);

        hBox.getChildren().add(textField);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(textField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(textField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(passButton)) {
                return textField.getText();
            }
            return null;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }


    /**
     * Cria janela pedindo um CPF
     * @param mensager Mensagem
     * @return CPF
     */
    public static int[] getCPF(String mensager) {
        HBox hBox = new HBox();
        Dialog<String> dialog = new Dialog<String>();
        TextField textField = new TextField();
        ButtonType passButton = new ButtonType("Ok!", ButtonBar.ButtonData.OK_DONE);

        dialog.setContentText(mensager);
        dialog.setTitle("CPF");
        dialog.getDialogPane().getButtonTypes().addAll(passButton, ButtonType.CANCEL);

        textField.setPromptText("CPF");

        hBox.getChildren().add(textField);
        hBox.setPadding(new Insets(16));
        HBox.setHgrow(textField, Priority.ALWAYS);

        dialog.getDialogPane().setContent(hBox);
        Platform.runLater(textField::requestFocus);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(passButton)) {
                return textField.getText();
            }
            return null;
        });
        dialog.showAndWait();
        return UserTools.stringToCpf(dialog.getResult());
    }


    /**
     * Mosta mensagem de alerta
     * @param title Titulo
     * @param header Corpo
     * @param content Conteudo
     */
    public void showMessage(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Mostra mensagem pedindo confimaçao de açao
     * @param title Titulo
     * @param content Conteudo
     * @return Se aceito
     */
    public static boolean confirmationMessage(String title, String content){
        Dialog<Boolean> dialog = new Dialog<Boolean>();
        dialog.setContentText(content);
        dialog.setTitle(title);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.YES, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton.equals(ButtonType.YES)) {
                return true;
            }
            return false;
        });
        dialog.showAndWait();
        return dialog.getResult();
    }
    
    /**
     * Aplica evento a alteraçao de texto de um TextField para aceitar apenas caracteres compativeis com Double
     * @param textFields TextFields
     */
    public static void onlyNumberTextField(TextField... textFields){
        for(TextField textField : textFields) {
            textField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String old, String newText) {
                    if (!newText.isEmpty()) {
                        try {
                            Double.parseDouble(newText);
                        } catch (NumberFormatException e) {
                            if (old != null) {
                                textField.setText(old);
                            } else {
                                textField.clear();
                            }
                        }
                    }
                }
            });
        }
    }

    public static void about(){
        AnchorPane anchorPane = new AnchorPane();

        Dialog<String> dialog = new Dialog<String>();
        Label label = new Label();

        dialog.setContentText("About");
        dialog.setTitle("About");
        dialog.setWidth(400);
        dialog.setHeight(500);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        String aboutText = "BDApp 0.1.1a" +
                "\nAlunos:" +
                "\n     Ezequias Moises dos Santos Silva" +
                "\n     José Sávio Gama Macêdo" +
                "\n     Marcos Vinicius Valério Silva Filho" +
                "\nDisciplina: BD" +
                "\nProfesor: Dimas Cassimiro"+
                "\nSemetre: 2019.2";
        label.setText(aboutText);
        label.setWrapText(true);

        anchorPane.getChildren().add(label);
        AnchorPane.setBottomAnchor(label, 16.0);
        AnchorPane.setLeftAnchor(label, 16.0);
        AnchorPane.setRightAnchor(label, 16.0);
        AnchorPane.setTopAnchor(label, 16.0);

        dialog.getDialogPane().setContent(anchorPane);
        Platform.runLater(label::requestFocus);
        dialog.showAndWait();
    }
}
