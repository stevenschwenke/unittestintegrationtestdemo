package de.stevenschwenke.java.unittestintegrationtestdemo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("slow_integration_test")
@SpringBootTest
class MovieImportServiceIntegrationTests {

    @Autowired
    private MovieImportService movieImportService;

    @Test
    void importFromExcelCreatesCorrectListOfJavaObjects() throws IOException {

        List<Movie> importedMovies = movieImportService.importMovies("movies.xlsx");

        assertEquals(3, importedMovies.size());

        assertEquals("Matrix", importedMovies.get(0).getName());
        assertEquals(1999, importedMovies.get(0).getYear());
        assertEquals(3, importedMovies.get(0).getRanking());
        assertEquals("The Big Lebowski", importedMovies.get(1).getName());
        assertEquals(1998, importedMovies.get(1).getYear());
        assertEquals(2, importedMovies.get(1).getRanking());
        assertEquals("Lord of the Rings", importedMovies.get(2).getName());
        assertEquals(2001, importedMovies.get(2).getYear());
        assertEquals(1, importedMovies.get(2).getRanking());
    }

}