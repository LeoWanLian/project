package com.leowan.pss.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.leowan.pss.query.BaseQuery;
import com.leowan.pss.query.PageList;
import com.leowan.pss.repository.BaseRepository;
import com.leowan.pss.service.IBaseService;

/**
 * service层要处理事务
 * 
 * @author LeoWan
 *
 * @param <T>
 * @param <ID>
 */
// 在这里只有两个方法需要事务,所以配置全局为不需要事务,再到需要事务的两个方法上面加上事务注解
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class BaseServiceImpl<T, ID extends Serializable> implements IBaseService<T, ID> {
	// spring4.x开始直接注入BaseRepository，自动获取对应的子接口
	@Autowired
	protected BaseRepository<T, ID> baseRepository;

	@Override
	@Transactional
	public void save(T t) {
		baseRepository.save(t);
	}

	@Override
	@Transactional
	public void delete(ID id) {
		baseRepository.delete(id);

	}

	@Override
	public T get(ID id) {
		// baseRepository.findOne(id);不是懒加载
		// baseRepository.getOne(id); 是懒加载
		/*
		 * find()返回指定OID的实体，如果这个实体存在于当前的persistence context中，
		 * 那么返回值是被缓存的对象；否则会创建一个新的实体，并从数据库中加载相关的持久状态。如果数据库不存在指定的OID的记录，
		 * 那么find()方法返回null。
		 */
		return baseRepository.findOne(id);
	}

	@Override
	public List<T> getAll() {

		return baseRepository.findAll();
	}

	@Override
	public List findByJpql(String jpql, Object... values) {

		return baseRepository.findByJpql(jpql, values);
	}

	@Override
	public List findCacheByJpql(String jpql, Object... values) {

		return baseRepository.findCacheByJpql(jpql, values);
	}

	@Override
	public PageList findByQuery(BaseQuery baseQuery) {

		return baseRepository.findByQuery(baseQuery);
	}

	@Override
	public InputStream download(List<String[]> data, String[] heads) throws Exception {
		// 创建一个对象内存
		SXSSFWorkbook wb = new SXSSFWorkbook();
		// 创建一个表
		Sheet sh = wb.createSheet();
		// 创建表里面的行
		// 先处理表头
		Row row = sh.createRow(0);
		for (int i = 0; i < heads.length; i++) {
			// 处理单元格
			Cell cell = row.createCell(i);
			cell.setCellValue(heads[i]);
		}
		// 处理数据
		for (int rownum = 0; rownum < data.size(); rownum++) {
			// 创建表里面的行,排除表头
			row = sh.createRow(rownum + 1);
			// 获取每一行的数据
			String[] strings = data.get(rownum);
			for (int cellnum = 0; cellnum < strings.length; cellnum++) {
				// 处理单元格
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(strings[cellnum]);
			}
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		wb.write(out);
		out.close();
		wb.dispose();

		return new ByteArrayInputStream(out.toByteArray());
	}

	@Override
	public List<String[]> importXlsx(File upload) throws Exception {
		// 准备返回的List
		List<String[]> list = new ArrayList<String[]>();
		// 准备要读取的文件流
		FileInputStream is = new FileInputStream(upload);// 解析的是上传的xlsx文件
		// 创建读取对象
		Workbook workbook = new XSSFWorkbook(is);
		// 固定获取读取的第一个sheet表
		Sheet sheet = workbook.getSheetAt(0);
		// 获取表里面有多少行
		int num = sheet.getLastRowNum();
		// 遍历行
		for (int i = 0; i < num; i++) {
			// 获取行对象
			Row row = sheet.getRow(i + 1);// 按照我们的规定上传的xlsx文件的包含表头的，排除表头i = 1
			// 获取行里面有多少列
			short cellNum = row.getLastCellNum();
			// 实例化数组
			String[] strings = new String[cellNum];
			for (int j = 0; j < cellNum; j++) {
				// 获取格子对象
				Cell cell = row.getCell(j);
				// 获格格子对象里面的数据
				strings[j] = cell.getStringCellValue();
			}
			// 添加数据到集合
			list.add(strings);
		}
		return list;
	}

}
