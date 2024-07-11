package com.example.semestral.models;

import java.util.List;

public class CategoriaResponse {
    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    private List<Categoria> categorias;


}
