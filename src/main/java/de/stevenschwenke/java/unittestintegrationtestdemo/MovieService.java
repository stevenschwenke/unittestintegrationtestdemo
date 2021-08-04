package de.stevenschwenke.java.unittestintegrationtestdemo;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final MovieImportService movieImportService;

    public MovieService(MovieImportService movieImportService) {
        this.movieImportService = movieImportService;
    }

    public String retrieveMovieRecommendation() throws IOException {

        List<Movie> movies = movieImportService.importMovies("movies.xlsx");

        List<Movie> moviesRearranged = rearrange(movies);

        return toRecommendationText(moviesRearranged);
    }

    static List<Movie> rearrange(List<Movie> movies) {

        Optional<Movie> bigLebowski =
                movies.stream().filter(movie -> "The Big Lebowski".equals(movie.getName())).findFirst();

        if(bigLebowski.isEmpty())
            return movies;

        Movie lebowskiSwap =
                movies.stream().filter(movie -> Integer.valueOf(1).equals(movie.getRanking())).findFirst().orElseThrow();
        lebowskiSwap.setRanking(bigLebowski.get().getRanking());

        bigLebowski.get().setRanking(1);

        return movies;
    }

    static String toRecommendationText(List<Movie> movies) {

        if (movies.isEmpty()) {
            return "No movies known. Please add some movies to create a recommendation.";
        }

        if (movies.size() == 1) {
            Movie movie = movies.get(0);
            return "Only one movie known: " + movie.getName() + " from " + movie.getYear() + ". " +
                    "Better watch that one and add some more later.";
        }

        String movieString = movies.stream()
                .sorted(Comparator.comparingInt(Movie::getRanking))
                .map(movie -> movie.getName() + " from " + movie.getYear())
                .collect(Collectors.joining("," + System.lineSeparator()));

        return "This is your personal movie recommendation. Watch the following films in that order:"
                + System.lineSeparator() +
                movieString;
    }

}
