package com.example.springaichatmodel.ETL.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.RecursiveParserWrapper;
import org.apache.tika.sax.BasicContentHandlerFactory;
import org.apache.tika.sax.RecursiveParserWrapperHandler;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.xml.sax.ContentHandler;
import org.springframework.ai.document.*;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomTikaDocumentReader extends TikaDocumentReader {
    public static final String METADATA_SOURCE = "source";
    private static final Logger log = LogManager.getLogger(CustomTikaDocumentReader.class);
    private final RecursiveParserWrapper parser;
    private final ContentHandler handler;
    private final ParseContext context;
    private final Resource resource;
    private final ExtractedTextFormatter textFormatter;

    public CustomTikaDocumentReader(String resourceUrl) {
        this(resourceUrl, ExtractedTextFormatter.defaults());
    }

    public CustomTikaDocumentReader(String resourceUrl, ExtractedTextFormatter textFormatter) {
        this(new DefaultResourceLoader().getResource(resourceUrl), textFormatter);
    }

    public CustomTikaDocumentReader(Resource resource) {
        this(resource, ExtractedTextFormatter.defaults());
    }
    public CustomTikaDocumentReader(Resource resource, ExtractedTextFormatter textFormatter) {
        this(resource, new RecursiveParserWrapperHandler(
                new BasicContentHandlerFactory(
                        BasicContentHandlerFactory.HANDLER_TYPE.TEXT,
                        -1
                )
        ), textFormatter);
    }
    public CustomTikaDocumentReader(Resource resource, ContentHandler contentHandler, ExtractedTextFormatter textFormatter) {
        super(resource,contentHandler,textFormatter);
        this.parser = new RecursiveParserWrapper(new AutoDetectParser());
        this.handler = contentHandler;
        this.context = new ParseContext();
        this.resource = resource;
        this.textFormatter = textFormatter;
    }

    @Override
    public List<Document> get() {
        log.info("get method called");
        try (InputStream stream = this.resource.getInputStream()) {
            Metadata metadata = new Metadata();
            this.parser.parse(stream, this.handler, metadata, this.context);

            RecursiveParserWrapperHandler wrapper =
                    (RecursiveParserWrapperHandler) this.handler;

            List<Metadata> metadataList = wrapper.getMetadataList();
            List<Document> docs = new ArrayList<>();

            for (Metadata md : metadataList) {

                String contentType = md.get("Content-Type");

                // Convert metadata → Map
                Map<String, Object> metaMap = new HashMap<>();
                for (String name : md.names()) {
                    metaMap.put(name, md.get(name));
                }

                // ---- Extract TEXT from metadata ----
                String text = md.get(TikaCoreProperties.TIKA_CONTENT);

                // ---- TEXT DOCUMENT ----
                if (text != null && !text.isBlank()) {

                    String cleaned = this.textFormatter.format(text);

                    docs.add(
                            Document.builder()
                                    .text(cleaned)
                                    .metadata(metaMap)
                                    .build()
                    );
                }

                // ---- IMAGE DOCUMENT ----
//                if (contentType != null && contentType.startsWith("image")) {
//
//                    String embedded = md.get("embedded:embeddedFile");
//
//                    if (embedded != null) {
//                        byte[] bytes = embedded.getBytes();
//
//                        docs.add(
//                                Document.builder()
//                                        .media(new Media(
//                                                Meddi.fromMimeType(contentType),
//                                                bytes))
//                                        .metadata(metaMap)
//                                        .build()
//                        );
//                    }
//                }
            }

            return docs;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
