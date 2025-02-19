package bg.sofia.uni.fmi.mjt.csvprocessor.table.column;

import bg.sofia.uni.fmi.mjt.csvprocessor.validator.Validator;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class BaseColumn implements Column {
    private final Set<String> values;

    public BaseColumn() {
        this(new LinkedHashSet<>());
    }

    public BaseColumn(Set<String> values) {
        this.values = values;
    }

    @Override
    public void addData(String data) {
        Validator.validateNotNull(data, "data");
        Validator.validateNotBlank(data, "data");

        values.add(data);
    }

    @Override
    public Collection<String> getData() {
        return Collections.unmodifiableSet(values);
    }
}
