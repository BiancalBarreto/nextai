package br.com.fiap.netxai.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.netxai.model.Produto;
import br.com.fiap.netxai.repository.ProdutoRepository;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("produto")
@Slf4j
public class ProdutoController {

    @Autowired
    ProdutoRepository produtoRepository;

    @GetMapping
    public List<Produto> index(){
        return produtoRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto create(@RequestBody Produto produto){
        log.info("cadastrando produto: {}", produto);
        return produtoRepository.save(produto);
    }

    @GetMapping("{id}")
    public ResponseEntity<Produto> get(@PathVariable Long id){
        log.info("Buscar por id");
        return produtoRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void destorty(@PathVariable Long id){
        log.info("apagando produto", id);
        verificarSeExisteProduto(id);
        produtoRepository.deleteById(id);
    }

    @PutMapping("{id}")
    public Produto update(@PathVariable Long id, @RequestBody Produto produto){
        log.info("atualizando produto por id {} para id {}", id, produto);
        verificarSeExisteProduto(id);
        produto.setId(id);
        return produtoRepository.save(produto);
    }

    private void verificarSeExisteProduto(Long id){
        produtoRepository.findById(id).orElseThrow( 
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado")
        );
    }

}
