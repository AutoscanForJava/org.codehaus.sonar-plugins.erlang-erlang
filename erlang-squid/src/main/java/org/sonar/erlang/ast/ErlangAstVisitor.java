/*
 * Sonar Erlang Plugin
 * Copyright (C) 2012 Tamas Kende
 * kende.tamas@gmail.com
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
package org.sonar.erlang.ast;

import com.sonar.sslr.squid.SquidAstVisitor;
import org.sonar.squid.api.SourceClass;
import org.sonar.squid.api.SourceCode;
import org.sonar.squid.api.SourceFile;
import org.sonar.squid.api.SourcePackage;
import org.sonar.sslr.parser.LexerlessGrammar;

public abstract class ErlangAstVisitor  extends SquidAstVisitor<LexerlessGrammar> {

  protected final SourceFile peekSourceFile() {
    SourceCode sourceCode = getContext().peekSourceCode();
    if (sourceCode.isType(SourceFile.class)) {
      return (SourceFile) getContext().peekSourceCode();
    }
    return sourceCode.getParent(SourceFile.class);
  }

  protected final SourcePackage peekParentPackage() {
    SourceCode sourceCode = getContext().peekSourceCode();
    if (sourceCode.isType(SourcePackage.class)) {
      return (SourcePackage) getContext().peekSourceCode();
    }
    return sourceCode.getParent(SourcePackage.class);
  }

  protected final SourceClass peekSourceClass() {
    SourceCode sourceCode = getContext().peekSourceCode();
    if (sourceCode.isType(SourceClass.class)) {
      return (SourceClass) sourceCode;
    }
    return sourceCode.getParent(SourceClass.class);
  }

}
