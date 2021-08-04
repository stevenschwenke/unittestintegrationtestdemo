package de.stevenschwenke.java.unittestintegrationtestdemo;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@Tag("fast_unit_test")
public class MovieServiceUnitTest {

    @Test
    void recommendationIsEmptyInCaseOfNoMovies() {

        String recommendationText = MovieService.toRecommendationText(new ArrayList<>());

        assertEquals("No movies known. Please add some movies to create a recommendation.", recommendationText);
    }

    @Test
    void recommendationListsOnlyOneMovieInCaseOfOneMovieKnown() {

        String recommendationText =
                MovieService.toRecommendationText(List.of(
                        Movie.builder().name("Matrix").year(1999).ranking(1).build()));

        assertEquals("Only one movie known: Matrix from 1999. Better watch that one and add some more later.",
                recommendationText);

        recommendationText =
                MovieService.toRecommendationText(List.of(
                        Movie.builder().name("Lord of the Rings").year(2001).ranking(1).build()));

        assertEquals("Only one movie known: Lord of the Rings from 2001. Better watch that one and add some more " +
                        "later.",
                recommendationText);
    }

    @Test
    void recommendationListsAllMoviesInCorrectOrder() {

        List<Movie> movies = List.of(
                Movie.builder().name("Matrix").year(1999).ranking(3).build(),
                Movie.builder().name("The Big Lebowski").year(1998).ranking(2).build(),
                Movie.builder().name("Lord of the Rings").year(2001).ranking(1).build());

        assertEquals("This is your personal movie recommendation. Watch the following films in that order:"
                + System.lineSeparator() +
                "Lord of the Rings from 2001," + System.lineSeparator() +
                "The Big Lebowski from 1998," + System.lineSeparator() +
                "Matrix from 1999", MovieService.toRecommendationText(movies));
    }

    @Test
    void emptyMovieListDoesNotRearrangeAndReturnsEmptyList() {
        assertTrue(MovieService.rearrange(List.of()).isEmpty());
    }

    @Test
    void movieListWithOneMovieDoesNotRearrangeAndReturnsListWithOneMovie() {

        List<Movie> rearrangedList = MovieService.rearrange(List.of(
                Movie.builder().name("One Movie").year(2000).ranking(42).build()));

        assertEquals(1, rearrangedList.size());
        assertEquals("One Movie", rearrangedList.get(0).getName());
    }

    @Test
    void movieListRearrangementPutsLebowskiAlwaysOnTop() {

        List<Movie> rearrangedList = MovieService.rearrange(List.of(
                Movie.builder().name("Matrix").year(1999).ranking(3).build(),
                Movie.builder().name("The Big Lebowski").year(1998).ranking(2).build(),
                Movie.builder().name("Lord of the Rings").year(2001).ranking(1).build()
        ));

        assertEquals(3, rearrangedList.size());

        assertEquals("Matrix", rearrangedList.get(0).getName());
        assertEquals(3, rearrangedList.get(0).getRanking());
        assertEquals("The Big Lebowski", rearrangedList.get(1).getName());
        assertEquals(1, rearrangedList.get(1).getRanking());
        assertEquals("Lord of the Rings", rearrangedList.get(2).getName());
        assertEquals(2, rearrangedList.get(2).getRanking());
    }

}
