package com.siemens.soap.bfs.objects;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.siemens.soap.bfs.objects.webbfs.SearchObject;
import com.siemens.soap.bfs.objects.webbfs.SearchVal;
import com.siemens.soap.bfs.objects.webbfs.SelectCol;

public class SearchTest {

	private SearchObject search;

	@Before
	public void init() {
		search = new SearchObject();
	}

	@Test
	public void getMainTableName_isNull_shouldBeNull() {
		String name = search.getMainTableName();
		assertThat(null, equalTo(name));
	}

	@Test
	public void getMainTableName_notNull_shouldBeNotNull() {
		search.setMainTableName("Anl");
		String name = search.getMainTableName();
		assertThat("Anl", equalTo(name));
	}

	@Test
	public void getSearchVals_ifSearchValNotExist_searchValsShouldBeEmpty() {
		List<SearchVal> searchVals = search.getSearchVals();
		assertTrue(searchVals.isEmpty());
	}

	@Test
	public void getSearchVals_ifSearchValExists_searchValsShouldNotBeEmpty() {
		search.setSearchVal(new SearchVal("EQ", "Anl.AenDtm", "16/02/2010 12:54:100.000"));
		List<SearchVal> searchVals = search.getSearchVals();
		assertFalse(searchVals.isEmpty());
	}

	@Test
	public void getSearchVals_ifSearchValExists_verifySearchValsValues() {
		search.setSearchVal(new SearchVal("EQ", "Anl.AenDtm", "16/02/2010 12:54:100.000"));
		List<SearchVal> searchVals = search.getSearchVals();
		assertThat(1, equalTo(searchVals.size()));
		assertThat("EQ", equalTo(searchVals.get(0).getOperator()));
		assertThat("Anl.AenDtm", equalTo(searchVals.get(0).getTabAndCol()));
		assertThat("16/02/2010 12:54:100.000", equalTo(searchVals.get(0).getValue()));
	}

	@Test
	public void getSelectCols_ifSelectColNotExist_SelectColsShouldBeEmpty() {
		List<SelectCol> cols = search.getSelectCols();
		assertTrue(cols.isEmpty());
	}

	@Test
	public void getSelectCols_ifSelectColExists_SelectColsShouldNotBeEmpty() {
		search.setSelectCol(new SelectCol("0", "Anl.AnlNum"));
		List<SelectCol> cols = search.getSelectCols();
		assertFalse(cols.isEmpty());
	}

	@Test
	public void getSelectCols_ifSelectColExists_verifySelectColsVals() {
		search.setSelectCol(new SelectCol("0", "Anl.AnlNum"));
		List<SelectCol> cols = search.getSelectCols();
		assertThat(1, equalTo(cols.size()));
		assertThat("0", equalTo(cols.get(0).getOrderBy()));
		assertThat("Anl.AnlNum", equalTo(cols.get(0).getTabAndCol()));
	}
}
