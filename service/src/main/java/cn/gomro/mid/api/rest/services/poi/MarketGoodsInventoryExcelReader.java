package cn.gomro.mid.api.rest.services.poi;

import cn.gomro.mid.core.biz.goods.biz.GoodsVO;
import cn.gomro.mid.core.biz.goods.service.impl.GoodsSpecService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremy on 2016/7/14.
 * http://www.cnblogs.com/hongten/p/java_poi_excel_xls_xlsx.html
 */
public class MarketGoodsInventoryExcelReader {
    final Logger logger = LoggerFactory.getLogger(GoodsSpecService.class);

    public List<GoodsVO> read(String url) {
        ReadExcel excel = new ReadExcel();
        HSSFWorkbook wb = (HSSFWorkbook) excel.read(url);


        GoodsVO goods = null;
        List<GoodsVO> list = new ArrayList<GoodsVO>();

        //循环读取表格（仅处理第一页）
        for (int numSheet = 0; numSheet < 1; numSheet++) {
            HSSFSheet sheet = wb.getSheetAt(numSheet);
            if (sheet == null) {
                continue;
            }
            //rowNum ：行号起始Index
            for (int rowNum = 3; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow row = sheet.getRow(rowNum);
                if (row != null) {
                    Integer rowNumber = row.getRowNum();
                    Integer flag = 0;
                    Integer uid = 0;
                    Integer uType = 1;
                    Integer memberId = 0;
                    Integer pid = null;

                    String category = getString(row.getCell(0));
                    String brand = getString(row.getCell(1));
                    String name = getString(row.getCell(2));
                    String model = getString(row.getCell(3));
                    String spec = getString(row.getCell(4));
                    String packageNum = getString(row.getCell(5));
                    String packageUnit = getString(row.getCell(6));
                    String minOrder = getString(row.getCell(7));
                    String unit = getString(row.getCell(8));
                    String price = getString(row.getCell(9));
                    String marketPrice = getString(row.getCell(10));
                    String discount = getString(row.getCell(11));
                    String delivery = getString(row.getCell(12));
                    String goodsDescript = getString(row.getCell(13));
                    String length = getString(row.getCell(14));
                    String width = getString(row.getCell(15));
                    String height = getString(row.getCell(16));
                    String weight = getString(row.getCell(17));
                    String warranty = getString(row.getCell(18));
                    String inventoryNum = getString(row.getCell(19));
                    String warehouseName = getString(row.getCell(20));
                    String memo = getString(row.getCell(21));
                    String sku = getString(row.getCell(22));
                    String freightTemplate = getString(row.getCell(23));

                    goods = new GoodsVO(rowNumber, flag, uid, uType, memberId, pid, category, brand, name, model, spec, packageNum, packageUnit, sku, weight, length, width, height, warranty, minOrder, unit, price, marketPrice, discount, delivery, goodsDescript, inventoryNum, warehouseName, memo, freightTemplate);
                    if (!StringUtils.isNotBlank(goods.getBrand()) &&
                            !StringUtils.isNotBlank(goods.getName()) &&
                            !StringUtils.isNotBlank(goods.getModel())) {
                        continue;
                    }
                    list.add(goods);
                }
            }
        }
        return list;

    }

    private String getString(HSSFCell cell) {
        String ret = "";
        if (null != cell) {
            try {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_BLANK:
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        ret = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_ERROR:
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        ret = cell.getRichStringCellValue().getString();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        ret = NumberToTextConverter.toText(cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        ret = cell.getRichStringCellValue().getString();
                        break;
                    default:
                        ret = "";
                }
            } catch (Exception e) {
                logger.error("Method: getString(), {}", e.getMessage());
                if (e.getCause() != null) {
                    logger.error("Method: getString(), {}", e.getCause().getMessage());
                }
            }
        }
        return ret;
    }
}
