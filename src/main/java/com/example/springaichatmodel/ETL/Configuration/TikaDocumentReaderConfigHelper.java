package com.example.springaichatmodel.ETL.Configuration;

import org.apache.tika.sax.BasicContentHandlerFactory;
import org.apache.tika.sax.RecursiveParserWrapperHandler;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.Resource;
import org.xml.sax.ContentHandler;

public class TikaDocumentReaderConfigHelper {

    private TikaDocumentReaderConfigHelper( ){
    }

    public static TikaDocumentReader getTikaDocumentReaderInstance(Resource resource){
        ContentHandler handler = new RecursiveParserWrapperHandler(
                new BasicContentHandlerFactory(
                        BasicContentHandlerFactory.HANDLER_TYPE.TEXT, -1
                )
        );
        ExtractedTextFormatter formatter = new ExtractedTextFormatter.Builder().build();
        return new CustomTikaDocumentReader(resource, handler, formatter);
    }
}
