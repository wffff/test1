package cn.gomro.mid.core.biz.goods.service;

/**
 * Created by momo on 2016/8/1.
 */
public enum GoodsType {

    ALL("全部"), SHOP("商城"), MARKET("市场");

    private final String value;

    GoodsType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}