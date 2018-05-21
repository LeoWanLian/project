package com.leowan.pss.web.action;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Employee;
import com.leowan.pss.domain.PurchaseBill;
import com.leowan.pss.domain.PurchaseBillItem;
import com.leowan.pss.query.PurchaseBillQuery;
import com.leowan.pss.service.IEmployeeService;
import com.leowan.pss.service.IPurchaseBillService;
import com.leowan.pss.service.ISupplierService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 员工列表的aciton
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class PurchaseBillAction extends CRUDAction<PurchaseBill> {

	@Autowired
	private IPurchaseBillService purchaseBillService;

	// 由struts2管理baseQuery需要提供getter/setter方法
	private PurchaseBillQuery baseQuery = new PurchaseBillQuery();

	private PurchaseBill purchaseBill;

	@Autowired
	private IEmployeeService employeeService;
	@Autowired
	private ISupplierService supplierService;

	/*
	 * @Override public String execute() throws Exception {
	 * System.out.println("execute"); pageList =
	 * purchaseBillService.findByQuery(baseQuery); //
	 * System.out.println("------------" + pageList.getCurrentPage()); return
	 * SUCCESS; }
	 */

	// 代替了execute方法. 因为父类里面有execute方法.里面会调用list方法.
	@Override
	protected void list() throws Exception {
		pageList = purchaseBillService.findByQuery(baseQuery);
		putContext("allStatus", purchaseBillService.getAll());
		// System.out.println(pageList.getData());
	}

	// 跳转到input页面
	@Override
	public String input() throws Exception {
		// System.out.println("input");
		putContext("allBuyers", employeeService.getBuyers());
		putContext("allSuppliers", supplierService.getAll());
		return INPUT;
	}

	// 从session中获取到当前的inputUser
	Employee inputer = (Employee) ServletActionContext.getRequest().getSession().getAttribute(USER_IN_SESSION);

	// 修改保存的方法,由spring data jpa来判断是update还是save
	// 出现转换异常或者验证异常，跳转到input方法
	@InputConfig(methodName = INPUT)
	public String save() throws Exception {
		BigDecimal totalAmount = new BigDecimal(0);// 总金额
		BigDecimal totalNum = new BigDecimal(0);// 总数量
		// 获取当前采购明细集合(从一方获取多方,已经建立关系)
		List<PurchaseBillItem> items = purchaseBill.getItems();
		for (PurchaseBillItem purchaseBillItem : items) {
			// 设置多方到一方的关系
			// not-null property references a null or transient value:
			// cn.itsource.pss.domain.PurchaseBillItem.bill
			purchaseBillItem.setBill(purchaseBill);
			// 计算小计
			purchaseBillItem.setAmount(purchaseBillItem.getPrice().multiply(purchaseBillItem.getNum()));
			// 累加
			totalAmount = totalAmount.add(purchaseBillItem.getAmount());
			totalNum = totalNum.add(purchaseBillItem.getNum());
		}
		// 设置总金额,总数量
		purchaseBill.setTotalAmount(totalAmount);
		purchaseBill.setTotalNum(totalNum);

		if (id == null) {
			// 设置inputUser
			// 新增后的保存自动跳转到最后一页
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
			purchaseBill.setInputUser(inputer);
			// System.out.println("inputer:" + inputer);
		}
		// System.out.println(purchaseBill + "------------");
		purchaseBillService.save(purchaseBill);
		return RELOAD;// 在BaseAction中定义一个常量字段的结果视图,配置重定向提交到展示列表. 防止重复提交
	}

	// ajax删除的方法
	// {"success":true,"msg":"删除成功"}
	// {"success":false,"msg":"删除失败"}
	public String delete() throws Exception {
		// 获取页面输出流,输出页面需要的json格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			// 如果删除成功,返回json 为true
			if (id != null) {
				purchaseBillService.delete(id);
				// 拼接完整的Json格式
				out.print("{\"success\":true,\"msg\":\"删除成功\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 拼接完整的Json格式 - 并把错误信息打印到前台
			out.print("{\"success\":false,\"msg\":\"" + e.getMessage() + "\"}");
		}
		return null;// 返回给json的,不需要视图
	}

	// 会自动在struts访问方法之前执行
	@Override
	public void prepare() throws Exception {
		// System.out.println("prepare");
	}

	public void prepareSave() throws Exception {

		// 如果id为空,那么就实例化
		if (id == null) {
			this.purchaseBill = new PurchaseBill();// 压栈
		} else {
			// 如果id不为空,就从数据库里面查询purchaseBill,那么数据就不会丢失
			this.purchaseBill = purchaseBillService.get(id);
			// 处理修改部门名称的问题
			// jpa中不能修改持久状态对象的主键. 需要将其设为null再由spring new出来
			purchaseBill.setBuyer(null);
			purchaseBill.setSupplier(null);
			// 必须在bill上面添加orphanRemoval = true，下面的clear才有效果
			purchaseBill.getItems().clear();
		}
	}

	// 回显
	public void prepareInput() throws Exception {
		// 如果是修改,设置回显的值
		if (id != null) {
			this.purchaseBill = purchaseBillService.get(id); // 通过获取到的id查询回显.purchaseBill就到栈顶了
		} else {
			// 设置新增回显的时间
			purchaseBill = new PurchaseBill();
			purchaseBill.setVdate(new Date());
		}
	}

	// 修改审核状态
	public String audit() throws Exception {
		// 获取页面输出流,输出页面需要的json格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			System.out.println("---------------------");
			purchaseBill = purchaseBillService.get(id);
			if (purchaseBill.getStatus() == 0) {
				purchaseBill.setStatus(1);
				purchaseBillService.save(purchaseBill);
				out.print("{\"success\":true,\"msg\":\"审核成功\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 拼接完整的Json格式 - 并把错误信息打印到前台
			out.print("{\"success\":false,\"msg\":\"" + e.getMessage() + "\"}");
		}
		return null;// 返回给json的,不需要视图
	}

	public PurchaseBillQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(PurchaseBillQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	@Override
	public PurchaseBill getModel() {
		return purchaseBill;
	}
}
