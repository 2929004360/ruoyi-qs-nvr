package com.ruoyi.haikang.isup.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.transform.sax.SAXSource;
import java.io.StringReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class XmlParserUtils {
    private static final Map<Class<?>, JAXBContext> CONTEXT_CACHE = new ConcurrentHashMap<>();

    public static <T> T parseXmlToObject(String xml, Class<T> clazz) {
        if (xml == null || xml.isEmpty()) {
            log.warn("XmlUtils.fromXml: XML 字符串为空");
            return null;
        }
        try {
            JAXBContext context = CONTEXT_CACHE.computeIfAbsent(clazz, c -> {
                try {
                    return JAXBContext.newInstance(c);
                } catch (JAXBException e) {
                    throw new RuntimeException(e);
                }
            });
            Unmarshaller unmarshaller = context.createUnmarshaller();

            XMLReader xmlReader = XMLReaderFactory.createXMLReader();
            XMLFilterImpl nsFilter = new XMLFilterImpl(xmlReader) {
                @Override
                public void startElement(String uri, String localName, String qName, Attributes atts)
                        throws org.xml.sax.SAXException {
                    super.startElement("", localName, qName, atts);
                }

                @Override
                public void endElement(String uri, String localName, String qName)
                        throws org.xml.sax.SAXException {
                    super.endElement("", localName, qName);
                }
            };

            InputSource inputSource = new InputSource(new StringReader(xml));
            SAXSource saxSource = new SAXSource(nsFilter, inputSource);

            @SuppressWarnings("unchecked")
            T result = (T) unmarshaller.unmarshal(saxSource);
            return result;

        } catch (Exception e) {
            log.error("解析XML失败: {}, 异常: {}", xml, e.getMessage(), e);
            return null;
        }
    }
}
