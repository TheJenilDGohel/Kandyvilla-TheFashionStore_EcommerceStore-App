package com.kandyvilla.kandyvillathefashionstore.admin.models;

public class CategoryAddModel {
    String name;
    String description;
    String img_url;
    String type;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String category;

    public CategoryAddModel(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    String documentId;

    public CategoryAddModel() {
    }

    public CategoryAddModel(String name, String description, String img_url, String type) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.type = type;
    }


}
