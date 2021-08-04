package de.stevenschwenke.java.unittestintegrationtestdemo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("slow_integration_test")
@SpringBootTest
class MovieServiceIntegrationTest {

    @Autowired
    private MovieService movieService;

    @Test
    void retrievingMovieRecommendationReturnsReadableString() throws IOException {

        String output = movieService.retrieveMovieRecommendation();

        System.out.println(output);

        assertEquals("This is your personal movie recommendation. Watch the following films in that order:"
                + System.lineSeparator() +
                "The Big Lebowski from 1998," + System.lineSeparator() +
                "Lord of the Rings from 2001," + System.lineSeparator() +
                "Matrix from 1999", output);
    }
}