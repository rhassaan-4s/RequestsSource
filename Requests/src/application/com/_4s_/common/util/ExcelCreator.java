package com._4s_.common.util;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.lang.exception.NestableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.Region;

import com._4s_.i18n.model.MyLocale;

public class ExcelCreator {
	
	public static final int NORMAL_FONT = 0;
	public static final int HEADER_FONT = 1;
	public static final int CONTENT_FONT = 2;

	//1. create new ExcelCreator & give the method the sheet name. 2. Create rows & their cells 3. write workbook to output stream
	private Log log = LogFactory.getLog(getClass());
	private HSSFWorkbook workBook = new HSSFWorkbook();
	private HSSFCellStyle cellStyle = workBook.createCellStyle();
	private String sheetName = "Report";		
	private HSSFSheet sheet;
	private HSSFSheet Region ;
	private HSSFFont font = workBook.createFont();
	HSSFFont fontHeader = workBook.createFont();
	HSSFFont fontContent = workBook.createFont();
	
	private short rowNo = 0;
	private short columnNo = 0;

	public ExcelCreator(String sheetName) {
		this.sheetName = sheetName;
		this.sheet = workBook.createSheet(this.sheetName);
		
		//  font.setColor(HSSFFont.COLOR_RED);
		font.setFontName("Arial");
		font.setFontHeightInPoints((short)10);
//		font.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );

		// fontHeader.setColor( HSSFColor.BLUE_GREY.index);
		fontHeader.setFontName("Arial");
		fontHeader.setFontHeightInPoints((short)12);
		fontHeader.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );

		// fontContent.setColor( HSSFColor.DARK_GREEN.index);
		fontContent.setFontName("Arial");
		fontContent.setFontHeightInPoints((short)10);
//		fontContent.setBoldweight( HSSFFont.BOLDWEIGHT_BOLD );
	}

	public HSSFRow createNewRow() {
		HSSFRow tableRow = sheet.createRow(rowNo);
		rowNo++;
		columnNo = 0;
		return tableRow;
	}

	public void createNewCell(HSSFRow row, String cellValue, int cellType) { //cellType 0:normal 1:header 2:content
		HSSFCell cell = row.createCell((short) columnNo);
		cell.setCellStyle(cellStyle);
		cell.setEncoding(org.apache.poi.hssf.usermodel.HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(cellValue);
		try {
			HSSFCellUtil.setAlignment(cell, workBook, HSSFCellStyle.ALIGN_CENTER);
		} catch (NestableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(cellType == 0) {
			try {
				HSSFCellUtil.setFont(cell,workBook,font);
			} catch (NestableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (cellType ==1) {
			try {
				HSSFCellUtil.setFont(cell,workBook,fontHeader);
			} catch (NestableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				HSSFCellUtil.setFont(cell,workBook,fontContent);
			} catch (NestableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		sheet.addMergedRegion(new Region(0, (short) 0, 1,(short)0));
		columnNo++;
	}
	
	public void createNewMergedCell(HSSFRow row, String cellValue, int cellType, int noOfCells) { //cellType 0:normal 1:header 2:content
		HSSFCell cell = row.createCell((short) columnNo);
		cell.setCellStyle(cellStyle);
		cell.setEncoding(org.apache.poi.hssf.usermodel.HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(cellValue);
		try {
			HSSFCellUtil.setAlignment(cell, workBook, HSSFCellStyle.ALIGN_CENTER);
		} catch (NestableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(cellType == 0) {
			try {
				HSSFCellUtil.setFont(cell,workBook,font);
			} catch (NestableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (cellType ==1) {
			try {
				HSSFCellUtil.setFont(cell,workBook,fontHeader);
			} catch (NestableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				HSSFCellUtil.setFont(cell,workBook,fontContent);
			} catch (NestableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sheet.addMergedRegion(new Region((short)(rowNo-1), columnNo, (short)(rowNo-1), (short)(columnNo + noOfCells - 1)));
		columnNo += noOfCells;
	}

	public void writeWorkbookToOutputStream(OutputStream os) {
		try {
			workBook.write(os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setFontName(String fontName) {
		font.setFontName(fontName);
	}

	public void setFontHeight(short fontHeight) {
		font.setFontHeightInPoints(fontHeight);
	}

	public void setFontHeaderName(String fontHeaderName) {
		fontHeader.setFontName(fontHeaderName);
	}

	public void setFontHeaderHeight(short fontHeaderHeight) {
		fontHeader.setFontHeightInPoints(fontHeaderHeight);
	}

	public void setFontContentName(String fontContentName) {
		fontContent.setFontName(fontContentName);
	}

	public void setFontContentHeight(short fontContentHeight) {
		fontContent.setFontHeightInPoints(fontContentHeight);
	}

}
