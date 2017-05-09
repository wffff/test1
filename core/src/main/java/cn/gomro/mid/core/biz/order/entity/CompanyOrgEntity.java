package cn.gomro.mid.core.biz.order.entity;

import cn.gomro.mid.core.biz.base.AbstractEntity;

import javax.persistence.*;

/**
 * Created by adam on 2017/4/26.
 */
@Entity
@Table(name = "t_company_org")
public class CompanyOrgEntity extends AbstractEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
        private Double credit; //信用账户金额

        public CompanyOrgEntity() {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Double getCredit() {
            return credit;
        }

        public void setCredit(Double credit) {
            this.credit = credit;
        }
    }
