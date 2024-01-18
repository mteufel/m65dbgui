package org.mega65.m65dbgui;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.mega65.m65dbgui.opcodes.BneTests;
import org.mega65.m65dbgui.services.OpcodeServiceTest;

@Suite
@SelectClasses({  OpcodeServiceTest.class,
                  BneTests.class })
public class AllUnitTests {
}
