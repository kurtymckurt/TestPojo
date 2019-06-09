package org.kurtymckurt.TestPojo;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.kurtymckurt.TestPojo.limiters.Limiter;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegexTest {


    @Test
    public void testBasicRegex() {
        Pattern cwe = Pattern.compile("CWE-[0-9]{4}");
        Pattern cve = Pattern.compile("CVE-\\d\\d\\d\\d-[0-9]{4,7}");
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
        assertTrue(cwe.matcher(regexTestObject.cwe).matches());
        assertTrue(cve.matcher(regexTestObject.cve).matches());
    }



    @Data
    public class RegexObject {
        private String cve;
        private String cwe;
    }
}
