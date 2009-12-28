

/**
 * <copyright>
 * </copyright>
 *
 * $Id: J_Figure.java,v 1.1 2009-03-04 23:06:48 andrew Exp $
 */
package org.eclipse.uml2;


import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>JFigure</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.uml2.J_Figure#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getRecreator <em>Recreator</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getAnchor1Id <em>Anchor1 Id</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getAnchor2Id <em>Anchor2 Id</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getContainedName <em>Contained Name</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getText <em>Text</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getVirtualPoint <em>Virtual Point</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getPoints <em>Points</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getBrOffset <em>Br Offset</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getTlOffset <em>Tl Offset</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getShow <em>Show</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getAutosized <em>Autosized</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getIcon <em>Icon</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getPoint <em>Point</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getDimensions <em>Dimensions</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getSuppressAttributes <em>Suppress Attributes</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getSuppressOperations <em>Suppress Operations</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getSuppressContents <em>Suppress Contents</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getOffset <em>Offset</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getMin <em>Min</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getAccessibility <em>Accessibility</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getClassifierScope <em>Classifier Scope</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.uml2.J_Figure#getSubject <em>Subject</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.uml2.UML2Package#getJ_Figure()
 * @model
 * @generated
 */
public interface J_Figure extends J_FigureContainer{
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String copyright = "Copyright (c) IBM Corporation and others."; //$NON-NLS-1$

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Id</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Id()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getId();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
  void setId(String value);





	/**
	 * Returns the value of the '<em><b>Recreator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Recreator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Recreator</em>' attribute.
	 * @see #setRecreator(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Recreator()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getRecreator();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getRecreator <em>Recreator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Recreator</em>' attribute.
	 * @see #getRecreator()
	 * @generated
	 */
	void setRecreator(String value);





	/**
	 * Returns the value of the '<em><b>Anchor1 Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anchor1 Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anchor1 Id</em>' attribute.
	 * @see #setAnchor1Id(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Anchor1Id()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getAnchor1Id();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getAnchor1Id <em>Anchor1 Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anchor1 Id</em>' attribute.
	 * @see #getAnchor1Id()
	 * @generated
	 */
	void setAnchor1Id(String value);





	/**
	 * Returns the value of the '<em><b>Anchor2 Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Anchor2 Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Anchor2 Id</em>' attribute.
	 * @see #setAnchor2Id(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Anchor2Id()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getAnchor2Id();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getAnchor2Id <em>Anchor2 Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Anchor2 Id</em>' attribute.
	 * @see #getAnchor2Id()
	 * @generated
	 */
	void setAnchor2Id(String value);





	/**
	 * Returns the value of the '<em><b>Contained Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Contained Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contained Name</em>' attribute.
	 * @see #setContainedName(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_ContainedName()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getContainedName();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getContainedName <em>Contained Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Contained Name</em>' attribute.
	 * @see #getContainedName()
	 * @generated
	 */
	void setContainedName(String value);





