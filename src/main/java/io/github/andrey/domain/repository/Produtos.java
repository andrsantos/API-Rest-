package io.github.andrey.domain.repository;

import io.github.andrey.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos  extends JpaRepository<Produto, Integer> {
}
