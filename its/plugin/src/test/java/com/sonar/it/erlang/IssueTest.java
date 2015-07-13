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
import com.sonar.orchestrator.build.SonarRunner;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.sonar.wsclient.issue.Issue;
import org.sonar.wsclient.issue.IssueQuery;

import java.io.File;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class IssueTest {

  @ClassRule
  public static Orchestrator orchestrator = ErlangTestSuite.ORCHESTRATOR;

  private static final String PROJECT_KEY = "metrics";
  private static final String FILE_NAME = "erlcount_lib.erl";

  @BeforeClass
  public static void init() {
    orchestrator.resetData();

    SonarRunner build = ErlangTestSuite.createSonarRunner()
      .setProjectDir(new File("projects/metrics/"))
      .setProjectKey(PROJECT_KEY)
      .setProjectName(PROJECT_KEY)
      .setProjectVersion("1.0")
      .setSourceDirs("src")
      .setProperty("sonar.sourceEncoding", "UTF-8")
      .setProfile("issue-profile");
    orchestrator.executeBuild(build);
  }

  @Test
  public void one_issue_for_export_one_function_per_line() {
    List<Issue> issues = orchestrator.getServer().wsClient().issueClient().find(IssueQuery.create()).list();

    assertThat(issues).hasSize(1);
    assertThat(issues.get(0).ruleKey()).isEqualTo("erlang:ExportOneFunctionPerLine");
    assertThat(issues.get(0).componentKey()).isEqualTo(keyFor(FILE_NAME));
  }

  /* Helper methods */

  private static String keyFor(String s) {
    return PROJECT_KEY + (orchestrator.getConfiguration().getSonarVersion().isGreaterThanOrEquals("4.2") ? ":src/" : ":") + s;
  }
}
