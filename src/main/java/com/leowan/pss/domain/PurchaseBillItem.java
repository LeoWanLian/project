package com.leowan.pss.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 采购订单明细:组合关系的多方
 * 
 */
@Entity
@Table(name = "purchasebillitem")
public class PurchaseBillItem extends BaseDomain {
  private BigDecimal price;
  private BigDecimal num;
  private BigDecimal amount;
  private String descs;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id")
  private Product product;// 多对一,非空
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "bill_id")
  private PurchaseBill bill;// 组合关系,非空

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getNum() {
    return num;
  }

  public void setNum(BigDecimal num) {
    this.num = num;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getDescs() {
    return descs;
  }

  public void setDescs(String descs) {
    this.descs = descs;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public PurchaseBill getBill() {
    return bill;
  }

  public void setBill(PurchaseBill bill) {
    this.bill = bill;
  }

  @Override
  public String toString() {
    return "PurchaseBillItem [id=" + id + ", price=" + price + ", num=" + num + ", amount=" + amount + ", descs="
	+ descs + "]";
  }

}
