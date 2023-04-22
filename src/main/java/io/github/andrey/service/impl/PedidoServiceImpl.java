package io.github.andrey.service.impl;

import io.github.andrey.domain.entity.Cliente;
import io.github.andrey.domain.entity.Item_Pedido;
import io.github.andrey.domain.entity.Pedido;
import io.github.andrey.domain.entity.Produto;
import io.github.andrey.domain.enums.StatusPedido;
import io.github.andrey.domain.repository.Clientes;
import io.github.andrey.domain.repository.ItemsPedido;
import io.github.andrey.domain.repository.Pedidos;
import io.github.andrey.domain.repository.Produtos;
import io.github.andrey.exception.PedidoNaoEncontradoException;
import io.github.andrey.exception.RegraNegocioException;
import io.github.andrey.rest.dto.ItemPedidoDTO;
import io.github.andrey.rest.dto.PedidoDTO;
import io.github.andrey.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
    private final Pedidos repository;
    private final Clientes ClientesRepository;
    private  final Produtos produtosRepository;
    private final ItemsPedido itemsRepository;



    @Override
    @Transactional
    public Pedido salvar(PedidoDTO dto) {
        Integer idCliente = dto.getCliente();
        Cliente cliente = ClientesRepository.findById(idCliente)
                .orElseThrow( () -> new RegraNegocioException("Código de Cliente Inválida!"));
        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);
        List<Item_Pedido> itemsPedidos = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsRepository.saveAll(itemsPedidos);
        pedido.setItens(itemsPedidos);
        return pedido;

    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {

        return repository.findByIdFetchItems(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        repository.findById(id)
                .map( pedido -> {
                    pedido.setStatus(statusPedido);
                    return repository.save(pedido);
                }).orElseThrow(
                        () -> new PedidoNaoEncontradoException()
                );
    }

    private List<Item_Pedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items!");
        }
        return items
                .stream()
                .map( dto -> {

                    Integer idProduto = dto.getProduto();
                    Produto produto = produtosRepository.findById(idProduto).orElseThrow(
                            () -> new RegraNegocioException("Código de produto inválido!")
                    );
                    Item_Pedido itemPedido = new Item_Pedido();
                    itemPedido.setQuantidade(dto.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;
                }).collect(Collectors.toList());
    }
}
