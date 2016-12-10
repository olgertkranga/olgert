package com.vvv.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "product")
    private String product;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "price")
    private Double price;

    @Column(name = "sqty")
    private Integer sqty;

    @Column(name = "mqty")
    private Integer mqty;

    @Column(name = "lqty")
    private Integer lqty;

    @Column(name = "xlqty")
    private Integer xlqty;

    @Column(name = "xxlqty")
    private Integer xxlqty;

    @Column(name = "cur")
    private String cur;

    @Column(name = "img_path")
    private String img_path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public Product product(String product) {
        this.product = product;
        return this;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getCost() {
        return cost;
    }

    public Product cost(Double cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPrice() {
        return price;
    }

    public Product price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getSqty() {
        return sqty;
    }

    public Product sqty(Integer sqty) {
        this.sqty = sqty;
        return this;
    }

    public void setSqty(Integer sqty) {
        this.sqty = sqty;
    }

    public Integer getMqty() {
        return mqty;
    }

    public Product mqty(Integer mqty) {
        this.mqty = mqty;
        return this;
    }

    public void setMqty(Integer mqty) {
        this.mqty = mqty;
    }

    public Integer getLqty() {
        return lqty;
    }

    public Product lqty(Integer lqty) {
        this.lqty = lqty;
        return this;
    }

    public void setLqty(Integer lqty) {
        this.lqty = lqty;
    }

    public Integer getXlqty() {
        return xlqty;
    }

    public Product xlqty(Integer xlqty) {
        this.xlqty = xlqty;
        return this;
    }

    public void setXlqty(Integer xlqty) {
        this.xlqty = xlqty;
    }

    public Integer getXxlqty() {
        return xxlqty;
    }

    public Product xxlqty(Integer xxlqty) {
        this.xxlqty = xxlqty;
        return this;
    }

    public void setXxlqty(Integer xxlqty) {
        this.xxlqty = xxlqty;
    }

    public String getCur() {
        return cur;
    }

    public Product cur(String cur) {
        this.cur = cur;
        return this;
    }

    public void setCur(String cur) {
        this.cur = cur;
    }

    public String getImg_path() {
        return img_path;
    }

    public Product img_path(String img_path) {
        this.img_path = img_path;
        return this;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if(product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", product='" + product + "'" +
            ", cost='" + cost + "'" +
            ", price='" + price + "'" +
            ", sqty='" + sqty + "'" +
            ", mqty='" + mqty + "'" +
            ", lqty='" + lqty + "'" +
            ", xlqty='" + xlqty + "'" +
            ", xxlqty='" + xxlqty + "'" +
            ", cur='" + cur + "'" +
            ", img_path='" + img_path + "'" +
            '}';
    }
}
