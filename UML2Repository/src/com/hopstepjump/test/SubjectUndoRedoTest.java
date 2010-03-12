package com.hopstepjump.test;

import org.eclipse.uml2.*;

import static org.junit.Assert.*;
import org.eclipse.uml2.Class;
import org.junit.*;

import com.hopstepjump.repository.*;
import com.hopstepjump.repositorybase.*;

public class SubjectUndoRedoTest
{
	@Test
	public void testCreate() throws Exception
	{
		SubjectRepositoryFacet repos = setupRepository();
		
		repos.startTransaction("", "");
		Model m = repos.getTopLevelModel();
		Class cl = m.createOwnedClass("Test", false);
		Class c2 = m.createOwnedClass("Test", false);
		assertFalse(cl.isThisDeleted());
		assertFalse(c2.isThisDeleted());
		repos.commitTransaction();
		repos.undoTransaction();
		assertTrue(cl.isThisDeleted());
		Assert.assertTrue(c2.isThisDeleted());
		repos.redoTransaction();
		Assert.assertFalse(cl.isThisDeleted());
		Assert.assertFalse(c2.isThisDeleted());
	}

	@Test
	public void testAdd() throws Exception
	{
		SubjectRepositoryFacet repos = setupRepository();
		
		repos.startTransaction("", "");
		Model m = repos.getTopLevelModel();
		Class cl = m.createOwnedClass("Test", false);
		cl.createOwnedAttribute().setName("attr");
		assertEquals(1, cl.undeleted_getOwnedAttributes().size());
		repos.commitTransaction();
		repos.undoTransaction();
		assertEquals(0, cl.undeleted_getOwnedAttributes().size());
		repos.redoTransaction();
		assertEquals(1, cl.undeleted_getOwnedAttributes().size());
		repos.startTransaction("", "");
		cl.createOwnedAttribute().setName("attr2");
		assertEquals(2, cl.undeleted_getOwnedAttributes().size());
		repos.commitTransaction();		
		repos.undoTransaction();
		assertEquals(1, cl.undeleted_getOwnedAttributes().size());
		repos.undoTransaction();
		assertEquals(0, cl.undeleted_getOwnedAttributes().size());
		assertTrue(cl.isThisDeleted());
	}
	
	@Test
	public void testRemove() throws Exception
	{
		SubjectRepositoryFacet repos = setupRepository();
		
		repos.startTransaction("", "");
		Model m = repos.getTopLevelModel();
		Class cl = m.createOwnedClass("Test", false);
		cl.createOwnedAttribute().setName("attr");
		assertEquals(1, cl.undeleted_getOwnedAttributes().size());
		repos.commitTransaction();
		
		repos.startTransaction("", "");		
		cl.settable_getOwnedAttributes().remove(0);
		assertEquals(0, cl.undeleted_getOwnedAttributes().size());
		repos.commitTransaction();
		repos.undoTransaction();
		assertEquals(1, cl.undeleted_getOwnedAttributes().size());
	}
	
	@Test
	public void testMove() throws Exception
	{
		SubjectRepositoryFacet repos = setupRepository();
		
		repos.startTransaction("", "");
		Model m = repos.getTopLevelModel();
		Class cl = m.createOwnedClass("Test", false);
		Class c2 = m.createOwnedClass("Test", false);
		repos.commitTransaction();
		
		Property prop = cl.createOwnedAttribute();
		assertEquals(1, cl.undeleted_getOwnedAttributes().size());
		assertEquals(0, c2.undeleted_getOwnedAttributes().size());

		repos.startTransaction("", "");		
		cl.settable_getOwnedAttributes().remove(0);
		c2.settable_getOwnedAttributes().add(prop);
		assertEquals(0, cl.undeleted_getOwnedAttributes().size());
		assertEquals(1, c2.undeleted_getOwnedAttributes().size());
		repos.commitTransaction();
		
		repos.undoTransaction();
		assertEquals(1, cl.undeleted_getOwnedAttributes().size());
		assertEquals(0, c2.undeleted_getOwnedAttributes().size());

		repos.redoTransaction();
		assertEquals(0, cl.undeleted_getOwnedAttributes().size());
		assertEquals(1, c2.undeleted_getOwnedAttributes().size());
	}
	
	@Test
	public void testSet() throws Exception
	{
		SubjectRepositoryFacet repos = setupRepository();
		
		repos.startTransaction("", "");
		Model m = repos.getTopLevelModel();
		Class cl = m.createOwnedClass("Test", false);
		repos.commitTransaction();
		
		repos.startTransaction("", "");		
		cl.setIsAbstract(true);
		assertTrue(cl.isAbstract());
		repos.commitTransaction();
		
		repos.undoTransaction();
		assertFalse(cl.isAbstract());

		repos.redoTransaction();
		assertTrue(cl.isAbstract());
	}
	
	@Test
	public void testAnonymous1() throws Exception
	{
		SubjectRepositoryFacet repos = setupRepository();
		
		repos.startTransaction("", "");
		Model m = repos.getTopLevelModel();
		Class cl = UML2Factory.eINSTANCE.createClass();
		m.settable_getOwnedMembers().add(cl);
		repos.commitTransaction();
		
		repos.undoTransaction();
    assertEquals(0, m.undeleted_getOwnedMembers().size());
    assertTrue(cl.isThisDeleted());
	}
	
	@Test
	public void testAnonymous2() throws Exception
	{
		SubjectRepositoryFacet repos = setupRepository();
		
		repos.startTransaction("", "");
		Model m = repos.getTopLevelModel();
		Class cl = m.createOwnedClass("Test", false);
		Property prop = cl.createOwnedAttribute();
		repos.commitTransaction();
		
		repos.startTransaction("", "");
    Expression expression = UML2Factory.eINSTANCE.createExpression();
    expression.setBody("Hello");
    AppliedBasicStereotypeValue applied = cl.createAppliedBasicStereotypeValues();
    applied.setProperty(prop);
    applied.setValue(expression);
    assertEquals(1, cl.undeleted_getAppliedBasicStereotypeValues().size());
		repos.commitTransaction();
		
		repos.undoTransaction();
    assertEquals(0, cl.undeleted_getAppliedBasicStereotypeValues().size());
    assertTrue(expression.isThisDeleted());

		repos.redoTransaction();
    assertEquals(1, cl.undeleted_getAppliedBasicStereotypeValues().size());
    assertFalse(expression.isThisDeleted());
	}
	
	private SubjectRepositoryFacet setupRepository() throws RepositoryOpeningException
	{
		XMLSubjectRepositoryGem gem = XMLSubjectRepositoryGem.openFile(null, false);
		SubjectRepositoryFacet repos = gem.getSubjectRepositoryFacet();
		GlobalSubjectRepository.repository = repos;
		return repos;
	}
}
