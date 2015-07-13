/*
 * SonarSource :: Erlang :: Integration Tests
 * Copyright (C) 2014 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.sonar.it.erlang;

import com.sonar.orchestrator.Orchestrator;
import com.sonar.orchestrator.OrchestratorBuilder;
import com.sonar.orchestrator.build.SonarRunner;
import com.sonar.orchestrator.locator.FileLocation;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
  MetricsTest.class,
  IssueTest.class,
  TestsAndCoverageTest.class
})
public class ErlangTestSuite {

  public static final String PLUGIN_KEY = "erlang";

  @ClassRule
  public static final Orchestrator ORCHESTRATOR = initOrchestrator();

  private static Orchestrator initOrchestrator() {
    OrchestratorBuilder builder = Orchestrator.builderEnv();
    builder
      .addPlugin(PLUGIN_KEY)
      .setMainPluginKey(PLUGIN_KEY)
      .restoreProfileAtStartup(FileLocation.ofClasspath("/com/sonar/it/erlang/issue-profile.xml"))
      .restoreProfileAtStartup(FileLocation.ofClasspath("/com/sonar/it/erlang/empty-profile.xml"));

    return builder.build();
  }

  public static SonarRunner createSonarRunner() {
    SonarRunner build = SonarRunner.create();
    if (!is_multi_language()) {
      build.setProperty("sonar.language", "erlang");
    }
    return build;
  }

  private static boolean is_multi_language() {
    return is_after_sonar_4_2();
  }

  public static boolean is_after_sonar_4_2() {
    return ORCHESTRATOR.getConfiguration().getSonarVersion().isGreaterThanOrEquals("4.2");
  }

}
