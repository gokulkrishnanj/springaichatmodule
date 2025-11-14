package com.example.springaichatmodel.ETL.Configuration;

import org.apache.tika.sax.BasicContentHandlerFactory;
import org.apache.tika.sax.RecursiveParserWrapperHandler;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;

public class TikaDocumentReaderConfigHelper {

    private TikaDocumentReaderConfigHelper( ){
    }
    private static TikaDocumentReader tikaDocumentReader;

    public static TikaDocumentReader getTikaDocumentReaderInstance(Resource resource){
        if(tikaDocumentReader==null)
            tikaDocumentReader = new TikaDocumentReader(resource, new RecursiveParserWrapperHandler(new BasicContentHandlerFactory(BasicContentHandlerFactory.HANDLER_TYPE.TEXT,-1)), new ExtractedTextFormatter.Builder().build());
        return tikaDocumentReader;
    }
}
