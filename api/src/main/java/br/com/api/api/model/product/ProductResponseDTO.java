package br.com.api.api.model.product;

public record ProductResponseDTO(Integer id, String nome,Double preco){

    public ProductResponseDTO(Product product){
        this(product.getId(), product.getNome(), product.getPreco());
    }
}
