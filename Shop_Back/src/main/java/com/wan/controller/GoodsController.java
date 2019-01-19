package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wan.pojo.Goods;
import com.wan.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/1/18
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private IGoodService goodService;

    //引入Fast文件保管客户端
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Value("${fdfs.goodsImgPath}")
    private String goodsImgPath;


    /**
     * 查询返回商品列表
     * @return
     */
    @RequestMapping("/goodList")
    public String goodsManager(Model model){

        //调用注册中心服务接口取得集合
        List<Goods> goodsList = goodService.findAllGoods();
        System.out.println("已经进入查询商品列表:"+goodsList);
        //将集合放入Model中返回视图
        model.addAttribute("goods",goodsList);
        //将配置文件中的商品图片前缀返回页面
        model.addAttribute("goodsImgPath",goodsImgPath);
        return  "goodList";
    }

    @RequestMapping("/insertGoods")
    public String insertGoods(Goods goods){
        //调用服务接口插入商品信息
        goodService.insertGoods(goods);
        return "redirect:/goods/goodList";
    }



    /**
     * 添加商品——上传商品的图片
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/uploadimg")
    @ResponseBody
    public String uploadImg(MultipartFile file) throws Exception {
        System.out.println("上传的文件名称"+file.getOriginalFilename());
        System.out.println("上传的文件大小"+file.getSize());

        //通过客户端上传图片并生成缩略图到FastDFS服务上
        StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(
                file.getInputStream(),
                file.getSize(),
                "jpg",
                null
        );

        System.out.println("上传到FastDFS的文件路径："+result.getFullPath());
        //上传成功,返回JSON(控件必须)
        return "{\"imgpath\":\"" + result.getFullPath() +"\"}";
    }


}
