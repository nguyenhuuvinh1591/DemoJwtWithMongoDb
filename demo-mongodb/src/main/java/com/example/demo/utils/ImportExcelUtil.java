package com.example.demo.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.example.demo.common.ItemColsExcelDto;

public class ImportExcelUtil<T> {

	private enum DataType {
		LONG, DOUBLE, INTEGER, STRING, DATE, INT, BIGDECIMAL;
	}

	/** List<T> */
	private List<T> data;

	public List<T> getData() {
		return data != null ? new ArrayList<>(data) : null;
	}

	public void setData(List<T> data) {
		this.data = data != null ? new ArrayList<>(data) : null;
	}

	public void setDataFileExcel(Iterator<Row> rows, List<ItemColsExcelDto> cols, Row rowHeader, Class<T> obj)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		try {
			List<T> list = new ArrayList<T>();
			Map<String, Field> mapFields = new HashMap<String, Field>();
			// get list fields of class
			T objDefault = obj.newInstance();
			Class<?> cls = objDefault.getClass();
			Field[] fields = cls.getDeclaredFields();

			for (Field f : fields) {
				mapFields.put(f.getName().toUpperCase(), f);
			}

			// loop rows
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				T objAdd = obj.newInstance();
				// set value of object
				for (ItemColsExcelDto col : cols) {
					Cell currentCell = currentRow.getCell(col.getColIndex());
					// set value of fields
					Field f = mapFields.get(col.getColName().toUpperCase().trim());
					f.setAccessible(true);
					String typeFields = f.getType().getSimpleName();
					typeFields = typeFields.toUpperCase();
					DataType dataType = DataType.valueOf(typeFields);
					if (currentCell != null && currentCell.getCellType() != CellType.BLANK) {
						String valueOfString = "";
						Date valueOfDate = null;
						// check type cell
						switch (currentCell.getCellType()) {
						case BOOLEAN:
							break;
						case STRING:
							valueOfString = currentCell.getStringCellValue();
							break;
						case NUMERIC:
							if (DateUtil.isCellDateFormatted(currentCell)) {
								valueOfDate = currentCell.getDateCellValue();
							} else {
								valueOfString = String.valueOf(currentCell.getNumericCellValue());
							}
							break;
						case FORMULA:
							break;
						default:
							break;
						}

						// set value in object
						valueOfString = valueOfString.trim();

						switch (dataType) {
						case LONG:
							Long valueOfLong = valueOfString.length() == 0 ? null : Long.parseLong(valueOfString);
							f.set(objAdd, valueOfLong);
							break;
						case DOUBLE:
							Double valueOfDouble = valueOfString.length() == 0 ? null
									: Double.parseDouble(valueOfString);
							f.set(objAdd, valueOfDouble);
							break;
						case INTEGER:
						case INT:
							int valueOfInt = valueOfString.length() == 0 ? null : (int) Float.parseFloat(valueOfString); // special
							// case
							// :
							// value
							// =
							// 1.0
							f.set(objAdd, valueOfInt);
							break;
						case STRING:
							f.set(objAdd, valueOfString);
							break;
						case DATE:
							f.set(objAdd, valueOfDate);
							break;
						default:
							break;
						}
					}
				}

				list.add(objAdd);
			} // end white
			this.data = list;
		} catch (Exception ex) {
			this.data = new ArrayList<T>();
		}
	}

	public void setDataFileExcel(Sheet sheet, int startRow, int lastRow, List<ItemColsExcelDto> cols, Row rowHeader,
			Class<T> obj)
			throws IllegalArgumentException, IllegalAccessException, InstantiationException, ParseException {
		List<T> list = new ArrayList<>();

		Map<String, Field> mapFields = new HashMap<>();
		// get list fields of class
		T objDefault = obj.newInstance();
		Class<?> cls = objDefault.getClass();
		Field[] fields = cls.getDeclaredFields();

		for (Field f : fields) {

			mapFields.put(f.getName().toUpperCase(), f);

		}

		// loop rows to get data
		for (int i = startRow; i < lastRow; i++) {
			try {
				Row currentRow = sheet.getRow(i);
				T objAdd = obj.newInstance();

				// set value of object
				for (ItemColsExcelDto col : cols) {

					Cell currentCell = currentRow.getCell(col.getColIndex());

					// set value of fields
					Field f = mapFields.get(col.getColName().toUpperCase().trim());

					f.setAccessible(true);
					String typeFields = f.getType().getSimpleName();
					typeFields = typeFields.toUpperCase();
					DataType dataType = DataType.valueOf(typeFields);
					if (currentCell != null && currentCell.getCellType() != CellType.BLANK) {
						String valueOfString = "";
						Date valueOfDate = null;
						// check type cell
						switch (currentCell.getCellType()) {
						case BOOLEAN:
							valueOfString = currentCell.getBooleanCellValue() + "";
							break;
						case STRING:

							valueOfString = currentCell.getStringCellValue();

							break;

						case NUMERIC:

							if (DateUtil.isCellDateFormatted(currentCell)) {

								valueOfDate = currentCell.getDateCellValue();
								valueOfString = new SimpleDateFormat("dd-MMM-yyyy").format(valueOfDate);

							} else if (dataType == DataType.STRING) {

								// cast number format to int format
								valueOfString = String.valueOf(
										(int) Double.parseDouble(String.valueOf(currentCell.getNumericCellValue())));

							} else {

								valueOfString = String.valueOf(currentCell.getNumericCellValue());

							}

							break;
						case FORMULA:
							break;
						default:
							break;
						}

						// set value in object
						valueOfString = valueOfString.trim();

						switch (dataType) {
						case LONG:
							Long valueOfLong = valueOfString.length() == 0 ? null
									: (long) Double.parseDouble(valueOfString);
							f.set(objAdd, valueOfLong);
							break;
						case DOUBLE:
							Double valueOfDouble = valueOfString.length() == 0 ? null
									: Double.parseDouble(valueOfString);
							f.set(objAdd, valueOfDouble);
							break;
						case INTEGER:
							Integer valueOfInt = null;

							if (valueOfString.length() != 0) {

								if ("true".equalsIgnoreCase(valueOfString)) {
									valueOfInt = 1;
								} else if ("false".equalsIgnoreCase(valueOfString)) {
									valueOfInt = 0;
								} else {
									valueOfInt = (int) Float.parseFloat(valueOfString);
								}

							}

							f.set(objAdd, valueOfInt);
							break;
						case INT:

							int valueOfIntPri = 0;

							if (valueOfString.length() != 0) {

								if ("true".equalsIgnoreCase(valueOfString)) {
									valueOfIntPri = 1;
								} else if ("false".equalsIgnoreCase(valueOfString)) {
									valueOfIntPri = 0;
								} else {
									valueOfIntPri = (int) Float.parseFloat(valueOfString);
								}

							}

							f.set(objAdd, valueOfIntPri);
							break;
						case STRING:
							f.set(objAdd, valueOfString);
							break;
						case DATE:

							if (valueOfDate == null) {

								f.set(objAdd, new SimpleDateFormat("dd-MMM-yyyy").parse(valueOfString));

							} else {

								f.set(objAdd, valueOfDate);

							}

							break;
						case BIGDECIMAL:
							// Create a DecimalFormat that fits your
							// requirements
							DecimalFormatSymbols symbols = new DecimalFormatSymbols();
							symbols.setGroupingSeparator(',');
							symbols.setDecimalSeparator('.');
							String pattern = "#,##0.0#";
							DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
							decimalFormat.setParseBigDecimal(true);
							BigDecimal valueOfBigDecimal = valueOfString.length() == 0 ? null
									: (BigDecimal) decimalFormat.parse(valueOfString);
							f.set(objAdd, valueOfBigDecimal);
							break;
						default:
							break;
						}
					}

				}

				list.add(objAdd);

			} catch (NullPointerException ex) {
				this.data = list;
				break;
			}
		} // end of file

		this.data = list;

	}

	public static <E extends Enum<E>> Boolean isValidFileExcelImport(Row rowHeader, List<ItemColsExcelDto> cols) {
		Boolean isCheck = true;
		for (int i = 0; i < rowHeader.getLastCellNum(); i++) {
			Cell currentCell = rowHeader.getCell(i);
			String valueCell = "";
			switch (currentCell.getCellType()) {
			case BOOLEAN:
				valueCell = String.valueOf(currentCell.getBooleanCellValue());
			case STRING:
				valueCell = currentCell.getStringCellValue();
				break;
			case NUMERIC:
				valueCell = String.valueOf(currentCell.getNumericCellValue());
				break;
			case FORMULA:
				break;
			default:
				break;
			}

			Boolean isExist = false;
			for (ItemColsExcelDto col : cols) {
				if (col.getColName().equals(valueCell) && i == col.getColIndex()) {
					isExist = true;
					break;
				}
			}
			if (isExist == false) {
				isCheck = false;
				break;
			}
		}
		// check columns have loop in file ?
		if (isCheck == true) {
			for (int i = 0; i < rowHeader.getLastCellNum(); i++) {
				Cell celli = rowHeader.getCell(i);
				Boolean isExist = false;
				for (int j = i + 1; j < rowHeader.getLastCellNum(); j++) {
					Cell cellj = rowHeader.getCell(j);
					if (celli.getStringCellValue().trim().equals(cellj.getStringCellValue().trim())) {
						isExist = true;
						break;
					}
				}
				if (isExist == true) {
					isCheck = false;
					break;
				}
			}
		}
		return isCheck;
	}

	public static <E extends Enum<E>> void setListColumnExcel(Class<E> enumType, List<ItemColsExcelDto> cols) {
		// loop enum
		for (E en : enumType.getEnumConstants()) {
			ItemColsExcelDto itemCol = new ItemColsExcelDto();
			itemCol.setColName(en.name());
			itemCol.setColIndex(Integer.parseInt(en.toString()));
			cols.add(itemCol);
		}
	}
}
