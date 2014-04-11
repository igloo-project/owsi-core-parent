package fr.openwide.core.imports.excel.mapping.column.builder.state;

import fr.openwide.core.commons.util.functional.builder.function.generic.GenericBooleanFunctionBuildStateImpl;
import fr.openwide.core.imports.excel.mapping.AbstractExcelImportColumnSet;

public abstract class BooleanState<TSheet, TRow, TCell, TCellReference> extends GenericBooleanFunctionBuildStateImpl
		<
		AbstractExcelImportColumnSet<TSheet, TRow, TCell, TCellReference>.Column<Boolean>,
		BooleanState<TSheet, TRow, TCell, TCellReference>,
		DateState<TSheet, TRow, TCell, TCellReference>,
		IntegerState<TSheet, TRow, TCell, TCellReference>,
		StringState<TSheet, TRow, TCell, TCellReference>
		> {

}
