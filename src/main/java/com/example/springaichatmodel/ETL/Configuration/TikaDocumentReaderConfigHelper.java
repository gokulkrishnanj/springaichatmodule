package com.example.springaichatmodel.ETL.Configuration;

import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;

public class TikaDocumentReaderConfigHelper {

    private TikaDocumentReaderConfigHelper( ){
    }
    private static TikaDocumentReader tikaDocumentReader;

    public static TikaDocumentReader getTikaDocumentReaderInstance(Resource resource){
        if(tikaDocumentReader==null)
            tikaDocumentReader = new TikaDocumentReader(resource);
        return tikaDocumentReader;
    }
}
