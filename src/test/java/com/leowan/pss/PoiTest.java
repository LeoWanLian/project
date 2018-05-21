package com.leowan.pss;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

public class PoiTest {

	@Test
	public void test() throws IOException {
		// 创建一个workbook对象内存
		SXSSFWorkbook wb = new SXSSFWorkbook();
		// 创建一个表
		Sheet sh = wb.createSheet();
		// 新建一行，且是第一行，java中的行是从0开始计算的
		for (int rownum = 1; rownum < 10; rownum++) {
			// 创建表里面的行
			Row row = sh.createRow(rownum);
			for (int cellnum = 1; cellnum < 10; cellnum++) {
				// 创建列.处理单元格
				Cell cell = row.createCell(cellnum);
				cell.setCellValue(rownum * cellnum);
			}
		}
		FileOutputStream out = new FileOutputStream("xxxx.xlsx");
		//workbook对象内存调用write方法以FileOutputStream的形式写出
		wb.write(out);
		out.close();
		wb.dispose();
	}

}
