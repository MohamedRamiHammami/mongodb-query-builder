package com.app.blotter.model.filter;

import com.app.blotter.model.AdaptableColumnDataType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ColumnFilterDef {

    private ColumnFilter columnFilter;
    private AdaptableColumnDataType dataType;
}
