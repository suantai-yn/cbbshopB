package com.example.cbbshop.service;

import com.example.cbbshop.mapper.FavoriteMapper;
import com.example.cbbshop.model.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Autowired
    public FavoriteService(FavoriteMapper favoriteMapper) {
        this.favoriteMapper = favoriteMapper;
    }

    public void addFavorite(Favorite favorite) {
        // 添加到收藏逻辑
        favoriteMapper.addFavorite(favorite);
    }

    public void removeFavorite(Integer favoriteId) {
        // 删除收藏逻辑
        favoriteMapper.removeFavorite(favoriteId);
    }

    public List<Favorite> getFavoriteList(Integer buyerId) {
        // 获取收藏列表逻辑
        return favoriteMapper.findByBuyerId(buyerId);
    }

    public boolean checkIfFavorite(Integer buyerId, Integer productId) {
        // 查询数据库检查是否存在该用户和商品的收藏关系
        return favoriteMapper.existsByBuyerIdAndProductId(buyerId, productId);
    }
    public Integer getFavoriteId(Integer buyerId, Integer productId) {
        // 查询数据库获取该用户对商品的收藏 ID
        return favoriteMapper.findFavoriteIdByBuyerIdAndProductId(buyerId, productId);
    }
}