package org.mega65.m65dbgui;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.mega65.m65dbgui.opcodes.*;
import org.mega65.m65dbgui.services.OpcodeServiceTest;

@Suite
@SelectClasses({  OpcodeServiceTest.class,
                  BneTests.class, CmpTests.class, IncTests.class, JmpTests.class,
                  LdaTests.class,
                  OtherOpcodesTest.class})
public class AllUnitTests {
}
