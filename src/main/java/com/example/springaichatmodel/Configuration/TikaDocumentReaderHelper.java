package com.example.springaichatmodel.Configuration;

import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;

public class TikaDocumentReaderHelper {

    private TikaDocumentReaderHelper( ){
    }
    private static TikaDocumentReader tikaDocumentReader;

    public static TikaDocumentReader getTikaDocumentReaderInstance(Resource resource){
        if(tikaDocumentReader==null)
            tikaDocumentReader = new TikaDocumentReader(resource);
        return tikaDocumentReader;
    }
}
