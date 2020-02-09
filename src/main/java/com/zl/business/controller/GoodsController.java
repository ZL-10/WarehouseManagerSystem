package com.zl.business.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zl.business.domain.Goods;
import com.zl.business.domain.Provider;
import com.zl.business.service.GoodsService;
import com.zl.business.service.ProviderService;
import com.zl.business.vo.GoodsVo;
import com.zl.sys.common.DataGridView;
import com.zl.sys.common.MyFileUtils;
import com.zl.sys.common.ResultObj;
import com.zl.sys.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    @Lazy
    private GoodsService goodsService;

    @Autowired
    @Lazy
    private ProviderService providerService;

    @Autowired
    private FileService fileService;

    /**
     * 查询商品
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo){
        try{
            IPage<Goods> page = new Page<>(goodsVo.getPage(), goodsVo.getLimit());
            QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
            List<Goods> goods = goodsService.loadAllGoods(page,queryWrapper,goodsVo);
            for (Goods good : goods) {
                Provider provider = this.providerService.getById(good.getProviderid());
                if(null!=provider) {
                    good.setProvidername(provider.getProvidername());
                }
            }
            return new DataGridView(page.getTotal(), goods);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 添加商品
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/addGoods")
    public ResultObj addGoods(GoodsVo goodsVo){
        try{
            if(goodsVo.getGoodsimg()!=null) {
                goodsVo.setGoodsimg(this.fileService.uploadFile(goodsVo.getGoodsimg()));
            }
            this.goodsService.save(goodsVo);
            return ResultObj.ADD_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }



    }

    /**
     * 更新商品
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/updateGoods")
    public ResultObj updateGoods(GoodsVo goodsVo){
        try{
            if(goodsVo.getGoodsimg()!=null) {
                this.fileService.removeFile(this.goodsService.getById(goodsVo.getId()).getGoodsimg());
                goodsVo.setGoodsimg(this.fileService.uploadFile(goodsVo.getGoodsimg()));

            }
            this.goodsService.updateById(goodsVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


    /**
     * 删除商品
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/deleteGoods")
    public ResultObj deleteGoods(GoodsVo goodsVo){
        try{
            if(goodsVo.getGoodsimg()!=null) {
                this.fileService.removeFile(goodsVo.getGoodsimg());
            }
            this.goodsService.removeById(goodsVo);
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


    /**
     * 删除商品
     * @param ids
     * @return
     */
    @RequestMapping(value = "/batchDeleteGoods")
    public ResultObj batchDeleteGoods(Integer[] ids){
        try{
            this.goodsService.removeByIds(Arrays.asList(ids));
            return ResultObj.DELETE_SUCCESS;
        }catch(Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 获取所有商品
     * @return
     */
    @RequestMapping(value = "/loadAllGoodsForSelect")
    public DataGridView loadAllGoodsForSelect(){
        try{
//            List<Goods> goods = ;

            return new DataGridView(this.goodsService.list());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询该供货商id下的货物
     * @param providerId
     * @return
     */
    @RequestMapping(value = "/loadGoodsByProviderId")
    public DataGridView loadGoodsByProviderId(Integer providerId){
        try{
            if(providerId!=0){
                List<Goods> goods = this.goodsService.loadGoodsByProviderId(providerId);
                String providername = this.providerService.getById(providerId).getProvidername();
                for(Goods good:goods){
                    good.setProvidername(providername);
                }
                return new DataGridView(goods);
            }else{
                return new DataGridView(this.goodsService.list());
            }


        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

