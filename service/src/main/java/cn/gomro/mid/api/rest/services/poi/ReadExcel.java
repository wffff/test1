package cn.gomro.mid.api.rest.services.poi;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class ReadExcel {
    private final static String EXCEL_2003_SUFFIX = "xls";
    private final static String EXCEL_2007_SUFFIX = "xlsx";

    private Workbook wb = null;

    public Workbook read(String fileName) {
        InputStream inputStream = null;
        inputStream = readExcelToStream(fileName);

        if (inputStream != null) {
            try {
                if (isXLSX(fileName)) {
                    wb = new XSSFWorkbook(inputStream);
                } else if (isXLS(fileName)) {
                    wb = new HSSFWorkbook(inputStream);
                }
                return wb;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return wb;
    }

    private InputStream readExcelToStream(String url) {
        InputStream inputStream = null;
        URL fileUrl = transUrl(url);
        URLConnection urlConnection = null;
        try {
            urlConnection = fileUrl.openConnection();
            inputStream = urlConnection.getInputStream();
            return inputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isXLSX(String fileName) {
        boolean isExcel2007 = false;
        if (fileName.endsWith(EXCEL_2007_SUFFIX)) {
            isExcel2007 = true;
        }
        return isExcel2007;
    }

    public boolean isXLS(String fileName) {
        boolean isExcel2003 = false;
        if (fileName.endsWith(EXCEL_2003_SUFFIX)) {
            isExcel2003 = true;
        }
        return isExcel2003;
    }

    //链接地址转换
    private static URL transUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


}
