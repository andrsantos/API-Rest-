package io.github.andrey.rest;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Data

public class ApiErrors {

    @Getter
    private List<String> erros;

    public ApiErrors(String mensagemErro){
        this.erros = Arrays.asList(mensagemErro);
    }

    public ApiErrors(List<String> erros) {
        this.erros = erros;
    }
}
