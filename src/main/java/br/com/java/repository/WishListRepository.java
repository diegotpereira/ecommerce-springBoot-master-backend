package br.com.java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.java.model.WishList;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer>{
    List<WishList> findAllByUserIdOrderByCreatedDateDesc(Integer userId);
}
