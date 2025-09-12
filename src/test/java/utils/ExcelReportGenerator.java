package utils;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestResult;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ExcelReportGenerator {

    public static void generateExcel(List<ITestResult> results, File outFile) {
        try (Workbook wb = new XSSFWorkbook()) {
            // Dashboard sheet
            Sheet dash = wb.createSheet("dashboard");
            Row header = dash.createRow(0);
            header.createCell(0).setCellValue("Metric");
            header.createCell(1).setCellValue("Count");

            Map<String, Integer> metrics = computeMetrics(results);
            int r = 1;
            for (Map.Entry<String, Integer> e : metrics.entrySet()) {
                Row row = dash.createRow(r++);
                row.createCell(0).setCellValue(e.getKey());
                row.createCell(1).setCellValue(e.getValue());
            }

            // TestClassResults sheet
            Sheet resultsSheet = wb.createSheet("TestClassResults");
            Row rh = resultsSheet.createRow(0);
            rh.createCell(0).setCellValue("Class Name");
            rh.createCell(1).setCellValue("Test Name");
            rh.createCell(2).setCellValue("Status");
            rh.createCell(3).setCellValue("Failure Details");

            int row = 1;
            for (ITestResult tr : results) {
                Row rr = resultsSheet.createRow(row++);
                rr.createCell(0).setCellValue(tr.getTestClass().getName());
                rr.createCell(1).setCellValue(tr.getMethod().getMethodName());
                String status = (tr.getStatus() == ITestResult.FAILURE) ? "FAIL" : (tr.getStatus() == ITestResult.SKIP ? "SKIP" : "PASS");
                rr.createCell(2).setCellValue(status);
                if (tr.getThrowable() != null) {
                    rr.createCell(3).setCellValue(tr.getThrowable().toString());
                } else {
                    rr.createCell(3).setCellValue("");
                }
            }

            // auto-size a few columns
            for (int i = 0; i < 4; i++) resultsSheet.autoSizeColumn(i);

            // write file
            outFile.getParentFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(outFile)) {
                wb.write(fos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> computeMetrics(List<ITestResult> results) {
        Map<String, Integer> m = new HashMap<>();
        int pass=0, fail=0, skip=0;
        for (ITestResult r : results) {
            if (r.getStatus() == ITestResult.FAILURE) fail++;
            else if (r.getStatus() == ITestResult.SKIP) skip++;
            else pass++;
        }
        m.put("Total Tests", results.size());
        m.put("Passed", pass);
        m.put("Failed", fail);
        m.put("Skipped", skip);
        return m;
    }
}
