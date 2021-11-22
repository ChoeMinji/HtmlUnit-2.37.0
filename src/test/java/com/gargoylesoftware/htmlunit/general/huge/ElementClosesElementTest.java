/*
 * Copyright (c) 2002-2020 Gargoyle Software Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gargoylesoftware.htmlunit.general.huge;

import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.CHROME;
import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.FF;
import static com.gargoylesoftware.htmlunit.BrowserRunner.TestedBrowser.IE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;

import com.gargoylesoftware.htmlunit.BrowserParameterizedRunner;
import com.gargoylesoftware.htmlunit.BrowserParameterizedRunner.Default;
import com.gargoylesoftware.htmlunit.BrowserRunner.Alerts;
import com.gargoylesoftware.htmlunit.BrowserRunner.NotYetImplemented;
import com.gargoylesoftware.htmlunit.WebDriverTestCase;
import com.gargoylesoftware.htmlunit.html.HtmlPageTest;

/**
 * Tests for an element to close another element, which is defined in
 * {@link net.sourceforge.htmlunit.cyberneko.HTMLElements}.
 *
 * @author Ahmed Ashour
 * @author Ronald Brill
 */
@RunWith(BrowserParameterizedRunner.class)
public class ElementClosesElementTest extends WebDriverTestCase {

    private static int ServerRestartCount_ = 0;

    /**
     * Returns the parameterized data.
     * @return the parameterized data
     * @throws Exception if an error occurs
     */
    @Parameters
    public static Collection<Object[]> data() throws Exception {
        final List<Object[]> list = new ArrayList<>();
        final List<String> strings = new ArrayList<>(HtmlPageTest.HTML_TAGS_);
        for (final String parent : strings) {
            for (final String child : strings) {
                list.add(new Object[] {parent, child});
            }
        }
        return list;
    }

    private void test(final String parent, final String child) throws Exception {
        String parentString = "<" + parent + " id='outer'>";
        String suffix = "\n";
        if ("script".equals(parent)) {
            parentString += "//";
            suffix = "\n</script>\n";
        }
        if ("svg".equals(parent)) {
            suffix = "";
        }
        String childString = "<" + child + ">";
        if ("script".equals(child)) {
            childString += "</" + child + ">";
        }

        final String html = "<html>\n"
                + "<head>\n"
                + "<title>-</title>\n"
                + "<script>\n"
                + "function test() {\n"
                + "  var e = document.getElementById('outer');\n"
                + "  document.title = e == null ? e : e.childNodes.length;\n"
                + "}\n"
                + "</script>\n"
                + "</head>\n"
                + "<body onload='test()'>\n"
                + parentString + childString + suffix
                + "</body></html>";

        ServerRestartCount_++;
        if (ServerRestartCount_ == 200) {
            stopWebServers();
            ServerRestartCount_ = 0;
        }
        final WebDriver driver = loadPage2(html);
        assertTitle(driver, getExpectedAlerts()[0]);
    }

    /**
     * The parent element name.
     */
    @Parameter
    public String parent_;

    /**
     * The child element name.
     */
    @Parameter(1)
    public String child_;

