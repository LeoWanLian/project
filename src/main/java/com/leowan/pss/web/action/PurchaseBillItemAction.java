package com.leowan.pss.web.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Department;
import com.leowan.pss.domain.Employee;
import com.leowan.pss.domain.PurchaseBillItem;
import com.leowan.pss.query.PageList;
import com.leowan.pss.query.PurchaseBillItemQuery;
import com.leowan.pss.service.IPurchaseBillItemService;

/**
 * 员工列表的aciton
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class PurchaseBillItemAction extends BaseAction {

	@Autowired
	private IPurchaseBillItemService purchaseBillItemService;

	// 由struts2管理baseQuery需要提供getter/setter方法
	private PurchaseBillItemQuery baseQuery = new PurchaseBillItemQuery();

	private Object groupValue;

	public Object getGroupValue() {
		return groupValue;
	}

	public void setGroupValue(Object groupValue) {
		this.groupValue = groupValue;
	}

	@Override
	// 第一条分组查询
	public String execute() throws Exception {
		putContext("list", purchaseBillItemService.findByGroupBy(baseQuery));
		return SUCCESS;
	}

	// 从jsp页面访问此方法
	/*
	 * 在jsp页面调用这个方法 <s:iterator value="findItems(#objects[0])">
	 * 如果此action在栈顶,iterator迭代器可以通过此方式来调用action里面的方法,来遍历返回值的集合
	 */
	public List<PurchaseBillItem> findItems(Object groupByValue) throws Exception {
		return purchaseBillItemService.findItems(baseQuery, groupByValue);
	}

	public String chart1() throws Exception {
		return "chart1";
	}

	public String chart2() throws Exception {
		return "chart2";
	}

	// 查询饼图所需要的数据,需要的是供应商和小计金额 supplier.name 和sum(amount)
	// {
	// name: 'Chrome',
	// y: 12.8,
	// sliced: true,
	// selected: true
	// }
	public String data() throws Exception {
		// 返回值是Object[] 而json需要的是键值对数组需要转换
		List<Object[]> list = purchaseBillItemService.findByGroupBy2(baseQuery);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for (Object[] objects : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", objects[0]);
			map.put("y", objects[1]);
			// map.put("sliced", true);
			// map.put("selected", true);
			// 将数据添加到data里面
			data.add(map);
		}
		putContext("map", data);
		return "json";
	}

	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	String[] heads = { "明细编号", "供应商名称", "采购员", "产品名称", "交易时间", "采购数量", "采购价格", "小计", "产品类别", "状态" };

	public String download() throws Exception {
		List<Object[]> groupBy = purchaseBillItemService.findByGroupBy(baseQuery);// [东莞供应商,1][成都供应商,3]
		// 创建一个List 用来装数据
		List<String[]> rows = new ArrayList<>();

		for (Object[] objects : groupBy) {
			// 遍历items 获取到分组的数据
			List<PurchaseBillItem> items = purchaseBillItemService.findItems(baseQuery, objects[0]);// [东莞供应商,2][成都供应商,8][武汉供应商,1]
			String[] supplier = { objects[0] + "-记录数" + objects[1] };
			// 将中间的供应商条数添加进去
			rows.add(supplier);
			// 定义三个变量用来存总计,每次内循环之前,清空
			//总数量
			BigDecimal totalNum = new BigDecimal(0);
			//平均价格
			BigDecimal avgPrice = new BigDecimal(0);
			//总金额
			BigDecimal totalAmount = new BigDecimal(0);
			// 内循环遍历明细,将每一行的数据搞定.
			for (PurchaseBillItem purchaseBillItem : items) {
				String[] strings1 = new String[heads.length];
				Object[] objects2 = groupBy.get(0);
				strings1[0] = purchaseBillItem.getId().toString();
				strings1[1] = purchaseBillItem.getBill().getSupplier().getName();
				strings1[2] = purchaseBillItem.getBill().getBuyer().getUsername();
				strings1[3] = purchaseBillItem.getProduct().getName();
				strings1[4] = purchaseBillItem.getBill().getVdate().toString();
				strings1[5] = purchaseBillItem.getNum().toString();
				strings1[6] = purchaseBillItem.getPrice().toString();
				strings1[7] = purchaseBillItem.getAmount().toString();
				strings1[8] = purchaseBillItem.getProduct().getTypes().getName();
				Integer status = purchaseBillItem.getBill().getStatus();
				// 判断状态,换成中文
				if (status == 1) {
					strings1[9] = "已审";
				} else if (status == 0) {
					strings1[9] = "待审";
				} else {
					strings1[9] = "作废";
				}
				// 将每一行的数据添加进去
				rows.add(strings1);
				// 每次内循环结束之后,计算总计
				totalNum = totalNum.add(purchaseBillItem.getNum());
				totalAmount = totalAmount.add(purchaseBillItem.getAmount());
				avgPrice = totalAmount.divide(totalNum, 2, BigDecimal.ROUND_HALF_DOWN);
			}
			//搞定总计的行
			String[] row = new String[heads.length];
			row[0] = "总计";
			row[5] = totalNum.toString();
			row[6] = avgPrice.toString();
			row[7] = totalAmount.toString();
			// 将总计添加进List
			rows.add(row);

		}
		this.inputStream = purchaseBillItemService.download(rows, heads);
		return "download";
	}

	public PurchaseBillItemQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(PurchaseBillItemQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

}
