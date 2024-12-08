package com.app.blotter.model;

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
