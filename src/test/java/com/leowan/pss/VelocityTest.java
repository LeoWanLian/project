package com.leowan.pss;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.junit.Test;

public class VelocityTest {
	// 定义常量来放路径
	private static final String SRC = "src/main/java/";
	// 定义常量来放包名
	private static final String PACKAGE = "com/leowan/pss/";
	// 定义webapp的路径
	private static final String WEBAPP = "src/main/webapp/";
	// 定义测试类的路径
	private static final String TEST = "src/test/java/";
	// 定义一个flag ,控制是否覆盖文件
	private static final boolean FLAG = false;

	// 准备需要生成的所有domain对象

	private String[] domains = { "StockIncomeBill", "StockIncomeBillItem","ProductStock","Depot"};
	// private String[] domains = { "Role", "Permission" };
	// 准备9个接收的路径
	private String[] files = { SRC + PACKAGE + "web/action/", WEBAPP + "js/model/", WEBAPP + "WEB-INF/views/",
			WEBAPP + "WEB-INF/views/", SRC + PACKAGE + "query/", SRC + PACKAGE + "repository/",
			SRC + PACKAGE + "service/", SRC + PACKAGE + "service/impl/", TEST + PACKAGE + "service/" };
	// 准备9个模板
	private String[] templates = { "Action.java", "domain.js", "list.jsp", "input.jsp", "Query.java", "Repository.java",
			"Service.java", "ServiceImpl.java", "ServiceTest.java" };

	@Test
	public void creatCode() throws Exception {
		// 如果模板数量和文件数量不一致,就抛异常
		if (templates.length != files.length) {
			throw new RuntimeException("templates.length==files.length必须一致");
		}
		// 创建模板应用上下文
		VelocityContext context = new VelocityContext();
		// 外循环,控制domain个数
		for (int i = 0; i < domains.length; i++) {
			// 准备类名的首字母小写的string
			String lowerEntityDomain = domains[i].substring(0, 1).toLowerCase() + domains[i].substring(1);
			context.put("entityDomain", domains[i]);
			context.put("lowerEntityDomain", lowerEntityDomain);
			// 内循环,控制生成文件的个数
			for (int j = 0; j < templates.length; j++) {
				File file = new File(files[j] + domains[i] + templates[j]);
				// 对特殊的文件进行判断处理
				if ("domain.js".equals(templates[j])) {
					file = new File(files[j] + lowerEntityDomain + ".js");
				} else if ("list.jsp".equals(templates[j])) {
					file = new File(files[j] + lowerEntityDomain + "/" + lowerEntityDomain + ".jsp");
				} else if ("input.jsp".equals(templates[j])) {
					file = new File(files[j] + lowerEntityDomain + "/" + lowerEntityDomain + "_input.jsp");
				} else if ("Service.java".equals(templates[j])) {
					file = new File(files[j] + "I" + domains[i] + templates[j]);
				}
				// // 控制是否覆盖已经存在的文件
				if (!FLAG && file.exists()) {
					// return; //结束所有代码
					// break; //结束当前这一次循环
					continue; // 结束本次内一层的循环,开始下一次循环
				}
				System.out.println(file.getAbsolutePath());

				// 只创建父目录
				File parentFile = file.getParentFile();
				if (!parentFile.exists()) {
					parentFile.mkdirs();
				}
				// 加载模板
				Template template = Velocity.getTemplate("template/" + templates[j], "UTF-8");
				// 准备输出流
				FileWriter writer = new FileWriter(file);
				// 合并数据和模板
				template.merge(context, writer);
				writer.flush();
				writer.close();
			}
		}
		System.out.println("刷新工程无异常，修改映射文件,运行测试,启动tomcat");
	}

	@Test
	public void testVelocity() throws Exception {
		// 创建模板应用上下文
		VelocityContext context = new VelocityContext();
		// 从html页面取值用el表达式:${msg}
		context.put("msg", "这是代码生成器");
		// 拿到相应的模板(需要设置好编码)
		Template template = Velocity.getTemplate("template2/hello.html", "UTF-8");
		// 准备输出流
		StringWriter writer = new StringWriter();
		// 合并数据
		template.merge(context, writer);
		System.out.println(writer);
	}
}
