package com.leowan.pss.web.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.leowan.pss.domain.Product;
import com.leowan.pss.domain.ProductType;
import com.leowan.pss.query.ProductQuery;
import com.leowan.pss.service.IProductService;
import com.leowan.pss.service.IProductTypeService;
import com.leowan.pss.service.ISystemDictionaryDetailService;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import net.coobird.thumbnailator.Thumbnails;

/**
 * 员工列表的aciton
 * 
 * @author LeoWan
 *
 */
@Controller
@Scope("prototype")
public class ProductAction extends CRUDAction<Product> {

	@Autowired
	private IProductService productService;
	@Autowired
	private IProductTypeService productTypeService;

	@Autowired
	private ISystemDictionaryDetailService systemDictionaryDetailService;

	// 处理上传
	private File upload;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmssS");
	// 由struts2管理baseQuery需要提供getter/setter方法
	private ProductQuery baseQuery = new ProductQuery();

	private Product product;

	/*
	 * @Override public String execute() throws Exception {
	 * System.out.println("execute"); pageList =
	 * productService.findByQuery(baseQuery); //
	 * System.out.println("------------" + pageList.getCurrentPage()); return
	 * SUCCESS; }
	 */

	// 代替了execute方法. 因为父类里面有execute方法.里面会调用list方法.
	@Override
	protected void list() throws Exception {
		pageList = productService.findByQuery(baseQuery);
		// putContext("allDeps", departmentService.getAll());
		// System.out.println(pageList.getData());
	}

	// 跳转到input页面
	@Override
	public String input() throws Exception {
		// System.out.println("input");
		// putContext("allDeps", departmentService.getAll());
		return INPUT;
	}

	// 修改保存的方法,由spring data jpa来判断是update还是save
	// 出现转换异常或者验证异常，跳转到input方法
	@InputConfig(methodName = INPUT)
	public String save() throws Exception {
		if (upload != null) {
			System.out.println("---------------------");
			// 获取webapp在服务器上面的物理路径
			// D:\EclipseAndTomcat\eclipse_new\workspace\pss\src\main\webapp
			String webapp = ServletActionContext.getServletContext().getRealPath("/");

			// 修改怎样处理原图:直接删除,月底在一次性删除
			if (id != null && StringUtils.isNotBlank(product.getPic())) {
				File deleteFile = new File(webapp, product.getPic());
				File deleteFile2 = new File(webapp, product.getSmallPic());
				if (deleteFile.exists()) {
					deleteFile.delete();
				}
				if (deleteFile2.exists()) {
					deleteFile2.delete();
				}
			}
			// 上传文件命名,拷贝到webapp的位置,设置pic到product
			Date date = new Date();
			// 大小图的路径+文件名称
			String fileName = "upload/" + sdf.format(date) + ".png";
			String smallFileName = "upload/" + sdf.format(date) + "_small.png";
			// 大小图的在服务器上面的物理路径
			File destFile = new File(webapp, fileName);
			File smallDestFile = new File(webapp, smallFileName);

			// 生成upload目录
			File parentFile = destFile.getParentFile();
			if (!parentFile.exists()) {
				parentFile.mkdirs();// 自动生成upload目录
			}

			// 把上传的临时图片，复制到当前项目的webapp路径
			FileUtils.copyFile(upload, destFile);
			// 处理缩略图
			Thumbnails.of(upload).scale(0.1F).toFile(smallDestFile);
			// 把大小图的相对webapp的路径设置到数据库产品表里面
			product.setPic(fileName);
			product.setSmallPic(smallFileName);
		}

		if (id == null) {
			// 新增后的保存自动跳转到最后一页
			baseQuery.setCurrentPage(Integer.MAX_VALUE);
		}
		productService.save(product);
		return RELOAD;// 在BaseAction中定义一个常量字段的结果视图,配置重定向提交到展示列表. 防止重复提交
	}

	// ajax删除的方法
	// {"success":true,"msg":"删除成功"}
	// {"success":false,"msg":"删除失败"}
	public String delete() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", false);

