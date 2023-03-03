package com.naga.suites.calculatorSuite;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Running Addition tests..")
@SelectPackages("com.naga.calculations")
@IncludeTags("Addition")
public class AddtionSuite {

}
