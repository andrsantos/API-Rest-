package io.github.andrey.service;


import io.github.andrey.domain.entity.Pedido;
import io.github.andrey.domain.enums.StatusPedido;
import io.github.andrey.rest.dto.PedidoDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface PedidoService {

    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);

    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
