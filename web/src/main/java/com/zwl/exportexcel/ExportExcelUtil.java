package com.zwl.exportexcel;

import com.zwl.model.vo.ExcelConfig;
import org.apache.poi.hssf.usermodel.*;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ExportExcelUtil<T> {

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    public void exportExcel(String title, List<T> dataset, OutputStream out, String pattern) throws Exception {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        if (dataset == null || dataset.size() == 0)
            return;
        // 获取一个对象
        T tempT = dataset.get(0);
        // 获取对象的属性字段
        Field[] heads = tempT.getClass().getDeclaredFields();
        // 创建一个表头list
        List<String> headList = new ArrayList<>();
        // 获取字段注解的表头
        for (int i = 0; i < heads.length; i++) {
            // 过滤掉没加注解的字段
            if (heads[i].getAnnotations().length == 0)
                continue;
            ExcelConfig exAnno = (ExcelConfig) heads[i].getAnnotations()[0];
            String head = exAnno.head();
            int width = exAnno.width();
            System.out.println("width" + width);
            // 汉字是512，数字是256.
            sheet.setColumnWidth(i, width * 256);

            headList.add(head);
        }
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headList.size(); i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headList.get(i));
            cell.setCellValue(text);

        }
        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            Field[] fields = t.getClass().getDeclaredFields();
            List<Field> fieldsList = new ArrayList<>();
            for (Field field : fields) {
                if (field.getAnnotations().length != 0)
                    fieldsList.add(field);
            }
            for (Field field : fieldsList) {
                HSSFCell cell = row.createCell(fieldsList.indexOf(field));

                String fieldName = field.getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                Object value = getMethod.invoke(t, new Object[]{});
                // 判断值的类型后进行强制类型转换
                String textValue = null;
                if (value == null) {
                    cell.setCellValue("");
                }
                if (value instanceof Integer) {
                    int intValue = (Integer) value;
                    cell.setCellValue(intValue);
                } else if (value instanceof Float) {
                    float fValue = (Float) value;
                    cell.setCellValue(fValue);
                } else if (value instanceof Double) {
                    double dValue = (Double) value;
                    cell.setCellValue(dValue);
                } else if (value instanceof Long) {
                    long longValue = (Long) value;
                    cell.setCellValue(longValue);
                } else if (value instanceof Date) {
                    Date date = (Date) value;
                    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                    textValue = sdf.format(date);
                    cell.setCellValue(textValue);
                } else {
                    // 其它数据类型都当作字符串简单处理
                    textValue = value == null ? "" : value.toString();
                    cell.setCellValue(textValue);
                }
            }
        }
        workbook.write(out);
    }

}
