package br.com.java.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.com.java.model.WishList;
import br.com.java.repository.WishListRepository;

@Service
@Transactional
public class WishListService {
    
    private final WishListRepository repository;

    public WishListService(WishListRepository repository) {
        this.repository = repository;
    }

    public void createWishlist(WishList wishList) {
        repository.save(wishList);
    }

    public List<WishList> readWishList(Integer userId){

        return repository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }
}