    /**
     * Cleanup.
     */
    @After
    public void after() {
        parent_ = null;
        child_ = null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean isWebClientCached() {
        return true;
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _a_a() throws Exception {
        test("a", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_area() throws Exception {
        test("a", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_base() throws Exception {
        test("a", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_basefont() throws Exception {
        test("a", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_bgsound() throws Exception {
        test("a", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_br() throws Exception {
        test("a", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _a_command() throws Exception {
        test("a", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_embed() throws Exception {
        test("a", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_hr() throws Exception {
        test("a", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_image() throws Exception {
        test("a", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_img() throws Exception {
        test("a", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_input() throws Exception {
        test("a", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _a_isindex() throws Exception {
        test("a", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_keygen() throws Exception {
        test("a", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_link() throws Exception {
        test("a", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_meta() throws Exception {
        test("a", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_param() throws Exception {
        test("a", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_script() throws Exception {
        test("a", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_source() throws Exception {
        test("a", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_track() throws Exception {
        test("a", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _a_wbr() throws Exception {
        test("a", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_area() throws Exception {
        test("abbr", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_base() throws Exception {
        test("abbr", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_basefont() throws Exception {
        test("abbr", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_bgsound() throws Exception {
        test("abbr", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_br() throws Exception {
        test("abbr", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _abbr_command() throws Exception {
        test("abbr", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_embed() throws Exception {
        test("abbr", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_hr() throws Exception {
        test("abbr", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_image() throws Exception {
        test("abbr", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_img() throws Exception {
        test("abbr", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_input() throws Exception {
        test("abbr", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _abbr_isindex() throws Exception {
        test("abbr", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_keygen() throws Exception {
        test("abbr", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_link() throws Exception {
        test("abbr", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_meta() throws Exception {
        test("abbr", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_param() throws Exception {
        test("abbr", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_script() throws Exception {
        test("abbr", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_source() throws Exception {
        test("abbr", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_track() throws Exception {
        test("abbr", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _abbr_wbr() throws Exception {
        test("abbr", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_area() throws Exception {
        test("acronym", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_base() throws Exception {
        test("acronym", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_basefont() throws Exception {
        test("acronym", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_bgsound() throws Exception {
        test("acronym", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_br() throws Exception {
        test("acronym", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _acronym_command() throws Exception {
        test("acronym", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_embed() throws Exception {
        test("acronym", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_hr() throws Exception {
        test("acronym", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_image() throws Exception {
        test("acronym", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_img() throws Exception {
        test("acronym", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_input() throws Exception {
        test("acronym", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _acronym_isindex() throws Exception {
        test("acronym", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_keygen() throws Exception {
        test("acronym", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_link() throws Exception {
        test("acronym", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_meta() throws Exception {
        test("acronym", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_param() throws Exception {
        test("acronym", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_script() throws Exception {
        test("acronym", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_source() throws Exception {
        test("acronym", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_track() throws Exception {
        test("acronym", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _acronym_wbr() throws Exception {
        test("acronym", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_area() throws Exception {
        test("address", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_base() throws Exception {
        test("address", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_basefont() throws Exception {
        test("address", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_bgsound() throws Exception {
        test("address", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_br() throws Exception {
        test("address", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _address_command() throws Exception {
        test("address", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_embed() throws Exception {
        test("address", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_hr() throws Exception {
        test("address", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_image() throws Exception {
        test("address", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_img() throws Exception {
        test("address", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_input() throws Exception {
        test("address", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _address_isindex() throws Exception {
        test("address", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_keygen() throws Exception {
        test("address", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_link() throws Exception {
        test("address", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_meta() throws Exception {
        test("address", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_param() throws Exception {
        test("address", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_script() throws Exception {
        test("address", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_source() throws Exception {
        test("address", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_track() throws Exception {
        test("address", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _address_wbr() throws Exception {
        test("address", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_area() throws Exception {
        test("applet", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_base() throws Exception {
        test("applet", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_basefont() throws Exception {
        test("applet", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_bgsound() throws Exception {
        test("applet", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_br() throws Exception {
        test("applet", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _applet_command() throws Exception {
        test("applet", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_embed() throws Exception {
        test("applet", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_hr() throws Exception {
        test("applet", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_image() throws Exception {
        test("applet", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_img() throws Exception {
        test("applet", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_input() throws Exception {
        test("applet", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _applet_isindex() throws Exception {
        test("applet", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_keygen() throws Exception {
        test("applet", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_link() throws Exception {
        test("applet", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_meta() throws Exception {
        test("applet", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_param() throws Exception {
        test("applet", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_script() throws Exception {
        test("applet", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_source() throws Exception {
        test("applet", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_track() throws Exception {
        test("applet", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _applet_wbr() throws Exception {
        test("applet", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_a() throws Exception {
        test("area", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_abbr() throws Exception {
        test("area", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_acronym() throws Exception {
        test("area", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_address() throws Exception {
        test("area", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_applet() throws Exception {
        test("area", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_area() throws Exception {
        test("area", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_article() throws Exception {
        test("area", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_aside() throws Exception {
        test("area", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_audio() throws Exception {
        test("area", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_b() throws Exception {
        test("area", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_base() throws Exception {
        test("area", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_basefont() throws Exception {
        test("area", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_bdi() throws Exception {
        test("area", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_bdo() throws Exception {
        test("area", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_bgsound() throws Exception {
        test("area", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_big() throws Exception {
        test("area", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_blink() throws Exception {
        test("area", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_blockquote() throws Exception {
        test("area", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_body() throws Exception {
        test("area", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_br() throws Exception {
        test("area", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_button() throws Exception {
        test("area", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_canvas() throws Exception {
        test("area", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_caption() throws Exception {
        test("area", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_center() throws Exception {
        test("area", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_cite() throws Exception {
        test("area", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_code() throws Exception {
        test("area", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_col() throws Exception {
        test("area", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_colgroup() throws Exception {
        test("area", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_command() throws Exception {
        test("area", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_content() throws Exception {
        test("area", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_data() throws Exception {
        test("area", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_datalist() throws Exception {
        test("area", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_dd() throws Exception {
        test("area", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_del() throws Exception {
        test("area", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_details() throws Exception {
        test("area", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_dfn() throws Exception {
        test("area", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_dialog() throws Exception {
        test("area", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_dir() throws Exception {
        test("area", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_div() throws Exception {
        test("area", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_dl() throws Exception {
        test("area", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_dt() throws Exception {
        test("area", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_em() throws Exception {
        test("area", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_embed() throws Exception {
        test("area", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_fieldset() throws Exception {
        test("area", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_figcaption() throws Exception {
        test("area", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_figure() throws Exception {
        test("area", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_font() throws Exception {
        test("area", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_footer() throws Exception {
        test("area", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_form() throws Exception {
        test("area", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_frame() throws Exception {
        test("area", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_frameset() throws Exception {
        test("area", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_h1() throws Exception {
        test("area", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_h2() throws Exception {
        test("area", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_h3() throws Exception {
        test("area", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_h4() throws Exception {
        test("area", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_h5() throws Exception {
        test("area", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_h6() throws Exception {
        test("area", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_head() throws Exception {
        test("area", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_header() throws Exception {
        test("area", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_hr() throws Exception {
        test("area", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_html() throws Exception {
        test("area", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_i() throws Exception {
        test("area", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_iframe() throws Exception {
        test("area", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_image() throws Exception {
        test("area", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_img() throws Exception {
        test("area", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_input() throws Exception {
        test("area", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_ins() throws Exception {
        test("area", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_isindex() throws Exception {
        test("area", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_kbd() throws Exception {
        test("area", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_keygen() throws Exception {
        test("area", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_label() throws Exception {
        test("area", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_layer() throws Exception {
        test("area", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_legend() throws Exception {
        test("area", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_li() throws Exception {
        test("area", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_link() throws Exception {
        test("area", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_listing() throws Exception {
        test("area", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_main() throws Exception {
        test("area", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_map() throws Exception {
        test("area", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_mark() throws Exception {
        test("area", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_marquee() throws Exception {
        test("area", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_menu() throws Exception {
        test("area", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_menuitem() throws Exception {
        test("area", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_meta() throws Exception {
        test("area", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_meter() throws Exception {
        test("area", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_multicol() throws Exception {
        test("area", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_nav() throws Exception {
        test("area", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_nextid() throws Exception {
        test("area", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_nobr() throws Exception {
        test("area", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_noembed() throws Exception {
        test("area", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_noframes() throws Exception {
        test("area", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_nolayer() throws Exception {
        test("area", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_noscript() throws Exception {
        test("area", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_object() throws Exception {
        test("area", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_ol() throws Exception {
        test("area", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_optgroup() throws Exception {
        test("area", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_option() throws Exception {
        test("area", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_output() throws Exception {
        test("area", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_p() throws Exception {
        test("area", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_param() throws Exception {
        test("area", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_picture() throws Exception {
        test("area", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_plaintext() throws Exception {
        test("area", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_pre() throws Exception {
        test("area", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_progress() throws Exception {
        test("area", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_q() throws Exception {
        test("area", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_rp() throws Exception {
        test("area", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_rt() throws Exception {
        test("area", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_ruby() throws Exception {
        test("area", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_s() throws Exception {
        test("area", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_samp() throws Exception {
        test("area", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_script() throws Exception {
        test("area", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_section() throws Exception {
        test("area", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_select() throws Exception {
        test("area", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_slot() throws Exception {
        test("area", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_small() throws Exception {
        test("area", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_source() throws Exception {
        test("area", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_span() throws Exception {
        test("area", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_strike() throws Exception {
        test("area", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_strong() throws Exception {
        test("area", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_style() throws Exception {
        test("area", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_sub() throws Exception {
        test("area", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_summary() throws Exception {
        test("area", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_sup() throws Exception {
        test("area", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_svg() throws Exception {
        test("area", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_table() throws Exception {
        test("area", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_tbody() throws Exception {
        test("area", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_td() throws Exception {
        test("area", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_template() throws Exception {
        test("area", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_textarea() throws Exception {
        test("area", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_tfoot() throws Exception {
        test("area", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_th() throws Exception {
        test("area", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_thead() throws Exception {
        test("area", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_time() throws Exception {
        test("area", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_title() throws Exception {
        test("area", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_tr() throws Exception {
        test("area", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_track() throws Exception {
        test("area", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_tt() throws Exception {
        test("area", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_u() throws Exception {
        test("area", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_ul() throws Exception {
        test("area", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_var() throws Exception {
        test("area", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_video() throws Exception {
        test("area", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_wbr() throws Exception {
        test("area", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _area_xmp() throws Exception {
        test("area", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_area() throws Exception {
        test("article", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_base() throws Exception {
        test("article", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_basefont() throws Exception {
        test("article", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_bgsound() throws Exception {
        test("article", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_br() throws Exception {
        test("article", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _article_command() throws Exception {
        test("article", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_embed() throws Exception {
        test("article", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_hr() throws Exception {
        test("article", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_image() throws Exception {
        test("article", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_img() throws Exception {
        test("article", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_input() throws Exception {
        test("article", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _article_isindex() throws Exception {
        test("article", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_keygen() throws Exception {
        test("article", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_link() throws Exception {
        test("article", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_meta() throws Exception {
        test("article", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_param() throws Exception {
        test("article", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_script() throws Exception {
        test("article", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_source() throws Exception {
        test("article", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_track() throws Exception {
        test("article", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _article_wbr() throws Exception {
        test("article", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_area() throws Exception {
        test("aside", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_base() throws Exception {
        test("aside", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_basefont() throws Exception {
        test("aside", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_bgsound() throws Exception {
        test("aside", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_br() throws Exception {
        test("aside", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _aside_command() throws Exception {
        test("aside", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_embed() throws Exception {
        test("aside", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_hr() throws Exception {
        test("aside", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_image() throws Exception {
        test("aside", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_img() throws Exception {
        test("aside", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_input() throws Exception {
        test("aside", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _aside_isindex() throws Exception {
        test("aside", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_keygen() throws Exception {
        test("aside", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_link() throws Exception {
        test("aside", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_meta() throws Exception {
        test("aside", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_param() throws Exception {
        test("aside", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_script() throws Exception {
        test("aside", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_source() throws Exception {
        test("aside", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_track() throws Exception {
        test("aside", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _aside_wbr() throws Exception {
        test("aside", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_area() throws Exception {
        test("audio", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_base() throws Exception {
        test("audio", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_basefont() throws Exception {
        test("audio", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_bgsound() throws Exception {
        test("audio", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_br() throws Exception {
        test("audio", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _audio_command() throws Exception {
        test("audio", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_embed() throws Exception {
        test("audio", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_hr() throws Exception {
        test("audio", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_image() throws Exception {
        test("audio", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_img() throws Exception {
        test("audio", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_input() throws Exception {
        test("audio", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _audio_isindex() throws Exception {
        test("audio", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_keygen() throws Exception {
        test("audio", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_link() throws Exception {
        test("audio", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_meta() throws Exception {
        test("audio", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_param() throws Exception {
        test("audio", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_script() throws Exception {
        test("audio", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_source() throws Exception {
        test("audio", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_track() throws Exception {
        test("audio", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _audio_wbr() throws Exception {
        test("audio", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_area() throws Exception {
        test("b", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_base() throws Exception {
        test("b", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_basefont() throws Exception {
        test("b", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_bgsound() throws Exception {
        test("b", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_br() throws Exception {
        test("b", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _b_command() throws Exception {
        test("b", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_embed() throws Exception {
        test("b", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_hr() throws Exception {
        test("b", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_image() throws Exception {
        test("b", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_img() throws Exception {
        test("b", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_input() throws Exception {
        test("b", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _b_isindex() throws Exception {
        test("b", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_keygen() throws Exception {
        test("b", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_link() throws Exception {
        test("b", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_meta() throws Exception {
        test("b", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_param() throws Exception {
        test("b", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_script() throws Exception {
        test("b", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_source() throws Exception {
        test("b", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_track() throws Exception {
        test("b", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _b_wbr() throws Exception {
        test("b", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_a() throws Exception {
        test("base", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_abbr() throws Exception {
        test("base", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_acronym() throws Exception {
        test("base", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_address() throws Exception {
        test("base", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_applet() throws Exception {
        test("base", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_area() throws Exception {
        test("base", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_article() throws Exception {
        test("base", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_aside() throws Exception {
        test("base", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_audio() throws Exception {
        test("base", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_b() throws Exception {
        test("base", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_base() throws Exception {
        test("base", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_basefont() throws Exception {
        test("base", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_bdi() throws Exception {
        test("base", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_bdo() throws Exception {
        test("base", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_bgsound() throws Exception {
        test("base", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_big() throws Exception {
        test("base", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_blink() throws Exception {
        test("base", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_blockquote() throws Exception {
        test("base", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_body() throws Exception {
        test("base", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_br() throws Exception {
        test("base", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_button() throws Exception {
        test("base", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_canvas() throws Exception {
        test("base", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_caption() throws Exception {
        test("base", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_center() throws Exception {
        test("base", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_cite() throws Exception {
        test("base", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_code() throws Exception {
        test("base", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_col() throws Exception {
        test("base", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_colgroup() throws Exception {
        test("base", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_command() throws Exception {
        test("base", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_content() throws Exception {
        test("base", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_data() throws Exception {
        test("base", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_datalist() throws Exception {
        test("base", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_dd() throws Exception {
        test("base", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_del() throws Exception {
        test("base", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_details() throws Exception {
        test("base", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_dfn() throws Exception {
        test("base", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_dialog() throws Exception {
        test("base", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_dir() throws Exception {
        test("base", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_div() throws Exception {
        test("base", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_dl() throws Exception {
        test("base", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_dt() throws Exception {
        test("base", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_em() throws Exception {
        test("base", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_embed() throws Exception {
        test("base", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_fieldset() throws Exception {
        test("base", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_figcaption() throws Exception {
        test("base", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_figure() throws Exception {
        test("base", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_font() throws Exception {
        test("base", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_footer() throws Exception {
        test("base", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_form() throws Exception {
        test("base", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_frame() throws Exception {
        test("base", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_frameset() throws Exception {
        test("base", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_h1() throws Exception {
        test("base", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_h2() throws Exception {
        test("base", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_h3() throws Exception {
        test("base", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_h4() throws Exception {
        test("base", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_h5() throws Exception {
        test("base", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_h6() throws Exception {
        test("base", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_head() throws Exception {
        test("base", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_header() throws Exception {
        test("base", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_hr() throws Exception {
        test("base", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_html() throws Exception {
        test("base", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_i() throws Exception {
        test("base", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_iframe() throws Exception {
        test("base", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_image() throws Exception {
        test("base", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_img() throws Exception {
        test("base", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_input() throws Exception {
        test("base", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_ins() throws Exception {
        test("base", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_isindex() throws Exception {
        test("base", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_kbd() throws Exception {
        test("base", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_keygen() throws Exception {
        test("base", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_label() throws Exception {
        test("base", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_layer() throws Exception {
        test("base", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_legend() throws Exception {
        test("base", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_li() throws Exception {
        test("base", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_link() throws Exception {
        test("base", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_listing() throws Exception {
        test("base", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_main() throws Exception {
        test("base", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_map() throws Exception {
        test("base", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_mark() throws Exception {
        test("base", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_marquee() throws Exception {
        test("base", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_menu() throws Exception {
        test("base", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_menuitem() throws Exception {
        test("base", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_meta() throws Exception {
        test("base", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_meter() throws Exception {
        test("base", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_multicol() throws Exception {
        test("base", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_nav() throws Exception {
        test("base", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_nextid() throws Exception {
        test("base", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_nobr() throws Exception {
        test("base", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_noembed() throws Exception {
        test("base", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_noframes() throws Exception {
        test("base", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_nolayer() throws Exception {
        test("base", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_noscript() throws Exception {
        test("base", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_object() throws Exception {
        test("base", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_ol() throws Exception {
        test("base", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_optgroup() throws Exception {
        test("base", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_option() throws Exception {
        test("base", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_output() throws Exception {
        test("base", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_p() throws Exception {
        test("base", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_param() throws Exception {
        test("base", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_picture() throws Exception {
        test("base", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_plaintext() throws Exception {
        test("base", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_pre() throws Exception {
        test("base", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_progress() throws Exception {
        test("base", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_q() throws Exception {
        test("base", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_rp() throws Exception {
        test("base", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_rt() throws Exception {
        test("base", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_ruby() throws Exception {
        test("base", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_s() throws Exception {
        test("base", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_samp() throws Exception {
        test("base", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_script() throws Exception {
        test("base", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_section() throws Exception {
        test("base", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_select() throws Exception {
        test("base", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_slot() throws Exception {
        test("base", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_small() throws Exception {
        test("base", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_source() throws Exception {
        test("base", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_span() throws Exception {
        test("base", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_strike() throws Exception {
        test("base", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_strong() throws Exception {
        test("base", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_style() throws Exception {
        test("base", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_sub() throws Exception {
        test("base", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_summary() throws Exception {
        test("base", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_sup() throws Exception {
        test("base", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_svg() throws Exception {
        test("base", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_table() throws Exception {
        test("base", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_tbody() throws Exception {
        test("base", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_td() throws Exception {
        test("base", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_template() throws Exception {
        test("base", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_textarea() throws Exception {
        test("base", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_tfoot() throws Exception {
        test("base", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_th() throws Exception {
        test("base", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_thead() throws Exception {
        test("base", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_time() throws Exception {
        test("base", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_title() throws Exception {
        test("base", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_tr() throws Exception {
        test("base", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_track() throws Exception {
        test("base", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_tt() throws Exception {
        test("base", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_u() throws Exception {
        test("base", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_ul() throws Exception {
        test("base", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_var() throws Exception {
        test("base", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_video() throws Exception {
        test("base", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_wbr() throws Exception {
        test("base", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _base_xmp() throws Exception {
        test("base", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_a() throws Exception {
        test("basefont", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_abbr() throws Exception {
        test("basefont", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_acronym() throws Exception {
        test("basefont", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_address() throws Exception {
        test("basefont", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_applet() throws Exception {
        test("basefont", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_area() throws Exception {
        test("basefont", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_article() throws Exception {
        test("basefont", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_aside() throws Exception {
        test("basefont", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_audio() throws Exception {
        test("basefont", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_b() throws Exception {
        test("basefont", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_base() throws Exception {
        test("basefont", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_basefont() throws Exception {
        test("basefont", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_bdi() throws Exception {
        test("basefont", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_bdo() throws Exception {
        test("basefont", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_bgsound() throws Exception {
        test("basefont", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_big() throws Exception {
        test("basefont", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_blink() throws Exception {
        test("basefont", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_blockquote() throws Exception {
        test("basefont", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_body() throws Exception {
        test("basefont", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_br() throws Exception {
        test("basefont", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_button() throws Exception {
        test("basefont", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_canvas() throws Exception {
        test("basefont", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_caption() throws Exception {
        test("basefont", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_center() throws Exception {
        test("basefont", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_cite() throws Exception {
        test("basefont", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_code() throws Exception {
        test("basefont", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_col() throws Exception {
        test("basefont", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_colgroup() throws Exception {
        test("basefont", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_command() throws Exception {
        test("basefont", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_content() throws Exception {
        test("basefont", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_data() throws Exception {
        test("basefont", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_datalist() throws Exception {
        test("basefont", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_dd() throws Exception {
        test("basefont", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_del() throws Exception {
        test("basefont", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_details() throws Exception {
        test("basefont", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_dfn() throws Exception {
        test("basefont", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_dialog() throws Exception {
        test("basefont", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_dir() throws Exception {
        test("basefont", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_div() throws Exception {
        test("basefont", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_dl() throws Exception {
        test("basefont", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_dt() throws Exception {
        test("basefont", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_em() throws Exception {
        test("basefont", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_embed() throws Exception {
        test("basefont", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_fieldset() throws Exception {
        test("basefont", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_figcaption() throws Exception {
        test("basefont", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_figure() throws Exception {
        test("basefont", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_font() throws Exception {
        test("basefont", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_footer() throws Exception {
        test("basefont", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_form() throws Exception {
        test("basefont", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_frame() throws Exception {
        test("basefont", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_frameset() throws Exception {
        test("basefont", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_h1() throws Exception {
        test("basefont", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_h2() throws Exception {
        test("basefont", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_h3() throws Exception {
        test("basefont", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_h4() throws Exception {
        test("basefont", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_h5() throws Exception {
        test("basefont", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_h6() throws Exception {
        test("basefont", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_head() throws Exception {
        test("basefont", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_header() throws Exception {
        test("basefont", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_hr() throws Exception {
        test("basefont", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_html() throws Exception {
        test("basefont", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_i() throws Exception {
        test("basefont", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_iframe() throws Exception {
        test("basefont", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_image() throws Exception {
        test("basefont", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_img() throws Exception {
        test("basefont", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_input() throws Exception {
        test("basefont", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_ins() throws Exception {
        test("basefont", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_isindex() throws Exception {
        test("basefont", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_kbd() throws Exception {
        test("basefont", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_keygen() throws Exception {
        test("basefont", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_label() throws Exception {
        test("basefont", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_layer() throws Exception {
        test("basefont", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_legend() throws Exception {
        test("basefont", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_li() throws Exception {
        test("basefont", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_link() throws Exception {
        test("basefont", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_listing() throws Exception {
        test("basefont", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_main() throws Exception {
        test("basefont", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_map() throws Exception {
        test("basefont", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_mark() throws Exception {
        test("basefont", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_marquee() throws Exception {
        test("basefont", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_menu() throws Exception {
        test("basefont", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_menuitem() throws Exception {
        test("basefont", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_meta() throws Exception {
        test("basefont", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_meter() throws Exception {
        test("basefont", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_multicol() throws Exception {
        test("basefont", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_nav() throws Exception {
        test("basefont", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_nextid() throws Exception {
        test("basefont", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_nobr() throws Exception {
        test("basefont", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_noembed() throws Exception {
        test("basefont", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_noframes() throws Exception {
        test("basefont", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_nolayer() throws Exception {
        test("basefont", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_noscript() throws Exception {
        test("basefont", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_object() throws Exception {
        test("basefont", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_ol() throws Exception {
        test("basefont", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_optgroup() throws Exception {
        test("basefont", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_option() throws Exception {
        test("basefont", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_output() throws Exception {
        test("basefont", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_p() throws Exception {
        test("basefont", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_param() throws Exception {
        test("basefont", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_picture() throws Exception {
        test("basefont", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_plaintext() throws Exception {
        test("basefont", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_pre() throws Exception {
        test("basefont", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_progress() throws Exception {
        test("basefont", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_q() throws Exception {
        test("basefont", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_rp() throws Exception {
        test("basefont", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_rt() throws Exception {
        test("basefont", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_ruby() throws Exception {
        test("basefont", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_s() throws Exception {
        test("basefont", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_samp() throws Exception {
        test("basefont", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_script() throws Exception {
        test("basefont", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_section() throws Exception {
        test("basefont", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_select() throws Exception {
        test("basefont", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_slot() throws Exception {
        test("basefont", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_small() throws Exception {
        test("basefont", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_source() throws Exception {
        test("basefont", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_span() throws Exception {
        test("basefont", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_strike() throws Exception {
        test("basefont", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_strong() throws Exception {
        test("basefont", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_style() throws Exception {
        test("basefont", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_sub() throws Exception {
        test("basefont", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_summary() throws Exception {
        test("basefont", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_sup() throws Exception {
        test("basefont", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_svg() throws Exception {
        test("basefont", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_table() throws Exception {
        test("basefont", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_tbody() throws Exception {
        test("basefont", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_td() throws Exception {
        test("basefont", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_template() throws Exception {
        test("basefont", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_textarea() throws Exception {
        test("basefont", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_tfoot() throws Exception {
        test("basefont", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_th() throws Exception {
        test("basefont", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_thead() throws Exception {
        test("basefont", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_time() throws Exception {
        test("basefont", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_title() throws Exception {
        test("basefont", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_tr() throws Exception {
        test("basefont", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_track() throws Exception {
        test("basefont", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_tt() throws Exception {
        test("basefont", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_u() throws Exception {
        test("basefont", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_ul() throws Exception {
        test("basefont", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_var() throws Exception {
        test("basefont", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_video() throws Exception {
        test("basefont", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_wbr() throws Exception {
        test("basefont", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _basefont_xmp() throws Exception {
        test("basefont", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_area() throws Exception {
        test("bdi", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_base() throws Exception {
        test("bdi", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_basefont() throws Exception {
        test("bdi", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_bgsound() throws Exception {
        test("bdi", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_br() throws Exception {
        test("bdi", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _bdi_command() throws Exception {
        test("bdi", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_embed() throws Exception {
        test("bdi", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_hr() throws Exception {
        test("bdi", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_image() throws Exception {
        test("bdi", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_img() throws Exception {
        test("bdi", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_input() throws Exception {
        test("bdi", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _bdi_isindex() throws Exception {
        test("bdi", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_keygen() throws Exception {
        test("bdi", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_link() throws Exception {
        test("bdi", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_meta() throws Exception {
        test("bdi", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_param() throws Exception {
        test("bdi", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_script() throws Exception {
        test("bdi", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_source() throws Exception {
        test("bdi", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_track() throws Exception {
        test("bdi", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdi_wbr() throws Exception {
        test("bdi", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_area() throws Exception {
        test("bdo", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_base() throws Exception {
        test("bdo", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_basefont() throws Exception {
        test("bdo", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_bgsound() throws Exception {
        test("bdo", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_br() throws Exception {
        test("bdo", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _bdo_command() throws Exception {
        test("bdo", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_embed() throws Exception {
        test("bdo", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_hr() throws Exception {
        test("bdo", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_image() throws Exception {
        test("bdo", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_img() throws Exception {
        test("bdo", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_input() throws Exception {
        test("bdo", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _bdo_isindex() throws Exception {
        test("bdo", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_keygen() throws Exception {
        test("bdo", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_link() throws Exception {
        test("bdo", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_meta() throws Exception {
        test("bdo", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_param() throws Exception {
        test("bdo", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_script() throws Exception {
        test("bdo", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_source() throws Exception {
        test("bdo", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_track() throws Exception {
        test("bdo", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _bdo_wbr() throws Exception {
        test("bdo", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_a() throws Exception {
        test("bgsound", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_abbr() throws Exception {
        test("bgsound", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_acronym() throws Exception {
        test("bgsound", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_address() throws Exception {
        test("bgsound", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_applet() throws Exception {
        test("bgsound", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_area() throws Exception {
        test("bgsound", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_article() throws Exception {
        test("bgsound", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_aside() throws Exception {
        test("bgsound", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_audio() throws Exception {
        test("bgsound", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_b() throws Exception {
        test("bgsound", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_base() throws Exception {
        test("bgsound", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_basefont() throws Exception {
        test("bgsound", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_bdi() throws Exception {
        test("bgsound", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_bdo() throws Exception {
        test("bgsound", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_bgsound() throws Exception {
        test("bgsound", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_big() throws Exception {
        test("bgsound", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_blink() throws Exception {
        test("bgsound", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_blockquote() throws Exception {
        test("bgsound", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_body() throws Exception {
        test("bgsound", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_br() throws Exception {
        test("bgsound", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_button() throws Exception {
        test("bgsound", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_canvas() throws Exception {
        test("bgsound", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_caption() throws Exception {
        test("bgsound", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_center() throws Exception {
        test("bgsound", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_cite() throws Exception {
        test("bgsound", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_code() throws Exception {
        test("bgsound", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_col() throws Exception {
        test("bgsound", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_colgroup() throws Exception {
        test("bgsound", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_command() throws Exception {
        test("bgsound", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_content() throws Exception {
        test("bgsound", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_data() throws Exception {
        test("bgsound", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_datalist() throws Exception {
        test("bgsound", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_dd() throws Exception {
        test("bgsound", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_del() throws Exception {
        test("bgsound", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_details() throws Exception {
        test("bgsound", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_dfn() throws Exception {
        test("bgsound", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_dialog() throws Exception {
        test("bgsound", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_dir() throws Exception {
        test("bgsound", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_div() throws Exception {
        test("bgsound", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_dl() throws Exception {
        test("bgsound", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_dt() throws Exception {
        test("bgsound", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_em() throws Exception {
        test("bgsound", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_embed() throws Exception {
        test("bgsound", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_fieldset() throws Exception {
        test("bgsound", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_figcaption() throws Exception {
        test("bgsound", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_figure() throws Exception {
        test("bgsound", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_font() throws Exception {
        test("bgsound", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_footer() throws Exception {
        test("bgsound", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_form() throws Exception {
        test("bgsound", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_frame() throws Exception {
        test("bgsound", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_frameset() throws Exception {
        test("bgsound", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_h1() throws Exception {
        test("bgsound", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_h2() throws Exception {
        test("bgsound", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_h3() throws Exception {
        test("bgsound", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_h4() throws Exception {
        test("bgsound", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_h5() throws Exception {
        test("bgsound", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_h6() throws Exception {
        test("bgsound", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_head() throws Exception {
        test("bgsound", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_header() throws Exception {
        test("bgsound", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_hr() throws Exception {
        test("bgsound", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_html() throws Exception {
        test("bgsound", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_i() throws Exception {
        test("bgsound", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_iframe() throws Exception {
        test("bgsound", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_image() throws Exception {
        test("bgsound", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_img() throws Exception {
        test("bgsound", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_input() throws Exception {
        test("bgsound", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_ins() throws Exception {
        test("bgsound", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_isindex() throws Exception {
        test("bgsound", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_kbd() throws Exception {
        test("bgsound", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_keygen() throws Exception {
        test("bgsound", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_label() throws Exception {
        test("bgsound", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_layer() throws Exception {
        test("bgsound", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_legend() throws Exception {
        test("bgsound", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_li() throws Exception {
        test("bgsound", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_link() throws Exception {
        test("bgsound", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_listing() throws Exception {
        test("bgsound", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_main() throws Exception {
        test("bgsound", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_map() throws Exception {
        test("bgsound", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_mark() throws Exception {
        test("bgsound", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_marquee() throws Exception {
        test("bgsound", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_menu() throws Exception {
        test("bgsound", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_menuitem() throws Exception {
        test("bgsound", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_meta() throws Exception {
        test("bgsound", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_meter() throws Exception {
        test("bgsound", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_multicol() throws Exception {
        test("bgsound", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_nav() throws Exception {
        test("bgsound", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_nextid() throws Exception {
        test("bgsound", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_nobr() throws Exception {
        test("bgsound", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_noembed() throws Exception {
        test("bgsound", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_noframes() throws Exception {
        test("bgsound", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_nolayer() throws Exception {
        test("bgsound", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_noscript() throws Exception {
        test("bgsound", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_object() throws Exception {
        test("bgsound", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_ol() throws Exception {
        test("bgsound", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_optgroup() throws Exception {
        test("bgsound", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_option() throws Exception {
        test("bgsound", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_output() throws Exception {
        test("bgsound", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_p() throws Exception {
        test("bgsound", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_param() throws Exception {
        test("bgsound", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_picture() throws Exception {
        test("bgsound", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_plaintext() throws Exception {
        test("bgsound", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_pre() throws Exception {
        test("bgsound", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_progress() throws Exception {
        test("bgsound", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_q() throws Exception {
        test("bgsound", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_rp() throws Exception {
        test("bgsound", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_rt() throws Exception {
        test("bgsound", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_ruby() throws Exception {
        test("bgsound", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_s() throws Exception {
        test("bgsound", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_samp() throws Exception {
        test("bgsound", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_script() throws Exception {
        test("bgsound", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_section() throws Exception {
        test("bgsound", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_select() throws Exception {
        test("bgsound", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_slot() throws Exception {
        test("bgsound", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_small() throws Exception {
        test("bgsound", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_source() throws Exception {
        test("bgsound", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_span() throws Exception {
        test("bgsound", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_strike() throws Exception {
        test("bgsound", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_strong() throws Exception {
        test("bgsound", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_style() throws Exception {
        test("bgsound", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_sub() throws Exception {
        test("bgsound", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_summary() throws Exception {
        test("bgsound", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_sup() throws Exception {
        test("bgsound", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_svg() throws Exception {
        test("bgsound", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_table() throws Exception {
        test("bgsound", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_tbody() throws Exception {
        test("bgsound", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_td() throws Exception {
        test("bgsound", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_template() throws Exception {
        test("bgsound", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_textarea() throws Exception {
        test("bgsound", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_tfoot() throws Exception {
        test("bgsound", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_th() throws Exception {
        test("bgsound", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_thead() throws Exception {
        test("bgsound", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_time() throws Exception {
        test("bgsound", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_title() throws Exception {
        test("bgsound", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_tr() throws Exception {
        test("bgsound", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_track() throws Exception {
        test("bgsound", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_tt() throws Exception {
        test("bgsound", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_u() throws Exception {
        test("bgsound", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_ul() throws Exception {
        test("bgsound", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_var() throws Exception {
        test("bgsound", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_video() throws Exception {
        test("bgsound", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_wbr() throws Exception {
        test("bgsound", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _bgsound_xmp() throws Exception {
        test("bgsound", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_area() throws Exception {
        test("big", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_base() throws Exception {
        test("big", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_basefont() throws Exception {
        test("big", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_bgsound() throws Exception {
        test("big", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_br() throws Exception {
        test("big", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _big_command() throws Exception {
        test("big", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_embed() throws Exception {
        test("big", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_hr() throws Exception {
        test("big", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_image() throws Exception {
        test("big", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_img() throws Exception {
        test("big", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_input() throws Exception {
        test("big", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _big_isindex() throws Exception {
        test("big", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_keygen() throws Exception {
        test("big", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_link() throws Exception {
        test("big", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_meta() throws Exception {
        test("big", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_param() throws Exception {
        test("big", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_script() throws Exception {
        test("big", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_source() throws Exception {
        test("big", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_track() throws Exception {
        test("big", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _big_wbr() throws Exception {
        test("big", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_area() throws Exception {
        test("blink", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_base() throws Exception {
        test("blink", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_basefont() throws Exception {
        test("blink", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_bgsound() throws Exception {
        test("blink", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_br() throws Exception {
        test("blink", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _blink_command() throws Exception {
        test("blink", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_embed() throws Exception {
        test("blink", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_hr() throws Exception {
        test("blink", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_image() throws Exception {
        test("blink", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_img() throws Exception {
        test("blink", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_input() throws Exception {
        test("blink", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _blink_isindex() throws Exception {
        test("blink", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_keygen() throws Exception {
        test("blink", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_link() throws Exception {
        test("blink", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_meta() throws Exception {
        test("blink", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_param() throws Exception {
        test("blink", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_script() throws Exception {
        test("blink", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_source() throws Exception {
        test("blink", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_track() throws Exception {
        test("blink", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blink_wbr() throws Exception {
        test("blink", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_area() throws Exception {
        test("blockquote", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_base() throws Exception {
        test("blockquote", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_basefont() throws Exception {
        test("blockquote", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_bgsound() throws Exception {
        test("blockquote", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_br() throws Exception {
        test("blockquote", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _blockquote_command() throws Exception {
        test("blockquote", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_embed() throws Exception {
        test("blockquote", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_hr() throws Exception {
        test("blockquote", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_image() throws Exception {
        test("blockquote", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_img() throws Exception {
        test("blockquote", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_input() throws Exception {
        test("blockquote", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _blockquote_isindex() throws Exception {
        test("blockquote", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_keygen() throws Exception {
        test("blockquote", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_link() throws Exception {
        test("blockquote", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_meta() throws Exception {
        test("blockquote", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_param() throws Exception {
        test("blockquote", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_script() throws Exception {
        test("blockquote", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_source() throws Exception {
        test("blockquote", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_track() throws Exception {
        test("blockquote", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _blockquote_wbr() throws Exception {
        test("blockquote", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_a() throws Exception {
        test("body", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_abbr() throws Exception {
        test("body", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_acronym() throws Exception {
        test("body", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_address() throws Exception {
        test("body", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_applet() throws Exception {
        test("body", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_area() throws Exception {
        test("body", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_article() throws Exception {
        test("body", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_aside() throws Exception {
        test("body", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_audio() throws Exception {
        test("body", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_b() throws Exception {
        test("body", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_base() throws Exception {
        test("body", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_basefont() throws Exception {
        test("body", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_bdi() throws Exception {
        test("body", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_bdo() throws Exception {
        test("body", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_bgsound() throws Exception {
        test("body", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_big() throws Exception {
        test("body", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_blink() throws Exception {
        test("body", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_blockquote() throws Exception {
        test("body", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_br() throws Exception {
        test("body", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_button() throws Exception {
        test("body", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_canvas() throws Exception {
        test("body", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_center() throws Exception {
        test("body", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_cite() throws Exception {
        test("body", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_code() throws Exception {
        test("body", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "3",
            FF = "2")
    public void _body_command() throws Exception {
        test("body", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_content() throws Exception {
        test("body", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_data() throws Exception {
        test("body", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_datalist() throws Exception {
        test("body", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_dd() throws Exception {
        test("body", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_del() throws Exception {
        test("body", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_details() throws Exception {
        test("body", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_dfn() throws Exception {
        test("body", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_dialog() throws Exception {
        test("body", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_dir() throws Exception {
        test("body", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_div() throws Exception {
        test("body", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_dl() throws Exception {
        test("body", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_dt() throws Exception {
        test("body", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_em() throws Exception {
        test("body", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_embed() throws Exception {
        test("body", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_fieldset() throws Exception {
        test("body", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_figcaption() throws Exception {
        test("body", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_figure() throws Exception {
        test("body", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_font() throws Exception {
        test("body", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_footer() throws Exception {
        test("body", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_form() throws Exception {
        test("body", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_h1() throws Exception {
        test("body", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_h2() throws Exception {
        test("body", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_h3() throws Exception {
        test("body", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_h4() throws Exception {
        test("body", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_h5() throws Exception {
        test("body", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_h6() throws Exception {
        test("body", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_header() throws Exception {
        test("body", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_hr() throws Exception {
        test("body", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_i() throws Exception {
        test("body", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_iframe() throws Exception {
        test("body", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_image() throws Exception {
        test("body", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_img() throws Exception {
        test("body", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_input() throws Exception {
        test("body", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_ins() throws Exception {
        test("body", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "3")
    @NotYetImplemented(IE)
    public void _body_isindex() throws Exception {
        test("body", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_kbd() throws Exception {
        test("body", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_keygen() throws Exception {
        test("body", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_label() throws Exception {
        test("body", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_layer() throws Exception {
        test("body", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_legend() throws Exception {
        test("body", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_li() throws Exception {
        test("body", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_link() throws Exception {
        test("body", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_listing() throws Exception {
        test("body", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_main() throws Exception {
        test("body", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_map() throws Exception {
        test("body", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_mark() throws Exception {
        test("body", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_marquee() throws Exception {
        test("body", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_menu() throws Exception {
        test("body", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_menuitem() throws Exception {
        test("body", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_meta() throws Exception {
        test("body", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_meter() throws Exception {
        test("body", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_multicol() throws Exception {
        test("body", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_nav() throws Exception {
        test("body", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_nextid() throws Exception {
        test("body", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_nobr() throws Exception {
        test("body", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_noembed() throws Exception {
        test("body", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_noframes() throws Exception {
        test("body", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_nolayer() throws Exception {
        test("body", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_noscript() throws Exception {
        test("body", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_object() throws Exception {
        test("body", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_ol() throws Exception {
        test("body", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_optgroup() throws Exception {
        test("body", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_option() throws Exception {
        test("body", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_output() throws Exception {
        test("body", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_p() throws Exception {
        test("body", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_param() throws Exception {
        test("body", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_picture() throws Exception {
        test("body", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_plaintext() throws Exception {
        test("body", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_pre() throws Exception {
        test("body", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_progress() throws Exception {
        test("body", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_q() throws Exception {
        test("body", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_rp() throws Exception {
        test("body", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_rt() throws Exception {
        test("body", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_ruby() throws Exception {
        test("body", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_s() throws Exception {
        test("body", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_samp() throws Exception {
        test("body", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_script() throws Exception {
        test("body", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_section() throws Exception {
        test("body", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_select() throws Exception {
        test("body", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_slot() throws Exception {
        test("body", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_small() throws Exception {
        test("body", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_source() throws Exception {
        test("body", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_span() throws Exception {
        test("body", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_strike() throws Exception {
        test("body", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_strong() throws Exception {
        test("body", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_style() throws Exception {
        test("body", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_sub() throws Exception {
        test("body", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_summary() throws Exception {
        test("body", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_sup() throws Exception {
        test("body", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_svg() throws Exception {
        test("body", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_table() throws Exception {
        test("body", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_template() throws Exception {
        test("body", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_textarea() throws Exception {
        test("body", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_time() throws Exception {
        test("body", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_title() throws Exception {
        test("body", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_track() throws Exception {
        test("body", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_tt() throws Exception {
        test("body", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_u() throws Exception {
        test("body", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_ul() throws Exception {
        test("body", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_var() throws Exception {
        test("body", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_video() throws Exception {
        test("body", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    public void _body_wbr() throws Exception {
        test("body", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _body_xmp() throws Exception {
        test("body", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_a() throws Exception {
        test("br", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_abbr() throws Exception {
        test("br", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_acronym() throws Exception {
        test("br", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_address() throws Exception {
        test("br", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_applet() throws Exception {
        test("br", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_area() throws Exception {
        test("br", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_article() throws Exception {
        test("br", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_aside() throws Exception {
        test("br", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_audio() throws Exception {
        test("br", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_b() throws Exception {
        test("br", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_base() throws Exception {
        test("br", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_basefont() throws Exception {
        test("br", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_bdi() throws Exception {
        test("br", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_bdo() throws Exception {
        test("br", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_bgsound() throws Exception {
        test("br", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_big() throws Exception {
        test("br", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_blink() throws Exception {
        test("br", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_blockquote() throws Exception {
        test("br", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_body() throws Exception {
        test("br", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_br() throws Exception {
        test("br", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_button() throws Exception {
        test("br", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_canvas() throws Exception {
        test("br", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_caption() throws Exception {
        test("br", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_center() throws Exception {
        test("br", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_cite() throws Exception {
        test("br", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_code() throws Exception {
        test("br", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_col() throws Exception {
        test("br", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_colgroup() throws Exception {
        test("br", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_command() throws Exception {
        test("br", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_content() throws Exception {
        test("br", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_data() throws Exception {
        test("br", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_datalist() throws Exception {
        test("br", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_dd() throws Exception {
        test("br", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_del() throws Exception {
        test("br", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_details() throws Exception {
        test("br", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_dfn() throws Exception {
        test("br", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_dialog() throws Exception {
        test("br", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_dir() throws Exception {
        test("br", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_div() throws Exception {
        test("br", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_dl() throws Exception {
        test("br", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_dt() throws Exception {
        test("br", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_em() throws Exception {
        test("br", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_embed() throws Exception {
        test("br", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_fieldset() throws Exception {
        test("br", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_figcaption() throws Exception {
        test("br", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_figure() throws Exception {
        test("br", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_font() throws Exception {
        test("br", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_footer() throws Exception {
        test("br", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_form() throws Exception {
        test("br", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_frame() throws Exception {
        test("br", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_frameset() throws Exception {
        test("br", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_h1() throws Exception {
        test("br", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_h2() throws Exception {
        test("br", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_h3() throws Exception {
        test("br", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_h4() throws Exception {
        test("br", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_h5() throws Exception {
        test("br", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_h6() throws Exception {
        test("br", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_head() throws Exception {
        test("br", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_header() throws Exception {
        test("br", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_hr() throws Exception {
        test("br", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_html() throws Exception {
        test("br", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_i() throws Exception {
        test("br", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_iframe() throws Exception {
        test("br", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_image() throws Exception {
        test("br", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_img() throws Exception {
        test("br", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_input() throws Exception {
        test("br", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_ins() throws Exception {
        test("br", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_isindex() throws Exception {
        test("br", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_kbd() throws Exception {
        test("br", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_keygen() throws Exception {
        test("br", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_label() throws Exception {
        test("br", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_layer() throws Exception {
        test("br", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_legend() throws Exception {
        test("br", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_li() throws Exception {
        test("br", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_link() throws Exception {
        test("br", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_listing() throws Exception {
        test("br", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_main() throws Exception {
        test("br", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_map() throws Exception {
        test("br", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_mark() throws Exception {
        test("br", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_marquee() throws Exception {
        test("br", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_menu() throws Exception {
        test("br", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_menuitem() throws Exception {
        test("br", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_meta() throws Exception {
        test("br", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_meter() throws Exception {
        test("br", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_multicol() throws Exception {
        test("br", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_nav() throws Exception {
        test("br", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_nextid() throws Exception {
        test("br", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_nobr() throws Exception {
        test("br", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_noembed() throws Exception {
        test("br", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_noframes() throws Exception {
        test("br", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_nolayer() throws Exception {
        test("br", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_noscript() throws Exception {
        test("br", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_object() throws Exception {
        test("br", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_ol() throws Exception {
        test("br", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_optgroup() throws Exception {
        test("br", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_option() throws Exception {
        test("br", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_output() throws Exception {
        test("br", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_p() throws Exception {
        test("br", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_param() throws Exception {
        test("br", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_picture() throws Exception {
        test("br", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_plaintext() throws Exception {
        test("br", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_pre() throws Exception {
        test("br", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_progress() throws Exception {
        test("br", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_q() throws Exception {
        test("br", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_rp() throws Exception {
        test("br", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_rt() throws Exception {
        test("br", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_ruby() throws Exception {
        test("br", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_s() throws Exception {
        test("br", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_samp() throws Exception {
        test("br", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_script() throws Exception {
        test("br", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_section() throws Exception {
        test("br", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_select() throws Exception {
        test("br", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_slot() throws Exception {
        test("br", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_small() throws Exception {
        test("br", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_source() throws Exception {
        test("br", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_span() throws Exception {
        test("br", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_strike() throws Exception {
        test("br", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_strong() throws Exception {
        test("br", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_style() throws Exception {
        test("br", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_sub() throws Exception {
        test("br", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_summary() throws Exception {
        test("br", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_sup() throws Exception {
        test("br", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_svg() throws Exception {
        test("br", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_table() throws Exception {
        test("br", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_tbody() throws Exception {
        test("br", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_td() throws Exception {
        test("br", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_template() throws Exception {
        test("br", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_textarea() throws Exception {
        test("br", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_tfoot() throws Exception {
        test("br", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_th() throws Exception {
        test("br", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_thead() throws Exception {
        test("br", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_time() throws Exception {
        test("br", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_title() throws Exception {
        test("br", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_tr() throws Exception {
        test("br", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_track() throws Exception {
        test("br", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_tt() throws Exception {
        test("br", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_u() throws Exception {
        test("br", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_ul() throws Exception {
        test("br", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_var() throws Exception {
        test("br", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_video() throws Exception {
        test("br", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_wbr() throws Exception {
        test("br", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _br_xmp() throws Exception {
        test("br", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_area() throws Exception {
        test("button", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_base() throws Exception {
        test("button", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_basefont() throws Exception {
        test("button", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_bgsound() throws Exception {
        test("button", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_br() throws Exception {
        test("button", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _button_button() throws Exception {
        test("button", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _button_command() throws Exception {
        test("button", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_embed() throws Exception {
        test("button", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    public void _button_form() throws Exception {
        test("button", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_hr() throws Exception {
        test("button", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_image() throws Exception {
        test("button", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_img() throws Exception {
        test("button", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_input() throws Exception {
        test("button", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _button_isindex() throws Exception {
        test("button", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_keygen() throws Exception {
        test("button", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_link() throws Exception {
        test("button", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_meta() throws Exception {
        test("button", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_param() throws Exception {
        test("button", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_script() throws Exception {
        test("button", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_source() throws Exception {
        test("button", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_track() throws Exception {
        test("button", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _button_wbr() throws Exception {
        test("button", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_area() throws Exception {
        test("canvas", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_base() throws Exception {
        test("canvas", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_basefont() throws Exception {
        test("canvas", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_bgsound() throws Exception {
        test("canvas", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_br() throws Exception {
        test("canvas", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _canvas_command() throws Exception {
        test("canvas", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_embed() throws Exception {
        test("canvas", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_hr() throws Exception {
        test("canvas", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_image() throws Exception {
        test("canvas", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_img() throws Exception {
        test("canvas", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_input() throws Exception {
        test("canvas", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _canvas_isindex() throws Exception {
        test("canvas", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_keygen() throws Exception {
        test("canvas", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_link() throws Exception {
        test("canvas", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_meta() throws Exception {
        test("canvas", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_param() throws Exception {
        test("canvas", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_script() throws Exception {
        test("canvas", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_source() throws Exception {
        test("canvas", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_track() throws Exception {
        test("canvas", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _canvas_wbr() throws Exception {
        test("canvas", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_a() throws Exception {
        test("caption", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_abbr() throws Exception {
        test("caption", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_acronym() throws Exception {
        test("caption", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_address() throws Exception {
        test("caption", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_applet() throws Exception {
        test("caption", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_area() throws Exception {
        test("caption", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_article() throws Exception {
        test("caption", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_aside() throws Exception {
        test("caption", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_audio() throws Exception {
        test("caption", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_b() throws Exception {
        test("caption", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_base() throws Exception {
        test("caption", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_basefont() throws Exception {
        test("caption", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_bdi() throws Exception {
        test("caption", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_bdo() throws Exception {
        test("caption", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_bgsound() throws Exception {
        test("caption", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_big() throws Exception {
        test("caption", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_blink() throws Exception {
        test("caption", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_blockquote() throws Exception {
        test("caption", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_body() throws Exception {
        test("caption", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_br() throws Exception {
        test("caption", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_button() throws Exception {
        test("caption", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_canvas() throws Exception {
        test("caption", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_caption() throws Exception {
        test("caption", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_center() throws Exception {
        test("caption", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_cite() throws Exception {
        test("caption", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_code() throws Exception {
        test("caption", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_col() throws Exception {
        test("caption", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_colgroup() throws Exception {
        test("caption", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_command() throws Exception {
        test("caption", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_content() throws Exception {
        test("caption", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_data() throws Exception {
        test("caption", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_datalist() throws Exception {
        test("caption", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_dd() throws Exception {
        test("caption", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_del() throws Exception {
        test("caption", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_details() throws Exception {
        test("caption", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_dfn() throws Exception {
        test("caption", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_dialog() throws Exception {
        test("caption", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_dir() throws Exception {
        test("caption", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_div() throws Exception {
        test("caption", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_dl() throws Exception {
        test("caption", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_dt() throws Exception {
        test("caption", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_em() throws Exception {
        test("caption", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_embed() throws Exception {
        test("caption", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_fieldset() throws Exception {
        test("caption", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_figcaption() throws Exception {
        test("caption", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_figure() throws Exception {
        test("caption", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_font() throws Exception {
        test("caption", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_footer() throws Exception {
        test("caption", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_form() throws Exception {
        test("caption", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_frame() throws Exception {
        test("caption", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_frameset() throws Exception {
        test("caption", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_h1() throws Exception {
        test("caption", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_h2() throws Exception {
        test("caption", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_h3() throws Exception {
        test("caption", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_h4() throws Exception {
        test("caption", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_h5() throws Exception {
        test("caption", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_h6() throws Exception {
        test("caption", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_head() throws Exception {
        test("caption", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_header() throws Exception {
        test("caption", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_hr() throws Exception {
        test("caption", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_html() throws Exception {
        test("caption", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_i() throws Exception {
        test("caption", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_iframe() throws Exception {
        test("caption", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_image() throws Exception {
        test("caption", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_img() throws Exception {
        test("caption", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_input() throws Exception {
        test("caption", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_ins() throws Exception {
        test("caption", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_isindex() throws Exception {
        test("caption", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_kbd() throws Exception {
        test("caption", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_keygen() throws Exception {
        test("caption", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_label() throws Exception {
        test("caption", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_layer() throws Exception {
        test("caption", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_legend() throws Exception {
        test("caption", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_li() throws Exception {
        test("caption", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_link() throws Exception {
        test("caption", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_listing() throws Exception {
        test("caption", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_main() throws Exception {
        test("caption", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_map() throws Exception {
        test("caption", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_mark() throws Exception {
        test("caption", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_marquee() throws Exception {
        test("caption", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_menu() throws Exception {
        test("caption", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_menuitem() throws Exception {
        test("caption", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_meta() throws Exception {
        test("caption", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_meter() throws Exception {
        test("caption", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_multicol() throws Exception {
        test("caption", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_nav() throws Exception {
        test("caption", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_nextid() throws Exception {
        test("caption", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_nobr() throws Exception {
        test("caption", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_noembed() throws Exception {
        test("caption", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_noframes() throws Exception {
        test("caption", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_nolayer() throws Exception {
        test("caption", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_noscript() throws Exception {
        test("caption", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_object() throws Exception {
        test("caption", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_ol() throws Exception {
        test("caption", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_optgroup() throws Exception {
        test("caption", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_option() throws Exception {
        test("caption", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_output() throws Exception {
        test("caption", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_p() throws Exception {
        test("caption", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_param() throws Exception {
        test("caption", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_picture() throws Exception {
        test("caption", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_plaintext() throws Exception {
        test("caption", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_pre() throws Exception {
        test("caption", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_progress() throws Exception {
        test("caption", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_q() throws Exception {
        test("caption", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_rp() throws Exception {
        test("caption", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_rt() throws Exception {
        test("caption", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_ruby() throws Exception {
        test("caption", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_s() throws Exception {
        test("caption", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_samp() throws Exception {
        test("caption", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_script() throws Exception {
        test("caption", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_section() throws Exception {
        test("caption", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_select() throws Exception {
        test("caption", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_slot() throws Exception {
        test("caption", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_small() throws Exception {
        test("caption", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_source() throws Exception {
        test("caption", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_span() throws Exception {
        test("caption", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_strike() throws Exception {
        test("caption", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_strong() throws Exception {
        test("caption", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_style() throws Exception {
        test("caption", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_sub() throws Exception {
        test("caption", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_summary() throws Exception {
        test("caption", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_sup() throws Exception {
        test("caption", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_svg() throws Exception {
        test("caption", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_table() throws Exception {
        test("caption", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_tbody() throws Exception {
        test("caption", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_td() throws Exception {
        test("caption", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_template() throws Exception {
        test("caption", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_textarea() throws Exception {
        test("caption", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_tfoot() throws Exception {
        test("caption", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_th() throws Exception {
        test("caption", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_thead() throws Exception {
        test("caption", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_time() throws Exception {
        test("caption", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_title() throws Exception {
        test("caption", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_tr() throws Exception {
        test("caption", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_track() throws Exception {
        test("caption", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_tt() throws Exception {
        test("caption", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_u() throws Exception {
        test("caption", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_ul() throws Exception {
        test("caption", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_var() throws Exception {
        test("caption", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_video() throws Exception {
        test("caption", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_wbr() throws Exception {
        test("caption", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _caption_xmp() throws Exception {
        test("caption", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_area() throws Exception {
        test("center", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_base() throws Exception {
        test("center", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_basefont() throws Exception {
        test("center", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_bgsound() throws Exception {
        test("center", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_br() throws Exception {
        test("center", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _center_command() throws Exception {
        test("center", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_embed() throws Exception {
        test("center", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_hr() throws Exception {
        test("center", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_image() throws Exception {
        test("center", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_img() throws Exception {
        test("center", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_input() throws Exception {
        test("center", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _center_isindex() throws Exception {
        test("center", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_keygen() throws Exception {
        test("center", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_link() throws Exception {
        test("center", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_meta() throws Exception {
        test("center", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_param() throws Exception {
        test("center", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_script() throws Exception {
        test("center", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_source() throws Exception {
        test("center", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_track() throws Exception {
        test("center", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _center_wbr() throws Exception {
        test("center", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_area() throws Exception {
        test("cite", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_base() throws Exception {
        test("cite", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_basefont() throws Exception {
        test("cite", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_bgsound() throws Exception {
        test("cite", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_br() throws Exception {
        test("cite", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _cite_command() throws Exception {
        test("cite", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_embed() throws Exception {
        test("cite", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_hr() throws Exception {
        test("cite", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_image() throws Exception {
        test("cite", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_img() throws Exception {
        test("cite", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_input() throws Exception {
        test("cite", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _cite_isindex() throws Exception {
        test("cite", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_keygen() throws Exception {
        test("cite", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_link() throws Exception {
        test("cite", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_meta() throws Exception {
        test("cite", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_param() throws Exception {
        test("cite", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_script() throws Exception {
        test("cite", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_source() throws Exception {
        test("cite", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_track() throws Exception {
        test("cite", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _cite_wbr() throws Exception {
        test("cite", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_area() throws Exception {
        test("code", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_base() throws Exception {
        test("code", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_basefont() throws Exception {
        test("code", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_bgsound() throws Exception {
        test("code", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_br() throws Exception {
        test("code", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _code_command() throws Exception {
        test("code", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_embed() throws Exception {
        test("code", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_hr() throws Exception {
        test("code", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_image() throws Exception {
        test("code", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_img() throws Exception {
        test("code", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_input() throws Exception {
        test("code", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _code_isindex() throws Exception {
        test("code", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_keygen() throws Exception {
        test("code", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_link() throws Exception {
        test("code", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_meta() throws Exception {
        test("code", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_param() throws Exception {
        test("code", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_script() throws Exception {
        test("code", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_source() throws Exception {
        test("code", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_track() throws Exception {
        test("code", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _code_wbr() throws Exception {
        test("code", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_a() throws Exception {
        test("col", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_abbr() throws Exception {
        test("col", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_acronym() throws Exception {
        test("col", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_address() throws Exception {
        test("col", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_applet() throws Exception {
        test("col", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_area() throws Exception {
        test("col", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_article() throws Exception {
        test("col", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_aside() throws Exception {
        test("col", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_audio() throws Exception {
        test("col", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_b() throws Exception {
        test("col", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_base() throws Exception {
        test("col", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_basefont() throws Exception {
        test("col", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_bdi() throws Exception {
        test("col", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_bdo() throws Exception {
        test("col", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_bgsound() throws Exception {
        test("col", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_big() throws Exception {
        test("col", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_blink() throws Exception {
        test("col", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_blockquote() throws Exception {
        test("col", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_body() throws Exception {
        test("col", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_br() throws Exception {
        test("col", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_button() throws Exception {
        test("col", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_canvas() throws Exception {
        test("col", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_caption() throws Exception {
        test("col", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_center() throws Exception {
        test("col", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_cite() throws Exception {
        test("col", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_code() throws Exception {
        test("col", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_col() throws Exception {
        test("col", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_colgroup() throws Exception {
        test("col", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_command() throws Exception {
        test("col", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_content() throws Exception {
        test("col", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_data() throws Exception {
        test("col", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_datalist() throws Exception {
        test("col", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_dd() throws Exception {
        test("col", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_del() throws Exception {
        test("col", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_details() throws Exception {
        test("col", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_dfn() throws Exception {
        test("col", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_dialog() throws Exception {
        test("col", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_dir() throws Exception {
        test("col", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_div() throws Exception {
        test("col", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_dl() throws Exception {
        test("col", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_dt() throws Exception {
        test("col", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_em() throws Exception {
        test("col", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_embed() throws Exception {
        test("col", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_fieldset() throws Exception {
        test("col", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_figcaption() throws Exception {
        test("col", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_figure() throws Exception {
        test("col", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_font() throws Exception {
        test("col", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_footer() throws Exception {
        test("col", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_form() throws Exception {
        test("col", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_frame() throws Exception {
        test("col", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_frameset() throws Exception {
        test("col", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_h1() throws Exception {
        test("col", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_h2() throws Exception {
        test("col", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_h3() throws Exception {
        test("col", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_h4() throws Exception {
        test("col", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_h5() throws Exception {
        test("col", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_h6() throws Exception {
        test("col", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_head() throws Exception {
        test("col", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_header() throws Exception {
        test("col", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_hr() throws Exception {
        test("col", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_html() throws Exception {
        test("col", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_i() throws Exception {
        test("col", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_iframe() throws Exception {
        test("col", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_image() throws Exception {
        test("col", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_img() throws Exception {
        test("col", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_input() throws Exception {
        test("col", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_ins() throws Exception {
        test("col", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_isindex() throws Exception {
        test("col", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_kbd() throws Exception {
        test("col", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_keygen() throws Exception {
        test("col", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_label() throws Exception {
        test("col", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_layer() throws Exception {
        test("col", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_legend() throws Exception {
        test("col", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_li() throws Exception {
        test("col", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_link() throws Exception {
        test("col", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_listing() throws Exception {
        test("col", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_main() throws Exception {
        test("col", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_map() throws Exception {
        test("col", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_mark() throws Exception {
        test("col", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_marquee() throws Exception {
        test("col", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_menu() throws Exception {
        test("col", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_menuitem() throws Exception {
        test("col", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_meta() throws Exception {
        test("col", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_meter() throws Exception {
        test("col", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_multicol() throws Exception {
        test("col", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_nav() throws Exception {
        test("col", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_nextid() throws Exception {
        test("col", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_nobr() throws Exception {
        test("col", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_noembed() throws Exception {
        test("col", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_noframes() throws Exception {
        test("col", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_nolayer() throws Exception {
        test("col", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_noscript() throws Exception {
        test("col", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_object() throws Exception {
        test("col", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_ol() throws Exception {
        test("col", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_optgroup() throws Exception {
        test("col", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_option() throws Exception {
        test("col", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_output() throws Exception {
        test("col", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_p() throws Exception {
        test("col", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_param() throws Exception {
        test("col", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_picture() throws Exception {
        test("col", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_plaintext() throws Exception {
        test("col", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_pre() throws Exception {
        test("col", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_progress() throws Exception {
        test("col", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_q() throws Exception {
        test("col", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_rp() throws Exception {
        test("col", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_rt() throws Exception {
        test("col", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_ruby() throws Exception {
        test("col", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_s() throws Exception {
        test("col", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_samp() throws Exception {
        test("col", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_script() throws Exception {
        test("col", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_section() throws Exception {
        test("col", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_select() throws Exception {
        test("col", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_slot() throws Exception {
        test("col", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_small() throws Exception {
        test("col", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_source() throws Exception {
        test("col", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_span() throws Exception {
        test("col", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_strike() throws Exception {
        test("col", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_strong() throws Exception {
        test("col", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_style() throws Exception {
        test("col", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_sub() throws Exception {
        test("col", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_summary() throws Exception {
        test("col", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_sup() throws Exception {
        test("col", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_svg() throws Exception {
        test("col", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_table() throws Exception {
        test("col", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_tbody() throws Exception {
        test("col", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_td() throws Exception {
        test("col", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_template() throws Exception {
        test("col", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_textarea() throws Exception {
        test("col", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_tfoot() throws Exception {
        test("col", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_th() throws Exception {
        test("col", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_thead() throws Exception {
        test("col", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_time() throws Exception {
        test("col", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_title() throws Exception {
        test("col", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_tr() throws Exception {
        test("col", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_track() throws Exception {
        test("col", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_tt() throws Exception {
        test("col", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_u() throws Exception {
        test("col", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_ul() throws Exception {
        test("col", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_var() throws Exception {
        test("col", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_video() throws Exception {
        test("col", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_wbr() throws Exception {
        test("col", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _col_xmp() throws Exception {
        test("col", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_a() throws Exception {
        test("colgroup", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_abbr() throws Exception {
        test("colgroup", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_acronym() throws Exception {
        test("colgroup", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_address() throws Exception {
        test("colgroup", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_applet() throws Exception {
        test("colgroup", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_area() throws Exception {
        test("colgroup", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_article() throws Exception {
        test("colgroup", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_aside() throws Exception {
        test("colgroup", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_audio() throws Exception {
        test("colgroup", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_b() throws Exception {
        test("colgroup", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_base() throws Exception {
        test("colgroup", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_basefont() throws Exception {
        test("colgroup", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_bdi() throws Exception {
        test("colgroup", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_bdo() throws Exception {
        test("colgroup", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_bgsound() throws Exception {
        test("colgroup", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_big() throws Exception {
        test("colgroup", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_blink() throws Exception {
        test("colgroup", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_blockquote() throws Exception {
        test("colgroup", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_body() throws Exception {
        test("colgroup", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_br() throws Exception {
        test("colgroup", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_button() throws Exception {
        test("colgroup", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_canvas() throws Exception {
        test("colgroup", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_caption() throws Exception {
        test("colgroup", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_center() throws Exception {
        test("colgroup", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_cite() throws Exception {
        test("colgroup", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_code() throws Exception {
        test("colgroup", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_col() throws Exception {
        test("colgroup", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_colgroup() throws Exception {
        test("colgroup", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_command() throws Exception {
        test("colgroup", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_content() throws Exception {
        test("colgroup", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_data() throws Exception {
        test("colgroup", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_datalist() throws Exception {
        test("colgroup", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_dd() throws Exception {
        test("colgroup", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_del() throws Exception {
        test("colgroup", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_details() throws Exception {
        test("colgroup", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_dfn() throws Exception {
        test("colgroup", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_dialog() throws Exception {
        test("colgroup", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_dir() throws Exception {
        test("colgroup", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_div() throws Exception {
        test("colgroup", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_dl() throws Exception {
        test("colgroup", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_dt() throws Exception {
        test("colgroup", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_em() throws Exception {
        test("colgroup", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_embed() throws Exception {
        test("colgroup", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_fieldset() throws Exception {
        test("colgroup", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_figcaption() throws Exception {
        test("colgroup", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_figure() throws Exception {
        test("colgroup", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_font() throws Exception {
        test("colgroup", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_footer() throws Exception {
        test("colgroup", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_form() throws Exception {
        test("colgroup", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_frame() throws Exception {
        test("colgroup", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_frameset() throws Exception {
        test("colgroup", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_h1() throws Exception {
        test("colgroup", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_h2() throws Exception {
        test("colgroup", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_h3() throws Exception {
        test("colgroup", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_h4() throws Exception {
        test("colgroup", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_h5() throws Exception {
        test("colgroup", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_h6() throws Exception {
        test("colgroup", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_head() throws Exception {
        test("colgroup", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_header() throws Exception {
        test("colgroup", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_hr() throws Exception {
        test("colgroup", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_html() throws Exception {
        test("colgroup", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_i() throws Exception {
        test("colgroup", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_iframe() throws Exception {
        test("colgroup", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_image() throws Exception {
        test("colgroup", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_img() throws Exception {
        test("colgroup", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_input() throws Exception {
        test("colgroup", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_ins() throws Exception {
        test("colgroup", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_isindex() throws Exception {
        test("colgroup", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_kbd() throws Exception {
        test("colgroup", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_keygen() throws Exception {
        test("colgroup", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_label() throws Exception {
        test("colgroup", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_layer() throws Exception {
        test("colgroup", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_legend() throws Exception {
        test("colgroup", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_li() throws Exception {
        test("colgroup", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_link() throws Exception {
        test("colgroup", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_listing() throws Exception {
        test("colgroup", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_main() throws Exception {
        test("colgroup", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_map() throws Exception {
        test("colgroup", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_mark() throws Exception {
        test("colgroup", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_marquee() throws Exception {
        test("colgroup", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_menu() throws Exception {
        test("colgroup", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_menuitem() throws Exception {
        test("colgroup", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_meta() throws Exception {
        test("colgroup", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_meter() throws Exception {
        test("colgroup", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_multicol() throws Exception {
        test("colgroup", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_nav() throws Exception {
        test("colgroup", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_nextid() throws Exception {
        test("colgroup", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_nobr() throws Exception {
        test("colgroup", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_noembed() throws Exception {
        test("colgroup", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_noframes() throws Exception {
        test("colgroup", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_nolayer() throws Exception {
        test("colgroup", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_noscript() throws Exception {
        test("colgroup", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_object() throws Exception {
        test("colgroup", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_ol() throws Exception {
        test("colgroup", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_optgroup() throws Exception {
        test("colgroup", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_option() throws Exception {
        test("colgroup", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_output() throws Exception {
        test("colgroup", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_p() throws Exception {
        test("colgroup", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_param() throws Exception {
        test("colgroup", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_picture() throws Exception {
        test("colgroup", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_plaintext() throws Exception {
        test("colgroup", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_pre() throws Exception {
        test("colgroup", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_progress() throws Exception {
        test("colgroup", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_q() throws Exception {
        test("colgroup", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_rp() throws Exception {
        test("colgroup", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_rt() throws Exception {
        test("colgroup", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_ruby() throws Exception {
        test("colgroup", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_s() throws Exception {
        test("colgroup", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_samp() throws Exception {
        test("colgroup", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_script() throws Exception {
        test("colgroup", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_section() throws Exception {
        test("colgroup", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_select() throws Exception {
        test("colgroup", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_slot() throws Exception {
        test("colgroup", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_small() throws Exception {
        test("colgroup", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_source() throws Exception {
        test("colgroup", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_span() throws Exception {
        test("colgroup", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_strike() throws Exception {
        test("colgroup", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_strong() throws Exception {
        test("colgroup", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_style() throws Exception {
        test("colgroup", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_sub() throws Exception {
        test("colgroup", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_summary() throws Exception {
        test("colgroup", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_sup() throws Exception {
        test("colgroup", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_svg() throws Exception {
        test("colgroup", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_table() throws Exception {
        test("colgroup", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_tbody() throws Exception {
        test("colgroup", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_td() throws Exception {
        test("colgroup", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_template() throws Exception {
        test("colgroup", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_textarea() throws Exception {
        test("colgroup", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_tfoot() throws Exception {
        test("colgroup", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_th() throws Exception {
        test("colgroup", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_thead() throws Exception {
        test("colgroup", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_time() throws Exception {
        test("colgroup", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_title() throws Exception {
        test("colgroup", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_tr() throws Exception {
        test("colgroup", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_track() throws Exception {
        test("colgroup", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_tt() throws Exception {
        test("colgroup", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_u() throws Exception {
        test("colgroup", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_ul() throws Exception {
        test("colgroup", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_var() throws Exception {
        test("colgroup", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_video() throws Exception {
        test("colgroup", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_wbr() throws Exception {
        test("colgroup", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _colgroup_xmp() throws Exception {
        test("colgroup", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_a() throws Exception {
        test("command", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_abbr() throws Exception {
        test("command", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_acronym() throws Exception {
        test("command", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_address() throws Exception {
        test("command", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_applet() throws Exception {
        test("command", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_area() throws Exception {
        test("command", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_article() throws Exception {
        test("command", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_aside() throws Exception {
        test("command", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_audio() throws Exception {
        test("command", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_b() throws Exception {
        test("command", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_base() throws Exception {
        test("command", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_basefont() throws Exception {
        test("command", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_bdi() throws Exception {
        test("command", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_bdo() throws Exception {
        test("command", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_bgsound() throws Exception {
        test("command", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_big() throws Exception {
        test("command", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_blink() throws Exception {
        test("command", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_blockquote() throws Exception {
        test("command", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_body() throws Exception {
        test("command", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_br() throws Exception {
        test("command", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_button() throws Exception {
        test("command", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_canvas() throws Exception {
        test("command", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_caption() throws Exception {
        test("command", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_center() throws Exception {
        test("command", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_cite() throws Exception {
        test("command", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_code() throws Exception {
        test("command", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_col() throws Exception {
        test("command", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_colgroup() throws Exception {
        test("command", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_command() throws Exception {
        test("command", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_content() throws Exception {
        test("command", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_data() throws Exception {
        test("command", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_datalist() throws Exception {
        test("command", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_dd() throws Exception {
        test("command", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_del() throws Exception {
        test("command", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_details() throws Exception {
        test("command", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_dfn() throws Exception {
        test("command", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_dialog() throws Exception {
        test("command", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_dir() throws Exception {
        test("command", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_div() throws Exception {
        test("command", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_dl() throws Exception {
        test("command", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_dt() throws Exception {
        test("command", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_em() throws Exception {
        test("command", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_embed() throws Exception {
        test("command", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_fieldset() throws Exception {
        test("command", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_figcaption() throws Exception {
        test("command", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_figure() throws Exception {
        test("command", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_font() throws Exception {
        test("command", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_footer() throws Exception {
        test("command", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_form() throws Exception {
        test("command", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_frame() throws Exception {
        test("command", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_frameset() throws Exception {
        test("command", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_h1() throws Exception {
        test("command", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_h2() throws Exception {
        test("command", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_h3() throws Exception {
        test("command", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_h4() throws Exception {
        test("command", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_h5() throws Exception {
        test("command", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_h6() throws Exception {
        test("command", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_head() throws Exception {
        test("command", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_header() throws Exception {
        test("command", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_hr() throws Exception {
        test("command", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_html() throws Exception {
        test("command", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_i() throws Exception {
        test("command", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_iframe() throws Exception {
        test("command", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_image() throws Exception {
        test("command", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_img() throws Exception {
        test("command", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_input() throws Exception {
        test("command", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_ins() throws Exception {
        test("command", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_isindex() throws Exception {
        test("command", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_kbd() throws Exception {
        test("command", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_keygen() throws Exception {
        test("command", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_label() throws Exception {
        test("command", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_layer() throws Exception {
        test("command", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_legend() throws Exception {
        test("command", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_li() throws Exception {
        test("command", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_link() throws Exception {
        test("command", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_listing() throws Exception {
        test("command", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_main() throws Exception {
        test("command", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_map() throws Exception {
        test("command", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_mark() throws Exception {
        test("command", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_marquee() throws Exception {
        test("command", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_menu() throws Exception {
        test("command", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_menuitem() throws Exception {
        test("command", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_meta() throws Exception {
        test("command", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_meter() throws Exception {
        test("command", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_multicol() throws Exception {
        test("command", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_nav() throws Exception {
        test("command", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_nextid() throws Exception {
        test("command", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_nobr() throws Exception {
        test("command", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_noembed() throws Exception {
        test("command", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_noframes() throws Exception {
        test("command", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_nolayer() throws Exception {
        test("command", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_noscript() throws Exception {
        test("command", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_object() throws Exception {
        test("command", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_ol() throws Exception {
        test("command", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_optgroup() throws Exception {
        test("command", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_option() throws Exception {
        test("command", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_output() throws Exception {
        test("command", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_p() throws Exception {
        test("command", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_param() throws Exception {
        test("command", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_picture() throws Exception {
        test("command", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_plaintext() throws Exception {
        test("command", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_pre() throws Exception {
        test("command", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_progress() throws Exception {
        test("command", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_q() throws Exception {
        test("command", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_rp() throws Exception {
        test("command", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_rt() throws Exception {
        test("command", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_ruby() throws Exception {
        test("command", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_s() throws Exception {
        test("command", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_samp() throws Exception {
        test("command", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_script() throws Exception {
        test("command", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_section() throws Exception {
        test("command", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_select() throws Exception {
        test("command", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_slot() throws Exception {
        test("command", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_small() throws Exception {
        test("command", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_source() throws Exception {
        test("command", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_span() throws Exception {
        test("command", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_strike() throws Exception {
        test("command", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_strong() throws Exception {
        test("command", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_style() throws Exception {
        test("command", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_sub() throws Exception {
        test("command", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_summary() throws Exception {
        test("command", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_sup() throws Exception {
        test("command", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_svg() throws Exception {
        test("command", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_table() throws Exception {
        test("command", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_tbody() throws Exception {
        test("command", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_td() throws Exception {
        test("command", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_template() throws Exception {
        test("command", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_textarea() throws Exception {
        test("command", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_tfoot() throws Exception {
        test("command", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_th() throws Exception {
        test("command", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_thead() throws Exception {
        test("command", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_time() throws Exception {
        test("command", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_title() throws Exception {
        test("command", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_tr() throws Exception {
        test("command", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_track() throws Exception {
        test("command", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_tt() throws Exception {
        test("command", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_u() throws Exception {
        test("command", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_ul() throws Exception {
        test("command", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_var() throws Exception {
        test("command", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_video() throws Exception {
        test("command", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _command_wbr() throws Exception {
        test("command", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "1")
    public void _command_xmp() throws Exception {
        test("command", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_area() throws Exception {
        test("content", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_base() throws Exception {
        test("content", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_basefont() throws Exception {
        test("content", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_bgsound() throws Exception {
        test("content", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_br() throws Exception {
        test("content", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _content_command() throws Exception {
        test("content", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_embed() throws Exception {
        test("content", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_hr() throws Exception {
        test("content", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_image() throws Exception {
        test("content", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_img() throws Exception {
        test("content", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_input() throws Exception {
        test("content", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _content_isindex() throws Exception {
        test("content", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_keygen() throws Exception {
        test("content", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_link() throws Exception {
        test("content", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_meta() throws Exception {
        test("content", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_param() throws Exception {
        test("content", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_script() throws Exception {
        test("content", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_source() throws Exception {
        test("content", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_track() throws Exception {
        test("content", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _content_wbr() throws Exception {
        test("content", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_area() throws Exception {
        test("data", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_base() throws Exception {
        test("data", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_basefont() throws Exception {
        test("data", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_bgsound() throws Exception {
        test("data", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_br() throws Exception {
        test("data", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _data_command() throws Exception {
        test("data", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_embed() throws Exception {
        test("data", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_hr() throws Exception {
        test("data", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_image() throws Exception {
        test("data", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_img() throws Exception {
        test("data", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_input() throws Exception {
        test("data", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _data_isindex() throws Exception {
        test("data", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_keygen() throws Exception {
        test("data", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_link() throws Exception {
        test("data", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_meta() throws Exception {
        test("data", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_param() throws Exception {
        test("data", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_script() throws Exception {
        test("data", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_source() throws Exception {
        test("data", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_track() throws Exception {
        test("data", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _data_wbr() throws Exception {
        test("data", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_area() throws Exception {
        test("datalist", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_base() throws Exception {
        test("datalist", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_basefont() throws Exception {
        test("datalist", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_bgsound() throws Exception {
        test("datalist", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_br() throws Exception {
        test("datalist", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _datalist_command() throws Exception {
        test("datalist", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_embed() throws Exception {
        test("datalist", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_hr() throws Exception {
        test("datalist", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_image() throws Exception {
        test("datalist", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_img() throws Exception {
        test("datalist", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_input() throws Exception {
        test("datalist", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _datalist_isindex() throws Exception {
        test("datalist", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_keygen() throws Exception {
        test("datalist", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_link() throws Exception {
        test("datalist", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_meta() throws Exception {
        test("datalist", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_param() throws Exception {
        test("datalist", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_script() throws Exception {
        test("datalist", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_source() throws Exception {
        test("datalist", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_track() throws Exception {
        test("datalist", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _datalist_wbr() throws Exception {
        test("datalist", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_area() throws Exception {
        test("dd", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_base() throws Exception {
        test("dd", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_basefont() throws Exception {
        test("dd", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_bgsound() throws Exception {
        test("dd", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_br() throws Exception {
        test("dd", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _dd_command() throws Exception {
        test("dd", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _dd_dd() throws Exception {
        test("dd", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _dd_dt() throws Exception {
        test("dd", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_embed() throws Exception {
        test("dd", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_hr() throws Exception {
        test("dd", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_image() throws Exception {
        test("dd", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_img() throws Exception {
        test("dd", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_input() throws Exception {
        test("dd", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _dd_isindex() throws Exception {
        test("dd", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_keygen() throws Exception {
        test("dd", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_link() throws Exception {
        test("dd", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_meta() throws Exception {
        test("dd", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_param() throws Exception {
        test("dd", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_script() throws Exception {
        test("dd", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_source() throws Exception {
        test("dd", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_track() throws Exception {
        test("dd", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dd_wbr() throws Exception {
        test("dd", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_area() throws Exception {
        test("del", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_base() throws Exception {
        test("del", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_basefont() throws Exception {
        test("del", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_bgsound() throws Exception {
        test("del", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_br() throws Exception {
        test("del", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _del_command() throws Exception {
        test("del", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_embed() throws Exception {
        test("del", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_hr() throws Exception {
        test("del", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_image() throws Exception {
        test("del", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_img() throws Exception {
        test("del", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_input() throws Exception {
        test("del", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _del_isindex() throws Exception {
        test("del", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_keygen() throws Exception {
        test("del", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_link() throws Exception {
        test("del", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_meta() throws Exception {
        test("del", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_param() throws Exception {
        test("del", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_script() throws Exception {
        test("del", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_source() throws Exception {
        test("del", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_track() throws Exception {
        test("del", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _del_wbr() throws Exception {
        test("del", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_area() throws Exception {
        test("details", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_base() throws Exception {
        test("details", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_basefont() throws Exception {
        test("details", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_bgsound() throws Exception {
        test("details", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_br() throws Exception {
        test("details", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _details_command() throws Exception {
        test("details", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_embed() throws Exception {
        test("details", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_hr() throws Exception {
        test("details", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_image() throws Exception {
        test("details", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_img() throws Exception {
        test("details", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_input() throws Exception {
        test("details", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _details_isindex() throws Exception {
        test("details", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_keygen() throws Exception {
        test("details", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_link() throws Exception {
        test("details", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_meta() throws Exception {
        test("details", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_param() throws Exception {
        test("details", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_script() throws Exception {
        test("details", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_source() throws Exception {
        test("details", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_track() throws Exception {
        test("details", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _details_wbr() throws Exception {
        test("details", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_area() throws Exception {
        test("dfn", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_base() throws Exception {
        test("dfn", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_basefont() throws Exception {
        test("dfn", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_bgsound() throws Exception {
        test("dfn", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_br() throws Exception {
        test("dfn", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _dfn_command() throws Exception {
        test("dfn", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_embed() throws Exception {
        test("dfn", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_hr() throws Exception {
        test("dfn", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_image() throws Exception {
        test("dfn", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_img() throws Exception {
        test("dfn", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_input() throws Exception {
        test("dfn", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _dfn_isindex() throws Exception {
        test("dfn", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_keygen() throws Exception {
        test("dfn", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_link() throws Exception {
        test("dfn", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_meta() throws Exception {
        test("dfn", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_param() throws Exception {
        test("dfn", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_script() throws Exception {
        test("dfn", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_source() throws Exception {
        test("dfn", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_track() throws Exception {
        test("dfn", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dfn_wbr() throws Exception {
        test("dfn", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_area() throws Exception {
        test("dialog", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_base() throws Exception {
        test("dialog", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_basefont() throws Exception {
        test("dialog", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_bgsound() throws Exception {
        test("dialog", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_br() throws Exception {
        test("dialog", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _dialog_command() throws Exception {
        test("dialog", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_embed() throws Exception {
        test("dialog", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_hr() throws Exception {
        test("dialog", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_image() throws Exception {
        test("dialog", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_img() throws Exception {
        test("dialog", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_input() throws Exception {
        test("dialog", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _dialog_isindex() throws Exception {
        test("dialog", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_keygen() throws Exception {
        test("dialog", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_link() throws Exception {
        test("dialog", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_meta() throws Exception {
        test("dialog", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_param() throws Exception {
        test("dialog", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_script() throws Exception {
        test("dialog", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_source() throws Exception {
        test("dialog", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_track() throws Exception {
        test("dialog", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dialog_wbr() throws Exception {
        test("dialog", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_area() throws Exception {
        test("dir", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_base() throws Exception {
        test("dir", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_basefont() throws Exception {
        test("dir", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_bgsound() throws Exception {
        test("dir", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_br() throws Exception {
        test("dir", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _dir_command() throws Exception {
        test("dir", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_embed() throws Exception {
        test("dir", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_hr() throws Exception {
        test("dir", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_image() throws Exception {
        test("dir", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_img() throws Exception {
        test("dir", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_input() throws Exception {
        test("dir", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _dir_isindex() throws Exception {
        test("dir", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_keygen() throws Exception {
        test("dir", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_link() throws Exception {
        test("dir", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_meta() throws Exception {
        test("dir", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_param() throws Exception {
        test("dir", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_script() throws Exception {
        test("dir", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_source() throws Exception {
        test("dir", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_track() throws Exception {
        test("dir", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dir_wbr() throws Exception {
        test("dir", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_area() throws Exception {
        test("div", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_base() throws Exception {
        test("div", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_basefont() throws Exception {
        test("div", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_bgsound() throws Exception {
        test("div", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_br() throws Exception {
        test("div", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _div_command() throws Exception {
        test("div", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_embed() throws Exception {
        test("div", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_hr() throws Exception {
        test("div", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_image() throws Exception {
        test("div", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_img() throws Exception {
        test("div", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_input() throws Exception {
        test("div", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _div_isindex() throws Exception {
        test("div", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_keygen() throws Exception {
        test("div", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_link() throws Exception {
        test("div", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_meta() throws Exception {
        test("div", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_param() throws Exception {
        test("div", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_script() throws Exception {
        test("div", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_source() throws Exception {
        test("div", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_track() throws Exception {
        test("div", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _div_wbr() throws Exception {
        test("div", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_area() throws Exception {
        test("dl", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_base() throws Exception {
        test("dl", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_basefont() throws Exception {
        test("dl", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_bgsound() throws Exception {
        test("dl", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_br() throws Exception {
        test("dl", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _dl_command() throws Exception {
        test("dl", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_embed() throws Exception {
        test("dl", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_hr() throws Exception {
        test("dl", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_image() throws Exception {
        test("dl", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_img() throws Exception {
        test("dl", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_input() throws Exception {
        test("dl", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _dl_isindex() throws Exception {
        test("dl", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_keygen() throws Exception {
        test("dl", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_link() throws Exception {
        test("dl", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_meta() throws Exception {
        test("dl", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_param() throws Exception {
        test("dl", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_script() throws Exception {
        test("dl", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_source() throws Exception {
        test("dl", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_track() throws Exception {
        test("dl", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dl_wbr() throws Exception {
        test("dl", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_area() throws Exception {
        test("dt", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_base() throws Exception {
        test("dt", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_basefont() throws Exception {
        test("dt", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_bgsound() throws Exception {
        test("dt", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_br() throws Exception {
        test("dt", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _dt_command() throws Exception {
        test("dt", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _dt_dd() throws Exception {
        test("dt", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _dt_dt() throws Exception {
        test("dt", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_embed() throws Exception {
        test("dt", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_hr() throws Exception {
        test("dt", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_image() throws Exception {
        test("dt", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_img() throws Exception {
        test("dt", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_input() throws Exception {
        test("dt", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _dt_isindex() throws Exception {
        test("dt", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_keygen() throws Exception {
        test("dt", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_link() throws Exception {
        test("dt", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_meta() throws Exception {
        test("dt", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_param() throws Exception {
        test("dt", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_script() throws Exception {
        test("dt", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_source() throws Exception {
        test("dt", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_track() throws Exception {
        test("dt", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _dt_wbr() throws Exception {
        test("dt", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_area() throws Exception {
        test("em", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_base() throws Exception {
        test("em", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_basefont() throws Exception {
        test("em", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_bgsound() throws Exception {
        test("em", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_br() throws Exception {
        test("em", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _em_command() throws Exception {
        test("em", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_embed() throws Exception {
        test("em", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_hr() throws Exception {
        test("em", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_image() throws Exception {
        test("em", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_img() throws Exception {
        test("em", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_input() throws Exception {
        test("em", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _em_isindex() throws Exception {
        test("em", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_keygen() throws Exception {
        test("em", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_link() throws Exception {
        test("em", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_meta() throws Exception {
        test("em", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_param() throws Exception {
        test("em", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_script() throws Exception {
        test("em", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_source() throws Exception {
        test("em", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_track() throws Exception {
        test("em", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _em_wbr() throws Exception {
        test("em", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_a() throws Exception {
        test("embed", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_abbr() throws Exception {
        test("embed", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_acronym() throws Exception {
        test("embed", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_address() throws Exception {
        test("embed", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_applet() throws Exception {
        test("embed", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_area() throws Exception {
        test("embed", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_article() throws Exception {
        test("embed", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_aside() throws Exception {
        test("embed", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_audio() throws Exception {
        test("embed", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_b() throws Exception {
        test("embed", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_base() throws Exception {
        test("embed", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_basefont() throws Exception {
        test("embed", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_bdi() throws Exception {
        test("embed", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_bdo() throws Exception {
        test("embed", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_bgsound() throws Exception {
        test("embed", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_big() throws Exception {
        test("embed", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_blink() throws Exception {
        test("embed", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_blockquote() throws Exception {
        test("embed", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_body() throws Exception {
        test("embed", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_br() throws Exception {
        test("embed", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_button() throws Exception {
        test("embed", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_canvas() throws Exception {
        test("embed", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_caption() throws Exception {
        test("embed", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_center() throws Exception {
        test("embed", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_cite() throws Exception {
        test("embed", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_code() throws Exception {
        test("embed", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_col() throws Exception {
        test("embed", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_colgroup() throws Exception {
        test("embed", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_command() throws Exception {
        test("embed", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_content() throws Exception {
        test("embed", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_data() throws Exception {
        test("embed", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_datalist() throws Exception {
        test("embed", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_dd() throws Exception {
        test("embed", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_del() throws Exception {
        test("embed", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_details() throws Exception {
        test("embed", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_dfn() throws Exception {
        test("embed", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_dialog() throws Exception {
        test("embed", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_dir() throws Exception {
        test("embed", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_div() throws Exception {
        test("embed", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_dl() throws Exception {
        test("embed", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_dt() throws Exception {
        test("embed", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_em() throws Exception {
        test("embed", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_embed() throws Exception {
        test("embed", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_fieldset() throws Exception {
        test("embed", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_figcaption() throws Exception {
        test("embed", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_figure() throws Exception {
        test("embed", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_font() throws Exception {
        test("embed", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_footer() throws Exception {
        test("embed", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_form() throws Exception {
        test("embed", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_frame() throws Exception {
        test("embed", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_frameset() throws Exception {
        test("embed", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_h1() throws Exception {
        test("embed", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_h2() throws Exception {
        test("embed", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_h3() throws Exception {
        test("embed", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_h4() throws Exception {
        test("embed", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_h5() throws Exception {
        test("embed", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_h6() throws Exception {
        test("embed", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_head() throws Exception {
        test("embed", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_header() throws Exception {
        test("embed", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_hr() throws Exception {
        test("embed", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_html() throws Exception {
        test("embed", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_i() throws Exception {
        test("embed", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_iframe() throws Exception {
        test("embed", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_image() throws Exception {
        test("embed", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_img() throws Exception {
        test("embed", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_input() throws Exception {
        test("embed", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_ins() throws Exception {
        test("embed", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_isindex() throws Exception {
        test("embed", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_kbd() throws Exception {
        test("embed", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_keygen() throws Exception {
        test("embed", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_label() throws Exception {
        test("embed", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_layer() throws Exception {
        test("embed", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_legend() throws Exception {
        test("embed", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_li() throws Exception {
        test("embed", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_link() throws Exception {
        test("embed", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_listing() throws Exception {
        test("embed", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_main() throws Exception {
        test("embed", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_map() throws Exception {
        test("embed", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_mark() throws Exception {
        test("embed", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_marquee() throws Exception {
        test("embed", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_menu() throws Exception {
        test("embed", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_menuitem() throws Exception {
        test("embed", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_meta() throws Exception {
        test("embed", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_meter() throws Exception {
        test("embed", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_multicol() throws Exception {
        test("embed", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_nav() throws Exception {
        test("embed", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_nextid() throws Exception {
        test("embed", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_nobr() throws Exception {
        test("embed", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_noembed() throws Exception {
        test("embed", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_noframes() throws Exception {
        test("embed", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_nolayer() throws Exception {
        test("embed", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_noscript() throws Exception {
        test("embed", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_object() throws Exception {
        test("embed", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_ol() throws Exception {
        test("embed", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_optgroup() throws Exception {
        test("embed", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_option() throws Exception {
        test("embed", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_output() throws Exception {
        test("embed", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_p() throws Exception {
        test("embed", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_param() throws Exception {
        test("embed", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_picture() throws Exception {
        test("embed", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_plaintext() throws Exception {
        test("embed", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_pre() throws Exception {
        test("embed", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_progress() throws Exception {
        test("embed", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_q() throws Exception {
        test("embed", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_rp() throws Exception {
        test("embed", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_rt() throws Exception {
        test("embed", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_ruby() throws Exception {
        test("embed", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_s() throws Exception {
        test("embed", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_samp() throws Exception {
        test("embed", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_script() throws Exception {
        test("embed", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_section() throws Exception {
        test("embed", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_select() throws Exception {
        test("embed", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_slot() throws Exception {
        test("embed", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_small() throws Exception {
        test("embed", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_source() throws Exception {
        test("embed", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_span() throws Exception {
        test("embed", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_strike() throws Exception {
        test("embed", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_strong() throws Exception {
        test("embed", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_style() throws Exception {
        test("embed", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_sub() throws Exception {
        test("embed", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_summary() throws Exception {
        test("embed", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_sup() throws Exception {
        test("embed", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_svg() throws Exception {
        test("embed", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_table() throws Exception {
        test("embed", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_tbody() throws Exception {
        test("embed", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_td() throws Exception {
        test("embed", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_template() throws Exception {
        test("embed", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_textarea() throws Exception {
        test("embed", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_tfoot() throws Exception {
        test("embed", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_th() throws Exception {
        test("embed", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_thead() throws Exception {
        test("embed", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_time() throws Exception {
        test("embed", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_title() throws Exception {
        test("embed", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_tr() throws Exception {
        test("embed", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_track() throws Exception {
        test("embed", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_tt() throws Exception {
        test("embed", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_u() throws Exception {
        test("embed", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_ul() throws Exception {
        test("embed", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_var() throws Exception {
        test("embed", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_video() throws Exception {
        test("embed", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_wbr() throws Exception {
        test("embed", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _embed_xmp() throws Exception {
        test("embed", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_area() throws Exception {
        test("fieldset", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_base() throws Exception {
        test("fieldset", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_basefont() throws Exception {
        test("fieldset", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_bgsound() throws Exception {
        test("fieldset", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_br() throws Exception {
        test("fieldset", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _fieldset_command() throws Exception {
        test("fieldset", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_embed() throws Exception {
        test("fieldset", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_hr() throws Exception {
        test("fieldset", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_image() throws Exception {
        test("fieldset", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_img() throws Exception {
        test("fieldset", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_input() throws Exception {
        test("fieldset", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _fieldset_isindex() throws Exception {
        test("fieldset", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_keygen() throws Exception {
        test("fieldset", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_link() throws Exception {
        test("fieldset", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_meta() throws Exception {
        test("fieldset", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_param() throws Exception {
        test("fieldset", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_script() throws Exception {
        test("fieldset", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_source() throws Exception {
        test("fieldset", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_track() throws Exception {
        test("fieldset", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _fieldset_wbr() throws Exception {
        test("fieldset", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_area() throws Exception {
        test("figcaption", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_base() throws Exception {
        test("figcaption", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_basefont() throws Exception {
        test("figcaption", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_bgsound() throws Exception {
        test("figcaption", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_br() throws Exception {
        test("figcaption", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _figcaption_command() throws Exception {
        test("figcaption", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_embed() throws Exception {
        test("figcaption", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_hr() throws Exception {
        test("figcaption", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_image() throws Exception {
        test("figcaption", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_img() throws Exception {
        test("figcaption", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_input() throws Exception {
        test("figcaption", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _figcaption_isindex() throws Exception {
        test("figcaption", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_keygen() throws Exception {
        test("figcaption", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_link() throws Exception {
        test("figcaption", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_meta() throws Exception {
        test("figcaption", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_param() throws Exception {
        test("figcaption", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_script() throws Exception {
        test("figcaption", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_source() throws Exception {
        test("figcaption", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_track() throws Exception {
        test("figcaption", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figcaption_wbr() throws Exception {
        test("figcaption", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_area() throws Exception {
        test("figure", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_base() throws Exception {
        test("figure", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_basefont() throws Exception {
        test("figure", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_bgsound() throws Exception {
        test("figure", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_br() throws Exception {
        test("figure", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _figure_command() throws Exception {
        test("figure", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_embed() throws Exception {
        test("figure", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_hr() throws Exception {
        test("figure", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_image() throws Exception {
        test("figure", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_img() throws Exception {
        test("figure", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_input() throws Exception {
        test("figure", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _figure_isindex() throws Exception {
        test("figure", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_keygen() throws Exception {
        test("figure", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_link() throws Exception {
        test("figure", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_meta() throws Exception {
        test("figure", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_param() throws Exception {
        test("figure", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_script() throws Exception {
        test("figure", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_source() throws Exception {
        test("figure", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_track() throws Exception {
        test("figure", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _figure_wbr() throws Exception {
        test("figure", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_area() throws Exception {
        test("font", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_base() throws Exception {
        test("font", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_basefont() throws Exception {
        test("font", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_bgsound() throws Exception {
        test("font", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_br() throws Exception {
        test("font", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _font_command() throws Exception {
        test("font", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_embed() throws Exception {
        test("font", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_hr() throws Exception {
        test("font", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_image() throws Exception {
        test("font", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_img() throws Exception {
        test("font", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_input() throws Exception {
        test("font", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _font_isindex() throws Exception {
        test("font", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_keygen() throws Exception {
        test("font", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_link() throws Exception {
        test("font", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_meta() throws Exception {
        test("font", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_param() throws Exception {
        test("font", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_script() throws Exception {
        test("font", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_source() throws Exception {
        test("font", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_track() throws Exception {
        test("font", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _font_wbr() throws Exception {
        test("font", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_area() throws Exception {
        test("footer", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_base() throws Exception {
        test("footer", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_basefont() throws Exception {
        test("footer", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_bgsound() throws Exception {
        test("footer", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_br() throws Exception {
        test("footer", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _footer_command() throws Exception {
        test("footer", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_embed() throws Exception {
        test("footer", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_hr() throws Exception {
        test("footer", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_image() throws Exception {
        test("footer", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_img() throws Exception {
        test("footer", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_input() throws Exception {
        test("footer", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _footer_isindex() throws Exception {
        test("footer", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_keygen() throws Exception {
        test("footer", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_link() throws Exception {
        test("footer", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_meta() throws Exception {
        test("footer", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_param() throws Exception {
        test("footer", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_script() throws Exception {
        test("footer", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_source() throws Exception {
        test("footer", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_track() throws Exception {
        test("footer", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _footer_wbr() throws Exception {
        test("footer", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_area() throws Exception {
        test("form", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_base() throws Exception {
        test("form", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_basefont() throws Exception {
        test("form", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_bgsound() throws Exception {
        test("form", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_br() throws Exception {
        test("form", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _form_command() throws Exception {
        test("form", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_embed() throws Exception {
        test("form", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_hr() throws Exception {
        test("form", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_image() throws Exception {
        test("form", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_img() throws Exception {
        test("form", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_input() throws Exception {
        test("form", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_keygen() throws Exception {
        test("form", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_link() throws Exception {
        test("form", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_meta() throws Exception {
        test("form", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_param() throws Exception {
        test("form", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_script() throws Exception {
        test("form", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_source() throws Exception {
        test("form", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_track() throws Exception {
        test("form", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _form_wbr() throws Exception {
        test("form", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_a() throws Exception {
        test("frame", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_abbr() throws Exception {
        test("frame", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_acronym() throws Exception {
        test("frame", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_address() throws Exception {
        test("frame", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_applet() throws Exception {
        test("frame", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_area() throws Exception {
        test("frame", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_article() throws Exception {
        test("frame", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_aside() throws Exception {
        test("frame", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_audio() throws Exception {
        test("frame", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_b() throws Exception {
        test("frame", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_base() throws Exception {
        test("frame", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_basefont() throws Exception {
        test("frame", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_bdi() throws Exception {
        test("frame", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_bdo() throws Exception {
        test("frame", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_bgsound() throws Exception {
        test("frame", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_big() throws Exception {
        test("frame", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_blink() throws Exception {
        test("frame", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_blockquote() throws Exception {
        test("frame", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_body() throws Exception {
        test("frame", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_br() throws Exception {
        test("frame", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_button() throws Exception {
        test("frame", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_canvas() throws Exception {
        test("frame", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_caption() throws Exception {
        test("frame", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_center() throws Exception {
        test("frame", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_cite() throws Exception {
        test("frame", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_code() throws Exception {
        test("frame", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_col() throws Exception {
        test("frame", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_colgroup() throws Exception {
        test("frame", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_command() throws Exception {
        test("frame", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_content() throws Exception {
        test("frame", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_data() throws Exception {
        test("frame", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_datalist() throws Exception {
        test("frame", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_dd() throws Exception {
        test("frame", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_del() throws Exception {
        test("frame", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_details() throws Exception {
        test("frame", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_dfn() throws Exception {
        test("frame", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_dialog() throws Exception {
        test("frame", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_dir() throws Exception {
        test("frame", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_div() throws Exception {
        test("frame", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_dl() throws Exception {
        test("frame", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_dt() throws Exception {
        test("frame", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_em() throws Exception {
        test("frame", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_embed() throws Exception {
        test("frame", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_fieldset() throws Exception {
        test("frame", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_figcaption() throws Exception {
        test("frame", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_figure() throws Exception {
        test("frame", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_font() throws Exception {
        test("frame", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_footer() throws Exception {
        test("frame", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_form() throws Exception {
        test("frame", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_frame() throws Exception {
        test("frame", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_frameset() throws Exception {
        test("frame", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_h1() throws Exception {
        test("frame", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_h2() throws Exception {
        test("frame", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_h3() throws Exception {
        test("frame", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_h4() throws Exception {
        test("frame", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_h5() throws Exception {
        test("frame", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_h6() throws Exception {
        test("frame", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_head() throws Exception {
        test("frame", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_header() throws Exception {
        test("frame", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_hr() throws Exception {
        test("frame", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_html() throws Exception {
        test("frame", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_i() throws Exception {
        test("frame", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_iframe() throws Exception {
        test("frame", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_image() throws Exception {
        test("frame", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_img() throws Exception {
        test("frame", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_input() throws Exception {
        test("frame", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_ins() throws Exception {
        test("frame", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_isindex() throws Exception {
        test("frame", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_kbd() throws Exception {
        test("frame", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_keygen() throws Exception {
        test("frame", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_label() throws Exception {
        test("frame", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_layer() throws Exception {
        test("frame", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_legend() throws Exception {
        test("frame", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_li() throws Exception {
        test("frame", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_link() throws Exception {
        test("frame", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_listing() throws Exception {
        test("frame", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_main() throws Exception {
        test("frame", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_map() throws Exception {
        test("frame", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_mark() throws Exception {
        test("frame", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_marquee() throws Exception {
        test("frame", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_menu() throws Exception {
        test("frame", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_menuitem() throws Exception {
        test("frame", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_meta() throws Exception {
        test("frame", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_meter() throws Exception {
        test("frame", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_multicol() throws Exception {
        test("frame", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_nav() throws Exception {
        test("frame", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_nextid() throws Exception {
        test("frame", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_nobr() throws Exception {
        test("frame", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_noembed() throws Exception {
        test("frame", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_noframes() throws Exception {
        test("frame", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_nolayer() throws Exception {
        test("frame", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_noscript() throws Exception {
        test("frame", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_object() throws Exception {
        test("frame", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_ol() throws Exception {
        test("frame", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_optgroup() throws Exception {
        test("frame", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_option() throws Exception {
        test("frame", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_output() throws Exception {
        test("frame", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_p() throws Exception {
        test("frame", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_param() throws Exception {
        test("frame", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_picture() throws Exception {
        test("frame", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_plaintext() throws Exception {
        test("frame", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_pre() throws Exception {
        test("frame", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_progress() throws Exception {
        test("frame", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_q() throws Exception {
        test("frame", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_rp() throws Exception {
        test("frame", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_rt() throws Exception {
        test("frame", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_ruby() throws Exception {
        test("frame", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_s() throws Exception {
        test("frame", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_samp() throws Exception {
        test("frame", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_script() throws Exception {
        test("frame", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_section() throws Exception {
        test("frame", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_select() throws Exception {
        test("frame", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_slot() throws Exception {
        test("frame", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_small() throws Exception {
        test("frame", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_source() throws Exception {
        test("frame", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_span() throws Exception {
        test("frame", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_strike() throws Exception {
        test("frame", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_strong() throws Exception {
        test("frame", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_style() throws Exception {
        test("frame", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_sub() throws Exception {
        test("frame", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_summary() throws Exception {
        test("frame", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_sup() throws Exception {
        test("frame", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_svg() throws Exception {
        test("frame", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_table() throws Exception {
        test("frame", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_tbody() throws Exception {
        test("frame", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_td() throws Exception {
        test("frame", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_template() throws Exception {
        test("frame", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_textarea() throws Exception {
        test("frame", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_tfoot() throws Exception {
        test("frame", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_th() throws Exception {
        test("frame", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_thead() throws Exception {
        test("frame", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_time() throws Exception {
        test("frame", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_title() throws Exception {
        test("frame", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_tr() throws Exception {
        test("frame", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_track() throws Exception {
        test("frame", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_tt() throws Exception {
        test("frame", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_u() throws Exception {
        test("frame", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_ul() throws Exception {
        test("frame", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_var() throws Exception {
        test("frame", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_video() throws Exception {
        test("frame", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_wbr() throws Exception {
        test("frame", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frame_xmp() throws Exception {
        test("frame", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_a() throws Exception {
        test("frameset", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_abbr() throws Exception {
        test("frameset", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_acronym() throws Exception {
        test("frameset", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_address() throws Exception {
        test("frameset", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_applet() throws Exception {
        test("frameset", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_area() throws Exception {
        test("frameset", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_article() throws Exception {
        test("frameset", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_aside() throws Exception {
        test("frameset", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_audio() throws Exception {
        test("frameset", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_b() throws Exception {
        test("frameset", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_base() throws Exception {
        test("frameset", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_basefont() throws Exception {
        test("frameset", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_bdi() throws Exception {
        test("frameset", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_bdo() throws Exception {
        test("frameset", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_bgsound() throws Exception {
        test("frameset", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_big() throws Exception {
        test("frameset", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_blink() throws Exception {
        test("frameset", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_blockquote() throws Exception {
        test("frameset", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_body() throws Exception {
        test("frameset", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_br() throws Exception {
        test("frameset", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_button() throws Exception {
        test("frameset", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_canvas() throws Exception {
        test("frameset", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_caption() throws Exception {
        test("frameset", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_center() throws Exception {
        test("frameset", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_cite() throws Exception {
        test("frameset", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_code() throws Exception {
        test("frameset", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_col() throws Exception {
        test("frameset", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_colgroup() throws Exception {
        test("frameset", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_command() throws Exception {
        test("frameset", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_content() throws Exception {
        test("frameset", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_data() throws Exception {
        test("frameset", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_datalist() throws Exception {
        test("frameset", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_dd() throws Exception {
        test("frameset", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_del() throws Exception {
        test("frameset", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_details() throws Exception {
        test("frameset", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_dfn() throws Exception {
        test("frameset", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_dialog() throws Exception {
        test("frameset", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_dir() throws Exception {
        test("frameset", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_div() throws Exception {
        test("frameset", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_dl() throws Exception {
        test("frameset", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_dt() throws Exception {
        test("frameset", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_em() throws Exception {
        test("frameset", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_embed() throws Exception {
        test("frameset", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_fieldset() throws Exception {
        test("frameset", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_figcaption() throws Exception {
        test("frameset", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_figure() throws Exception {
        test("frameset", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_font() throws Exception {
        test("frameset", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_footer() throws Exception {
        test("frameset", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_form() throws Exception {
        test("frameset", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_frame() throws Exception {
        test("frameset", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_frameset() throws Exception {
        test("frameset", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_h1() throws Exception {
        test("frameset", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_h2() throws Exception {
        test("frameset", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_h3() throws Exception {
        test("frameset", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_h4() throws Exception {
        test("frameset", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_h5() throws Exception {
        test("frameset", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_h6() throws Exception {
        test("frameset", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_head() throws Exception {
        test("frameset", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_header() throws Exception {
        test("frameset", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_hr() throws Exception {
        test("frameset", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_html() throws Exception {
        test("frameset", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_i() throws Exception {
        test("frameset", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_iframe() throws Exception {
        test("frameset", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_image() throws Exception {
        test("frameset", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_img() throws Exception {
        test("frameset", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_input() throws Exception {
        test("frameset", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_ins() throws Exception {
        test("frameset", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_isindex() throws Exception {
        test("frameset", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_kbd() throws Exception {
        test("frameset", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_keygen() throws Exception {
        test("frameset", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_label() throws Exception {
        test("frameset", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_layer() throws Exception {
        test("frameset", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_legend() throws Exception {
        test("frameset", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_li() throws Exception {
        test("frameset", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_link() throws Exception {
        test("frameset", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_listing() throws Exception {
        test("frameset", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_main() throws Exception {
        test("frameset", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_map() throws Exception {
        test("frameset", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_mark() throws Exception {
        test("frameset", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_marquee() throws Exception {
        test("frameset", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_menu() throws Exception {
        test("frameset", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_menuitem() throws Exception {
        test("frameset", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_meta() throws Exception {
        test("frameset", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_meter() throws Exception {
        test("frameset", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_multicol() throws Exception {
        test("frameset", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_nav() throws Exception {
        test("frameset", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_nextid() throws Exception {
        test("frameset", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_nobr() throws Exception {
        test("frameset", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_noembed() throws Exception {
        test("frameset", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_noframes() throws Exception {
        test("frameset", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_nolayer() throws Exception {
        test("frameset", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_noscript() throws Exception {
        test("frameset", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_object() throws Exception {
        test("frameset", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_ol() throws Exception {
        test("frameset", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_optgroup() throws Exception {
        test("frameset", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_option() throws Exception {
        test("frameset", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_output() throws Exception {
        test("frameset", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_p() throws Exception {
        test("frameset", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_param() throws Exception {
        test("frameset", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_picture() throws Exception {
        test("frameset", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_plaintext() throws Exception {
        test("frameset", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_pre() throws Exception {
        test("frameset", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_progress() throws Exception {
        test("frameset", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_q() throws Exception {
        test("frameset", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_rp() throws Exception {
        test("frameset", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_rt() throws Exception {
        test("frameset", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_ruby() throws Exception {
        test("frameset", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_s() throws Exception {
        test("frameset", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_samp() throws Exception {
        test("frameset", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_script() throws Exception {
        test("frameset", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_section() throws Exception {
        test("frameset", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_select() throws Exception {
        test("frameset", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_slot() throws Exception {
        test("frameset", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_small() throws Exception {
        test("frameset", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_source() throws Exception {
        test("frameset", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_span() throws Exception {
        test("frameset", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_strike() throws Exception {
        test("frameset", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_strong() throws Exception {
        test("frameset", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_style() throws Exception {
        test("frameset", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_sub() throws Exception {
        test("frameset", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_summary() throws Exception {
        test("frameset", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_sup() throws Exception {
        test("frameset", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_svg() throws Exception {
        test("frameset", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_table() throws Exception {
        test("frameset", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_tbody() throws Exception {
        test("frameset", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_td() throws Exception {
        test("frameset", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_template() throws Exception {
        test("frameset", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_textarea() throws Exception {
        test("frameset", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_tfoot() throws Exception {
        test("frameset", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_th() throws Exception {
        test("frameset", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_thead() throws Exception {
        test("frameset", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_time() throws Exception {
        test("frameset", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_title() throws Exception {
        test("frameset", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_tr() throws Exception {
        test("frameset", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_track() throws Exception {
        test("frameset", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_tt() throws Exception {
        test("frameset", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_u() throws Exception {
        test("frameset", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_ul() throws Exception {
        test("frameset", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_var() throws Exception {
        test("frameset", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_video() throws Exception {
        test("frameset", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_wbr() throws Exception {
        test("frameset", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _frameset_xmp() throws Exception {
        test("frameset", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_area() throws Exception {
        test("h1", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_base() throws Exception {
        test("h1", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_basefont() throws Exception {
        test("h1", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_bgsound() throws Exception {
        test("h1", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_br() throws Exception {
        test("h1", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _h1_command() throws Exception {
        test("h1", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_embed() throws Exception {
        test("h1", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h1_h1() throws Exception {
        test("h1", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h1_h2() throws Exception {
        test("h1", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h1_h3() throws Exception {
        test("h1", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h1_h4() throws Exception {
        test("h1", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h1_h5() throws Exception {
        test("h1", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h1_h6() throws Exception {
        test("h1", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_hr() throws Exception {
        test("h1", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_image() throws Exception {
        test("h1", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_img() throws Exception {
        test("h1", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_input() throws Exception {
        test("h1", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _h1_isindex() throws Exception {
        test("h1", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_keygen() throws Exception {
        test("h1", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_link() throws Exception {
        test("h1", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_meta() throws Exception {
        test("h1", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_param() throws Exception {
        test("h1", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_script() throws Exception {
        test("h1", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_source() throws Exception {
        test("h1", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_track() throws Exception {
        test("h1", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h1_wbr() throws Exception {
        test("h1", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_area() throws Exception {
        test("h2", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_base() throws Exception {
        test("h2", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_basefont() throws Exception {
        test("h2", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_bgsound() throws Exception {
        test("h2", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_br() throws Exception {
        test("h2", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _h2_command() throws Exception {
        test("h2", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_embed() throws Exception {
        test("h2", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h2_h1() throws Exception {
        test("h2", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h2_h2() throws Exception {
        test("h2", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h2_h3() throws Exception {
        test("h2", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h2_h4() throws Exception {
        test("h2", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h2_h5() throws Exception {
        test("h2", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h2_h6() throws Exception {
        test("h2", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_hr() throws Exception {
        test("h2", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_image() throws Exception {
        test("h2", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_img() throws Exception {
        test("h2", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_input() throws Exception {
        test("h2", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _h2_isindex() throws Exception {
        test("h2", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_keygen() throws Exception {
        test("h2", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_link() throws Exception {
        test("h2", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_meta() throws Exception {
        test("h2", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_param() throws Exception {
        test("h2", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_script() throws Exception {
        test("h2", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_source() throws Exception {
        test("h2", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_track() throws Exception {
        test("h2", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h2_wbr() throws Exception {
        test("h2", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_area() throws Exception {
        test("h3", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_base() throws Exception {
        test("h3", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_basefont() throws Exception {
        test("h3", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_bgsound() throws Exception {
        test("h3", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_br() throws Exception {
        test("h3", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _h3_command() throws Exception {
        test("h3", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_embed() throws Exception {
        test("h3", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h3_h1() throws Exception {
        test("h3", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h3_h2() throws Exception {
        test("h3", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h3_h3() throws Exception {
        test("h3", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h3_h4() throws Exception {
        test("h3", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h3_h5() throws Exception {
        test("h3", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h3_h6() throws Exception {
        test("h3", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_hr() throws Exception {
        test("h3", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_image() throws Exception {
        test("h3", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_img() throws Exception {
        test("h3", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_input() throws Exception {
        test("h3", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _h3_isindex() throws Exception {
        test("h3", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_keygen() throws Exception {
        test("h3", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_link() throws Exception {
        test("h3", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_meta() throws Exception {
        test("h3", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_param() throws Exception {
        test("h3", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_script() throws Exception {
        test("h3", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_source() throws Exception {
        test("h3", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_track() throws Exception {
        test("h3", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h3_wbr() throws Exception {
        test("h3", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_area() throws Exception {
        test("h4", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_base() throws Exception {
        test("h4", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_basefont() throws Exception {
        test("h4", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_bgsound() throws Exception {
        test("h4", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_br() throws Exception {
        test("h4", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _h4_command() throws Exception {
        test("h4", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_embed() throws Exception {
        test("h4", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h4_h1() throws Exception {
        test("h4", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h4_h2() throws Exception {
        test("h4", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h4_h3() throws Exception {
        test("h4", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h4_h4() throws Exception {
        test("h4", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h4_h5() throws Exception {
        test("h4", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h4_h6() throws Exception {
        test("h4", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_hr() throws Exception {
        test("h4", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_image() throws Exception {
        test("h4", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_img() throws Exception {
        test("h4", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_input() throws Exception {
        test("h4", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _h4_isindex() throws Exception {
        test("h4", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_keygen() throws Exception {
        test("h4", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_link() throws Exception {
        test("h4", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_meta() throws Exception {
        test("h4", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_param() throws Exception {
        test("h4", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_script() throws Exception {
        test("h4", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_source() throws Exception {
        test("h4", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_track() throws Exception {
        test("h4", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h4_wbr() throws Exception {
        test("h4", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_area() throws Exception {
        test("h5", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_base() throws Exception {
        test("h5", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_basefont() throws Exception {
        test("h5", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_bgsound() throws Exception {
        test("h5", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_br() throws Exception {
        test("h5", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _h5_command() throws Exception {
        test("h5", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_embed() throws Exception {
        test("h5", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h5_h1() throws Exception {
        test("h5", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h5_h2() throws Exception {
        test("h5", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h5_h3() throws Exception {
        test("h5", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h5_h4() throws Exception {
        test("h5", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h5_h5() throws Exception {
        test("h5", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h5_h6() throws Exception {
        test("h5", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_hr() throws Exception {
        test("h5", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_image() throws Exception {
        test("h5", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_img() throws Exception {
        test("h5", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_input() throws Exception {
        test("h5", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _h5_isindex() throws Exception {
        test("h5", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_keygen() throws Exception {
        test("h5", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_link() throws Exception {
        test("h5", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_meta() throws Exception {
        test("h5", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_param() throws Exception {
        test("h5", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_script() throws Exception {
        test("h5", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_source() throws Exception {
        test("h5", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_track() throws Exception {
        test("h5", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h5_wbr() throws Exception {
        test("h5", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_area() throws Exception {
        test("h6", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_base() throws Exception {
        test("h6", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_basefont() throws Exception {
        test("h6", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_bgsound() throws Exception {
        test("h6", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_br() throws Exception {
        test("h6", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _h6_command() throws Exception {
        test("h6", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_embed() throws Exception {
        test("h6", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h6_h1() throws Exception {
        test("h6", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h6_h2() throws Exception {
        test("h6", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h6_h3() throws Exception {
        test("h6", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h6_h4() throws Exception {
        test("h6", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h6_h5() throws Exception {
        test("h6", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _h6_h6() throws Exception {
        test("h6", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_hr() throws Exception {
        test("h6", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_image() throws Exception {
        test("h6", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_img() throws Exception {
        test("h6", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_input() throws Exception {
        test("h6", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _h6_isindex() throws Exception {
        test("h6", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_keygen() throws Exception {
        test("h6", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_link() throws Exception {
        test("h6", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_meta() throws Exception {
        test("h6", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_param() throws Exception {
        test("h6", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_script() throws Exception {
        test("h6", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_source() throws Exception {
        test("h6", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_track() throws Exception {
        test("h6", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _h6_wbr() throws Exception {
        test("h6", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_a() throws Exception {
        test("head", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_abbr() throws Exception {
        test("head", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_acronym() throws Exception {
        test("head", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_address() throws Exception {
        test("head", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_applet() throws Exception {
        test("head", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_area() throws Exception {
        test("head", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_article() throws Exception {
        test("head", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_aside() throws Exception {
        test("head", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_audio() throws Exception {
        test("head", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_b() throws Exception {
        test("head", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_base() throws Exception {
        test("head", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_basefont() throws Exception {
        test("head", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_bdi() throws Exception {
        test("head", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_bdo() throws Exception {
        test("head", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_bgsound() throws Exception {
        test("head", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_big() throws Exception {
        test("head", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_blink() throws Exception {
        test("head", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_blockquote() throws Exception {
        test("head", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_body() throws Exception {
        test("head", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_br() throws Exception {
        test("head", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_button() throws Exception {
        test("head", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_canvas() throws Exception {
        test("head", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_caption() throws Exception {
        test("head", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_center() throws Exception {
        test("head", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_cite() throws Exception {
        test("head", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_code() throws Exception {
        test("head", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_col() throws Exception {
        test("head", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_colgroup() throws Exception {
        test("head", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_command() throws Exception {
        test("head", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_content() throws Exception {
        test("head", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_data() throws Exception {
        test("head", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_datalist() throws Exception {
        test("head", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_dd() throws Exception {
        test("head", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_del() throws Exception {
        test("head", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_details() throws Exception {
        test("head", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_dfn() throws Exception {
        test("head", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_dialog() throws Exception {
        test("head", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_dir() throws Exception {
        test("head", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_div() throws Exception {
        test("head", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_dl() throws Exception {
        test("head", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_dt() throws Exception {
        test("head", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_em() throws Exception {
        test("head", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_embed() throws Exception {
        test("head", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_fieldset() throws Exception {
        test("head", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_figcaption() throws Exception {
        test("head", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_figure() throws Exception {
        test("head", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_font() throws Exception {
        test("head", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_footer() throws Exception {
        test("head", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_form() throws Exception {
        test("head", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_frame() throws Exception {
        test("head", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_frameset() throws Exception {
        test("head", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_h1() throws Exception {
        test("head", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_h2() throws Exception {
        test("head", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_h3() throws Exception {
        test("head", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_h4() throws Exception {
        test("head", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_h5() throws Exception {
        test("head", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_h6() throws Exception {
        test("head", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_head() throws Exception {
        test("head", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_header() throws Exception {
        test("head", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_hr() throws Exception {
        test("head", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_html() throws Exception {
        test("head", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_i() throws Exception {
        test("head", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_iframe() throws Exception {
        test("head", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_image() throws Exception {
        test("head", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_img() throws Exception {
        test("head", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_input() throws Exception {
        test("head", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_ins() throws Exception {
        test("head", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_isindex() throws Exception {
        test("head", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_kbd() throws Exception {
        test("head", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_keygen() throws Exception {
        test("head", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_label() throws Exception {
        test("head", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_layer() throws Exception {
        test("head", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_legend() throws Exception {
        test("head", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_li() throws Exception {
        test("head", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_link() throws Exception {
        test("head", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_listing() throws Exception {
        test("head", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_main() throws Exception {
        test("head", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_map() throws Exception {
        test("head", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_mark() throws Exception {
        test("head", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_marquee() throws Exception {
        test("head", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_menu() throws Exception {
        test("head", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_menuitem() throws Exception {
        test("head", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_meta() throws Exception {
        test("head", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_meter() throws Exception {
        test("head", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_multicol() throws Exception {
        test("head", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_nav() throws Exception {
        test("head", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_nextid() throws Exception {
        test("head", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_nobr() throws Exception {
        test("head", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_noembed() throws Exception {
        test("head", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_noframes() throws Exception {
        test("head", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_nolayer() throws Exception {
        test("head", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_noscript() throws Exception {
        test("head", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_object() throws Exception {
        test("head", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_ol() throws Exception {
        test("head", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_optgroup() throws Exception {
        test("head", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_option() throws Exception {
        test("head", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_output() throws Exception {
        test("head", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_p() throws Exception {
        test("head", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_param() throws Exception {
        test("head", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_picture() throws Exception {
        test("head", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_plaintext() throws Exception {
        test("head", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_pre() throws Exception {
        test("head", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_progress() throws Exception {
        test("head", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_q() throws Exception {
        test("head", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_rp() throws Exception {
        test("head", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_rt() throws Exception {
        test("head", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_ruby() throws Exception {
        test("head", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_s() throws Exception {
        test("head", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_samp() throws Exception {
        test("head", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_script() throws Exception {
        test("head", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_section() throws Exception {
        test("head", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_select() throws Exception {
        test("head", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_slot() throws Exception {
        test("head", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_small() throws Exception {
        test("head", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_source() throws Exception {
        test("head", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_span() throws Exception {
        test("head", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_strike() throws Exception {
        test("head", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_strong() throws Exception {
        test("head", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_style() throws Exception {
        test("head", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_sub() throws Exception {
        test("head", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_summary() throws Exception {
        test("head", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_sup() throws Exception {
        test("head", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_svg() throws Exception {
        test("head", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_table() throws Exception {
        test("head", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_tbody() throws Exception {
        test("head", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_td() throws Exception {
        test("head", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_template() throws Exception {
        test("head", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_textarea() throws Exception {
        test("head", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_tfoot() throws Exception {
        test("head", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_th() throws Exception {
        test("head", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_thead() throws Exception {
        test("head", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_time() throws Exception {
        test("head", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_title() throws Exception {
        test("head", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_tr() throws Exception {
        test("head", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_track() throws Exception {
        test("head", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_tt() throws Exception {
        test("head", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_u() throws Exception {
        test("head", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_ul() throws Exception {
        test("head", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_var() throws Exception {
        test("head", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_video() throws Exception {
        test("head", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_wbr() throws Exception {
        test("head", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("null")
    public void _head_xmp() throws Exception {
        test("head", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_area() throws Exception {
        test("header", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_base() throws Exception {
        test("header", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_basefont() throws Exception {
        test("header", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_bgsound() throws Exception {
        test("header", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_br() throws Exception {
        test("header", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _header_command() throws Exception {
        test("header", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_embed() throws Exception {
        test("header", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_hr() throws Exception {
        test("header", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_image() throws Exception {
        test("header", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_img() throws Exception {
        test("header", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_input() throws Exception {
        test("header", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _header_isindex() throws Exception {
        test("header", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_keygen() throws Exception {
        test("header", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_link() throws Exception {
        test("header", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_meta() throws Exception {
        test("header", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_param() throws Exception {
        test("header", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_script() throws Exception {
        test("header", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_source() throws Exception {
        test("header", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_track() throws Exception {
        test("header", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _header_wbr() throws Exception {
        test("header", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_a() throws Exception {
        test("hr", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_abbr() throws Exception {
        test("hr", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_acronym() throws Exception {
        test("hr", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_address() throws Exception {
        test("hr", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_applet() throws Exception {
        test("hr", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_area() throws Exception {
        test("hr", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_article() throws Exception {
        test("hr", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_aside() throws Exception {
        test("hr", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_audio() throws Exception {
        test("hr", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_b() throws Exception {
        test("hr", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_base() throws Exception {
        test("hr", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_basefont() throws Exception {
        test("hr", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_bdi() throws Exception {
        test("hr", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_bdo() throws Exception {
        test("hr", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_bgsound() throws Exception {
        test("hr", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_big() throws Exception {
        test("hr", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_blink() throws Exception {
        test("hr", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_blockquote() throws Exception {
        test("hr", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_body() throws Exception {
        test("hr", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_br() throws Exception {
        test("hr", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_button() throws Exception {
        test("hr", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_canvas() throws Exception {
        test("hr", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_caption() throws Exception {
        test("hr", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_center() throws Exception {
        test("hr", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_cite() throws Exception {
        test("hr", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_code() throws Exception {
        test("hr", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_col() throws Exception {
        test("hr", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_colgroup() throws Exception {
        test("hr", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_command() throws Exception {
        test("hr", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_content() throws Exception {
        test("hr", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_data() throws Exception {
        test("hr", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_datalist() throws Exception {
        test("hr", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_dd() throws Exception {
        test("hr", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_del() throws Exception {
        test("hr", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_details() throws Exception {
        test("hr", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_dfn() throws Exception {
        test("hr", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_dialog() throws Exception {
        test("hr", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_dir() throws Exception {
        test("hr", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_div() throws Exception {
        test("hr", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_dl() throws Exception {
        test("hr", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_dt() throws Exception {
        test("hr", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_em() throws Exception {
        test("hr", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_embed() throws Exception {
        test("hr", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_fieldset() throws Exception {
        test("hr", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_figcaption() throws Exception {
        test("hr", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_figure() throws Exception {
        test("hr", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_font() throws Exception {
        test("hr", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_footer() throws Exception {
        test("hr", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_form() throws Exception {
        test("hr", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_frame() throws Exception {
        test("hr", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_frameset() throws Exception {
        test("hr", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_h1() throws Exception {
        test("hr", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_h2() throws Exception {
        test("hr", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_h3() throws Exception {
        test("hr", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_h4() throws Exception {
        test("hr", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_h5() throws Exception {
        test("hr", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_h6() throws Exception {
        test("hr", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_head() throws Exception {
        test("hr", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_header() throws Exception {
        test("hr", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_hr() throws Exception {
        test("hr", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_html() throws Exception {
        test("hr", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_i() throws Exception {
        test("hr", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_iframe() throws Exception {
        test("hr", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_image() throws Exception {
        test("hr", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_img() throws Exception {
        test("hr", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_input() throws Exception {
        test("hr", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_ins() throws Exception {
        test("hr", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_isindex() throws Exception {
        test("hr", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_kbd() throws Exception {
        test("hr", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_keygen() throws Exception {
        test("hr", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_label() throws Exception {
        test("hr", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_layer() throws Exception {
        test("hr", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_legend() throws Exception {
        test("hr", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_li() throws Exception {
        test("hr", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_link() throws Exception {
        test("hr", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_listing() throws Exception {
        test("hr", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_main() throws Exception {
        test("hr", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_map() throws Exception {
        test("hr", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_mark() throws Exception {
        test("hr", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_marquee() throws Exception {
        test("hr", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_menu() throws Exception {
        test("hr", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_menuitem() throws Exception {
        test("hr", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_meta() throws Exception {
        test("hr", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_meter() throws Exception {
        test("hr", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_multicol() throws Exception {
        test("hr", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_nav() throws Exception {
        test("hr", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_nextid() throws Exception {
        test("hr", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_nobr() throws Exception {
        test("hr", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_noembed() throws Exception {
        test("hr", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_noframes() throws Exception {
        test("hr", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_nolayer() throws Exception {
        test("hr", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_noscript() throws Exception {
        test("hr", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_object() throws Exception {
        test("hr", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_ol() throws Exception {
        test("hr", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_optgroup() throws Exception {
        test("hr", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_option() throws Exception {
        test("hr", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_output() throws Exception {
        test("hr", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_p() throws Exception {
        test("hr", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_param() throws Exception {
        test("hr", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_picture() throws Exception {
        test("hr", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_plaintext() throws Exception {
        test("hr", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_pre() throws Exception {
        test("hr", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_progress() throws Exception {
        test("hr", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_q() throws Exception {
        test("hr", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_rp() throws Exception {
        test("hr", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_rt() throws Exception {
        test("hr", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_ruby() throws Exception {
        test("hr", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_s() throws Exception {
        test("hr", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_samp() throws Exception {
        test("hr", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_script() throws Exception {
        test("hr", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_section() throws Exception {
        test("hr", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_select() throws Exception {
        test("hr", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_slot() throws Exception {
        test("hr", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_small() throws Exception {
        test("hr", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_source() throws Exception {
        test("hr", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_span() throws Exception {
        test("hr", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_strike() throws Exception {
        test("hr", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_strong() throws Exception {
        test("hr", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_style() throws Exception {
        test("hr", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_sub() throws Exception {
        test("hr", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_summary() throws Exception {
        test("hr", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_sup() throws Exception {
        test("hr", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_svg() throws Exception {
        test("hr", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_table() throws Exception {
        test("hr", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_tbody() throws Exception {
        test("hr", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_td() throws Exception {
        test("hr", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_template() throws Exception {
        test("hr", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_textarea() throws Exception {
        test("hr", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_tfoot() throws Exception {
        test("hr", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_th() throws Exception {
        test("hr", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_thead() throws Exception {
        test("hr", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_time() throws Exception {
        test("hr", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_title() throws Exception {
        test("hr", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_tr() throws Exception {
        test("hr", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_track() throws Exception {
        test("hr", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_tt() throws Exception {
        test("hr", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_u() throws Exception {
        test("hr", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_ul() throws Exception {
        test("hr", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_var() throws Exception {
        test("hr", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_video() throws Exception {
        test("hr", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_wbr() throws Exception {
        test("hr", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _hr_xmp() throws Exception {
        test("hr", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_a() throws Exception {
        test("html", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_abbr() throws Exception {
        test("html", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_acronym() throws Exception {
        test("html", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_address() throws Exception {
        test("html", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_applet() throws Exception {
        test("html", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_area() throws Exception {
        test("html", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_article() throws Exception {
        test("html", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_aside() throws Exception {
        test("html", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_audio() throws Exception {
        test("html", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_b() throws Exception {
        test("html", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_base() throws Exception {
        test("html", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_basefont() throws Exception {
        test("html", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_bdi() throws Exception {
        test("html", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_bdo() throws Exception {
        test("html", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_bgsound() throws Exception {
        test("html", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_big() throws Exception {
        test("html", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_blink() throws Exception {
        test("html", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_blockquote() throws Exception {
        test("html", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_body() throws Exception {
        test("html", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_br() throws Exception {
        test("html", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_button() throws Exception {
        test("html", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_canvas() throws Exception {
        test("html", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_caption() throws Exception {
        test("html", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_center() throws Exception {
        test("html", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_cite() throws Exception {
        test("html", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_code() throws Exception {
        test("html", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_col() throws Exception {
        test("html", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_colgroup() throws Exception {
        test("html", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_command() throws Exception {
        test("html", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_content() throws Exception {
        test("html", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_data() throws Exception {
        test("html", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_datalist() throws Exception {
        test("html", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_dd() throws Exception {
        test("html", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_del() throws Exception {
        test("html", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_details() throws Exception {
        test("html", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_dfn() throws Exception {
        test("html", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_dialog() throws Exception {
        test("html", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_dir() throws Exception {
        test("html", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_div() throws Exception {
        test("html", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_dl() throws Exception {
        test("html", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_dt() throws Exception {
        test("html", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_em() throws Exception {
        test("html", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_embed() throws Exception {
        test("html", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_fieldset() throws Exception {
        test("html", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_figcaption() throws Exception {
        test("html", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_figure() throws Exception {
        test("html", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_font() throws Exception {
        test("html", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_footer() throws Exception {
        test("html", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_form() throws Exception {
        test("html", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_frame() throws Exception {
        test("html", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_frameset() throws Exception {
        test("html", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_h1() throws Exception {
        test("html", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_h2() throws Exception {
        test("html", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_h3() throws Exception {
        test("html", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_h4() throws Exception {
        test("html", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_h5() throws Exception {
        test("html", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_h6() throws Exception {
        test("html", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_head() throws Exception {
        test("html", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_header() throws Exception {
        test("html", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_hr() throws Exception {
        test("html", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_html() throws Exception {
        test("html", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_i() throws Exception {
        test("html", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_iframe() throws Exception {
        test("html", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_image() throws Exception {
        test("html", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_img() throws Exception {
        test("html", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_input() throws Exception {
        test("html", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_ins() throws Exception {
        test("html", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_isindex() throws Exception {
        test("html", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_kbd() throws Exception {
        test("html", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_keygen() throws Exception {
        test("html", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_label() throws Exception {
        test("html", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_layer() throws Exception {
        test("html", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_legend() throws Exception {
        test("html", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_li() throws Exception {
        test("html", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_link() throws Exception {
        test("html", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_listing() throws Exception {
        test("html", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_main() throws Exception {
        test("html", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_map() throws Exception {
        test("html", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_mark() throws Exception {
        test("html", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_marquee() throws Exception {
        test("html", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_menu() throws Exception {
        test("html", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_menuitem() throws Exception {
        test("html", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_meta() throws Exception {
        test("html", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_meter() throws Exception {
        test("html", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_multicol() throws Exception {
        test("html", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_nav() throws Exception {
        test("html", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_nextid() throws Exception {
        test("html", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_nobr() throws Exception {
        test("html", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_noembed() throws Exception {
        test("html", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_noframes() throws Exception {
        test("html", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_nolayer() throws Exception {
        test("html", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_noscript() throws Exception {
        test("html", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_object() throws Exception {
        test("html", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_ol() throws Exception {
        test("html", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_optgroup() throws Exception {
        test("html", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_option() throws Exception {
        test("html", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_output() throws Exception {
        test("html", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_p() throws Exception {
        test("html", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_param() throws Exception {
        test("html", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_picture() throws Exception {
        test("html", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_plaintext() throws Exception {
        test("html", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_pre() throws Exception {
        test("html", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_progress() throws Exception {
        test("html", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_q() throws Exception {
        test("html", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_rp() throws Exception {
        test("html", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_rt() throws Exception {
        test("html", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_ruby() throws Exception {
        test("html", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_s() throws Exception {
        test("html", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_samp() throws Exception {
        test("html", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_script() throws Exception {
        test("html", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_section() throws Exception {
        test("html", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_select() throws Exception {
        test("html", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_slot() throws Exception {
        test("html", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_small() throws Exception {
        test("html", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_source() throws Exception {
        test("html", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_span() throws Exception {
        test("html", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_strike() throws Exception {
        test("html", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_strong() throws Exception {
        test("html", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_style() throws Exception {
        test("html", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_sub() throws Exception {
        test("html", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_summary() throws Exception {
        test("html", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_sup() throws Exception {
        test("html", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_svg() throws Exception {
        test("html", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_table() throws Exception {
        test("html", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_tbody() throws Exception {
        test("html", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_td() throws Exception {
        test("html", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_template() throws Exception {
        test("html", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_textarea() throws Exception {
        test("html", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_tfoot() throws Exception {
        test("html", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_th() throws Exception {
        test("html", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_thead() throws Exception {
        test("html", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_time() throws Exception {
        test("html", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_title() throws Exception {
        test("html", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_tr() throws Exception {
        test("html", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_track() throws Exception {
        test("html", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_tt() throws Exception {
        test("html", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_u() throws Exception {
        test("html", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_ul() throws Exception {
        test("html", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_var() throws Exception {
        test("html", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_video() throws Exception {
        test("html", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_wbr() throws Exception {
        test("html", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("3")
    @NotYetImplemented
    public void _html_xmp() throws Exception {
        test("html", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_area() throws Exception {
        test("i", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_base() throws Exception {
        test("i", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_basefont() throws Exception {
        test("i", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_bgsound() throws Exception {
        test("i", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_br() throws Exception {
        test("i", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _i_command() throws Exception {
        test("i", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_embed() throws Exception {
        test("i", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_hr() throws Exception {
        test("i", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_image() throws Exception {
        test("i", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_img() throws Exception {
        test("i", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_input() throws Exception {
        test("i", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _i_isindex() throws Exception {
        test("i", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_keygen() throws Exception {
        test("i", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_link() throws Exception {
        test("i", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_meta() throws Exception {
        test("i", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_param() throws Exception {
        test("i", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_script() throws Exception {
        test("i", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_source() throws Exception {
        test("i", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_track() throws Exception {
        test("i", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _i_wbr() throws Exception {
        test("i", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_a() throws Exception {
        test("image", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_abbr() throws Exception {
        test("image", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_acronym() throws Exception {
        test("image", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_address() throws Exception {
        test("image", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_applet() throws Exception {
        test("image", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_area() throws Exception {
        test("image", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_article() throws Exception {
        test("image", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_aside() throws Exception {
        test("image", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_audio() throws Exception {
        test("image", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_b() throws Exception {
        test("image", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_base() throws Exception {
        test("image", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_basefont() throws Exception {
        test("image", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_bdi() throws Exception {
        test("image", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_bdo() throws Exception {
        test("image", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_bgsound() throws Exception {
        test("image", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_big() throws Exception {
        test("image", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_blink() throws Exception {
        test("image", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_blockquote() throws Exception {
        test("image", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_body() throws Exception {
        test("image", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_br() throws Exception {
        test("image", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_button() throws Exception {
        test("image", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_canvas() throws Exception {
        test("image", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_caption() throws Exception {
        test("image", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_center() throws Exception {
        test("image", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_cite() throws Exception {
        test("image", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_code() throws Exception {
        test("image", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_col() throws Exception {
        test("image", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_colgroup() throws Exception {
        test("image", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_command() throws Exception {
        test("image", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_content() throws Exception {
        test("image", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_data() throws Exception {
        test("image", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_datalist() throws Exception {
        test("image", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_dd() throws Exception {
        test("image", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_del() throws Exception {
        test("image", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_details() throws Exception {
        test("image", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_dfn() throws Exception {
        test("image", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_dialog() throws Exception {
        test("image", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_dir() throws Exception {
        test("image", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_div() throws Exception {
        test("image", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_dl() throws Exception {
        test("image", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_dt() throws Exception {
        test("image", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_em() throws Exception {
        test("image", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_embed() throws Exception {
        test("image", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_fieldset() throws Exception {
        test("image", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_figcaption() throws Exception {
        test("image", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_figure() throws Exception {
        test("image", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_font() throws Exception {
        test("image", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_footer() throws Exception {
        test("image", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_form() throws Exception {
        test("image", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_frame() throws Exception {
        test("image", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_frameset() throws Exception {
        test("image", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_h1() throws Exception {
        test("image", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_h2() throws Exception {
        test("image", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_h3() throws Exception {
        test("image", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_h4() throws Exception {
        test("image", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_h5() throws Exception {
        test("image", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_h6() throws Exception {
        test("image", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_head() throws Exception {
        test("image", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_header() throws Exception {
        test("image", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_hr() throws Exception {
        test("image", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_html() throws Exception {
        test("image", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_i() throws Exception {
        test("image", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_iframe() throws Exception {
        test("image", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_image() throws Exception {
        test("image", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_img() throws Exception {
        test("image", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_input() throws Exception {
        test("image", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_ins() throws Exception {
        test("image", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_isindex() throws Exception {
        test("image", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_kbd() throws Exception {
        test("image", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_keygen() throws Exception {
        test("image", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_label() throws Exception {
        test("image", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_layer() throws Exception {
        test("image", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_legend() throws Exception {
        test("image", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_li() throws Exception {
        test("image", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_link() throws Exception {
        test("image", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_listing() throws Exception {
        test("image", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_main() throws Exception {
        test("image", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_map() throws Exception {
        test("image", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_mark() throws Exception {
        test("image", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_marquee() throws Exception {
        test("image", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_menu() throws Exception {
        test("image", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_menuitem() throws Exception {
        test("image", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_meta() throws Exception {
        test("image", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_meter() throws Exception {
        test("image", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_multicol() throws Exception {
        test("image", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_nav() throws Exception {
        test("image", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_nextid() throws Exception {
        test("image", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_nobr() throws Exception {
        test("image", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_noembed() throws Exception {
        test("image", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_noframes() throws Exception {
        test("image", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_nolayer() throws Exception {
        test("image", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_noscript() throws Exception {
        test("image", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_object() throws Exception {
        test("image", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_ol() throws Exception {
        test("image", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_optgroup() throws Exception {
        test("image", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_option() throws Exception {
        test("image", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_output() throws Exception {
        test("image", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_p() throws Exception {
        test("image", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_param() throws Exception {
        test("image", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_picture() throws Exception {
        test("image", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_plaintext() throws Exception {
        test("image", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_pre() throws Exception {
        test("image", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_progress() throws Exception {
        test("image", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_q() throws Exception {
        test("image", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_rp() throws Exception {
        test("image", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_rt() throws Exception {
        test("image", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_ruby() throws Exception {
        test("image", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_s() throws Exception {
        test("image", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_samp() throws Exception {
        test("image", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_script() throws Exception {
        test("image", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_section() throws Exception {
        test("image", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_select() throws Exception {
        test("image", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_slot() throws Exception {
        test("image", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_small() throws Exception {
        test("image", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_source() throws Exception {
        test("image", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_span() throws Exception {
        test("image", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_strike() throws Exception {
        test("image", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_strong() throws Exception {
        test("image", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_style() throws Exception {
        test("image", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_sub() throws Exception {
        test("image", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_summary() throws Exception {
        test("image", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_sup() throws Exception {
        test("image", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_svg() throws Exception {
        test("image", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_table() throws Exception {
        test("image", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_tbody() throws Exception {
        test("image", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_td() throws Exception {
        test("image", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_template() throws Exception {
        test("image", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_textarea() throws Exception {
        test("image", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_tfoot() throws Exception {
        test("image", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_th() throws Exception {
        test("image", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_thead() throws Exception {
        test("image", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_time() throws Exception {
        test("image", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_title() throws Exception {
        test("image", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_tr() throws Exception {
        test("image", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_track() throws Exception {
        test("image", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_tt() throws Exception {
        test("image", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_u() throws Exception {
        test("image", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_ul() throws Exception {
        test("image", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_var() throws Exception {
        test("image", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_video() throws Exception {
        test("image", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_wbr() throws Exception {
        test("image", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _image_xmp() throws Exception {
        test("image", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_a() throws Exception {
        test("img", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_abbr() throws Exception {
        test("img", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_acronym() throws Exception {
        test("img", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_address() throws Exception {
        test("img", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_applet() throws Exception {
        test("img", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_area() throws Exception {
        test("img", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_article() throws Exception {
        test("img", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_aside() throws Exception {
        test("img", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_audio() throws Exception {
        test("img", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_b() throws Exception {
        test("img", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_base() throws Exception {
        test("img", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_basefont() throws Exception {
        test("img", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_bdi() throws Exception {
        test("img", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_bdo() throws Exception {
        test("img", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_bgsound() throws Exception {
        test("img", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_big() throws Exception {
        test("img", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_blink() throws Exception {
        test("img", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_blockquote() throws Exception {
        test("img", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_body() throws Exception {
        test("img", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_br() throws Exception {
        test("img", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_button() throws Exception {
        test("img", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_canvas() throws Exception {
        test("img", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_caption() throws Exception {
        test("img", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_center() throws Exception {
        test("img", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_cite() throws Exception {
        test("img", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_code() throws Exception {
        test("img", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_col() throws Exception {
        test("img", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_colgroup() throws Exception {
        test("img", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_command() throws Exception {
        test("img", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_content() throws Exception {
        test("img", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_data() throws Exception {
        test("img", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_datalist() throws Exception {
        test("img", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_dd() throws Exception {
        test("img", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_del() throws Exception {
        test("img", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_details() throws Exception {
        test("img", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_dfn() throws Exception {
        test("img", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_dialog() throws Exception {
        test("img", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_dir() throws Exception {
        test("img", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_div() throws Exception {
        test("img", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_dl() throws Exception {
        test("img", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_dt() throws Exception {
        test("img", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_em() throws Exception {
        test("img", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_embed() throws Exception {
        test("img", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_fieldset() throws Exception {
        test("img", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_figcaption() throws Exception {
        test("img", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_figure() throws Exception {
        test("img", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_font() throws Exception {
        test("img", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_footer() throws Exception {
        test("img", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_form() throws Exception {
        test("img", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_frame() throws Exception {
        test("img", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_frameset() throws Exception {
        test("img", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_h1() throws Exception {
        test("img", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_h2() throws Exception {
        test("img", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_h3() throws Exception {
        test("img", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_h4() throws Exception {
        test("img", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_h5() throws Exception {
        test("img", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_h6() throws Exception {
        test("img", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_head() throws Exception {
        test("img", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_header() throws Exception {
        test("img", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_hr() throws Exception {
        test("img", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_html() throws Exception {
        test("img", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_i() throws Exception {
        test("img", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_iframe() throws Exception {
        test("img", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_image() throws Exception {
        test("img", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_img() throws Exception {
        test("img", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_input() throws Exception {
        test("img", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_ins() throws Exception {
        test("img", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_isindex() throws Exception {
        test("img", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_kbd() throws Exception {
        test("img", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_keygen() throws Exception {
        test("img", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_label() throws Exception {
        test("img", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_layer() throws Exception {
        test("img", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_legend() throws Exception {
        test("img", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_li() throws Exception {
        test("img", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_link() throws Exception {
        test("img", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_listing() throws Exception {
        test("img", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_main() throws Exception {
        test("img", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_map() throws Exception {
        test("img", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_mark() throws Exception {
        test("img", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_marquee() throws Exception {
        test("img", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_menu() throws Exception {
        test("img", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_menuitem() throws Exception {
        test("img", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_meta() throws Exception {
        test("img", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_meter() throws Exception {
        test("img", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_multicol() throws Exception {
        test("img", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_nav() throws Exception {
        test("img", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_nextid() throws Exception {
        test("img", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_nobr() throws Exception {
        test("img", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_noembed() throws Exception {
        test("img", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_noframes() throws Exception {
        test("img", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_nolayer() throws Exception {
        test("img", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_noscript() throws Exception {
        test("img", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_object() throws Exception {
        test("img", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_ol() throws Exception {
        test("img", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_optgroup() throws Exception {
        test("img", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_option() throws Exception {
        test("img", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_output() throws Exception {
        test("img", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_p() throws Exception {
        test("img", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_param() throws Exception {
        test("img", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_picture() throws Exception {
        test("img", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_plaintext() throws Exception {
        test("img", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_pre() throws Exception {
        test("img", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_progress() throws Exception {
        test("img", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_q() throws Exception {
        test("img", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_rp() throws Exception {
        test("img", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_rt() throws Exception {
        test("img", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_ruby() throws Exception {
        test("img", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_s() throws Exception {
        test("img", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_samp() throws Exception {
        test("img", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_script() throws Exception {
        test("img", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_section() throws Exception {
        test("img", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_select() throws Exception {
        test("img", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_slot() throws Exception {
        test("img", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_small() throws Exception {
        test("img", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_source() throws Exception {
        test("img", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_span() throws Exception {
        test("img", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_strike() throws Exception {
        test("img", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_strong() throws Exception {
        test("img", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_style() throws Exception {
        test("img", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_sub() throws Exception {
        test("img", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_summary() throws Exception {
        test("img", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_sup() throws Exception {
        test("img", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_svg() throws Exception {
        test("img", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_table() throws Exception {
        test("img", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_tbody() throws Exception {
        test("img", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_td() throws Exception {
        test("img", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_template() throws Exception {
        test("img", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_textarea() throws Exception {
        test("img", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_tfoot() throws Exception {
        test("img", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_th() throws Exception {
        test("img", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_thead() throws Exception {
        test("img", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_time() throws Exception {
        test("img", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_title() throws Exception {
        test("img", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_tr() throws Exception {
        test("img", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_track() throws Exception {
        test("img", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_tt() throws Exception {
        test("img", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_u() throws Exception {
        test("img", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_ul() throws Exception {
        test("img", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_var() throws Exception {
        test("img", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_video() throws Exception {
        test("img", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_wbr() throws Exception {
        test("img", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _img_xmp() throws Exception {
        test("img", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_a() throws Exception {
        test("input", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_abbr() throws Exception {
        test("input", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_acronym() throws Exception {
        test("input", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_address() throws Exception {
        test("input", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_applet() throws Exception {
        test("input", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_area() throws Exception {
        test("input", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_article() throws Exception {
        test("input", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_aside() throws Exception {
        test("input", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_audio() throws Exception {
        test("input", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_b() throws Exception {
        test("input", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_base() throws Exception {
        test("input", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_basefont() throws Exception {
        test("input", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_bdi() throws Exception {
        test("input", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_bdo() throws Exception {
        test("input", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_bgsound() throws Exception {
        test("input", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_big() throws Exception {
        test("input", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_blink() throws Exception {
        test("input", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_blockquote() throws Exception {
        test("input", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_body() throws Exception {
        test("input", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_br() throws Exception {
        test("input", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_button() throws Exception {
        test("input", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_canvas() throws Exception {
        test("input", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_caption() throws Exception {
        test("input", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_center() throws Exception {
        test("input", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_cite() throws Exception {
        test("input", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_code() throws Exception {
        test("input", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_col() throws Exception {
        test("input", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_colgroup() throws Exception {
        test("input", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_command() throws Exception {
        test("input", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_content() throws Exception {
        test("input", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_data() throws Exception {
        test("input", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_datalist() throws Exception {
        test("input", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_dd() throws Exception {
        test("input", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_del() throws Exception {
        test("input", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_details() throws Exception {
        test("input", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_dfn() throws Exception {
        test("input", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_dialog() throws Exception {
        test("input", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_dir() throws Exception {
        test("input", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_div() throws Exception {
        test("input", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_dl() throws Exception {
        test("input", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_dt() throws Exception {
        test("input", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_em() throws Exception {
        test("input", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_embed() throws Exception {
        test("input", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_fieldset() throws Exception {
        test("input", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_figcaption() throws Exception {
        test("input", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_figure() throws Exception {
        test("input", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_font() throws Exception {
        test("input", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_footer() throws Exception {
        test("input", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_form() throws Exception {
        test("input", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_frame() throws Exception {
        test("input", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_frameset() throws Exception {
        test("input", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_h1() throws Exception {
        test("input", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_h2() throws Exception {
        test("input", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_h3() throws Exception {
        test("input", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_h4() throws Exception {
        test("input", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_h5() throws Exception {
        test("input", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_h6() throws Exception {
        test("input", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_head() throws Exception {
        test("input", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_header() throws Exception {
        test("input", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_hr() throws Exception {
        test("input", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_html() throws Exception {
        test("input", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_i() throws Exception {
        test("input", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_iframe() throws Exception {
        test("input", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_image() throws Exception {
        test("input", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_img() throws Exception {
        test("input", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_input() throws Exception {
        test("input", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_ins() throws Exception {
        test("input", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_isindex() throws Exception {
        test("input", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_kbd() throws Exception {
        test("input", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_keygen() throws Exception {
        test("input", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_label() throws Exception {
        test("input", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_layer() throws Exception {
        test("input", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_legend() throws Exception {
        test("input", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_li() throws Exception {
        test("input", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_link() throws Exception {
        test("input", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_listing() throws Exception {
        test("input", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_main() throws Exception {
        test("input", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_map() throws Exception {
        test("input", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_mark() throws Exception {
        test("input", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_marquee() throws Exception {
        test("input", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_menu() throws Exception {
        test("input", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_menuitem() throws Exception {
        test("input", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_meta() throws Exception {
        test("input", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_meter() throws Exception {
        test("input", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_multicol() throws Exception {
        test("input", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_nav() throws Exception {
        test("input", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_nextid() throws Exception {
        test("input", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_nobr() throws Exception {
        test("input", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_noembed() throws Exception {
        test("input", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_noframes() throws Exception {
        test("input", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_nolayer() throws Exception {
        test("input", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_noscript() throws Exception {
        test("input", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_object() throws Exception {
        test("input", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_ol() throws Exception {
        test("input", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_optgroup() throws Exception {
        test("input", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_option() throws Exception {
        test("input", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_output() throws Exception {
        test("input", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_p() throws Exception {
        test("input", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_param() throws Exception {
        test("input", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_picture() throws Exception {
        test("input", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_plaintext() throws Exception {
        test("input", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_pre() throws Exception {
        test("input", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_progress() throws Exception {
        test("input", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_q() throws Exception {
        test("input", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_rp() throws Exception {
        test("input", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_rt() throws Exception {
        test("input", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_ruby() throws Exception {
        test("input", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_s() throws Exception {
        test("input", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_samp() throws Exception {
        test("input", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_script() throws Exception {
        test("input", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_section() throws Exception {
        test("input", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_select() throws Exception {
        test("input", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_slot() throws Exception {
        test("input", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_small() throws Exception {
        test("input", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_source() throws Exception {
        test("input", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_span() throws Exception {
        test("input", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_strike() throws Exception {
        test("input", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_strong() throws Exception {
        test("input", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_style() throws Exception {
        test("input", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_sub() throws Exception {
        test("input", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_summary() throws Exception {
        test("input", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_sup() throws Exception {
        test("input", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_svg() throws Exception {
        test("input", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_table() throws Exception {
        test("input", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_tbody() throws Exception {
        test("input", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_td() throws Exception {
        test("input", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_template() throws Exception {
        test("input", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_textarea() throws Exception {
        test("input", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_tfoot() throws Exception {
        test("input", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_th() throws Exception {
        test("input", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_thead() throws Exception {
        test("input", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_time() throws Exception {
        test("input", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_title() throws Exception {
        test("input", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_tr() throws Exception {
        test("input", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_track() throws Exception {
        test("input", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_tt() throws Exception {
        test("input", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_u() throws Exception {
        test("input", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_ul() throws Exception {
        test("input", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_var() throws Exception {
        test("input", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_video() throws Exception {
        test("input", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_wbr() throws Exception {
        test("input", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _input_xmp() throws Exception {
        test("input", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_area() throws Exception {
        test("ins", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_base() throws Exception {
        test("ins", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_basefont() throws Exception {
        test("ins", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_bgsound() throws Exception {
        test("ins", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_br() throws Exception {
        test("ins", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _ins_command() throws Exception {
        test("ins", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_embed() throws Exception {
        test("ins", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_hr() throws Exception {
        test("ins", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_image() throws Exception {
        test("ins", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_img() throws Exception {
        test("ins", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_input() throws Exception {
        test("ins", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _ins_isindex() throws Exception {
        test("ins", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_keygen() throws Exception {
        test("ins", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_link() throws Exception {
        test("ins", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_meta() throws Exception {
        test("ins", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_param() throws Exception {
        test("ins", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_script() throws Exception {
        test("ins", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_source() throws Exception {
        test("ins", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_track() throws Exception {
        test("ins", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ins_wbr() throws Exception {
        test("ins", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_a() throws Exception {
        test("isindex", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_abbr() throws Exception {
        test("isindex", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_acronym() throws Exception {
        test("isindex", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_address() throws Exception {
        test("isindex", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_applet() throws Exception {
        test("isindex", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_area() throws Exception {
        test("isindex", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_article() throws Exception {
        test("isindex", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_aside() throws Exception {
        test("isindex", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_audio() throws Exception {
        test("isindex", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_b() throws Exception {
        test("isindex", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_base() throws Exception {
        test("isindex", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_basefont() throws Exception {
        test("isindex", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_bdi() throws Exception {
        test("isindex", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_bdo() throws Exception {
        test("isindex", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_bgsound() throws Exception {
        test("isindex", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_big() throws Exception {
        test("isindex", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_blink() throws Exception {
        test("isindex", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_blockquote() throws Exception {
        test("isindex", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_body() throws Exception {
        test("isindex", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_br() throws Exception {
        test("isindex", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_button() throws Exception {
        test("isindex", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_canvas() throws Exception {
        test("isindex", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_caption() throws Exception {
        test("isindex", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_center() throws Exception {
        test("isindex", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_cite() throws Exception {
        test("isindex", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_code() throws Exception {
        test("isindex", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_col() throws Exception {
        test("isindex", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_colgroup() throws Exception {
        test("isindex", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            CHROME = "2",
            FF = "1")
    @NotYetImplemented(IE)
    public void _isindex_command() throws Exception {
        test("isindex", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_content() throws Exception {
        test("isindex", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_data() throws Exception {
        test("isindex", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_datalist() throws Exception {
        test("isindex", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_dd() throws Exception {
        test("isindex", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_del() throws Exception {
        test("isindex", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_details() throws Exception {
        test("isindex", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_dfn() throws Exception {
        test("isindex", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_dialog() throws Exception {
        test("isindex", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_dir() throws Exception {
        test("isindex", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_div() throws Exception {
        test("isindex", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_dl() throws Exception {
        test("isindex", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_dt() throws Exception {
        test("isindex", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_em() throws Exception {
        test("isindex", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_embed() throws Exception {
        test("isindex", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_fieldset() throws Exception {
        test("isindex", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_figcaption() throws Exception {
        test("isindex", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_figure() throws Exception {
        test("isindex", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_font() throws Exception {
        test("isindex", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_footer() throws Exception {
        test("isindex", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_form() throws Exception {
        test("isindex", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_frame() throws Exception {
        test("isindex", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_frameset() throws Exception {
        test("isindex", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_h1() throws Exception {
        test("isindex", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_h2() throws Exception {
        test("isindex", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_h3() throws Exception {
        test("isindex", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_h4() throws Exception {
        test("isindex", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_h5() throws Exception {
        test("isindex", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_h6() throws Exception {
        test("isindex", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_head() throws Exception {
        test("isindex", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_header() throws Exception {
        test("isindex", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_hr() throws Exception {
        test("isindex", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_html() throws Exception {
        test("isindex", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_i() throws Exception {
        test("isindex", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_iframe() throws Exception {
        test("isindex", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_image() throws Exception {
        test("isindex", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_img() throws Exception {
        test("isindex", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_input() throws Exception {
        test("isindex", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_ins() throws Exception {
        test("isindex", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    public void _isindex_isindex() throws Exception {
        test("isindex", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_kbd() throws Exception {
        test("isindex", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_keygen() throws Exception {
        test("isindex", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_label() throws Exception {
        test("isindex", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_layer() throws Exception {
        test("isindex", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_legend() throws Exception {
        test("isindex", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_li() throws Exception {
        test("isindex", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_link() throws Exception {
        test("isindex", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_listing() throws Exception {
        test("isindex", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_main() throws Exception {
        test("isindex", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_map() throws Exception {
        test("isindex", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_mark() throws Exception {
        test("isindex", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_marquee() throws Exception {
        test("isindex", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_menu() throws Exception {
        test("isindex", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_menuitem() throws Exception {
        test("isindex", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_meta() throws Exception {
        test("isindex", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_meter() throws Exception {
        test("isindex", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_multicol() throws Exception {
        test("isindex", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_nav() throws Exception {
        test("isindex", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_nextid() throws Exception {
        test("isindex", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_nobr() throws Exception {
        test("isindex", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_noembed() throws Exception {
        test("isindex", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_noframes() throws Exception {
        test("isindex", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_nolayer() throws Exception {
        test("isindex", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_noscript() throws Exception {
        test("isindex", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_object() throws Exception {
        test("isindex", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_ol() throws Exception {
        test("isindex", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_optgroup() throws Exception {
        test("isindex", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_option() throws Exception {
        test("isindex", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_output() throws Exception {
        test("isindex", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_p() throws Exception {
        test("isindex", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_param() throws Exception {
        test("isindex", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_picture() throws Exception {
        test("isindex", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_plaintext() throws Exception {
        test("isindex", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_pre() throws Exception {
        test("isindex", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_progress() throws Exception {
        test("isindex", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_q() throws Exception {
        test("isindex", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_rp() throws Exception {
        test("isindex", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_rt() throws Exception {
        test("isindex", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_ruby() throws Exception {
        test("isindex", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_s() throws Exception {
        test("isindex", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_samp() throws Exception {
        test("isindex", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_script() throws Exception {
        test("isindex", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_section() throws Exception {
        test("isindex", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_select() throws Exception {
        test("isindex", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_slot() throws Exception {
        test("isindex", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_small() throws Exception {
        test("isindex", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_source() throws Exception {
        test("isindex", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_span() throws Exception {
        test("isindex", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_strike() throws Exception {
        test("isindex", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_strong() throws Exception {
        test("isindex", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_style() throws Exception {
        test("isindex", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_sub() throws Exception {
        test("isindex", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_summary() throws Exception {
        test("isindex", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_sup() throws Exception {
        test("isindex", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_svg() throws Exception {
        test("isindex", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_table() throws Exception {
        test("isindex", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_tbody() throws Exception {
        test("isindex", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_td() throws Exception {
        test("isindex", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_template() throws Exception {
        test("isindex", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_textarea() throws Exception {
        test("isindex", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_tfoot() throws Exception {
        test("isindex", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_th() throws Exception {
        test("isindex", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_thead() throws Exception {
        test("isindex", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_time() throws Exception {
        test("isindex", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_title() throws Exception {
        test("isindex", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_tr() throws Exception {
        test("isindex", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_track() throws Exception {
        test("isindex", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_tt() throws Exception {
        test("isindex", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_u() throws Exception {
        test("isindex", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_ul() throws Exception {
        test("isindex", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_var() throws Exception {
        test("isindex", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_video() throws Exception {
        test("isindex", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_wbr() throws Exception {
        test("isindex", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    @NotYetImplemented(IE)
    public void _isindex_xmp() throws Exception {
        test("isindex", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_area() throws Exception {
        test("kbd", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_base() throws Exception {
        test("kbd", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_basefont() throws Exception {
        test("kbd", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_bgsound() throws Exception {
        test("kbd", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_br() throws Exception {
        test("kbd", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _kbd_command() throws Exception {
        test("kbd", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_embed() throws Exception {
        test("kbd", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_hr() throws Exception {
        test("kbd", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_image() throws Exception {
        test("kbd", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_img() throws Exception {
        test("kbd", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_input() throws Exception {
        test("kbd", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _kbd_isindex() throws Exception {
        test("kbd", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_keygen() throws Exception {
        test("kbd", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_link() throws Exception {
        test("kbd", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_meta() throws Exception {
        test("kbd", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_param() throws Exception {
        test("kbd", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_script() throws Exception {
        test("kbd", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_source() throws Exception {
        test("kbd", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_track() throws Exception {
        test("kbd", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _kbd_wbr() throws Exception {
        test("kbd", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_a() throws Exception {
        test("keygen", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_abbr() throws Exception {
        test("keygen", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_acronym() throws Exception {
        test("keygen", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_address() throws Exception {
        test("keygen", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_applet() throws Exception {
        test("keygen", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_area() throws Exception {
        test("keygen", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_article() throws Exception {
        test("keygen", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_aside() throws Exception {
        test("keygen", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_audio() throws Exception {
        test("keygen", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_b() throws Exception {
        test("keygen", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_base() throws Exception {
        test("keygen", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_basefont() throws Exception {
        test("keygen", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_bdi() throws Exception {
        test("keygen", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_bdo() throws Exception {
        test("keygen", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_bgsound() throws Exception {
        test("keygen", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_big() throws Exception {
        test("keygen", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_blink() throws Exception {
        test("keygen", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_blockquote() throws Exception {
        test("keygen", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_body() throws Exception {
        test("keygen", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_br() throws Exception {
        test("keygen", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_button() throws Exception {
        test("keygen", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_canvas() throws Exception {
        test("keygen", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_caption() throws Exception {
        test("keygen", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_center() throws Exception {
        test("keygen", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_cite() throws Exception {
        test("keygen", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_code() throws Exception {
        test("keygen", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_col() throws Exception {
        test("keygen", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_colgroup() throws Exception {
        test("keygen", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_command() throws Exception {
        test("keygen", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_content() throws Exception {
        test("keygen", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_data() throws Exception {
        test("keygen", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_datalist() throws Exception {
        test("keygen", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_dd() throws Exception {
        test("keygen", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_del() throws Exception {
        test("keygen", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_details() throws Exception {
        test("keygen", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_dfn() throws Exception {
        test("keygen", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_dialog() throws Exception {
        test("keygen", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_dir() throws Exception {
        test("keygen", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_div() throws Exception {
        test("keygen", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_dl() throws Exception {
        test("keygen", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_dt() throws Exception {
        test("keygen", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_em() throws Exception {
        test("keygen", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_embed() throws Exception {
        test("keygen", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_fieldset() throws Exception {
        test("keygen", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_figcaption() throws Exception {
        test("keygen", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_figure() throws Exception {
        test("keygen", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_font() throws Exception {
        test("keygen", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_footer() throws Exception {
        test("keygen", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_form() throws Exception {
        test("keygen", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_frame() throws Exception {
        test("keygen", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_frameset() throws Exception {
        test("keygen", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_h1() throws Exception {
        test("keygen", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_h2() throws Exception {
        test("keygen", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_h3() throws Exception {
        test("keygen", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_h4() throws Exception {
        test("keygen", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_h5() throws Exception {
        test("keygen", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_h6() throws Exception {
        test("keygen", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_head() throws Exception {
        test("keygen", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_header() throws Exception {
        test("keygen", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_hr() throws Exception {
        test("keygen", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_html() throws Exception {
        test("keygen", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_i() throws Exception {
        test("keygen", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_iframe() throws Exception {
        test("keygen", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_image() throws Exception {
        test("keygen", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_img() throws Exception {
        test("keygen", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_input() throws Exception {
        test("keygen", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_ins() throws Exception {
        test("keygen", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_isindex() throws Exception {
        test("keygen", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_kbd() throws Exception {
        test("keygen", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_keygen() throws Exception {
        test("keygen", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_label() throws Exception {
        test("keygen", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_layer() throws Exception {
        test("keygen", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_legend() throws Exception {
        test("keygen", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_li() throws Exception {
        test("keygen", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_link() throws Exception {
        test("keygen", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_listing() throws Exception {
        test("keygen", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_main() throws Exception {
        test("keygen", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_map() throws Exception {
        test("keygen", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_mark() throws Exception {
        test("keygen", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_marquee() throws Exception {
        test("keygen", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_menu() throws Exception {
        test("keygen", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_menuitem() throws Exception {
        test("keygen", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_meta() throws Exception {
        test("keygen", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_meter() throws Exception {
        test("keygen", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_multicol() throws Exception {
        test("keygen", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_nav() throws Exception {
        test("keygen", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_nextid() throws Exception {
        test("keygen", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_nobr() throws Exception {
        test("keygen", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_noembed() throws Exception {
        test("keygen", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_noframes() throws Exception {
        test("keygen", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_nolayer() throws Exception {
        test("keygen", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_noscript() throws Exception {
        test("keygen", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_object() throws Exception {
        test("keygen", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_ol() throws Exception {
        test("keygen", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_optgroup() throws Exception {
        test("keygen", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_option() throws Exception {
        test("keygen", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_output() throws Exception {
        test("keygen", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_p() throws Exception {
        test("keygen", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_param() throws Exception {
        test("keygen", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_picture() throws Exception {
        test("keygen", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_plaintext() throws Exception {
        test("keygen", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_pre() throws Exception {
        test("keygen", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_progress() throws Exception {
        test("keygen", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_q() throws Exception {
        test("keygen", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_rp() throws Exception {
        test("keygen", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_rt() throws Exception {
        test("keygen", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_ruby() throws Exception {
        test("keygen", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_s() throws Exception {
        test("keygen", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_samp() throws Exception {
        test("keygen", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_script() throws Exception {
        test("keygen", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_section() throws Exception {
        test("keygen", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_select() throws Exception {
        test("keygen", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_slot() throws Exception {
        test("keygen", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_small() throws Exception {
        test("keygen", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_source() throws Exception {
        test("keygen", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_span() throws Exception {
        test("keygen", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_strike() throws Exception {
        test("keygen", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_strong() throws Exception {
        test("keygen", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_style() throws Exception {
        test("keygen", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_sub() throws Exception {
        test("keygen", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_summary() throws Exception {
        test("keygen", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_sup() throws Exception {
        test("keygen", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_svg() throws Exception {
        test("keygen", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_table() throws Exception {
        test("keygen", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_tbody() throws Exception {
        test("keygen", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_td() throws Exception {
        test("keygen", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_template() throws Exception {
        test("keygen", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_textarea() throws Exception {
        test("keygen", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_tfoot() throws Exception {
        test("keygen", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_th() throws Exception {
        test("keygen", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_thead() throws Exception {
        test("keygen", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_time() throws Exception {
        test("keygen", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_title() throws Exception {
        test("keygen", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_tr() throws Exception {
        test("keygen", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_track() throws Exception {
        test("keygen", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_tt() throws Exception {
        test("keygen", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_u() throws Exception {
        test("keygen", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_ul() throws Exception {
        test("keygen", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_var() throws Exception {
        test("keygen", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_video() throws Exception {
        test("keygen", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_wbr() throws Exception {
        test("keygen", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            FF = "2")
    public void _keygen_xmp() throws Exception {
        test("keygen", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_area() throws Exception {
        test("label", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_base() throws Exception {
        test("label", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_basefont() throws Exception {
        test("label", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_bgsound() throws Exception {
        test("label", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_br() throws Exception {
        test("label", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _label_command() throws Exception {
        test("label", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_embed() throws Exception {
        test("label", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_hr() throws Exception {
        test("label", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_image() throws Exception {
        test("label", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_img() throws Exception {
        test("label", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_input() throws Exception {
        test("label", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _label_isindex() throws Exception {
        test("label", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_keygen() throws Exception {
        test("label", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_link() throws Exception {
        test("label", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_meta() throws Exception {
        test("label", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_param() throws Exception {
        test("label", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_script() throws Exception {
        test("label", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_source() throws Exception {
        test("label", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_track() throws Exception {
        test("label", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _label_wbr() throws Exception {
        test("label", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_area() throws Exception {
        test("layer", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_base() throws Exception {
        test("layer", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_basefont() throws Exception {
        test("layer", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_bgsound() throws Exception {
        test("layer", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_br() throws Exception {
        test("layer", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _layer_command() throws Exception {
        test("layer", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_embed() throws Exception {
        test("layer", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_hr() throws Exception {
        test("layer", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_image() throws Exception {
        test("layer", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_img() throws Exception {
        test("layer", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_input() throws Exception {
        test("layer", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _layer_isindex() throws Exception {
        test("layer", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_keygen() throws Exception {
        test("layer", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_link() throws Exception {
        test("layer", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_meta() throws Exception {
        test("layer", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_param() throws Exception {
        test("layer", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_script() throws Exception {
        test("layer", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_source() throws Exception {
        test("layer", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_track() throws Exception {
        test("layer", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _layer_wbr() throws Exception {
        test("layer", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_area() throws Exception {
        test("legend", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_base() throws Exception {
        test("legend", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_basefont() throws Exception {
        test("legend", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_bgsound() throws Exception {
        test("legend", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_br() throws Exception {
        test("legend", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _legend_command() throws Exception {
        test("legend", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_embed() throws Exception {
        test("legend", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_hr() throws Exception {
        test("legend", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_image() throws Exception {
        test("legend", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_img() throws Exception {
        test("legend", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_input() throws Exception {
        test("legend", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _legend_isindex() throws Exception {
        test("legend", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_keygen() throws Exception {
        test("legend", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_link() throws Exception {
        test("legend", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_meta() throws Exception {
        test("legend", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_param() throws Exception {
        test("legend", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_script() throws Exception {
        test("legend", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_source() throws Exception {
        test("legend", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_track() throws Exception {
        test("legend", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _legend_wbr() throws Exception {
        test("legend", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_area() throws Exception {
        test("li", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_base() throws Exception {
        test("li", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_basefont() throws Exception {
        test("li", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_bgsound() throws Exception {
        test("li", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_br() throws Exception {
        test("li", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _li_command() throws Exception {
        test("li", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_embed() throws Exception {
        test("li", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_hr() throws Exception {
        test("li", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_image() throws Exception {
        test("li", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_img() throws Exception {
        test("li", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_input() throws Exception {
        test("li", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _li_isindex() throws Exception {
        test("li", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_keygen() throws Exception {
        test("li", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _li_li() throws Exception {
        test("li", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_link() throws Exception {
        test("li", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_meta() throws Exception {
        test("li", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_param() throws Exception {
        test("li", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_script() throws Exception {
        test("li", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_source() throws Exception {
        test("li", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_track() throws Exception {
        test("li", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _li_wbr() throws Exception {
        test("li", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_a() throws Exception {
        test("link", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_abbr() throws Exception {
        test("link", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_acronym() throws Exception {
        test("link", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_address() throws Exception {
        test("link", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_applet() throws Exception {
        test("link", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_area() throws Exception {
        test("link", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_article() throws Exception {
        test("link", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_aside() throws Exception {
        test("link", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_audio() throws Exception {
        test("link", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_b() throws Exception {
        test("link", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_base() throws Exception {
        test("link", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_basefont() throws Exception {
        test("link", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_bdi() throws Exception {
        test("link", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_bdo() throws Exception {
        test("link", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_bgsound() throws Exception {
        test("link", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_big() throws Exception {
        test("link", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_blink() throws Exception {
        test("link", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_blockquote() throws Exception {
        test("link", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_body() throws Exception {
        test("link", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_br() throws Exception {
        test("link", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_button() throws Exception {
        test("link", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_canvas() throws Exception {
        test("link", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_caption() throws Exception {
        test("link", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_center() throws Exception {
        test("link", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_cite() throws Exception {
        test("link", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_code() throws Exception {
        test("link", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_col() throws Exception {
        test("link", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_colgroup() throws Exception {
        test("link", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_command() throws Exception {
        test("link", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_content() throws Exception {
        test("link", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_data() throws Exception {
        test("link", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_datalist() throws Exception {
        test("link", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_dd() throws Exception {
        test("link", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_del() throws Exception {
        test("link", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_details() throws Exception {
        test("link", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_dfn() throws Exception {
        test("link", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_dialog() throws Exception {
        test("link", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_dir() throws Exception {
        test("link", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_div() throws Exception {
        test("link", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_dl() throws Exception {
        test("link", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_dt() throws Exception {
        test("link", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_em() throws Exception {
        test("link", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_embed() throws Exception {
        test("link", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_fieldset() throws Exception {
        test("link", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_figcaption() throws Exception {
        test("link", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_figure() throws Exception {
        test("link", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_font() throws Exception {
        test("link", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_footer() throws Exception {
        test("link", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_form() throws Exception {
        test("link", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_frame() throws Exception {
        test("link", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_frameset() throws Exception {
        test("link", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_h1() throws Exception {
        test("link", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_h2() throws Exception {
        test("link", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_h3() throws Exception {
        test("link", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_h4() throws Exception {
        test("link", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_h5() throws Exception {
        test("link", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_h6() throws Exception {
        test("link", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_head() throws Exception {
        test("link", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_header() throws Exception {
        test("link", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_hr() throws Exception {
        test("link", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_html() throws Exception {
        test("link", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_i() throws Exception {
        test("link", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_iframe() throws Exception {
        test("link", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_image() throws Exception {
        test("link", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_img() throws Exception {
        test("link", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_input() throws Exception {
        test("link", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_ins() throws Exception {
        test("link", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_isindex() throws Exception {
        test("link", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_kbd() throws Exception {
        test("link", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_keygen() throws Exception {
        test("link", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_label() throws Exception {
        test("link", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_layer() throws Exception {
        test("link", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_legend() throws Exception {
        test("link", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_li() throws Exception {
        test("link", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_link() throws Exception {
        test("link", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_listing() throws Exception {
        test("link", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_main() throws Exception {
        test("link", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_map() throws Exception {
        test("link", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_mark() throws Exception {
        test("link", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_marquee() throws Exception {
        test("link", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_menu() throws Exception {
        test("link", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_menuitem() throws Exception {
        test("link", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_meta() throws Exception {
        test("link", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_meter() throws Exception {
        test("link", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_multicol() throws Exception {
        test("link", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_nav() throws Exception {
        test("link", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_nextid() throws Exception {
        test("link", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_nobr() throws Exception {
        test("link", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_noembed() throws Exception {
        test("link", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_noframes() throws Exception {
        test("link", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_nolayer() throws Exception {
        test("link", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_noscript() throws Exception {
        test("link", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_object() throws Exception {
        test("link", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_ol() throws Exception {
        test("link", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_optgroup() throws Exception {
        test("link", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_option() throws Exception {
        test("link", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_output() throws Exception {
        test("link", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_p() throws Exception {
        test("link", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_param() throws Exception {
        test("link", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_picture() throws Exception {
        test("link", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_plaintext() throws Exception {
        test("link", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_pre() throws Exception {
        test("link", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_progress() throws Exception {
        test("link", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_q() throws Exception {
        test("link", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_rp() throws Exception {
        test("link", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_rt() throws Exception {
        test("link", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_ruby() throws Exception {
        test("link", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_s() throws Exception {
        test("link", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_samp() throws Exception {
        test("link", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_script() throws Exception {
        test("link", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_section() throws Exception {
        test("link", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_select() throws Exception {
        test("link", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_slot() throws Exception {
        test("link", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_small() throws Exception {
        test("link", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_source() throws Exception {
        test("link", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_span() throws Exception {
        test("link", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_strike() throws Exception {
        test("link", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_strong() throws Exception {
        test("link", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_style() throws Exception {
        test("link", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_sub() throws Exception {
        test("link", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_summary() throws Exception {
        test("link", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_sup() throws Exception {
        test("link", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_svg() throws Exception {
        test("link", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_table() throws Exception {
        test("link", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_tbody() throws Exception {
        test("link", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_td() throws Exception {
        test("link", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_template() throws Exception {
        test("link", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_textarea() throws Exception {
        test("link", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_tfoot() throws Exception {
        test("link", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_th() throws Exception {
        test("link", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_thead() throws Exception {
        test("link", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_time() throws Exception {
        test("link", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_title() throws Exception {
        test("link", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_tr() throws Exception {
        test("link", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_track() throws Exception {
        test("link", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_tt() throws Exception {
        test("link", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_u() throws Exception {
        test("link", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_ul() throws Exception {
        test("link", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_var() throws Exception {
        test("link", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_video() throws Exception {
        test("link", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_wbr() throws Exception {
        test("link", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _link_xmp() throws Exception {
        test("link", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_area() throws Exception {
        test("listing", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_base() throws Exception {
        test("listing", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_basefont() throws Exception {
        test("listing", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_bgsound() throws Exception {
        test("listing", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_br() throws Exception {
        test("listing", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _listing_command() throws Exception {
        test("listing", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_embed() throws Exception {
        test("listing", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_hr() throws Exception {
        test("listing", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_image() throws Exception {
        test("listing", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_img() throws Exception {
        test("listing", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_input() throws Exception {
        test("listing", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _listing_isindex() throws Exception {
        test("listing", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_keygen() throws Exception {
        test("listing", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_link() throws Exception {
        test("listing", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_meta() throws Exception {
        test("listing", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_param() throws Exception {
        test("listing", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_script() throws Exception {
        test("listing", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_source() throws Exception {
        test("listing", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_track() throws Exception {
        test("listing", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _listing_wbr() throws Exception {
        test("listing", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_area() throws Exception {
        test("main", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_base() throws Exception {
        test("main", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_basefont() throws Exception {
        test("main", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_bgsound() throws Exception {
        test("main", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_br() throws Exception {
        test("main", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _main_command() throws Exception {
        test("main", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_embed() throws Exception {
        test("main", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_hr() throws Exception {
        test("main", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_image() throws Exception {
        test("main", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_img() throws Exception {
        test("main", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_input() throws Exception {
        test("main", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _main_isindex() throws Exception {
        test("main", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_keygen() throws Exception {
        test("main", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_link() throws Exception {
        test("main", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_meta() throws Exception {
        test("main", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_param() throws Exception {
        test("main", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_script() throws Exception {
        test("main", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_source() throws Exception {
        test("main", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_track() throws Exception {
        test("main", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _main_wbr() throws Exception {
        test("main", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_area() throws Exception {
        test("map", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_base() throws Exception {
        test("map", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_basefont() throws Exception {
        test("map", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_bgsound() throws Exception {
        test("map", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_br() throws Exception {
        test("map", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _map_command() throws Exception {
        test("map", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_embed() throws Exception {
        test("map", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_hr() throws Exception {
        test("map", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_image() throws Exception {
        test("map", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_img() throws Exception {
        test("map", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_input() throws Exception {
        test("map", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _map_isindex() throws Exception {
        test("map", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_keygen() throws Exception {
        test("map", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_link() throws Exception {
        test("map", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_meta() throws Exception {
        test("map", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_param() throws Exception {
        test("map", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_script() throws Exception {
        test("map", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_source() throws Exception {
        test("map", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_track() throws Exception {
        test("map", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _map_wbr() throws Exception {
        test("map", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_area() throws Exception {
        test("mark", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_base() throws Exception {
        test("mark", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_basefont() throws Exception {
        test("mark", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_bgsound() throws Exception {
        test("mark", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_br() throws Exception {
        test("mark", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _mark_command() throws Exception {
        test("mark", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_embed() throws Exception {
        test("mark", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_hr() throws Exception {
        test("mark", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_image() throws Exception {
        test("mark", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_img() throws Exception {
        test("mark", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_input() throws Exception {
        test("mark", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _mark_isindex() throws Exception {
        test("mark", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_keygen() throws Exception {
        test("mark", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_link() throws Exception {
        test("mark", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_meta() throws Exception {
        test("mark", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_param() throws Exception {
        test("mark", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_script() throws Exception {
        test("mark", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_source() throws Exception {
        test("mark", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_track() throws Exception {
        test("mark", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _mark_wbr() throws Exception {
        test("mark", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_area() throws Exception {
        test("marquee", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_base() throws Exception {
        test("marquee", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_basefont() throws Exception {
        test("marquee", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_bgsound() throws Exception {
        test("marquee", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_br() throws Exception {
        test("marquee", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _marquee_command() throws Exception {
        test("marquee", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_embed() throws Exception {
        test("marquee", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_hr() throws Exception {
        test("marquee", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_image() throws Exception {
        test("marquee", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_img() throws Exception {
        test("marquee", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_input() throws Exception {
        test("marquee", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _marquee_isindex() throws Exception {
        test("marquee", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_keygen() throws Exception {
        test("marquee", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_link() throws Exception {
        test("marquee", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_meta() throws Exception {
        test("marquee", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_param() throws Exception {
        test("marquee", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_script() throws Exception {
        test("marquee", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_source() throws Exception {
        test("marquee", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_track() throws Exception {
        test("marquee", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _marquee_wbr() throws Exception {
        test("marquee", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_area() throws Exception {
        test("menu", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_base() throws Exception {
        test("menu", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_basefont() throws Exception {
        test("menu", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_bgsound() throws Exception {
        test("menu", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_br() throws Exception {
        test("menu", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _menu_command() throws Exception {
        test("menu", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_embed() throws Exception {
        test("menu", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_hr() throws Exception {
        test("menu", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_image() throws Exception {
        test("menu", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_img() throws Exception {
        test("menu", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_input() throws Exception {
        test("menu", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _menu_isindex() throws Exception {
        test("menu", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_keygen() throws Exception {
        test("menu", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_link() throws Exception {
        test("menu", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_meta() throws Exception {
        test("menu", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_param() throws Exception {
        test("menu", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_script() throws Exception {
        test("menu", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_source() throws Exception {
        test("menu", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_track() throws Exception {
        test("menu", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menu_wbr() throws Exception {
        test("menu", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_area() throws Exception {
        test("menuitem", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_base() throws Exception {
        test("menuitem", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_basefont() throws Exception {
        test("menuitem", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_bgsound() throws Exception {
        test("menuitem", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_br() throws Exception {
        test("menuitem", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _menuitem_command() throws Exception {
        test("menuitem", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_embed() throws Exception {
        test("menuitem", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_hr() throws Exception {
        test("menuitem", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_image() throws Exception {
        test("menuitem", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_img() throws Exception {
        test("menuitem", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_input() throws Exception {
        test("menuitem", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _menuitem_isindex() throws Exception {
        test("menuitem", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_keygen() throws Exception {
        test("menuitem", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_link() throws Exception {
        test("menuitem", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_meta() throws Exception {
        test("menuitem", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_param() throws Exception {
        test("menuitem", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_script() throws Exception {
        test("menuitem", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_source() throws Exception {
        test("menuitem", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_track() throws Exception {
        test("menuitem", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _menuitem_wbr() throws Exception {
        test("menuitem", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_a() throws Exception {
        test("meta", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_abbr() throws Exception {
        test("meta", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_acronym() throws Exception {
        test("meta", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_address() throws Exception {
        test("meta", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_applet() throws Exception {
        test("meta", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_area() throws Exception {
        test("meta", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_article() throws Exception {
        test("meta", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_aside() throws Exception {
        test("meta", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_audio() throws Exception {
        test("meta", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_b() throws Exception {
        test("meta", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_base() throws Exception {
        test("meta", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_basefont() throws Exception {
        test("meta", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_bdi() throws Exception {
        test("meta", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_bdo() throws Exception {
        test("meta", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_bgsound() throws Exception {
        test("meta", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_big() throws Exception {
        test("meta", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_blink() throws Exception {
        test("meta", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_blockquote() throws Exception {
        test("meta", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_body() throws Exception {
        test("meta", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_br() throws Exception {
        test("meta", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_button() throws Exception {
        test("meta", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_canvas() throws Exception {
        test("meta", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_caption() throws Exception {
        test("meta", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_center() throws Exception {
        test("meta", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_cite() throws Exception {
        test("meta", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_code() throws Exception {
        test("meta", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_col() throws Exception {
        test("meta", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_colgroup() throws Exception {
        test("meta", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_command() throws Exception {
        test("meta", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_content() throws Exception {
        test("meta", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_data() throws Exception {
        test("meta", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_datalist() throws Exception {
        test("meta", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_dd() throws Exception {
        test("meta", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_del() throws Exception {
        test("meta", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_details() throws Exception {
        test("meta", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_dfn() throws Exception {
        test("meta", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_dialog() throws Exception {
        test("meta", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_dir() throws Exception {
        test("meta", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_div() throws Exception {
        test("meta", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_dl() throws Exception {
        test("meta", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_dt() throws Exception {
        test("meta", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_em() throws Exception {
        test("meta", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_embed() throws Exception {
        test("meta", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_fieldset() throws Exception {
        test("meta", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_figcaption() throws Exception {
        test("meta", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_figure() throws Exception {
        test("meta", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_font() throws Exception {
        test("meta", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_footer() throws Exception {
        test("meta", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_form() throws Exception {
        test("meta", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_frame() throws Exception {
        test("meta", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_frameset() throws Exception {
        test("meta", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_h1() throws Exception {
        test("meta", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_h2() throws Exception {
        test("meta", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_h3() throws Exception {
        test("meta", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_h4() throws Exception {
        test("meta", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_h5() throws Exception {
        test("meta", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_h6() throws Exception {
        test("meta", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_head() throws Exception {
        test("meta", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_header() throws Exception {
        test("meta", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_hr() throws Exception {
        test("meta", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_html() throws Exception {
        test("meta", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_i() throws Exception {
        test("meta", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_iframe() throws Exception {
        test("meta", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_image() throws Exception {
        test("meta", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_img() throws Exception {
        test("meta", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_input() throws Exception {
        test("meta", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_ins() throws Exception {
        test("meta", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_isindex() throws Exception {
        test("meta", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_kbd() throws Exception {
        test("meta", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_keygen() throws Exception {
        test("meta", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_label() throws Exception {
        test("meta", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_layer() throws Exception {
        test("meta", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_legend() throws Exception {
        test("meta", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_li() throws Exception {
        test("meta", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_link() throws Exception {
        test("meta", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_listing() throws Exception {
        test("meta", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_main() throws Exception {
        test("meta", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_map() throws Exception {
        test("meta", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_mark() throws Exception {
        test("meta", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_marquee() throws Exception {
        test("meta", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_menu() throws Exception {
        test("meta", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_menuitem() throws Exception {
        test("meta", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_meta() throws Exception {
        test("meta", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_meter() throws Exception {
        test("meta", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_multicol() throws Exception {
        test("meta", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_nav() throws Exception {
        test("meta", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_nextid() throws Exception {
        test("meta", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_nobr() throws Exception {
        test("meta", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_noembed() throws Exception {
        test("meta", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_noframes() throws Exception {
        test("meta", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_nolayer() throws Exception {
        test("meta", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_noscript() throws Exception {
        test("meta", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_object() throws Exception {
        test("meta", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_ol() throws Exception {
        test("meta", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_optgroup() throws Exception {
        test("meta", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_option() throws Exception {
        test("meta", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_output() throws Exception {
        test("meta", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_p() throws Exception {
        test("meta", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_param() throws Exception {
        test("meta", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_picture() throws Exception {
        test("meta", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_plaintext() throws Exception {
        test("meta", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_pre() throws Exception {
        test("meta", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_progress() throws Exception {
        test("meta", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_q() throws Exception {
        test("meta", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_rp() throws Exception {
        test("meta", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_rt() throws Exception {
        test("meta", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_ruby() throws Exception {
        test("meta", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_s() throws Exception {
        test("meta", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_samp() throws Exception {
        test("meta", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_script() throws Exception {
        test("meta", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_section() throws Exception {
        test("meta", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_select() throws Exception {
        test("meta", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_slot() throws Exception {
        test("meta", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_small() throws Exception {
        test("meta", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_source() throws Exception {
        test("meta", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_span() throws Exception {
        test("meta", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_strike() throws Exception {
        test("meta", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_strong() throws Exception {
        test("meta", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_style() throws Exception {
        test("meta", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_sub() throws Exception {
        test("meta", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_summary() throws Exception {
        test("meta", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_sup() throws Exception {
        test("meta", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_svg() throws Exception {
        test("meta", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_table() throws Exception {
        test("meta", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_tbody() throws Exception {
        test("meta", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_td() throws Exception {
        test("meta", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_template() throws Exception {
        test("meta", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_textarea() throws Exception {
        test("meta", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_tfoot() throws Exception {
        test("meta", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_th() throws Exception {
        test("meta", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_thead() throws Exception {
        test("meta", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_time() throws Exception {
        test("meta", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_title() throws Exception {
        test("meta", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_tr() throws Exception {
        test("meta", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_track() throws Exception {
        test("meta", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_tt() throws Exception {
        test("meta", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_u() throws Exception {
        test("meta", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_ul() throws Exception {
        test("meta", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_var() throws Exception {
        test("meta", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_video() throws Exception {
        test("meta", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_wbr() throws Exception {
        test("meta", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _meta_xmp() throws Exception {
        test("meta", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_area() throws Exception {
        test("meter", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_base() throws Exception {
        test("meter", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_basefont() throws Exception {
        test("meter", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_bgsound() throws Exception {
        test("meter", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_br() throws Exception {
        test("meter", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _meter_command() throws Exception {
        test("meter", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_embed() throws Exception {
        test("meter", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_hr() throws Exception {
        test("meter", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_image() throws Exception {
        test("meter", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_img() throws Exception {
        test("meter", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_input() throws Exception {
        test("meter", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _meter_isindex() throws Exception {
        test("meter", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_keygen() throws Exception {
        test("meter", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_link() throws Exception {
        test("meter", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_meta() throws Exception {
        test("meter", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_param() throws Exception {
        test("meter", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_script() throws Exception {
        test("meter", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_source() throws Exception {
        test("meter", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_track() throws Exception {
        test("meter", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _meter_wbr() throws Exception {
        test("meter", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_area() throws Exception {
        test("multicol", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_base() throws Exception {
        test("multicol", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_basefont() throws Exception {
        test("multicol", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_bgsound() throws Exception {
        test("multicol", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_br() throws Exception {
        test("multicol", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _multicol_command() throws Exception {
        test("multicol", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_embed() throws Exception {
        test("multicol", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_hr() throws Exception {
        test("multicol", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_image() throws Exception {
        test("multicol", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_img() throws Exception {
        test("multicol", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_input() throws Exception {
        test("multicol", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _multicol_isindex() throws Exception {
        test("multicol", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_keygen() throws Exception {
        test("multicol", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_link() throws Exception {
        test("multicol", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_meta() throws Exception {
        test("multicol", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_param() throws Exception {
        test("multicol", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_script() throws Exception {
        test("multicol", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_source() throws Exception {
        test("multicol", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_track() throws Exception {
        test("multicol", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _multicol_wbr() throws Exception {
        test("multicol", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_area() throws Exception {
        test("nav", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_base() throws Exception {
        test("nav", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_basefont() throws Exception {
        test("nav", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_bgsound() throws Exception {
        test("nav", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_br() throws Exception {
        test("nav", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _nav_command() throws Exception {
        test("nav", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_embed() throws Exception {
        test("nav", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_hr() throws Exception {
        test("nav", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_image() throws Exception {
        test("nav", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_img() throws Exception {
        test("nav", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_input() throws Exception {
        test("nav", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _nav_isindex() throws Exception {
        test("nav", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_keygen() throws Exception {
        test("nav", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_link() throws Exception {
        test("nav", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_meta() throws Exception {
        test("nav", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_param() throws Exception {
        test("nav", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_script() throws Exception {
        test("nav", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_source() throws Exception {
        test("nav", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_track() throws Exception {
        test("nav", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nav_wbr() throws Exception {
        test("nav", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_area() throws Exception {
        test("nextid", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_base() throws Exception {
        test("nextid", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_basefont() throws Exception {
        test("nextid", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_bgsound() throws Exception {
        test("nextid", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_br() throws Exception {
        test("nextid", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _nextid_command() throws Exception {
        test("nextid", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_embed() throws Exception {
        test("nextid", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_hr() throws Exception {
        test("nextid", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_image() throws Exception {
        test("nextid", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_img() throws Exception {
        test("nextid", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_input() throws Exception {
        test("nextid", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _nextid_isindex() throws Exception {
        test("nextid", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_keygen() throws Exception {
        test("nextid", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_link() throws Exception {
        test("nextid", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_meta() throws Exception {
        test("nextid", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_param() throws Exception {
        test("nextid", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_script() throws Exception {
        test("nextid", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_source() throws Exception {
        test("nextid", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_track() throws Exception {
        test("nextid", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nextid_wbr() throws Exception {
        test("nextid", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_area() throws Exception {
        test("nobr", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_base() throws Exception {
        test("nobr", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_basefont() throws Exception {
        test("nobr", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_bgsound() throws Exception {
        test("nobr", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_br() throws Exception {
        test("nobr", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _nobr_command() throws Exception {
        test("nobr", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_embed() throws Exception {
        test("nobr", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_hr() throws Exception {
        test("nobr", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_image() throws Exception {
        test("nobr", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_img() throws Exception {
        test("nobr", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_input() throws Exception {
        test("nobr", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _nobr_isindex() throws Exception {
        test("nobr", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_keygen() throws Exception {
        test("nobr", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_link() throws Exception {
        test("nobr", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_meta() throws Exception {
        test("nobr", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _nobr_nobr() throws Exception {
        test("nobr", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_param() throws Exception {
        test("nobr", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_script() throws Exception {
        test("nobr", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_source() throws Exception {
        test("nobr", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_track() throws Exception {
        test("nobr", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nobr_wbr() throws Exception {
        test("nobr", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_area() throws Exception {
        test("noembed", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_base() throws Exception {
        test("noembed", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_basefont() throws Exception {
        test("noembed", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_bgsound() throws Exception {
        test("noembed", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_br() throws Exception {
        test("noembed", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented({CHROME, IE})
    public void _noembed_command() throws Exception {
        test("noembed", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_embed() throws Exception {
        test("noembed", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_hr() throws Exception {
        test("noembed", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_image() throws Exception {
        test("noembed", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_img() throws Exception {
        test("noembed", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_input() throws Exception {
        test("noembed", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_keygen() throws Exception {
        test("noembed", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_link() throws Exception {
        test("noembed", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_meta() throws Exception {
        test("noembed", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_param() throws Exception {
        test("noembed", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_script() throws Exception {
        test("noembed", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_source() throws Exception {
        test("noembed", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_track() throws Exception {
        test("noembed", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _noembed_wbr() throws Exception {
        test("noembed", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_area() throws Exception {
        test("nolayer", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_base() throws Exception {
        test("nolayer", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_basefont() throws Exception {
        test("nolayer", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_bgsound() throws Exception {
        test("nolayer", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_br() throws Exception {
        test("nolayer", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _nolayer_command() throws Exception {
        test("nolayer", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_embed() throws Exception {
        test("nolayer", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_hr() throws Exception {
        test("nolayer", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_image() throws Exception {
        test("nolayer", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_img() throws Exception {
        test("nolayer", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_input() throws Exception {
        test("nolayer", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _nolayer_isindex() throws Exception {
        test("nolayer", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_keygen() throws Exception {
        test("nolayer", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_link() throws Exception {
        test("nolayer", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_meta() throws Exception {
        test("nolayer", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_param() throws Exception {
        test("nolayer", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_script() throws Exception {
        test("nolayer", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_source() throws Exception {
        test("nolayer", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_track() throws Exception {
        test("nolayer", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _nolayer_wbr() throws Exception {
        test("nolayer", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_area() throws Exception {
        test("object", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_base() throws Exception {
        test("object", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_basefont() throws Exception {
        test("object", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_bgsound() throws Exception {
        test("object", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_br() throws Exception {
        test("object", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _object_command() throws Exception {
        test("object", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_embed() throws Exception {
        test("object", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_hr() throws Exception {
        test("object", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_image() throws Exception {
        test("object", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_img() throws Exception {
        test("object", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_input() throws Exception {
        test("object", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _object_isindex() throws Exception {
        test("object", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_keygen() throws Exception {
        test("object", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_link() throws Exception {
        test("object", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_meta() throws Exception {
        test("object", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_param() throws Exception {
        test("object", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_script() throws Exception {
        test("object", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_source() throws Exception {
        test("object", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_track() throws Exception {
        test("object", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _object_wbr() throws Exception {
        test("object", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_area() throws Exception {
        test("ol", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_base() throws Exception {
        test("ol", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_basefont() throws Exception {
        test("ol", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_bgsound() throws Exception {
        test("ol", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_br() throws Exception {
        test("ol", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _ol_command() throws Exception {
        test("ol", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_embed() throws Exception {
        test("ol", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_hr() throws Exception {
        test("ol", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_image() throws Exception {
        test("ol", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_img() throws Exception {
        test("ol", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_input() throws Exception {
        test("ol", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _ol_isindex() throws Exception {
        test("ol", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_keygen() throws Exception {
        test("ol", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_link() throws Exception {
        test("ol", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_meta() throws Exception {
        test("ol", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_param() throws Exception {
        test("ol", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_script() throws Exception {
        test("ol", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_source() throws Exception {
        test("ol", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_track() throws Exception {
        test("ol", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ol_wbr() throws Exception {
        test("ol", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_area() throws Exception {
        test("optgroup", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_base() throws Exception {
        test("optgroup", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_basefont() throws Exception {
        test("optgroup", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_bgsound() throws Exception {
        test("optgroup", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_br() throws Exception {
        test("optgroup", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _optgroup_command() throws Exception {
        test("optgroup", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_embed() throws Exception {
        test("optgroup", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_hr() throws Exception {
        test("optgroup", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_image() throws Exception {
        test("optgroup", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_img() throws Exception {
        test("optgroup", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_input() throws Exception {
        test("optgroup", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _optgroup_isindex() throws Exception {
        test("optgroup", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_keygen() throws Exception {
        test("optgroup", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_link() throws Exception {
        test("optgroup", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_meta() throws Exception {
        test("optgroup", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_param() throws Exception {
        test("optgroup", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_script() throws Exception {
        test("optgroup", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_source() throws Exception {
        test("optgroup", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_track() throws Exception {
        test("optgroup", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _optgroup_wbr() throws Exception {
        test("optgroup", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_area() throws Exception {
        test("option", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_base() throws Exception {
        test("option", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_basefont() throws Exception {
        test("option", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_bgsound() throws Exception {
        test("option", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_br() throws Exception {
        test("option", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _option_command() throws Exception {
        test("option", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_embed() throws Exception {
        test("option", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_hr() throws Exception {
        test("option", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_image() throws Exception {
        test("option", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_img() throws Exception {
        test("option", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_input() throws Exception {
        test("option", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _option_isindex() throws Exception {
        test("option", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_keygen() throws Exception {
        test("option", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_link() throws Exception {
        test("option", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_meta() throws Exception {
        test("option", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _option_optgroup() throws Exception {
        test("option", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _option_option() throws Exception {
        test("option", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_param() throws Exception {
        test("option", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_script() throws Exception {
        test("option", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_source() throws Exception {
        test("option", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_track() throws Exception {
        test("option", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _option_wbr() throws Exception {
        test("option", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_area() throws Exception {
        test("output", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_base() throws Exception {
        test("output", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_basefont() throws Exception {
        test("output", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_bgsound() throws Exception {
        test("output", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_br() throws Exception {
        test("output", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _output_command() throws Exception {
        test("output", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_embed() throws Exception {
        test("output", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_hr() throws Exception {
        test("output", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_image() throws Exception {
        test("output", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_img() throws Exception {
        test("output", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_input() throws Exception {
        test("output", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _output_isindex() throws Exception {
        test("output", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_keygen() throws Exception {
        test("output", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_link() throws Exception {
        test("output", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_meta() throws Exception {
        test("output", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_param() throws Exception {
        test("output", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_script() throws Exception {
        test("output", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_source() throws Exception {
        test("output", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_track() throws Exception {
        test("output", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _output_wbr() throws Exception {
        test("output", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_address() throws Exception {
        test("p", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_area() throws Exception {
        test("p", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_article() throws Exception {
        test("p", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_aside() throws Exception {
        test("p", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_base() throws Exception {
        test("p", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_basefont() throws Exception {
        test("p", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_bgsound() throws Exception {
        test("p", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_blockquote() throws Exception {
        test("p", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_br() throws Exception {
        test("p", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_center() throws Exception {
        test("p", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _p_command() throws Exception {
        test("p", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_dd() throws Exception {
        test("p", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_details() throws Exception {
        test("p", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            FF = "0")
    @NotYetImplemented(FF)
    public void _p_dialog() throws Exception {
        test("p", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_dir() throws Exception {
        test("p", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_div() throws Exception {
        test("p", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_dl() throws Exception {
        test("p", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_dt() throws Exception {
        test("p", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_embed() throws Exception {
        test("p", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_fieldset() throws Exception {
        test("p", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_figcaption() throws Exception {
        test("p", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_figure() throws Exception {
        test("p", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_footer() throws Exception {
        test("p", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_form() throws Exception {
        test("p", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_h1() throws Exception {
        test("p", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_h2() throws Exception {
        test("p", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_h3() throws Exception {
        test("p", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_h4() throws Exception {
        test("p", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_h5() throws Exception {
        test("p", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_h6() throws Exception {
        test("p", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_header() throws Exception {
        test("p", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_hr() throws Exception {
        test("p", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_image() throws Exception {
        test("p", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_img() throws Exception {
        test("p", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_input() throws Exception {
        test("p", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "0")
    public void _p_isindex() throws Exception {
        test("p", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_keygen() throws Exception {
        test("p", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_li() throws Exception {
        test("p", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_link() throws Exception {
        test("p", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_listing() throws Exception {
        test("p", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "0",
            IE = "1")
    public void _p_main() throws Exception {
        test("p", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_menu() throws Exception {
        test("p", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_meta() throws Exception {
        test("p", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_nav() throws Exception {
        test("p", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_ol() throws Exception {
        test("p", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_p() throws Exception {
        test("p", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_param() throws Exception {
        test("p", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_plaintext() throws Exception {
        test("p", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_pre() throws Exception {
        test("p", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_script() throws Exception {
        test("p", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_section() throws Exception {
        test("p", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_source() throws Exception {
        test("p", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_summary() throws Exception {
        test("p", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_track() throws Exception {
        test("p", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_ul() throws Exception {
        test("p", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _p_wbr() throws Exception {
        test("p", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _p_xmp() throws Exception {
        test("p", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_a() throws Exception {
        test("param", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_abbr() throws Exception {
        test("param", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_acronym() throws Exception {
        test("param", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_address() throws Exception {
        test("param", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_applet() throws Exception {
        test("param", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_area() throws Exception {
        test("param", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_article() throws Exception {
        test("param", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_aside() throws Exception {
        test("param", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_audio() throws Exception {
        test("param", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_b() throws Exception {
        test("param", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_base() throws Exception {
        test("param", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_basefont() throws Exception {
        test("param", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_bdi() throws Exception {
        test("param", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_bdo() throws Exception {
        test("param", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_bgsound() throws Exception {
        test("param", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_big() throws Exception {
        test("param", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_blink() throws Exception {
        test("param", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_blockquote() throws Exception {
        test("param", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_body() throws Exception {
        test("param", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_br() throws Exception {
        test("param", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_button() throws Exception {
        test("param", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_canvas() throws Exception {
        test("param", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_caption() throws Exception {
        test("param", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_center() throws Exception {
        test("param", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_cite() throws Exception {
        test("param", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_code() throws Exception {
        test("param", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_col() throws Exception {
        test("param", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_colgroup() throws Exception {
        test("param", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_command() throws Exception {
        test("param", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_content() throws Exception {
        test("param", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_data() throws Exception {
        test("param", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_datalist() throws Exception {
        test("param", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_dd() throws Exception {
        test("param", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_del() throws Exception {
        test("param", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_details() throws Exception {
        test("param", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_dfn() throws Exception {
        test("param", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_dialog() throws Exception {
        test("param", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_dir() throws Exception {
        test("param", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_div() throws Exception {
        test("param", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_dl() throws Exception {
        test("param", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_dt() throws Exception {
        test("param", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_em() throws Exception {
        test("param", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_embed() throws Exception {
        test("param", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_fieldset() throws Exception {
        test("param", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_figcaption() throws Exception {
        test("param", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_figure() throws Exception {
        test("param", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_font() throws Exception {
        test("param", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_footer() throws Exception {
        test("param", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_form() throws Exception {
        test("param", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_frame() throws Exception {
        test("param", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_frameset() throws Exception {
        test("param", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_h1() throws Exception {
        test("param", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_h2() throws Exception {
        test("param", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_h3() throws Exception {
        test("param", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_h4() throws Exception {
        test("param", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_h5() throws Exception {
        test("param", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_h6() throws Exception {
        test("param", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_head() throws Exception {
        test("param", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_header() throws Exception {
        test("param", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_hr() throws Exception {
        test("param", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_html() throws Exception {
        test("param", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_i() throws Exception {
        test("param", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_iframe() throws Exception {
        test("param", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_image() throws Exception {
        test("param", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_img() throws Exception {
        test("param", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_input() throws Exception {
        test("param", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_ins() throws Exception {
        test("param", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_isindex() throws Exception {
        test("param", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_kbd() throws Exception {
        test("param", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_keygen() throws Exception {
        test("param", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_label() throws Exception {
        test("param", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_layer() throws Exception {
        test("param", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_legend() throws Exception {
        test("param", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_li() throws Exception {
        test("param", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_link() throws Exception {
        test("param", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_listing() throws Exception {
        test("param", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_main() throws Exception {
        test("param", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_map() throws Exception {
        test("param", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_mark() throws Exception {
        test("param", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_marquee() throws Exception {
        test("param", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_menu() throws Exception {
        test("param", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_menuitem() throws Exception {
        test("param", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_meta() throws Exception {
        test("param", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_meter() throws Exception {
        test("param", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_multicol() throws Exception {
        test("param", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_nav() throws Exception {
        test("param", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_nextid() throws Exception {
        test("param", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_nobr() throws Exception {
        test("param", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_noembed() throws Exception {
        test("param", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_noframes() throws Exception {
        test("param", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_nolayer() throws Exception {
        test("param", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_noscript() throws Exception {
        test("param", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_object() throws Exception {
        test("param", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_ol() throws Exception {
        test("param", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_optgroup() throws Exception {
        test("param", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_option() throws Exception {
        test("param", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_output() throws Exception {
        test("param", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_p() throws Exception {
        test("param", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_param() throws Exception {
        test("param", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_picture() throws Exception {
        test("param", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_plaintext() throws Exception {
        test("param", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_pre() throws Exception {
        test("param", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_progress() throws Exception {
        test("param", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_q() throws Exception {
        test("param", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_rp() throws Exception {
        test("param", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_rt() throws Exception {
        test("param", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_ruby() throws Exception {
        test("param", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_s() throws Exception {
        test("param", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_samp() throws Exception {
        test("param", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_script() throws Exception {
        test("param", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_section() throws Exception {
        test("param", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_select() throws Exception {
        test("param", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_slot() throws Exception {
        test("param", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_small() throws Exception {
        test("param", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_source() throws Exception {
        test("param", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_span() throws Exception {
        test("param", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_strike() throws Exception {
        test("param", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_strong() throws Exception {
        test("param", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_style() throws Exception {
        test("param", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_sub() throws Exception {
        test("param", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_summary() throws Exception {
        test("param", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_sup() throws Exception {
        test("param", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_svg() throws Exception {
        test("param", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_table() throws Exception {
        test("param", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_tbody() throws Exception {
        test("param", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_td() throws Exception {
        test("param", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_template() throws Exception {
        test("param", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_textarea() throws Exception {
        test("param", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_tfoot() throws Exception {
        test("param", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_th() throws Exception {
        test("param", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_thead() throws Exception {
        test("param", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_time() throws Exception {
        test("param", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_title() throws Exception {
        test("param", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_tr() throws Exception {
        test("param", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_track() throws Exception {
        test("param", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_tt() throws Exception {
        test("param", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_u() throws Exception {
        test("param", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_ul() throws Exception {
        test("param", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_var() throws Exception {
        test("param", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_video() throws Exception {
        test("param", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_wbr() throws Exception {
        test("param", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _param_xmp() throws Exception {
        test("param", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_area() throws Exception {
        test("picture", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_base() throws Exception {
        test("picture", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_basefont() throws Exception {
        test("picture", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_bgsound() throws Exception {
        test("picture", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_br() throws Exception {
        test("picture", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _picture_command() throws Exception {
        test("picture", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_embed() throws Exception {
        test("picture", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_hr() throws Exception {
        test("picture", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_image() throws Exception {
        test("picture", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_img() throws Exception {
        test("picture", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_input() throws Exception {
        test("picture", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _picture_isindex() throws Exception {
        test("picture", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_keygen() throws Exception {
        test("picture", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_link() throws Exception {
        test("picture", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_meta() throws Exception {
        test("picture", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_param() throws Exception {
        test("picture", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_script() throws Exception {
        test("picture", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_source() throws Exception {
        test("picture", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_track() throws Exception {
        test("picture", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _picture_wbr() throws Exception {
        test("picture", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_area() throws Exception {
        test("pre", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_base() throws Exception {
        test("pre", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_basefont() throws Exception {
        test("pre", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_bgsound() throws Exception {
        test("pre", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_br() throws Exception {
        test("pre", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _pre_command() throws Exception {
        test("pre", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_embed() throws Exception {
        test("pre", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_hr() throws Exception {
        test("pre", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_image() throws Exception {
        test("pre", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_img() throws Exception {
        test("pre", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_input() throws Exception {
        test("pre", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _pre_isindex() throws Exception {
        test("pre", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_keygen() throws Exception {
        test("pre", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_link() throws Exception {
        test("pre", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_meta() throws Exception {
        test("pre", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_param() throws Exception {
        test("pre", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_script() throws Exception {
        test("pre", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_source() throws Exception {
        test("pre", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_track() throws Exception {
        test("pre", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _pre_wbr() throws Exception {
        test("pre", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_area() throws Exception {
        test("progress", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_base() throws Exception {
        test("progress", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_basefont() throws Exception {
        test("progress", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_bgsound() throws Exception {
        test("progress", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_br() throws Exception {
        test("progress", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _progress_command() throws Exception {
        test("progress", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_embed() throws Exception {
        test("progress", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_hr() throws Exception {
        test("progress", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_image() throws Exception {
        test("progress", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_img() throws Exception {
        test("progress", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_input() throws Exception {
        test("progress", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _progress_isindex() throws Exception {
        test("progress", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_keygen() throws Exception {
        test("progress", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_link() throws Exception {
        test("progress", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_meta() throws Exception {
        test("progress", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_param() throws Exception {
        test("progress", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_script() throws Exception {
        test("progress", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_source() throws Exception {
        test("progress", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_track() throws Exception {
        test("progress", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _progress_wbr() throws Exception {
        test("progress", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_area() throws Exception {
        test("q", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_base() throws Exception {
        test("q", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_basefont() throws Exception {
        test("q", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_bgsound() throws Exception {
        test("q", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_br() throws Exception {
        test("q", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _q_command() throws Exception {
        test("q", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_embed() throws Exception {
        test("q", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_hr() throws Exception {
        test("q", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_image() throws Exception {
        test("q", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_img() throws Exception {
        test("q", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_input() throws Exception {
        test("q", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _q_isindex() throws Exception {
        test("q", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_keygen() throws Exception {
        test("q", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_link() throws Exception {
        test("q", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_meta() throws Exception {
        test("q", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_param() throws Exception {
        test("q", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_script() throws Exception {
        test("q", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_source() throws Exception {
        test("q", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_track() throws Exception {
        test("q", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _q_wbr() throws Exception {
        test("q", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_area() throws Exception {
        test("rp", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_base() throws Exception {
        test("rp", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_basefont() throws Exception {
        test("rp", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_bgsound() throws Exception {
        test("rp", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_br() throws Exception {
        test("rp", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _rp_command() throws Exception {
        test("rp", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_embed() throws Exception {
        test("rp", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_hr() throws Exception {
        test("rp", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_image() throws Exception {
        test("rp", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_img() throws Exception {
        test("rp", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_input() throws Exception {
        test("rp", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _rp_isindex() throws Exception {
        test("rp", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_keygen() throws Exception {
        test("rp", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_link() throws Exception {
        test("rp", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_meta() throws Exception {
        test("rp", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_param() throws Exception {
        test("rp", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _rp_rt() throws Exception {
        test("rp", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_script() throws Exception {
        test("rp", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_source() throws Exception {
        test("rp", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_track() throws Exception {
        test("rp", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rp_wbr() throws Exception {
        test("rp", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_area() throws Exception {
        test("rt", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_base() throws Exception {
        test("rt", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_basefont() throws Exception {
        test("rt", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_bgsound() throws Exception {
        test("rt", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_br() throws Exception {
        test("rt", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _rt_command() throws Exception {
        test("rt", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_embed() throws Exception {
        test("rt", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_hr() throws Exception {
        test("rt", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_image() throws Exception {
        test("rt", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_img() throws Exception {
        test("rt", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_input() throws Exception {
        test("rt", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _rt_isindex() throws Exception {
        test("rt", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_keygen() throws Exception {
        test("rt", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_link() throws Exception {
        test("rt", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_meta() throws Exception {
        test("rt", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_param() throws Exception {
        test("rt", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_script() throws Exception {
        test("rt", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_source() throws Exception {
        test("rt", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_track() throws Exception {
        test("rt", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _rt_wbr() throws Exception {
        test("rt", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_area() throws Exception {
        test("ruby", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_base() throws Exception {
        test("ruby", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_basefont() throws Exception {
        test("ruby", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_bgsound() throws Exception {
        test("ruby", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_br() throws Exception {
        test("ruby", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _ruby_command() throws Exception {
        test("ruby", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_embed() throws Exception {
        test("ruby", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_hr() throws Exception {
        test("ruby", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_image() throws Exception {
        test("ruby", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_img() throws Exception {
        test("ruby", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_input() throws Exception {
        test("ruby", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _ruby_isindex() throws Exception {
        test("ruby", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_keygen() throws Exception {
        test("ruby", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_link() throws Exception {
        test("ruby", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_meta() throws Exception {
        test("ruby", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_param() throws Exception {
        test("ruby", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_script() throws Exception {
        test("ruby", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_source() throws Exception {
        test("ruby", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_track() throws Exception {
        test("ruby", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _ruby_wbr() throws Exception {
        test("ruby", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_area() throws Exception {
        test("s", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_base() throws Exception {
        test("s", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_basefont() throws Exception {
        test("s", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_bgsound() throws Exception {
        test("s", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_br() throws Exception {
        test("s", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _s_command() throws Exception {
        test("s", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_embed() throws Exception {
        test("s", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_hr() throws Exception {
        test("s", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_image() throws Exception {
        test("s", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_img() throws Exception {
        test("s", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_input() throws Exception {
        test("s", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _s_isindex() throws Exception {
        test("s", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_keygen() throws Exception {
        test("s", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_link() throws Exception {
        test("s", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_meta() throws Exception {
        test("s", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_param() throws Exception {
        test("s", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_script() throws Exception {
        test("s", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_source() throws Exception {
        test("s", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_track() throws Exception {
        test("s", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _s_wbr() throws Exception {
        test("s", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_area() throws Exception {
        test("samp", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_base() throws Exception {
        test("samp", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_basefont() throws Exception {
        test("samp", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_bgsound() throws Exception {
        test("samp", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_br() throws Exception {
        test("samp", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _samp_command() throws Exception {
        test("samp", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_embed() throws Exception {
        test("samp", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_hr() throws Exception {
        test("samp", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_image() throws Exception {
        test("samp", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_img() throws Exception {
        test("samp", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_input() throws Exception {
        test("samp", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _samp_isindex() throws Exception {
        test("samp", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_keygen() throws Exception {
        test("samp", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_link() throws Exception {
        test("samp", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_meta() throws Exception {
        test("samp", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_param() throws Exception {
        test("samp", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_script() throws Exception {
        test("samp", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_source() throws Exception {
        test("samp", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_track() throws Exception {
        test("samp", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _samp_wbr() throws Exception {
        test("samp", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    public void _script_param() throws Exception {
        test("script", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    public void _script_script() throws Exception {
        test("script", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_area() throws Exception {
        test("section", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_base() throws Exception {
        test("section", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_basefont() throws Exception {
        test("section", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_bgsound() throws Exception {
        test("section", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_br() throws Exception {
        test("section", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _section_command() throws Exception {
        test("section", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_embed() throws Exception {
        test("section", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_hr() throws Exception {
        test("section", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_image() throws Exception {
        test("section", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_img() throws Exception {
        test("section", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_input() throws Exception {
        test("section", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _section_isindex() throws Exception {
        test("section", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_keygen() throws Exception {
        test("section", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_link() throws Exception {
        test("section", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_meta() throws Exception {
        test("section", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_param() throws Exception {
        test("section", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_script() throws Exception {
        test("section", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_source() throws Exception {
        test("section", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_track() throws Exception {
        test("section", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _section_wbr() throws Exception {
        test("section", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_area() throws Exception {
        test("select", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_base() throws Exception {
        test("select", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_basefont() throws Exception {
        test("select", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_bgsound() throws Exception {
        test("select", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_br() throws Exception {
        test("select", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented({CHROME, IE})
    public void _select_command() throws Exception {
        test("select", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_embed() throws Exception {
        test("select", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_hr() throws Exception {
        test("select", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_image() throws Exception {
        test("select", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_img() throws Exception {
        test("select", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _select_input() throws Exception {
        test("select", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _select_keygen() throws Exception {
        test("select", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_link() throws Exception {
        test("select", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_meta() throws Exception {
        test("select", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_param() throws Exception {
        test("select", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _select_script() throws Exception {
        test("select", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_section() throws Exception {
        test("select", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _select_select() throws Exception {
        test("select", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_source() throws Exception {
        test("select", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _select_textarea() throws Exception {
        test("select", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_track() throws Exception {
        test("select", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("1")
    @NotYetImplemented
    public void _select_wbr() throws Exception {
        test("select", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_area() throws Exception {
        test("slot", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_base() throws Exception {
        test("slot", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_basefont() throws Exception {
        test("slot", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_bgsound() throws Exception {
        test("slot", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_br() throws Exception {
        test("slot", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _slot_command() throws Exception {
        test("slot", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_embed() throws Exception {
        test("slot", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_hr() throws Exception {
        test("slot", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_image() throws Exception {
        test("slot", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_img() throws Exception {
        test("slot", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_input() throws Exception {
        test("slot", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _slot_isindex() throws Exception {
        test("slot", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_keygen() throws Exception {
        test("slot", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_link() throws Exception {
        test("slot", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_meta() throws Exception {
        test("slot", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_param() throws Exception {
        test("slot", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_script() throws Exception {
        test("slot", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_source() throws Exception {
        test("slot", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_track() throws Exception {
        test("slot", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _slot_wbr() throws Exception {
        test("slot", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_area() throws Exception {
        test("small", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_base() throws Exception {
        test("small", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_basefont() throws Exception {
        test("small", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_bgsound() throws Exception {
        test("small", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_br() throws Exception {
        test("small", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _small_command() throws Exception {
        test("small", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_embed() throws Exception {
        test("small", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_hr() throws Exception {
        test("small", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_image() throws Exception {
        test("small", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_img() throws Exception {
        test("small", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_input() throws Exception {
        test("small", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _small_isindex() throws Exception {
        test("small", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_keygen() throws Exception {
        test("small", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_link() throws Exception {
        test("small", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_meta() throws Exception {
        test("small", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_param() throws Exception {
        test("small", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_script() throws Exception {
        test("small", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_source() throws Exception {
        test("small", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_track() throws Exception {
        test("small", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _small_wbr() throws Exception {
        test("small", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_a() throws Exception {
        test("source", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_abbr() throws Exception {
        test("source", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_acronym() throws Exception {
        test("source", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_address() throws Exception {
        test("source", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_applet() throws Exception {
        test("source", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_area() throws Exception {
        test("source", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_article() throws Exception {
        test("source", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_aside() throws Exception {
        test("source", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_audio() throws Exception {
        test("source", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_b() throws Exception {
        test("source", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_base() throws Exception {
        test("source", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_basefont() throws Exception {
        test("source", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_bdi() throws Exception {
        test("source", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_bdo() throws Exception {
        test("source", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_bgsound() throws Exception {
        test("source", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_big() throws Exception {
        test("source", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_blink() throws Exception {
        test("source", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_blockquote() throws Exception {
        test("source", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_body() throws Exception {
        test("source", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_br() throws Exception {
        test("source", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_button() throws Exception {
        test("source", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_canvas() throws Exception {
        test("source", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_caption() throws Exception {
        test("source", "caption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_center() throws Exception {
        test("source", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_cite() throws Exception {
        test("source", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_code() throws Exception {
        test("source", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_col() throws Exception {
        test("source", "col");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_colgroup() throws Exception {
        test("source", "colgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_command() throws Exception {
        test("source", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_content() throws Exception {
        test("source", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_data() throws Exception {
        test("source", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_datalist() throws Exception {
        test("source", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_dd() throws Exception {
        test("source", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_del() throws Exception {
        test("source", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_details() throws Exception {
        test("source", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_dfn() throws Exception {
        test("source", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_dialog() throws Exception {
        test("source", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_dir() throws Exception {
        test("source", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_div() throws Exception {
        test("source", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_dl() throws Exception {
        test("source", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_dt() throws Exception {
        test("source", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_em() throws Exception {
        test("source", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_embed() throws Exception {
        test("source", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_fieldset() throws Exception {
        test("source", "fieldset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_figcaption() throws Exception {
        test("source", "figcaption");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_figure() throws Exception {
        test("source", "figure");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_font() throws Exception {
        test("source", "font");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_footer() throws Exception {
        test("source", "footer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_form() throws Exception {
        test("source", "form");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_frame() throws Exception {
        test("source", "frame");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_frameset() throws Exception {
        test("source", "frameset");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_h1() throws Exception {
        test("source", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_h2() throws Exception {
        test("source", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_h3() throws Exception {
        test("source", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_h4() throws Exception {
        test("source", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_h5() throws Exception {
        test("source", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_h6() throws Exception {
        test("source", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_head() throws Exception {
        test("source", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_header() throws Exception {
        test("source", "header");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_hr() throws Exception {
        test("source", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_html() throws Exception {
        test("source", "html");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_i() throws Exception {
        test("source", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_iframe() throws Exception {
        test("source", "iframe");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_image() throws Exception {
        test("source", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_img() throws Exception {
        test("source", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_input() throws Exception {
        test("source", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_ins() throws Exception {
        test("source", "ins");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_isindex() throws Exception {
        test("source", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_kbd() throws Exception {
        test("source", "kbd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_keygen() throws Exception {
        test("source", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_label() throws Exception {
        test("source", "label");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_layer() throws Exception {
        test("source", "layer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_legend() throws Exception {
        test("source", "legend");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_li() throws Exception {
        test("source", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_link() throws Exception {
        test("source", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_listing() throws Exception {
        test("source", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_main() throws Exception {
        test("source", "main");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_map() throws Exception {
        test("source", "map");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_mark() throws Exception {
        test("source", "mark");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_marquee() throws Exception {
        test("source", "marquee");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_menu() throws Exception {
        test("source", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_menuitem() throws Exception {
        test("source", "menuitem");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_meta() throws Exception {
        test("source", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_meter() throws Exception {
        test("source", "meter");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_multicol() throws Exception {
        test("source", "multicol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_nav() throws Exception {
        test("source", "nav");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_nextid() throws Exception {
        test("source", "nextid");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_nobr() throws Exception {
        test("source", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_noembed() throws Exception {
        test("source", "noembed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_noframes() throws Exception {
        test("source", "noframes");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_nolayer() throws Exception {
        test("source", "nolayer");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_noscript() throws Exception {
        test("source", "noscript");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_object() throws Exception {
        test("source", "object");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_ol() throws Exception {
        test("source", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_optgroup() throws Exception {
        test("source", "optgroup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_option() throws Exception {
        test("source", "option");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_output() throws Exception {
        test("source", "output");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_p() throws Exception {
        test("source", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_param() throws Exception {
        test("source", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_picture() throws Exception {
        test("source", "picture");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_plaintext() throws Exception {
        test("source", "plaintext");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_pre() throws Exception {
        test("source", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_progress() throws Exception {
        test("source", "progress");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_q() throws Exception {
        test("source", "q");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_rp() throws Exception {
        test("source", "rp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_rt() throws Exception {
        test("source", "rt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_ruby() throws Exception {
        test("source", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_s() throws Exception {
        test("source", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_samp() throws Exception {
        test("source", "samp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_script() throws Exception {
        test("source", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_section() throws Exception {
        test("source", "section");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_select() throws Exception {
        test("source", "select");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_slot() throws Exception {
        test("source", "slot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_small() throws Exception {
        test("source", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_source() throws Exception {
        test("source", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_span() throws Exception {
        test("source", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_strike() throws Exception {
        test("source", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_strong() throws Exception {
        test("source", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_style() throws Exception {
        test("source", "style");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_sub() throws Exception {
        test("source", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_summary() throws Exception {
        test("source", "summary");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_sup() throws Exception {
        test("source", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_svg() throws Exception {
        test("source", "svg");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_table() throws Exception {
        test("source", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_tbody() throws Exception {
        test("source", "tbody");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_td() throws Exception {
        test("source", "td");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_template() throws Exception {
        test("source", "template");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_textarea() throws Exception {
        test("source", "textarea");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_tfoot() throws Exception {
        test("source", "tfoot");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_th() throws Exception {
        test("source", "th");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_thead() throws Exception {
        test("source", "thead");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_time() throws Exception {
        test("source", "time");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_title() throws Exception {
        test("source", "title");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_tr() throws Exception {
        test("source", "tr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_track() throws Exception {
        test("source", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_tt() throws Exception {
        test("source", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_u() throws Exception {
        test("source", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_ul() throws Exception {
        test("source", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_var() throws Exception {
        test("source", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_video() throws Exception {
        test("source", "video");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_wbr() throws Exception {
        test("source", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _source_xmp() throws Exception {
        test("source", "xmp");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_area() throws Exception {
        test("span", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_base() throws Exception {
        test("span", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_basefont() throws Exception {
        test("span", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_bgsound() throws Exception {
        test("span", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_br() throws Exception {
        test("span", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _span_command() throws Exception {
        test("span", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_embed() throws Exception {
        test("span", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_hr() throws Exception {
        test("span", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_image() throws Exception {
        test("span", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_img() throws Exception {
        test("span", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_input() throws Exception {
        test("span", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _span_isindex() throws Exception {
        test("span", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_keygen() throws Exception {
        test("span", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_link() throws Exception {
        test("span", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_meta() throws Exception {
        test("span", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_param() throws Exception {
        test("span", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_script() throws Exception {
        test("span", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_source() throws Exception {
        test("span", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_track() throws Exception {
        test("span", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _span_wbr() throws Exception {
        test("span", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_area() throws Exception {
        test("strike", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_base() throws Exception {
        test("strike", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_basefont() throws Exception {
        test("strike", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_bgsound() throws Exception {
        test("strike", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_br() throws Exception {
        test("strike", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _strike_command() throws Exception {
        test("strike", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_embed() throws Exception {
        test("strike", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_hr() throws Exception {
        test("strike", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_image() throws Exception {
        test("strike", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_img() throws Exception {
        test("strike", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_input() throws Exception {
        test("strike", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _strike_isindex() throws Exception {
        test("strike", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_keygen() throws Exception {
        test("strike", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_link() throws Exception {
        test("strike", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_meta() throws Exception {
        test("strike", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_param() throws Exception {
        test("strike", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_script() throws Exception {
        test("strike", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_source() throws Exception {
        test("strike", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_track() throws Exception {
        test("strike", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strike_wbr() throws Exception {
        test("strike", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_area() throws Exception {
        test("strong", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_base() throws Exception {
        test("strong", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_basefont() throws Exception {
        test("strong", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_bgsound() throws Exception {
        test("strong", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_br() throws Exception {
        test("strong", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _strong_command() throws Exception {
        test("strong", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_embed() throws Exception {
        test("strong", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_hr() throws Exception {
        test("strong", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_image() throws Exception {
        test("strong", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_img() throws Exception {
        test("strong", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_input() throws Exception {
        test("strong", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _strong_isindex() throws Exception {
        test("strong", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_keygen() throws Exception {
        test("strong", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_link() throws Exception {
        test("strong", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_meta() throws Exception {
        test("strong", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_param() throws Exception {
        test("strong", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_script() throws Exception {
        test("strong", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_source() throws Exception {
        test("strong", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_track() throws Exception {
        test("strong", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _strong_wbr() throws Exception {
        test("strong", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_area() throws Exception {
        test("sub", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_base() throws Exception {
        test("sub", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_basefont() throws Exception {
        test("sub", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_bgsound() throws Exception {
        test("sub", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_br() throws Exception {
        test("sub", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _sub_command() throws Exception {
        test("sub", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_embed() throws Exception {
        test("sub", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_hr() throws Exception {
        test("sub", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_image() throws Exception {
        test("sub", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_img() throws Exception {
        test("sub", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_input() throws Exception {
        test("sub", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _sub_isindex() throws Exception {
        test("sub", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_keygen() throws Exception {
        test("sub", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_link() throws Exception {
        test("sub", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_meta() throws Exception {
        test("sub", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_param() throws Exception {
        test("sub", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_script() throws Exception {
        test("sub", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_source() throws Exception {
        test("sub", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_track() throws Exception {
        test("sub", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sub_wbr() throws Exception {
        test("sub", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_area() throws Exception {
        test("summary", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_base() throws Exception {
        test("summary", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_basefont() throws Exception {
        test("summary", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_bgsound() throws Exception {
        test("summary", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_br() throws Exception {
        test("summary", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _summary_command() throws Exception {
        test("summary", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_embed() throws Exception {
        test("summary", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_hr() throws Exception {
        test("summary", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_image() throws Exception {
        test("summary", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_img() throws Exception {
        test("summary", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_input() throws Exception {
        test("summary", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _summary_isindex() throws Exception {
        test("summary", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_keygen() throws Exception {
        test("summary", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_link() throws Exception {
        test("summary", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_meta() throws Exception {
        test("summary", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_param() throws Exception {
        test("summary", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_script() throws Exception {
        test("summary", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_source() throws Exception {
        test("summary", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_track() throws Exception {
        test("summary", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _summary_wbr() throws Exception {
        test("summary", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_area() throws Exception {
        test("sup", "area");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_base() throws Exception {
        test("sup", "base");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_basefont() throws Exception {
        test("sup", "basefont");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_bgsound() throws Exception {
        test("sup", "bgsound");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_br() throws Exception {
        test("sup", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "2",
            FF = "1")
    public void _sup_command() throws Exception {
        test("sup", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_embed() throws Exception {
        test("sup", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_hr() throws Exception {
        test("sup", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_image() throws Exception {
        test("sup", "image");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_img() throws Exception {
        test("sup", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_input() throws Exception {
        test("sup", "input");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            IE = "2")
    @NotYetImplemented(IE)
    public void _sup_isindex() throws Exception {
        test("sup", "isindex");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_keygen() throws Exception {
        test("sup", "keygen");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_link() throws Exception {
        test("sup", "link");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_meta() throws Exception {
        test("sup", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_param() throws Exception {
        test("sup", "param");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_script() throws Exception {
        test("sup", "script");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_source() throws Exception {
        test("sup", "source");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_track() throws Exception {
        test("sup", "track");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("2")
    public void _sup_wbr() throws Exception {
        test("sup", "wbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_b() throws Exception {
        test("svg", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_big() throws Exception {
        test("svg", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_blockquote() throws Exception {
        test("svg", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_body() throws Exception {
        test("svg", "body");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_br() throws Exception {
        test("svg", "br");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_center() throws Exception {
        test("svg", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_code() throws Exception {
        test("svg", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_dd() throws Exception {
        test("svg", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_div() throws Exception {
        test("svg", "div");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_dl() throws Exception {
        test("svg", "dl");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_dt() throws Exception {
        test("svg", "dt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_em() throws Exception {
        test("svg", "em");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_embed() throws Exception {
        test("svg", "embed");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_h1() throws Exception {
        test("svg", "h1");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_h2() throws Exception {
        test("svg", "h2");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_h3() throws Exception {
        test("svg", "h3");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_h4() throws Exception {
        test("svg", "h4");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_h5() throws Exception {
        test("svg", "h5");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_h6() throws Exception {
        test("svg", "h6");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_head() throws Exception {
        test("svg", "head");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_hr() throws Exception {
        test("svg", "hr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_i() throws Exception {
        test("svg", "i");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_img() throws Exception {
        test("svg", "img");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_li() throws Exception {
        test("svg", "li");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_listing() throws Exception {
        test("svg", "listing");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_menu() throws Exception {
        test("svg", "menu");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_meta() throws Exception {
        test("svg", "meta");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_nobr() throws Exception {
        test("svg", "nobr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_ol() throws Exception {
        test("svg", "ol");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_p() throws Exception {
        test("svg", "p");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_pre() throws Exception {
        test("svg", "pre");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_ruby() throws Exception {
        test("svg", "ruby");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_s() throws Exception {
        test("svg", "s");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_small() throws Exception {
        test("svg", "small");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_span() throws Exception {
        test("svg", "span");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _svg_strike() throws Exception {
        test("svg", "strike");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _svg_strong() throws Exception {
        test("svg", "strong");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _svg_sub() throws Exception {
        test("svg", "sub");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _svg_sup() throws Exception {
        test("svg", "sup");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_table() throws Exception {
        test("svg", "table");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_tt() throws Exception {
        test("svg", "tt");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_u() throws Exception {
        test("svg", "u");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_ul() throws Exception {
        test("svg", "ul");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _svg_var() throws Exception {
        test("svg", "var");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_a() throws Exception {
        test("table", "a");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_abbr() throws Exception {
        test("table", "abbr");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_acronym() throws Exception {
        test("table", "acronym");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_address() throws Exception {
        test("table", "address");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_applet() throws Exception {
        test("table", "applet");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_article() throws Exception {
        test("table", "article");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_aside() throws Exception {
        test("table", "aside");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_audio() throws Exception {
        test("table", "audio");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_b() throws Exception {
        test("table", "b");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_bdi() throws Exception {
        test("table", "bdi");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_bdo() throws Exception {
        test("table", "bdo");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_big() throws Exception {
        test("table", "big");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_blink() throws Exception {
        test("table", "blink");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_blockquote() throws Exception {
        test("table", "blockquote");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    @NotYetImplemented
    public void _table_button() throws Exception {
        test("table", "button");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_canvas() throws Exception {
        test("table", "canvas");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_center() throws Exception {
        test("table", "center");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_cite() throws Exception {
        test("table", "cite");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_code() throws Exception {
        test("table", "code");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts(DEFAULT = "1",
            FF = "0")
    public void _table_command() throws Exception {
        test("table", "command");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_content() throws Exception {
        test("table", "content");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_data() throws Exception {
        test("table", "data");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_datalist() throws Exception {
        test("table", "datalist");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_dd() throws Exception {
        test("table", "dd");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_del() throws Exception {
        test("table", "del");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_details() throws Exception {
        test("table", "details");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_dfn() throws Exception {
        test("table", "dfn");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_dialog() throws Exception {
        test("table", "dialog");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_dir() throws Exception {
        test("table", "dir");
    }

    /**
     * @throws Exception if the test fails
     */
    @Test
    @Alerts("0")
    public void _table_div() throws Exception {
        test("table", "div");
    }

    /**
     * @throws Exception if the test fails
     */
