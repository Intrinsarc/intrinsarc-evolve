package com.intrinsarc.evolve.html;

import com.intrinsarc.idraw.foundation.*;
import com.intrinsarc.repositorybase.*;

public interface DocumentationPackageVisitor
{
  void visit(DocumentationPackage pkg) throws
    HTMLGenerationException,
    PersistentFigureRecreatorNotFoundException,
    RepositoryPersistenceException;
}
