/*
 * Copyright 2009-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.freedom.gj.beans.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import net.freedom.gj.beans.mapper.MappingData;
import net.freedom.gj.beans.mapper.MappingInformation;
import net.freedom.gj.beans.mapper.PostProcessor;
import net.freedom.gj.beans.util.Lg;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Martin Jamszolik
 */
public class SimpleXmlMapperConfiguration extends AbstractMapperConfiguration {

   
    public MappingInformation getMappingInformation() {
        try {
            if (mappingInformation != null) {
                return mappingInformation;
            }
            mappingInformation = new MappingInformation();
            InputStream stream = this.getClass().getResourceAsStream(configurationFile);            
            
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            sp.parse(stream, new XmlDefaultHandler(mappingInformation));
            stream.close();
        } catch (Exception ex) {
            Lg.log(this, Lg.ERROR, "Error", ex);
        }
        return mappingInformation;
    }

    

    class XmlDefaultHandler extends DefaultHandler {

        private MappingInformation mappingInformation;

        private MappingData collectionData;
        private List<PostProcessor> procList;

        public XmlDefaultHandler(MappingInformation info) {
            mappingInformation = info;
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
           if( qName.equals("collection") ){
               mappingInformation.getMappingData().add(collectionData);
               collectionData = null;
           }else if( qName.equals("post-processors") ){
               mappingInformation.setPostProcessors(procList);
               procList = null;
           }
        }
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("bind")) {
                MappingData d = new MappingData();
                d.setSourceExpression(attributes.getValue("source"));
                d.setTargetExpression(attributes.getValue("target"));
                d.setConverter(getConverter( attributes.getValue("converter")  ));
                
                if( collectionData != null )
                    collectionData.getCollectionMappingData().add(d);
                else
                    mappingInformation.getMappingData().add(d);

            }else if( qName.equals("collection") ){
                collectionData = new MappingData();
                collectionData.setCollection(true);
                collectionData.setSourceExpression(attributes.getValue("source"));
                collectionData.setTargetExpression(attributes.getValue("target"));
                collectionData.setCollectionObjectType(attributes.getValue("type"));
                collectionData.setCollectionMappingData(new ArrayList<MappingData>());

            }else if ( qName.equals("post-processors") ){
                procList = new ArrayList<PostProcessor>();

            }else if( qName.equals("processor") ){
                PostProcessor proc = (PostProcessor)getInstance(attributes.getValue("value") );
                if( proc != null )
                procList.add( proc );
            }
        }

        private Object getInstance(String impl){
            try {
                return (Class.forName(impl)).newInstance();
            } catch (Exception ex) {
                Lg.log(Lg.ERROR,"Faild to create new Instance of {0}. Error: {1}",impl,ex.getMessage() );
                return null;
            }
        }
    }

    
}
