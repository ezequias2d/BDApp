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

import Consultas.*;
import com.grr.bdapp.Repositorios.*;
import com.grr.bdapp.Repositorios.Tools.Mode;
import com.grr.bdapp.elfoAPI.exception.data.DataCannotBeAccessedException;
import com.grr.bdapp.objects.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Ezequias
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        root.alignmentProperty().setValue(Pos.CENTER);
        root.setPadding(new Insets(16));
        root.setSpacing(4);

        Button armazem = CreateArmazem();
        Button cliente = CreateCliente();
        Button estoque = CreateEstoque();
        Button fornecedor = CreateFornecedor();
        Button item = CreateItem();
        Button pedido = CreatePedido();
        Button produto = CreateProduto();
        Button consultas = CreateConsultas();
        Button sobre = CreateSobre();

        root.getChildren().addAll(armazem, cliente, estoque, fornecedor, item, pedido, produto, consultas, sobre);

        Scene scene = new Scene(root, 192, 256);

        primaryStage.setTitle("BD");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        
        primaryStage.show();
    }
    
    private Button CreateSobre(){
        Button sobre = new Button();
        sobre.setText("Sobre");
        sobre.setOnAction((ActionEvent) -> {
            UserInputFX.about();
        });
        return sobre;
    }

    private Button CreateConsultas() {
        Button consultas = new Button();
        consultas.setText("Consultas");
        consultas.setOnAction((ActionEvent) -> {
            int m = UserInputFX.showConsultas("Consultas");
            if (m == 0) {
                return;
            }

            AConsulta consulta = null;
            switch (m) {
                case 1:
                    consulta = new Consulta1();
                    break;
                case 2:
                    consulta = new Consulta2();
                    break;
                case 3:
                    consulta = new Consulta3();
                    break;
                case 4:
                    consulta = new Consulta4();
                    break;
                case 5:
                    consulta = new Consulta5();
                    break;
                case 7:
                    consulta = new Consulta7();
                    break;
                case 8:
                    consulta = new Consulta8();
                    break;
                case 9:
                    consulta = new Consulta9();
                    break;
                case 10:
                    consulta = new Consulta10();
                    break;
                case 11:
                    consulta = new Consulta11();
                    break;
            }
            consulta.show(consulta.Execute());
        });
        return consultas;
    }

    private Button CreateArmazem() {
        Button armazem = new Button();
        armazem.setText("Armazens");
        armazem.setOnAction((ActionEvent) -> {
            Mode m = UserInputFX.showMode("Armazem");
            if (m == Mode.NONE) {
                return;
            }
            ArmazemRepositorio resp = new ArmazemRepositorio();
            try {
                Armazem armazem1;
                switch (m) {
                    case SELECT:
                        UserInputFX.showArmazem(resp.toArray(), "Armazens");
                        break;
                    case UPDATE:
                        armazem1 = UserInputFX.showArmazem(resp.toArray(), "Editar armazem");
                        if (armazem1 != null && UserInputFX.editArmazem(armazem1, "Editar armazem")) {
                            resp.update(armazem1);
                        }
                        break;
                    case INSERT:
                        armazem1 = new Armazem(0);
                        if (UserInputFX.editArmazem(armazem1, "Editar armazem")) {
                            resp.add(armazem1);
                        }
                        break;
                    case DELETE:
                        armazem1 = UserInputFX.showArmazem(resp.toArray(), "Deletar armazem");
                        if (armazem1 != null && UserInputFX.confirmationMessage("Confirmação de deletar!", "Deseja realmente deletar o armazem? (Não será possivel recuperar)")) {
                            resp.remove(armazem1);
                        }
                        break;
                }
            } catch (DataCannotBeAccessedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return armazem;
    }

    private Button CreateCliente() {
        Button button = new Button();
        button.setText("Clientes");
        button.setOnAction((ActionEvent) -> {
            Mode m = UserInputFX.showMode("Cliente");
            if (m == Mode.NONE) {
                return;
            }
            ClienteRepositorio resp = new ClienteRepositorio();
            try {
                Cliente cliente;
                switch (m) {
                    case SELECT:
                        UserInputFX.showCliente(resp.toArray(), "Cliente");
                        break;
                    case UPDATE:
                        cliente = UserInputFX.showCliente(resp.toArray(), "Editar cliente");
                        if (cliente != null && UserInputFX.editCliente(cliente, "Editar armazem")) {
                            resp.update(cliente);
                        }
                        break;
                    case INSERT:
                        cliente = new Cliente(0);
                        if (UserInputFX.editCliente(cliente, "Editar cliente")) {
                            resp.add(cliente);
                        }
                        break;
                    case DELETE:
                        cliente = UserInputFX.showCliente(resp.toArray(), "Deletar cliente");
                        if (cliente != null && UserInputFX.confirmationMessage("Confirmação de deletar!", "Deseja realmente deletar o cliente? (Não será possivel recuperar)")) {
                            resp.remove(cliente);
                        }
                        break;
                }
            } catch (DataCannotBeAccessedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return button;
    }

    private Button CreateEstoque() {
        Button button = new Button();
        button.setText("Estoque");
        button.setOnAction((ActionEvent) -> {
            Mode m = UserInputFX.showMode("Estoque");
            if (m == Mode.NONE) {
                return;
            }
            ProdutoRepositorio respP = new ProdutoRepositorio();
            ArmazemRepositorio respA = new ArmazemRepositorio();
            try {
                Armazem armazem = UserInputFX.showArmazem(respA.toArray(), "Armazens");
                Estoque estoque;
                switch (m) {
                    case SELECT:
                        if (armazem != null) {
                            UserInputFX.showEstoque(armazem.getEstoque(), "Estoques do armazem '" + armazem.getNome() + "' e ID '" + armazem.getIdentity() + "'");
                        }
                        break;
                    case UPDATE:
                        if (armazem != null) {
                            estoque = UserInputFX.showEstoque(armazem.getEstoque(), "Estoques do armazem '" + armazem.getNome() + "' e ID '" + armazem.getIdentity() + "'");
                            if (estoque != null && UserInputFX.editEstoque(estoque, respA.toArray(), respP.toArray(), "Editar estoque")) {
                                armazem.remove(estoque);
                                armazem.add(estoque);
                                respA.update(armazem);
                            }
                        }
                        break;
                    case INSERT:
                        if (armazem != null) {
                            estoque = new Estoque();
                            if (UserInputFX.editEstoque(estoque, respA.toArray(), respP.toArray(), "Editar estoque")) {
                                armazem.add(estoque);
                                respA.update(armazem);
                            }
                        }
                        break;
                    case DELETE:
                        if (armazem != null) {
                            estoque = UserInputFX.showEstoque(armazem.getEstoque(), "Estoques do armazem '" + armazem.getNome() + "' e ID '" + armazem.getIdentity() + "'");
                            if (estoque != null && UserInputFX.confirmationMessage("Confirmação de deletar!", "Deseja realmente deletar o estoque? (Não será possivel recuperar)")) {
                                armazem.remove(estoque);
                                respA.update(armazem);
                            }
                        }
                        break;
                }
            } catch (DataCannotBeAccessedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return button;
    }

    private Button CreateFornecedor() {
        Button button = new Button();
        button.setText("Fornecedor");
        button.setOnAction((ActionEvent) -> {
            Mode m = UserInputFX.showMode("Fornecedor");
            if (m == Mode.NONE) {
                return;
            }
            FornecedorRepositorio resp = new FornecedorRepositorio();
            try {
                Fornecedor fornecedor = null;
                switch (m) {
                    case SELECT:
                        UserInputFX.showFornecedor(resp.toArray(), "Fornecedores");
                        break;
                    case UPDATE:
                        fornecedor = UserInputFX.showFornecedor(resp.toArray(), "Fornecedores");
                        if (fornecedor != null && UserInputFX.editFornecedor(fornecedor, "Fornecedor - edição")) {
                            resp.update(fornecedor);
                        }
                        break;
                    case INSERT:
                        fornecedor = new Fornecedor(-1);
                        if (UserInputFX.editFornecedor(fornecedor, "Fornecedor - adicionar")) {
                            resp.add(fornecedor);
                        }
                        break;
                    case DELETE:
                        fornecedor = UserInputFX.showFornecedor(resp.toArray(), "Fornecedores");
                        if (fornecedor != null && UserInputFX.confirmationMessage("Confirmação de deletar!", "Deseja realmente deletar o fornecedor? (Não será possivel recuperar)")) {
                            resp.remove(fornecedor);
                        }
                        break;
                }
            } catch (DataCannotBeAccessedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return button;
    }

    private Button CreateItem() {
        Button button = new Button();
        button.setText("Item");
        button.setOnAction((ActionEvent) -> {
            Mode m = UserInputFX.showMode("Item");
            if (m == Mode.NONE) {
                return;
            }
            PedidoRepositorio respP = new PedidoRepositorio();
            ProdutoRepositorio respPR = new ProdutoRepositorio();
            try {
                Pedido pedido = UserInputFX.showPedido(respP.toArray(), "Selecione o pedido");
                Item item = null;
                if (pedido != null) {
                    switch (m) {
                        case SELECT:
                            UserInputFX.showItem(pedido.getItems(), "Itens");
                            break;
                        case UPDATE:
                            item = UserInputFX.showItem(pedido.getItems(), "Selecione o item");
                            if (item != null && UserInputFX.editItem(item, respPR.toArray(), respP.toArray(), "Item - editar")) {
                                respP.update(pedido);
                            }
                            break;
                        case INSERT:
                            item = new Item(-1, pedido.getIdentity());
                            if (UserInputFX.editItem(item, respPR.toArray(), respP.toArray(), "Item - novo")) {
                                if (pedido.getIdentity() != item.getIDPedido()) {
                                    pedido = respP.get(item.getIDPedido());
                                }
                                pedido.add(item);
                                respP.update(pedido);
                            }
                            break;
                        case DELETE:
                            if (UserInputFX.confirmationMessage("Confirmação de deletar!", "Deseja realmente deletar o item? (Não será possivel recuperar)")) {
                                pedido.remove(item);
                                respP.update(pedido);
                            }
                            break;
                    }
                }
            } catch (DataCannotBeAccessedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return button;
    }

    private Button CreatePedido() {
        Button button = new Button();
        button.setText("Pedido");
        button.setOnAction((ActionEvent) -> {
            Mode m = UserInputFX.showMode("Pedido");
            if (m == Mode.NONE) {
                return;
            }
            PedidoRepositorio resp = new PedidoRepositorio();
            ClienteRepositorio respC = new ClienteRepositorio();
            try {
                Pedido pedido = null;
                switch (m) {
                    case SELECT:
                        UserInputFX.showPedido(resp.toArray(), "Pedidos");
                        break;
                    case UPDATE:
                        pedido = UserInputFX.showPedido(resp.toArray(), "Pedidos");
                        if (pedido != null && UserInputFX.editPedido(pedido, respC.toArray(), "Edição de pedido")) {
                            resp.update(pedido);
                        }
                        break;
                    case INSERT:
                        pedido = new Pedido(-1, -1);
                        if (UserInputFX.editPedido(pedido, respC.toArray(), "Criar pedido")) {
                            resp.add(pedido);
                        }
                        break;
                    case DELETE:
                        pedido = UserInputFX.showPedido(resp.toArray(), "Pedidos");
                        if (pedido != null && UserInputFX.confirmationMessage("Confirmação de deletar!", "Deseja realmente deletar o pedido? (Não será possivel recuperar)")) {
                            resp.remove(pedido);
                        }
                        break;
                }

            } catch (DataCannotBeAccessedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return button;
    }

    private Button CreateProduto() {
        Button button = new Button();
        button.setText("Produto");
        button.setOnAction((ActionEvent) -> {
            Mode m = UserInputFX.showMode("Produto");
            if (m == Mode.NONE) {
                return;
            }
            ProdutoRepositorio resp = new ProdutoRepositorio();
            FornecedorRepositorio respF = new FornecedorRepositorio();
            try {
                Produto produto = null;
                switch (m) {
                    case SELECT:
                        UserInputFX.showProdutos(resp.toArray(), "Produtos");
                        break;
                    case UPDATE:
                        produto = UserInputFX.showProdutos(resp.toArray(), "Produtos - Atualizar");
                        if (produto != null && UserInputFX.editProduto(produto, respF.toArray(), "Edição de produto")) {
                            resp.update(produto);
                        }
                        break;
                    case INSERT:
                        produto = new Produto(-1, -1);
                        if (UserInputFX.editProduto(produto, respF.toArray(), "Criar produto")) {
                            resp.add(produto);
                        }
                        break;
                    case DELETE:
                        produto = UserInputFX.showProdutos(resp.toArray(), "Produtos - Deletar");
                        if (produto != null && UserInputFX.confirmationMessage("Confirmação de deletar!", "Deseja realmente deletar o produto? (Não será possivel recuperar)")) {
                            resp.remove(produto);
                        }
                        break;
                }

            } catch (DataCannotBeAccessedException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return button;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }
}
