package com.leowan.pss;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

public class UploadTest {

	@Test
	public void test() throws Exception {
		// 准备要读取的文件流
		FileInputStream is = new FileInputStream("xxxx.xlsx");
		// 创建读取对象
		Workbook workbook = new XSSFWorkbook(is);
		// 固定获取读取的第一个sheet表
		Sheet sheet = workbook.getSheetAt(0);
		// 获取表里面有多少行
		int num = sheet.getLastRowNum();
		// 遍历行
		for (int i = 1; i <= num; i++) {
			// 获取行对象
			Row row = sheet.getRow(i);
			// 获取行里面有多少列
			short cellNum = row.getLastCellNum();
			for (int j = 1; j < cellNum; j++) {
				// 获取格子对象
				Cell cell = row.getCell(j);
				// 获格格子对象里面的数据
				System.out.print(cell.getNumericCellValue() + "\t");
			}
			System.out.println();
		}
		workbook.close();
	}

}
