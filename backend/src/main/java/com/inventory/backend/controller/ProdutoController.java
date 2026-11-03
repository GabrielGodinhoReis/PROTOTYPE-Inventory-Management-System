
package com.inventory.backend.controller;

import com.inventory.backend.model.Produto;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import java.util.List;
import java.util.ArrayList;

@RequestMapping("/produtos")
@RestController
public class ProdutoController {

    private List<Produto> produtos = new ArrayList<>();

    private Long proximoId = 5L;

    public ProdutoController() {
        produtos.add(new Produto(1L, "Mouse Gamer", "Periféricos", 149.90, 10));
        produtos.add(new Produto(2L, "Teclado Mecânico", "Periféricos", 299.90, 5));
        produtos.add(new Produto(3L, "Monitor", "Monitores", 899.90, 3));
    }

    // LISTAR PRODUTOS
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtos;
    }

    // CADASTRAR PRODUTO
    @PostMapping
    public Produto cadastrarProduto(@Valid @RequestBody Produto produto) {

        produto.setId(proximoId);

        produtos.add(produto);

        proximoId++;

        return produto;
    }

    // ATUALIZAR PRODUTO
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(
            @PathVariable Long id,
            @Valid @RequestBody Produto produtoAtualizado) {

        for (Produto produto : produtos) {

            if (produto.getId().equals(id)) {

                produto.setNome(produtoAtualizado.getNome());
                produto.setCategoria(produtoAtualizado.getCategoria());
                produto.setPreco(produtoAtualizado.getPreco());
                produto.setQuantidade(produtoAtualizado.getQuantidade());

                return ResponseEntity.ok(produto);
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Produto não encontrado.");
    }

    // EXCLUIR PRODUTO
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirProduto(@PathVariable Long id) {

        for (Produto produto : produtos) {

            if (produto.getId().equals(id)) {

                produtos.remove(produto);

                return ResponseEntity.ok("Produto excluído com sucesso!");
            }
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("Produto não encontrado.");
    }
}
