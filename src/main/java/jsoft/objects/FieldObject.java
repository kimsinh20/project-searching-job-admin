package jsoft.objects;

public class FieldObject {
    private int field_id;
    private String field_name;
    private String field_notes;
    private String field_logo;
    private String field_created_date;
    private int field_delete;
    private int field_enable;
    private String field_last_modified;
    private int field_author_id;
    private int field_actions;


    // Các phương thức getter và setter cho các thuộc tính
    public int getFieldId() {
        return field_id;
    }

    public void setFieldId(int fieldId) {
        this.field_id = fieldId;
    }

    public String getFieldName() {
        return field_name;
    }

    public void setFieldName(String fieldName) {
        this.field_name = fieldName;
    }

    public String getFieldNotes() {
        return field_notes;
    }

    public void setFieldNotes(String fieldNotes) {
        this.field_notes = fieldNotes;
    }

    public String getFieldLogo() {
        return field_logo;
    }

    public void setFieldLogo(String fieldLogo) {
        this.field_logo = fieldLogo;
    }

    public String getFieldCreatedDate() {
        return field_created_date;
    }

    public void setFieldCreatedDate(String fieldCreatedDate) {
        this.field_created_date = fieldCreatedDate;
    }

    public int getFieldDelete() {
        return field_delete;
    }

    public void setFieldDelete(int fieldDelete) {
        this.field_delete = fieldDelete;
    }

    public int getFieldEnable() {
        return field_enable;
    }

    public void setFieldEnable(int fieldEnable) {
        this.field_enable = fieldEnable;
    }

    public String getFieldLastModified() {
        return field_last_modified;
    }

    public void setFieldLastModified(String fieldLastModified) {
        this.field_last_modified = fieldLastModified;
    }

    public int getFieldAuthorId() {
        return field_author_id;
    }

    public void setFieldAuthorId(int fieldAuthorId) {
        this.field_author_id = fieldAuthorId;
    }

    public int getFieldActions() {
        return field_actions;
    }

    public void setFieldActions(int fieldActions) {
        this.field_actions = fieldActions;
    }
}
