package de.stevenschwenke.java.unittestintegrationtestdemo;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MovieImportService {

    public List<Movie> importMovies(String filename) throws IOException {

        byte[] importFile = readContentIntoByteArray(filename);

        List<Movie> movieImportFromFile = new ArrayList<>();
        InputStream file = new ByteArrayInputStream(importFile);

        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            if (containsValue(sheet.getRow(i))) {
                Movie movie = getMovieFromRow(sheet.getRow(i));
                movieImportFromFile.add(movie);
            }
        }

        file.close();
        workbook.close();

        return movieImportFromFile;
    }

    protected static byte[] readContentIntoByteArray(String filename) {

        ClassLoader classLoader = MethodHandles.lookup().lookupClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
        FileInputStream fileInputStream;
        byte[] byteArray = new byte[(int) file.length()];
        try {
            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(byteArray);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    private Movie getMovieFromRow(XSSFRow row) {

        DataFormatter formatter = new DataFormatter();

        return Movie.builder()
                .name(formatter.formatCellValue(row.getCell(0)))
                .year(Integer.valueOf(formatter.formatCellValue(row.getCell(1))))
                .ranking(Integer.valueOf(formatter.formatCellValue(row.getCell(2))))
                .build();
    }

    public static boolean containsValue(XSSFRow row) {
        boolean flag = false;
        for (int i = 0; i <= row.getPhysicalNumberOfCells(); i++) {
            if (!StringUtils.isEmpty(String.valueOf(row.getCell(i))) &&
                    !StringUtils.isWhitespace(String.valueOf(row.getCell(i))) &&
                    !StringUtils.isBlank(String.valueOf(row.getCell(i))) &&
                    String.valueOf(row.getCell(i)).length() != 0 &&
                    row.getCell(i) != null) {
                flag = true;
            }
        }
        return flag;
    }
}
