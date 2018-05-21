package com.leowan.pss.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 产品
 *
 */
@Entity
@Table(name = "product")
public class Product extends BaseDomain {
	private String name;// 产品名称
	private String color;// 产品颜色
	private String pic;// 产品图片的相对路径
	private String smallPic;
	private BigDecimal costPrice;// 成本价格
	private BigDecimal salePrice;// 销售价格
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "types_id")
	private ProductType types;// 对应的二级产品类型
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "unit_id")
	private SystemDictionaryDetail unit;// 数据字典明细：单位
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brand_id")
	private SystemDictionaryDetail brand;// 数据字典明细：品牌

	public Product() {

	}

	public Product(Long id) {
		super();
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSmallPic() {
		return smallPic;
	}

	public void setSmallPic(String smallPic) {
		this.smallPic = smallPic;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public ProductType getTypes() {
		return types;
	}

	public void setTypes(ProductType types) {
		this.types = types;
	}

	public SystemDictionaryDetail getUnit() {
		return unit;
	}

	public void setUnit(SystemDictionaryDetail unit) {
		this.unit = unit;
	}

	public SystemDictionaryDetail getBrand() {
		return brand;
	}

	public void setBrand(SystemDictionaryDetail brand) {
		this.brand = brand;
	}
}
