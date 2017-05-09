package cn.gomro.mid.core.biz.goods.biz.impl;

import cn.gomro.mid.core.biz.base.AbstractSessionBiz;
import cn.gomro.mid.core.biz.common.entity.AccessTokenEntity;
import cn.gomro.mid.core.biz.goods.biz.GoodsVO;
import cn.gomro.mid.core.biz.goods.biz.IGoodsBizLocal;
import cn.gomro.mid.core.biz.goods.biz.IGoodsBizRemote;
import cn.gomro.mid.core.biz.goods.entity.*;
import cn.gomro.mid.core.biz.goods.service.*;
import cn.gomro.mid.core.common.message.ReturnCode;
import cn.gomro.mid.core.common.message.ReturnMessage;
import cn.gomro.mid.core.common.utils.NumericUtil;
import cn.gomro.mid.core.common.utils.StrEscapeUtils;
import cn.gomro.mid.core.common.utils.Utils;
import cn.gomro.mid.core.search.lucene.IGoodsSearchBiz;
import cn.gomro.mid.core.search.lucene.SearchGoodsVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

/**
 * Created by momo on 2016/8/2.
 */
@Stateless
public class GoodsBiz extends AbstractSessionBiz implements IGoodsBizRemote, IGoodsBizLocal {

    @EJB
    IGoodsBrandService brandService;
    @EJB
    IGoodsCategoryService categoryService;
    @EJB
    IGoodsUnitService goodsUnitService;
    @EJB
    ICorporationService corporationService;
    @EJB
    IWarehouseService warehouseService;
    @EJB
    IGoodsSpecService specService;
    @EJB
    IGoodsService goodsService;
    @EJB
    IGoodsModelService modelService;
    @EJB
    IGoodsInventoryService goodsInventoryService;
    @EJB
    IMemberInventoryService memberInventoryService;
    @EJB
    IFreightTemplateService freightTemplateService;
    @EJB
    IGoodsSearchBiz goodsSearchBiz;

    final Logger logger = LoggerFactory.getLogger(GoodsBiz.class);

    public GoodsBiz() {
    }

