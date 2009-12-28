package com.hopstepjump.jumble.html;

import com.hopstepjump.idraw.foundation.*;
import com.hopstepjump.repositorybase.*;

public interface DocumentationPackageVisitor
{
  void visit(DocumentationPackage pkg) throws
    HTMLGenerationException,
    PersistentFigureRecreatorNotFoundException,
    RepositoryPersistenceException;
}
