package org.mega65.m65dbgui;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.mega65.m65dbgui.services.DisassemblerTests;
import org.mega65.m65dbgui.services.OpcodeServiceTest;

@Suite
@SelectClasses({  OpcodeServiceTest.class,
                  DisassemblerTests.class })
public class AllUnitTests {
}
