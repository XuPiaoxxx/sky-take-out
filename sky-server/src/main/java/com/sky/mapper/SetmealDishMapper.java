package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id来查套餐id
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdByDishId(List<Long> dishIds);

    /**
     * 插入套餐和菜品的一系列对应关系
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
