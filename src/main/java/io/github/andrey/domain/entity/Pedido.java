package io.github.andrey.domain.entity;

import io.github.andrey.domain.enums.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Column(name = "data_pedido")
    private LocalDate dataPedido;
    @Column(name = "total", precision = 20, scale = 2)
    private BigDecimal total;
    @OneToMany(mappedBy = "pedido")
    private List<Item_Pedido> itens;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedido status;

  /*  public List<Item_Pedido> getItens() {
        return itens;
    }

    public void setItens(List<Item_Pedido> itens) {
        this.itens = itens;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", dataPedido=" + dataPedido +
                ", total=" + total +
                '}';
    }*/
}