    /**
     * Excel数据校验
     *
     * @param goodsVOList
     * @param memberId
     * @return
     */
    @Override
    public ReturnMessage<List<GoodsVO>> checkGoodsVOList(GoodsType type, Integer memberId, List<GoodsVO> goodsVOList) {

        logger.info("Checking GoodsVOList from Excel file, Size: {}", goodsVOList.size());

        String errorRowNumbers = "";
        try {

            for (GoodsVO goodsVO : goodsVOList) {

                if (this.checkGoodsVO(memberId, goodsVO)) {

                    goodsVO.setMemberId(memberId);

                    if (this.existGoodsVO(type, goodsVO)) goodsVO.setFlag(2);//如果查到重复，则标记重复
                    else goodsVO.setFlag(0);//如果未查到重复，标记正常

                    goodsVO.setPrice(String.format("%.4f", Double.parseDouble(goodsVO.getPrice())));//格式化价格
                    goodsVO.setPackageNum(String.valueOf(NumericUtil.parseInt(goodsVO.getPackageNum())));//格式化包装规格格式
                    goodsVO.setMinOrder(String.valueOf(NumericUtil.parseInt(goodsVO.getMinOrder())));//格式化最小起订量
                    goodsVO.setDelivery(String.valueOf(NumericUtil.parseInt(goodsVO.getDelivery())));//格式化交货期

                    //格式化市场价 折扣 库存量
                    if (StringUtils.isNotBlank(goodsVO.getMarketPrice()) || StringUtils.isNotBlank(goodsVO.getDiscount())) {
                        Map<String, Double> map = this.formatPrice(goodsVO);//格式化价格信息
                        goodsVO.setMarketPrice(String.format("%.4f", map.get("marketPrice")));
                        goodsVO.setDiscount(String.format("%.2f", map.get("disCount")));//更新折扣
                    }

                    if (StringUtils.isNotBlank(goodsVO.getInventoryNum())) {
                        goodsVO.setInventoryNum(NumericUtil.parseInt(goodsVO.getInventoryNum()) + "");
                    }
                    //格式化长宽高重
                    if (StringUtils.isNotBlank(goodsVO.getLength())) {
                        goodsVO.setLength(String.format("%.4f", Double.parseDouble(goodsVO.getLength())));
                    }
                    if (StringUtils.isNotBlank(goodsVO.getWidth())) {
                        goodsVO.setWidth(String.format("%.4f", Double.parseDouble(goodsVO.getWidth())));
                    }
                    if (StringUtils.isNotBlank(goodsVO.getHeight())) {
                        goodsVO.setHeight(String.format("%.4f", Double.parseDouble(goodsVO.getHeight())));
                    }
                    if (StringUtils.isNotBlank(goodsVO.getWeight())) {
                        goodsVO.setWeight(String.format("%.4f", Double.parseDouble(goodsVO.getWeight())));
                    }

                } else {
                    goodsVO.setFlag(1);//如果上传的字段非法，则标记非法
                    errorRowNumbers += "[" + goodsVO.getRowNumber() + "]";
                }
            }
            if (!"".equals(errorRowNumbers)) errorRowNumbers += "行格式不正确！";

            logger.info("Check GoodsVOList End. Size: {}", goodsVOList.size());
            /**
             * 这是解决input value quotation problems
             */
            for (GoodsVO vo:goodsVOList){
                vo.setName(StrEscapeUtils.escapeQuots(vo.getName()));
                vo.setModel(StrEscapeUtils.escapeQuots(vo.getModel()));
                vo.setSpec(StrEscapeUtils.escapeQuots(vo.getSpec()));
            }
            return ReturnMessage.success(goodsVOList.size() + "" + errorRowNumbers, goodsVOList);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ReturnMessage.message(ReturnCode.FAILED, ReturnCode.FAILED_SYSTEM, goodsVOList);
    }

    @Override
    public ReturnMessage<GoodsEntity> getItem(Integer id) {
        return goodsService.getItemById(id);

    }

    @Override
    public ReturnMessage<GoodsSpecEntity> getGoodsBySpecId(Integer specId) {
        return specService.getItem(specId);
    }

    @Override
    public ReturnMessage<GoodsSpecEntityManyToOne> getGoodsSpecById(Integer id) {
        return specService.getManyToOneItem(id);
    }

    @Override
    public ReturnMessage<List<GoodsSpecEntityManyToOne>> getGoodsSpecByIds(List<Integer> id) {
        List<GoodsSpecEntityManyToOne> specEntityList = new ArrayList<>();
        for (int i = 0; i < id.size(); i++) {
            specEntityList.add(this.getGoodsSpecById(id.get(i)).getData());
        }

        Collections.sort(specEntityList, new Comparator<GoodsSpecEntityManyToOne>() {
            @Override
            public int compare(GoodsSpecEntityManyToOne o1, GoodsSpecEntityManyToOne o2) {
                return o1.getCorporation().getId() > o2.getCorporation().getId() ? 1 : -1;
            }
        });
        return ReturnMessage.message(specEntityList.size(), specEntityList.size() + "", specEntityList);
    }

    public List<GoodsSpecEntityManyToOne> getGoodsSpecModelGoodByIds(Integer id) {



        return specService.getGoodsSpecModelGoodByIds(id);
    }
    @Override
    public ReturnMessage getName(Integer id) {
        return goodsService.getName(id);
    }

    @Override
    public ReturnMessage getModelName(Integer id) {
        return modelService.getName(id);
    }

    @Override
    public ReturnMessage getSpecName(Integer id) {
        return specService.getName(id);
    }

    @Override
    public ReturnMessage delGoods(Integer id) {
        try {
            GoodsEntity entity = goodsService.getItemById(id).getData();
            if (entity == null) {
                return ReturnMessage.failed(ReturnCode.DELETE_FAILED);
            }
            List<GoodsModelEntity> goodsModelList = entity.getGoodsModelList();
            Date last = new Date();
            entity.setSaled(false);//设置商品标示为不可售
            entity.setDel(true);
            for (GoodsModelEntity m : goodsModelList) {//删除商品下的所有型号
                m.setDel(true);
                m.setSaled(false);
                m.setLast(last);
                for (GoodsSpecEntity s : m.getGoodsSpecList()) {//删除型号下的所有规格
                    s.setSaled(false);
                    s.setDel(true);
                    s.setLast(last);
                    //删除规格总库存
                    goodsInventoryService.delGoodsInventoryBySpec(s.getId());
                    //删除规格分库存
                    memberInventoryService.delMemberInventoryList(s.getId(), s.getCorporation().getId());
                }
            }

            goodsSearchBiz.delDocumentById(entity);//删除索引文件
            goodsService.editItem(entity);//删除商品
            return ReturnMessage.success(ReturnCode.DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setRollbackOnly();
            return ReturnMessage.failed(ReturnCode.DELETE_FAILED);
        }
    }

    @Override
    public ReturnMessage delGoodsSpec(Integer id) {
        try {
            GoodsSpecEntity specEntity = specService.getItem(id).getData();
            specService.delItem(specEntity);
            GoodsEntity goodsEntity = goodsService.getItemBySpecId(id).getData();
            List<GoodsModelEntity> goodsModelList = goodsEntity.getGoodsModelList();

            if (goodsModelList.size() > 0) {
                for (GoodsModelEntity model : goodsModelList) {
                    if (model.getGoodsSpecList().size() == 0) { //如果型号里的规格列表为空，则删除该型号
                        modelService.delItem(model);
                    }
                }
            }

            if(goodsModelList.size()==0){//如果商品的型号列表为空，则删除该商品
                goodsService.delItem(goodsEntity);
            }

            return ReturnMessage.success();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<GoodsSpecEntity> editGoodsSpec(Integer id, String images, Integer brand, Integer category, String unspsc, String hscode, String goodsName, String tags,
                                                        Double priceMin, Double priceMax, Integer sales, Integer views, Boolean goodsSaled, String packages, String technology,
                                                        String goodsDescription, String memo,
                                                        String modelName, String specName, Double price, Double marketPrice, Double discount, Integer minOrder, Integer unit,
                                                        Integer packageNum, Integer packageUnit, Integer delivery, String sku, Double weight, Double length, Double width,
                                                        Double height, String warranty, Integer member, Integer freightTemplate, String description, Boolean saled) {
        GoodsSpecEntity spec = specService.getItem(id).getData();
        if (spec != null) {
            GoodsModelEntity goodsModel = modelService.getItem(spec.getModel()).getData();
            if (goodsModel != null) {
                Integer goodsId = goodsModel.getGoods();
                GoodsEntity goodsEntity = goodsService.getItem(goodsId).getData();
                if (goodsEntity != null) {
                    if (StringUtils.isNotBlank(images)) goodsEntity.setImages(images);//更新图片
                    if (brand != null && brand > 0) {
                        GoodsBrandEntity brandEntity = brandService.getItem(brand).getData();//更新品牌
                        if (brand != null) goodsEntity.setBrand(brandEntity);
                    }
                    if (category != null && category > 0) {
                        GoodsCategoryEntityWithOutTree categoryEntity = categoryService.getCategoryWithOutTreeById(category).getData();//更新分类
                        if (categoryEntity != null) goodsEntity.setCategory(categoryEntity);
                    }
                    if (StringUtils.isNotBlank(unspsc)) goodsEntity.setUnspsc(unspsc);//更新unspsc
                    if (StringUtils.isNotBlank(hscode)) goodsEntity.setHscode(hscode);//更新hscode
                    if (StringUtils.isNotBlank(goodsName)) goodsEntity.setName(goodsName);//更新商品名
                    if (StringUtils.isNotBlank(tags)) goodsEntity.setTags(tags);//更新标签
                    if (priceMin != null && priceMin > 0) goodsEntity.setPriceMin(priceMin);//更新最低价
                    if (priceMax != null && priceMax > 0) goodsEntity.setPriceMax(priceMax);//更新最高价
                    if (sales != null && sales > 0) goodsEntity.setSales(sales);//更新销售数据
                    if (views != null && views > 0) goodsEntity.setViews(views);//更新浏览数据
                    if (goodsSaled != null) goodsEntity.setSaled(goodsSaled);//更新是否可售
                    if (StringUtils.isNotBlank(packages)) goodsEntity.setPackages(packages);//更新包装信息
                    if (StringUtils.isNotBlank(technology)) goodsEntity.setTechnology(technology);//更新技术信息
                    if (StringUtils.isNotBlank(goodsDescription)) goodsEntity.setDescription(goodsDescription);//更新描述
                    if (StringUtils.isNotBlank(memo)) goodsEntity.setMemo(memo);//更新备注
                    goodsEntity.setLast(new Date());//更新时间
                    goodsService.editItem(goodsEntity);
                    if (StringUtils.isNotBlank(modelName)) goodsModel.setName(modelName);
                    goodsModel.setLast(new Date());//更新时间
                    modelService.editItem(goodsModel);
                    if (StringUtils.isNotBlank(specName)) spec.setName(specName);
                    if (price != null && price > 0) spec.setPrice(price);//更新价格
                    if (marketPrice != null && marketPrice > 0) spec.setMarketPrice(marketPrice);//更新市场价格
                    if (discount != null && discount > 0) spec.setDiscount(discount);//更新折扣
                    if (minOrder != null && minOrder > 0) spec.setMinOrder(minOrder);//更新起订量
                    if (unit != null && unit > 0) {
                        GoodsUnitEntity unitEntity = goodsUnitService.getItem(unit).getData();
                        if (unitEntity != null) spec.setUnit(unitEntity);//更新单位
                    }
                    if (packageNum != null && packageNum > 0) spec.setPackageNum(packageNum);//更新包装数量
                    if (packageUnit != null && packageUnit > 0) {
                        GoodsUnitEntity unitEntity = goodsUnitService.getItem(packageUnit).getData();
                        if (unitEntity != null) spec.setUnit(unitEntity);//更新包装单位
                    }
                    if (delivery != null && delivery > 0) spec.setDelivery(delivery);//更新包装数量
                    if (StringUtils.isNotBlank(sku)) spec.setSku(sku);//更新sku
                    if (weight != null && weight > 0) spec.setWeight(weight);//更新重
                    if (length != null && length > 0) spec.setLength(length);//更新长
                    if (width != null && width > 0) spec.setWidth(width);//更新宽
                    if (height != null && height > 0) spec.setHeight(height);//更新高
                    if (StringUtils.isNotBlank(warranty)) spec.setWarranty(warranty);//更新质保说明
                    if (member != null && member > 0) {
                        CorporationEntity corporationEntity = corporationService.getItem(member).getData();
                        if (corporationEntity != null) {
                            spec.setCorporation(corporationEntity);//更新供应商
                        }
                    }
                    if (freightTemplate != null && freightTemplate > 0) {
                        FreightTemplateEntity freightTemplateEntity = freightTemplateService.getItem(freightTemplate).getData();
                        if (freightTemplateEntity != null) {
                            spec.setFreightTemplate(freightTemplate);//更新模板
                        }
                    }
                    if (StringUtils.isNotBlank(description)) spec.setDescription(description);//更新描述
                    if (saled != null) spec.setSaled(saled);//更新可售
                    if (specService.editItem(spec).getCode() >= 0) ;
                    return ReturnMessage.success(spec);
                }
            }
        }

        return ReturnMessage.failed();
    }



    @Override
    public ReturnMessage<GoodsSpecEntityManyToOne> addGoodsBySpec(GoodsSpecEntityManyToOne spec) {
        return specService.addSpec(spec);
    }


    public ReturnMessage<GoodsSpecEntityManyToOne> addGoodsBySpec(GoodsSpecEntityManyToOne spec,Integer modelid, Integer brandId, Integer categoryid,Integer unitid,String goodsname,String modelname,String images,String UNSpsc,String HSCode,Integer packageUnitid) {
        return specService.addSpec(spec,modelid,brandId,categoryid,unitid,goodsname,modelname,images,UNSpsc,HSCode,packageUnitid);
    }
    /**
     * 添加商品及库存信息
     * @param goodsVOList
     * @return
     */
    @Override
    public ReturnMessage<List<GoodsVO>> addGoodsVOList(GoodsType type, List<GoodsVO> goodsVOList) {

        logger.info("Add GoodsVOList start, size: {}", goodsVOList.size());

        try {
            boolean isAllRight = true;//数据是否全部正确
            for (GoodsVO goodsVO : goodsVOList) {
                if (!this.checkGoodsVO(goodsVO.getMemberId(), goodsVO)) {
                    goodsVO.setFlag(1);
                    isAllRight = false;
                }
            }

            if (!isAllRight) {//如果有数据不正确，直接返回
                return ReturnMessage.failed(ReturnCode.DATA_FORMAT_ERROR, goodsVOList);
            }

            boolean del = false;
            boolean saled = true;
            Date last = new Date();
            Date time = last;
            Integer pid = null;
            Set<SearchGoodsVO> searchGoodsList = new HashSet<>();//索引列表
            long timeStart = System.currentTimeMillis();

            logger.info("Recursive add GoodsVOList start, size: {}", goodsVOList.size());
            for (GoodsVO goodsVO : goodsVOList) {
                //添加或获取仓库ID
                WarehouseEntity warehouseEntity = warehouseService.queryOrCreateWarehouseByMemberAndName(goodsVO.getMemberId(), goodsVO.getWarehouseName()).getData();

                //设置库存默认值
                String inventoryNum = goodsVO.getInventoryNum();
                Integer num = NumericUtil.isInteger(inventoryNum) ? NumericUtil.parseInt(goodsVO.getInventoryNum()) : 0;

                //获取或创建分类 如果分类为空则使用其它分类
                GoodsCategoryEntityWithOutTree category = categoryService.queryOrCreateCategoryByName(pid, type, goodsVO.getCategory()).getData();

                //获取商品对象,判断是否是重复商品
                GoodsSpecEntityManyToOne goodsSpec = specService.getExistsGoodsSpec(type, goodsVO.getMemberId(), goodsVO.getBrand(), goodsVO.getName(), goodsVO.getModel(), goodsVO.getSpec(), NumericUtil.parseInt(goodsVO.getPackageNum()), goodsVO.getUnit(), goodsVO.getPackageUnit()).getData();
                if (goodsSpec == null) {//如果未查到重复商品则新建商品

                    GoodsBrandEntity brand = brandService.queryOrCreateBrandByName(type, goodsVO.getBrand()).getData();
                    GoodsUnitEntity unit = goodsUnitService.queryOrCreateUnitByName(type, goodsVO.getUnit()).getData();
                    GoodsUnitEntity packageUnit = goodsUnitService.queryOrCreateUnitByName(type, goodsVO.getPackageUnit()).getData();
                    if (brand == null || category == null || unit == null || packageUnit == null) {
                        return ReturnMessage.message(ReturnCode.FAILED, ReturnCode.FAILED_SYSTEM, goodsVOList);
                    }

                    String images = "";
                    String name = goodsVO.getName();
                    String tags = "";
                    Double priceMin = Double.parseDouble(goodsVO.getPrice());
                    Double priceMax = priceMin;
                    Integer sales = 0;
                    Integer views = 0;
                    String packages = "";
                    String technology = "";
                    String hscode = "";
                    String unspsc = "";
                    String description = goodsVO.getGoodsDescript();
                    String memo = goodsVO.getMemo();
                    //获取会员
                    CorporationEntity corporationEntity = corporationService.getItem(goodsVO.getMemberId()).getData();
                    //创建商品对象
                    GoodsEntity goods = new GoodsEntity(type, images, brand, category, hscode, unspsc, name, tags, priceMin, priceMax, sales, views, saled, packages, technology, description, memo, del, last, time);
                    //增加商品,并取得商品Id
                    Integer goodsId = goodsService.addItem(goods).getData().getId();
                    //创建型号对象
                    String modelName = goodsVO.getModel();
                    GoodsModelEntity model = new GoodsModelEntity(goodsId, modelName, saled, del, last, time);
                    //增加型号,并取得型号Id
                    Integer modelId = modelService.addItem(model).getData().getId();

                    Double price = Double.parseDouble(goodsVO.getPrice());
                    //市场价
                    Double marketPrice = StringUtils.isNotBlank(goodsVO.getMarketPrice()) ? Double.parseDouble(goodsVO.getMarketPrice()) : null;
                    //折扣
                    Double discount = StringUtils.isNotBlank(goodsVO.getDiscount()) ? Double.parseDouble(goodsVO.getDiscount()) : null;
                    //对已检查的字符串进行转成数值
                    Integer minOrder = Integer.parseInt(goodsVO.getMinOrder());
                    Integer packageNum = Integer.parseInt(goodsVO.getPackageNum());
                    Integer delivery = Integer.parseInt(goodsVO.getDelivery());
                    //获取//长//宽//高//重
                    Double length = Utils.reqDoubleFromString(goodsVO.getLength());
                    Double width = Utils.reqDoubleFromString(goodsVO.getWidth());
                    Double height = Utils.reqDoubleFromString(goodsVO.getHeight());
                    Double weight = Utils.reqDoubleFromString(goodsVO.getWeight());
                    //质保期
                    String warranty = goodsVO.getWarranty();
                    String sku = goodsVO.getSku();

                    //创建规格对象
                    String specName = goodsVO.getSpec();
                    GoodsSpecEntity spec = new GoodsSpecEntity(modelId, specName, price, marketPrice, discount, minOrder, unit, packageNum, packageUnit, delivery, sku, weight, length, width, height, warranty, corporationEntity, null, description, saled, del, last, time);
                    //增加规格,并取得规格Id
                    Integer specId = specService.addItem(spec).getData().getId();//增加规格详情

                    //更新商品的 最大和最小 价格
                    //市场上传商品，因一条商品和一条规格对应，暂不需要更新
                    /*Map<String, Double> data = specService.queryGoodsMaxAndMinPrice(goodsId).getData();
                    if (data != null) {
                        goods.setPriceMin(data.get("priceMin"));
                        goods.setPriceMax(data.get("priceMax"));
                        goodsService.editItem(goods);
                    }*/

                    //创建供应商商品库存
                    MemberInventoryEntity inventory = new MemberInventoryEntity(specId, corporationEntity.getId(), warehouseEntity, num, del, last, time);
                    //添加商品库存
                    memberInventoryService.addItem(inventory);
                    //创建商品总库存
                    SpecInventoryEntity specInventoryEntity = new SpecInventoryEntity(corporationEntity.getId(), specId, num, num, 0, false, time, time);
                    //添加商品总库存
                    goodsInventoryService.addItem(specInventoryEntity);
                    goodsVO.setFlag(0);//未重复 标识新增
                    goodsVO.setId(specInventoryEntity.getId());//更新商品ID;

                    //添加搜索索引
                    SearchGoodsVO searchGoodsVO = new SearchGoodsVO(String.valueOf(goodsId),
                            String.valueOf(type.ordinal()), images,
                            String.valueOf(category.getId()), category.getName(),
                            String.valueOf(brand.getId()), brand.getName(), name, model.getName(),
                            spec.getName(), tags,
                            String.valueOf(priceMin),
                            String.valueOf(priceMax),
                            String.valueOf(sales),
                            String.valueOf(views),
                            warehouseEntity.getAddress(),
                            String.valueOf(corporationEntity.getId()),
                            corporationEntity.getQq());
                    searchGoodsList.add(searchGoodsVO);

                } else {//如果按照 品牌	商品名称	型号	规格	包装规格	单位 仓库名称 检索到商品 则更新以下信息

                    Map<String, Double> map = this.formatPrice(goodsVO);//格式化价格信息
                    Integer deliveryDate = NumericUtil.parseInt(goodsVO.getDelivery());
                    deliveryDate = deliveryDate >= 0 ? deliveryDate : 0;//设置货期默认值

                    goodsSpec.setMinOrder(NumericUtil.parseInt(goodsVO.getMinOrder()));//更新最小起订量
                    goodsSpec.setPrice(map.get("price"));//更新价格
                    goodsSpec.setMarketPrice(map.get("marketPrice"));//更新市场价
                    goodsSpec.setDiscount(map.get("disCount"));//更新折扣
                    goodsSpec.setDelivery(deliveryDate);//更新货期
                    goodsSpec.setLength(Utils.reqDoubleFromString(goodsVO.getLength()));//更新长
                    goodsSpec.setWidth(Utils.reqDoubleFromString(goodsVO.getWidth()));//更新宽
                    goodsSpec.setHeight(Utils.reqDoubleFromString(goodsVO.getHeight()));//更新高
                    goodsSpec.setWeight(Utils.reqDoubleFromString(goodsVO.getWeight()));//更新重
                    goodsSpec.setWarranty(goodsVO.getWarranty());//更新质保期
                    goodsSpec.setLast(time);//更新商品操作日期

                    //获取型号
                    GoodsModelEntityManyToOne model = goodsSpec.getModel();
                    //获取商品
                    GoodsEntityManyToOne goods = model.getGoods();

                    //由商品进行级联更新操作
                    //specService.editSpec(goodsSpec);//更新商品信息

                    goods.setCategory(category);//更新分类
                    goods.setDescription(goodsVO.getGoodsDescript());// 商品描述
                    goods.setMemo(goodsVO.getMemo());// 备注

                    /*更新最大和最小价格*/
                    //市场上传商品，因一条商品和一条规格对应，暂不需要更新
                    /*Map<String, Double> data = specService.queryGoodsMaxAndMinPrice(goods.getId()).getData();
                    if (data != null) {
                        goods.setPriceMin(data.get("priceMin"));
                        goods.setPriceMax(data.get("priceMax"));
                        goodsService.editGoodsManyToOne(goods);//更新商品信息
                    }*/

                    //更新库存信息
                    MemberInventoryEntity memberInventoryEntity = memberInventoryService.querySingleMemberInventory(goodsSpec.getId(), goodsVO.getMemberId(), goodsVO.getWarehouseName()).getData();
                    if (memberInventoryEntity != null) {
                        memberInventoryEntity.setNum(NumericUtil.parseInt(goodsVO.getInventoryNum()));
                    } else {
                        WarehouseEntity warehouse = warehouseService.queryOrCreateWarehouseByMemberAndName(goodsVO.getMemberId(), goodsVO.getWarehouseName()).getData();
                        memberInventoryEntity = new MemberInventoryEntity(goodsSpec.getId(), goodsVO.getMemberId(), warehouse, NumericUtil.parseInt(goodsVO.getInventoryNum()), false, time, time);
                        memberInventoryService.addItem(memberInventoryEntity);
                    }

                    Integer amount = 0;
                    Integer useable = 0;
                    Integer freezed = 0;
                    //仅查询商家规格的总库存
                    Integer memberInventoryAmountNum = memberInventoryService.queryMemberInventoryAmount(goodsVO.getMemberId(), goodsSpec.getId()).getData();
                    SpecInventoryEntity specInventoryEntity = goodsInventoryService.getGoodsInventoryBySpec(goodsVO.getMemberId()).getData();

                    //如果未查到总库存记录数则新建
                    if (specInventoryEntity == null) {
                        amount = memberInventoryAmountNum == null ? 0 : memberInventoryAmountNum;
                        //如果没有商品总库存则增加一条记录
                        SpecInventoryEntity goodsInventory = new SpecInventoryEntity(goodsVO.getMemberId(), goodsSpec.getId(), amount, useable, freezed, false, time, time);
                        goodsInventoryService.addItem(goodsInventory);//添加商品总库存
                    }

                    specService.editSpec(goodsSpec);
                    goodsVO.setFlag(3);//重复已修改标识
                    goodsVO.setId(goodsSpec.getId());//更新商品ID;
                }
            }
            //添加索引到索引文件
            goodsSearchBiz.addDocumentList(searchGoodsList);

            logger.info("Upload And add GoodsVOList size : {}, Added SearcherIndex size : {} , Time Spend : {} milliseconds",
                    goodsVOList.size(), searchGoodsList.size(), (System.currentTimeMillis() - timeStart));

            return ReturnMessage.success(goodsVOList);
        } catch (Exception e) {
            ctx.setRollbackOnly();
            logger.error(e.getMessage());
            if (e.getCause() != null) {
                logger.error(e.getCause().getMessage());
            }
            return ReturnMessage.message(ReturnCode.FAILED, ReturnCode.FAILED_SYSTEM, goodsVOList);
        }
    }


    @Override
    public ReturnMessage addItem(GoodsType type, String images, Integer brand, Integer category, String unspsc, String hscode, String goodsName, String tags,
                                 Double priceMin, Double priceMax, Boolean goodsSaled, String packages, String technology, String goodsDescription, String memo,
                                 List<String> modelName, List<Boolean> modelSaled,
                                 List<String> specName, List<Double> price, List<Double> marketPrice, List<Double> discount, List<Integer> minOrder, List<Integer> unit, List<Integer> packageNum,
                                 List<Integer> packageUnit, List<Integer> delivery, List<String> sku, List<Double> weight, List<Double> length, List<Double> width, List<Double> height,
                                 List<String> warranty, List<Integer> member, List<Integer> freightTemplate, List<String> description, List<Boolean> saled) {
        GoodsBrandEntity brandEntity = brandService.getItem(brand).getData();
        GoodsCategoryEntityWithOutTree categoryEntity = categoryService.getCategoryWithOutTreeById(category).getData();

        boolean del = false;
        Date last = new Date();
        Date time = last;

        GoodsEntity goodsEntity = new GoodsEntity(type, images, brandEntity, categoryEntity, hscode, unspsc, goodsName, tags, priceMin, priceMax, 0, 0, goodsSaled, packages, technology, goodsDescription, memo, del, last, time);
        try {
            goodsEntity = goodsService.addItem(goodsEntity).getData();//新增商品
            if (goodsEntity != null && modelName != null) {
                for (int i = 0; i < modelName.size(); i++) {
                    GoodsModelEntity modelEntity = new GoodsModelEntity(goodsEntity.getId(), modelName.get(i), modelSaled.get(i), del, last, time);
                    modelEntity = modelService.addItem(modelEntity).getData();
                    if (modelEntity != null && specName != null) {
                        for (int j = 0; j < specName.size(); j++) {
                            GoodsUnitEntity unitEntity = goodsUnitService.getItem(unit.get(j)).getData();
                            GoodsUnitEntity packageUnitEntity = goodsUnitService.getItem(packageUnit.get(j)).getData();
                            CorporationEntity corporationEntity = corporationService.getItem(member.get(j)).getData();
                            GoodsSpecEntity specEntity = new GoodsSpecEntity(modelEntity.getId(), specName.get(j), price.get(j), marketPrice.get(j), discount.get(j), minOrder.get(j), unitEntity,
                                    packageNum.get(j), packageUnitEntity, delivery.get(j), sku.get(j), weight.get(j), length.get(j), width.get(j), height.get(j), warranty.get(j), corporationEntity,
                                    freightTemplate.get(j), description.get(j), saled.get(j), del, last, time);
                            return specService.addItem(specEntity).getCode() >= 0 ? ReturnMessage.success() : ReturnMessage.failed();
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            ctx.setRollbackOnly();
        }
        return ReturnMessage.failed();
    }

    /**
     * 根据条件查询商品列表
     */
    @Override
    public ReturnMessage<List<GoodsEntity>> queryGoodsList(GoodsType type, Integer brandId, Integer categoryId, boolean onlyCategory, String brandName, String goodsName,
                                                           boolean haseImages, String modelName, String specName, Integer memberId, String memberName, String keyWord,
                                                           Boolean goodsSaled, Boolean specSaled, Boolean modelSaled, String orderBy, Double priceMin, Double priceMax,
                                                           Integer page, Integer size) {

        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        ReturnMessage<List<GoodsEntity>> listReturnMessage = goodsService.queryGoodsList(type, brandId, categoryId, onlyCategory, brandName, goodsName, haseImages, modelName,
                specName, memberId, memberName, keyWord,
                goodsSaled, specSaled, modelSaled, orderBy, priceMin, priceMax, page, size);

        return listReturnMessage;
    }

    /**
     * 根据条件查询商品列表
     */
    @Override
    public ReturnMessage<List<GoodsEntityManyToOne>> queryGoodsManyToOneList(GoodsType type, Integer brandId, String brandName, Integer categoryId, boolean onlyCategory, String goodsName,
                                                                             boolean hasImages, Boolean goodsSaled, Boolean goodsDel, String orderBy, Double priceMin, Double priceMax,
                                                                             Integer page, Integer size) {
        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);
        ReturnMessage<List<GoodsEntityManyToOne>> listReturnMessage = goodsService.queryGoodsManyToOneList(type, brandId, brandName, categoryId, onlyCategory,
                hasImages, goodsName, goodsSaled, goodsDel, orderBy, priceMin, priceMax, page, size);
        return listReturnMessage;
    }

    /**
     * 根据条件查询商品规格列表
     */
    @Override
    public ReturnMessage<List<GoodsSpecEntityManyToOne>> specList(GoodsType type, Integer goodsId, String goodsName,
                                                                  Boolean goodsSaled, Integer brandId, String brandName,
                                                                  Integer categoryId, Integer modelId, String modelName,
                                                                  Boolean modelSaled, Integer specId, String specName,
                                                                  Boolean specSaled, Double priceMin, Double priceMax,
                                                                  Integer memberId, String memberName, String keyWord,
                                                                  String orderBy,
                                                                  boolean hasImages, Integer page, Integer size) {
        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        ReturnMessage<List<GoodsSpecEntityManyToOne>> listReturnMessage = specService.queryGoodsSpecList(type, goodsId, goodsName, goodsSaled, brandId, brandName, categoryId, modelId,
                modelName, modelSaled, specId, specName, specSaled, priceMin, priceMax, memberId, memberName, keyWord, orderBy, hasImages, page, size);

        return listReturnMessage;
    }

    public ReturnMessage<List<GoodsSpecEntityManyToOne>> AudiSpecList(GoodsType type, Integer goodsId, String goodsName,
                                                                  Boolean goodsSaled, Integer brandId, String brandName,
                                                                  Integer categoryId, Integer modelId, String modelName,
                                                                  Boolean modelSaled, Integer specId, String specName,
                                                                  Boolean specSaled, Double priceMin, Double priceMax,
                                                                  Integer memberId, String memberName, String keyWord,
                                                                  String orderBy,
                                                                  boolean hasImages, Integer page, Integer size) {
        size = Utils.reqPageSize(size);
        page = Utils.reqInt(page, 1);

        ReturnMessage<List<GoodsSpecEntityManyToOne>> listReturnMessage = specService.queryAudiSpecList(type, goodsId, goodsName, goodsSaled, brandId, brandName, categoryId, modelId,
                modelName, modelSaled, specId, specName, specSaled, priceMin, priceMax, memberId, memberName, keyWord, orderBy, hasImages, page, size);

        return listReturnMessage;
    }

    /**
     * 根据库存id更新商品信息
     *
     * @param goodsVO
     * @return
     */
    @Override
    public ReturnMessage<GoodsVO> updateGoods(GoodsType type, Integer specId, GoodsVO goodsVO) {

        specId = Utils.reqInt(specId, 0);
        if (specId <= 0) {
            return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
        }

        MemberInventoryEntity inventory = memberInventoryService.querySingleMemberInventory(specId, goodsVO.getMemberId(), goodsVO.getWarehouseName()).getData();
        if (inventory == null) {
            logger.error("需要修改的商品没有库存信息，请核实; id=" + specId);
            return ReturnMessage.failed(ReturnCode.FAILED_SYSTEM);
        }
        int vid = inventory.getWarehouse().getCorporation().getId();
        if (!this.checkGoodsVO(vid, goodsVO)) {
            goodsVO.setFlag(1);
            return ReturnMessage.failed(ReturnCode.DATA_FORMAT_ERROR, goodsVO);
        }
        //先获取唯一商品规格
        GoodsSpecEntity spec = specService.getItem(specId).getData();

        WarehouseEntity warehouse = warehouseService.queryOrCreateWarehouseByMemberAndName(vid, goodsVO.getWarehouseName()).getData();//获取仓库
        GoodsCategoryEntityWithOutTree category = categoryService.queryOrCreateCategoryByName(null, type, goodsVO.getCategory()).getData();//获取分类
        GoodsUnitEntity unit = goodsUnitService.queryOrCreateUnitByName(type, goodsVO.getUnit()).getData();//获取销售单位
        GoodsUnitEntity packageUnit = goodsUnitService.queryOrCreateUnitByName(type, goodsVO.getPackageUnit()).getData();//获取包装单位
        GoodsBrandEntity brand = brandService.queryOrCreateBrandByName(type, goodsVO.getBrand()).getData();//获取品牌

        Map<String, Double> map = this.formatPrice(goodsVO);//格式化价格信息
        Integer deliveryDate = NumericUtil.parseInt(goodsVO.getDelivery());
        deliveryDate = deliveryDate < 0 ? 0 : deliveryDate;//设置货期默认值


        GoodsModelEntity model = modelService.getItem(spec.getModel()).getData();
        GoodsEntity goods = goodsService.getItem(model.getGoods()).getData();
        goodsVO.setId(spec.getId());//添加商品id
        model.setName(goodsVO.getModel());//更新型号
        modelService.editItem(model);//更新型号

        spec.setName(goodsVO.getName());//更新名称
        spec.setName(goodsVO.getSpec());//更新规格
        spec.setPackageNum(NumericUtil.parseInt(goodsVO.getPackageNum()));//更新包装规格
        spec.setUnit(unit);//更新销售单位
        spec.setPackageUnit(packageUnit);//更新包装单位
        spec.setLength(Utils.reqDoubleFromString(goodsVO.getLength()));//更新长
        spec.setWidth(Utils.reqDoubleFromString(goodsVO.getWidth()));//更新宽
        spec.setHeight(Utils.reqDoubleFromString(goodsVO.getHeight()));//更新高
        spec.setWeight(Utils.reqDoubleFromString(goodsVO.getWeight()));//更新重
        spec.setWarranty(goodsVO.getWarranty());//更新质保期
        spec.setMinOrder(NumericUtil.parseInt(goodsVO.getMinOrder()));//更新最小起订量
        spec.setPrice(map.get("price"));//更新价格
        spec.setMarketPrice(map.get("marketPrice"));//更新市场价
        spec.setDiscount(map.get("disCount"));//更新折扣
        spec.setDelivery(deliveryDate);//更新货期
        inventory.setNum(NumericUtil.parseInt(goodsVO.getInventoryNum()));//更新库存
        inventory.setWarehouse(warehouse);//更新仓库
        spec.setSku(goodsVO.getSku());
        spec.setLast(new Date());//商品更新日期
        specService.editItem(spec);//更新商品信息

        goods.setCategory(category);//更新分类
        goods.setBrand(brand);//更新品牌
        goods.setMemo(goodsVO.getMemo());// 备注
        goods.setDescription(goodsVO.getGoodsDescript());// 商品描述

        /*更新最大和最小价格*/
        Map<String, Double> data = specService.queryGoodsMaxAndMinPrice(model.getGoods()).getData();
        if (data != null) {
            goods.setPriceMin(data.get("priceMin"));
            goods.setPriceMax(data.get("priceMax"));
            goodsService.editItem(goods);//更新商品信息
        }

        inventory.setTime(new Date());//库存更新时间
        inventory.setSpec(spec.getId());//设置商品实体

        memberInventoryService.editItem(inventory);//操作更新
        goodsVO.setFlag(3);//重复已修改标识

        return ReturnMessage.success(goodsVO);
    }


    /**
     * 根据GoodsVO 格式化处理折扣及市场价格
     */
    private Map<java.lang.String, Double> formatPrice(GoodsVO goodsVO) {
        Double marketPrice = null;
        Double disCount = null;
        Double price = null;
        if (NumericUtil.isDouble(goodsVO.getPrice())) {
            price = Double.parseDouble(goodsVO.getPrice());
        }

        if (NumericUtil.isDouble(goodsVO.getMarketPrice())) {//如果填写市场价则计算折扣
            marketPrice = Double.parseDouble(goodsVO.getMarketPrice());
            disCount = price / marketPrice * 100;
        } else if (NumericUtil.isDouble(goodsVO.getDiscount())) {//如果仅填写折扣则计算市场价
            disCount = Double.parseDouble(goodsVO.getDiscount());
            marketPrice = price / disCount * 100;
        }

        Map<java.lang.String, Double> map = new HashMap<>();
        map.put("price", price);
        map.put("marketPrice", marketPrice);
        map.put("disCount", disCount);
        return map;
    }

    /**
     * 检查数据完整
     *
     * @param goodsVO goodsVO
     * @return GoodsVO goodsVO
     */
    private boolean checkGoodsVO(Integer memberId, GoodsVO goodsVO) {
        /*Boolean havefreightTemplate = true;
        //检查模板是否存在
        FreightTemplateEntity freightTemplateEntity = freightTemplateService.getFreightTemplateByMemberIdAndName(memberId, goodsVO.getFreightTemplate()).getData();
        if(freightTemplateEntity==null){
            havefreightTemplate = false;
        }*/
        return (goodsVO != null &&

                //数据必填项
                StringUtils.isNotBlank(goodsVO.getBrand()) &&
                StringUtils.isNotBlank(goodsVO.getName()) &&
                StringUtils.isNotBlank(goodsVO.getModel()) &&
                NumericUtil.isInteger(goodsVO.getPackageNum()) &&
                StringUtils.isNotBlank(goodsVO.getUnit()) &&
                StringUtils.isNotBlank(goodsVO.getPackageUnit()) &&
                NumericUtil.isInteger(goodsVO.getMinOrder()) &&
                NumericUtil.isDouble(goodsVO.getPrice()) &&
                Double.valueOf(goodsVO.getPrice()) > 0 &&
                NumericUtil.isInteger(goodsVO.getDelivery()) &&

                //数据格式必须正确项
                Utils.isBlankDouble(goodsVO.getMarketPrice()) &&
                Utils.isBlankDouble(goodsVO.getDiscount()) &&
                Utils.isBlankDouble(goodsVO.getHeight()) &&
                Utils.isBlankDouble(goodsVO.getWeight()) &&
                Utils.isBlankDouble(goodsVO.getLength()) &&
                Utils.isBlankDouble(goodsVO.getWidth()) &&
                Utils.isBlankInteger(goodsVO.getInventoryNum()));
    }

    /**
     * 检查是否有重复商品
     */
    private boolean existGoodsVO(GoodsType type, GoodsVO goodsVO) {
        ReturnMessage<GoodsSpecEntityManyToOne> existsGoodsSpec = specService.getExistsGoodsSpec(type, goodsVO.getMemberId(), goodsVO.getBrand(), goodsVO.getName(),
                goodsVO.getModel(), goodsVO.getSpec(), Utils.reqIntegerFromString(goodsVO.getPackageNum(), 1),
                goodsVO.getUnit(), goodsVO.getPackageUnit());
        return existsGoodsSpec.getCode() >= 0 && existsGoodsSpec.getData() != null;
    }

    @Override
    public ReturnMessage goodsViewsAdd(Integer id) {
        try {
            GoodsEntity goodsEntity = goodsService.getItem(id).getData();
            goodsEntity.setViews(goodsEntity.getViews() + 1);
            goodsService.editItem(goodsEntity);
            return ReturnMessage.message(goodsEntity.getViews(), String.valueOf(goodsEntity.getViews()), id);
        } catch (Exception e) {
            return ReturnMessage.failed();
        }
    }

    @Override
    public ReturnMessage<GoodsSpecEntity> editGoods(Integer id, String images, Integer brand, Integer category, String unspsc, String hscode, String goodsName, String tags,
                                                    Double priceMin, Double priceMax, Integer sales, Integer views, Boolean goodsSaled, String packages, String technology,
                                                    String goodsDescription, String memo,
                                                    List<Integer> modelId, List<String> modelName, List<Boolean> modelSaled,
                                                    List<Integer> specId, List<String> specName, List<Double> price, List<Double> marketPrice, List<Double> discount, List<Integer> minOrder,
                                                    List<Integer> unit, List<Integer> packageNum, List<Integer> packageUnit, List<Integer> delivery, List<String> sku,
                                                    List<Double> weight, List<Double> length, List<Double> width, List<Double> height, List<String> warranty, List<Integer> member,
                                                    List<Integer> freightTemplate, List<String> description, List<Boolean> specSaled) {
        GoodsEntity goodsEntity = goodsService.getItem(id).getData();
        goodsEntity.setImages(images);
        goodsEntity.setBrand(brandService.getItem(brand).getData());
        goodsEntity.setCategory(categoryService.getCategoryWithOutTreeById(category).getData());
        goodsEntity.setUnspsc(unspsc);
        goodsEntity.setHscode(hscode);
        goodsEntity.setName(goodsName);
        goodsEntity.setTags(tags);
        goodsEntity.setPriceMin(Collections.min(price));
        goodsEntity.setPriceMax(Collections.max(price));
        if (sales != null && sales > 0) goodsEntity.setSales(sales);
        if (views != null && views > 0) goodsEntity.setViews(views);
        goodsEntity.setSaled(goodsSaled);
        goodsEntity.setPackages(packages);
        goodsEntity.setTechnology(technology);
        goodsEntity.setDescription(goodsDescription);
        goodsEntity.setMemo(memo);
        List<GoodsModelEntity> modelList = goodsEntity.getGoodsModelList();
        //遍历型号参数数据
        for (int i = 0; i < modelId.size(); i++) {
            boolean isNew = true;
            //遍历商品对象型号列表数据
            for (GoodsModelEntity model : modelList) {
                if (model.getId() == modelId.get(i)) {
                    GoodsModelEntity modelEntity = model;
                    modelEntity.setName(modelName.get(i));
                    modelEntity.setSaled(modelSaled.get(i));
                    List<GoodsSpecEntity> goodsSpecList = modelEntity.getGoodsSpecList();
                    //遍历商品规格参数数据
                    for (GoodsSpecEntity spec : goodsSpecList) {
                        GoodsSpecEntity specEntity = spec;
                        //遍历商品型号对象 商品规格列表数据
                        for (int m = 0; m < specId.size(); m++) {
                            if (specId.get(m) == spec.getId()) {
                                specEntity.setName(specName.get(m));
                                specEntity.setPrice(price.get(m));
                                specEntity.setMarketPrice(marketPrice.get(m));
                                specEntity.setDiscount(discount.get(m));
                                specEntity.setMinOrder(minOrder.get(m));
                                specEntity.setUnit(goodsUnitService.getItem(unit.get(m)).getData());
                                specEntity.setPackageNum(packageNum.get(m));
                                specEntity.setPackageUnit(goodsUnitService.getItem(packageUnit.get(m)).getData());
                                specEntity.setDelivery(delivery.get(m));
                                specEntity.setSku(sku.get(m));
                                specEntity.setWeight(weight.get(m));
                                specEntity.setLength(length.get(m));
                                specEntity.setWidth(width.get(m));
                                specEntity.setHeight(height.get(m));
                                specEntity.setWarranty(warranty.get(m));
                                specEntity.setCorporation(corporationService.getItem(member.get(m)).getData());
                                specEntity.setFreightTemplate(freightTemplate.get(m));
                                specEntity.setDescription(description.get(m));
                                specEntity.setSaled(specSaled.get(m));
                            }
                        }
                    }
                }
            }
        }
        return goodsService.editItem(goodsEntity);
    }

    @Override
    public ReturnMessage<List<GoodsEntity>> getShopGoodsRandom(Integer size, Integer categoryId, boolean eachCategory, Integer brandId) {
        try {
            GoodsType type = GoodsType.SHOP;
            Random random = new Random();
            size = Utils.reqInt(size, 1);
            List<GoodsEntity> goodsRandomList = new ArrayList<>();
            if (categoryId == null) {
                List<GoodsCategoryEntity> categoryList = categoryService.queryAllCategoryList(type, null, true).getData();
                for (int j = 0; j < categoryList.size(); j++) {
                    for (int i = 0; i < size; i++) {
                        ReturnMessage<List<GoodsEntity>> listReturnMessage = this.queryGoodsList(type, brandId, categoryList.get(j).getId(), false, null, null, true, null, null, null, null,
                                null, null, null, null, null, null, null, 1, 1);
                        int code = listReturnMessage.getCode();
                        if (code <= 0) continue;
                        List<GoodsEntity> data = this.queryGoodsList(type, brandId, categoryList.get(j).getId(), false, null, null, true, null, null, null, null,
                                null, null, null, null, null, null, null, random.nextInt(code) + 1, 1).getData();
                        goodsRandomList.add(data.get(0));
                    }
                }
            } else {
                for (int i = 0; i < size; i++) {
                    ReturnMessage<List<GoodsEntity>> listReturnMessage = this.queryGoodsList(type, brandId, categoryId, false, null, null, true, null, null, null, null,
                            null, null, null, null, null, null, null, 1, 1);
                    int code = listReturnMessage.getCode();
                    if (code <= 0) continue;
                    goodsRandomList.add(this.queryGoodsList(type, brandId, categoryId, false, null, null, true, null, null, null, null,
                            null, null, null, null, null, null, null, code + 1, 1).getData().get(0));
                }
            }
            return ReturnMessage.message(goodsRandomList.size(), goodsRandomList.size() + "", goodsRandomList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ReturnMessage.failed();
    }

    @Override
    public ReturnMessage<List<GoodsEntity>> randomQueryGoodsByCategoryOrBrand(GoodsType type, Integer brandId, Integer categoryId,
                                                                              boolean hasImages, Integer size) {
        return goodsService.randomQueryGoodsByCategoryOrBrand(type, brandId, categoryId, hasImages, size);
    }

    @Override
    public ReturnMessage setForSale(Integer[] id) {
        return goodsService.setForSale(id);
    }

    @Override
    public ReturnMessage adjustPrice(AccessTokenEntity access, GoodsType type, Integer brandId, Integer percent) {
        return specService.adjustPrice(access, type, brandId, percent);
    }

}
