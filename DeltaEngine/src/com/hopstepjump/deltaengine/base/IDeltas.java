package com.hopstepjump.deltaengine.base;

import java.util.*;

public interface IDeltas
{
	Set<ErrorDescription> isWellFormed(DEStratum perspective);
	Set<DeltaPair> getConstituents(DEStratum perspective);
	Set<DeltaPair> getConstituents(DEStratum perspective, boolean omitSynthetics);
	Set<DeltaPair> getPairs(DEStratum perspective, DeltaPairTypeEnum toGet);
	Collection<String> getIds(DEStratum perspective, DeltaIdTypeEnum idType);

	Set<DeltaPair> getAddObjects();
	Set<String> getDeleteObjects();
	Set<DeltaPair> getReplaceObjects();
}