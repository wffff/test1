package cn.gomro.mid.core.biz.order.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;

/**
 * Created by adam on 2017/4/26.
 */
@Entity
@Table(name = "t_credit_expense")
public class CreditExpenseEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer order_id;//订单ID(t_member_order的ID)
    private Double money;//借款金额
    private String memo;
    //time 消费日期 见AbstractEntity


    public CreditExpenseEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
