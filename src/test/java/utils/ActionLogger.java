package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ActionLogger {

    private final WebDriver driver;
    private final List<ActionStep> steps = new ArrayList<>();
    private final Path pdfFile;

    public ActionLogger(WebDriver driver, Path pdfFile) {
        this.driver = driver;
        this.pdfFile = pdfFile;
    }

    public void logStep(String description) throws IOException {
        // Take screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destFile = new File("target/reports/screenshots/" + System.currentTimeMillis() + ".png");
        FileUtils.copyFile(srcFile, destFile);

        steps.add(new ActionStep(description, destFile));
    }

    public void generatePdfReport() throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile.toFile()));
        document.open();

        for (ActionStep step : steps) {
            Image screenshot = Image.getInstance(step.screenshot.getAbsolutePath());
            screenshot.scaleToFit(500, 400);
            screenshot.setAlignment(Element.ALIGN_CENTER);
            document.add(screenshot);

            Paragraph desc = new Paragraph(
                    step.description,
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK)
            );
            desc.setSpacingBefore(10f);
            desc.setAlignment(Element.ALIGN_CENTER);
            document.add(desc);

            document.newPage();
        }

        document.close();
    }

    private static class ActionStep {
        String description;
        File screenshot;

        public ActionStep(String description, File screenshot) {
            this.description = description;
            this.screenshot = screenshot;
        }
    }
}
