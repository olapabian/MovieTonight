package com.example.MovieTonight.Model.others;

import com.example.MovieTonight.JSONs.Filmweb.InfoRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoAndBool {
    private InfoRequest infoRequest;
    private boolean flag;

    public boolean getFlag() {
        return  flag;
    }
}
