package de.stevenschwenke.java.unittestintegrationtestdemo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {

    private String name;
    private Integer year;
    private Integer ranking;

}