		// 获取页面输出流,输出页面需要的json格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			// 如果删除成功,返回json 为true
			if (id != null) {
				product = productService.get(id);
				productService.delete(id);
				// 删除图片的代码，写在delete方法之后
				String webapp = ServletActionContext.getServletContext().getRealPath("/");
				if (id != null && StringUtils.isNotBlank(product.getPic())) {
					File deleteFile = new File(webapp, product.getPic());
					if (deleteFile.exists()) {
						deleteFile.delete();
					}
					File deleteSmallFile = new File(webapp, product.getSmallPic());
					if (deleteSmallFile.exists()) {
						deleteSmallFile.delete();
					}
				}
				map.put("success", true);
				map.put("msg", "删除成功");
				// 拼接完整的Json格式
				// out.print("{\"success\":true,\"msg\":\"删除成功\"}");
			} else {
				map.put("msg", "没有对应的id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 拼接完整的Json格式 - 并把错误信息打印到前台
			map.put("msg", "异常:" + e.getMessage());
			// out.print("{\"success\":false,\"msg\":\"" + e.getMessage() +
			// "\"}");
		}
		// return null;// 返回给json的,不需要视图
		putContext("map", map);
		return "json";// 返回json视图
	}

	// 会自动在struts访问方法之前执行
	@Override
	public void prepare() throws Exception {
		// System.out.println("prepare");
	}

	public void prepareSave() throws Exception {

		// 如果id为空,那么就实例化
		if (id == null) {
			this.product = new Product();// 压栈
		} else {
			// 如果id不为空,就从数据库里面查询product,那么数据就不会丢失
			this.product = productService.get(id);
			// 处理修改部门名称的问题
			// jpa中不能修改持久状态对象的主键. 需要将其设为null再由spring new出来
			// 把持久状态的xxx变成临时状态
			product.setTypes(null);
			product.setUnit(null);
			product.setBrand(null);
		}
	}

	// 回显
	public void prepareInput() throws Exception {
		if (id != null) {
			this.product = productService.get(id); // 通过获取到的id查询回显.product就到栈顶了
			// System.out.println(product);
		} else {
			product = new Product();
		}
		// System.out.println("prepareInput");
		// 需要选择品牌列表
		putContext("allTypes", productTypeService.getAll());

		putContext("allParents", productTypeService.getParents());
		// 二级类型列表
		List<ProductType> allChildrens = new ArrayList<ProductType>();
		// 如果能够获取到产品的一级类型的id,说明需要自动回显二级类型
		// <s:select id="parent" list="#allParents" name="types.parent.id"
		// 提交的二级类型
		ProductType types = product.getTypes();// types
		if (types != null) {
			ProductType parentTypes = types.getParent();
			if (parentTypes != null && parentTypes.getId() != -1L) {
				List<ProductType> childrenList = productTypeService.findChildren(parentTypes.getId());
				allChildrens.addAll(childrenList);
			}
		} else {
			ProductType productType = new ProductType();
			productType.setId(-1L);
			productType.setName("--请选择--");
			allChildrens.add(productType);
		}
		putContext("allChildrens", allChildrens);
		for (ProductType productType : allChildrens) {
			System.out.println(productType);
		}
		// 需要选择品牌列表
		putContext("allBrands", systemDictionaryDetailService.getBrands());
		// 需要选择单位列表
		putContext("allUnits", systemDictionaryDetailService.getUnits());

	}

	// jsp发出ajax:返回二级类型的json数据
	public String findChildren() {
		List<ProductType> childrenList = productTypeService.findChildren(id);
		putContext("map", childrenList);
		return "json";
	}

	// 采购定义点击选择产品的访问此方法
	public String bill() throws Exception {
		this.pageList = productService.findByQuery(baseQuery);
		return "bill";
	}

	public ProductQuery getBaseQuery() {
		return baseQuery;
	}

	public void setBaseQuery(ProductQuery baseQuery) {
		this.baseQuery = baseQuery;
	}

	@Override
	public Product getModel() {
		return product;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

}
