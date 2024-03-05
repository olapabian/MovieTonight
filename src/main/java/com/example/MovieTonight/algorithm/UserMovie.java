package com.example.MovieTonight.algorithm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMovie {
    public String title;
    public String originalTitle;
    public Long matchingKeywords;
}