	/**
	 * Returns the value of the '<em><b>Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Text</em>' attribute.
	 * @see #setText(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Text()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getText();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getText <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Text</em>' attribute.
	 * @see #getText()
	 * @generated
	 */
	void setText(String value);





	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Name()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);





	/**
	 * Returns the value of the '<em><b>Virtual Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual Point</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual Point</em>' attribute.
	 * @see #setVirtualPoint(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_VirtualPoint()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getVirtualPoint();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getVirtualPoint <em>Virtual Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Virtual Point</em>' attribute.
	 * @see #getVirtualPoint()
	 * @generated
	 */
	void setVirtualPoint(String value);





	/**
	 * Returns the value of the '<em><b>Points</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Points</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Points</em>' attribute.
	 * @see #setPoints(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Points()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getPoints();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getPoints <em>Points</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Points</em>' attribute.
	 * @see #getPoints()
	 * @generated
	 */
	void setPoints(String value);





	/**
	 * Returns the value of the '<em><b>Br Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Br Offset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Br Offset</em>' attribute.
	 * @see #setBrOffset(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_BrOffset()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getBrOffset();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getBrOffset <em>Br Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Br Offset</em>' attribute.
	 * @see #getBrOffset()
	 * @generated
	 */
	void setBrOffset(String value);





	/**
	 * Returns the value of the '<em><b>Tl Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tl Offset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tl Offset</em>' attribute.
	 * @see #setTlOffset(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_TlOffset()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getTlOffset();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getTlOffset <em>Tl Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tl Offset</em>' attribute.
	 * @see #getTlOffset()
	 * @generated
	 */
	void setTlOffset(String value);





	/**
	 * Returns the value of the '<em><b>Show</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Show</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Show</em>' attribute.
	 * @see #setShow(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Show()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getShow();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getShow <em>Show</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Show</em>' attribute.
	 * @see #getShow()
	 * @generated
	 */
  void setShow(String value);





	/**
	 * Returns the value of the '<em><b>Autosized</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Autosized</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Autosized</em>' attribute.
	 * @see #setAutosized(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Autosized()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getAutosized();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getAutosized <em>Autosized</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Autosized</em>' attribute.
	 * @see #getAutosized()
	 * @generated
	 */
  void setAutosized(String value);





	/**
	 * Returns the value of the '<em><b>Icon</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Icon</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Icon</em>' attribute.
	 * @see #setIcon(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Icon()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getIcon();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getIcon <em>Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Icon</em>' attribute.
	 * @see #getIcon()
	 * @generated
	 */
  void setIcon(String value);





	/**
	 * Returns the value of the '<em><b>Point</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Point</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Point</em>' attribute.
	 * @see #setPoint(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Point()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getPoint();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getPoint <em>Point</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Point</em>' attribute.
	 * @see #getPoint()
	 * @generated
	 */
	void setPoint(String value);





	/**
	 * Returns the value of the '<em><b>Dimensions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dimensions</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dimensions</em>' attribute.
	 * @see #setDimensions(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Dimensions()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getDimensions();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getDimensions <em>Dimensions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dimensions</em>' attribute.
	 * @see #getDimensions()
	 * @generated
	 */
	void setDimensions(String value);





	/**
	 * Returns the value of the '<em><b>Suppress Attributes</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Suppress Attributes</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Suppress Attributes</em>' attribute.
	 * @see #setSuppressAttributes(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_SuppressAttributes()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getSuppressAttributes();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getSuppressAttributes <em>Suppress Attributes</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Suppress Attributes</em>' attribute.
	 * @see #getSuppressAttributes()
	 * @generated
	 */
  void setSuppressAttributes(String value);





	/**
	 * Returns the value of the '<em><b>Suppress Operations</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Suppress Operations</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Suppress Operations</em>' attribute.
	 * @see #setSuppressOperations(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_SuppressOperations()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getSuppressOperations();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getSuppressOperations <em>Suppress Operations</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Suppress Operations</em>' attribute.
	 * @see #getSuppressOperations()
	 * @generated
	 */
  void setSuppressOperations(String value);





	/**
	 * Returns the value of the '<em><b>Suppress Contents</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Suppress Contents</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Suppress Contents</em>' attribute.
	 * @see #setSuppressContents(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_SuppressContents()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getSuppressContents();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getSuppressContents <em>Suppress Contents</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Suppress Contents</em>' attribute.
	 * @see #getSuppressContents()
	 * @generated
	 */
  void setSuppressContents(String value);





	/**
	 * Returns the value of the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Offset</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Offset</em>' attribute.
	 * @see #setOffset(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Offset()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getOffset();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getOffset <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Offset</em>' attribute.
	 * @see #getOffset()
	 * @generated
	 */
	void setOffset(String value);





	/**
	 * Returns the value of the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min</em>' attribute.
	 * @see #setMin(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Min()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getMin();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getMin <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min</em>' attribute.
	 * @see #getMin()
	 * @generated
	 */
	void setMin(String value);





	/**
	 * Returns the value of the '<em><b>Accessibility</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Accessibility</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Accessibility</em>' attribute.
	 * @see #setAccessibility(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Accessibility()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getAccessibility();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getAccessibility <em>Accessibility</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Accessibility</em>' attribute.
	 * @see #getAccessibility()
	 * @generated
	 */
	void setAccessibility(String value);





	/**
	 * Returns the value of the '<em><b>Classifier Scope</b></em>' attribute.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Classifier Scope</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Classifier Scope</em>' attribute.
	 * @see #setClassifierScope(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_ClassifierScope()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
  String getClassifierScope();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getClassifierScope <em>Classifier Scope</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Classifier Scope</em>' attribute.
	 * @see #getClassifierScope()
	 * @generated
	 */
  void setClassifierScope(String value);





	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Type()
	 * @model dataType="org.eclipse.uml2.String"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);





	/**
	 * Returns the value of the '<em><b>Subject</b></em>' reference.
	 * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Subject</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' reference.
	 * @see #setSubject(Element)
	 * @see org.eclipse.uml2.UML2Package#getJ_Figure_Subject()
	 * @model
	 * @generated
	 */
  Element getSubject();

	/**
	 * Sets the value of the '{@link org.eclipse.uml2.J_Figure#getSubject <em>Subject</em>}' reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' reference.
	 * @see #getSubject()
	 * @generated
	 */
  void setSubject(Element value);




	/**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  Element undeleted_getSubject();

} // J_Figure
