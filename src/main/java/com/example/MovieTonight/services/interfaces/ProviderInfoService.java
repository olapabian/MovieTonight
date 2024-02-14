package com.example.MovieTonight.services.interfaces;

import java.util.HashMap;

public interface ProviderInfoService {

    HashMap<Long,String> getAllProviders();
    public void saveAllProviders();

}
