package com.example.majid_fit5.mornitask.base;

import com.example.majid_fit5.mornitask.data.RemoteDataSource;
import com.example.majid_fit5.mornitask.data.DataRepository;

/**
 * Created by Eng. Abdulmajid Alyafey on 12/14/2017.
 */

public class Injection {

    // provide the data repository....
    public static DataRepository getDataRepository(){
       return DataRepository.getInstance(RemoteDataSource.getInstance());

    }

}
