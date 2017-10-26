package ua.lady.scm.parser;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ua.lady.scm.domain.ExcelConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;

@Service
@Log
@Setter
@Getter
public class ExcelParser {
    private ExcelConfig config;
    private DataFormatter dataFormatter = new DataFormatter();


    public List<Map<String, String>> parse(InputStream excel) throws IOException {
        List<Map<String, String>> res = new ArrayList<>();
        try {
            Workbook workbook = null;
            try {
                workbook = new HSSFWorkbook(excel);
            } catch (OLE2NotOfficeXmlFileException e) {
                workbook = new XSSFWorkbook(excel);
            }
            Sheet sheet = workbook.getSheetAt(0);
            sheet.getRow(config.getHeaderRowIndex() + 1);
            log.finest(config.toString());
            log.finest("Last Logical Row Num: " + sheet.getLastRowNum());
            log.finest("Last Physical Row Num: " + sheet.getPhysicalNumberOfRows());
            List<String> categories = new ArrayList<>();
            List<String> tmpCategories = new ArrayList<>();
            boolean categoryProcessed = false;
            for (int i = config.getHeaderRowIndex(); i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (log.isLoggable(Level.FINEST)) {
                    printRow(row);
                }
                if (checkIfRowIsEmpty(row)) continue;
                //check if price present - this is product row
                int priceIx = config.getColumnIndex("price") - 1;
                Cell priceCell = row.getCell(priceIx);
                if (!Objects.isNull(priceCell) && priceCell.getCellTypeEnum() == CellType.NUMERIC) {
                    // product row
                    // process categories
                    if (!categoryProcessed) {
                        if (tmpCategories.size() > categories.size()) {
                            categories.clear();
                            categories.addAll(tmpCategories);
                        } else {
                            for (int j = 0; j < tmpCategories.size(); j++) {
                                categories.set(categories.size() - tmpCategories.size() + j, tmpCategories.get(j));
                            }
                        }
                        tmpCategories.clear();
                        categoryProcessed = true;
                    }
                    // process values
                    Map<String, String> productMap = new HashMap<>();
                    config.getColumnMapping().forEach((k, v) -> {
                        Cell cell = row.getCell(v - 1);
                        String value = getCellValue(row.getCell(v - 1));
                        if (!StringUtils.isEmpty(value)) {
                            productMap.put(k, value);
                        }
                    });
                    if (!categories.isEmpty()) {
                        ExcelConfig.BrandStrategy brandStrategy = config.getBrandStrategy();
                        switch (brandStrategy) {
                            case FIRST:
                                productMap.put("brand", categories.get(0));
                                break;
                            case LAST:
                                productMap.put("brand", categories.get(categories.size() - 1));
                                break;
                        }
                    }
                    res.add(productMap);
                } else {
                    //brand row
                    Iterator<Cell> it = row.cellIterator();
                    while (it.hasNext()) {
                        Cell cell = it.next();
                        String value = getCellValue(cell);
                        if (!StringUtils.isEmpty(value)) {
                            tmpCategories.add(value);
                            break;
                        }
                    }
                    categoryProcessed = false;
                }
            }
        } finally {
            excel.close();
        }
        return res;
    }

    private void printRow(Row row) {
        log.finest(String.format("[%d] first[%d] last[%d] physical[%d]", row.getRowNum(), row.getFirstCellNum(), row.getLastCellNum(), row.getPhysicalNumberOfCells()));
        short minColIx = row.getFirstCellNum();
        short maxColIx = row.getLastCellNum();
        for (short colIx = minColIx; colIx < maxColIx; colIx++) {
            Cell cell = row.getCell(colIx);
            if (cell == null) {
                continue;
            }
            log.finest(String.format("\t%s", printCell(cell)));

        }
    }

    public String getCellValue(Cell cell) {
        return StringUtils.trimWhitespace(dataFormatter.formatCellValue(cell));
    }

    public String printCell(Cell cell) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(cell.getColumnIndex());
        switch (cell.getCellTypeEnum()) {
            case _NONE:
                break;
            case NUMERIC:
                sb.append(",").append(cell.getNumericCellValue());
                break;
            case STRING:
                sb.append(",").append(cell.getStringCellValue());
                break;
            case FORMULA:
                sb.append(",").append(cell.getCellFormula());
                break;
            case BLANK:
                break;
            case BOOLEAN:
                sb.append(",").append(cell.getBooleanCellValue());
                break;
            case ERROR:
                sb.append(",").append(cell.getErrorCellValue());
                break;
        }
        sb.append(",").append(cell.getCellTypeEnum()).append("]");
        return sb.toString();
    }

    private boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK && !StringUtils.isEmpty(getCellValue(cell))) {
                return false;
            }
        }
        return true;
    }
}
