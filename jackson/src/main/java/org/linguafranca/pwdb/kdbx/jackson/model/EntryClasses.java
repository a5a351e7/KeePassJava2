/*
 * Copyright 2015 Giuseppe Valente
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.linguafranca.pwdb.kdbx.jackson.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.linguafranca.pwdb.kdbx.jackson.JacksonEntry;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;


public abstract class EntryClasses {
    public static StringProperty getStringProperty(String name, List<StringProperty> string) {
        for (StringProperty property : string) {
            if (property.key.equals(name)) {
                return property;
            }
        }
        return null;
    }

    public static String getStringContent(StringProperty property) {
        return property == null || property.value == null? null:property.value.text;
    }

    public static BinaryProperty getBinaryProp(String name, List<BinaryProperty> binary) {
        for (BinaryProperty property : binary) {
            if (property.key.equals(name)) {
                return property;
            }
        }
        return null;
    }

    public static String getBinaryContent(BinaryProperty property) {
        return property == null || property.value == null ? null :  property.value.ref;
    }

    @JacksonXmlRootElement(localName = "AutoType")
    public static class AutoType {
        @JacksonXmlProperty(localName = "Enabled")
        protected Boolean enabled;

        @JacksonXmlProperty(localName = "DataTransferObfuscation")
        protected int dataTransferObfuscation;

        @JacksonXmlProperty(localName = "DefaultSequence")
        protected String defaultSequence;

        @JacksonXmlProperty(localName = "Association")
        protected AutoType.Association association;

        public static class Association {

            @JacksonXmlProperty(localName = "Window")
            protected String window;
            @JacksonXmlProperty(localName = "KeystrokeSequence")
            protected String keystrokeSequence;

            protected List<Object> windowAndKeystrokeSequence;
        }
    }

    @JacksonXmlRootElement(localName = "String")
    public static class StringProperty  {

        public StringProperty() {
            this("",new Value());
        }
        public StringProperty(String key, Value value) {
            this.key = key;
            this.value = value;
        }

        @JacksonXmlProperty(localName = "Key")
        String key;

        @JacksonXmlProperty(localName = "Value")
        Value value;

        public String getName() {
            return key;
        }

        public String getKey() {
            return key;
        }

        public Value getValue() {
            return value;
        }

        @JacksonXmlRootElement(localName = "Value")
        public static class Value {
            public Value(){
                this("");
            }
            public Value(String text) {
                this.text = text;
                this._protected = false;
            }

            public Value(String text, Boolean _protected) {
                this._protected = _protected;
                this.text = text;
            }

            @JacksonXmlProperty(localName = "ProtectInMemory", isAttribute = true)
            protected Boolean protectInMemory;

            @JacksonXmlProperty(localName = "Protected", isAttribute = true)
            Boolean _protected;
            
            @JacksonXmlProperty(localName = "kpj2-ProtectOnOutput", isAttribute = true)
            Boolean protectOnOutput;

            @JacksonXmlText
            String text;

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public void setProtectOnOutput(boolean aProtected) {
                this.protectOnOutput = aProtected;
            }

            public boolean getProtectOnOutput() {
                return Objects.nonNull(this.protectOnOutput) && this.protectOnOutput;
            }


            public Boolean getProtected() {
                return _protected;
            }

            public void setProtected(Boolean _protected) {
                this._protected = _protected;
            }
        }
    }

    @JacksonXmlRootElement(localName = "Binary")
    public static class BinaryProperty  {

        @JacksonXmlProperty(localName = "Key")
        String key;

        @JacksonXmlProperty(localName = "Value")
        Value value;

        public String getKey() {
            return key;
        }

        public String getName() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setValue(Value value) {
            this.value = value;
        }

        @JacksonXmlRootElement(localName = "Value")
        public static class Value {
            @JacksonXmlProperty(localName = "Ref", isAttribute = true)
            String ref;

            public void setRef(String ref) {
                this.ref = ref;
            }
        }
    }

    public static class History {

        @JacksonXmlElementWrapper(localName = "JacksonEntry", useWrapping = false)
        private List<JacksonEntry> list;

        public History(){
            list = new ArrayList<>();
        }
        public List<JacksonEntry> getHistory(){
            return list;
        }
    }
}
