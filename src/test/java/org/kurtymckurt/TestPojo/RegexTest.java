package org.kurtymckurt.TestPojo;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojo.limiters.Limiter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegexTest {


    @Test
    public void testBasicRegex() {
        RegexObject regexTestObject = TestPojo.builder(RegexObject.class)
                .addLimiter("cve",
                        Limiter.builder()
                                .regex("CVE-\\d\\d\\d\\d-[0-9]{4,7}")
                                .build())
                .addLimiter("cwe",
                        Limiter.builder()
                                .regex("CWE-[0-9]{4}")
                                .build())
                .addProviderFunction(RegexObject::new, RegexObject.class)
                .build();
        assertNotNull(regexTestObject.cve);
        assertNotNull(regexTestObject.cwe);
    }



    @Data
    public class RegexObject {
        private String cve;
        private String cwe;
        public RegexObject() {

        }
    }
}
