package com.wan.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author 万星明
 * @Date 2019/1/17
 */
@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
public class Goods implements Serializable {

    //主键回填
    @TableId(type = IdType.AUTO)
    private int id;//商品ID
    private String title;//商品标题
    private String ginfo;//商品详情
    private int gcount;//商品剩余数

    private int tid;//分类表的外键

    private double allprice;//商品总价
    private double price;//商品价格
    private String gimage;//商品图片

}
