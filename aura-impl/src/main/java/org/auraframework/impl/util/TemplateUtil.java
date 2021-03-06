/*
 * Copyright (C) 2013 salesforce.com, inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.auraframework.impl.util;

import java.io.IOException;
import java.util.List;

public class TemplateUtil {

    private static final String HTML_STYLE = "        <link href=\"%s\" rel=\"stylesheet\" type=\"text/css\"/>\n";
    private static final String HTML_SCRIPT = "       <script src=\"%s\" ></script>\n";
    private static final String HTML_LAZY_SCRIPT = "       <script data-src=\"%s\" ></script>\n";

    public void writeHtmlStyles(List<String> styles, Appendable out) throws IOException {
        if (styles != null) {
            for (String style : styles) {
                out.append(String.format(HTML_STYLE, style));
            }
        }
    }

    public void writeHtmlScripts(List<String> scripts, Appendable out) throws IOException {
    	writeHtmlScripts(scripts, false, out);
    }

    public void writeHtmlScripts(List<String> scripts, boolean lazy, Appendable out) throws IOException {
        if (scripts != null) {
            for (String script : scripts) {
                out.append(String.format(lazy ? HTML_LAZY_SCRIPT : HTML_SCRIPT, script));
            }
        }
    }
}
