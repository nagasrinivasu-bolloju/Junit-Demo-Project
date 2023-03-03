package com.naga.suites.calculatorSuite;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.runner.RunWith;

@SuppressWarnings("deprecation")
@RunWith(JUnitPlatform.class)
@SelectPackages({"com.naga.calculations"})
@IncludeTags("Division")
public class DivisionTestSuite {

}
