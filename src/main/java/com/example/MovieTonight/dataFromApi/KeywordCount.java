package com.example.MovieTonight.dataFromApi;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Getter
@Setter
public class KeywordCount {

   private Long keywordId;
   private Long genreId;

}
