package com.spingular.web.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.spingular.web.domain.ConfigVariables} entity. This class is used
 * in {@link com.spingular.web.web.rest.ConfigVariablesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /config-variables?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ConfigVariablesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter configVarLong1;

    private LongFilter configVarLong2;

    private LongFilter configVarLong3;

    private LongFilter configVarLong4;

    private LongFilter configVarLong5;

    private LongFilter configVarLong6;

    private LongFilter configVarLong7;

    private LongFilter configVarLong8;

    private LongFilter configVarLong9;

    private LongFilter configVarLong10;

    private LongFilter configVarLong11;

    private LongFilter configVarLong12;

    private LongFilter configVarLong13;

    private LongFilter configVarLong14;

    private LongFilter configVarLong15;

    private BooleanFilter configVarBoolean16;

    private BooleanFilter configVarBoolean17;

    private BooleanFilter configVarBoolean18;

    private StringFilter configVarString19;

    private StringFilter configVarString20;

    public ConfigVariablesCriteria(){
    }

    public ConfigVariablesCriteria(ConfigVariablesCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.configVarLong1 = other.configVarLong1 == null ? null : other.configVarLong1.copy();
        this.configVarLong2 = other.configVarLong2 == null ? null : other.configVarLong2.copy();
        this.configVarLong3 = other.configVarLong3 == null ? null : other.configVarLong3.copy();
        this.configVarLong4 = other.configVarLong4 == null ? null : other.configVarLong4.copy();
        this.configVarLong5 = other.configVarLong5 == null ? null : other.configVarLong5.copy();
        this.configVarLong6 = other.configVarLong6 == null ? null : other.configVarLong6.copy();
        this.configVarLong7 = other.configVarLong7 == null ? null : other.configVarLong7.copy();
        this.configVarLong8 = other.configVarLong8 == null ? null : other.configVarLong8.copy();
        this.configVarLong9 = other.configVarLong9 == null ? null : other.configVarLong9.copy();
        this.configVarLong10 = other.configVarLong10 == null ? null : other.configVarLong10.copy();
        this.configVarLong11 = other.configVarLong11 == null ? null : other.configVarLong11.copy();
        this.configVarLong12 = other.configVarLong12 == null ? null : other.configVarLong12.copy();
        this.configVarLong13 = other.configVarLong13 == null ? null : other.configVarLong13.copy();
        this.configVarLong14 = other.configVarLong14 == null ? null : other.configVarLong14.copy();
        this.configVarLong15 = other.configVarLong15 == null ? null : other.configVarLong15.copy();
        this.configVarBoolean16 = other.configVarBoolean16 == null ? null : other.configVarBoolean16.copy();
        this.configVarBoolean17 = other.configVarBoolean17 == null ? null : other.configVarBoolean17.copy();
        this.configVarBoolean18 = other.configVarBoolean18 == null ? null : other.configVarBoolean18.copy();
        this.configVarString19 = other.configVarString19 == null ? null : other.configVarString19.copy();
        this.configVarString20 = other.configVarString20 == null ? null : other.configVarString20.copy();
    }

    @Override
    public ConfigVariablesCriteria copy() {
        return new ConfigVariablesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getConfigVarLong1() {
        return configVarLong1;
    }

    public void setConfigVarLong1(LongFilter configVarLong1) {
        this.configVarLong1 = configVarLong1;
    }

    public LongFilter getConfigVarLong2() {
        return configVarLong2;
    }

    public void setConfigVarLong2(LongFilter configVarLong2) {
        this.configVarLong2 = configVarLong2;
    }

    public LongFilter getConfigVarLong3() {
        return configVarLong3;
    }

    public void setConfigVarLong3(LongFilter configVarLong3) {
        this.configVarLong3 = configVarLong3;
    }

    public LongFilter getConfigVarLong4() {
        return configVarLong4;
    }

    public void setConfigVarLong4(LongFilter configVarLong4) {
        this.configVarLong4 = configVarLong4;
    }

    public LongFilter getConfigVarLong5() {
        return configVarLong5;
    }

    public void setConfigVarLong5(LongFilter configVarLong5) {
        this.configVarLong5 = configVarLong5;
    }

    public LongFilter getConfigVarLong6() {
        return configVarLong6;
    }

    public void setConfigVarLong6(LongFilter configVarLong6) {
        this.configVarLong6 = configVarLong6;
    }

    public LongFilter getConfigVarLong7() {
        return configVarLong7;
    }

    public void setConfigVarLong7(LongFilter configVarLong7) {
        this.configVarLong7 = configVarLong7;
    }

    public LongFilter getConfigVarLong8() {
        return configVarLong8;
    }

    public void setConfigVarLong8(LongFilter configVarLong8) {
        this.configVarLong8 = configVarLong8;
    }

    public LongFilter getConfigVarLong9() {
        return configVarLong9;
    }

    public void setConfigVarLong9(LongFilter configVarLong9) {
        this.configVarLong9 = configVarLong9;
    }

    public LongFilter getConfigVarLong10() {
        return configVarLong10;
    }

    public void setConfigVarLong10(LongFilter configVarLong10) {
        this.configVarLong10 = configVarLong10;
    }

    public LongFilter getConfigVarLong11() {
        return configVarLong11;
    }

    public void setConfigVarLong11(LongFilter configVarLong11) {
        this.configVarLong11 = configVarLong11;
    }

    public LongFilter getConfigVarLong12() {
        return configVarLong12;
    }

    public void setConfigVarLong12(LongFilter configVarLong12) {
        this.configVarLong12 = configVarLong12;
    }

    public LongFilter getConfigVarLong13() {
        return configVarLong13;
    }

    public void setConfigVarLong13(LongFilter configVarLong13) {
        this.configVarLong13 = configVarLong13;
    }

    public LongFilter getConfigVarLong14() {
        return configVarLong14;
    }

    public void setConfigVarLong14(LongFilter configVarLong14) {
        this.configVarLong14 = configVarLong14;
    }

    public LongFilter getConfigVarLong15() {
        return configVarLong15;
    }

    public void setConfigVarLong15(LongFilter configVarLong15) {
        this.configVarLong15 = configVarLong15;
    }

    public BooleanFilter getConfigVarBoolean16() {
        return configVarBoolean16;
    }

    public void setConfigVarBoolean16(BooleanFilter configVarBoolean16) {
        this.configVarBoolean16 = configVarBoolean16;
    }

    public BooleanFilter getConfigVarBoolean17() {
        return configVarBoolean17;
    }

    public void setConfigVarBoolean17(BooleanFilter configVarBoolean17) {
        this.configVarBoolean17 = configVarBoolean17;
    }

    public BooleanFilter getConfigVarBoolean18() {
        return configVarBoolean18;
    }

    public void setConfigVarBoolean18(BooleanFilter configVarBoolean18) {
        this.configVarBoolean18 = configVarBoolean18;
    }

    public StringFilter getConfigVarString19() {
        return configVarString19;
    }

    public void setConfigVarString19(StringFilter configVarString19) {
        this.configVarString19 = configVarString19;
    }

    public StringFilter getConfigVarString20() {
        return configVarString20;
    }

    public void setConfigVarString20(StringFilter configVarString20) {
        this.configVarString20 = configVarString20;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ConfigVariablesCriteria that = (ConfigVariablesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(configVarLong1, that.configVarLong1) &&
            Objects.equals(configVarLong2, that.configVarLong2) &&
            Objects.equals(configVarLong3, that.configVarLong3) &&
            Objects.equals(configVarLong4, that.configVarLong4) &&
            Objects.equals(configVarLong5, that.configVarLong5) &&
            Objects.equals(configVarLong6, that.configVarLong6) &&
            Objects.equals(configVarLong7, that.configVarLong7) &&
            Objects.equals(configVarLong8, that.configVarLong8) &&
            Objects.equals(configVarLong9, that.configVarLong9) &&
            Objects.equals(configVarLong10, that.configVarLong10) &&
            Objects.equals(configVarLong11, that.configVarLong11) &&
            Objects.equals(configVarLong12, that.configVarLong12) &&
            Objects.equals(configVarLong13, that.configVarLong13) &&
            Objects.equals(configVarLong14, that.configVarLong14) &&
            Objects.equals(configVarLong15, that.configVarLong15) &&
            Objects.equals(configVarBoolean16, that.configVarBoolean16) &&
            Objects.equals(configVarBoolean17, that.configVarBoolean17) &&
            Objects.equals(configVarBoolean18, that.configVarBoolean18) &&
            Objects.equals(configVarString19, that.configVarString19) &&
            Objects.equals(configVarString20, that.configVarString20);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        configVarLong1,
        configVarLong2,
        configVarLong3,
        configVarLong4,
        configVarLong5,
        configVarLong6,
        configVarLong7,
        configVarLong8,
        configVarLong9,
        configVarLong10,
        configVarLong11,
        configVarLong12,
        configVarLong13,
        configVarLong14,
        configVarLong15,
        configVarBoolean16,
        configVarBoolean17,
        configVarBoolean18,
        configVarString19,
        configVarString20
        );
    }

    @Override
    public String toString() {
        return "ConfigVariablesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (configVarLong1 != null ? "configVarLong1=" + configVarLong1 + ", " : "") +
                (configVarLong2 != null ? "configVarLong2=" + configVarLong2 + ", " : "") +
                (configVarLong3 != null ? "configVarLong3=" + configVarLong3 + ", " : "") +
                (configVarLong4 != null ? "configVarLong4=" + configVarLong4 + ", " : "") +
                (configVarLong5 != null ? "configVarLong5=" + configVarLong5 + ", " : "") +
                (configVarLong6 != null ? "configVarLong6=" + configVarLong6 + ", " : "") +
                (configVarLong7 != null ? "configVarLong7=" + configVarLong7 + ", " : "") +
                (configVarLong8 != null ? "configVarLong8=" + configVarLong8 + ", " : "") +
                (configVarLong9 != null ? "configVarLong9=" + configVarLong9 + ", " : "") +
                (configVarLong10 != null ? "configVarLong10=" + configVarLong10 + ", " : "") +
                (configVarLong11 != null ? "configVarLong11=" + configVarLong11 + ", " : "") +
                (configVarLong12 != null ? "configVarLong12=" + configVarLong12 + ", " : "") +
                (configVarLong13 != null ? "configVarLong13=" + configVarLong13 + ", " : "") +
                (configVarLong14 != null ? "configVarLong14=" + configVarLong14 + ", " : "") +
                (configVarLong15 != null ? "configVarLong15=" + configVarLong15 + ", " : "") +
                (configVarBoolean16 != null ? "configVarBoolean16=" + configVarBoolean16 + ", " : "") +
                (configVarBoolean17 != null ? "configVarBoolean17=" + configVarBoolean17 + ", " : "") +
                (configVarBoolean18 != null ? "configVarBoolean18=" + configVarBoolean18 + ", " : "") +
                (configVarString19 != null ? "configVarString19=" + configVarString19 + ", " : "") +
                (configVarString20 != null ? "configVarString20=" + configVarString20 + ", " : "") +
            "}";
    }

}
