package edu.tamu.aser.scheduling.events;

import org.jetbrains.annotations.Nullable;

public class FieldAccessEventDesc extends EventDesc {

    private String fieldOwner;

    private String fieldName;

    private String fieldDesc;

    @Nullable
    private Object value;

    public FieldAccessEventDesc(EventType eventType, String fieldOwner, String fieldName, String fieldDesc) {
        this(eventType, fieldOwner, fieldName, fieldDesc, null);
    }

    public FieldAccessEventDesc(EventType eventType, String fieldOwner, String fieldName, String fieldDesc, Object value) {
        super(eventType);
        if (!(eventType.equals(EventType.READ) || eventType.equals(EventType.WRITE))) {
            throw new IllegalArgumentException("Event type has to be read or write");
        }
        this.fieldOwner = fieldOwner;
        this.fieldName = fieldName;
        this.fieldDesc = fieldDesc;
        this.value = value;
    }

    public String getFieldOwner() {
        return fieldOwner;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    @Override
    public String toString() {
        return "FieldAccessEvent [eventType=" + getEventType() + ", fieldOwner=" + fieldOwner + ", fieldName=" + fieldName + ", fieldDesc="
                + fieldDesc + ", value=" + value +"]";
    }

}
